package com.zyxf.workdivision;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.test.TestActivity;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/17.
 */
public class LoginActivity extends BaseActivity {
    private EditText accountEt;
    private EditText passwordEt;

    private boolean hasLogin = false;

    @Override
    protected void initView() {
        checkLogin();
        if (hasLogin) {
            startActivity(MainActivity.class);
            finish();
        }
        setContentView(R.layout.activity_login);

        accountEt = (EditText) this.findViewById(R.id.et_account);
        passwordEt = (EditText) this.findViewById(R.id.et_password);

    }

    private void checkLogin() {
        hasLogin = PreferenceUtils.getBoolean(getApplicationContext(), Constants.HAS_LOGIN, false);
        Check check = (Check) mCache.getAsObject(Constants.CHECK);
//        HalcyonApplication.setCheck();
        if (check != null) {
            HalcyonApplication.setCheck(check);
        }

        if (mCache.getAsJSONObject(Constants.LOGINED_USER) != null) {
            hasLogin = true;
        } else {
            StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_CHECK_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    Check check = gson.fromJson(s, Check.class);
                    LogUtils.i(check.toString());
                    mCache.put(Constants.CHECK, check);
                    mCache.put(Constants.LOGINED_USER, check.user);
                    PreferenceUtils.putBoolean(getApplicationContext(), Constants.HAS_LOGIN, true);
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
                    String cookie = PreferenceUtils.getString(HalcyonApplication.getApplication(), Constants.COOKIE, "");
                    map.put("Cookie", cookie);
                    return map;
                }

            };
            mQueue.add(request);
        }
    }

    @Override
    protected void setListeners() {
        this.findViewById(R.id.ll_introduction).setOnClickListener(this);
        this.findViewById(R.id.ll_contact_us).setOnClickListener(this);
        this.findViewById(R.id.ll_share).setOnClickListener(this);
        this.findViewById(R.id.ll_shop).setOnClickListener(this);
        this.findViewById(R.id.tv_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_introduction:
                startActivity(IntroduceActivity.class);
                break;
            case R.id.ll_contact_us:
                startActivity(ContactUsActivity.class);
                break;
            case R.id.ll_share:
//TODO 7、	推荐好友，能链接到QQ、微信、微博，将软件信息和下载链接发送给好友。
                break;
            case R.id.ll_shop:
                //TODO 链接到京东
                startActivity(TestActivity.class);
                break;
            case R.id.tv_login:
                login();
                break;
        }
    }

    private void login() {
        final String account = accountEt.getText().toString().trim();
        final String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            passwordEt.setText("");
            Toast.makeText(this, "账号或者密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest workerRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                startActivity(MainActivity.class);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("workerRequest\n" + volleyError.toString());
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_string", account); // test "36112319850226193X"
                map.put("password", password);// test "123456"
                return map;
            }

            @Override
            protected String getParamsEncoding() {
                return "utf-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String cookie = responseHeaders.get("Set-Cookie");
                    HalcyonApplication.setCookie(cookie);
                    PreferenceUtils.putString(HalcyonApplication.getApplication(), Constants.COOKIE, cookie);
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        StringRequest leaderRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_LEADER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                startActivity(MainActivity.class);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("workerRequest\n" + volleyError.toString());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                //TODO 取消测试号
                map.put("username", account); // test "李四"  or "test"
                map.put("password", password);// test "123456"
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String cookie = responseHeaders.get("Set-Cookie");
                    HalcyonApplication.setCookie(cookie);
                    PreferenceUtils.putString(HalcyonApplication.getApplication(), Constants.COOKIE, cookie);
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected String getParamsEncoding() {
                return "utf-8";
            }
        };
        mQueue.add(leaderRequest);
        mQueue.add(workerRequest);
    }


}
