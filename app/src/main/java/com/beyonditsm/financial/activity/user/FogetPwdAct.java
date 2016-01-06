package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 忘记密码
 * Created by wangbin on 15/11/16.
 */
public class FogetPwdAct extends BaseActivity {
    private EditText etPhone;
    private EditText etPwd;
    private Button btnCode;
    private EditText etCode;
    private Button btnSure;

    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    private String name, code, pwd;

    private void assignViews() {
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnCode = (Button) findViewById(R.id.btnCode);
        etCode = (EditText) findViewById(R.id.etCode);
        btnSure = (Button) findViewById(R.id.btnSure);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_fogetpwd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("忘记密码");
        setLeftTv("返回");
        assignViews();
    }

    @OnClick({R.id.btnCode, R.id.btnSure})
    public void toClick(View v) {
        switch (v.getId()) {
            //获取验证码
            case R.id.btnCode:
                name = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
                    return;
                }
                if (name.length() != 11) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    return;
                }
                i = 60;
                btnCode.setEnabled(false);
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
                String mobile = etPhone.getText().toString().trim();
                break;
            //确定
            case R.id.btnSure:
                finish();
                break;
        }
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
                btnCode.setEnabled(true);
                btnCode.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                btnCode.setText(msg.what + "秒");
            }
        }

    };
}
