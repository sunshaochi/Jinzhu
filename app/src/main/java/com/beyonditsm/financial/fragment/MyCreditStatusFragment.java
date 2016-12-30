package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.OrderDetailAdapter;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.OrderDealEntity;
import com.beyonditsm.financial.entity.OrderListBean;
import com.beyonditsm.financial.entity.OrderWorkMarkBean;
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
 * Created by Administrator on 2015/12/13
 */
public class MyCreditStatusFragment extends BaseFragment {
    @ViewInject(R.id.lv_credit_status)
    private LoadRefreshView plvCreditStatus;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    private MyCreditBean.RowsEntity rowe;
    private List<OrderDealEntity> orderList;
    private OrderDetailAdapter detailAdapter;
    private MyBroadCastReceiver receiver;
    private OrderListBean orderListBean;
    private List<OrderWorkMarkBean>list;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.mycreditstatus_frag, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        rowe = getArguments().getParcelable("rowe");
        orderListBean = getArguments().getParcelable("orderListBean");
        plvCreditStatus.setPullRefreshEnabled(true);
        plvCreditStatus.setScrollLoadEnabled(false);
        plvCreditStatus.setPullLoadEnabled(false);
        plvCreditStatus.setHasMoreData(true);
        plvCreditStatus.getRefreshableView().setDivider(null);
        plvCreditStatus.getRefreshableView().setVerticalScrollBarEnabled(false);
        plvCreditStatus.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plvCreditStatus.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
       if(orderListBean.getOrderWorkMark()!=null){
         list=orderListBean.getOrderWorkMark();
       }
        findOrderDealHisory(list);
    }

    @Override
    public void setListener() {
        plvCreditStatus.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                findOrderDealHisory(list);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findOrderDealHisory(list);
            }
        });
    }


    public void findOrderDealHisory(List<OrderWorkMarkBean>list) {
//        RequestManager.getUserManager().findOrderDealHistory(orderId, new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                loadingView.loadComplete();
//                plvCreditStatus.onPullDownRefreshComplete();
//                JSONObject object = new JSONObject(result);
//                JSONArray data = object.getJSONArray("data");
//                if (data == null) {
//                    loadingView.noContent();
//                }
//                Gson gson = new Gson();
//                if (data != null) {
//                    orderList = gson.fromJson(data.toString(), new TypeToken<List<OrderDealEntity>>() {
//                    }.getType());
//                }
//                Collections.reverse(orderList);
//                if (detailAdapter == null) {
//                    detailAdapter = new OrderDetailAdapter(getActivity(), orderList);
//                    plvCreditStatus.getRefreshableView().setAdapter(detailAdapter);
//                } else {
//                    detailAdapter.setDatas();
//                }
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                plvCreditStatus.onPullDownRefreshComplete();
//                loadingView.loadError();
//            }
//        });
        if(list.size()==0){
            loadingView.noContent();
        }else if(list.size()>0){
            loadingView.loadComplete();
            if (detailAdapter == null) {
                    detailAdapter = new OrderDetailAdapter(getActivity(), list);
                    plvCreditStatus.getRefreshableView().setAdapter(detailAdapter);
                } else {
                    detailAdapter.setDatas();
                }
        }else {
            loadingView.loadError();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }
        getActivity().registerReceiver(receiver, new IntentFilter(UPDATE_DEAL));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    public static final String UPDATE_DEAL = "update_deal";

    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            findOrderDealHisory(list);
        }
    }
}
