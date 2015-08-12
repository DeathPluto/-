package com.zyxf.workdivision;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.base.BaseFragment;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.fragment.AdditionalFragment;
import com.zyxf.workdivision.fragment.MainFragment;
import com.zyxf.workdivision.utils.PreferenceUtils;
import com.zyxf.workdivision.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private List<BaseFragment> fragmentList;
    private SlideAdapter adapter;
    private TextView titleTv;
    private ImageView dotIv1;
    private ImageView dotIv2;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        fragmentList = new ArrayList<>();

        dotIv1 = (ImageView) this.findViewById(R.id.iv_dot_1);
        dotIv2 = (ImageView) this.findViewById(R.id.iv_dot_2);
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
    protected void onResume() {
        super.onResume();
        int openTimes = PreferenceUtils.getInt(getApplicationContext(), Constants.OPEN_TIMES, 0);
        PreferenceUtils.putInt(
                getApplicationContext(),
                Constants.OPEN_TIMES,
                ++openTimes
        );
        if (openTimes > 50) {
            Toast.makeText(getApplicationContext(), "试用结束,请安装正式版:)", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void setListeners() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_checkingin_today:
                startActivity(TimeCheckinginActivity.class);
                break;
            case R.id.rb_project_info:
                startActivity(ProjectActivity.class);
//                startActivity(TestActivity.class);
                break;
            case R.id.rb_checkingin_week:
                startActivity(WeekCheckinginActivity.class);
                break;
            case R.id.rb_more:
                startActivity(MoreActivity.class);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //TODO 点动态移动效果
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            dotIv1.setVisibility(View.VISIBLE);
            dotIv2.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            dotIv1.setVisibility(View.INVISIBLE);
            dotIv2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
