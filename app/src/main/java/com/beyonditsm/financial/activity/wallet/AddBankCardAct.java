package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AddBankCardEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加银行卡
 * Created by Administrator on 2016/3/15.
 */
public class AddBankCardAct  extends BaseActivity{
    @ViewInject(R.id.et_cardName)
    private TextView etAccountName;
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
    @ViewInject(R.id.sp_bankList)
    private Spinner spBankList;
    private ArrayAdapter<String> adapter;
    private UserEntity user;//用户实体

    @Override
    public void setLayout() {
        setContentView(R.layout.act_addbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加银行卡");
        setLeftTv("返回");
        user=getIntent().getParcelableExtra("userinfo");
//        getBankList();
        tvSureAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputEmpty()) {
                    addBankCard();
                }
            }
        });
        tvSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBankCardAct.this,SetPwdActivity.class);
                intent.putExtra("userPhone",user.getAccountName());
                startActivity(intent);
            }
        });
    }

    /*获取支持的银行列表*/
    private void getBankList() {
        RequestManager.getWalletManager().getBank(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private boolean isInputEmpty(){
        if (TextUtils.isEmpty(etAccountName.getText().toString())){
            MyToastUtils.showShortToast(AddBankCardAct.this,"请输入开户姓名");
            return  false;
        }
        if (TextUtils.isEmpty(etCardNo.getText().toString())){
            MyToastUtils.showShortToast(AddBankCardAct.this,"请输入银行卡号");
            return  false;
        }
        if (TextUtils.isEmpty(etBankName.getText().toString())){
            MyToastUtils.showShortToast(AddBankCardAct.this,"请输入银行名称");
            return  false;
        }
        if (TextUtils.isEmpty(etFundPassword.getText().toString())){
            MyToastUtils.showShortToast(AddBankCardAct.this,"请输入资金密码");
            return  false;
        }
        return true;
    }
    private void addBankCard(){
        AddBankCardEntity abce = new AddBankCardEntity();
        abce.setAccountName(etAccountName.getText().toString().trim());
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
                MyToastUtils.showShortToast(AddBankCardAct.this,msg);
            }
        });
    }
}
