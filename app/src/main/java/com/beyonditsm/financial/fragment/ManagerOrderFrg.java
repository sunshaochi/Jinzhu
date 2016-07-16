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
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.manager.OrderDetailAct;
import com.beyonditsm.financial.adapter.ManagerOrderAdp;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 信贷经理订单管理
 * Created by Yang on 2015/11/17 0017.
 */
public class ManagerOrderFrg extends BaseFragment {
    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    private ManagerOrderAdp adapter;
    private int page;
    private List<GrabOrderBean.RowsEntity> datas = new ArrayList<>();

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.managerorderfrg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page = 1;
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(true);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        getOrder(page);
    }

    @Override
    public void setListener() {
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.onPullDownRefreshComplete();
                page = 1;
                getOrder(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.onPullUpRefreshComplete();
                page++;
                getOrder(page);
            }
        });

        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), OrderDetailAct.class);
                GrabOrderBean.RowsEntity data = (GrabOrderBean.RowsEntity) adapter.getItem(i);
                intent.putExtra(ConstantValue.ORDER, data);
                getActivity().startActivity(intent);
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                page = 1;
                getOrder(page);
            }
        });
    }

//    @OnClick({R.id.tvSearch, R.id.rlMonth})
//    public void todo(View v) {
//        switch (v.getId()) {
//            case R.id.tvSearch:
//                page = 1;
//                getOrder(page);
//                break;
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
//        }
//    }

    /**
     * 获取已经抢到的订单列表
     *
     * @param page 页数
     */
    private void getOrder(final int page) {
        int rows = 10;
        RequestManager.getMangManger().findHasOrder(page, rows, new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                GrabOrderBean grabOrderBean = rd.getData();
                List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();
//                List<GrabOrderBean.RowsEntity> list1 = new ArrayList<GrabOrderBean.RowsEntity>();
                if (list == null || list.size() == 0) {
                    if (page == 1)
                        loadingView.noContent();
                    else
                        plv.setHasMoreData(false);
                    return;
                }
                if (page == 1)
                    datas.clear();
//                for (int i = 0; i < list.size(); i++) {
//                    String ordersts = list.get(i).getOrderSts();
//                    if (ordersts.equals("CREDIT_MANAGER_APPROVAL")
//                            || ordersts.equals("SUPPLEMENT_DATA")) {
//                        list1.add(list.get(i));
//                    }
//                }
                    datas.addAll(list);
                if (adapter == null) {
                    adapter = new ManagerOrderAdp(context,datas);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.UpData(datas);
                }
            }

            @Override
            public void onError(int status,String msg) {
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
    public final static String Updata = "com.beyonditsm.managerorderfrg";

    public class UpdataRecivier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
            intent.getStringExtra("orderSts");
            getOrder(page);
        }
    }
}
