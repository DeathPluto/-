package com.zyxf.workdivision;

import android.view.View;
import android.widget.TextView;

import com.zyxf.workdivision.base.BaseActivity;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class LinkActivity extends BaseActivity {
    private TextView titleTv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_link);
        titleTv = (TextView) this.findViewById(R.id.tv_title);

        titleTv.setText("链接");
    }

    @Override
    protected void setListeners() {
        this.findViewById(R.id.btn_police).setOnClickListener(this);
        this.findViewById(R.id.btn_hm).setOnClickListener(this);
        this.findViewById(R.id.btn_construction).setOnClickListener(this);
        this.findViewById(R.id.btn_metro).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_police:

                break;
            case R.id.btn_hm:

                break;
            case R.id.btn_construction:

                break;
            case R.id.btn_metro:

                break;
        }
    }
}
