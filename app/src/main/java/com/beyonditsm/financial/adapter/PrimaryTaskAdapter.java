package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.TaskEntity;

import java.util.List;

/**
 * Created by gxy on 2015/11/26
 */
public class PrimaryTaskAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TaskEntity> list;

    public PrimaryTaskAdapter(List<TaskEntity> list,Context context){
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    public void notifyChange(List<TaskEntity> tlist){
        this.list=tlist;
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.hard_credit_item,parent,false);
            holder=new ViewHolder();
            holder.tv_tasklevel= (TextView) convertView.findViewById(R.id.tv_tasklevel);
            holder.iv_finish= (ImageView) convertView.findViewById(R.id.iv_finish);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_tasklevel.setText(list.get(position).getTaskName());
        if(list.get(position).getTaskStatus()==1){
            holder.iv_finish.setVisibility(View.VISIBLE);
        }else if(list.get(position).getTaskStatus()==0){
            holder.iv_finish.setVisibility(View.VISIBLE);
            holder.iv_finish.setImageResource(R.mipmap.status_middle);
        }else if(list.get(position).getTaskStatus()==-1){
            holder.iv_finish.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_tasklevel;
        public ImageView iv_finish;
    }
}

