package com.zyxf.workdivision.test;

import android.net.Uri;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zyxf.workdivision.R;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.http.VolleyTool;
import com.zyxf.workdivision.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class TestActivity extends BaseActivity {

    private VolleyTool volleyTool;
    private RequestQueue queue;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        volleyTool = VolleyTool.getInstance(getApplicationContext());
        queue = volleyTool.getQueue();
    }

    @Override
    protected void setListeners() {

    }

    public void leaderLogin(View v) {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_LEADER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i("cuowu");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", Uri.encode("%E6%9D%8E%E5%9B%9B"));
                map.put("password", "123456");
                return map;
            }

        };
        queue.add(request);
    }


    @Override
    public void onClick(View v) {

    }
}
