package com.beyonditsm.financial.view;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 沉浸式通知栏
 * Created by Administrator on 2016/5/5.
 */
@SuppressWarnings({"InlinedApi","ResourceType"})
public class NotificationImmersed {
    private Activity activity ;
    //设置沉浸式通知栏的ID(防止重复创建)
    private final static int IMMERSED_NOTIFICATION_BAR_ID = 123;
    private final static String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height" ;

    public NotificationImmersed(Activity activity) {
        this.activity = activity;
    }
    //获取状态栏高度
    private int getStatusBarHeight(Resources res){
        int statusBarHeight = 0;
        int resourceId = res.getIdentifier(STATUS_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight ;
    }
    //添加顶部状态栏
    private View addStateBar(Activity activity,ViewGroup rootView,int statusBarHeight){
        //创建新的View,并添加到rootView顶部)
        View statusBarView ;
        if(null!=rootView.findViewById(IMMERSED_NOTIFICATION_BAR_ID)){
            statusBarView = rootView.findViewById(IMMERSED_NOTIFICATION_BAR_ID);
        }else{
            statusBarView = new View(activity);
            rootView.addView(statusBarView);
        }
        statusBarView.setId(IMMERSED_NOTIFICATION_BAR_ID) ;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setVisibility(View.VISIBLE);
        return statusBarView ;
    }
    /**
     * 设置状态栏颜色
     * @param ColorId
     */
    public void setStateBarColor(int ColorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = activity.getWindow();
            //activity的顶级布局
            ViewGroup rootView = (ViewGroup) window.getDecorView();
            //透明化状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            Resources res = activity.getResources();
            //获取状态栏目的高度
            int statusBarHeight = getStatusBarHeight(res);

            View stateBarView = addStateBar(activity,rootView,statusBarHeight) ;
            stateBarView.setBackgroundColor(ColorId) ;

        }

    }
    /**
     * 设置状态栏颜色
     * @param drawable
     */
    public void setStateBarDrawable(Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = activity.getWindow();
            //activity的顶级布局
            ViewGroup rootView = (ViewGroup) window.getDecorView();
            //透明化状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            Resources res = activity.getResources();
            //获取状态栏目的高度
            int statusBarHeight = getStatusBarHeight(res);

            View stateBarView = addStateBar(activity,rootView,statusBarHeight) ;
            stateBarView.setBackgroundDrawable(drawable) ;

        }

    }
}
