package com.zyxf.workdivision.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyxf.workdivision.R;
import com.zyxf.workdivision.application.HalcyonApplication;

/**
 * Created by DeathPluto on 2015/5/24.
 */
public class DialogManager {

    public interface ChangePwdListener {
        public void onChange(DialogInterface dialog, String newPassword);
    }

    public static Dialog showProgressDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.animation_loading);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;
    }

    public static Dialog showChangePwdDialog(Context context, final ChangePwdListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_change_pwd, null);// 得到加载view
        final EditText newPwdEt = (EditText) v.findViewById(R.id.et_new);
        final EditText confirmPwdEt = (EditText) v.findViewById(R.id.et_confirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(v);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = newPwdEt.getText().toString().trim();
                String confirmPassword = confirmPwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword) || !TextUtils.equals(newPassword, confirmPassword)) {
                    Toast.makeText(HalcyonApplication.getApplication(), "密码不能为空,且应保持一致!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onChange(dialog, newPassword);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        Dialog changePwdDialog = builder.show();
        return changePwdDialog;
    }
}
