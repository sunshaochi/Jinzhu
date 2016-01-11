package com.beyonditsm.financial.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 工具类
 * Created by wangbin on 15/11/14.
 */
public class FinancialUtil {


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 时间戳转化成时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));

    }

    /**
     * 获得手机唯一IMEI
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

    /**
     * 获取手机屏幕宽
     *
     * @param activity
     */
    public static int getScreenWidth(Context activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) activity).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // float density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        // int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return metrics.widthPixels;
    }

    /**
     * @param activity
     */
    public static int getPhoneDensityDpi(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    /**
     * 获取手机屏幕高
     *
     * @param activity
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;

    }

    /**
     * 获取手机状态栏的高度
     *
     * @param
     */
    public static int getStatusHeight(Context context) {
        // Rect frame = new Rect();
        // activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // return frame.top;

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param activity
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        // final Display display = ((WindowManager)
        // context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }

    /**
     * 获取版本
     *
     * @param context
     * @return
     */
    public static int getAppVer(Context context) {
        int version = 1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;

    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取屏幕密度 (单位 px)
     */
    public static Point getScreenMeture(Context context) {
        Display mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        return new Point(mDisplay.getWidth(), mDisplay.getHeight());
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 像素值
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 生成0-9的随机数
     *
     * @param count 生成几位数（6位）
     * @return random 随机数
     */

    public static String getRandomNumber(int count) {
        String random = "";
        for (int i = 0; i < count; i++) {
            String str = String.valueOf((int) (Math.random() * 10 - 1));
            random = random + str;
        }
        return random;
    }

    /***
     * 获取MAC地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            return wifiInfo.getMacAddress();
        } else {
            return "";
        }
    }

    /**
     * 获取运行时间
     *
     * @return 运行时间(单位/s)
     */
    public static long getRunTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        return ut;
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * sdcard是否可读写
     */
    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * sim卡是否可读
     *
     * @param context
     * @return
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public void hideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = ((Activity) context).getWindow().getDecorView();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 根据某个固定的View隐藏软键盘
     *
     * @param context
     * @param v
     */
    public void hideSoftKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 作用：获取当前设备的内核数目
     *
     * @return
     */
    public static int getAvailableProcessorsNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 从下载文件的url中截取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        if (fileName == null || "".equals(fileName.trim())) {
            fileName = UUID.randomUUID().toString() + ".tmp"; // 默认名
        }
        return fileName;
    }

    /**
     * 加载本地小图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            // return BitmapFactory.decodeStream(fis); //把流转化为Bitmap图片
            return BitmapFactory.decodeStream(fis, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DisplayMetrics getWindowDisplay(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        // float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        // int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        // float xdpi = dm.xdpi;
        // float ydpi = dm.ydpi;

        return dm;
    }

    /**
     * 获取手机串号，需要权限：<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机系统的版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取应用的版本号,默认值为1.0
     *
     * @return 当前应用的版本号
     */
    public static String getAppVersion(Context context) {
        String version = "1.0";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;

    }

    /**
     * 调用系统的分享控件
     *
     * @param activity
     * @param content
     */
    public static void shareContent(Context activity, String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        activity.startActivity(intent);
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packName
     * @return
     */
    public static boolean appInstalled(Context context, String packName) {
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        return packageInfo == null ? false : true;
    }

    /**
     * 判断某个服务是不是活着
     *
     * @param mContext
     * @param serviceName
     * @return
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {

        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * @param context 上下文
     * @param actName 要校验Activity的名称
     * @return true Activity还运行，false
     */
    public static boolean isRunningActivity(Context context, String actName) {
        // ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            for (RunningTaskInfo runningTaskInfo : runningTasks) {
                String name = runningTaskInfo.topActivity.getClassName();
                if (actName.equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 当月第一天
     *
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 当月最后一天
     *
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }

    private static int QR_WIDTH = 400;
    private static int QR_HEIGHT = 400;

    // 要转换的地址或字符串,可以是中文
    public static Bitmap createQRImage(String url) {
        Bitmap bitmap = null;
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //千分位方法
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    /**
     * 打开输入法
     * @param context
     * @param editText
     */
    public static  void openIM(Context context, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

    /**
     * 关闭输入法
     * @param context
     * @param editText
     */
    public static void closeIM(Context context, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }




}
