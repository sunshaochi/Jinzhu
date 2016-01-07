package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.user.ClauseJinzhu;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GeneralUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;

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
 * 贷款第一步
 * Created by Yang on 2015/11/12 0012.
 */
public class CreditFirstFrag extends BaseFragment {
    private EditText creName;
    private EditText crePhone;
    private TextView creBtnYzm;
    private EditText creYzm;
    private EditText crePwd;
//    private EditText crePwd2;
    private EditText creYqm;
    private CheckBox creTk;
    private Button firstBtnNext;
    private TextView creTvLogin;
    private View view;

    private Intent intent;
    private String phone;
    private String yzm;
    private String pwd;
    private String yqm;
    private GeneralUtils generalUtil;
    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    private TextView intro;//金蛛条款说明



    private void assignViews() {
        creName = (EditText) view.findViewById(R.id.cre_name);
        crePhone = (EditText) view.findViewById(R.id.cre_phone);
        creBtnYzm = (TextView) view.findViewById(R.id.cre_btn_yzm);
        creYzm = (EditText) view.findViewById(R.id.cre_yzm);
        crePwd = (EditText) view.findViewById(R.id.cre_pwd);
//        crePwd2 = (EditText) view.findViewById(R.id.cre_pwd2);
        creYqm = (EditText) view.findViewById(R.id.cre_yqm);
        creTk = (CheckBox) view.findViewById(R.id.cre_tk);
        firstBtnNext = (Button) view.findViewById(R.id.first_btn_next);
        creTvLogin = (TextView) view.findViewById(R.id.cre_tv_login);
        intro= (TextView) view.findViewById(R.id.intro);
    }


    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.creditfirstfrag, null);
        assignViews();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        generalUtil = new GeneralUtils();
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.cre_btn_yzm, R.id.first_btn_next, R.id.cre_tv_login,R.id.intro})
    public void todo(View v) {
        switch (v.getId()) {
            //金蛛条款说明
            case R.id.intro:
                Intent intent=new Intent();
                intent.setClass(context, ClauseJinzhu.class);
                getActivity().startActivity(intent);
                break;
            //获取验证码
            case R.id.cre_btn_yzm:
                phone = crePhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    MyToastUtils.showShortToast(context, "请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    MyToastUtils.showShortToast(context, "请输入正确的手机号码");
                    return;
                }

                String mobile = crePhone.getText().toString().trim();
                generalUtil.getCode(mobile);
                generalUtil.setiCode(new GeneralUtils.ICode() {
                    @Override
                    public void isSucess(int flag) {
                        if(flag==1){
                            i = 60;
                            creBtnYzm.setEnabled(false);
                            timer = new Timer();
                            myTask = new MyTimerTask();
                            timer.schedule(myTask, 0, 1000);
                        }
                    }
                });
                break;
            //下一步
            case R.id.first_btn_next:
                if (isValidate()){
                    if(!creTk.isChecked()){
                        MyToastUtils.showShortToast(context,"请同意金珠条款");
                    }else {
                        UserEntity ue=new UserEntity();
                        ue.setUsername(phone);
                        ue.setPassword(phone.substring(phone.length() - 6, phone.length()));
                        String capture=creYzm.getText().toString();
                        toRegister(ue, phone,capture);
                    }
                }
                break;
            //跳登录
            case R.id.cre_tv_login:
                ConstantValue.STEP = 10;
                intent = new Intent(context, LoginAct.class);
                startActivity(intent);
                break;
        }
    }

//    private void numberIsValidate(){
//        RequestManager.getCommManager().isPhoneValidate(phone, new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                UserEntity ue=new UserEntity();
//                ue.setUsername(phone);
//                ue.setPassword(phone.substring(phone.length() - 6, phone.length()));
//                String capture=creYzm.getText().toString();
//                toRegister(ue,phone,capture);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private boolean isValidate() {
        phone = crePhone.getText().toString().trim();
        yzm = creYzm.getText().toString().trim();
        pwd = crePwd.getText().toString().trim();
//        pwd2 = regPwd2.getText().toString().trim();
        yqm = creYqm.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(context, "请输入手机号");
            crePhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(yzm)) {
            MyToastUtils.showShortDebugToast(context, "请输入验证码");
            creYzm.requestFocus();
            return false;
        }
/*
        if (TextUtils.isEmpty(pwd)) {
            MyToastUtils.showShortDebugToast(context, "请输入密码");
            crePwd.requestFocus();
            return false;
        }
*/
        return true;
    }

    private void toRegister(UserEntity ue,String phone,String capture){
        RequestManager.getCommManager().toRegister(ue,phone,capture, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                context.sendBroadcast(new Intent(MineFragment.UPDATE_USER));
                context.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
//                    String roleName = data.optString("roleName");
                    String accountId=data.optString("accountAlias");
                    String agencyIdTag=data.optString("agencyIdTag");
                    String token = data.optString("rcToken");
                    SpUtils.setRoleName(getActivity(), "ROLE_COMMON_CLIENT");
                    SpUtils.setToken(getActivity(), token);
                    connect(token);
                    if (JPushInterface.isPushStopped(getActivity())) {
                        JPushInterface.resumePush(getActivity());
                    }
                    Set<String> set = new HashSet<String>();
                    if (!TextUtils.isEmpty(agencyIdTag)) {
                        set.add(agencyIdTag);
                    }
                    JPushInterface.setAliasAndTags(getActivity(), accountId, set, new TagAliasCallback() {
                        @Override
                        public void gotResult(int arg0, String arg1, Set<String> arg2) {
                        }
                    });
                    EventBus.getDefault().post(new CreditStepAct.FirstEvent(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!TextUtils.isEmpty(yqm)) {
                    RequestManager.getCommManager().myReferralCode(yqm, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {

                        }

                        @Override
                        public void onError(int status, String msg) {

                        }
                    });
                }
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(context,msg);
            }
        });
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
                creBtnYzm.setEnabled(true);
                creBtnYzm.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                creBtnYzm.setText(msg.what + "秒");
            }
        }

    };

    /**
     * 建立与融云服务器的连接
     */
    private void connect(final String token) {
        if (getActivity().getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getActivity().getApplicationContext()))) {
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
                    Log.d("LoginActivity", "--onSuccess" + userid);
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
