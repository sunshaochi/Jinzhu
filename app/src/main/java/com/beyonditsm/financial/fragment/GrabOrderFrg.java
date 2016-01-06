package com.beyonditsm.financial.fragment;

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
import com.beyonditsm.financial.adapter.GrabOrderAdp;
import com.beyonditsm.financial.entity.GrabOrderBean;
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
 * 信贷经理抢单界面
 * Created by Yang on 2015/11/16 0016.
 */
public class GrabOrderFrg extends BaseFragment {
    @ViewInject(R.id.plv)
    private LoadRefreshView plv;

    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

//    @ViewInject(R.id.etAmount)
//    private EditText etAmount;//输入金额
//    @ViewInject(R.id.tvM)
//    private TextView tvM;//月份
//    @ViewInject(R.id.tvSearch)
//    private TextView tvSearch;//搜索


    private GrabOrderAdp adapter;
    private List<GrabOrderBean.RowsEntity> datas = new ArrayList<>();

    private int page;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.graborderfrg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page = 1;
        getList();
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
                page = 1;
                getList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                page++;
                getList();
            }
        });
    }

    @Override
    public void setListener() {
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                page = 1;
                getList();
            }
        });
    }

//    @OnClick({R.id.tvSearch, R.id.rlMonth})
//    public void toClick(View v) {
//        switch (v.getId()) {
//            case R.id.rlMonth:
//                int postition;
//                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
//                    postition = 0;
//                } else {
//                    postition = Integer.valueOf(tvM.getText().toString().trim()) - 1;
//                }
//                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(context,null).builder(postition);
//                dialogChooseMonth.show();
//                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
//                    @Override
//                    public void getAdress(String adress) {
//                        tvM.setText(adress.substring(0, adress.length() - 2));
//                    }
//                });
//                break;
//            case R.id.tvSearch:
//                page = 1;
//                getList();
////                product.setMaxVal(Double.parseDouble(tvMoney.getText().toString()));
////                product.setTimeMaxVal();
////                tvDate.getText().toString();
//                break;
//        }
//    }

    /**
     * 获取list集合信息
     */
    private void getList() {
        int rows = 10;
        RequestManager.getMangManger().getGrabOeder(page, rows, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                loadingView.loadComplete();
                ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                GrabOrderBean grabOrderBean = rd.getData();
                List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();
                if (list == null || list.size() == 0) {
                    if (page == 1)
                        loadingView.noContent();
                    else
                        plv.setHasMoreData(false);
                    return;
                }
                if (page == 1)
                    datas.clear();
                datas.addAll(list);
                if (adapter == null) {
                    adapter = new GrabOrderAdp(context, datas);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.UpData(datas);
                }
            }

            @Override
            public void onError(int status,String msg) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Updata);
        if (recivier == null) {
            recivier = new UpdataRecivier();
        }
        context.registerReceiver(recivier, filter);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(recivier);
        recivier = null;
        super.onDestroy();
    }

    private UpdataRecivier recivier;
    public final static String Updata = "com.beyonditsm.graborderfrg";

    public class UpdataRecivier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
            getList();
        }
    }
}
