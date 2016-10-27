package com.beyonditsm.financial.activity.helpcenter;

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
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.HelpAllBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HelpCenterActivity extends BaseActivity {

    @ViewInject(R.id.lpv_helpAlls)
    private LoadRefreshView lpvHelpAlls;
    @ViewInject(R.id.loadingView_help)
    private LoadingView loadHelp;

    private List<HelpAllBean> helpAllList;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_help_center);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("帮助中心");
        setLeftTv("返回");
        lpvHelpAlls.setPullRefreshEnabled(true);
        lpvHelpAlls.setScrollLoadEnabled(false);
        lpvHelpAlls.setPullLoadEnabled(false);
        lpvHelpAlls.setHasMoreData(false);
        lpvHelpAlls.getRefreshableView().setDivider(null);
        lpvHelpAlls.getRefreshableView().setVerticalScrollBarEnabled(false);
        lpvHelpAlls.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                findHelpAlls();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        loadHelp.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findHelpAlls();
            }
        });
        findHelpAlls();
    }

    private void findHelpAlls() {
        RequestManager.getCommManager().findHelpAlls(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadHelp.loadComplete();
                lpvHelpAlls.onPullDownRefreshComplete();
                lpvHelpAlls.onPullUpRefreshComplete();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                helpAllList = gson.fromJson(data.toString(), new TypeToken<List<HelpAllBean>>() {
                }.getType());
                lpvHelpAlls.getRefreshableView().setAdapter(new LvHelpAdapter());
            }

            @Override
            public void onError(int status, String msg) {
                loadHelp.loadError();
                lpvHelpAlls.onPullUpRefreshComplete();
                lpvHelpAlls.onPullDownRefreshComplete();
            }
        });
    }

    private class LvHelpAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return helpAllList.size();
        }

        @Override
        public Object getItem(int position) {
            return helpAllList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HelpCenterActivity.this).inflate(R.layout.lv_helpalls_item, null);
                holder = new Holder();
                holder.ivHelpIcon = (ImageView) convertView.findViewById(R.id.ivHelpIcon);
                holder.tvHelpTitle = (TextView) convertView.findViewById(R.id.tvHelpTitle);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            String themeName = helpAllList.get(position).getThemeName();
            holder.tvHelpTitle.setText(themeName);

//            if (position == 0) {
//                holder.ivHelpIcon.setBackgroundResource(R.mipmap.icon_user_help);
//            } else if (position == 1) {
//                holder.ivHelpIcon.setBackgroundResource(R.mipmap.icon_service_help);
//            } else if (position == 2) {
//                holder.ivHelpIcon.setBackgroundResource(R.mipmap.icon_normal_help);
//            }
            lpvHelpAlls.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HelpCenterActivity.this, HelpCenterSecondAct.class);
                    String themeId = helpAllList.get(position).getThemeId();
                    intent.putExtra("themeName",helpAllList.get(position).getThemeName());
                    intent.putExtra("themeId", themeId);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class Holder {
            TextView tvHelpTitle;
            ImageView ivHelpIcon;
        }
    }
}
