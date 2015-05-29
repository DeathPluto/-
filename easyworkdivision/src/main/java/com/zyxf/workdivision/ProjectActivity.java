package com.zyxf.workdivision;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.Project;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.manager.DialogManager;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class ProjectActivity extends BaseActivity {
    private TextView titleTv;
    private Project project;

    private TextView chineseTv;
    private TextView englishTv;
    private TextView countryTv;
    private TextView cityTv;
    private TextView addressTv;
    private TextView personTv;
    private TextView faxTv;
    private TextView postTv;
    private TextView phone1Tv;
    private TextView phone2Tv;
    private Dialog progressDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_project);


        titleTv = (TextView) this.findViewById(R.id.tv_title);

        chineseTv = (TextView) this.findViewById(R.id.tv_name);
        englishTv = (TextView) this.findViewById(R.id.tv_name_en);
        countryTv = (TextView) this.findViewById(R.id.tv_country);
        cityTv = (TextView) this.findViewById(R.id.tv_city);
        addressTv = (TextView) this.findViewById(R.id.tv_address);
        personTv = (TextView) this.findViewById(R.id.tv_person);
        faxTv = (TextView) this.findViewById(R.id.tv_fax);
        postTv = (TextView) this.findViewById(R.id.tv_post);
        phone1Tv = (TextView) this.findViewById(R.id.tv_phone_1);
        phone2Tv = (TextView) this.findViewById(R.id.tv_phone_2);


    }

    @Override
    public void initData() {
        chineseTv.setText(project.chinese_name);
        englishTv.setText(project.english_name);
        countryTv.setText(project.country);
        cityTv.setText(project.city);
        addressTv.setText(project.address);
        personTv.setText(project.supervisor);
        faxTv.setText(project.fax);
        postTv.setText(project.zip_code);
        phone1Tv.setText(project.phone_number_1);
        phone2Tv.setText(project.phone_number_2);
    }

    @Override
    protected void initAfterSetListeners() {
        titleTv.setText("工地工程信息");

        progressDialog = DialogManager.showProgressDialog(this, "获取信息中...");
        String url = Urls.URL_CHECK_PROJECT;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                project = gson.fromJson(s, Project.class);
                initData();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
                Toast.makeText(getApplicationContext(), "网络异常,获取信息失败", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                Map<String, String> map = new HashMap<>();
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
