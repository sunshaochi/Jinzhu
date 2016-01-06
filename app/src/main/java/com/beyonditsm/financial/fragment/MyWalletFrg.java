package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.servicer.WithdrawalsAct;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;

import org.json.JSONException;

/**
 * 服务者钱包
 * Created by Yang on 2015/11/13 0013.
 */
public class MyWalletFrg extends BaseFragment implements View.OnClickListener {

    private Intent intent;

    private TextView tvTotal;
    private EditText etMoney;
    private Button txBtn;
    private TextView lookRecord;

    private String money;
    private ImageView ivGame;//游戏
    private ScaleAnimation animation;


    private void assignViews() {
        tvTotal = (TextView) view.findViewById(R.id.tv_total);//总金额
        etMoney = (EditText) view.findViewById(R.id.et_money);//提现输入框
        ivGame= (ImageView) view.findViewById(R.id.ivGame);
        animation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF,0.8f);
        animation.setDuration(1000);
//        animation.setInterpolator(new CycleInterpolator(1));
        //设置输入框监听，可输入小数点，小数点后只能输入两位数
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etMoney.setCursorVisible(true);
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        etMoney.setText(s);
                        etMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    etMoney.setText(s);
                    etMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etMoney.setText(s.subSequence(0, 1));
                        etMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handler.sendEmptyMessage(0);
                        Thread.sleep(800);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        txBtn = (Button) view.findViewById(R.id.tx_btn);//提现按钮
        lookRecord = (TextView) view.findViewById(R.id.look_record);//查看提现记录
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ivGame.startAnimation(animation);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.mywalletfrg, null);
        assignViews();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void setListener() {
        txBtn.setOnClickListener(this);
        lookRecord.setOnClickListener(this);
        ivGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //提现
            case R.id.tx_btn:
                money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    MyToastUtils.showShortToast(getActivity(), "请输入提现金额");
                    return;
                }
//                double amount = se.getAccountBalance() - Double.valueOf(money);
                serviceWitchDraw(money);

                break;
            //查看提现记录
            case R.id.look_record:
                intent = new Intent(context, WithdrawalsAct.class);
//                intent.putExtra(WithdrawalsAct.MONEY,money);
                startActivity(intent);
                break;
            case R.id.ivGame:
                intent = new Intent(context, GameActivity.class);
//                intent.putExtra(WithdrawalsAct.MONEY,money);
                startActivity(intent);
                break;
        }
    }

    /**
     * 提现
     *
     * @param amount
     */
    private void serviceWitchDraw(final String amount) {
        RequestManager.getServicerManager().serviceWithDraw(amount, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                findServantInfo();

                MyToastUtils.showShortToast(getContext(), "提现申请成功");
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        findServantInfo();
    }

    private void findServantInfo() {

        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                UserEntity ue = rd.getData();
                if (ue != null) {
                    String balance = ue.getAccountBalance();
                    if (!TextUtils.isEmpty(balance)) {
                        String fmtMicrometer = FinancialUtil.fmtMicrometer(balance);
                        tvTotal.setText(fmtMicrometer);
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }


}
