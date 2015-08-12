package com.zyxf.workdivision;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.manager.DialogManager;
import com.zyxf.workdivision.utils.BrowserUtils;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/18.
 */
public class MoreActivity extends BaseActivity {
    private TextView titleTv;
    private Dialog progressDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_more);

        titleTv = (TextView) this.findViewById(R.id.tv_title);


        titleTv.setText("更多");
    }

    @Override
    protected void setListeners() {
        this.findViewById(R.id.tv_info).setOnClickListener(this);
        this.findViewById(R.id.btn_submit).setOnClickListener(this);
        this.findViewById(R.id.tv_contact).setOnClickListener(this);
        this.findViewById(R.id.tv_production).setOnClickListener(this);
        this.findViewById(R.id.tv_shop).setOnClickListener(this);
        this.findViewById(R.id.tv_version).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                logout();
                break;
            case R.id.tv_info:
                startActivity(MyInfomationActivity.class);
                break;
            case R.id.tv_production:
                startActivity(IntroduceActivity.class);
                break;
            case R.id.tv_shop:
                BrowserUtils.openWebSite(getApplicationContext(), Urls.URL_JD);
                break;
            case R.id.tv_contact:
                startActivity(ContactUsActivity.class);
                break;
            case R.id.tv_version:
                //TODO 检查版本
                break;
        }
    }

    private void logout() {
        progressDialog = DialogManager.showProgressDialog(this, "登出中...");
        StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_LOGOUT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                PreferenceUtils.putBoolean(getApplicationContext(), Constants.HAS_LOGIN, false);
                mCache.put(Constants.CHECK, "");
                progressDialog.dismiss();
                startActivity(LoginActivity.class);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("登出失败\n" + volleyError.toString());
                }
                PreferenceUtils.putBoolean(getApplicationContext(), Constants.HAS_LOGIN, false);
                mCache.put(Constants.CHECK, "");
                progressDialog.dismiss();
                startActivity(LoginActivity.class);
                finish();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null));
                return map;
            }
        };
        mQueue.add(request);
    }
}
