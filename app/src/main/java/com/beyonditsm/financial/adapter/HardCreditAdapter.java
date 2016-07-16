package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;

import java.util.List;

/**
 * Created by gxy on 2015/11/26
 */
public class HardCreditAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;


    public HardCreditAdapter(List<String> list,Context context){
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    public void notifyChange(List<String> mlist){
        this.list=mlist;
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.hard_credit_item,parent,false);
            holder=new ViewHolder();
            holder.tv_tasklevel= (TextView) convertView.findViewById(R.id.tv_tasklevel);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_tasklevel.setText(list.get(position));

        return convertView;
    }

    static class ViewHolder{
        public TextView tv_tasklevel;
    }
}

