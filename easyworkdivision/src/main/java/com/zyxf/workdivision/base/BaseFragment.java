package com.zyxf.workdivision.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater, container);
        setListeners();
        initAfterSetListeners();
        return rootView;
    }

    protected void initAfterSetListeners() {

    }

    protected abstract void initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void setListeners();

    public void initData() {
    }

    @Override
    public abstract void onClick(View v);


    protected void startActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }

}
