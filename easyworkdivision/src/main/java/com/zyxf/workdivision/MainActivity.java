package com.zyxf.workdivision;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.base.BaseFragment;
import com.zyxf.workdivision.fragment.AdditionalFragment;
import com.zyxf.workdivision.fragment.MainFragment;
import com.zyxf.workdivision.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private List<BaseFragment> fragmentList;
    private SlideAdapter adapter;
    private TextView titleTv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        fragmentList = new ArrayList<>();

        titleTv = (TextView) this.findViewById(R.id.tv_title);
        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.radiogroup);

        titleTv.setText(UIUtils.getString(R.string.app_name));
        fragmentList.add(new MainFragment());
        fragmentList.add(new AdditionalFragment());

        adapter = new SlideAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
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

    private class SlideAdapter extends FragmentPagerAdapter {

        private SlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
