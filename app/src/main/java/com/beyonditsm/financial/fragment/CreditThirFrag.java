package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.entity.CreditEvent;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 贷款第三步
 * Created by wangbin on 16/3/21.
 */
public class CreditThirFrag extends BaseFragment {
    @ViewInject(R.id.lvCredit)
    private ListView lvCredit;
    @ViewInject(R.id.tvCredit)
    private TextView tvCredit;
    private Gson gson = new Gson();
    private MyAdapter adapter;

    List<UpLoadEntity> datas;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.credit_third_frg, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getUploadList("09401072b5b011e580cf00163e0e011c");
        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CreditUploadAct.class);
                intent.putExtra("orderId", "09401072b5b011e580cf00163e0e011c");
                intent.putExtra("flowId", datas.get(position).getFlowId());
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 提交成功，刷新
     */
    public void onEvent(CreditEvent event) {
        getUploadList("09401072b5b011e580cf00163e0e011c");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setListener() {

    }

    /**
     * 获取上传资料列表
     * @param orderId
     */
    private void getUploadList(final String orderId) {
        RequestManager.getCommManager().getUpLoadList(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = jsonObject.getJSONArray("data");
                datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                }.getType());
                setIsTvClick(datas);
                if (adapter == null) {
                    adapter = new MyAdapter(datas);
                    lvCredit.setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 提交审核
     * @param orderId
     */
    private void applayCredit(String orderId){
        RequestManager.getCommManager().applyCredit(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                EventBus.getDefault().post(new CreditStepAct.FirstEvent(3));
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getContext(),msg);
            }
        });
    }

    private void setIsTvClick(List<UpLoadEntity> list){
        boolean isClick = true;
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                isClick=isClick&&("1".equals(list.get(i).getIsComplete()));
            }
        }
        if(isClick){
            tvCredit.setBackgroundResource(R.drawable.button_gen);
            tvCredit.setEnabled(true);
            tvCredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applayCredit("09401072b5b011e580cf00163e0e011c");
                }
            });
        }else{
            tvCredit.setBackgroundResource(R.drawable.button_grey);
            tvCredit.setEnabled(false);
        }
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<UpLoadEntity> list;

        public MyAdapter(List<UpLoadEntity> list) {
            this.list = list;
        }

        public void notifyChange(List<UpLoadEntity> list) {
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
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.lv_upload_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(list.get(position).getDisplayName());
            if ("0".equals(list.get(position).getIsComplete())) {
//                holder.ivUpload.setImageResource(R.mipmap.cm_btn_more_nor);
                holder.tvIsLoad.setText("未上传");
            } else {
//                holder.ivUpload.setImageResource(R.mipmap.load_sucess);
                holder.tvIsLoad.setText("已上传");
            }
            return convertView;
        }

        public class ViewHolder {
            public final View root;
            public final TextView tvContent;
            public final TextView tvIsLoad;
            public final TextView tvState;

            public ViewHolder(View root) {
                tvContent = (TextView) root.findViewById(R.id.tvContent);
                tvIsLoad = (TextView) root.findViewById(R.id.tvIsLoad);
                tvState = (TextView) root.findViewById(R.id.tvState);
                this.root = root;
            }
        }
    }
}
