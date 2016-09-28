package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beyonditsm.financial.R;

/**
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditAdapter extends BaseAdapter {
    private Context context;

    public SpeedCreditAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = View.inflate(context, R.layout.speed_credit_item,null);
        }
        return convertView;
    }
}
