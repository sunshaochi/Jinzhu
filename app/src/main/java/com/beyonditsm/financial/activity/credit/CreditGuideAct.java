package com.beyonditsm.financial.activity.credit;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

/**
 * 贷款指南
 * Created by wangbin on 16/2/26.
 */
public class CreditGuideAct extends BaseActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("贷款指南");
    }
}
