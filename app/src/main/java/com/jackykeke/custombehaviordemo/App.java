package com.jackykeke.custombehaviordemo;

import android.app.Application;
import android.content.Context;


import com.jackykeke.base.base.BaseModuleApp;

import java.util.LinkedList;
import java.util.List;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppLifeManger.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppLifeManger.register(new BaseModuleApp(this, ProductInfo.getInstance()));
        AppLifeManger.onAttachBaseContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppLifeManger.onTerminate();
    }

    /**
     * application 声明周期分发
     */
    static class AppLifeManger {
        private static List<BaseModuleApp> mList = new LinkedList<>();


        static void register(BaseModuleApp appBase) {
            if (!mList.contains(appBase)) {
                mList.add(appBase);
            }
        }

        static void onCreate() {
            for (BaseModuleApp appBase : mList) {
                appBase.onCreate();
            }
        }

        static void onAttachBaseContext() {
            for (BaseModuleApp appBase : mList) {
                appBase.attachBaseContext();
            }
        }

        static void onTerminate() {
            for (BaseModuleApp appBase : mList) {
                appBase.onTerminate();
            }
            mList.clear();
        }
    }
}
