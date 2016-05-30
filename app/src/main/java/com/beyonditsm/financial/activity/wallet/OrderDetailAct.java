package com.beyonditsm.financial.activity.wallet;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.OrderDetailAdp;
import com.beyonditsm.financial.entity.OrderListEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单明细
 * Created by Administrator on 2016/1/14.
 */
public class OrderDetailAct extends BaseActivity {
    @ViewInject(R.id.lv_order_detail)
    private LoadRefreshView lvOrderDetail;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.lv_Order_Detail)
    private ListView lvOrder;
    private OrderDetailAdp orderDetailAdapter;
    private int page;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("订单明细");
        page = 1;
        findOrderDetail();
        lvOrderDetail.setPullRefreshEnabled(true);
        lvOrderDetail.setScrollLoadEnabled(false);
        lvOrderDetail.setPullLoadEnabled(true);
        lvOrderDetail.setHasMoreData(true);
        lvOrderDetail.getRefreshableView().setDivider(null);
        lvOrderDetail.getRefreshableView().setDividerHeight(20);
        lvOrderDetail.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvOrderDetail.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lvOrderDetail.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        lvOrderDetail.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lvOrderDetail.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                page = 1;
                findOrderDetail();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                findOrderDetail();
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                page=1;
                findOrderDetail();
            }
        });
    }

    private List<OrderListEntity.RowsEntity> datas = new ArrayList<>();
    private void findOrderDetail() {
        int rows = 10;
        RequestManager.getWalletManager().findOrderList(page, rows, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                lvOrderDetail.onPullUpRefreshComplete();
                lvOrderDetail.onPullDownRefreshComplete();
                loadingView.loadComplete();
                ResultData<OrderListEntity> rd = (ResultData<OrderListEntity>) GsonUtils.json(result, OrderListEntity.class);
                OrderListEntity data = rd.getData();
                List<OrderListEntity.RowsEntity> list = data.getRows();
                if (list==null||list.size()==0){
                    if (page==1){
                        loadingView.noContent();
                        loadingView.setNoContentTxt("暂时没有订单");
                    }else{
                        lvOrderDetail.setHasMoreData(false);
                    }
                    return;
                }
                if (page==1){
                    datas.clear();
                }
                datas.addAll(list);
                if (orderDetailAdapter == null) {
                    orderDetailAdapter = new OrderDetailAdp(OrderDetailAct.this,datas);
                    lvOrderDetail.getRefreshableView().setAdapter(orderDetailAdapter);
                }else{
                    orderDetailAdapter.setDatas(datas);
                }
            }

            @Override
            public void onError(int status, String msg) {
                lvOrderDetail.onPullUpRefreshComplete();
                lvOrderDetail.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
//
    }
}
