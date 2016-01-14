package com.beyonditsm.financial.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.BalanceAdapter;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

/**
 * 抵扣券fragment
 * Created by Administrator on 2016/1/14.
 */
public class RebateFragment extends BaseFragment{
    @ViewInject(R.id.lv_credit_status)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    //    private List<OrderDealEntity> orderList;
    private BalanceAdapter balanceAdapter;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_balance,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(false);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        findOrderDealHisory("");
    }

    @Override
    public void setListener() {
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                findOrderDealHisory(rowe.getId());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }
    public void findOrderDealHisory(String orderId){
        RequestManager.getUserManager().findOrderDealHistory(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
//                loadingView.loadComplete();
//                plv.onPullDownRefreshComplete();
//                JSONObject object = new JSONObject(result);
//                JSONArray data = object.getJSONArray("data");
//                if (data == null) {
//                    loadingView.noContent();
//                }
//                Gson gson = new Gson();
//                orderList = gson.fromJson(data.toString(), new TypeToken<List<OrderDealEntity>>() {
//                }.getType());
//                Collections.reverse(orderList);
                if (balanceAdapter == null) {
                    balanceAdapter = new BalanceAdapter();
                    plv.getRefreshableView().setAdapter(balanceAdapter);
                }
            }

            @Override
            public void onError(int status, String msg) {
                plv.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
    }
}
