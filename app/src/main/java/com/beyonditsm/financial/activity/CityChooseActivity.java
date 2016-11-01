package com.beyonditsm.financial.activity;

import android.os.Bundle;

import com.beyonditsm.financial.R;

public class CityChooseActivity extends BaseActivity {


    @Override
    public void setLayout() {
        setContentView(R.layout.act_city_choose);
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void setTopTitle(String title) {
        super.setTopTitle("选择城市");
    }
}
