package com.zyxf.eazyworkdivision.application;


import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zyxf.eazyworkdivision.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class HalcyonApplication extends Application {

    /**
     * 主线程的上下�?
     */
    private static HalcyonApplication mContext = null;
    /**
     * 主线程的handler
     */
    private static Handler mMainThreadHandler = null;
    /**
     * 主线程的looper
     */
    private static Looper mMainThreadLooper = null;
    /**
     * 主线�?
     */
    private static Thread mMainThread = null;
    /**
     * 主线程的id
     */
    private static int mMainThreadId;
    private static HandlerCallback mHandlerCallback;

    public interface HandlerCallback {
        public void handleMessage(Message msg);
    }

    @Override
    public void onCreate() {
        this.mContext = this;
        this.mMainThreadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mHandlerCallback.handleMessage(msg);
            }
        };
        this.mMainThreadLooper = getMainLooper();
        this.mMainThread = Thread.currentThread();
        this.mMainThreadId = android.os.Process.myTid();
        /* 全局捕获异常 */
//		Thread.currentThread().setUncaughtExceptionHandler(new HalcyonExceptionHandler());
        super.onCreate();
    }


    public static HalcyonApplication getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }


    protected class HalcyonExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            try {
                LogUtils.e("发生了异�?被捕获了");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                File file = new File(Environment.getExternalStorageDirectory(), "error.log");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sw.toString().getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* 结束自我进程,让android系统重新启动自身 */
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
