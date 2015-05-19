package com.zyxf.workdivision;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.http.VolleyTool;
import com.zyxf.workdivision.test.TestActivity;
import com.zyxf.workdivision.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/17.
 */
public class LoginActivity extends BaseActivity {
    private EditText accountEt;
    private EditText passwordEt;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);

        accountEt = (EditText) this.findViewById(R.id.et_account);
        passwordEt = (EditText) this.findViewById(R.id.et_password);
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
        String account = accountEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            passwordEt.setText("");
            Toast.makeText(this, "账号或者密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        VolleyTool volleyTool = VolleyTool.getInstance(getApplicationContext());
        RequestQueue queue = volleyTool.getQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                startActivity(MainActivity.class);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i("cuowu");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_string", "36112319850226193X");
                map.put("password", "123456");
                return map;
            }

            @Override
            protected String getParamsEncoding() {
                return "utf-8";
            }
        };
        queue.add(stringRequest);
    }


}
