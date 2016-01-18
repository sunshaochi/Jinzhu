package com.beyonditsm.financial.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.BalanceAdapter;
import com.beyonditsm.financial.entity.OrderDealEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;


/**
 * 现金券fragment
 * Created by Administrator on 2016/1/14.
 */
public class CouponsFragment extends BaseFragment{
    @ViewInject(R.id.lv_credit_status)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.lv)
    private ListView lv;
//    private List<OrderDealEntity> orderList;
    private BalanceAdapter balanceAdapter;
    private int page;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_balance,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page=1;
        findOrderDealHisory();
//        plv.setPullRefreshEnabled(true);
//        plv.setScrollLoadEnabled(false);
//        plv.setPullLoadEnabled(false);
//        plv.setHasMoreData(true);
//        plv.getRefreshableView().setDivider(null);
//        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
//        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
//        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());

//        if (balanceAdapter == null) {
//            balanceAdapter = new BalanceAdapter();
//            lv.setAdapter(balanceAdapter);
//        }
    }

    @Override
    public void setListener() {
//        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
////                findOrderDealHisory(rowe.getId());
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });
    }
    public void findOrderDealHisory(){
        int rows = 10;
        RequestManager.getWalletManager().findCashHistory(page, rows, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
