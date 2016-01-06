package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.NewWorkAct;
import com.beyonditsm.financial.adapter.HomeCreditAdapter;
import com.beyonditsm.financial.entity.HomeHotProductEntity;
import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.plv_hotCredit)
    private LoadRefreshView plvHotCredit;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    @ViewInject(R.id.ll_credit)
    private LinearLayout llCredit;
    @ViewInject(R.id.ll_tillage)
    private LinearLayout llTillage;
    @ViewInject(R.id.ll_work)
    private LinearLayout llWork;
    private CreditFragment creditFragment;
    private int currentPage = 1;
    private HomeCreditAdapter adapter;
    private List<HomeHotProductEntity> hotList;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        getHotProductList(currentPage);

        plvHotCredit.setPullRefreshEnabled(true);
        plvHotCredit.setScrollLoadEnabled(false);
        plvHotCredit.setPullLoadEnabled(false);
        plvHotCredit.setHasMoreData(true);
        plvHotCredit.getRefreshableView().setDivider(null);
        plvHotCredit.getRefreshableView().setVerticalScrollBarEnabled(false);
        plvHotCredit.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        plvHotCredit.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentPage = 1;
                getHotProductList(currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getHotProductList(currentPage);
            }
        });

//        MaterialRippleLayout.on(llCredit)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
//        MaterialRippleLayout.on(llTillage)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
//        MaterialRippleLayout.on(llWork)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
        getHotProductList(currentPage);
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getHotProductList(currentPage);
            }
        });
    }

    @Override
    public void setListener() {
        plvHotCredit.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HomeCreditDetailAct.class);
                intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
                intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY+"");
                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME,ConstantValue.CREDIT_MONTH+"");
                intent.putExtra(HomeCreditDetailAct.CREDIT_NAME,datas.get(position).getProductName());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.ll_credit, R.id.ll_tillage, R.id.ll_work})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_credit://我要贷款
                EventBus.getDefault().post(new ToSwitchEvent());
                break;
            case R.id.ll_tillage://信用耕耘
                if(TextUtils.isEmpty(SpUtils.getRoleName(context).toString())){
                    Intent goLog = new Intent(context,LoginAct.class);
                    context.startActivity(goLog);
                }else{
                    intent = new Intent(getActivity(), GameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_work://打工挣钱
                if(!"".equals(SpUtils.getRoleName(getActivity()))) {
                    intent = new Intent(getActivity(), NewWorkAct.class);
                    startActivity(intent);
                }else{
                    Intent goLog = new Intent(context,LoginAct.class);
                    context.startActivity(goLog);
                }
                break;
        }
    }


    public class ToSwitchEvent{

    }

    private List<HomeHotProductEntity> datas = new ArrayList<>();
    private void getHotProductList(final int Page) {
        HotProduct hp = new HotProduct();
        hp.setPage(Page);
        hp.setRows(30);
        RequestManager.getUserManager().findHotProductList(hp, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                plvHotCredit.onPullUpRefreshComplete();
                plvHotCredit.onPullDownRefreshComplete();

                JSONObject object  =new JSONObject(result);
                JSONArray data = object.getJSONArray("data");
                Gson gson =new Gson();
                hotList = gson.fromJson(data.toString(), new TypeToken<List<HomeHotProductEntity>>() {
                }.getType());
                if (data==null){
                    loadingView.noContent();
                    return;
                }
                if (hotList==null||hotList.size()==0){
                    if (Page==1) {
                        loadingView.noContent();
                    }else{
                        plvHotCredit.setHasMoreData(false);
                    }
                    return;
                }
                if (Page==1){
                    datas.clear();
                }
                datas.addAll(hotList);
                if (adapter==null) {
                    adapter = new HomeCreditAdapter(getContext(), hotList);
                    plvHotCredit.getRefreshableView().setAdapter(adapter);
                }else{
                    adapter.setDatas(hotList);
                }
            }

            @Override
            public void onError(int status,String msg) {
                plvHotCredit.onPullUpRefreshComplete();
                plvHotCredit.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
    }
}
