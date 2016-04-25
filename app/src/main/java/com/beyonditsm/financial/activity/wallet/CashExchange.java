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
import com.beyonditsm.financial.entity.QueryBankCardEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
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

import java.util.List;

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
    @ViewInject(R.id.depositBank)
    private EditText depositBank;//支行名称
    @ViewInject(R.id.tvBankCount)
    private TextView tvBankCount;//选择银行卡
    @ViewInject(R.id.tvBankName)
    private TextView tvBankName;//选择银行
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
    private List<QueryBankCardEntity> bindList;
    private int bankNamePos;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cash_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(CashExchange.this);
        setLeftTv("返回");
        setTopTitle("现金兑换");
        user = getIntent().getParcelableExtra("userInfo");
        findBankCard();
        if (user != null) {
//            if (!TextUtils.isEmpty(user.getUserName())){
//                name.setText(user.getUserName());
//                name.setEnabled(false);
//            }else{
//                user.setUserName(name.getText().toString().trim());
//            }
            if (!TextUtils.isEmpty(user.getCashTicketAmount())) {
                double dCashA = Double.valueOf(user.getCashTicketAmount());
                tvxianjin.setText((long) dCashA + "");
                MAX_MARK = Double.parseDouble(user.getCashTicketAmount());
            }
        }
        setListener();
