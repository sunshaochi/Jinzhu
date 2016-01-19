package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * Created by gxy on 2016/1/14.
 */
public class InterestDeduction extends BaseActivity {
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;//抵扣券
    @ViewInject(R.id.tv_creName)
    private TextView tvcreName;//贷款产品按钮
    @ViewInject(R.id.creName)
    private TextView creName;//贷款产品
    @ViewInject(R.id.deductionAll)
    private TextView deductionAll;//总贷款利息
    @ViewInject(R.id.deductionDebit)
    private TextView deductionDebit;//可扣款利息
    @ViewInject(R.id.tvlixifen)
    private EditText tvlixifen;//可扣款利息分
    @ViewInject(R.id.tvlixixianjin)
    private TextView tvlixixianjin;//可扣款利息现金
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

    private OrderBean orderBean;//订单实体
    private UserEntity user;//用户实体
    @Override
    public void setLayout() {
        setContentView(R.layout.act_interest_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(InterestDeduction.this);
        setLeftTv("返回");
        setTopTitle("抵扣利息");

        user=getIntent().getParcelableExtra("userInfo");
        if(user!=null){
            if(!TextUtils.isEmpty(user.getDeductionTicketAmount())){
                tvDikouMoney.setText(user.getDeductionTicketAmount());
//                tvlixifen.setText(user.getDeductionTicketAmount());
//                tvlixixianjin.setText(Double.parseDouble(user.getDeductionTicketAmount())/10+"");
            }
        }
        getOrderNoList();
        tvlixifen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith(".")) {

                    if (s.toString().trim().length() == 0) {
                        tvlixixianjin.setText("");
                    }
                    if (!TextUtils.isEmpty(tvlixifen.getText().toString().trim())) {
                        tvlixixianjin.setText(Double.parseDouble(s.toString()) / 100 + "");

                    }

                } else if (s.toString().startsWith(".")) {
                    Toast.makeText(InterestDeduction.this, "不能以小数点开头", Toast.LENGTH_SHORT).show();
                    tvlixifen.setText("");
                }
            }
        });
    }
    @OnClick({R.id.lldk,R.id.btn_ok,R.id.lldiqu,R.id.tvset,R.id.tv_creName})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.lldk:
                MySelfSheetDialog dialog=new MySelfSheetDialog(InterestDeduction.this).builder();
                dialog.addSheetItem("", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        creName.setText("");
                    }
                });
                dialog.show();
                break;
            //确认
            case R.id.btn_ok:
                setOrderBean();
                double d=0.0;
                if(tvlixifen.getText().toString().trim().length()==0){
                    d=0.0;
                }else {
                    d=Double.parseDouble(tvlixifen.getText().toString());
                }
                    if (d <= Double.parseDouble(user.getDeductionTicketAmount())) {

                        if (orderBean != null && !TextUtils.isEmpty(zjPassword.getText().toString())
                                && !TextUtils.isEmpty(orderBean.getUserName())
                                && !TextUtils.isEmpty(orderBean.getBankName())
                                && !TextUtils.isEmpty(orderBean.getBankCardNo())
                                && !TextUtils.isEmpty(orderBean.getCashOutAmount()+"")
                                ) {
                            RequestManager.getWalletManager().submitDeductionTOrder(orderBean, zjPassword.getText().toString(), new RequestManager.CallBack() {
                                @Override
                                public void onSucess(String result) throws JSONException {
                                    Intent intent = new Intent(InterestDeduction.this, OrderCommitSusAct.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(int status, String msg) {

                                }
                            });
                        } else {
                            Toast.makeText(InterestDeduction.this, "请检查您的输入是否有误", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(InterestDeduction.this,"您的抵扣券没有这么多哦",Toast.LENGTH_SHORT).show();
                        tvlixifen.requestFocus();
                    }
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
            //设置资金密码
            case R.id.tvset:
                intent=new Intent(InterestDeduction.this,SetPwdActivity.class);
                intent.putExtra("userPhone",user.getAccountName());
                startActivity(intent);
                break;
            case R.id.tv_creName:

                break;
        }
    }

    /**
     * 给订单实体赋值
     */
    private void setOrderBean(){
        orderBean=new OrderBean();
        if(!TextUtils.isEmpty(name.getText().toString())){
            orderBean.setUserName(name.getText().toString());
        }
        if(!TextUtils.isEmpty(bankName.getText().toString())){
            orderBean.setBankName(bankName.getText().toString());
        }
        if(!TextUtils.isEmpty(bankCount.getText().toString())){
            orderBean.setBankCardNo(bankCount.getText().toString());
        }
        if(!TextUtils.isEmpty(tvlixixianjin.getText().toString())){
            orderBean.setCashOutAmount(Double.parseDouble(tvlixixianjin.getText().toString()));
        }else {
            orderBean.setCashOutAmount(0.0);
        }
    }

    /**
     * 获取个人贷款订单编号列表
     */
    private void getOrderNoList(){
        RequestManager.getWalletManager().findOrderNoListByUserName(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyLogUtils.degug(result);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

}
