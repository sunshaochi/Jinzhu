package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.adapter.SpeedCreditAdapter;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.entity.CreditSpeedResult;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 急借通fragment
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditFrag extends BaseFragment {
    @ViewInject(R.id.lv_speedCredit)
    private LoadRefreshView lvSpeedCredit;
    private List<CreditSpeedEntity> creditSpeedList;
    private SpeedCreditAdapter speedCreditAdapter;
    private List<List<String>> propertyTypes = new ArrayList<>();
    private List<List<String>> jobIdentitys = new ArrayList<>();
    private int currentP = 1;
    @ViewInject(R.id.loading_speedCredit)
    private LoadingView loadingSpeedCredit;

    public static final String CREDIT_SPEED = "credit_speed";
    public static final String JOB_IDENTITYS = "job_identitys";
    public static final String PROPERTY_TYPES = "property_types";
    public static final String PAY_TYPE = "pay_type";

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_speedcredit, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        lvSpeedCredit.setPullRefreshEnabled(true);
        lvSpeedCredit.setScrollLoadEnabled(true);
        lvSpeedCredit.setPullLoadEnabled(false);
        lvSpeedCredit.setHasMoreData(true);
        lvSpeedCredit.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvSpeedCredit.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lvSpeedCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        lvSpeedCredit.getRefreshableView().setDivider(getResources().getDrawable(R.drawable.divider_grey));
        lvSpeedCredit.getRefreshableView().setDividerHeight(20);
        getCreditSpeed(currentP);//获取急速贷产品
    }

    private void getCreditSpeed(final int page) {
        RequestManager.getCommManager().getCreditSpeed(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingSpeedCredit.loadComplete();
                lvSpeedCredit.onPullDownRefreshComplete();
                lvSpeedCredit.onPullUpRefreshComplete();
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getString("status");
                if (TextUtils.equals(status,200+"")){
                    ResultData<CreditSpeedResult> rd = (ResultData<CreditSpeedResult>) GsonUtils.json(result, CreditSpeedResult.class);
                    CreditSpeedResult pr = rd.getData();
                    creditSpeedList = pr.getRows();

                    if (creditSpeedList.size() == 0 || creditSpeedList == null) {
                        if (page == 1) {
                            loadingSpeedCredit.noContent();
                        } else {
                            lvSpeedCredit.setHasMoreData(false);
                        }
                    }
                    if (speedCreditAdapter == null) {
                        speedCreditAdapter = new SpeedCreditAdapter(getActivity(), creditSpeedList);
                        lvSpeedCredit.getRefreshableView().setAdapter(speedCreditAdapter);
                    } else {
                        speedCreditAdapter.notifyChange(creditSpeedList);
                    }
                }
//                JSONArray rows = data.getJSONArray("rows");
//                for (int i = 0; i < rows.length(); i++) {
//                    JSONObject jsonObject1 = rows.getJSONObject(i);
//                    JSONObject propertyTypesObj = jsonObject1.getJSONObject("propertyTypes");
//                    JSONObject jobIdentitysObj = jsonObject1.getJSONObject("jobIdentitys");
//                    propertyTypes.add(FinancialUtil.toAnnotationStr(propertyTypesObj));
//                    jobIdentitys.add(FinancialUtil.toAnnotationStr(jobIdentitysObj));
//
//                }


            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getActivity(),msg);
                lvSpeedCredit.onPullDownRefreshComplete();
                lvSpeedCredit.onPullUpRefreshComplete();
                loadingSpeedCredit.loadError();
            }
        });
    }

    @Override
    public void setListener() {
        lvSpeedCredit.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CreditSpeedDetailAct.class);
                CreditSpeedEntity creditSpeedEntity = creditSpeedList.get(position);
//                intent.putExtra(JOB_IDENTITYS,(Serializable)creditSpeedEntity.getDebtTypemap());//借款用途
//                intent.putExtra(PROPERTY_TYPES,(Serializable) creditSpeedEntity.getResideStatusmap());//居住状况
//                intent.putExtra(PAY_TYPE,(Serializable) creditSpeedEntity.getPaytypemap());//还款方式
                intent.putExtra("productId", creditSpeedEntity.getProductId());//急速贷个体
                getActivity().startActivity(intent);
            }
        });
        lvSpeedCredit.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP = 1;
                getCreditSpeed(currentP);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP++;
                getCreditSpeed(currentP);
            }
        });
        loadingSpeedCredit.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCreditSpeed(currentP);
            }
        });
    }

}
