package com.beyonditsm.financial.fragment;

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
        if ("PASS".equals(orderStatus)) {
            ivProgress.setBackgroundResource(R.mipmap.progress_04);
            tvCredit.setVisibility(View.GONE);
        }
        getUploadList(orderId);
        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CreditUploadAct.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("flowId", datas.get(position).getFlowId());
                intent.putExtra("status", datas.get(position).getStatus());
                getActivity().startActivity(intent);
            }
        });
    }


    /**
     * 提交成功，刷新
     */
    public void onEvent(CreditEvent event) {
        getUploadList(orderId);
        applayStatus(orderId);
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
     * @param orderId
     */
    private void getUploadList(final String orderId) {

        RequestManager.getCommManager().getUpLoadList(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = jsonObject.getJSONArray("data");
                datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                }.getType());
                applayStatus(orderId);
                setIsTvClick(datas);

                if (adapter == null) {
                    adapter = new MyAdapter(datas);
                    lvCredit.setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                lvCreditThird.loadComplete();
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

    /**
     * 提交审核
     *
     * @param orderId
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
                isClick = isClick && ("1".equals(list.get(i).getIsComplete()));
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

    /**
     * 是否需要增信资料
     *
     * @param orderId
     */
    private void findOrderFlow(final String orderId) {
        RequestManager.getCommManager().findOrderFlow(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                if (!"没有可用的增信流程".equals(message)) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    List<UpLoadEntity> datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                    }.getType());
                    CreditStepAct.upList = datas;

                } else {
                    CreditStepAct.upList = null;
                }
                if (act_type == 0) {
//                    EventBus.getDefault().post(new CreditStepAct.FirstEvent(3, orderId));
                    llUploadSuccess.setVisibility(View.VISIBLE);
                    llUpload.setVisibility(View.GONE);
                    findUploadSuccess();
                } else {
                    MyToastUtils.showShortToast(getContext(), "订单已提交，请耐心等待审批");
                    getActivity().sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
                    getActivity().sendBroadcast(new Intent(MyCreditDetailFragment.UPDATE_ORDER));
                    getActivity().sendBroadcast(new Intent(MyCreditStatusFragment.UPDATE_DEAL));
                    getActivity().finish();
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
                    Intent intent = new Intent(getActivity(), SubFlowAct.class);
                    intent.putParcelableArrayListExtra("sub_list", (ArrayList<? extends Parcelable>) CreditStepAct.upList);
                    intent.putExtra("order_id", orderId);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    getActivity().finish();
                    AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                }
            }
        });
        crethrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                getActivity().finish();
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
            holder.tvContent.setText(list.get(position).getDisplayName());
            if ("0".equals(list.get(position).getIsComplete())) {
//                holder.ivUpload.setImageResource(R.mipmap.cm_btn_more_nor);
                holder.tvIsLoad.setText("未上传");
                holder.tvState.setVisibility(View.GONE);
            } else {
//                holder.ivUpload.setImageResource(R.mipmap.load_sucess);
//                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvIsLoad.setText("已上传");
                holder.tvIsLoad.setBackgroundResource(R.drawable.btn_bg_green);

            }
            if ("DRAFT".equals(orderStatus)) {
                holder.tvState.setVisibility(View.GONE);
            } else if ("WAIT_BACKGROUND_APPROVAL".equals(orderStatus) ||//待审批
                    "CANCEL_REQUET".equals(orderStatus) ||//取消订单
                    "NO_PASS".equals(orderStatus) ||//审批不通过
                    "REJECT".equals(orderStatus) ||//驳回
                    "PASS".equals(orderStatus) ||//通过
                    "SUPPLEMENT_DATA".equals(orderStatus) ||//补件中
                    "ORGANIZATION_APPROVAL".equals(orderStatus) ||//机构审批中
                    "CREDIT_MANAGER_GRAB".equals(orderStatus) ||//待抢单
                    "CREDIT_MANAGER_APPROVAL".equals(orderStatus)) {//已抢单
                holder.tvState.setVisibility(View.VISIBLE);
            }
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
            public final TextView tvContent;
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
