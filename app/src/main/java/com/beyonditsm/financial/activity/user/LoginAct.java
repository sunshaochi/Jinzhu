package com.beyonditsm.financial.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.entity.ProfileInfoBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.AutoAnimImageView;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 登录
 * Created by wangbin on 15/11/11.
 */
public class LoginAct extends BaseActivity {

    private ClearEditText loginPhone;//手机号
    private ClearEditText loginPwd;//密码
    private RelativeLayout loginBtn;//登录
    private TextView login_zc;//注册
    private AutoAnimImageView progressBar1;

    private String phone, pwd;

    public final String LOGIN_TYPE = "login_type";
    private int LTYPE;
    private String type;

    private UserLoginBean user;
    private ProfileInfoBean profileInfoBean;
    private void assignViews() {
        loginPhone = (ClearEditText) findViewById(R.id.login_phone);
        loginPwd = (ClearEditText) findViewById(R.id.login_pwd);
        loginBtn = (RelativeLayout) findViewById(R.id.login_btn);
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
        type = getIntent().getStringExtra("TYPE");
        LTYPE = getIntent().getIntExtra(LOGIN_TYPE, 0);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        int flag = getIntent().getIntExtra("FLAG", 1);
        if (flag == 0) {
            login_zc.setVisibility(View.INVISIBLE);
        } else {
            login_zc.setVisibility(View.VISIBLE);
        }
        loginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i == 3 || i == 8 || s.charAt(i) != ' ') {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    loginPhone.setText(sb.toString());
                    loginPhone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.login_btn, R.id.login_lost_pwd, R.id.tvRight, R.id.login_zc, R.id.rl_back})
    public void todo(View view) {
        switch (view.getId()) {
            /*登录*/
            case R.id.login_btn:
                if (isValidate()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(loginBtn.getWindowToken(), 0);
                    loginBtn.setEnabled(false);
                    progressBar1.setVisibility(View.VISIBLE);
                    UserEntity ue = new UserEntity();
                    ue.setUsername(phone);
                    ParamsUtil.getInstance().setUserID(loginPhone.getText().toString() + "");
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
//                if (LTYPE == 1) {
//                    Intent intent = new Intent(LoginAct.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                    finish();
//                }
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (LTYPE == 1) {
            Intent intent = new Intent(LoginAct.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            sendBroadcast(new Intent(MainActivity.UPDATATAB));
            finish();
        }
    }

    private boolean isValidate() {
        String s = loginPhone.getText().toString();
        phone = s.replaceAll(" +", "");
        pwd = loginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
            loginPhone.requestFocus();
            return false;
        }
//        if (!FinancialUtil.isMobileNO(phone)){
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
//            loginPhone.requestFocus();
//            return false;
//        }
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
     * @param ue 用户实体类
     */
    private void toLogin(final UserEntity ue) {
        RequestManager.getCommManager().toLogin(ue, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                getUserLoginInfo();

//                Intent intent=new Intent(MineFragment.USER_KEY);
//                intent.putParcelableArrayListExtra(MineFragment.USER_KEY,)
//                sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                sendBroadcast(new Intent("com.update.user"));

//                try {
//                    updateLocation(ParamsUtil.getInstance().getChangedCity());
//                    ParamsUtil.getInstance().setReLogin(true);
//                    TestinAgent.setUserInfo(ue.getUserName()+"");
//                    JSONObject objects = new JSONObject(result);
//                    JSONObject datas = objects.getJSONObject("data");
//                    String token = datas.optString("rcToken");
//                    loginBtn.setEnabled(true);
//                    progressBar1.setVisibility(View.GONE);
//                    if (ConstantValue.STEP == 10) {
//                        sendBroadcast(new Intent(CreditStepAct.UPDATA));
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            String roleName = data.getString("roleName");
//                            String accountId=data.optString("accountAlias");
//                            String agencyIdTag=data.optString("agencyIdTag");
//                            SpUtils.setRoleName(getApplicationContext(), "users");
//                            SpUtils.setToken(getApplicationContext(), token);
////
//                            if(JPushInterface.isPushStopped(getApplicationContext())){
//                                JPushInterface.resumePush(getApplicationContext());
//                            }
//                            Set<String> set= new HashSet<>();
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
////                        connect(SpUtils.getT);
//                        if (!TextUtils.isEmpty(type)&&type.equals("speed")){
//                            sendBroadcast(new Intent(CreditSpeedFirstAct.GET_LOGIN_STATUS));
//                            gotoActivity(CreditSpeedSecond_1Act.class,true);
//                        }
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
//                            SpUtils.setRoleName(getApplicationContext(), "users");
//                            SpUtils.setToken(getApplicationContext(), token);
//
//                            if(JPushInterface.isPushStopped(getApplicationContext())){
//                                JPushInterface.resumePush(getApplicationContext());
//                            }
//                            Set<String> set= new HashSet<>();
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
//                                connect(SpUtils.getToken(getApplicationContext()));
//                             if (SpUtils.getRoleName(getApplicationContext()).equals("ROLE_COMMON_CLIENT")) {
//                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                                sendBroadcast(new Intent(MineFragment.UPDATE_USER));
//                                if(null != ParamsUtil.getInstance().getServiceMainAct()){
//                                    ParamsUtil.getInstance().getServiceMainAct().finish();
//                                }
//                                gotoActivity(MainActivity.class, true);
//                            } else {
//                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                                sendBroadcast(new Intent(MineFragment.UPDATE_USER));
//                                if(null != ParamsUtil.getInstance().getServiceMainAct()){
//                                    ParamsUtil.getInstance().getServiceMainAct().finish();
//                                }
//                                gotoActivity(MainActivity.class, true);
////                                sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
////                                if(null != ParamsUtil.getInstance().getMainAct()){
////                                    ParamsUtil.getInstance().getMainAct().finish();
////                                }
////
////                                gotoActivity(ServiceMainAct.class, true);
//
//                            }
//                            loginBtn.setEnabled(true);
//                            progressBar1.setVisibility(View.GONE);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//
////                    connect(token,result);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
    private void connect(final String token) {
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
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

                ResultData<UserLoginBean> rd = (ResultData<UserLoginBean>) GsonUtils.json(result, UserLoginBean.class);
                UserLoginBean data = rd.getData();
                if (TextUtils.equals(data.getUserStatus(),"ROLE_COMMON_USER")){
                    ProfileInfoBean profileInfo = data.getProfileInfo();
                    if (!TextUtils.isEmpty(profileInfo.getName())){
                        SpUtils.setUsername(MyApplication.getInstance().getApplicationContext(),profileInfo.getName());
                    }else {
                        SpUtils.setUsername(MyApplication.getInstance().getApplicationContext(),data.getUsername());
                    }

                    loginBtn.setEnabled(true);
                    progressBar1.setVisibility(View.GONE);
                    SpUtils.setRoleName(MyApplication.getInstance().getApplicationContext(),rd.getData().getUserStatus()+"");
                    SpUtils.setPhonenumber(MyApplication.getInstance().getApplicationContext(),rd.getData().getUsername());
                    Intent intent = new Intent("com.update.user");
                    sendBroadcast(intent);
                    finish();
                }else if (TextUtils.equals(data.getUserStatus(),"ROLE_CREDIT_MANAGER_0") || TextUtils.equals(data.getUserStatus(),"UNASSIGNED")
                        || TextUtils.equals(data.getUserStatus(),"ROLE_AREA_MANAGER") || TextUtils.equals(data.getUserStatus(),"ROLE_CREDIT_MANAGER")){
                    MyToastUtils.showShortToast(MyApplication.getInstance().getApplicationContext(),"该用户为信贷经理用户，请下载信贷经理端后重新登录");
                    loginBtn.setEnabled(true);
                    progressBar1.setVisibility(View.GONE);
                    return;
                }
//                loginBtn.setEnabled(true);
//                progressBar1.setVisibility(View.GONE);
//                SpUtils.setRoleName(MyApplication.getInstance().getApplicationContext(),rd.getData().getUsername()+"");
//                Intent intent = new Intent("com.update.user");
//                sendBroadcast(intent);
//                finish();
//>>>>>>> 997395d331f60be2a0aa3eb875151111492b27b5
//                ParamsUtil.getInstance().setUle(rd.getData());
//                if ("ROLE_CREDIT_MANAGER".equals(roleName)) {
//                    sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
//                    gotoActivity(ManagerMainAct.class, true);
//                } else if (roleName.equals("ROLE_COMMON_CLIENT")) {
//                    sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                    sendBroadcast(new Intent(MineFragment.UPDATE_USER));
//                    gotoActivity(MainActivity.class, true);
//                } else {
//                    sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                    sendBroadcast(new Intent(MineFragment.UPDATE_USER));
//                    gotoActivity(MainActivity.class, true);
//                    sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
//                    gotoActivity(ServiceMainAct.class, true);

            }

            @Override
            public void onError(int status, String msg) {
                getUserLoginInfo();
            }
        });
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


    private void updateLocation(String area) {
        RequestManager.getCommManager().updateLocation(area, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParamsUtil.getInstance().setLogin(false);
    }
}
