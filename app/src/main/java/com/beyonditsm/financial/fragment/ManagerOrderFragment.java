package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.manager.OrderDetailAct;
import com.beyonditsm.financial.adapter.ManagerOrderAdp;
import com.beyonditsm.financial.adapter.ManagerOrderCommitAdp;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ManagerOrderFragment extends BaseFragment {
    @ViewInject(R.id.plv)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    private int data;
    private ManagerOrderAdp adapter;
    private ManagerOrderCommitAdp commitAdapter;
    private static int page;
    private int position;
    private List<GrabOrderBean.RowsEntity> datas = new ArrayList<>();
    List<GrabOrderBean.RowsEntity> list1 = new ArrayList<GrabOrderBean.RowsEntity>();
    private String orderStatus;


//    private void init(){
//        plv = (PullToRefreshListView) view.findViewById(R.id.plv);
//        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
//    }
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.managerorderfrg, null);
        plv = (LoadRefreshView) view.findViewById(R.id.plv);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        position=getArguments().getInt("position");
        page = 1;
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(true);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        if(position==1){
            orderStatus="CREDIT_MANAGER_APPROVAL";
        }else if(position==2){
            orderStatus="SUPPLEMENT_DATA";
        }else if(position==3){
            orderStatus="PASS";
        }else if(position==4){
            orderStatus="NO_PASS";
        }
        getOrderStatus(orderStatus,position, page);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }





    @Override
    public void setListener() {
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getOrderStatus(orderStatus,position, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getOrderStatus(orderStatus,position, page);
            }
        });

        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), OrderDetailAct.class);
                GrabOrderBean.RowsEntity data = (GrabOrderBean.RowsEntity) datas.get(i);
                intent.putExtra(ConstantValue.ORDER, data);
                getActivity().startActivity(intent);
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getOrderStatus(orderStatus, position, page);
            }
        });
    }

    //获取订单状态
    private void getOrderStatus(String orderSts,int position, final int page) {
        int rows = 10;
        switch (position) {
            case 0://待提交
                RequestManager.getMangManger().findHasOrder(page, rows, new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) throws JSONException {
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
                            adapter = new ManagerOrderAdp(context, datas);
                            plv.getRefreshableView().setAdapter(adapter);
                        } else {
                            adapter.UpData(datas);
                        }

                    }

                    @Override
                    public void onError(int status, String msg) {
                        loadingView.loadError();
                    }
                });
                break;
            case 1://已提交
                MyLogUtils.info("ssssssss:"+position+"saaaa:"+orderSts);
                    RequestManager.getMangManger().findCommitOrder("CREDIT_MANAGER_APPROVAL", page, rows, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            plv.onPullDownRefreshComplete();
                            plv.onPullUpRefreshComplete();
                            loadingView.loadComplete();
                            ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                            GrabOrderBean grabOrderBean = rd.getData();
                            List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();

                            getSucessData(list);
                        }

                        @Override
                        public void onError(int status, String msg) {
                            loadingView.loadError();
//                            MyLogUtils.info("已提交--------" + msg);
                        }
                    });
//                }
                break;
            case 2://补件中
//                if (orderStatus != null) {
                    RequestManager.getMangManger().findCommitOrder("SUPPLEMENT_DATA", page, rows, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            plv.onPullDownRefreshComplete();
                            plv.onPullUpRefreshComplete();
                            loadingView.loadComplete();
                            ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                            GrabOrderBean grabOrderBean = rd.getData();
                            List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();

                            getSucessData(list);
                        }

                        @Override
                        public void onError(int status, String msg) {
                        loadingView.loadError();
//                        MyToastUtils.showShortToast(getContext(),msg);
                        }
                    });
//                }
                break;
            case 3://已放款
                    RequestManager.getMangManger().findCommitOrder("PASS", page, rows, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            plv.onPullDownRefreshComplete();
                            plv.onPullUpRefreshComplete();
                            loadingView.loadComplete();
                            ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                            GrabOrderBean grabOrderBean = rd.getData();
                            List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();
                            getSucessData(list);
                        }

                        @Override
                        public void onError(int status, String msg) {
                        loadingView.loadError();
//                        MyLogUtils.info("已放款-------------"+msg);
                        }
                    });
//                }
                break;
            case 4://审核未通过
                    RequestManager.getMangManger().findCommitOrder("NO_PASS", page, rows, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            plv.onPullDownRefreshComplete();
                            plv.onPullUpRefreshComplete();
                            loadingView.loadComplete();
                            ResultData<GrabOrderBean> rd = (ResultData<GrabOrderBean>) GsonUtils.json(result, GrabOrderBean.class);
                            GrabOrderBean grabOrderBean = rd.getData();
                            List<GrabOrderBean.RowsEntity> list = grabOrderBean.getRows();
                            getSucessData(list);
                        }

                        @Override
                        public void onError(int status, String msg) {
                            loadingView.loadError();

                        }
                    });
//                }
                break;
        }
    }

    private void getSucessData( List<GrabOrderBean.RowsEntity> list){
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
        if (commitAdapter == null) {
            commitAdapter = new ManagerOrderCommitAdp(context, datas);
            plv.getRefreshableView().setAdapter(commitAdapter);
        } else {
            commitAdapter.UpData(datas);
        }
    }
    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATA);
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
    public final static String UPDATA = "com.beyonditsm.managerorderfrg";

    public class UpdataRecivier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
            String orderStatus = intent.getStringExtra("orderSts");
            if (!TextUtils.isEmpty(orderStatus)) {
                if (orderStatus.equals("CREDIT_MANAGER_APPROVAL")) {
                    LogUtils.i("刷新已提交界面————————————————————————————————————————————————————————————");
                    getOrderStatus(orderStatus,1, page);
                    getOrderStatus(orderStatus, 0, page);
                } else if (orderStatus.equals("SUPPLEMENT_DATA")) {
                    LogUtils.i("刷新补件中界面————————————————————————————————————————————————————————————");
                    getOrderStatus(orderStatus,2, page);
                    getOrderStatus(orderStatus, 0, page);
                } else if (orderStatus.equals("PASS")) {
                    LogUtils.i("刷已放款界面————————————————————————————————————————————————————————————");
                    getOrderStatus(orderStatus,3, page);
                } else if (orderStatus.equals("NO_PASS")) {
                    LogUtils.i("刷新审核未通过界面————————————————————————————————————————————————————————————");
                    getOrderStatus(orderStatus,4, page);
                }
            }else{
                LogUtils.i("刷新待提交界面————————————————————————————————————————————————————————————");
                getOrderStatus(orderStatus, 0, page);
            }
        }
    }
}