//        if (!TextUtils.isEmpty(bankName.getText()) && !TextUtils.isEmpty(bankCount.getText()) && !TextUtils.isEmpty(name.getText())) {
//            bankCount.setEnabled(false);
//            bankName.setEnabled(false);
//            name.setEnabled(false);
//        }
    }

    private void setListener() {
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
                            double dMAX = Double.valueOf(s.toString());
                            tvxianjinfen.setText((long) dMAX + "");
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            double dMIN = Double.valueOf(s.toString());
                            tvxianjinfen.setText((long) dMIN + "");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvgetxianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvxianjinfen.getText().toString().trim())) {
                                tvgetxianjin.setText(Integer.parseInt(s.toString()) + "");
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
                            Toast.makeText(getBaseContext(), "不能超过最大可兑换数字", Toast.LENGTH_SHORT).show();
                            double dMAX = Double.valueOf(MAX_MARK);
                            tvxianjinfen.setText((long) dMAX + "");
                        } else {
                            if (s.toString().trim().length() == 0) {
                                tvgetxianjin.setText("");
                            }
                            if (!TextUtils.isEmpty(tvxianjinfen.getText().toString().trim())) {
                                tvgetxianjin.setText(Integer.valueOf(s.toString()) + "");
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
        tvxianjinfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvxianjinfen.setCursorVisible(true);
                tvxianjinfen.requestFocus();
            }
        });
        tvBankCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindList != null) {
                    MySelfSheetDialog dialog = new MySelfSheetDialog(CashExchange.this).builder();
                    for (int i = 0; i < bindList.size(); i++) {
                        dialog.addSheetItem(bindList.get(i).getCardNo(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                bankName.setText(bindList.get(which - 1).getBankName());
                                bankCount.setText(bindList.get(which - 1).getCardNo());
                                name.setText(bindList.get(which - 1).getAccountName());
                                depositBank.setText(bindList.get(which - 1).getBranchBankName());
                                bankNamePos = which - 1;
                                bankCount.setTextColor(getResources().getColor(R.color.tv_primary_color));
                                bankName.setTextColor(getResources().getColor(R.color.tv_primary_color));
                                name.setTextColor(getResources().getColor(R.color.tv_primary_color));
                                depositBank.setTextColor(getResources().getColor(R.color.tv_primary_color));
                            }
                        });
                    }
                    dialog.show();
                }
            }
        });
    }

    @OnClick({R.id.btn_ok, R.id.lldiqu, R.id.rlset})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_ok:
                if (isValidate()) {
                    setOrderBean();
                    if (!TextUtils.isEmpty(zjPassword.getText().toString().trim())) {
                        RequestManager.getWalletManager().submitCashTOrder(orderBean, zjPassword.getText().toString(), new RequestManager.CallBack() {
                            @Override
                            public void onSucess(String result) throws JSONException {
                                Intent intent = new Intent(CashExchange.this, OrderCommitSusAct.class);
                                startActivity(intent);
                                MyLogUtils.degug(orderBean.getUserName() + ">" + orderBean.getBankName() + ">" + orderBean.getBankCardNo()
                                        + ">" + orderBean.getCashOutAmount() + ">" + zjPassword.getText().toString() + ">" + orderBean.getDepositBank());

                            }

                            @Override
                            public void onError(int status, String msg) {
                                Toast.makeText(CashExchange.this, msg, Toast.LENGTH_SHORT).show();
                                MyLogUtils.degug(msg);
                                MyLogUtils.degug(orderBean.getUserName() + ">" + orderBean.getBankName() + ">" + orderBean.getBankCardNo()
                                        + ">" + orderBean.getCashOutAmount() + ">" + zjPassword.getText().toString() + ">" + orderBean.getDepositBank());
                            }
                        });
                    } else {
                        Toast.makeText(CashExchange.this, "请输入资金密码", Toast.LENGTH_SHORT).show();
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
            case R.id.rlset:
                intent = new Intent(CashExchange.this, SetPwdActivity.class);
                intent.putExtra("userPhone", user.getAccountName());
                startActivity(intent);
                break;
        }
    }

    private void setOrderBean() {
        orderBean = new OrderBean();

        if (!TextUtils.isEmpty(name.getText().toString())) {
            orderBean.setUserName(name.getText().toString());
        }
        if (!TextUtils.isEmpty(bankName.getText().toString())) {
            orderBean.setBankName(bankName.getText().toString());
        }
        if (!TextUtils.isEmpty(bankCount.getText().toString())) {
            orderBean.setBankCardNo(bankCount.getText().toString());
        }
        if (!TextUtils.isEmpty(depositBank.getText().toString())) {
            orderBean.setDepositBank(depositBank.getText().toString());
        }
        if (!TextUtils.isEmpty(tvgetxianjin.getText().toString())) {
            orderBean.setCashOutAmount(Double.parseDouble(tvgetxianjin.getText().toString()));
        }
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(CashExchange.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(bankName.getText().toString())) {
            Toast.makeText(CashExchange.this, "请输入银行名称", Toast.LENGTH_SHORT).show();
            bankName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(bankCount.getText().toString())) {
            Toast.makeText(CashExchange.this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
            bankCount.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(bankCount.getText().toString()) && (!FinancialUtil.checkBankCard(bankCount.getText().toString()))) {
            Toast.makeText(CashExchange.this, "请输入正确的银行卡号", Toast.LENGTH_SHORT).show();
            bankCount.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(depositBank.getText().toString())) {
            MyToastUtils.showShortToast(CashExchange.this, "请填写支行名称");
            depositBank.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvgetxianjin.getText().toString())) {
            Toast.makeText(CashExchange.this, "未输入兑换金额", Toast.LENGTH_SHORT).show();
            tvxianjinfen.requestFocus();
            return false;
        } else if (!TextUtils.isEmpty(tvxianjinfen.getText().toString()) && ((Double.parseDouble(tvxianjinfen.getText().toString()) == 0))) {
            Toast.makeText(CashExchange.this, "输入的兑换金额无效", Toast.LENGTH_SHORT).show();
            tvxianjinfen.requestFocus();
            return false;
        }

        return true;
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
                                bankName.setTextColor(getResources().getColor(R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getCardNo())) {
                                bankCount.setText(bindList.get(i).getCardNo());
                                bankCount.setEnabled(false);
                                bankCount.setTextColor(getResources().getColor(R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getAccountName())) {
                                name.setText(bindList.get(i).getAccountName());
                                name.setEnabled(false);
                                name.setTextColor(getResources().getColor(R.color.tv_primary_color));
                            }
                            if (!TextUtils.isEmpty(bindList.get(i).getBranchBankName())) {
                                depositBank.setText(bindList.get(i).getBranchBankName());
                                depositBank.setEnabled(false);
                                depositBank.setTextColor(getResources().getColor(R.color.tv_primary_color));
                            }
                        }
                    }
                }

            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(CashExchange.this, msg);
            }
        });
    }
}
