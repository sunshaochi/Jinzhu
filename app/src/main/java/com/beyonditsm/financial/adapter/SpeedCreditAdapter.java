package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.CreditSpeedEntity;

import java.util.List;

/**
 * 急借通列表adapter
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditAdapter extends BaseAdapter {
    private Context context;
    private List<CreditSpeedEntity> list;
    public SpeedCreditAdapter(Context context, List<CreditSpeedEntity> list) {
        this.context = context;
        this.list = list;
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
        Holder holder = null;
        if (convertView==null){
            holder = new Holder();
            convertView = View.inflate(context, R.layout.speed_credit_item,null);
            holder.ivProductLogo = (ImageView) convertView.findViewById(R.id.iv_productLogo);
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
            holder.tvProductChara = (TextView) convertView.findViewById(R.id.tv_productChara);
            holder.tvCostDescribe = (TextView) convertView.findViewById(R.id.tv_costDescribe);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
            holder.tvRate = (TextView) convertView.findViewById(R.id.tv_rate);
            holder.tvLoanPeriod = (TextView) convertView.findViewById(R.id.tv_loanPeriod);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        CreditSpeedEntity creditSpeedEntity = list.get(position);
        holder.tvProductName.setText(creditSpeedEntity.getProductName());
        holder.tvProductChara.setText(creditSpeedEntity.getProductChara());
        holder.tvCostDescribe.setText(creditSpeedEntity.getCostDescribe());
        holder.tvTime.setText(creditSpeedEntity.getTimeMinVal()+"-"+ creditSpeedEntity.getTimeMaxVal()+"周");
        holder.tvValue.setText(creditSpeedEntity.getMinVal()+"-"+creditSpeedEntity.getMaxVal());
        holder.tvRate.setText(creditSpeedEntity.getMinRate()+"-"+creditSpeedEntity.getMaxRate()+"%(天)");
        holder.tvLoanPeriod.setText(creditSpeedEntity.getLoanPeriod()+"个工作日");

        return convertView;
    }

    class Holder{
        ImageView ivProductLogo;
        TextView tvProductName;
        TextView tvProductChara;
        TextView tvCostDescribe;
        TextView tvTime;
        TextView tvValue;
        TextView tvRate;
        TextView tvLoanPeriod;
    }
}
