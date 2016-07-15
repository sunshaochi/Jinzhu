package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.OrderDealEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * 我的贷款——贷款详情Adapter
 * Created by Administrator on 2015/12/15.
 */
public class OrderDetailAdapter extends BaseAdapter implements View.OnClickListener {
    private List<OrderDealEntity> orderList;
    private Context context;
    private final LayoutInflater inflater;

    public OrderDetailAdapter(Context context, List<OrderDealEntity> orderList) {
        this.orderList = orderList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderList != null ? orderList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return orderList != null ? orderList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.ciedit_lv_item, null);
            holder.ivIconStatus = (ImageView) convertView.findViewById(R.id.icon_credit_status);
            holder.tvCreditStatus = (TextView) convertView.findViewById(R.id.tv_credit_status);
            holder.tvCreditTime = (TextView) convertView.findViewById(R.id.tv_credit_time);
            holder.ivConnection = (ImageView) convertView.findViewById(R.id.iv_connetion);
            holder.rlCreditItem = (LinearLayout) convertView.findViewById(R.id.credit_item);
            holder.llListHead = (RelativeLayout) convertView.findViewById(R.id.ll_list_head);
            holder.llListFoot = (RelativeLayout) convertView.findViewById(R.id.ll_list_bottom);
            holder.tvCreditStatus.setSelected(true);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderDealEntity ode = orderList.get(position);
//        if (position==0){
//            holder.llListHead.setVisibility(View.VISIBLE);
//        }
//        if (position==orderList.size()-1){
//            holder.llListFoot.setVisibility(View.VISIBLE);
//        }
        if (!TextUtils.isEmpty(ode.getDealName())) {
            holder.tvCreditStatus.setText(ode.getDealName());
        }

        Date date = new Date(ode.getDealTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.tvCreditTime.setText(sdf.format(date));

//        if (!TextUtils.isEmpty(ode.getUserId())) {
//            holder.ivConnection.setVisibility(View.VISIBLE);
//            holder.ivConnection.setBackgroundResource(R.drawable.button_chat_r);
//            holder.ivConnection.setTag(position);
//            holder.ivConnection.setOnClickListener(this);
//        }
        if (!TextUtils.isEmpty(ode.getOrderFlowStatus())&&!TextUtils.isEmpty(ode.getUserId())) {
            if ("CUSTOMER_SUBMIT".equals(ode.getOrderFlowStatus())) {//客户提交订单
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_f);
            }
            if ("PLATFORM_PASS".equals(ode.getOrderFlowStatus())) {//平台审批通过
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_f);
            }
            if ("PLATFORM_REJECT".equals(ode.getOrderFlowStatus())){//平台审批不通过
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_failed);
                holder.tvCreditStatus.setText("平台审批不通过");
            }
            if ("CREDITMANAGER_CATCH_ORDER".equals(ode.getOrderFlowStatus())) {//信贷经理已抢单
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_sec);
                holder.ivConnection.setVisibility(View.VISIBLE);
                holder.ivConnection.setBackgroundResource(R.drawable.button_chat_r);
                holder.ivConnection.setTag(position);
                holder.ivConnection.setOnClickListener(this);
            }
            if ("CREDIT_MANAGER_PASS".equals(ode.getOrderFlowStatus())) {//信贷经理审批通过
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_f);
                holder.ivConnection.setVisibility(View.VISIBLE);
                holder.ivConnection.setBackgroundResource(R.drawable.button_chat_r);
                holder.ivConnection.setTag(position);
                holder.ivConnection.setOnClickListener(this);
            }
            if ("CREDIT_MANAGER_SUPPLEMENT".equals(ode.getOrderFlowStatus())) {//信贷经理要求补件
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_middle);
                holder.ivConnection.setVisibility(View.VISIBLE);
                holder.ivConnection.setBackgroundResource(R.drawable.button_chat_r);
                holder.ivConnection.setTag(position);
                holder.ivConnection.setOnClickListener(this);
            }
            if (!"CREDIT_MANAGER_PASS".equals(ode.getOrderFlowStatus())&&!"CREDITMANAGER_CATCH_ORDER".equals(ode.getOrderFlowStatus())&&!"CREDIT_MANAGER_SUPPLEMENT".equals(ode.getOrderFlowStatus())){
                holder.ivConnection.setVisibility(View.GONE);
            }
            if ("ORDER_AUTO_RELEASE".equals(ode.getOrderFlowStatus())) {//订单处理超时
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_failed);
            }
            if ("ORG_PASS".equals(ode.getOrderFlowStatus())) {//机构审批通过
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_suc);
            }
            if ("ORG_REJECT".equals(ode.getOrderFlowStatus())) {//机构驳回申请
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_failed);
            }
            if ("ORG_SUPPLEMENT".equals(ode.getOrderFlowStatus())) {//机构要求补件
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_middle);
            }
            if ("CUSTOMER_CANCEL_ORDER".equals(ode.getOrderFlowStatus())){//取消订单
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_failed);
            }
            if ("DATA_REJECT_UPDATA".equals(ode.getOrderFlowStatus())){//资料驳回
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_failed);
            }
            if (!"DATA_REJECT_UPDATA".equals(ode.getOrderFlowStatus())&&!"CUSTOMER_CANCEL_ORDER".equals(ode.getOrderFlowStatus())&&!"ORG_REJECT".equals(ode.getOrderFlowStatus())&&!"ORDER_AUTO_RELEASE".equals(ode.getOrderFlowStatus())){
                holder.ivIconStatus.setBackgroundResource(R.drawable.ico_status_f);
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_connetion:
                int position = (int) view.getTag();
                String userId = orderList.get(position).getUserId();
                String roleName = orderList.get(position).getNickName();
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(context, userId, roleName);
                    FriendBean friendBean = new FriendBean();
                    friendBean.setUserId(userId);
                    friendBean.setUserName(roleName);
                    FriendDao.saveMes(friendBean);
                }
                break;
        }
    }

    class ViewHolder {
        ImageView ivIconStatus;
        TextView tvCreditStatus;
        TextView tvCreditTime;
        ImageView ivConnection;
        LinearLayout rlCreditItem;
        RelativeLayout llListHead,llListFoot;
    }
}
