package com.beyonditsm.financial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * Created by Administrator on 2016/1/14.
 */
public class BalanceAdapter extends BaseAdapter {
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
