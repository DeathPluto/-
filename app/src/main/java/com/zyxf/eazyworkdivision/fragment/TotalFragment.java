package com.zyxf.eazyworkdivision.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyxf.eazyworkdivision.R;
import com.zyxf.eazyworkdivision.base.BaseFragment;
import com.zyxf.eazyworkdivision.config.Constants;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Title :       com.zyxf.eazyworkdivision.fragment                                     <br/>
 * Copyright :   金屏宝 Co. , Ltd.Copyright 2015,All rights reserved  <br/>
 *
 * @author :     Administrator                                            <br/>
 * @version :    1.0                                                <br/>
 * @since :      2015/5/11 10:11                                    <br/>
 * Description:  todo
 */
public class TotalFragment extends BaseFragment {
    protected Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_total,container,false);
    }

    @Override
    protected void setListeners() {
        rootView.findViewById(R.id.tv_start_date).setOnClickListener(this);
        rootView.findViewById(R.id.tv_end_date).setOnClickListener(this);
        rootView.findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_date:

                break;
            case R.id.tv_end_date:

                break;
            case R.id.btn_search:

                break;
        }
    }
}
