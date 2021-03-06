package com.zyxf.eazyworkdivision;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zyxf.eazyworkdivision.base.BaseActivity;
import com.zyxf.eazyworkdivision.base.BaseFragment;
import com.zyxf.eazyworkdivision.factory.FragmentFactory;
import com.zyxf.eazyworkdivision.utils.UIUtils;
import com.zyxf.eazyworkdivision.view.LazyViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private LazyViewPager mViewPager;
    private PageSwitchAdapter adapter;
    private List<BaseFragment> fragmentList;
    private RadioGroup mRadioGroup;
    private TextView titleTv;
    private RadioButton homeRb;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        mViewPager = (LazyViewPager) this.findViewById(R.id.viewpager);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.radiogroup);
        titleTv = (TextView) this.findViewById(R.id.tv_title);
        homeRb = (RadioButton) this.findViewById(R.id.rb_home);

        fragmentList = new ArrayList<>();
        fragmentList.add(FragmentFactory.createFragment(FragmentFactory.FRAGMENT_HOME));
        fragmentList.add(FragmentFactory.createFragment(FragmentFactory.FRAGMENT_SCAN));
        fragmentList.add(FragmentFactory.createFragment(FragmentFactory.FRAGMENT_TOTAL));
        fragmentList.add(FragmentFactory.createFragment(FragmentFactory.FRAGMENT_SETTING));

        adapter = new PageSwitchAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void setListeners() {

        mRadioGroup.setOnCheckedChangeListener(this);

        afterSetListeners();
    }

    protected void afterSetListeners() {
        homeRb.setChecked(true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int position = 0;
        String title = UIUtils.getString(R.string.page_home);
        switch (checkedId){
            case R.id.rb_home:
                position = FragmentFactory.FRAGMENT_HOME % 10;
                title = UIUtils.getString(R.string.page_home);
                break;
            case R.id.rb_scan:
                position = FragmentFactory.FRAGMENT_SCAN % 10;
                title = UIUtils.getString(R.string.page_scan);
                break;
            case R.id.rb_total:
                position = FragmentFactory.FRAGMENT_TOTAL % 10;
                title = UIUtils.getString(R.string.page_total);
                break;
            case R.id.rb_setting:
                position = FragmentFactory.FRAGMENT_SETTING % 10;
                title = UIUtils.getString(R.string.page_setting);
                break;
        }
        mViewPager.setCurrentItem(position);
        titleTv.setText(title);
    }

    private class PageSwitchAdapter extends FragmentPagerAdapter{
        private List<BaseFragment> fragmentList;

        private PageSwitchAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public BaseFragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
