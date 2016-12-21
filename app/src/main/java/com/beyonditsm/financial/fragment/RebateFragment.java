package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.RebateAdapter;
import com.beyonditsm.financial.entity.BalanceMxEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 抵扣券fragment明细
 * Created by Administrator on 2016/1/14.
 */
public class RebateFragment extends BaseFragment{
    @ViewInject(R.id.lv_credit_status)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    //    private List<OrderDealEntity> orderList;
    private List<BalanceMxEntity> list;
    private RebateAdapter rebateAdapter;
    private int page;
    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_balance,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page=1;

        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(false);
        plv.setPullLoadEnabled(true);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        findOrderDealHisory();
    }

    @Override
    public void setListener() {
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                page=1;
                findOrderDealHisory();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                findOrderDealHisory();
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                page = 1;
                findOrderDealHisory();
            }
        });
    }
    private List<BalanceMxEntity> datas = new ArrayList<>();
    public void findOrderDealHisory(){
        int rows = 10;
        list=new ArrayList<>();
        RequestManager.getWalletManager().findCashHistory(SpUtils.getPhonenumber(getContext()),page, rows,2 ,new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                //抵扣券明细
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray data2= data.getJSONArray("rows");
                Gson gson = new Gson();
                list = gson.fromJson(data2.toString(), new TypeToken<List<BalanceMxEntity>>() {
                }.getType());
                if (list==null||list.size()==0){
                    if (page==1){
                        loadingView.noContent();
                        loadingView.setNoContentTxt("暂时没有抵扣券");
                    }else{
                        plv.setHasMoreData(false);
                    }
                    return;
                }
                if (page==1){
                    datas.clear();
                }
                datas.addAll(list);
                if (rebateAdapter == null) {
                    rebateAdapter = new RebateAdapter(context,datas);
                    plv.getRefreshableView().setAdapter(rebateAdapter);
                }else{
                    rebateAdapter.setDatas(datas);
                }
            }

            @Override
            public void onError(int status, String msg) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
    }
}
