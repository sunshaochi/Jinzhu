package com.beyonditsm.financial.activity.newscenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.HotNewsAdapter;
import com.beyonditsm.financial.entity.HotNewsEntity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsCenterActivity extends BaseActivity implements LoadRefreshView.OnRefreshListener ,LoadingView.OnRetryListener{

    @ViewInject(R.id.lv_newsList)
    private LoadRefreshView newsList;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    private List<HotNewsEntity> hotNewsList;
    private HotNewsAdapter hotNewsAdapter;
    private int currentP = 1;
    private List<HotNewsEntity> datas = new ArrayList<>();
    private String rows = "10";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_news_center);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        findNewsMore(currentP);
        newsList.setPullRefreshEnabled(true);
        newsList.setScrollLoadEnabled(true);
        newsList.setPullLoadEnabled(true);
        newsList.getFooterLoadingLayout().setVisibility(View.GONE);
        newsList.getRefreshableView().setVerticalScrollBarEnabled(false);
        newsList.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        newsList.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        newsList.setOnRefreshListener(this);
        loadingView.setOnRetryListener(this);
        newsList.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsCenterActivity.this, NewsDetailActivity.class);
                intent.putExtra("hotnews",datas.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void setTopTitle(String title) {
        super.setTopTitle("热点资讯");
    }

    @Override
    public void setLeftTv(String tv) {
        super.setLeftTv("返回");
    }


    public void findNewsMore(int page) {
        CommManager.getCommManager().findNewsMobileMore(page+"",rows,new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                newsList.onPullDownRefreshComplete();
                newsList.onPullUpRefreshComplete();
                loadingView.loadComplete();
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                JSONArray rows = data.getJSONArray("rows");
                Gson gson = new Gson();

                hotNewsList = gson.fromJson(rows.toString(), new TypeToken<List<HotNewsEntity>>() {
                }.getType());





                if (hotNewsList == null || hotNewsList.size() == 0) {
                    if (currentP == 1) {
                        loadingView.noContent();
                    } else {
//                        .setHasMoreData(false);
                    }
                    return;
                }

                if (currentP == 1) {
                    datas.clear();
                }
                datas.addAll(hotNewsList);

                if (hotNewsAdapter == null) {
                    hotNewsAdapter = new HotNewsAdapter(NewsCenterActivity.this, datas);
                    newsList.getRefreshableView().setAdapter(hotNewsAdapter);
//                    hotNewsAdapter.setOnCreditCardListner(mParentActivity);

                } else {
                    hotNewsAdapter.setDatas(datas);
//                    hotNewsAdapter.setOnCreditCardListner(mParentActivity);
                    hotNewsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onError(int status, String msg) {
                currentP = 1;
                loadingView.loadError();
            }
        });
    }



    @Override
    public void OnRetry() {
        currentP = 1;
        findNewsMore(currentP);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        currentP = 1;
        findNewsMore(currentP);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        currentP++;
        findNewsMore(currentP);
    }
}
