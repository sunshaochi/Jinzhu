package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by gxy on 2016/1/14.
 */
public class CashExchange extends BaseActivity {

    @ViewInject(R.id.tvxianjin)
    private TextView tvxianjin;//兑换现金
    @ViewInject(R.id.tvxianjinfen)
    private TextView tvxianjinfen;//可兑换现金利息分
    @ViewInject(R.id.tvgetxianjin)
    private TextView tvgetxianjin;//可兑换现金
    @ViewInject(R.id.name)
    private EditText name;//用户姓名
    @ViewInject(R.id.bankName)
    private EditText bankName;//银行名称
    @ViewInject(R.id.bankCount)
    private EditText bankCount;//银行卡号
    @ViewInject(R.id.lldiqu)
    private LinearLayout lldiqu;//选择地区
    @ViewInject(R.id.tvdiqu)
    private TextView tvdiqu;//显示地区
    @ViewInject(R.id.moneyPassword)
    private EditText zjPassword;//资金密码
    @ViewInject(R.id.btn_ok)
    private Button btnOk;//确认
    @ViewInject(R.id.tvset)
    private TextView tvset;//设置资金密码
    private RelativeLayout rlset;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cash_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("现金兑换");
    }
    @OnClick({R.id.btn_ok,R.id.lldiqu,R.id.rlset})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_ok:
                intent=new Intent(CashExchange.this,OrderCommitSusAct.class);
                startActivity(intent);
                break;
            case R.id.lldiqu:
                DialogChooseProvince dialogChooseProvince = new DialogChooseProvince(this).builder();
                dialogChooseProvince.show();
                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseProvince.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvdiqu.setText(adress);
                    }
                });
                break;
            case R.id.rlset:
                intent=new Intent(CashExchange.this,SetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }
}
