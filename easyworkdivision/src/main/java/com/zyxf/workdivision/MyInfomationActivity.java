package com.zyxf.workdivision;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.User;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/29.
 */
public class MyInfomationActivity extends BaseActivity {
    private TextView titleTv;
    private TextView nameTv;
    private TextView sexTv;
    private TextView ethnicTv;
    private TextView birthTv;
    private TextView addressTv;
    private TextView idTv;
    private User user;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_infomation);

        titleTv = (TextView) this.findViewById(R.id.tv_title);

        nameTv = (TextView) this.findViewById(R.id.tv_name);
        sexTv = (TextView) this.findViewById(R.id.tv_sex);
        ethnicTv = (TextView) this.findViewById(R.id.tv_ethnic);
        birthTv = (TextView) this.findViewById(R.id.tv_birthdate);
        addressTv = (TextView) this.findViewById(R.id.tv_address);
        idTv = (TextView) this.findViewById(R.id.tv_id);
    }

    // 361123 19850226 193X

    @Override
    public void initData() {
        String name = user.name.substring(0, user.name.length() - 1) + "*";
        String id_string = user.id_string.substring(0, 6) + "********" + user.id_string.substring(14);
        nameTv.setText(name);
        sexTv.setText(user.sex);
        ethnicTv.setText(user.ethnic);
        birthTv.setText(user.birthday);
        addressTv.setText(user.address);
        idTv.setText(id_string);
    }

    @Override
    protected void initAfterSetListeners() {
        titleTv.setText("我的信息");
        Check check = (Check) mCache.getAsObject(Constants.CHECK);
        String url = Urls.URL_CHECK_SINGLE_USER + check.user.id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                user = gson.fromJson(s, User.class);
                initData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                map.put("Cookie", cookie);
                return map;
            }
        };
        mQueue.add(request);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
