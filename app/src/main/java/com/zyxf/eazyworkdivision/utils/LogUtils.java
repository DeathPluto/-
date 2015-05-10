package com.zyxf.eazyworkdivision.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/2/3.
 */
public class LogUtils {

    private static final boolean isDebugging = true;
    private static final String TAG = "Halcyon";
    public static void i(String msg) {
        if (isDebugging) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebugging) {
            Log.e(TAG, msg);
        }
    }
}
