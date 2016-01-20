package com.beyonditsm.financial.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.RebateAdapter;
import com.beyonditsm.financial.entity.BalanceEntity;
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
 * 抵扣券fragment
 * Created by Administrator on 2016/1/14.
 */
public class RebateFragment extends BaseFragment{
    @ViewInject(R.id.lv_credit_status)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.lv)
    private ListView lv;
    //    private List<OrderDealEntity> orderList;
    private RebateAdapter rebateAdapter;
    private int page;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_balance,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page=1;
        findOrderDealHisory();
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(false);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());

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
    private List<BalanceEntity.RowsEntity> datas = new ArrayList<>();
    public void findOrderDealHisory(){
        int rows = 10;
        RequestManager.getWalletManager().findDeductionHistory(page, rows, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                loadingView.loadComplete();
                ResultData<BalanceEntity> rd = (ResultData<BalanceEntity>) GsonUtils.json(result, BalanceEntity.class);
                BalanceEntity balanceEntity = rd.getData();
                List<BalanceEntity.RowsEntity> list = balanceEntity.getRows();
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
