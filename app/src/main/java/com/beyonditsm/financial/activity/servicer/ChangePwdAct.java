package com.beyonditsm.financial.activity.servicer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ChangePwdEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 代言人修改密码
 * Created by Yang on 2015/11/15 0015.
 */
public class ChangePwdAct extends BaseActivity {
    private EditText oldPwd;
    private EditText newPwd;
    private EditText newPwd2;

    private void assignViews() {
        oldPwd = (EditText) findViewById(R.id.old_pwd);
        newPwd = (EditText) findViewById(R.id.new_pwd);
        newPwd2 = (EditText) findViewById(R.id.new_pwd2);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.service_changepwdact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("修改密码");
        setLeftTv("返回");
        assignViews();
    }

    @OnClick(R.id.commit_btn)
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.commit_btn:
                changepwd();
                break;
        }
    }

    private void changepwd() {
        String oldpwd = oldPwd.getText().toString();
        String newpwd = newPwd.getText().toString();
        String newpwd2 = newPwd2.getText().toString();
        if (TextUtils.isEmpty(oldpwd)) {
            MyToastUtils.showShortToast(this, "请输入原密码");
        } else if (TextUtils.isEmpty(newpwd)) {
            MyToastUtils.showShortToast(this, "请输入新密码");
        } else if (TextUtils.isEmpty(newpwd2)) {
            MyToastUtils.showShortToast(this, "请确认新密码");
        } else if (!newpwd.equals(newpwd2)) {
            MyToastUtils.showShortToast(this, "两次输入密码不一致");
        } else {
            ChangePwdEntity changePwdEntity = new ChangePwdEntity();
            changePwdEntity.setPassword(oldpwd);
            changePwdEntity.setNewPassword(newpwd);
            RequestManager.getCommManager().toChangePwd(changePwdEntity, new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) {
                    MyToastUtils.showShortToast(ChangePwdAct.this, "密码修改成功");
                    finish();
                }

                @Override
                public void onError(int status,String msg) {
                    MyToastUtils.showShortToast(ChangePwdAct.this, msg);
                }
            });
        }
    }
}
