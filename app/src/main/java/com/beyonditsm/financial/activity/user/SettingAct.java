package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.servicer.ChangePwdAct;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GeneralUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.ToggleButton;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 设置
 * Created by wangbin on 15/11/12.
 */
public class SettingAct extends BaseActivity {
    @ViewInject(R.id.tb_msg)
    private ToggleButton tb_msg;//消息提醒
    @ViewInject(R.id.tb_sleep)
    private ToggleButton tb_sleep;//消息免打扰

    private GeneralUtils gUtils;
//    private UserEntity userInfo;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("设置");
        gUtils = new GeneralUtils();
//        userInfo=getIntent().getParcelableExtra("user_info");
        tb_msg.setIsSwitch(SpUtils.getMsg(SettingAct.this));
        tb_sleep.setIsSwitch(SpUtils.getSleep(SettingAct.this));
        tb_msg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                //是否接受新消息
                if (on) {
                    SpUtils.setMsg(getApplicationContext(), true);
                    if (JPushInterface.isPushStopped(getApplicationContext()))
                        JPushInterface.resumePush(getApplicationContext());
                } else {

                }
            }
        });
        tb_sleep.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                //消息免打扰
                if (on) {
                    SpUtils.setSleep(SettingAct.this, true);
                    JPushInterface.setSilenceTime(getApplicationContext(), 0, 0, 23, 59);
                    RongIM.getInstance().getRongIMClient().setNotificationQuietHours("00:00:00", 1399, new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });

                } else {
                    SpUtils.setSleep(SettingAct.this, false);
                    JPushInterface.setSilenceTime(getApplicationContext(), 0, 0, 0, 0);
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
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(!tb_msg.IsSwitch()){
            SpUtils.setMsg(getApplicationContext(), false);
            JPushInterface.stopPush(getApplicationContext());
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.rlUpdate, R.id.rlUpdatePwd, R.id.rlcheck, R.id.rlAbout})
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
                gUtils.toVersion(SettingAct.this, FinancialUtil.getAppVer(SettingAct.this),0);

                break;
            //关于
            case R.id.rlAbout:
                intent=new Intent(this,AboutOurs.class);
                startActivity(intent);
                break;
        }


    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        if(receiver==null){
//            receiver=new UpdateReceiver();
//        }
//       registerReceiver(receiver, new IntentFilter(UPDATE_USER));
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(receiver!=null){
//           unregisterReceiver(receiver);
//        }
//    }
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
}
