package com.beyonditsm.financial.activity.user;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;

/**
 * Created by bitch-1 on 2015/11/16.
 */
public class CultivationAct extends BaseActivity {
    @Override
    public void setLayout() {setContentView(R.layout.activity_cultivation);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("耕耘信用");
        setLeftTv("返回");
    }
}
