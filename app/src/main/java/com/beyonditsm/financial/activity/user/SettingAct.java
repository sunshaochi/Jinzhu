package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.servicer.ChangePwdAct;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GeneralUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.ToggleButton;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.ipc.RongExceptionHandler;

/**
 * 设置
 * Created by wangbin on 15/11/12.
 */
public class SettingAct extends BaseActivity {
    @ViewInject(R.id.tb_msg)
    private ToggleButton tb_msg;//消息提醒
    @ViewInject(R.id.tb_sleep)
    private ToggleButton tb_sleep;//消息免打扰
    @ViewInject(R.id.tvVersion)
    private TextView tvVersion;
    @ViewInject(R.id.rlcheck)
    private RelativeLayout rlCheck;
    @ViewInject(R.id.tvCacheSize)
    private TextView tvCacheSize;
    @ViewInject(R.id.pb_clearCache)
    private ProgressBar pbClearCache;

    private Thread tbSleepOption;
    private GeneralUtils gUtils;
        //    private UserEntity userInfo;
    public static final String ISLOADING = "com.settingAct.isloading";
    private static final String APP_CACAHE_DIRNAME = "/gamecache";
    private boolean isStart = false;
    private boolean isUploading = false;
    private boolean isFirstClick = false;
    private String totalCacheSize;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                tvCacheSize.setText(totalCacheSize);
                MyToastUtils.showShortToast(getApplicationContext(),"缓存清理完毕");
            }
        }
    };
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        try {
        setLeftTv("返回");
        setTopTitle("设置");
        gUtils = new GeneralUtils();
            totalCacheSize = FinancialUtil.getTotalCacheSize(getApplicationContext());
            tvCacheSize.setText(totalCacheSize);

        tvVersion.setText(FinancialUtil.getAppVersion(this));
//        userInfo=getIntent().getParcelableExtra("user_info");
        tb_msg.setIsSwitch(SpUtils.getMsg(SettingAct.this));
        tb_sleep.setIsSwitch(SpUtils.getSleep(SettingAct.this));
        tb_msg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                //是否接受新消息
                if (on) {
                    SpUtils.setMsg(getApplicationContext(), true);

                    if (JPushInterface.isPushStopped(getApplicationContext())){
                        JPushInterface.resumePush(getApplicationContext());
                    }

                } else {
                    SpUtils.setMsg(getApplicationContext(), false);
                    if (!JPushInterface.isPushStopped(getApplicationContext())){
                        JPushInterface.stopPush(getApplicationContext());
                    }

                }
            }
        });
        tb_sleep.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                //消息免打扰
                if (on) {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            SpUtils.setSleep(SettingAct.this, true);
                            JPushInterface.setSilenceTime(getApplicationContext(), 0, 0, 23, 59);
                            if (null!=RongIM.getInstance()){
                                RongIM.getInstance().getRongIMClient().setNotificationQuietHours("00:00:00", 1399, new RongIMClient.OperationCallback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                    }
                                });
                            }
                        }
                    }.start();

                } else {
                   new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            SpUtils.setSleep(SettingAct.this, false);
                            JPushInterface.setSilenceTime(getApplicationContext(), 0, 0, 0, 0);
                            if (null!=RongIM.getInstance()){
                                RongIM.getInstance().getRongIMClient().removeNotificationQuietHours(new RongIMClient.OperationCallback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                    }
                                });
                            }
                        }
                    }.start();


                }
            }
        });
            } catch (Exception e) {
                e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!tb_msg.IsSwitch()) {
            SpUtils.setMsg(getApplicationContext(), false);
            JPushInterface.stopPush(getApplicationContext());
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.rlUpdate, R.id.rlUpdatePwd, R.id.rlcheck, R.id.rlAbout,R.id.rlClearChche})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
//            //修改资料
//            case R.id.rlUpdate:
//                intent = new Intent(this, UpdateAct.class);
//                intent.putExtra(MineFragment.USER_KEY,userInfo);
//                startActivity(intent);
//                break;
            //修改密码
            case R.id.rlUpdatePwd:
//                intent = new Intent(this, FindPwdAct.class);
                intent = new Intent(this, ChangePwdAct.class);
                startActivity(intent);
                break;
            //检测新版本
            case R.id.rlcheck:

                if (isStart||isUploading){
                    rlCheck.setClickable(false);
                    MyToastUtils.showShortToast(getApplicationContext(),"正在努力升级新版本，请稍等...");
                }else{
                    gUtils.toVersion(SettingAct.this, FinancialUtil.getAppVer(SettingAct.this), 0);
                }
                break;
            //关于
            case R.id.rlAbout:
                intent = new Intent(this, AboutOurs.class);
                startActivity(intent);
                break;
            //清除缓存
            case R.id.rlClearChche:
//                clearWebViewCache();
//                if (isFirstClick) {
//                    MyToastUtils.showShortToast(SettingAct.this, "已清除缓存!");
//                    isFirstClick = false;
//                }else{

//                    isFirstClick = true;
//                }
                if (!totalCacheSize.equals("0K")){
                    MySelfSheetDialog dialog = new MySelfSheetDialog(this);
                    dialog.builder().addSheetItem("确定清除缓存？", MySelfSheetDialog.SheetItemColor.Red, new MySelfSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
//                        pbClearCache.setVisibility(View.VISIBLE);
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    ClearCache();
                                    try {
                                        totalCacheSize = FinancialUtil.getTotalCacheSize(getApplicationContext());
                                        handler.sendEmptyMessage(1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }.start();

                        }
                    }).show();
                }else{
                    MyToastUtils.showShortToast(SettingAct.this, "已清除缓存!");
                    return;
                }
                break;
        }


    }

    private void ClearCache() {
        FinancialUtil.clearAllCache(getApplicationContext());

    }


    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new MyBroadCastReceiver();
        }
       registerReceiver(receiver, new IntentFilter(ISLOADING));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
           unregisterReceiver(receiver);
        }
    }
//    private UpdateReceiver receiver;
//
//    public static final String UPDATE_USER="com.set.update.user";
//
//    public class UpdateReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            userInfo=intent.getParcelableExtra(MineFragment.USER_KEY);
//        }
//    }
    private MyBroadCastReceiver receiver;
    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isStart = intent.getBooleanExtra("isStart", true);
            isUploading = intent.getBooleanExtra("isUploading", true);
        }
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
//        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());
        MyLogUtils.info("appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
//        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());
        MyLogUtils.info("webviewCacheDir path="+webviewCacheDir.getAbsolutePath());
        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

//        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        MyLogUtils.info("delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
//            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
            MyLogUtils.info("delete file no exists " + file.getAbsolutePath());
        }
    }
}
