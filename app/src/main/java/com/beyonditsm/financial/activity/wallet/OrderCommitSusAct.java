package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单提交成功页面
 * Created by Administrator on 2016/1/14.
 */
public class OrderCommitSusAct extends BaseActivity{
    @ViewInject(R.id.ivBack)
    private ImageView ivBack;
    @ViewInject(R.id.sure)
    private Button btnSure;
    @Override
    public void setLayout() {

        setContentView(R.layout.activity_order_sus);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ivBack.setVisibility(View.INVISIBLE);
        setTopTitle("订单提交");
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
