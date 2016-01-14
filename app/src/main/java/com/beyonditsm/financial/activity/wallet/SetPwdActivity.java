package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 设置资金密码
 * Created by wangbin on 16/1/14.
 */
public class SetPwdActivity extends BaseActivity {
    private TextView tvAmount;//账户名
    private EditText etAPwd;//账户密码
    private EditText etCapitalPwd;//资金密码
    private EditText etSurePwd;//确认资金密码
    private TextView tvSure;//确认

    private String amount,apwd,capPwd,surePwd;

    private void assignViews() {
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        etAPwd = (EditText) findViewById(R.id.etAPwd);
        etCapitalPwd = (EditText) findViewById(R.id.etCapitalPwd);
        etSurePwd = (EditText) findViewById(R.id.etSurePwd);
        tvSure = (TextView) findViewById(R.id.tvSure);
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
    }

    @OnClick(R.id.tvSure)
    public void onClick(){
        if(isValidate()){
            finish();
        }
    }

    /**
     * 是否为空
     * @return
     */
    private boolean isValidate(){
//        amount=tvAmount.getText().toString().trim();
        apwd=etAPwd.getText().toString().trim();
        capPwd=etCapitalPwd.getText().toString().trim();
        surePwd=etSurePwd.getText().toString().trim();

        if(TextUtils.isEmpty(apwd)){
            MyToastUtils.showShortToast(getApplicationContext(),"请输入账户密码");
            etAPwd.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(capPwd)){
            MyToastUtils.showShortToast(getApplicationContext(),"请输入资金密码");
            return false;
        }
        if(TextUtils.isEmpty(surePwd)){
            MyToastUtils.showShortToast(getApplicationContext(),"请确认资金密码");
            return false;
        }
        return true;
    }
}
