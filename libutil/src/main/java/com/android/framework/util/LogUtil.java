package com.android.framework.util;

import android.util.Log;

/**
 * Created by meikai on 2017/11/07.
 */
public class LogUtil {

    private static final String TAG = "LogUtil";

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }



    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

}
