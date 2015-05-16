package com.zyxf.workdivision;

import android.view.View;

import com.zyxf.workdivision.base.BaseActivity;

/**
 * Created by DeathPluto on 2015/5/17.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setListeners() {
        this.findViewById(R.id.ll_introduction).setOnClickListener(this);
        this.findViewById(R.id.ll_contact_us).setOnClickListener(this);
        this.findViewById(R.id.ll_share).setOnClickListener(this);
        this.findViewById(R.id.ll_shop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_introduction:
                startActivity(IntroduceActivity.class);
                break;
            case R.id.ll_contact_us:
                startActivity(ContactUsActivity.class);
                break;
            case R.id.ll_share:
//TODO 7、	推荐好友，能链接到QQ、微信、微博，将软件信息和下载链接发送给好友。
                break;
            case R.id.ll_shop:
                //TODO 链接到京东
                break;
        }
    }


}
