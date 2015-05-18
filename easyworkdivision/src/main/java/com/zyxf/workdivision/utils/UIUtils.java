package com.zyxf.workdivision.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.zyxf.workdivision.application.HalcyonApplication;


/**
 * UI界面相关工具类
 */
public class UIUtils {
    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dip2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5);
    }

    /**
     * px转dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5);
    }

    /**
     * 用xml布局文件填充布局
     *
     * @param resId
     * @return
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取主线程的上下文
     *
     * @return
     */
    public static Context getContext() {
        return HalcyonApplication.getApplication();
    }

    /**
     * 获取主线程handler
     *
     * @return
     */
    public static Handler getHandler() {
        return HalcyonApplication.getMainThreadHandler();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread())
            runnable.run();
        else
            post(runnable);
    }

    /**
     * 从主线程执行runnable
     *
     * @param runnable
     * @return
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static long getMainThreadId() {
        return HalcyonApplication.getMainThreadId();
    }

    /**
     * 判断当前线程是否为主线程
     *
     * @return
     */
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {

        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获得图片资源
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }
}
