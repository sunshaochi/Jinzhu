package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.OrderListEntity;
import com.beyonditsm.financial.util.FinancialUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * 我的钱包——订单明细Adapter
 * Created by Administrator on 2016/1/14.
 */
public class OrderDetailAdp extends BaseAdapter {
    private List<OrderListEntity.RowsEntity> list;
    private final LayoutInflater inflater;

    public OrderDetailAdp(Context context,List<OrderListEntity.RowsEntity> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<OrderListEntity.RowsEntity> list){
        this.list = list;
        notifyDataSetChanged();
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.order_detail_item, null);
            holder.orderNo = (TextView) convertView.findViewById(R.id.order_no);
            holder.orderStatus = (TextView) convertView.findViewById(R.id.order_staus);
            holder.orderAmount = (TextView) convertView.findViewById(R.id.order_amount);
            holder.orderType = (TextView) convertView.findViewById(R.id.order_type);
            holder.orderTime = (TextView) convertView.findViewById(R.id.order_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        OrderListEntity.RowsEntity rowsEntity = list.get(position);
        holder.orderNo.setText("订单号:"+rowsEntity.getORDER_ID());
        switch (rowsEntity.getORDER_STS()) {
            case "WAIT_APPROVAL":
                holder.orderStatus.setText("审批中");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.tv_price_color));
                break;
            case "APPROVAL_NO_PASS":
                holder.orderStatus.setText("已驳回");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.backed));
                break;
            case "APPROVAL_PASS":
            case "PAY_SUCCESS":
                holder.orderStatus.setText("已通过");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.green_order));
                break;
            case "FINISHED":
                holder.orderStatus.setText("已完成");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.green_order));
                break;
            case "PAY_FAIL":
                holder.orderStatus.setText("支付失败");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.backed));
                break;
            case "PAY_ING":
                holder.orderStatus.setText("支付中");
                holder.orderStatus.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.tv_price_color));
                break;
        }
        DecimalFormat df = new DecimalFormat("##.00");

        try {
            holder.orderAmount.setText(df.format(df.parse(rowsEntity.getCASH_OUT_AMOUNT())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.orderType.setText(rowsEntity.getO_TYPE());
//        holder.orderTime.setText(FinancialUtil.timeToDate(Long.valueOf(rowsEntity.getCREATE_TIME())));
        if (!TextUtils.isEmpty(rowsEntity.getCREATE_TIME())) {
            holder.orderTime.setText(FinancialUtil.timeToDate(Long.valueOf(rowsEntity.getCREATE_TIME())));
        }else{
            holder.orderTime.setText("----");
        }
        return convertView;
    }
    class  ViewHolder{
        TextView orderNo,orderStatus,orderAmount,orderType,orderTime;
    }
}
