package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.QueryBankCardEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class BindBankCardAdp extends BaseAdapter {
    private Context context;
    private List<QueryBankCardEntity> list;
    private final LayoutInflater inflater;

    public BindBankCardAdp(Context context,List<QueryBankCardEntity> list) {
        this.context =context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setNotifyChange(List<QueryBankCardEntity> list){
        this.list = list;
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
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_bindcard_item, null);
            holder.tvBankName = (TextView) convertView.findViewById(R.id.tv_bankName);
            holder.tvCardNo = (TextView) convertView.findViewById(R.id.tv_cardNo);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        QueryBankCardEntity queryBankCardEntity = list.get(position);
        holder.tvBankName.setText(queryBankCardEntity.getBankName());
        holder.tvCardNo.setText(queryBankCardEntity.getCardNo());
        return convertView;
    }
    class ViewHolder{
        TextView tvBankName,tvCardNo,tvStatus;
    }
}
