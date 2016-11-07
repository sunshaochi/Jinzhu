package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sunshaochi-1 on 2015/11/19
 */
public class FindPwdAct extends BaseActivity {
    private TextView gettv;
    private ClearEditText findetphone;
    private EditText findetpwd;

    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
//    private String phoneNumber;

    @ViewInject(R.id.iv_sms_pic)
    ImageView ivSmsPic;

    public static final String PHONENUM = "phone";
    public static final String CAPTCHA = "captcha";
    private String name;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_findpw);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setTopTitle("找回密码");
        setLeftTv("返回");
        setView();
        getSMSPic();

        findetphone.addTextChangedListener(new TextWatcher() {
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
                    findetphone.setText(sb.toString());
                    findetphone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getSMSPic() {
//        CommManager.getCommManager().get
    }

    private void setView() {
        gettv = (TextView) findViewById(R.id.gettv);
        findetphone = (ClearEditText) findViewById(R.id.findetphone);
        findetpwd = (EditText) findViewById(R.id.findetpwd);
    }

    @OnClick({R.id.gettv, R.id.nexttv})
    public void toClick(View v) {
        switch (v.getId()) {
            //获取验证码


            case R.id.gettv:
                if (isPhoneNum()){
                        RequestManager.getCommManager().findpwbyCode(name, new RequestManager.CallBack() {
                            @Override
                            public void onSucess(String result) throws JSONException {
                                i = 60;
                                gettv.setEnabled(false);
                                timer = new Timer();
                                myTask = new MyTimerTask();
                                timer.schedule(myTask, 0, 1000);

                            }

                            @Override
                            public void onError(int status, String msg) {
                                MyToastUtils.showShortToast(getApplicationContext(), msg);
                            }
                        });

                }

                break;
            //下一步
            case R.id.nexttv:
                String name = findetphone.getText().toString().replaceAll(" +", "");
                String authcode = findetpwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
                    findetphone.requestFocus();
                    return;
                }
                if (name.length() != 11) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    findetphone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(authcode)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入验证码");
                    findetpwd.requestFocus();
                    return;
                }

                Intent intent = new Intent(this, UpdatePwdAct.class);
                intent.putExtra(PHONENUM, name);
                intent.putExtra(CAPTCHA, authcode);
                startActivity(intent);


                break;
        }
    }


    private boolean isPhoneNum() {
        name = findetphone.getText().toString().replaceAll(" +", "");
        if (TextUtils.isEmpty(name)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            return false;
        }
        if (name.length() != 11) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
            return false;
        }
        return true;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            handler.sendEmptyMessage(i--);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                gettv.setEnabled(true);
                gettv.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                gettv.setText(msg.what + "秒");
            }
        }
    };
}



