package com.jackykeke.custombehaviordemo.utils;

import android.content.Context;

import com.jackykeke.custombehaviordemo.base.BaseModuleApp;

public class DisplayUtil {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BaseModuleApp.application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BaseModuleApp.application.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
