package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

/**
 * Created by gxy on 2016/1/14.
 */
public class InterestDeduction extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_interest_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("抵扣利息");

    }
}
