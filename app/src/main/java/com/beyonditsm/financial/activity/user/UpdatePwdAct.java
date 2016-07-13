package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 修改密码
 * Created by wangbin on 15/11/12.
 */
public class UpdatePwdAct extends BaseActivity {
    private ClearEditText comfirmPwd1, comfirmPwd2;

    private String phone, code;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_updatapw);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("重置密码");
        setLeftTv("返回");
        setView();
        phone = getIntent().getStringExtra(FindPwdAct.PHONENUM);
        code = getIntent().getStringExtra(FindPwdAct.CAPTCHA);
    }

    private void setView() {
        comfirmPwd1 = (ClearEditText) findViewById(R.id.comfirmPwd1);
        comfirmPwd2 = (ClearEditText) findViewById(R.id.comfirmPwd2);
    }

    @OnClick(R.id.comfirmtv)
    public void toClick(View v) {
        switch (v.getId()) {
            case R.id.comfirmtv:
                String pwd1 = comfirmPwd1.getText().toString();
                String pwd2 = comfirmPwd2.getText().toString();
                if (TextUtils.isEmpty(pwd1)) {
                    MyToastUtils.showLongToast(getApplicationContext(), "输入新密码");
                    return;
                }
                if (pwd1.length() > 20 || pwd1.length() < 6) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入6~20个字节");
                    return;
                }
                if (TextUtils.isEmpty(pwd2)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "确认新密码");
                    return;
                }
                if (!(pwd2.equals(pwd1))) {
                    MyToastUtils.showShortToast(getApplicationContext(), "两次输入不一样");
                    return;
                }
                toFindPwd(pwd1);
                break;


        }

    }

    /**
     * 找回密码
     *
     * @param password 密码
     */
    private void toFindPwd(String password) {
        RequestManager.getCommManager().fogetPwd(phone, code, password, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(), "找回密码成功");
                AppManager.getAppManager().finishActivity(FindPwdAct.class);
                finish();
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

}
