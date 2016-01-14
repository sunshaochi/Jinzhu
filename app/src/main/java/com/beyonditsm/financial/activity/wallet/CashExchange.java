package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by gxy on 2016/1/14.
 */
public class CashExchange extends BaseActivity {
    private RelativeLayout rlset;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cash_exchange);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("现金兑换");
    }
    @OnClick({R.id.btn_ok,R.id.rlset})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_ok:
                intent=new Intent(CashExchange.this,OrderCommitSusAct.class);
                startActivity(intent);
                break;
            case R.id.rlset:
                intent=new Intent(CashExchange.this,SetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }
}
