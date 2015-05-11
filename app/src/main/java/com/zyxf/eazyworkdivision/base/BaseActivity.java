package com.zyxf.eazyworkdivision.base;

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
    }

    protected abstract void initView();

    protected abstract void setListeners();

    public void initData() {
    }

    @Override
    public abstract void onClick(View v);


}
