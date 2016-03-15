package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AddBankCardEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 添加银行卡
 * Created by Administrator on 2016/3/15.
 */
public class AddBankCardAct  extends BaseActivity{
    @ViewInject(R.id.et_cardNo)
    private EditText etCardNo;
    @ViewInject(R.id.et_bankName)
    private EditText etBankName;
    @ViewInject(R.id.et_branchName)
    private EditText etBranchName;
    @ViewInject(R.id.et_fundPassword)
    private EditText etFundPassword;
    @ViewInject(R.id.tv_setzjpassword)
    private TextView tvSetPassword;
    @ViewInject(R.id.tv_sureAdd)
    private TextView tvSureAdd;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_addbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加银行卡");
        tvSureAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBankCard();
            }
        });
    }

    private void addBankCard(){
        AddBankCardEntity abce = new AddBankCardEntity();
        abce.setCardNo(etCardNo.getText().toString().trim());
        abce.setBankName(etBankName.getText().toString().trim());
        abce.setBranchBankName(etBranchName.getText().toString().trim());
        abce.setFundPassword(etFundPassword.getText().toString().trim());
        RequestManager.getWalletManager().addBankCard(abce, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                Intent intent  = new Intent(BindBankCardAct.ADDBANKCARD);
                sendBroadcast(intent);
                MyToastUtils.showShortToast(AddBankCardAct.this, "已添加银行卡");
                finish();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
