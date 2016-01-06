package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ServantWithdrawBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 提现记录适配器
 * Created by Yang on 2015/11/13 0013.
 */
public class WithDrawalsAdp extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ServantWithdrawBean.RowsEntity> list;

//    private String money = null;
    public WithDrawalsAdp(Context context,List<ServantWithdrawBean.RowsEntity> list) {
        this.context = context;
        this.list = list;
//        this.money = money;
        inflater = LayoutInflater.from(context);
    }

    public void notifyChange(List<ServantWithdrawBean.RowsEntity> list){
        this.list = list;
//        this.money = money;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list!=null?list.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.txjl_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ServantWithdrawBean.RowsEntity rowsEntity = list.get(i);
        Date date = new Date(rowsEntity.getOperatTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.txjltime.setText(sdf.format(date));
        holder.txjlmoney.setText(rowsEntity.getAmount());
        holder.txjlstatus.setText(rowsEntity.getAuditStatus());
//        if (money!=null) {
//            holder.txjlmoney.setText(money);
//        }
        return view;
    }

    public class ViewHolder {
        public final TextView txjltime;//时间
        public final TextView txjlmoney;//提现金额
        public final TextView txjlyue;//余额
        public final TextView txjlstatus;//状态
        public final View root;

        public ViewHolder(View root) {
            txjltime = (TextView) root.findViewById(R.id.txjl_time);
            txjlmoney = (TextView) root.findViewById(R.id.txjl_money);
            txjlyue = (TextView) root.findViewById(R.id.txjl_yue);
            txjlstatus = (TextView) root.findViewById(R.id.txjl_status);
            this.root = root;
        }
    }
}
