package com.zyxf.workdivision.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.volley.RequestQueue;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.http.VolleyTool;
import com.zyxf.workdivision.utils.ACache;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
    protected VolleyTool mVolley;
    protected RequestQueue mQueue;
    protected ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVolley = VolleyTool.getInstance(getApplicationContext());
        mQueue = mVolley.getQueue();
        mCache = ACache.get(HalcyonApplication.getApplication());
        initView();
        setListeners();
        initAfterSetListeners();


    }

    protected void initAfterSetListeners() {

    }

    public ACache getCache() {
        return mCache;
    }

    public VolleyTool getVolley() {
        return mVolley;
    }

    public RequestQueue getQueue() {
        return mQueue;
    }

    protected abstract void initView();

    protected abstract void setListeners();

    public void initData() {
    }

    protected void startActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public abstract void onClick(View v);


}
