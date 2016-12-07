package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.LoanTermInfo;
import com.beyonditsm.financial.entity.MoneyScopeInfo;
import com.beyonditsm.financial.entity.OrderListEntity;
import com.beyonditsm.financial.entity.OrgTypeInfo;
import com.beyonditsm.financial.entity.ProductOrderInfo;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.util.FinancialUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * Created by xuleyuan on 2016/7/14
 * Sort params in CreditFragment
 */
public class ProductSortAdapter extends BaseAdapter {
    private List<?> list;
    private Context context;
    public final static short BANK = 1;
    public final static short MONEY = 3;
    public final static short TIME = 4;
    public final static short SORT = 2;
    private short type; //Param <?> type chosen

    public ProductSortAdapter(List <?> list, Context context,short type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    public void setDatas(List<LoanTermInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
        ViewHolder holder;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_item, null);
            holder.param = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        switch (type) {
            case BANK:
                holder.param.setText(((ProductSortEntity.OrgTypeBean) list.get(position)).getOptionName());
                break;
            case MONEY:
                holder.param.setText(((ProductSortEntity.MoneyScopeBean) list.get(position)).getOptionName());
                break;
            case SORT:
                holder.param.setText(((ProductSortEntity.ProductOrderBean) list.get(position)).getOptionName());
                break;
            case TIME:
                holder.param.setText(((ProductSortEntity.LoanTermBean) list.get(position)).getOptionName());
                break;
        }
        return convertView;
    }
    class  ViewHolder{
        TextView param;
    }

}
