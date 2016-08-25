package com.beyonditsm.financial.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lidroid.xutils.util.LogUtils;

/**
 * Created by xuleyuan on 2016/8/18.
 */
public class PackageUtil {
    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }
}
