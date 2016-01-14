package com.beyonditsm.financial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * 我的钱包——订单明细Adapter
 * Created by Administrator on 2016/1/14.
 */
public class OrderDetailAdp extends BaseAdapter {
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, null);
            holder.orderNo = (TextView) convertView.findViewById(R.id.order_no);
            holder.orderStatus = (TextView) convertView.findViewById(R.id.order_staus);
            holder.orderAmount = (TextView) convertView.findViewById(R.id.order_amount);
            holder.orderType = (TextView) convertView.findViewById(R.id.order_type);
            holder.orderTime = (TextView) convertView.findViewById(R.id.order_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if (position==0){
            holder.orderNo.setText("订单号：11111111111");
            holder.orderStatus.setText("审批中");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.tv_price_color));
        }
        if (position==1){
            holder.orderNo.setText("订单号：22222222222");
            holder.orderStatus.setText("已通过");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
        }
        if (position==2){
            holder.orderNo.setText("订单号：33333333333");
            holder.orderStatus.setText("已完成");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
        }
        if (position==3){
            holder.orderNo.setText("订单号：444444444444");
            holder.orderStatus.setText("已驳回");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.backed));
        }
        return convertView;
    }
    class  ViewHolder{
        TextView orderNo,orderStatus,orderAmount,orderType,orderTime;
    }
}
