package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.MyCreditDAct;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.fragment.MineFragment;
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
    private PushBroadReceiver pushBroadReceiver;
    private ImageView ivRedPoint;

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
                ivRedPoint.setVisibility(View.GONE);
                Intent intent = new Intent(MyCreditAct.this, MyCreditDAct.class);
                MyCreditBean.RowsEntity credit = (MyCreditBean.RowsEntity) datas.get(position);
                intent.putExtra(CREDIT, credit);
                intent.putExtra("position", position);
                startActivity(intent);
                sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
                sendBroadcast(new Intent(MineFragment.HIDE_POINT));
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
        if (pushBroadReceiver==null) {
            pushBroadReceiver = new PushBroadReceiver();
        }
        registerReceiver(receiver, new IntentFilter(CREDIT_RECEIVER));
        registerReceiver(pushBroadReceiver,new IntentFilter(JPUSH_MESSAGE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (pushBroadReceiver!=null){
            unregisterReceiver(pushBroadReceiver);
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

    public static final String JPUSH_MESSAGE = "com.jpush.message";
    private class PushBroadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ivRedPoint.setVisibility(View.VISIBLE);
        }
    }
    class MyCreditAdapter extends BaseAdapter {
        private Context context;
        private List<MyCreditBean.RowsEntity> list;

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");//保留小数

        public void setDatas(List<MyCreditBean.RowsEntity> list){
            this.list = list;
            notifyDataSetChanged();
        }
        public MyCreditAdapter(Context context,List<MyCreditBean.RowsEntity> list){
            this.context=context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list!=null?list.size():0;
        }

        @Override
        public Object getItem(int position) {
            return list!=null?list.get(position):null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView==null){
                holder = new ViewHolder();
                convertView=View.inflate(context, R.layout.lv_mycredit_item,null);
                holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
                holder.tvTotalAmount = (TextView) convertView.findViewById(R.id.tv_totalAmount);
                holder.tvPeriodsAmount = (TextView) convertView.findViewById(R.id.tv_periodsAmount);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                ivRedPoint = (ImageView) convertView.findViewById(R.id.ivMs);
                holder.ivRedPoint = ivRedPoint;
                holder.rlCreditList = (RelativeLayout) convertView.findViewById(R.id.rl_creditList);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            ;
            holder.tvProductName.setText(list.get(position).getProductName());
            holder.tvTotalAmount.setText(df.format(Double.valueOf(list.get(position).getTotalAmount())/10000)+"万");
            holder.tvPeriodsAmount.setText(df.format(Double.valueOf(list.get(position).getPeriodsAmount())/10000)+"万");
            String status=list.get(position).getOrderSts();
            if("CREDIT_MANAGER_APPROVAL".equals(status)||
                    "CREDIT_MANAGER_GRAB".equals(status)||"ORGANIZATION_APPROVAL".equals(status)){
                holder.tvName.setText("审批中");
                holder.tvName.setTextColor(Color.parseColor("#ff6633"));

            }else if("PASS".equals(status)){
                holder.tvName.setText("审批通过");
                holder.tvName.setTextColor(Color.parseColor("#1fd45f"));

            }else if("WAIT_BACKGROUND_APPROVAL".equals(status)
                    ){
                holder.tvName.setText("待审批");
                holder.tvName.setTextColor(Color.parseColor("#ff6633"));

            }else if("SUPPLEMENT_DATA".equals(status)){
                holder.tvName.setText("补件中");
                holder.tvName.setTextColor(Color.parseColor("#ff6633"));

            }else if("NO_PASS".equals(status)){
                holder.tvName.setText("审批不通过");
                holder.tvName.setTextColor(Color.parseColor("#ff0000"));
            }else if ("CANCEL_REQUET".equals(status)){
                holder.tvName.setText("已取消");
                holder.tvName.setTextColor(Color.parseColor("#ff8383"));
            }
            return convertView;
        }
        class ViewHolder{
            TextView tvProductName,tvTotalAmount,tvPeriodsAmount,tvName;
            ImageView ivRedPoint;//推送红点
            RelativeLayout rlCreditList;
        }

    }
}
