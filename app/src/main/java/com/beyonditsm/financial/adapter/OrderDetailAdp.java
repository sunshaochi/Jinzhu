package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.OrderListEntity;
import com.beyonditsm.financial.util.FinancialUtil;

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
        OrderListEntity.RowsEntity rowsEntity = list.get(position);
        holder.orderNo.setText("订单号:"+rowsEntity.getORDER_ID());
        if (rowsEntity.getORDER_STS().equals("WAIT_APPROVAL")){
            holder.orderStatus.setText("审批中");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.tv_price_color));
        }else if (rowsEntity.getORDER_STS().equals("APPROVAL_NO_PASS")){
            holder.orderStatus.setText("已驳回");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.backed));
        }else if (rowsEntity.getORDER_STS().equals("APPROVAL_PASS")||rowsEntity.getORDER_STS().equals("PAY_SUCCESS")){
            holder.orderStatus.setText("已通过");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
        }else if (rowsEntity.getORDER_STS().equals("FINISHED")){
            holder.orderStatus.setText("已完成");
            holder.orderStatus.setTextColor(convertView.getResources().getColor(R.color.green_order));
        }
        holder.orderAmount.setText(rowsEntity.getCASH_OUT_AMOUNT());
        holder.orderType.setText(rowsEntity.getO_TYPE());
        holder.orderTime.setText(FinancialUtil.timeToDate(Long.valueOf(rowsEntity.getCREATE_TIME())));
        return convertView;
    }
    class  ViewHolder{
        TextView orderNo,orderStatus,orderAmount,orderType,orderTime;
    }
}
