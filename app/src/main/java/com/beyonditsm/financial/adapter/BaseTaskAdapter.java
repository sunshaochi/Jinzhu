package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * Created by gxy on 2015/11/26.
 */
public class BaseTaskAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public BaseTaskAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 1;
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
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.hard_credit_item,parent,false);
            holder=new ViewHolder();
            holder.tv_tasklevel= (TextView) convertView.findViewById(R.id.tv_tasklevel);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        switch (position){
            case 0:
                holder.tv_tasklevel.setText("请补充您的个人资料");
                break;

        }
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_tasklevel;
    }
}

