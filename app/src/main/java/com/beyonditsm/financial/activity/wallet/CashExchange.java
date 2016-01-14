package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

/**
 * Created by gxy on 2016/1/14.
 */
public class CashExchange extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cash_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("现金兑换");
    }
}
