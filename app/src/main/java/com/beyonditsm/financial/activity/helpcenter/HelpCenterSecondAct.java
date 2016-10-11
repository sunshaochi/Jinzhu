package com.beyonditsm.financial.activity.helpcenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.HelpSecondBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.lidroid.xutils.view.annotation.ViewInject;

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
    private List<HelpSecondBean.ActicleListBean> acticleList;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_helpsecond);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        String themeId = getIntent().getStringExtra("themeId");
        String themeName = getIntent().getStringExtra("themeName");
        setTopTitle(themeName);
        setLeftTv("返回");
        findHelpSecond(themeId);
    }

    private void findHelpSecond(String id) {
        RequestManager.getCommManager().findHelpDetail(id, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadHelpSecond.loadComplete();
                JSONObject jsonObject = new JSONObject();
                JSONObject data = jsonObject.getJSONObject("data");
                ResultData<HelpSecondBean> rd = (ResultData<HelpSecondBean>) GsonUtils.json(data.toString(), HelpSecondBean.class);
                acticleList = rd.getData().getActicleList();
                lrvHelpSecond.getRefreshableView().setAdapter(new HelpSecondAdapter());
            }

            @Override
            public void onError(int status, String msg) {
                loadHelpSecond.loadError();
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

                }
            });
            return null;
        }
        class Holder{
            TextView tvHelpSecondTitle;
        }
    }
}
