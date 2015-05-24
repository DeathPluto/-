package com.zyxf.workdivision;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.manager.DialogManager;
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
    private Dialog progressDialog;

    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private Dialog loginProgressDialog;

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


        initShare();


    }

    private void initShare() {
        mController.setShareContent("易工分，http://www.umeng.com/social");

        mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    }

    private void checkLogin() {
        hasLogin = PreferenceUtils.getBoolean(getApplicationContext(), Constants.HAS_LOGIN, false);
        Check check = (Check) mCache.getAsObject(Constants.CHECK);

        if (check != null && check.logined == true) {
            hasLogin = true;
        } else {
            progressDialog = DialogManager.showProgressDialog(this, "检测登陆中...");
            StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_CHECK_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    Check check = gson.fromJson(s, Check.class);
                    LogUtils.i(check.toString());
                    mCache.put(Constants.CHECK, check);
                    if (check.logined == true) {
                        PreferenceUtils.putBoolean(getApplicationContext(), Constants.HAS_LOGIN, true);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError != null) {
                        LogUtils.i("未检查到登陆\n" + volleyError.toString());
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
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
                mController.openShare(this, false);
                break;
            case R.id.ll_shop:
                Uri uri = Uri.parse("http://www.jd.com/");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            case R.id.tv_login:
                login();
                break;
        }
    }

    private void login() {
        loginProgressDialog = DialogManager.showProgressDialog(this, "登陆中...");
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
                passwordEt.setText("");
                jumpAfterCheck();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("workerRequest\n" + volleyError.toString());
                }
                passwordEt.setText("");
                if (loginProgressDialog.isShowing()) {
                    loginProgressDialog.dismiss();
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
                if (loginProgressDialog.isShowing()) {
                    loginProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("workerRequest\n" + volleyError.toString());
                }
                if (loginProgressDialog.isShowing()) {
                    loginProgressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
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

    private void jumpAfterCheck() {
        StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_CHECK_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Check check = gson.fromJson(s, Check.class);
                LogUtils.i(check.toString());
                mCache.put(Constants.CHECK, check);
                if (check.logined == true) {
                    PreferenceUtils.putBoolean(getApplicationContext(), Constants.HAS_LOGIN, true);
                }
                if (loginProgressDialog.isShowing()) {
                    loginProgressDialog.dismiss();
                }
                startActivity(MainActivity.class);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i("未检查到登陆\n" + volleyError.toString());
                }
                if (loginProgressDialog.isShowing()) {
                    loginProgressDialog.dismiss();
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
