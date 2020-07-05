package com.jackykeke.custombehaviordemo.base;

import android.app.Application;
import android.util.Log;


public class BaseModuleApp {
    private static final String TAG = BaseModuleApp.class.getSimpleName();
    public static Application application;
    public static BaseProductInfo productInfo;

    public BaseModuleApp() {

    }

    public BaseModuleApp(Application app, BaseProductInfo baseProductInfo) {
        application = app;
        productInfo = baseProductInfo;
        Log.d(TAG, "init");
    }


    public void attachBaseContext() {
        Log.d(TAG, "attachBaseContext");

    }


    public void onCreate() {
        Log.d(TAG, "onCreate");

    }


    public void onTerminate() {
        Log.d(TAG, "onTerminate");
    }
}
