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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.ValidateImageView;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;

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
    LinearLayout llSmsPic;
    @ViewInject(R.id.sms_code)
    EditText SMSCode;
    public static final String PHONENUM = "phone";
    public static final String CAPTCHA = "captcha";
    private String name;
    private ValidateImageView view = null;
    String[] responseArray = null;

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(false) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象


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


    //为数组赋值1~9的随机数
    private String[] getRandomInteger() {
        String[] reuestArray = new String[4];
        for (int i = 0; i < 4; i++) {
            reuestArray[i] = String.valueOf((int) (Math.random() * 9 + 1));
        }
        return reuestArray;
    }

    //获取返回的数组
    private String getResponseStr(String[] response) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : response) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    private void getSMSPic() {
//
        view = new ValidateImageView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llSmsPic.addView(view);
        responseArray = view.getValidataAndSetImage(getRandomInteger());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseArray = view.getValidataAndSetImage(getRandomInteger());
            }
        });

//        CommManager.getCommManager().imageCaptcha(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });

//        ivSmsPic.setImageBitmap(Code.getInstance().createBitmap());
    }
//        ivSmsPic.setImageResource(R.mipmap.pro_default);
//        ImageLoader.getInstance().displayImage(IFinancialUrl.IMAGE_CAPTCHA,ivSmsPic,options);    }

    private void setView() {
        gettv = (TextView) findViewById(R.id.gettv);
        findetphone = (ClearEditText) findViewById(R.id.findetphone);
        findetpwd = (EditText) findViewById(R.id.findetpwd);
    }

    @OnClick({R.id.gettv, R.id.nexttv, R.id.iv_sms_pic})
    public void toClick(View v) {
        switch (v.getId()) {
            //获取验证码
//            case R.id.iv_sms_pic:
//                getSMSPic();
//                break;

            case R.id.gettv:
                if (isPhoneNum()) {
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
                checkSms(name,authcode);
                MyLogUtils.info(SMSCode.getText().toString() + "sss" + getResponseStr(responseArray));

//                if ((SMSCode.getText().toString() + "").trim().toLowerCase().equals(getResponseStr(responseArray).toLowerCase().trim())) {
//                    Intent intent = new Intent(FindPwdAct.this, UpdatePwdAct.class);
//                    intent.putExtra(PHONENUM, name);
//                    intent.putExtra(CAPTCHA, authcode);
//                    startActivity(intent);
//
//                } else {
//                    MyToastUtils.showShortToast(getApplicationContext(), "图形校验码错误");
//                }

//                SMSCaptchaFogetPassword(name,SMSCode.getText().toString()+"");


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

    /**
     * 校验短信验证码
     * @param phoneNum
     * @param capch
     */
    private void checkSms(final String phoneNum, final String capch){
        RequestManager.getCommManager().fogetJyPwd(phoneNum, capch, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                Intent intent = new Intent(FindPwdAct.this, UpdatePwdAct.class);
                intent.putExtra(PHONENUM, phoneNum);
                intent.putExtra(CAPTCHA, capch);
                startActivity(intent);
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(),msg );
            }
        });
    }
}



