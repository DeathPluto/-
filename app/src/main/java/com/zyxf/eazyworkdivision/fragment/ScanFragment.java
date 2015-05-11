package com.zyxf.eazyworkdivision.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zyxf.eazyworkdivision.R;
import com.zyxf.eazyworkdivision.base.BaseFragment;

/**
 * Title :       com.zyxf.eazyworkdivision.fragment                                     <br/>
 * Copyright :   金屏宝 Co. , Ltd.Copyright 2015,All rights reserved  <br/>
 *
 * @author :     Administrator                                            <br/>
 * @version :    1.0                                                <br/>
 * @since :      2015/5/11 10:11                                    <br/>
 * Description:  todo
 */
public class ScanFragment extends BaseFragment {
    private PullToRefreshExpandableListView mListView;
    private BarChart mBarChart;

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_scan,container,false);

        mListView = (PullToRefreshExpandableListView) rootView.findViewById(R.id.expandablelistView);
        mBarChart = (BarChart) rootView.findViewById(R.id.barchart);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
