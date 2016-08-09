package com.beyonditsm.financial;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.beyonditsm.financial.activity.SplashAct;
import com.lidroid.xutils.util.LogUtils;
import com.tandong.sa.zUImageLoader.cache.disc.naming.Md5FileNameGenerator;
import com.tandong.sa.zUImageLoader.core.ImageLoader;
import com.tandong.sa.zUImageLoader.core.ImageLoaderConfiguration;
import com.tandong.sa.zUImageLoader.core.assist.QueueProcessingType;
import com.testin.agent.TestinAgent;
import com.testin.agent.TestinAgentConfig;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.ipc.RongExceptionHandler;

/**
 * Created by wangbin on 15/11/11
 */
public class MyApplication extends Application {
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
    public void onCreate() {
        super.onCreate();
        instance = this;
        isDownload = false;
        MultiDex.install(this);
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_PHONE_STATE);
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(SplashAct, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_CALL_PHONE);
//                return;
//            } else {
//                initPragram();
//            }
//        }
        initPragram();

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
