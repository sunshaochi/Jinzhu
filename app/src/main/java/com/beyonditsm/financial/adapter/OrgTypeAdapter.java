package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ProductSortEntity;

import java.util.List;

/**
 * Created by xuleyuan on 2016/7/21.
 */
public class OrgTypeAdapter extends BaseAdapter {
    private List<ProductSortEntity.OrgTypeBean> orgTypeInfos;
    private Context context;

    public OrgTypeAdapter(List<ProductSortEntity.OrgTypeBean> orgType, Context context) {
        this.orgTypeInfos = orgType;
        this.context = context;
    }

    public void setDatas(List<ProductSortEntity.OrgTypeBean> list) {
        this.orgTypeInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orgTypeInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return orgTypeInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.org_type_list_item, null);
            holder.param = (TextView) convertView.findViewById(R.id.tv_org_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.param.setText(orgTypeInfos.get(position).getOptionName());
        return convertView;
    }

    class ViewHolder {
        TextView param;
    }

}

