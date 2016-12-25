package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 设置资金密码
 * Created by wangbin on 16/1/14.
 */
public class SetPwdActivity extends BaseActivity {
    private TextView tvAmount;//账户名
    private EditText etAPwd;//账户密码
    private EditText etCapitalPwd;//资金密码
    private EditText etSurePwd;//确认资金密码

    private String amount;
    private String apwd;
    private String capPwd;

    private void assignViews() {
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        etAPwd = (EditText) findViewById(R.id.etAPwd);
        etCapitalPwd = (EditText) findViewById(R.id.etCapitalPwd);
        etSurePwd = (EditText) findViewById(R.id.etSurePwd);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.act_setpwd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("设置资金密码");
        setLeftTv("返回");
        assignViews();
        amount= SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext());
        tvAmount.setText(amount);
    }

    @OnClick(R.id.tvSure)
    public void onClick(View view){
        if(isValidate()){
            setFunPwd(apwd,capPwd);
        }
    }

    /**
     * 是否为空
     */
    private boolean isValidate(){
        amount=tvAmount.getText().toString().trim();
        apwd=etAPwd.getText().toString().trim();
        capPwd=etCapitalPwd.getText().toString().trim();
        String surePwd = etSurePwd.getText().toString().trim();

        if(TextUtils.isEmpty(apwd)){
            MyToastUtils.showShortToast(getApplicationContext(),"请输入账户密码");
            etAPwd.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(capPwd)){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入资金密码");
            return false;
        }
        if(TextUtils.isEmpty(surePwd)){
            MyToastUtils.showShortToast(getApplicationContext(), "请确认资金密码");
            return false;
        }
        if(!capPwd.equals(surePwd)){
            MyToastUtils.showShortToast(getApplicationContext(), "两次输入的资金密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 设置密码
     * @param userPassword 用户密码
     * @param fundPassword 资金密码
     */
    private void setFunPwd(final String userPassword,final String fundPassword){

        RequestManager.getWalletManager().setFunPwd(userPassword, fundPassword, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(),"设置成功");
                finish();
            }

            @Override
            public void onError(int status, String msg) {
               MyToastUtils.showShortToast(getApplicationContext(),msg);
            }
        });
    }
}
