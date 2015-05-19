package com.zyxf.workdivision;

import android.test.InstrumentationTestCase;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.http.VolleyTool;
import com.zyxf.workdivision.utils.LogUtils;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class RequestTest extends InstrumentationTestCase {

    public void test() throws Exception {
    }

    public void testLogin() throws Exception {
        VolleyTool volleyTool = VolleyTool.getInstance(HalcyonApplication.getApplication());
        RequestQueue queue = volleyTool.getQueue();
        StringRequest request = new StringRequest("http://yigongfen.cn/worker", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i("shibai");
            }
        });
        queue.add(request);
    }
}
