package com.beyonditsm.financial.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

public class DensityUtil {
	public static int dipToPx(Context context, float dipValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
} 

	public static int px2dip(Context context, float pxValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(pxValue / scale + 0.5f); 
} 
	
	public static int GetResourceId(Context context,String name,String type){
        Resources themeResources=null;
        String packageName=context.getPackageName();
        PackageManager pm=context.getPackageManager();
        try {
            themeResources=pm.getResourcesForApplication(packageName);
            return themeResources.getIdentifier(name, type, packageName);
        } catch (NameNotFoundException e) {

            e.printStackTrace();
        }
        return 0;
 }
}
