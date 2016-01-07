package com.beyonditsm.financial.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.ViewUtils;
import com.tandong.sa.activity.SmartFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 基础activity
 * Created by wangbin on 15/11/11.
 */
public abstract class BaseActivity extends SmartFragmentActivity{

    private TextView tv_title;
    private TextView tvRight;
    private  TextView tvLeft;
    private RelativeLayout rl_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 手机窗口设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
        setLayout();
        // 注入控件
        ViewUtils.inject(this);
        init(savedInstanceState);
//        MobclickAgent.setDebugMode(true);//集成测试
//        MyLogUtils.info(getDeviceInfo(getApplicationContext()));
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    public abstract void init(Bundle savedInstanceState);

    /**
     * 设置顶部标题
     *
     * @param title
     */
    public void setTopTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 顶部右边按键
     *
     * @param rightText
     */
    public void setRightBtn(String rightText, View.OnClickListener listener) {
        tvRight = (TextView) findViewById(R.id.tvRight);
        rl_right = (RelativeLayout) findViewById(R.id.rl_right);
        if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
            rl_right.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
        }
        if (listener != null) {
            rl_right.setOnClickListener(listener);
        }
    }


    public void setRightVG(boolean isVisible) {
        rl_right = (RelativeLayout) findViewById(R.id.rl_right);
        if (isVisible) {
            rl_right.setVisibility(View.VISIBLE);
        } else {
            rl_right.setVisibility(View.GONE);
        }
    }
    /**
     * 设置左边文字
     * @param tv
     */
    public void setLeftTv(String tv){
        tvLeft= (TextView) findViewById(R.id.tvLeft);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(tv);
    }


    /**
     * 返回
     *
     * @param view
     */
    public void goback(View view) {
        finish();
    }


    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }


    /**
     * 获取测试设备信息
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
