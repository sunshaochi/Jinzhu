package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.BalanceMxEntity;
import com.beyonditsm.financial.util.FinancialUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2016/1/19
 */
public class CouponsAdapter extends BaseAdapter {
    private List<BalanceMxEntity> list;
    private final LayoutInflater inflater;

    public CouponsAdapter(Context context, List<BalanceMxEntity> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    public void setDatas(List<BalanceMxEntity> list){
        this.list=list;
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
        ViewHolder holder ;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.balance_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.Name);
            holder.pay = (TextView) convertView.findViewById(R.id.pay);
            holder.time = (TextView) convertView.findViewById(R.id.Time);
            holder.balance = (TextView) convertView.findViewById(R.id.balance);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        BalanceEntity.RowsEntity rowsEntity = list.get(position);
        holder.name.setText(list.get(position).getRemark());
        holder.time.setText(FinancialUtil.timeToDate(Long.parseLong(list.get(position).getCreateTime())));
        holder.balance.setText("余额："+list.get(position).getBalance());
        DecimalFormat df = new DecimalFormat("##.00");
        try {
            if (Double.valueOf(list.get(position).getAmount()) > 0) {
                holder.pay.setText("+" + df.format(df.parse(list.get(position).getAmount())));
            } else {
                holder.pay.setText(df.format(df.parse(list.get(position).getAmount())));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }
    class ViewHolder{
        TextView name,pay,time,balance;
    }
}
