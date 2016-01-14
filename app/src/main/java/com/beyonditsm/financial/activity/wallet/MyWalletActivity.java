package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;

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
    private RelativeLayout rlMyPayments;
    @ViewInject(R.id.rlMyOrder)
    private RelativeLayout rlMyOrder;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mywallet);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("我的钱包");
    }
}
