package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.MyCreditDAct;
import com.beyonditsm.financial.adapter.MyCreditAdapter;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的贷款
 * Created by wangbin on 15/11/12.
 */
public class MyCreditAct extends BaseActivity {

    @ViewInject(R.id.plv)
    private LoadRefreshView plv;

    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    private int currentPage = 1;
    private MyCreditAdapter adapter;

    public static String CREDIT = "credit";

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_mycredit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的贷款");
        setLeftTv("返回");

        loadingView.setNoContentTxt("暂无贷款");
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(true);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentPage = 1;
                getMycreditList(currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getMycreditList(currentPage);
            }
        });
        getMycreditList(currentPage);
        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyCreditAct.this, MyCreditDAct.class);
                MyCreditBean.RowsEntity credit = (MyCreditBean.RowsEntity) datas.get(position);
                intent.putExtra(CREDIT, credit);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getMycreditList(currentPage);
            }
        });
    }

    private List<MyCreditBean.RowsEntity> datas = new ArrayList<>();

    private void getMycreditList(final int page) {
        MyCreditEntity mce = new MyCreditEntity();
        mce.setPage(page);
        mce.setRows(10);
        RequestManager.getCommManager().myCredit(mce, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                loadingView.loadComplete();
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                ResultData<MyCreditBean> rd = (ResultData<MyCreditBean>) GsonUtils.json(result, MyCreditBean.class);
                MyCreditBean myCreditBean = rd.getData();
                List<MyCreditBean.RowsEntity> list = myCreditBean.getRows();
                if (list == null || list.size() == 0) {
                    if (page == 1)
                        loadingView.noContent();
                    else
                        plv.setHasMoreData(false);
                    return;
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter == null) {
                    adapter = new MyCreditAdapter(getApplicationContext(), datas);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.setDatas(datas);
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

    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new CreditBroadReceiver();
        }
        registerReceiver(receiver, new IntentFilter(CREDIT_RECEIVER));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }


    public static final String CREDIT_RECEIVER = "credit_receiver";
    private CreditBroadReceiver receiver;

    private class CreditBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentPage = 1;
            getMycreditList(currentPage);
        }
    }
}
