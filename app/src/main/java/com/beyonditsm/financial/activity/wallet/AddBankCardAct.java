package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AddBankCardEntity;
import com.beyonditsm.financial.entity.BankListEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加银行卡
 * Created by Administrator on 2016/3/15.
 */
public class AddBankCardAct extends BaseActivity {
    @ViewInject(R.id.et_cardName)
    private TextView etAccountName;
    @ViewInject(R.id.et_cardNo)
    private EditText etCardNo;
    @ViewInject(R.id.et_bankName)
    private TextView tvBankName;
    @ViewInject(R.id.et_branchName)
    private EditText etBranchName;
    @ViewInject(R.id.et_fundPassword)
    private EditText etFundPassword;
    @ViewInject(R.id.tv_setzjpassword)
    private TextView tvSetPassword;
    @ViewInject(R.id.tv_sureAdd)
    private TextView tvSureAdd;
    private ArrayAdapter<String> adapter;
    private UserEntity user;//用户实体
    private List<BankListEntity> bankList;
    private int bankNamePos;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_addbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加银行卡");
        setLeftTv("返回");
        user = getIntent().getParcelableExtra("userinfo");
        getBankList();
        bankList = new ArrayList<>();
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
                Intent intent = new Intent(AddBankCardAct.this, SetPwdActivity.class);
                intent.putExtra("userPhone", user.getAccountName());
                startActivity(intent);
            }
        });
        tvBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySelfSheetDialog dialog = new MySelfSheetDialog(AddBankCardAct.this).builder();
                if (bankList.size() != 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        dialog.addSheetItem(bankList.get(i).getBankName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvBankName.setText(bankList.get(which - 1).getBankName());
                                bankNamePos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();

            }
        });
        etAccountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!FinancialUtil.isInputChinese(etAccountName.getText().toString())) {
                    etAccountName.setError("开户姓名必须为中文！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*获取支持的银行列表*/
    private void getBankList() {
        RequestManager.getWalletManager().getBank(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                bankList = gson.fromJson(dataArr.toString(), new TypeToken<List<BankListEntity>>() {
                }.getType());
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    private boolean isInputEmpty() {
        if (!FinancialUtil.isInputChinese(etAccountName.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "开户姓名必须为中文！");
            return false;
        }
        if (TextUtils.isEmpty(etAccountName.getText().toString())) {
            MyToastUtils.showShortToast(AddBankCardAct.this, "请填写开户姓名");
            return false;
        }
        if (TextUtils.isEmpty(etCardNo.getText().toString())) {
            MyToastUtils.showShortToast(AddBankCardAct.this, "请填写银行卡号");
            return false;
        }
        if (TextUtils.isEmpty(tvBankName.getText().toString())) {
            MyToastUtils.showShortToast(AddBankCardAct.this, "请选择支持银行");
            return false;
        }
        if (TextUtils.isEmpty(etBranchName.getText().toString())){
            MyToastUtils.showShortToast(AddBankCardAct.this, "请填写支行名称");
            return false;
        }
        if (TextUtils.isEmpty(etFundPassword.getText().toString())) {
            MyToastUtils.showShortToast(AddBankCardAct.this, "请填写资金密码");
            return false;
        }
        if (!FinancialUtil.checkBankCard(etCardNo.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的银行卡号");
            etCardNo.requestFocus();
            return false;
        }

        return true;
    }

    private void addBankCard() {
        AddBankCardEntity abce = new AddBankCardEntity();
        abce.setAccountName(etAccountName.getText().toString().trim());
        abce.setCardNo(etCardNo.getText().toString().trim());
        abce.setBankName(bankList.get(bankNamePos).getBankName());
        abce.setBranchBankName(etBranchName.getText().toString().trim());
        abce.setFundPassword(etFundPassword.getText().toString().trim());
        RequestManager.getWalletManager().addBankCard(abce, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                Intent intent = new Intent(BindBankCardAct.ADDBANKCARD);
                sendBroadcast(intent);
                MyToastUtils.showShortToast(AddBankCardAct.this, "已添加银行卡");
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(AddBankCardAct.this, msg);
            }
        });
    }
}
