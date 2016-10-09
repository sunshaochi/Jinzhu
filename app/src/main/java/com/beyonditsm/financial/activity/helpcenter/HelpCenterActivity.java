package com.beyonditsm.financial.activity.helpcenter;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

public class HelpCenterActivity extends BaseActivity {


    @Override
    public void setLayout() {
        setContentView(R.layout.act_help_center);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("帮助中心");
        setLeftTv("返回");
    }
}
