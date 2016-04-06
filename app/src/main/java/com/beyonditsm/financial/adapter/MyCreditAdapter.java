package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.MyCreditBean;

import java.util.List;

/**
 * 我的贷款适配器
 * Created by wangbin on 15/11/12.
 */
public  class MyCreditAdapter extends BaseAdapter {
    private Context context;
    private List<MyCreditBean.RowsEntity> list;
    private String orderId;

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数

    public void setDatas(List<MyCreditBean.RowsEntity> list,String orderId) {
        this.list = list;
        this.orderId = orderId;
        notifyDataSetChanged();
    }

    public MyCreditAdapter(Context context, List<MyCreditBean.RowsEntity> list,String orderId) {
        this.context = context;
        this.list = list;
        this.orderId = orderId;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.lv_mycredit_item, null);
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
            holder.tvTotalAmount = (TextView) convertView.findViewById(R.id.tv_totalAmount);
            holder.tvPeriodsAmount = (TextView) convertView.findViewById(R.id.tv_periodsAmount);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ivRedPoint = (ImageView) convertView.findViewById(R.id.ivMs);
            holder.rlCreditList = (RelativeLayout) convertView.findViewById(R.id.rl_creditList);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(orderId)) {
            if (list.get(position).getId().equals(orderId)) {
                holder.ivRedPoint.setVisibility(View.VISIBLE);
            }
        }else{
            holder.ivRedPoint.setVisibility(View.GONE);
        }
        holder.tvProductName.setText(list.get(position).getProductName());
        holder.tvTotalAmount.setText(df.format(Double.valueOf(list.get(position).getTotalAmount()) / 10000) + "万");
        holder.tvPeriodsAmount.setText(df.format(Double.valueOf(list.get(position).getPeriodsAmount()) / 10000) + "万");
        String status = list.get(position).getOrderSts();
        if ("CREDIT_MANAGER_APPROVAL".equals(status) ||
                "CREDIT_MANAGER_GRAB".equals(status) || "ORGANIZATION_APPROVAL".equals(status)) {
            holder.tvName.setText("审批中");
            holder.tvName.setTextColor(Color.parseColor("#ff6633"));

        } else if ("PASS".equals(status)) {
            holder.tvName.setText("审批通过");
            holder.tvName.setTextColor(Color.parseColor("#1fd45f"));

        } else if ("WAIT_BACKGROUND_APPROVAL".equals(status)
                ) {
            holder.tvName.setText("待审批");
            holder.tvName.setTextColor(Color.parseColor("#ff6633"));

        } else if ("SUPPLEMENT_DATA".equals(status)) {
            holder.tvName.setText("补件中");
            holder.tvName.setTextColor(Color.parseColor("#ff6633"));

        } else if ("NO_PASS".equals(status)) {
            holder.tvName.setText("审批不通过");
            holder.tvName.setTextColor(Color.parseColor("#ff0000"));
        } else if ("CANCEL_REQUET".equals(status)) {
            holder.tvName.setText("已取消");
            holder.tvName.setTextColor(Color.parseColor("#ff8383"));
        } else if ("DRAFT".equals(status)) {
            holder.tvName.setText("资料待上传");
            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvProductName, tvTotalAmount, tvPeriodsAmount, tvName;
        ImageView ivRedPoint;//推送红点
        RelativeLayout rlCreditList;
    }

}

