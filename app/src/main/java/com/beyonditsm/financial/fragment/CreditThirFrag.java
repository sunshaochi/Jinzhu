package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.lidroid.xutils.view.annotation.ViewInject;
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
        getUploadList("09401072b5b011e580cf00163e0e011c");
        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CreditUploadAct.class);
                intent.putExtra("orderId","09401072b5b011e580cf00163e0e011c");
                intent.putExtra("flowId",datas.get(position).getFlowId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void setListener() {

    }


    private void getUploadList(final String orderId) {
        RequestManager.getCommManager().getUpLoadList(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = jsonObject.getJSONArray("data");
                datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                }.getType());
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
                holder.ivUpload.setImageResource(R.mipmap.cm_btn_more_nor);
            } else {
                holder.ivUpload.setImageResource(R.mipmap.load_sucess);
            }
            return convertView;
        }

        public class ViewHolder {
            public final ImageView ivUpload;
            public final View root;
            public final TextView tvContent;

            public ViewHolder(View root) {
                ivUpload = (ImageView) root.findViewById(R.id.ivUpload);
                tvContent = (TextView) root.findViewById(R.id.tvContent);
                this.root = root;
            }
        }
    }
}
