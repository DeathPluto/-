package com.zyxf.workdivision;

import android.view.View;
import android.widget.TextView;

import com.zyxf.workdivision.base.BaseActivity;

/**
 * Created by DeathPluto on 2015/5/18.
 */
public class MoreActivity extends BaseActivity {
    private TextView titleTv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_more);

        titleTv = (TextView) this.findViewById(R.id.tv_title);


        titleTv.setText("更多");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
