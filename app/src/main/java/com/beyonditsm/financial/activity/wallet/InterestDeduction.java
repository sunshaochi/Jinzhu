package com.beyonditsm.financial.activity.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.QueryBankCardEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 抵扣券
 * Created by gxy on 2016/1/14
 */
@SuppressLint("SetTextI18n")
public class InterestDeduction extends BaseActivity {
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;//抵扣券
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
    @ViewInject(R.id.depositBank)
    private EditText depositBank;//支行名称
    @ViewInject(R.id.tvBankCount)
    private TextView tvBankCount;//选择银行卡
    @ViewInject(R.id.tvdiqu)
    private TextView tvdiqu;//显示地区
    @ViewInject(R.id.moneyPassword)
    private EditText zjPassword;//资金密码
    @ViewInject(R.id.tv_minPayment)
    private TextView tvMinPayment;//最小兑换金额

    private OrderBean orderBean,orderBeanLixi;//订单实体
    private UserEntity user;//用户实体

    private List<OrderBean> list;
    private int position;//选择订单号的位置

    private double MIN_MARK = 0.0;
    private double MAX_MARK = 0.0;
    private List<QueryBankCardEntity> bindList;
    private int minPayment;

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
//            if (!TextUtils.isEmpty(user.getUserName())){
//                name.setText(user.getUserName());
//                name.setEnabled(false);
//            } else {
//                user.setUserName(name.getText().toString().trim());
//            }
            if(!TextUtils.isEmpty(user.getDeductionTicketAmount())){
                double dCashA=Double.valueOf(user.getDeductionTicketAmount());
                tvDikouMoney.setText((long)dCashA+"");
                MAX_MARK=Double.parseDouble(user.getDeductionTicketAmount());

            }
        }
        getOrderNoList();
        findBankCard();
        getMinExchange();

        setListener();
        if (!TextUtils.isEmpty(bankName.getText())&&!TextUtils.isEmpty(bankCount.getText())&&!TextUtils.isEmpty(name.getText())){
            bankCount.setEnabled(false);
            bankName.setEnabled(false);
            name.setEnabled(false);
        }
    }

    private void setListener() {
        tvlixifen.addTextChangedListener(new TextWatcher() {
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
                            double dMAX = Double.valueOf(s.toString());
                            tvlixifen.setText((long) dMAX + "");
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            double dMIN = Double.valueOf(s.toString());
                            tvlixifen.setText((long) dMIN + "");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvlixixianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvlixifen.getText().toString().trim())) {
//                                tvlixixianjin.setText(Double.parseDouble(s.toString()) / 100 + "");
                                tvlixixianjin.setText(Integer.parseInt(s.toString()) + "");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !"".equals(s.toString())) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal;
                        try {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            Toast.makeText(getBaseContext(), "不能超过最大可抵扣数字", Toast.LENGTH_SHORT).show();
                            double dMAX = MAX_MARK;
                            tvlixifen.setText((long) dMAX + "");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvlixixianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvlixifen.getText().toString().trim())) {
//                                tvlixixianjin.setText(Double.parseDouble(s.toString()) / 100 + "");
                                tvlixixianjin.setText(Integer.parseInt(s.toString()) + "");

                            }
                        }
                    }
                }
              /*  if (!s.toString().startsWith(".")) {

                    if (s.toString().trim().length() == 0) {
                        tvlixixianjin.setText("");
                    }
                    if (!TextUtils.isEmpty(tvlixifen.getText().toString().trim())) {
                        tvlixixianjin.setText(Double.parseDouble(s.toString()) / 100 + "");

                    }

                } else if (s.toString().startsWith(".")) {
                    Toast.makeText(InterestDeduction.this, "不能以小数点开头", Toast.LENGTH_SHORT).show();
                    tvlixifen.setText("");
                }*/
            }
        });
        tvlixifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvlixifen.setCursorVisible(true);
                tvlixifen.requestFocus();
            }
        });
        tvBankCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindList != null) {
                    MySelfSheetDialog dialog = new MySelfSheetDialog(InterestDeduction.this).builder();
                    for (int i = 0; i < bindList.size(); i++) {
                        dialog.addSheetItem(bindList.get(i).getCardNo(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                bankName.setText(bindList.get(which - 1).getBankName());
                                bankCount.setText(bindList.get(which - 1).getCardNo());
                                name.setText(bindList.get(which - 1).getAccountName());
                                depositBank.setText(bindList.get(which - 1).getBranchBankName());
                                bankCount.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                                bankName.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                                name.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                                depositBank.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                            }
                        });
                    }
                    dialog.show();
                }
            }
        });
    }

    @OnClick({R.id.lldk,R.id.btn_ok,R.id.lldiqu,R.id.tvset,R.id.tv_creName})
    public void toClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.lldk:
                if(list.size()!=0) {
                    MySelfSheetDialog dialog = new MySelfSheetDialog(InterestDeduction.this).builder();
                    for(int i=0;i<list.size();i++) {
                        dialog.addSheetItem(list.get(i).getOrderNo(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                creName.setText(list.get(which-1).getOrderNo());
                                position=which-1;
                                getLiXiByOrder(creName.getText().toString());
                            }
                        });
                    }
                    dialog.show();
                }
                break;
            //确认
            case R.id.btn_ok:
                if(isValidate()){
                    setOrderBean();
                        if (!TextUtils.isEmpty(zjPassword.getText().toString().trim())) {
                            RequestManager.getWalletManager().submitDeductionTOrder(orderBean, zjPassword.getText().toString(), new RequestManager.CallBack() {
                                @Override
                                public void onSucess(String result) throws JSONException {
                                    Intent intent = new Intent(InterestDeduction.this, OrderCommitSusAct.class);
                                    startActivity(intent);
                                    MyLogUtils.degug(orderBean.getUserName() + ">" + orderBean.getBankName() + ">" + orderBean.getBankCardNo()
                                            + ">" + orderBean.getCashOutAmount() + ">" + orderBean.getOrderNo() + ">" + zjPassword.getText().toString());

                                }

                                @Override
                                public void onError(int status, String msg) {
                                    Toast.makeText(InterestDeduction.this, msg, Toast.LENGTH_SHORT).show();
                                    MyLogUtils.degug(orderBean.getUserName() + ">" + orderBean.getBankName() + ">" + orderBean.getBankCardNo()
                                            + ">" + orderBean.getCashOutAmount() + ">" + orderBean.getOrderNo() + ">" + zjPassword.getText().toString());

                                }
                            });
                        } else {
                            Toast.makeText(InterestDeduction.this, "请输入资金密码", Toast.LENGTH_SHORT).show();
                            zjPassword.requestFocus();
                        }

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
            if(FinancialUtil.checkBankCard(bankCount.getText().toString())) {
                orderBean.setBankCardNo(bankCount.getText().toString());
            }else {
                bankCount.setText("");
                Toast.makeText(InterestDeduction.this,"请输入正确的银行卡号",Toast.LENGTH_SHORT).show();
                bankCount.requestFocus();
            }
        }
        if (!TextUtils.isEmpty(depositBank.getText().toString())){
            orderBean.setDepositBank(depositBank.getText().toString());
        }
        if(!TextUtils.isEmpty(tvlixixianjin.getText().toString())){
            orderBean.setCashOutAmount(Double.parseDouble(tvlixixianjin.getText().toString()));
        }
        if(!TextUtils.isEmpty(creName.getText().toString())){
            orderBean.setOrderNo(creName.getText().toString());
            orderBean.setOrderId(list.get(position).getOrderId());
        }
    }

    private boolean isValidate(){
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(InterestDeduction.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(bankName.getText().toString())){
            Toast.makeText(InterestDeduction.this, "请输入银行名称", Toast.LENGTH_SHORT).show();
            bankName.requestFocus();
            return false;
        }
        else if(!TextUtils.isEmpty(bankCount.getText().toString())&&(!FinancialUtil.checkBankCard(bankCount.getText().toString()))){
            Toast.makeText(InterestDeduction.this,"请输入正确的银行卡号",Toast.LENGTH_SHORT).show();
            bankCount.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(bankCount.getText().toString())){
            Toast.makeText(InterestDeduction.this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
            bankCount.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(depositBank.getText().toString())){
            MyToastUtils.showShortToast(getApplicationContext(),"请填写支行名称");
            depositBank.requestFocus();
            return false;
        } else if(TextUtils.isEmpty(tvlixixianjin.getText().toString())){
            Toast.makeText(InterestDeduction.this, "未输入抵扣金额", Toast.LENGTH_SHORT).show();
            tvlixifen.requestFocus();

            return false;
        }
        else if(!TextUtils.isEmpty(tvlixixianjin.getText().toString())&&((Double.parseDouble(tvlixixianjin.getText().toString())==0))){
            Toast.makeText(InterestDeduction.this,"输入的抵扣金额无效",Toast.LENGTH_SHORT).show();
            tvlixifen.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(creName.getText().toString())){
            Toast.makeText(InterestDeduction.this,"贷款产品不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!TextUtils.isEmpty(tvlixixianjin.getText().toString())&&orderBeanLixi==null){
            Toast.makeText(InterestDeduction.this, "请查看贷款产品及利息相关信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!TextUtils.isEmpty(tvlixixianjin.getText().toString())&&orderBeanLixi!=null){
            if (!(Double.parseDouble(tvlixifen.getText().toString()) <= Double.parseDouble(orderBeanLixi.getDeductibleInterest()))) {
                Toast.makeText(InterestDeduction.this, "抵扣利息金额超过最大可抵扣利息", Toast.LENGTH_SHORT).show();
                tvlixifen.setText("");
                tvlixifen.requestFocus();
                return false;
            }
        }else if (Integer.parseInt(tvlixifen.getText().toString())<minPayment){
            MyToastUtils.showShortToast(InterestDeduction.this,"申请兑换金额需大于"+minPayment+"元");
            tvlixifen.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 获取个人贷款订单编号列表
     */
    private void getOrderNoList(){
        RequestManager.getWalletManager().findOrderNoListByUserName(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                list=new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                list = gson.fromJson(dataArr.toString(), new TypeToken<List<OrderBean>>() {
                }.getType());


            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void getLiXiByOrder(String order){
        RequestManager.getWalletManager().findInterestByOrderNo(order, new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<OrderBean> rd = (ResultData<OrderBean>) GsonUtils.json(result, OrderBean.class);
                orderBeanLixi = rd.getData();
                if (orderBeanLixi != null) {
                    if (!TextUtils.isEmpty(orderBeanLixi.getTotalLoanInterest())) {
                        deductionAll.setText(orderBeanLixi.getTotalLoanInterest() + "元");
                    } else {
                        deductionAll.setText("0.0元");
                    }
                    if (!TextUtils.isEmpty(orderBeanLixi.getDeductibleInterest())) {
                        deductionDebit.setText(orderBeanLixi.getDeductibleInterest() + "元");
                    } else {
                        deductionDebit.setText("0.0元");
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*查询绑定银行卡*/
    private void findBankCard() {
        RequestManager.getWalletManager().findBankCard(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONArray data = object.getJSONArray("data");
                Gson gson = new Gson();
                bindList = gson.fromJson(data.toString(), new TypeToken<List<QueryBankCardEntity>>() {
                }.getType());

                if (bindList != null) {
                    for (int i = 0; i < bindList.size(); i++) {
                        int status = bindList.get(i).getStatus();
                        if (status == 2) {
                            if (!TextUtils.isEmpty(bindList.get(i).getBankName())) {
                                bankName.setText(bindList.get(i).getBankName());
                                bankName.setEnabled(false);
                                bankName.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getCardNo())) {
                                bankCount.setText(bindList.get(i).getCardNo());
                                bankCount.setEnabled(false);
                                bankCount.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getAccountName())) {
                                name.setText(bindList.get(i).getAccountName());
                                name.setEnabled(false);
                                name.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getBranchBankName())) {
                                depositBank.setText(bindList.get(i).getBranchBankName());
                                depositBank.setEnabled(false);
                                depositBank.setTextColor(ContextCompat.getColor(InterestDeduction.this,R.color.tv_primary_color));
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(InterestDeduction.this, msg);
            }
        });
    }

    /**
     * 获取最小兑换金额
     */
    private void getMinExchange(){
        RequestManager.getCommManager().getMinExchange(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                minPayment = data.getInt("minPayment");
                tvMinPayment.setText("申请兑换金额需大于"+minPayment+"元");
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
