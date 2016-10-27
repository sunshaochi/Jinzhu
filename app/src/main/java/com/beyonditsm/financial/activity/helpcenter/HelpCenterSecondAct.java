package com.beyonditsm.financial.activity.helpcenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ActicleListBean;
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

/**
 * 帮助中心第二级页面
 * Created by Administrator on 2016/10/10 0010.
 */

public class HelpCenterSecondAct extends BaseActivity {

    @ViewInject(R.id.lrv_helpSecond)
    private LoadRefreshView lrvHelpSecond;
    @ViewInject(R.id.loadingView_helpSecond)
    private LoadingView loadHelpSecond;
    private List<ActicleListBean> acticleList;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_helpsecond);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        final String themeId = getIntent().getStringExtra("themeId");
        String themeName = getIntent().getStringExtra("themeName");
        setTopTitle(themeName);
        setLeftTv("返回");

        lrvHelpSecond.setPullRefreshEnabled(true);
        lrvHelpSecond.setScrollLoadEnabled(false);
        lrvHelpSecond.setPullLoadEnabled(false);
        lrvHelpSecond.setHasMoreData(false);
        lrvHelpSecond.getRefreshableView().setDivider(null);
        lrvHelpSecond.getRefreshableView().setVerticalScrollBarEnabled(false);
        lrvHelpSecond.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                findHelpSecond(themeId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        findHelpSecond(themeId);
    }

    private void findHelpSecond(String id) {
        RequestManager.getCommManager().findHelpDetail(id, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadHelpSecond.loadComplete();
                lrvHelpSecond.onPullUpRefreshComplete();
                lrvHelpSecond.onPullDownRefreshComplete();
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray acticleBean = data.getJSONArray("ActicleList");
                Gson gson = new Gson();
                acticleList = gson.fromJson(acticleBean.toString(), new TypeToken<List<ActicleListBean>>() {
                }.getType());
                lrvHelpSecond.getRefreshableView().setAdapter(new HelpSecondAdapter());
            }

            @Override
            public void onError(int status, String msg) {
                loadHelpSecond.loadError();
                lrvHelpSecond.onPullUpRefreshComplete();
                lrvHelpSecond.onPullDownRefreshComplete();
            }
        });
    }

    private class HelpSecondAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return acticleList.size();
        }

        @Override
        public Object getItem(int position) {
            return acticleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView==null) {
                convertView = LayoutInflater.from(HelpCenterSecondAct.this).inflate(R.layout.lv_helpsecond_item, null);
                holder = new Holder();
                holder.tvHelpSecondTitle = (TextView) convertView.findViewById(R.id.tvHelpSecondTitle);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.tvHelpSecondTitle.setText(acticleList.get(position).getTitle());
            lrvHelpSecond.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("helpSecond",acticleList.get(position));
//                    bundle.putString("title",acticleList.get(position).getTitle());
//                    bundle.putString("content",acticleList.get(position).getContent());
                    gotoActivity(HelpCenterDetailAct.class,false,bundle);
                }
            });
            return convertView;
        }
        class Holder{
            TextView tvHelpSecondTitle;
        }
    }
}
