package com.zyxf.eazyworkdivision.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zyxf.eazyworkdivision.R;
import com.zyxf.eazyworkdivision.adapter.ImgAdapter;
import com.zyxf.eazyworkdivision.base.BaseFragment;
import com.zyxf.eazyworkdivision.view.CarouselView;

import java.util.ArrayList;

/**
 * Title :       com.zyxf.eazyworkdivision.fragment                                     <br/>
 * Copyright :   金屏宝 Co. , Ltd.Copyright 2015,All rights reserved  <br/>
 *
 * @author :     Administrator                                            <br/>
 * @version :    1.0                                                <br/>
 * @since :      2015/5/11 10:10                                    <br/>
 * Description:  todo
 */
public class HomeFragment extends BaseFragment {
    private CarouselView mCarousel;
    private ArrayList<Integer> imgList;
    private ArrayList<ImageView> portImg;
    private LinearLayout focuseLl;
    /**
     * 存储上一个选择项的Index
     */
    private int preSelImgIndex = 0;

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mCarousel = (CarouselView) rootView.findViewById(R.id.carousel);
        focuseLl = (LinearLayout) rootView.findViewById(R.id.ll_focus_indicator_container);

        imgList = new ArrayList<Integer>();
        imgList.add(R.drawable.img1);
        imgList.add(R.drawable.img2);
        imgList.add(R.drawable.img3);
        InitFocusIndicatorContainer();

        mCarousel.setAdapter(new ImgAdapter(getActivity(), imgList));
        mCarousel.setFocusable(true);
        mCarousel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = position % imgList.size();
                // 修改上一次选中项的背景
                portImg.get(preSelImgIndex).setImageResource(R.drawable.ic_focus);
                // 修改当前选中项的背景
                portImg.get(position).setImageResource(R.drawable.ic_focus_select);
                preSelImgIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void InitFocusIndicatorContainer() {
        portImg = new ArrayList<ImageView>();
        for (int i = 0; i < imgList.size(); i++) {
            ImageView localImageView = new ImageView(getActivity());
            localImageView.setId(i);
            ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
            localImageView.setScaleType(localScaleType);
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
                    24, 24);
            localImageView.setLayoutParams(localLayoutParams);
            localImageView.setPadding(5, 5, 5, 5);
            localImageView.setImageResource(R.drawable.ic_focus);
            portImg.add(localImageView);
            focuseLl.addView(localImageView);
        }
    }

    @Override
    protected void setListeners() {
        rootView.findViewById(R.id.btn_contribution).setOnClickListener(this);
        rootView.findViewById(R.id.btn_metro).setOnClickListener(this);
        rootView.findViewById(R.id.btn_police).setOnClickListener(this);
        rootView.findViewById(R.id.btn_baidu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contribution:

                break;
            case R.id.btn_metro:

                break;
            case R.id.btn_police:

                break;
            case R.id.btn_baidu:

                break;
        }
    }
}
