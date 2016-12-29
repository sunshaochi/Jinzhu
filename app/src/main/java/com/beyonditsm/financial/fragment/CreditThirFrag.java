package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.activity.credit.SubFlowAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.entity.CreditEvent;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.AutoDismissDialog;
import com.beyonditsm.financial.view.LoadingView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款第三步
 * Created by wangbin on 16/3/21.
 */
public class CreditThirFrag extends BaseFragment {
    @ViewInject(R.id.lvCredit)
    private ListView lvCredit;
    @ViewInject(R.id.tvCredit)
    private TextView tvCredit;
    @ViewInject(R.id.iv_progress)
    private ImageView ivProgress;
    @ViewInject(R.id.ll_upload)
    private LinearLayout llUpload;
    @ViewInject(R.id.ll_uploadSuccess)
    private LinearLayout llUploadSuccess;
    @ViewInject(R.id.crethr_btn1)
    private Button crethrBtn1;
    @ViewInject(R.id.crethr_btn2)
    private Button crethrBtn2;
    @ViewInject(R.id.tvNo)
    private TextView tvNo;//不需要增信材料
    @ViewInject(R.id.llHas)
    private LinearLayout llHas;//需要增信材料
    @ViewInject(R.id.lv_credit_third)
    private LoadingView lvCreditThird;
    private Gson gson = new Gson();
    private MyAdapter adapter;

    List<UpLoadEntity> datas;
    private String orderId;

    private int act_type;
    private String orderStatus;
    private String orderSts;
    
    private Activity mParentActivity;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.credit_third_frg, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        act_type = getArguments().getInt("act_type", 0);
        orderStatus = getArguments().getString("orderStatus");
        orderId = CreditStepAct.orderId;
//        if(act_type==1){
//            tvCredit.setVisibility(View.GONE);
//        }
        lvCreditThird.setNoContentTxt("暂无上传资料项");
        if ("PASS".equals(orderStatus)) {//订单状态
            ivProgress.setBackgroundResource(R.mipmap.progress_04);
            tvCredit.setVisibility(View.GONE);
        }
        getUploadList(orderId);//获取上传的列表
        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mParentActivity, CreditUploadAct.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("flowId", datas.get(position).getFlowId());//流程id
                intent.putExtra("status", datas.get(position).getStatus());//流程状态
                mParentActivity.startActivity(intent);
            }
        });
    }


    /**
     * 提交成功，刷新
     */
    public void onEvent(CreditEvent event) {
        getUploadList(orderId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setListener() {

    }

    /**
     * 获取上传资料列表
     *
     * @param orderId 订单id
     */
    private void getUploadList(final String orderId) {

        RequestManager.getCommManager().getUpLoadList(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyLogUtils.info("getUploadList"+result);
                lvCreditThird.loadComplete();
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.get("data") instanceof JSONArray) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                    }.getType());

                    if (adapter == null) {
                        adapter = new MyAdapter(datas);
                        lvCredit.setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas);
                    }
                }else{
                    lvCreditThird.noContent();
                    applayCredit(orderId);
                    MyDismissDialog myDismissDialog = new MyDismissDialog(mParentActivity);
                    myDismissDialog.builder().show();
                }
                applayStatus(orderId);
                setIsTvClick(datas);
            }



            @Override
            public void onError(int status, String msg) {
                lvCreditThird.loadError();
                lvCreditThird.setOnRetryListener(new LoadingView.OnRetryListener() {
                    @Override
                    public void OnRetry() {
                        getUploadList(orderId);
                    }
                });

            }
        });
    }

    private static class MyDismissDialog extends AutoDismissDialog{

        MyDismissDialog(Context context) {
            super(context);
        }
    }


    /**
     * 提交审核申请贷款
     *
     * @param orderId 订单id
     */
    private void applayCredit(final String orderId) {
        RequestManager.getCommManager().applyCredit(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                findOrderFlow(orderId);
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getContext(), msg);
            }
        });
    }

    private void setIsTvClick(List<UpLoadEntity> list) {
        boolean isClick = true;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                isClick = isClick && ("true".equals(list.get(i).getIsComplete()));
            }
        }
