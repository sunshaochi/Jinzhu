package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 佣金
 * Q164454216
 * Created by xiaoke on 2016/12/9.
 */

public class CourtageAct extends BaseActivity {
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tv_yj;
    @ViewInject(R.id.tvlixifen)//佣金兑换
    private EditText tv_dh;
    @ViewInject(R.id.name)
    private EditText name;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_interest_courtage);
    }

    @Override
    public void init(Bundle savedInstanceState) {
       String yj= getIntent().getStringExtra("yongjin");
        if (TextUtils.isEmpty(yj)){
            tv_yj.setText(0);
        }else {
            tv_yj.setText(yj);
        }

    }
}
