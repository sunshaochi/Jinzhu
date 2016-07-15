package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;

import java.util.List;

/**
 * Created by gxy on 2015/11/26
 */
public class TaskDetailAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;
    public TaskDetailAdapter(List<String> list,Context context){
        this.list=list;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.task_detail_item,parent,false);
            holder=new ViewHolder();
            holder.tv_degree= (TextView) convertView.findViewById(R.id.tv_degree);
            holder.iv_yes= (ImageView) convertView.findViewById(R.id.iv_yes);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_degree.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_degree;
        public ImageView iv_yes;
    }
}

