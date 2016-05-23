package com.beyonditsm.financial.activity.credit;

import android.os.Bundle;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 贷款指南
 * Created by wangbin on 16/2/26.
 */
public class CreditGuideAct extends BaseActivity {
    @ViewInject(R.id.iv_bg)
    private ImageView ivBg;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("贷款指南");
        String roleName = SpUtils.getRoleName(getApplicationContext());
        if (!"ROLE_COMMON_CLIENT".equals(roleName)){//非普通用户显示服务者指南
            setTopTitle("服务者指南");
            ivBg.setBackgroundResource(R.mipmap.servantguide);
        }
    }
}