//        if (!TextUtils.isEmpty(orderStatus)) {
//            MyLogUtils.info("订单状态：" + orderStatus);
//            if ("CREDIT_MANAGER_APPROVAL".equals(orderStatus)||
//                    "CREDIT_MANAGER_GRAB".equals(orderStatus) ||
//                    "ORGANIZATION_APPROVAL".equals(orderStatus) ||
//                    "WAIT_BACKGROUND_APPROVAL".equals(orderStatus)||
//                    "CANCEL_REQUET".equals(orderStatus)) {//审批中状态,待审批，已取消
//                isClick = false;
//            }else if ("SUPPLEMENT_DATA".equals(orderStatus)){
//                isClick= true;
//            }
//        }

        if (isClick) {
            tvCredit.setBackgroundResource(R.drawable.button_gen);
            tvCredit.setEnabled(true);
            tvCredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applayCredit(orderId);
                }
            });
        } else {
            tvCredit.setBackgroundResource(R.drawable.button_grey);
            tvCredit.setEnabled(false);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CreditStepAct){
            mParentActivity = activity;
        }
        if (mParentActivity ==null){
            mParentActivity = CreditStepAct.getInstance();
        }
    }

    /**
     * 是否需要增信资料
     *
     * @param orderId 订单id
     */
    private void findOrderFlow(final String orderId) {
        RequestManager.getCommManager().findOrderFlow(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.get("data") instanceof JSONArray  && jsonObject.getJSONArray("data").length()>0){
                    CreditStepAct.upList = gson.fromJson(jsonObject.getJSONArray("data").toString(), new TypeToken<List<UpLoadEntity>>() {
                    }.getType());
                    //                    EventBus.getDefault().post(new CreditStepAct.FirstEvent(3, orderId));
                    llUploadSuccess.setVisibility(View.VISIBLE);
                    llUpload.setVisibility(View.GONE);
                    findUploadSuccess();
                }else {
                    CreditStepAct.upList = null;
                    MyToastUtils.showShortToast(getContext(), "订单已提交，请耐心等待审批");
                    llUploadSuccess.setVisibility(View.VISIBLE);
                    llUpload.setVisibility(View.GONE);
                    mParentActivity.sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
                    mParentActivity.sendBroadcast(new Intent(MyCreditDetailFragment.UPDATE_ORDER));
                    mParentActivity.sendBroadcast(new Intent(MyCreditStatusFragment.UPDATE_DEAL));
                    findUploadSuccess();
//                    mParentActivity.finish();
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void findUploadSuccess() {
        if (CreditStepAct.upList != null && CreditStepAct.upList.size() > 0) {
            crethrBtn1.setText("提交增信材料");
            llHas.setVisibility(View.VISIBLE);
            tvNo.setVisibility(View.GONE);
        } else {
            crethrBtn1.setText("继续申请");
            llHas.setVisibility(View.GONE);
            tvNo.setVisibility(View.VISIBLE);
        }
        crethrBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreditStepAct.upList != null && CreditStepAct.upList.size() > 0) {
                    Intent intent = new Intent(mParentActivity, SubFlowAct.class);
                    intent.putParcelableArrayListExtra("sub_list", (ArrayList<? extends Parcelable>) CreditStepAct.upList);
                    intent.putExtra("order_id", orderId);
                    mParentActivity.startActivity(intent);
                    mParentActivity.finish();
                } else {
                    mParentActivity.finish();
                    AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                }
            }
        });
        crethrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentActivity.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                mParentActivity.finish();
                AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
            }
        });
    }


    private void applayStatus(final String orderId) {
        RequestManager.getCommManager().applayStatus(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                int r = data.getInt("result");
                orderSts = data.getString("orderSts");
                MyLogUtils.info("返回的结果：" + r + ",orderSts:" + orderSts);
                if (r == 1) {
                    tvCredit.setBackgroundResource(R.drawable.button_gen);
                    tvCredit.setEnabled(true);
                    tvCredit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            applayCredit(orderId);
                        }
                    });
                } else if (r == 0) {
                    tvCredit.setBackgroundResource(R.drawable.button_grey);
                    tvCredit.setEnabled(false);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<UpLoadEntity> list;

        public MyAdapter(List<UpLoadEntity> list) {
            this.list = list;
        }

        public void notifyChange(List<UpLoadEntity> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.lv_upload_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(list.get(position).getDisplayName());//流程展示名
            if ("false".equals(list.get(position).getIsComplete())) {
//                holder.ivUpload.setImageResource(R.mipmap.cm_btn_more_nor);
                holder.tvIsLoad.setText("未上传");
                holder.tvState.setVisibility(View.GONE);
            } else {
//                holder.ivUpload.setImageResource(R.mipmap.load_sucess);
//                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvIsLoad.setText("已上传");
                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvIsLoad.setBackgroundResource(R.drawable.btn_bg_green);

            }
//            if ("DRAFT".equals(orderStatus)) {
//                holder.tvState.setVisibility(View.GONE);
//            } else if ("WAIT_BACKGROUND_APPROVAL".equals(orderStatus) ||//待审批
//                    "CANCEL_REQUET".equals(orderStatus) ||//取消订单
//                    "NO_PASS".equals(orderStatus) ||//审批不通过
//                    "REJECT".equals(orderStatus) ||//驳回
//                    "PASS".equals(orderStatus) ||//通过
//                    "SUPPLEMENT_DATA".equals(orderStatus) ||//补件中
//                    "ORGANIZATION_APPROVAL".equals(orderStatus) ||//机构审批中
//                    "CREDIT_MANAGER_GRAB".equals(orderStatus) ||//待抢单
//                    "CREDIT_MANAGER_APPROVAL".equals(orderStatus)) {//已抢单
//                holder.tvState.setVisibility(View.VISIBLE);
//            }
//            else if () {
//                holder.tvState.setVisibility(View.VISIBLE);
//            }
//            if (!"DRAFT".equals(orderSts)){
//                holder.tvState.setVisibility(View.VISIBLE);
//            }

            if (list.get(position).getStatus() != null) {
                if (list.get(position).getStatus() == 0) {
                    holder.tvState.setText("驳回");
                    holder.tvState.setBackgroundResource(R.drawable.btn_bg_false);
                } else if (list.get(position).getStatus() == 1) {
                    holder.tvState.setText("通过");
                    holder.tvState.setBackgroundResource(R.drawable.btn_bg_green);
                } else if (list.get(position).getStatus() == 2) {
                    holder.tvState.setText("审核中");
                    holder.tvState.setBackgroundResource(R.drawable.button_gen);
                }
            }

            return convertView;
        }

        public class ViewHolder {
            public final View root;
            public final TextView tvContent;//流程展示名如上传身份证照
            public final TextView tvIsLoad;
            public final TextView tvState;

            public ViewHolder(View root) {
                tvContent = (TextView) root.findViewById(R.id.tvContent);
                tvIsLoad = (TextView) root.findViewById(R.id.tvIsLoad);
                tvState = (TextView) root.findViewById(R.id.tvState);
                this.root = root;
            }
        }
    }


}
