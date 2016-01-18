package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.BalanceEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class BalanceAdapter extends BaseAdapter {
    private List<BalanceEntity.RowsEntity> list;
    private Context context;

    public BalanceAdapter(Context context,List<BalanceEntity.RowsEntity> list) {
        this.list = list;
        this.context = context;
    }
    public void setDatas(List<BalanceEntity.RowsEntity> list){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.Name);
            holder.pay = (TextView) convertView.findViewById(R.id.pay);
            holder.time = (TextView) convertView.findViewById(R.id.Time);
            holder.balance = (TextView) convertView.findViewById(R.id.balance);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView name,pay,time,balance;
    }
}
