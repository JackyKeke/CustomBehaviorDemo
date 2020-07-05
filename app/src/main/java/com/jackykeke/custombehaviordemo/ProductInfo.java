package com.jackykeke.custombehaviordemo;

import com.jackykeke.base.base.BaseModuleApp;
import com.jackykeke.base.base.BaseProductInfo;

public class ProductInfo implements BaseProductInfo {
    /**
     * 相关协议
     **/
    private static final String USER_POLICY = "https://docs.qq.com/doc";    //用户协议
    private static final String PRIVACY_POLICY = "https://docs.qq.com/doc"; //隐私协议
    private static final String APP_SHARE_URL = "http://clean.startech.ltd"; //应用分享链接

    /**
     * APK   CHANNEL
     **/
    private static final String APK_CHANNEL = "xxxx00";

    /**
     * 广告-产品信息
     **/
    private static final String AD_INFO_CID = "xxxx";          //产品ID
    private static final String AD_INFO_CHANNEL = "000";    //产品渠道

    private static ProductInfo productInfo;

    private ProductInfo() {
    }

    public static ProductInfo getInstance() {
        if (null == productInfo) {
            productInfo = new ProductInfo();
        }
        return productInfo;
    }

    /**
     * GOOGLE PLAY商店应用详情地址
     **/
    @Override
    public String getStoreUrl() {
        return "http://play.google.com/store/apps/details?id=" + BaseModuleApp.application.getPackageName();
    }

    @Override
    public String getUserPolicy() {
        return USER_POLICY;
    }

    @Override
    public String getPrivacyPolicy() {
        return PRIVACY_POLICY;
    }

    @Override
    public String getApkChannel() {
        return APK_CHANNEL;
    }

    @Override
    public String getAdInfoCid() {
        return AD_INFO_CID;
    }

    @Override
    public String getAdInfoChannel() {
        return AD_INFO_CHANNEL;
    }


    @Override
    public String getAppShareUrl() {
        return APP_SHARE_URL;
    }

}
