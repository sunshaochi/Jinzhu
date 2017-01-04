package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.MyCreditDAct;
import com.beyonditsm.financial.adapter.MyCreditAdapter;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.OrderListBean;
import com.beyonditsm.financial.entity.RelationEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.ParamsUtil;
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
 * 我的贷款
 * Created by wangbin on 15/11/12.
 */
public class MyCreditAct extends BaseActivity {

    @ViewInject(R.id.plv)
    private LoadRefreshView plv;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;

    private int currentPage = 1;
    private MyCreditAdapter adapter;
    public static String CREDIT = "credit";
    private PushBroadReceiver pushBroadReceiver;
    private String orderId;
    private List<OrderListBean> orderList=new ArrayList<>();
    private List<String> keyLists;
    private List<RelationEntity> carList,hourseList, creditList;//车产，职业，房产，信用
    //    private String id;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_mycredit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的贷款");
        setLeftTv("返回");

        //强制关闭键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loadingView.setNoContentTxt("暂无贷款");
        orderId = SpUtils.getOrderId(MyApplication.getInstance());
        MyLogUtils.info("保存的orderID:"+ orderId);
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(false);
        plv.setPullLoadEnabled(true);
        plv.setHasMoreData(true);
//        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setDividerHeight(20);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentPage = 1;
                getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
            }
        });
        getDictionaryContent();//获取房产类型，车产类型，信用记录传递到详情里面匹配成功后，获取贷款列表
//        getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyCreditAct.this, MyCreditDAct.class);
//                MyCreditBean.RowsEntity credit = datas.get(position);
                OrderListBean orderListBean=datas.get(position);
//                if (position==0){
//                    intent.putExtra("type","comm");
//                }else if (position==1){
//                    intent.putExtra("type","speed");
//                }
//                intent.putExtra(CREDIT, credit);
//                intent.putExtra("position", position);
                intent.putExtra("orderListBean",orderListBean);
                startActivity(intent);
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getDictionaryContent();
//                getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 获取房产类型，车产类型，信用类型
     */
    private void getDictionaryContent() {
        keyLists = new ArrayList<>();
        keyLists.add("cardProperty");
        keyLists.add("houseProperty");
        keyLists.add("creidtType");
        RequestManager.getCommManager().findDicMap(keyLists, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                Gson gson = new Gson();
                carList = gson.fromJson(data.getJSONArray("cardProperty").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                hourseList = gson.fromJson(data.getJSONArray("houseProperty").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                creditList = gson.fromJson(data.getJSONArray("creidtType").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCarList(carList);
                ParamsUtil.getInstance().setHourseList(hourseList);
                ParamsUtil.getInstance().setCreditList(creditList);

                getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);

            }

            @Override
            public void onError(int status, String msg) {
                loadingView.loadError();
            }
        });
    }

    private List<OrderListBean> datas = new ArrayList<>();

    /**
     *mobilePhone 手机号
     * @param page
     * @param
     */
    private void getMycreditList(String mobilePhone,final int page) {
        MyCreditEntity mce = new MyCreditEntity();
        mce.setPage(page);
        mce.setRows(10);
        RequestManager.getCommManager().myCredit(mobilePhone,mce, new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
                loadingView.loadComplete();
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                try {
                    JSONObject objects = new JSONObject(result);
                    JSONObject datas = objects.getJSONObject("data");
                    JSONArray list=  datas.getJSONArray("list");
                    Gson gson = new Gson();
                    orderList = gson.fromJson(list.toString(), new TypeToken<List<OrderListBean>>() {
                    }.getType());
//                    MyLogUtils.info("解析后的集合===="+orderList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                ResultData<MyCreditBean> rd = (ResultData<MyCreditBean>) GsonUtils.json(result, MyCreditBean.class);
//                MyCreditBean myCreditBean = rd.getData();
//                List<MyCreditBean.RowsEntity> list = myCreditBean.getRows();
                if (orderList == null || orderList.size() == 0) {
                    if (page == 1)
                        loadingView.noContent();
                    else
                        plv.setHasMoreData(false);
                    return;
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(orderList);
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
        if (pushBroadReceiver == null) {
            pushBroadReceiver = new PushBroadReceiver();
        }
        registerReceiver(pushBroadReceiver, new IntentFilter(HIDE_MESSAGE));
        registerReceiver(receiver, new IntentFilter(CREDIT_RECEIVER));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
//        if (!TextUtils.isEmpty(orderId)){
        getDictionaryContent();
//            getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (pushBroadReceiver != null) {
            unregisterReceiver(pushBroadReceiver);
        }
    }


    public static final String CREDIT_RECEIVER = "credit_receiver";
    private CreditBroadReceiver receiver;

    private class CreditBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentPage = 1;
            getDictionaryContent();
//            getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
        }
    }

    public static final String HIDE_MESSAGE = "hide_message";

    private class PushBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderId = SpUtils.getOrderId(MyApplication.getInstance());
            MyLogUtils.info("广播发回来的orderID"+orderId);
            currentPage = 1;
            getDictionaryContent();
//            getMycreditList(SpUtils.getPhonenumber(MyApplication.getInstance().getApplicationContext()),currentPage);
        }
    }

}
