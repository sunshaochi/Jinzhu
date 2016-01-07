package com.beyonditsm.financial.activity.servicer;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.WithDrawalsAdp;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.ServantWithdrawBean;
import com.beyonditsm.financial.entity.ServantWithdrawEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 提现记录
 * Created by Yang on 2015/11/13 0013.
 */
public class WithdrawalsAct extends BaseActivity {

    private LoadRefreshView txjlPtr;

    private WithDrawalsAdp adapter;//提现记录适配器
    private int currentPage = 1;
    private LoadingView lvLoading;
//    private String money;

    private void assignViews() {
        txjlPtr = (LoadRefreshView) findViewById(R.id.txjl_ptr);
        lvLoading = (LoadingView) findViewById(R.id.loadView);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.withdrawalsact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("提现记录");
        setLeftTv("返回");
        assignViews();
//        money = getIntent().getStringExtra(MONEY);
//        getIntent().getStringExtra(Balance);
//        MyToastUtils.showShortToast(getApplicationContext(), "-----------" + money);
        txjlPtr.setPullRefreshEnabled(true);
        txjlPtr.setScrollLoadEnabled(true);
        txjlPtr.setPullLoadEnabled(false);
        txjlPtr.setHasMoreData(true);
        txjlPtr.getRefreshableView().setDivider(null);
        txjlPtr.getRefreshableView().setVerticalScrollBarEnabled(false);
        txjlPtr.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        txjlPtr.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        txjlPtr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                txjlPtr.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentPage=1;
                findSercantWithdrawls(currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                findSercantWithdrawls(currentPage);
            }
        });
        findSercantWithdrawls(currentPage);
        lvLoading.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findSercantWithdrawls(currentPage);
            }
        });
    }

    public static final String MONEY = "money";
    public static final String Balance = "balance";

    private List<ServantWithdrawBean.RowsEntity> datas = new ArrayList<>();
    private void findSercantWithdrawls(final int curentPage){
        ServantWithdrawEntity entity =new ServantWithdrawEntity();
        entity.setPage(curentPage);
        entity.setRows(10);
        RequestManager.getServicerManager().findServantWithdraw(entity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                lvLoading.loadComplete();
                txjlPtr.onPullDownRefreshComplete();
                txjlPtr.onPullUpRefreshComplete();
                ResultData<ServantWithdrawBean> rd = (ResultData<ServantWithdrawBean>) GsonUtils.json(result, ServantWithdrawBean.class);
                ServantWithdrawBean servantWithdrawBean = rd.getData();
                List<ServantWithdrawBean.RowsEntity> list = servantWithdrawBean.getRows();
                if (servantWithdrawBean.getTotal()==0){
                    lvLoading.noContent();
                    return;
                }
                if (list==null||list.size()==0){
                    if (curentPage==1){
                        lvLoading.noContent();
                    }else{
                        txjlPtr.setHasMoreData(false);
                    }
                    return;
                }
                if (curentPage==1){
                    datas.clear();
                }
                datas.addAll(list);
                Collections.reverse(datas);
                if (adapter==null){
//                    if (!TextUtils.isEmpty(money)) {

                        adapter = new WithDrawalsAdp(WithdrawalsAct.this, list);
                        txjlPtr.getRefreshableView().setAdapter(adapter);
//                    }
                }else{
                    adapter.notifyChange(datas);
                }
            }

            @Override
            public void onError(int status,String msg) {
                txjlPtr.onPullUpRefreshComplete();
                txjlPtr.onPullDownRefreshComplete();
                lvLoading.loadError();
            }
        });
    }
}
