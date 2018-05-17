package com.beyonditsm.financial.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.util.ParamsUtil;
import com.lidroid.xutils.ViewUtils;
import com.tandong.sa.activity.SmartFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 基础activity
 * Created by wangbin on 15/11/11.
 */
public abstract class BaseActivity extends SmartFragmentActivity {


    

    private RelativeLayout rl_right;
    private int  REQUEST_LOGIN = 110;
//    private RelativeLayout rlStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 手机窗口设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        if (Build.VERSION.SDK_INT>=21){
////            rlStatusBar.setVisibility(View.VISIBLE);
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
////            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
        //禁止EditText自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
        setLayout();
        // 注入控件
        ViewUtils.inject(this);
        init(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction("UNLOGIN");
        this.registerReceiver(mybroad, filter);
//        MobclickAgent.setDebugMode(true);//集成测试
//        MyLogUtils.info(getDeviceInfo(getApplicationContext()));
//        immersed = new NotificationImmersed(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mybroad);
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    public abstract void init(Bundle savedInstanceState);

    /**
     * 设置顶部标题
     *
     * @param title 标题
     */
    public void setTopTitle(String title) {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 顶部右边按键
     *
     * @param rightText 右边文字
     */
    public void setRightBtn(String rightText, View.OnClickListener listener) {
        TextView tvRight = (TextView) findViewById(R.id.tvRight);
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
     *
     * @param tv 左边文字
     */
    public void setLeftTv(String tv) {
        TextView tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvLeft.setVisibility(View.GONE);
        tvLeft.setText(tv);
    }


    /**
     * 返回
     *
     * @param view view
     */
    public void goback(View view) {
        finish();
    }


    protected void onResume() {
        super.onResume();
//        if (null!=ParamsUtil.getInstance().getCurrentAct()){
//            getUserLoginInfo(ParamsUtil.getInstance().getCurrentAct());
//        }
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
//        immersed.setStateBarColor(this.getResources().getColor(R.color.main_color));
    }
//    /**
//     * 获取用户的角色信息
//     */
//    private void getUserLoginInfo(final Activity activity) {
//        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                JSONObject obj = new JSONObject(result);
//                int status = obj.getInt("status");
//                if(status == 600){
//                    activity.startActivity(((new Intent(activity,MainActivity.class)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
//                    SpUtils.clearSp(MyApplication.getInstance());
//                    SpUtils.clearRoleName(MyApplication.getInstance());
//                    EventBus.getDefault().post(new SwitchEvent());
//                }
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//            }
//        });
//    }
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }


    BroadcastReceiver mybroad = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (!ParamsUtil.getInstance().isLogin()) {
                ParamsUtil.getInstance().setLogin(true);
                Intent intent1 = new Intent(context, LoginAct.class);
                startActivityForResult(intent1, REQUEST_LOGIN);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            Intent intent = new Intent();
            intent.setAction("LOGIN_BACK");
            sendBroadcast(intent);
            onLoginBack();
        }
    }

    /**
     * 当登陆返回时调用的方法
     */
    public void onLoginBack() {

    }

}
