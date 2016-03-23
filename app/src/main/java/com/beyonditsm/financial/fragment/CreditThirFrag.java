package com.beyonditsm.financial.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 贷款第三步
 * Created by wangbin on 16/3/21.
 */
public class CreditThirFrag extends BaseFragment{
    @ViewInject(R.id.lvCredit)
    private ListView lvCredit;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.credit_third_frg,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }


    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
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
            return null;
        }
    }
}
