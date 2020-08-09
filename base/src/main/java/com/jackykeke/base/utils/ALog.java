package com.jackykeke.base.utils;

import android.util.Log;

public class ALog {

    public static final String TAG = ALog.class.getName();
//    private static boolean sLogSwitch = Boolean.parseBoolean(System.getProperty(TAG));


    public static void d(String tag, String msg) {
        if (Boolean.parseBoolean(System.getProperty(TAG))) {
            Log.d(TAG + "@" + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Boolean.parseBoolean(System.getProperty(TAG))) {
            Log.i(TAG + "@" + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Boolean.parseBoolean(System.getProperty(TAG))) {
            Log.e(TAG + "@" + tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (Boolean.parseBoolean(System.getProperty(TAG))) {
            Log.v(TAG + "@" + tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        boolean logSwitch = Boolean.parseBoolean(System.getProperty(TAG));
        if (logSwitch) {
            Log.w(TAG + "@" + tag, msg);
        }
    }
}
