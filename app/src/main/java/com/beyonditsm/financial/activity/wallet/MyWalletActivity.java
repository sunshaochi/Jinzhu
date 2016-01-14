package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by wangbin on 16/1/14.
 */
public class MyWalletActivity extends BaseActivity{
    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;
    @ViewInject(R.id.tvName)
    private TextView tvName;
    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;
    @ViewInject(R.id.rlMyPayments)
    private RelativeLayout rlMyPayments;//收支明细
    @ViewInject(R.id.rlMyOrder)
    private RelativeLayout rlMyOrder;//订单明细
    @ViewInject(R.id.tv_ExchangeMoney)
    private TextView tvExangeMoney;
    @ViewInject(R.id.tv_WeitGetMoney)
    private TextView tvWeitGetMoney;
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mywallet);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("我的钱包");
    }

    @OnClick({R.id.rlMyPayments,R.id.rlMyOrder,R.id.rlxianjin,R.id.rldikou})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.rlMyPayments:
                intent=new Intent(MyWalletActivity.this,BalancePaymentsAct.class);
                startActivity(intent);
                break;
            case R.id.rlMyOrder:
                intent=new Intent(MyWalletActivity.this,OrderDetailAct.class);
                startActivity(intent);
                break;
            case R.id.rlxianjin:
                intent=new Intent(MyWalletActivity.this,CashExchange.class);
                startActivity(intent);
                break;
            case R.id.rldikou:
                intent=new Intent(MyWalletActivity.this,InterestDeduction.class);
                startActivity(intent);
                break;
        }
    }
}
