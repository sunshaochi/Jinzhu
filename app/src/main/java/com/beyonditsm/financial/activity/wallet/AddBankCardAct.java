package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

/**
 * 添加银行卡
 * Created by Administrator on 2016/3/15.
 */
public class AddBankCardAct  extends BaseActivity{
    @Override
    public void setLayout() {
        setContentView(R.layout.act_addbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加银行卡");

    }
}
