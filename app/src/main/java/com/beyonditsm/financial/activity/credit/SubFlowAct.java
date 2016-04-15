package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.CreditEvent;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.view.LoadingView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 提交增信材料
 * Created by wangbin on 16/3/31.
 */
public class SubFlowAct extends BaseActivity {
    @ViewInject(R.id.lvCredit)
    private ListView lvCredit;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    private Gson gson = new Gson();
    List<UpLoadEntity> datas;
    private String orderId;

    private MyAdapter adapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_sub_flow);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTopTitle("提交增信资料");
        orderId = getIntent().getStringExtra("order_id");
        datas = getIntent().getParcelableArrayListExtra("sub_list");
        loadView.setNoContentTxt("暂无增信资料需要上传");
        if (datas != null && datas.size() > 0) {
            loadView.loadComplete();
            adapter = new MyAdapter(datas);
            lvCredit.setAdapter(adapter);
        } else {
            findOrderFlow(orderId);
        }
        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubFlowAct.this, CreditUploadAct.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("flowId", datas.get(position).getFlowId());
                startActivity(intent);
            }
        });

        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findOrderFlow(orderId);
            }
        });
    }

    /**
     * 提交成功，刷新
     */
    public void onEvent(CreditEvent event) {
        findOrderFlow(orderId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 是否需要增信资料
     *
     * @param orderId
     */
    private void findOrderFlow(String orderId) {
        RequestManager.getCommManager().findOrderFlow(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                if (message.equals("查询流程成功")) {
                    JSONArray array = jsonObject.getJSONArray("data");
//                List<UpLoadEntity> datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
//                }.getType());
                    datas = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                    }.getType());

                    if (adapter == null) {
                        adapter = new MyAdapter(datas);
                        lvCredit.setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas);
                    }
                }else{
//                    JSONObject data = jsonObject.getJSONObject("data");
                    loadView.setNoContentTxt("暂无增信资料需要上传");
                }
            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
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
                convertView = View.inflate(SubFlowAct.this, R.layout.lv_upload_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(list.get(position).getDisplayName());
            if ("0".equals(list.get(position).getIsComplete())) {
//                holder.ivUpload.setImageResource(R.mipmap.cm_btn_more_nor);
                holder.tvIsLoad.setText("未上传");
                holder.tvState.setVisibility(View.GONE);
            } else {
//                holder.ivUpload.setImageResource(R.mipmap.load_sucess);
                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvIsLoad.setText("已上传");
                holder.tvIsLoad.setBackgroundResource(R.drawable.btn_bg_green);
            }
            if (list.get(position).getStatus() != null) {
                if (list.get(position).getStatus() == 0) {
                    holder.tvState.setText("驳回");
                    holder.tvState.setBackgroundResource(R.drawable.btn_bg_false);
                } else if (list.get(position).getStatus() == 1) {
                    holder.tvState.setText("通过");
                    holder.tvState.setBackgroundResource(R.drawable.btn_bg_green);
                } else if (list.get(position).getStatus() == 2) {
                    holder.tvState.setText("审核中");
                }
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
