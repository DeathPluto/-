package com.zyxf.workdivision.test;

import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zyxf.workdivision.R;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.http.VolleyTool;
import com.zyxf.workdivision.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class TestActivity extends BaseActivity {

    private VolleyTool volleyTool;
    private RequestQueue queue;
    private String cookie;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        volleyTool = VolleyTool.getInstance(getApplicationContext());
        queue = volleyTool.getQueue();
    }

    @Override
    protected void setListeners() {

    }

    public void firstDayOfWeek(View v) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        LogUtils.i("********得到本周一的日期*******" + df.format(cal.getTime()));
    }

    public void firstDayOfMonth(View v) {

    }


    public void checkLogin(View v) {
        StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_CHECK_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Check check = gson.fromJson(s, Check.class);
                LogUtils.i(check.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("未检查到登陆\n" + volleyError.toString());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", cookie);
                return map;
            }

        };
        queue.add(request);
    }


    public void leaderLogin(View v) {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_LEADER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i("登陆成功");
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
                map.put("username", "李四");
                map.put("password", "123456");
                return map;
            }

        };
        queue.add(request);
    }


    public void workerLogin(View v) {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i("登陆成功");
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
                map.put("id_string", "36112319850226193X");
                map.put("password", "123456");
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    cookie = responseHeaders.get("Set-Cookie");
                    LogUtils.i("cookie" + cookie);
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(request);
    }

    public void timeCheckingin(View v) {
        StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_SEARCH_CHECKINGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i("错误");
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
            }
        }) {

            private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("begin_timestamp", getStartTime());
                map.put("end_timestamp", getEndTime());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", HalcyonApplication.getCookie());
                return map;
            }

            private String getStartTime() {
                Calendar todayStart = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));
                todayStart.set(Calendar.HOUR, 0);
                todayStart.set(Calendar.MINUTE, 0);
                todayStart.set(Calendar.SECOND, 0);
                todayStart.set(Calendar.MILLISECOND, 0);
                return sdf.format(todayStart.getTime());
            }

            private String getEndTime() {
                Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));
                return sdf.format(currentTime.getTime());
            }
        };
        mQueue.add(request);
    }

    public void timestamp(View v) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayStart = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        LogUtils.i(sdf.format(todayStart.getTime()) + "\n" + sdf.format(new Date()));
    }


    @Override
    public void onClick(View v) {

    }
}
