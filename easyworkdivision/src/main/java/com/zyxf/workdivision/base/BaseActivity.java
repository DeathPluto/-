package com.zyxf.workdivision.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListeners();
        initAfterSetListeners();
    }

    protected void initAfterSetListeners() {

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
