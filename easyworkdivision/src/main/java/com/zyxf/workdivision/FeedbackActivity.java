package com.zyxf.workdivision;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.zyxf.workdivision.base.BaseActivity;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class FeedbackActivity extends BaseActivity {
    private TextView titleTv;
    private EditText themeEt;
    private EditText contentEt;
    private ButtonFlat clearBtn;
    private ButtonFlat submitBtn;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_feedback);

        titleTv = (TextView) this.findViewById(R.id.tv_title);
        themeEt = (EditText) this.findViewById(R.id.et_theme);
        contentEt = (EditText) this.findViewById(R.id.et_content);
        clearBtn = (ButtonFlat) this.findViewById(R.id.btn_clear);
        submitBtn = (ButtonFlat) this.findViewById(R.id.btn_submit);

        titleTv.setText("反馈");
    }

    @Override
    protected void setListeners() {
        clearBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clear();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        //TODO 调用反馈信息接口
//        Toast.makeText(this, "建设中...", Toast.LENGTH_SHORT).show();
        clear();
    }

    private void clear() {
        themeEt.setText("");
        contentEt.setText("");
        themeEt.requestFocus();
    }
}
