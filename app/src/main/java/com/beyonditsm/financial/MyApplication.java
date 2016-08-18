package com.beyonditsm.financial;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.beyonditsm.financial.activity.LoadResActivity;
import com.beyonditsm.financial.activity.SplashAct;
import com.beyonditsm.financial.util.PackageUtil;
import com.lidroid.xutils.util.LogUtils;
import com.tandong.sa.sql.util.Log;
import com.tandong.sa.zUImageLoader.cache.disc.naming.Md5FileNameGenerator;
import com.tandong.sa.zUImageLoader.core.ImageLoader;
import com.tandong.sa.zUImageLoader.core.ImageLoaderConfiguration;
import com.tandong.sa.zUImageLoader.core.assist.QueueProcessingType;
import com.testin.agent.TestinAgent;
import com.testin.agent.TestinAgentConfig;

import java.util.Map;
import java.util.jar.*;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.ipc.RongExceptionHandler;

/**
 * Created by wangbin on 15/11/11
 */
public class MyApplication extends Application {
    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";
    private static MyApplication instance;

    public static int screenWith;
    public static int screenHeight;

    //    public static String code_cookie;
    private boolean isDownload;

    public static String downloadApkUrl;

    private RequestQueue mRequestQueue;
//    // 百度定位参数
//    public LocationClient mLocationClient = null;
//    public BDLocationListener myListener = new MyLocationListener();
    // 百度定位参数
    public static final String TAG = "VolleyPatterns";

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d( "loadDex", "App attachBaseContext ");
        if (!quickStart() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//>=5.0的系统默认对dex进行oat优化
            if (needWait(base)){
                waitForDexopt(base);
            }
            MultiDex.install (this );
        } else {
            return;
        }
    }
    //neead wait for dexopt ?
    private boolean needWait(Context context){
        String flag = get2thDexSHA1(context);
        Log.d( "loadDex", "dex2-sha1 "+flag);
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtil.getPackageInfo(context). versionName, MODE_MULTI_PROCESS);
        String saveValue = sp.getString(KEY_DEX2_SHA1, "");
        return !TextUtils.equals(flag,saveValue);
    }
    public boolean quickStart() {
        if (getCurProcessName(this).contains(":mini")) {
            Log.d( "loadDex", ":mini start!");
            return true;
        }
        return false ;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isDownload = false;
        initPragram();
        if (quickStart()) {
            return;
        }
//        MultiDex.install(this);
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_PHONE_STATE);
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(SplashAct, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_CALL_PHONE);
//                return;
//            } else {
//                initPragram();
//            }
//        }

    }
    public void waitForDexopt(Context base) {
        Intent intent = new Intent();
        ComponentName componentName = new
                ComponentName( "com.beyounditsm", LoadResActivity.class.getName());
        intent.setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);
        long startWait = System.currentTimeMillis ();
        long waitTime = 10 * 1000 ;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1 ) {
            waitTime = 20 * 1000 ;//实测发现某些场景下有些2.3版本有可能10s都不能完成optdex
        }
        while (needWait(base)) {
            try {
                long nowWait = System.currentTimeMillis() - startWait;
                Log.d("loadDex" , "wait ms :" + nowWait);
                if (nowWait >= waitTime) {
                    return;
                }
                Thread.sleep(200 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Get classes.dex file signature
     * @param context
     * @return
     */
    private String get2thDexSHA1(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            java.util.jar.Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes2.dex");
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
    // optDex finish
    public void installFinish(Context context){
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtil.getPackageInfo(context).versionName, MODE_MULTI_PROCESS);
        sp.edit().putString(KEY_DEX2_SHA1,get2thDexSHA1(context)).commit();
    }
    private void initPragram() {
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            LogUtils.i("融云初始化成功");
            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
                RongCloudEvent.init(this);
                Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));
            }
        }
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        initImageLoader(this);
        initTestIn();
    }


//    public void getLocation() {
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        GPSAddressUtils.initLocation(mLocationClient);
//        mLocationClient.registerLocatixonListener(myListener);    //注册监听函数
//        mLocationClient.start();
//    }

    private void initTestIn() {

        TestinAgentConfig config = new TestinAgentConfig.Builder(getApplicationContext())
                .withAppKey("a50d2b5acb4bdaff30006cc017a6a991")             // Appkey of your appliation, required
                .withAppChannel("")         // Channel of your application
                .withDebugModel(true)        // Output the crash log in local if you open debug mode
                .withErrorActivity(true)     // Output the activity info in crash or error log
                .withCollectNDKCrash(true)   // Collect NDK crash or not if you use our NDK
                .withOpenCrash(true)         // Monitor crash if true
                .withReportOnlyWifi(true)    // Report data only on wifi mode
                .withReportOnBack(true)      // allow to report data when application in background
                .build();
        TestinAgent.init(config);

    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context 上下文
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 初始化ImageLoader
     *
     * @param context 上下文
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(5)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    //Volley请求数据

    /**
     * @return 获取RequestQueue
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            // 1
            // 2
            synchronized (MyApplication.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley
                            .newRequestQueue(getApplicationContext());
                }
            }
        }
        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
