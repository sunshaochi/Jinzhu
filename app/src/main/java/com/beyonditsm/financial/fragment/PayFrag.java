package com.beyonditsm.financial.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * 提现记录
 */

public class PayFrag extends BaseFragment {
    @ViewInject(R.id.plv_jl)
    private PullToRefreshListView plv_jl;//提现记录下拉刷新
    @ViewInject(R.id.loadView)
    private LoadingView loadingView;

    private PayAdapter adapter;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_pay,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        plv_jl.setPullRefreshEnabled(true);
        plv_jl.setScrollLoadEnabled(false);
        plv_jl.setPullLoadEnabled(true);
        plv_jl.setHasMoreData(true);
        plv_jl.getRefreshableView().setDividerHeight(20);
        plv_jl.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv_jl.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv_jl.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        loadingView.loadComplete();
        adapter=new PayAdapter(getActivity());
        plv_jl.getRefreshableView().setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    //提现记录适配器
    public class PayAdapter extends BaseAdapter{
        private Context context;

        public PayAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return 5;
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
            convertView=View.inflate(context,R.layout.item_frg_pay,null);
            return convertView;
        }
    }

}
