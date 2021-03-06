package com.beyonditsm.financial.activity.user;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GeneralUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 注册
 * Created by Yang on 2015/11/11 0011.
 */
@SuppressWarnings("deprecation")
public class RegisterAct extends BaseActivity {
    private ClearEditText regPhone;//手机号
    private EditText regYzm;//验证码
    private TextView regYzmBtn;//获取验证码
    //    private EditText regPwd2;//确认密码
    private EditText regYqm;//邀请码
    @ViewInject(R.id.cre_tk)
    private CheckBox cb;//金蛛条款

    @ViewInject(R.id.llYqm)
    private LinearLayout llYqm;
    @ViewInject(R.id.ivSlide)
    private ImageView ivSlide;

    private String phone;
    private String yzm;
    private String yqm;

    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;

    private GeneralUtils generalUtil;

    private boolean isShowYqm=false;

    private ObjectAnimator  obaDownts;
    private ObjectAnimator  obaOnts;

    private void assignViews() {
        regPhone = (ClearEditText) findViewById(R.id.reg_phone);
        regYzm = (EditText) findViewById(R.id.reg_yzm);
        regYzmBtn = (TextView) findViewById(R.id.reg_yzm_btn);
        //        regPwd2 = (EditText) findViewById(R.id.reg_pwd2);
        regYqm = (EditText) findViewById(R.id.reg_yqm);
        TextView intro = (TextView) findViewById(R.id.intro);
        intro.setText(Html.fromHtml("《金蛛金服用户协议》"));
        intro.setTextColor(getResources().getColor(R.color.blue_color));
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.registeract);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册");
        assignViews();
        setLeftTv("返回");
        generalUtil = new GeneralUtils();
        obaDownts = ObjectAnimator.ofFloat(ivSlide, "rotation", 0,
                180);
        obaDownts.setDuration(100);
        obaOnts = ObjectAnimator.ofFloat(ivSlide, "rotation", -180,
                0);
        obaOnts.setDuration(100);

    }

    @OnClick({R.id.reg_yzm_btn, R.id.reg_btn,R.id.intro,R.id.rlSlide})
    public void todo(View v) {
        switch (v.getId()) {
            //金蛛条款说明
            case R.id.intro:
                Intent intent = new Intent();
                intent.setClass(RegisterAct.this, ClauseJinzhu.class);
                startActivity(intent);
                break;
            //获取验证码
            case R.id.reg_yzm_btn:
                if (isPhoneEnable()) {
                    phone = regPhone.getText().toString().trim();
//                    String mobile = regPhone.getText().toString().trim();
                    generalUtil.getCode(phone);
                    generalUtil.setiCode(new GeneralUtils.ICode() {
                        @Override
                        public void isSucess(int flag) {
                            if (flag == 1) {
                                i = 60;
                                regYzmBtn.setEnabled(false);
                                timer = new Timer();
                                myTask = new MyTimerTask();
                                timer.schedule(myTask, 0, 1000);
                            }
                        }
                    });
                }
                break;
            //注册
            case R.id.reg_btn:
                if (isValidate()) {
                    if(cb.isChecked()){
                        UserEntity ue = new UserEntity();
                        ue.setUsername(phone);
//                        ue.setPassword(pwd);
                        ue.setCaptcha(yzm);
                        if (!TextUtils.isEmpty(yqm))
                            ue.setReferralCode(yqm);
                        toRegister(ue, phone, yzm);
                    }else {
                        MyToastUtils.showShortToast(getApplicationContext(),"请首先同意金蛛金服用户协议");
                    }

                }
                break;
            case R.id.rlSlide:
                if(!isShowYqm){
                    obaDownts.start();
                    llYqm.setVisibility(View.VISIBLE);
                    isShowYqm=true;
                }else{
                    obaOnts.start();
                    llYqm.setVisibility(View.GONE);
                    isShowYqm=false;
                }
                break;
        }
    }

    private void toRegister(final UserEntity ue, String phoneNumber, String captcha) {
        RequestManager.getCommManager().toRegister2(ue, phoneNumber, captcha, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                finish();
                MyToastUtils.showShortToast(getApplicationContext(),"注册成功，请查看短信登录密码");
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    String token = data.optString("rcToken");
                    SpUtils.setToken(getApplicationContext(), token);
                    connect(token, result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    private boolean isValidate() {
        phone = regPhone.getText().toString().trim();
        yzm = regYzm.getText().toString().trim();
        //        pwd2 = regPwd2.getText().toString().trim();
        yqm = regYqm.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
            regPhone.requestFocus();
            return false;
        }
//        if (!FinancialUtil.isMobileNO(phone)){
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
//            regPhone.requestFocus();
//            return false;
//        }
        if (TextUtils.isEmpty(yzm)) {
            MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入验证码");
            regYzm.requestFocus();
            return false;
        }
        if (yzm.length()<6){
            MyToastUtils.showShortToast(getApplicationContext(), "验证码必须为6位纯数字");
            regYzm.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isPhoneEnable(){
        phone = regPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
            regPhone.requestFocus();
            return false;
        }
//        if (!FinancialUtil.isMobileNO(phone)){
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
//            regPhone.requestFocus();
//            return false;
//        }
        return  true;
    }

    /**
     * 倒计时
     *
     * @author wangbin
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(i--);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                regYzmBtn.setEnabled(true);
                regYzmBtn.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                regYzmBtn.setText(msg.what + "秒");
            }
        }

    };


    /**
     * 建立与融云服务器的连接
     */
    private void connect(final String token, final String result) {
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    sendBroadcast(new Intent(MineFragment.UPDATE_USER));
                    sendBroadcast(new Intent(MainActivity.UPDATATAB));
                    SpUtils.setRoleName(getApplicationContext(), "ROLE_COMMON_CLIENT");
                    SpUtils.setToken(getApplicationContext(), token);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
//                        String roleName = data.getString("roleName");
                        String accountId = data.optString("accountAlias");
                        String agencyIdTag = data.optString("agencyIdTag");
                        SpUtils.setRoleName(getApplicationContext(),"ROLE_COMMON_CLIENT");

                        if (JPushInterface.isPushStopped(getApplicationContext())) {
                            JPushInterface.resumePush(getApplicationContext());
                        }
                        Set<String> set = new HashSet<>();
                        if (!TextUtils.isEmpty(agencyIdTag)) {
                            set.add(agencyIdTag);
                        }
                        JPushInterface.setAliasAndTags(getApplicationContext(), accountId, set, new TagAliasCallback() {
                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ConstantValue.STEP == 10) {
                        sendBroadcast(new Intent(CreditStepAct.UPDATA));
                        AppManager.getAppManager().finishActivity(LoginAct.class);
                        MyToastUtils.showShortToast(getApplicationContext(), "注册成功");
                        ConstantValue.STEP = 0;
                        finish();
                    } else {
                        gotoActivity(MainActivity.class, true);
                        AppManager.getAppManager().finishActivity(LoginAct.class);
                        MyToastUtils.showShortToast(getApplicationContext(), "注册成功");
                    }
                    RongCloudEvent.getInstance().setOtherListener();

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }
}
