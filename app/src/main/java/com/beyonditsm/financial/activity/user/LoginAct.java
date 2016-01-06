package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.manager.ManagerMainAct;
import com.beyonditsm.financial.activity.servicer.ServiceMainAct;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.AutoAnimImageView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 登录
 * Created by wangbin on 15/11/11.
 */
public class LoginAct extends BaseActivity{

    private RelativeLayout rlBack;//后退键
    private TextView tvTitle;//标题
    private EditText loginPhone;//手机号
    private EditText loginPwd;//密码
    private RelativeLayout loginBtn;//登录
    private TextView loginLostPwd;//忘记密码
    private TextView login_zc;//注册
    private AutoAnimImageView progressBar1;

    private String phone, pwd;


    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        loginPhone = (EditText) findViewById(R.id.login_phone);
        loginPwd = (EditText) findViewById(R.id.login_pwd);
        loginBtn = (RelativeLayout) findViewById(R.id.login_btn);
        loginLostPwd = (TextView) findViewById(R.id.login_lost_pwd);
        login_zc = (TextView) findViewById(R.id.login_zc);
        progressBar1 = (AutoAnimImageView) findViewById(R.id.progressBar1);
    }


    @Override
    public void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("用户登录");
        AppManager.getAppManager().addActivity(this);
        assignViews();
    }

    @OnClick({R.id.login_btn, R.id.login_lost_pwd, R.id.tvRight,R.id.login_zc,R.id.rl_back})
    public void todo(View view) {
        switch (view.getId()) {
            /*登录*/
            case R.id.login_btn:
                if (isValidate()) {
                    loginBtn.setEnabled(false);
                    progressBar1.setVisibility(View.VISIBLE);
                    UserEntity ue = new UserEntity();
                    ue.setUsername(phone);
                    ue.setPassword(pwd);
                    toLogin(ue);
                }
                break;
            /*忘记密码*/
            case R.id.login_lost_pwd:
                gotoActivity(FindPwdAct.class, false);
                break;
            /*注册*/
            case R.id.login_zc:
                gotoActivity(RegisterAct.class, false);
                break;
            case R.id.rl_back:
                sendBroadcast(new Intent(MainActivity.UPDATATAB));
                finish();
                break;
        }
    }

    private boolean isValidate() {
        phone = loginPhone.getText().toString().trim();
        pwd = loginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            loginPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入密码");
            loginPwd.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 登录
     *
     * @param ue
     */
    private void toLogin(UserEntity ue) {
        RequestManager.getCommManager().toLogin(ue, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {

                try {
                    JSONObject objects = new JSONObject(result);
                    JSONObject datas = objects.getJSONObject("data");
                    String token = datas.optString("rcToken");

//                    loginBtn.setEnabled(true);
//                    progressBar1.setVisibility(View.GONE);
//                    if (ConstantValue.STEP == 10) {
//                        sendBroadcast(new Intent(CreditStepAct.UPDATA));
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(result);
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            String roleName = data.getString("roleName");
//                            String accountId=data.optString("accountAlias");
//                            String agencyIdTag=data.optString("agencyIdTag");
//                            SpUtils.setRoleName(getApplicationContext(), roleName);
//                            SpUtils.setToken(getApplicationContext(), token);
//
//                            if(JPushInterface.isPushStopped(getApplicationContext())){
//                                JPushInterface.resumePush(getApplicationContext());
//                            }
//                            Set<String> set=new HashSet<String>();
//                            if(!TextUtils.isEmpty(agencyIdTag)){
//                                set.add(agencyIdTag);
//                            }
//
//                            JPushInterface.setAliasAndTags(getApplicationContext(),accountId, set, new TagAliasCallback() {
//                                @Override
//                                public void gotResult(int arg0, String arg1, Set<String> arg2) {
//                                    // TODO Auto-generated method stub
//
//                                }
//                            });
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        finish();
//                        ConstantValue.STEP = 0;
//                    } else {
//                        EventBus.getDefault().post(new LoginEvent(1));
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            String roleName = data.getString("roleName");
//                            String accountId=data.optString("accountAlias");
//                            String agencyIdTag=data.optString("agencyIdTag");
//                            SpUtils.setRoleName(getApplicationContext(), roleName);
//                            SpUtils.setToken(getApplicationContext(), token);
//
//                            if(JPushInterface.isPushStopped(getApplicationContext())){
//                                JPushInterface.resumePush(getApplicationContext());
//                            }
//                            Set<String> set=new HashSet<String>();
//                            if(!TextUtils.isEmpty(agencyIdTag)){
//                                set.add(agencyIdTag);
//                            }
//                            JPushInterface.setAliasAndTags(getApplicationContext(),accountId, set, new TagAliasCallback() {
//                                @Override
//                                public void gotResult(int arg0, String arg1, Set<String> arg2) {
//                                    // TODO Auto-generated method stub
//
//                                }
//                            });
//
//                            if ("ROLE_CREDIT_MANAGER".equals(roleName)) {
//                                sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
//                                gotoActivity(ManagerMainAct.class, true);
//                            } else if (roleName.equals("ROLE_COMMON_CLIENT")) {
//                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                                sendBroadcast(new Intent(MineFragment.UPDATE_USER));
//                                gotoActivity(MainActivity.class, true);
//                            } else {
//                                sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
//                                gotoActivity(ServiceMainAct.class, true);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }

//                    gotoActivity(MainActivity.class, true);
                    connect(token,result);
//                    gotoActivity(ServiceMainAct.class, true);
//                    gotoActivity(ManagerMainAct.class, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int status, String msg) {
                loginBtn.setEnabled(true);
                progressBar1.setVisibility(View.GONE);
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }


    /**
     * 建立与融云服务器的连接
     */
    private void connect(final String token,final String result) {
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
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    loginBtn.setEnabled(true);
                    progressBar1.setVisibility(View.GONE);
                    if (ConstantValue.STEP == 10) {
                        sendBroadcast(new Intent(CreditStepAct.UPDATA));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONObject data = jsonObject.getJSONObject("data");
                            String roleName = data.getString("roleName");
                            String accountId=data.optString("accountAlias");
                            String agencyIdTag=data.optString("agencyIdTag");
                            SpUtils.setRoleName(getApplicationContext(), roleName);
                            SpUtils.setToken(getApplicationContext(), token);

                            if(JPushInterface.isPushStopped(getApplicationContext())){
                                JPushInterface.resumePush(getApplicationContext());
                            }
                            Set<String> set=new HashSet<String>();
                            if(!TextUtils.isEmpty(agencyIdTag)){
                                set.add(agencyIdTag);
                            }

                            JPushInterface.setAliasAndTags(getApplicationContext(),accountId, set, new TagAliasCallback() {
                                @Override
                                public void gotResult(int arg0, String arg1, Set<String> arg2) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        finish();
                        ConstantValue.STEP = 0;
                    } else {
                        EventBus.getDefault().post(new LoginEvent(1));
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject data = jsonObject.getJSONObject("data");
                            String roleName = data.getString("roleName");
                            String accountId=data.optString("accountAlias");
                            String agencyIdTag=data.optString("agencyIdTag");
                            SpUtils.setRoleName(getApplicationContext(), roleName);
                            SpUtils.setToken(getApplicationContext(), token);

                            if(JPushInterface.isPushStopped(getApplicationContext())){
                                JPushInterface.resumePush(getApplicationContext());
                            }
                            Set<String> set=new HashSet<String>();
                            if(!TextUtils.isEmpty(agencyIdTag)){
                                set.add(agencyIdTag);
                            }
                            JPushInterface.setAliasAndTags(getApplicationContext(),accountId, set, new TagAliasCallback() {
                                @Override
                                public void gotResult(int arg0, String arg1, Set<String> arg2) {
                                    // TODO Auto-generated method stub

                                }
                            });

                            if ("ROLE_CREDIT_MANAGER".equals(roleName)) {
                                sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
                                gotoActivity(ManagerMainAct.class, true);
                            } else if (roleName.equals("ROLE_COMMON_CLIENT")) {
                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
                                sendBroadcast(new Intent(MineFragment.UPDATE_USER));
                                gotoActivity(MainActivity.class, true);
                            } else {
                                sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
                                gotoActivity(ServiceMainAct.class, true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RongCloudEvent.getInstance().setOtherListener();
                    }
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    loginBtn.setEnabled(true);
                    progressBar1.setVisibility(View.GONE);
                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }



    /**
     * 登录
     */
    public class LoginEvent {
        public int sucess;//1登录成功

        public LoginEvent(int suce) {
            sucess = suce;
        }
    }
}
