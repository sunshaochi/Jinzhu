package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.HotNewsEntity;
import com.beyonditsm.financial.util.MyLogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xuleyuan on 2016/10/9.
 */

public class HotNewsAdapter extends BaseAdapter {
    private List<HotNewsEntity> list;
    private final LayoutInflater inflater;

    public HotNewsAdapter(Context context, List<HotNewsEntity> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    public void setDatas(List<HotNewsEntity> list){
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
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.home_hot_news_item,null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MyLogUtils.info("tvTitle:" + list.get(position).getTitle()+"");
        holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
        holder.tvTime = (TextView) convertView.findViewById(R.id.time);
        holder.tvTitle.setText(list.get(position).getTitle());
        Date date = new Date(list.get(position).getCreateTime());
        @SuppressLint("SimpleDateFormat") DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        holder.tvTime.setText(df.format(date));

        return convertView;
    }
    class ViewHolder{
        TextView tvTitle,tvTime;
    }
}
