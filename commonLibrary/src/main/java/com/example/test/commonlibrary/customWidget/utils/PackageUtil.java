package com.example.test.commonlibrary.customWidget.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lulei on 2016/4/1.
 */
public class PackageUtil {
    /**
     *获取当前应用的versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageManager mgr = context.getPackageManager();
        try {
            PackageInfo info = mgr.getPackageInfo(context.getPackageName(),0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     *获取当前应用的versionCode
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        PackageManager mgr = context.getPackageManager();
        try {
            PackageInfo info = mgr.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
