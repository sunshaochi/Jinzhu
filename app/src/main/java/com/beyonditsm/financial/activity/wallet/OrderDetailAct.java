package com.beyonditsm.financial.activity.wallet;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.OrderDetailAdp;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单明细
 * Created by Administrator on 2016/1/14.
 */
public class OrderDetailAct extends BaseActivity {
    @ViewInject(R.id.lv_order_detail)
    private LoadRefreshView lvOrderDetail;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    private OrderDetailAdp orderDetailAdapter;

    @Override
    public void setLayout() {
        setLeftTv("返回");
        setTopTitle("订单明细");
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        lvOrderDetail.setPullRefreshEnabled(true);
        lvOrderDetail.setScrollLoadEnabled(false);
        lvOrderDetail.setPullLoadEnabled(false);
        lvOrderDetail.setHasMoreData(true);
        lvOrderDetail.getRefreshableView().setDivider(null);
        lvOrderDetail.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvOrderDetail.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lvOrderDetail.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        findOrderDetail();
    }
    private void findOrderDetail() {
        if (orderDetailAdapter==null) {
            orderDetailAdapter = new OrderDetailAdp();
            lvOrderDetail.getRefreshableView().setAdapter(orderDetailAdapter);
        }
    }
}
