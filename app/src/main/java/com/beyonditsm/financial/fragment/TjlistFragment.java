package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.TujianBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐列表fragment
 * Created by bitch-1 on 2016/12/8.
 */
public class TjlistFragment extends BaseFragment {
    private TujianBean tuijianbean;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.plv_load)
    private LoadRefreshView plv;
    private CreditAdapter adapter;
    private int page=1;
    private int rows=10;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_tjlist,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tuijianbean=TjfirstFragment.tujianBean;
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(true);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                page=1;
                getTjproduct(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),tuijianbean.getCreditMoney(),tuijianbean.getCreditTime(), tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getTjproduct(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),tuijianbean.getCreditMoney(),tuijianbean.getCreditTime(), tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

            }
        });
        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if(datas.get(position).getProductType().equals("1")){//极速贷
                    intent.putExtra("productId",datas.get(position).getProductId());
                    intent.setClass(getActivity(),CreditSpeedDetailAct.class);
                }else if(datas.get(position).getProductType().equals("0")){//大额贷
                    intent.setClass(getActivity(),HomeCreditDetailAct.class);
                    intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());//传递id
                    intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY + "");//传递金额
                    intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, ConstantValue.CREDIT_MONTH + "");//传递时间
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());//传递姓名
                }
                getActivity().startActivity(intent);
            }
        });


        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getTjproduct(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),tuijianbean.getCreditMoney(),tuijianbean.getCreditTime(), tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品
            }
        });
        //获取推荐列表
        getTjproduct(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),tuijianbean.getCreditMoney(),tuijianbean.getCreditTime(), tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

    }

    @Override
    public void setListener() {

    }
    private List<ProductBean> datas = new ArrayList<>();
    //获取推荐产品
    private void getTjproduct(String cityId, String creditMoney, String creditTime, String eduLevel, String domicile, String creditStatusKey, String jobIdentityKey, String age, String licenseTimeLength, String carStatusKey, String propertyTypeKey, String salary, String guaranteeSlip, String otherAssets, String fundTimeLength,String propertyName, final int page, int rows) {
        RequestManager.getMangManger().findTjProductList(cityId,creditMoney,creditTime, eduLevel, domicile, creditStatusKey, jobIdentityKey, age, licenseTimeLength, carStatusKey, propertyTypeKey, salary, guaranteeSlip, otherAssets, fundTimeLength,propertyName, page, rows, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                ResultData<ProductResult> rd = (ResultData<ProductResult>) GsonUtils.json(result, ProductResult.class);
                ProductResult pr = rd.getData();
                List<ProductBean> list = pr.getRows();

                if (list == null || list.size() == 0) {
                    if (page == 1) {
                        loadView.noContent();
                    } else {
                        plv.setHasMoreData(false);
                    }
                    return;
                }
                if (page == 1) {
                    datas.clear();
                }

                datas.addAll(list);
                if (adapter == null) {
                    adapter = new CreditAdapter(getActivity(), datas);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }


            }

            @Override
            public void onError(int status, String msg) {
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                loadView.loadError();
            }
        });
    }

}
