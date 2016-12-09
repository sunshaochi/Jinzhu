package com.beyonditsm.financial.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.TiXianBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private  List<TiXianBean> list;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_pay, null);
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

    }

    @Override
    public void setListener() {

    }

    //提现记录适配器
    public class PayAdapter extends BaseAdapter {
        private Context context;
        private List<TiXianBean> txList=new ArrayList<>();
        public PayAdapter(Context context, List<TiXianBean> txList ) {
            this.context = context;
            this.txList=txList;
        }

        @Override
        public int getCount() {
            return txList.size();
        }

        @Override
        public Object getItem(int position) {
            return txList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView != null && convertView.getTag() != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                viewHolder=new ViewHolder();
                convertView = View.inflate(context, R.layout.item_frg_pay, null);
                viewHolder.iv_statu= (ImageView) convertView.findViewById(R.id.iv_statu);
                viewHolder.tv_sj= (TextView) convertView.findViewById(R.id.tv_sj);
                viewHolder.tv_bh= (TextView) convertView.findViewById(R.id.tv_tx);
                viewHolder.tv_je= (TextView) convertView.findViewById(R.id.tv_je);
                convertView.setTag(viewHolder);
            }
            if (!TextUtils.isEmpty(txList.get(position).getCreateTime())){
                viewHolder.tv_sj.setText(txList.get(position).getAmount());
            }
            if (!TextUtils.isEmpty(txList.get(position).getAmount())){
                viewHolder.tv_je.setText(txList.get(position).getAmount());
            }
            if (!TextUtils.isEmpty(txList.get(position).getEnchashmentOrderNo())){
                viewHolder.tv_bh.setText(txList.get(position).getEnchashmentOrderNo());
            }
//            提现状态判定
//            if (!TextUtils.isEmpty(txList.get(position).get))
            return convertView;
        }

        public void update(List<TiXianBean> list) {
            this.txList=list;
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView tv_bh;
            ImageView iv_statu;
            TextView tv_sj;
            TextView tv_je;
        }
    }
        private void getTiXianHistory(String uid,int page,int rows){
            list=new ArrayList<>();
            RequestManager.getWalletManager().gettixianHistory(uid, page, rows, new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) throws JSONException {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    list = gson.fromJson(data.toString(), new TypeToken<List<TiXianBean>>() {
                    }.getType());
                    if (list!=null&&list.size()>0){
                        if (adapter==null){
                            adapter = new PayAdapter(getActivity(),list);
                            plv_jl.getRefreshableView().setAdapter(adapter);
                        }else {
                            adapter.update(list);
                        }

                    }
                }

                @Override
                public void onError(int status, String msg) {

                }
            });
        }
}
