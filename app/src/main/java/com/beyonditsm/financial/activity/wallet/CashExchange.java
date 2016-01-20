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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * Created by gxy on 2016/1/14.
 */
public class CashExchange extends BaseActivity {

    @ViewInject(R.id.tvxianjin)
    private TextView tvxianjin;//兑换现金
    @ViewInject(R.id.tvxianjinfen)
    private EditText tvxianjinfen;//可兑换现金利息分
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

    private OrderBean orderBean;//订单实体
    private UserEntity user;//用户实体

    private double MIN_MARK = 0.0;
    private double MAX_MARK = 0.0;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cash_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(CashExchange.this);
        setLeftTv("返回");
        setTopTitle("现金兑换");
        user=getIntent().getParcelableExtra("userInfo");
        if(user!=null){
            if(!TextUtils.isEmpty(user.getCashTicketAmount())){
                double dCashA=Double.valueOf(user.getCashTicketAmount());
                tvxianjin.setText((long)dCashA+"");
                MAX_MARK=Double.parseDouble(user.getCashTicketAmount());
            }
        }
        tvxianjinfen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            double dMAX=Double.valueOf(s.toString());
                            tvxianjinfen.setText((long)dMAX+"");
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            double dMIN=Double.valueOf(s.toString());
                            tvxianjinfen.setText((long)dMIN+"");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvgetxianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvxianjinfen.getText().toString().trim())) {
                                tvgetxianjin.setText(Double.parseDouble(s.toString()) / 100 + "");
                            }
                        }
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal = 0;
                        try {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            Toast.makeText(getBaseContext(), "不能超过指定数字", Toast.LENGTH_SHORT).show();
                            double dMAX=Double.valueOf(MAX_MARK);
                            tvxianjinfen.setText((long)dMAX+"");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvgetxianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvxianjinfen.getText().toString().trim())) {
                                tvgetxianjin.setText(Double.valueOf(s.toString()) / 100 + "");
                            }
                        }
                        return;
                    }
                }
              /*  if (!s.toString().startsWith(".")) {

                    if (s.toString().trim().length() == 0) {
                        tvgetxianjin.setText("");
                    }
                    if (!TextUtils.isEmpty(tvxianjinfen.getText().toString().trim())) {
                        tvgetxianjin.setText(Double.parseDouble(s.toString()) / 100 + "");

                    }

                } else if (s.toString().startsWith(".")) {
                    Toast.makeText(CashExchange.this, "不能以小数点开头", Toast.LENGTH_SHORT).show();
                    tvxianjinfen.setText("");
                }*/


            }
        });
    }
    @OnClick({R.id.btn_ok,R.id.lldiqu,R.id.rlset})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_ok:
                setOrderBean();
                double d=0.0;
                if(tvxianjinfen.getText().toString().trim().length()==0){
                    d=-1;
                }else {
                    d=Double.parseDouble(tvxianjinfen.getText().toString());
                    if(d==0){
                        d=-1;
                    }
                }
                    if ((d!=0)&&(d!=-1)&&(d<=Double.parseDouble(user.getCashTicketAmount()))) {
                        if (orderBean != null
                                && !TextUtils.isEmpty(zjPassword.getText().toString())
                                && !TextUtils.isEmpty(orderBean.getUserName())
                                && !TextUtils.isEmpty(orderBean.getBankName())
                                && !TextUtils.isEmpty(orderBean.getBankCardNo())
                                && !TextUtils.isEmpty(orderBean.getCashOutAmount()+"")) {
                            RequestManager.getWalletManager().submitCashTOrder(orderBean, zjPassword.getText().toString(), new RequestManager.CallBack() {
                                @Override
                                public void onSucess(String result) throws JSONException {
                                    Intent intent = new Intent(CashExchange.this, OrderCommitSusAct.class);
                                    startActivity(intent);
                                    MyLogUtils.degug(orderBean.getUserName() + ">" + orderBean.getBankName() + ">" + orderBean.getBankCardNo()
                                            + ">" + orderBean.getCashOutAmount() + ">" + zjPassword.getText().toString());

                                }

                                @Override
                                public void onError(int status, String msg) {
                                    Toast.makeText(CashExchange.this,msg,Toast.LENGTH_SHORT).show();
                                    MyLogUtils.degug(msg);
                                    MyLogUtils.degug(orderBean.getUserName()+">"+orderBean.getBankName()+">"+orderBean.getBankCardNo()
                                    +">"+orderBean.getCashOutAmount()+">"+zjPassword.getText().toString());
                                }
                            });
                        } else {
                            Toast.makeText(CashExchange.this, "请检查您的输入是否有误", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CashExchange.this,"暂无可兑现的现金券",Toast.LENGTH_SHORT).show();
                        tvxianjinfen.requestFocus();
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
            case R.id.rlset:
                intent=new Intent(CashExchange.this,SetPwdActivity.class);
                intent.putExtra("userPhone",user.getAccountName());
                startActivity(intent);
                break;
        }
    }

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
        if(!TextUtils.isEmpty(tvgetxianjin.getText().toString())){
            orderBean.setCashOutAmount(Double.parseDouble(tvgetxianjin.getText().toString()));
        }
    }
}
