package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.OrderListEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 我的钱包——订单明细Adapter
 * Created by Administrator on 2016/1/14.
 */
public class OrderDetailAdp extends BaseAdapter {
    private List<OrderListEntity.RowsEntity> list;
    private Context context;
    public OrderDetailAdp(Context context,List<OrderListEntity.RowsEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setDatas(List<OrderListEntity.RowsEntity> list){
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
<<<<<<< HEAD
        OrderListEntity.RowsEntity rowsEntity = list.get(position);
        holder.orderNo.setText(rowsEntity.getORDER_ID());
        holder.orderStatus.setText(rowsEntity.getORDER_STS());
        holder.orderAmount.setText(rowsEntity.getCASH_OUT_AMOUNT());
        holder.orderType.setText(rowsEntity.getO_TYPE());
        Date date = new Date(rowsEntity.getCREATE_TIME());
=======
        OrderListEntity orderListEntity = list.get(position);
//        holder.orderNo.setText(orderListEntity.getORDER_ID());
//        holder.orderStatus.setText(orderListEntity.getORDER_STS());
//        holder.orderAmount.setText(orderListEntity.getCASH_OUT_AMOUNT());
//        holder.orderType.setText(orderListEntity.getO_TYPE());
//        Date date = new Date(orderListEntity.getCREATE_TIME());
>>>>>>> 9243118785e373150efb55640c9cd47e924837ed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        holder.orderTime.setText(sdf.format(date));
//        if (position==0){
//            holder.orderNo.setText("订单号：11111111111");
//            holder.orderStatus.setText("审批中");
//            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.tv_price_color));
//        }
//        if (position==1){
//            holder.orderNo.setText("订单号：22222222222");
//            holder.orderStatus.setText("已通过");
//            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
//        }
//        if (position==2){
//            holder.orderNo.setText("订单号：33333333333");
//            holder.orderStatus.setText("已完成");
//            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
//        }
//        if (position==3){
//            holder.orderNo.setText("订单号：444444444444");
//            holder.orderStatus.setText("已驳回");
//            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.backed));
//        }
        return convertView;
    }
    class  ViewHolder{
        TextView orderNo,orderStatus,orderAmount,orderType,orderTime;
    }
}
