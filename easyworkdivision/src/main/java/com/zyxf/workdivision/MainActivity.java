package com.zyxf.workdivision;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.zyxf.workdivision.base.BaseActivity;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.radiogroup);
    }

    @Override
    protected void setListeners() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

        }
    }
}
