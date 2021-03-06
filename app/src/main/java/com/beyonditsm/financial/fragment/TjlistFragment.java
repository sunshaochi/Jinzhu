package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.OrgTypeListAct;
import com.beyonditsm.financial.activity.credit.CreditGuideAct;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.adapter.ProductSortAdapter;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.TujianBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.Uitls;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.slidebottompanel.SlideBottomPanel;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐列表fragment
 * Created by bitch-1 on 2016/12/8.
 */
public class TjlistFragment extends BaseFragment {
    @ViewInject(R.id.plv)
    private LoadRefreshView plv;

    @ViewInject(R.id.rb_bank)
    private TextView rbBank; // 银行
    @ViewInject(R.id.rb_range)
    private TextView rbRange;//排序方式
    @ViewInject(R.id.rb_money)
    private TextView rbMoney; //按金额
    @ViewInject(R.id.rb_time)
    private TextView rbTime; //按时间
    @ViewInject(R.id.arrow2)
    private ImageView arrow2;
    @ViewInject(R.id.arrow3)
    private ImageView arrow3;
    @ViewInject(R.id.arrow4)
    private ImageView arrow4;
    @ViewInject(R.id.sbp)
    private SlideBottomPanel sbp;
    @ViewInject(R.id.lv_credit_sort)
    private ListView lvCreditSort;

    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.ll_searchTitle)
    private LinearLayout llSearchTitle;
    private String cBank = "";
    private String cSort = "";
    private String cMoney = "";
    private String cTime = "";

    private List<ProductSortEntity.OrgTypeBean> orgTypeInfos;//机构
    private List<ProductSortEntity.ProductOrderBean> productInfos;//排序
    private List<ProductSortEntity.MoneyScopeBean> moneyScopeInfos;//金额
    private List<ProductSortEntity.LoanTermBean> loanTermInfos;//期限
    private short clickType;
    public static final int ORGQUEST = 1;


    private int page = 1;
    private int rows = 10;
    private CreditAdapter adapter;
    private TujianBean tuijianbean;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_tjlist,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        llSearchTitle.setVisibility(View.GONE);
        tuijianbean=TjfirstFragment.tujianBean;
        initTit();//初识化头部筛选条件
        getSortParam();//获取头部筛选条件
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
                getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

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
                getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品
            }
        });
        //获取推荐列表
        getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ABS", tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

    }

    /**
     * 初识化头部筛选条件
     */
    private void initTit() {
        rbBank.setText("机构类型");
        rbRange.setText("综合排序");
        rbMoney.setText("金额范围");
        rbTime.setText("贷款期限");
        cBank = "";
        cSort = "";
        cTime =tuijianbean.getCreditTime();
        cMoney = tuijianbean.getCreditMoney();
    }

    /**
     * 获取筛选条件
     */
    private void getSortParam() {
        RequestManager.getCommManager().findSortParam(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                ResultData<ProductSortEntity> rd = (ResultData<ProductSortEntity>) GsonUtils.json(result, ProductSortEntity.class);
                ProductSortEntity productSortEntity = rd.getData();
                llSearchTitle.setVisibility(View.VISIBLE);
                orgTypeInfos = productSortEntity.getOrgType();//机构集合
                ProductSortEntity.OrgTypeBean orgTypeBean = new ProductSortEntity.OrgTypeBean();
                orgTypeBean.setOptionValue("");
                orgTypeBean.setOptionName("全部机构");
                orgTypeInfos.add(0, orgTypeBean);//机构集合里面加一个
                ParamsUtil.getInstance().setTjorgTypeInfos(orgTypeInfos);//机构
                productInfos = productSortEntity.getProductOrder();//排序集合
                ProductSortEntity.ProductOrderBean productOrderBean = new ProductSortEntity.ProductOrderBean();
                productOrderBean.setOptionValue("");//排序
                productOrderBean.setOptionName("综合排序");
                productInfos.add(0, productOrderBean);//排序集合里面加一个
                moneyScopeInfos = productSortEntity.getMoneyScope();//金额集合
                ProductSortEntity.MoneyScopeBean moneyScopeBean = new ProductSortEntity.MoneyScopeBean();
                moneyScopeBean.setOptionValue("");
                moneyScopeBean.setOptionName("金额范围");//金额集合里面加一个
                moneyScopeInfos.add(0, moneyScopeBean);//金额
                loanTermInfos = productSortEntity.getLoanTerm();//期限集合
                ProductSortEntity.LoanTermBean loanTermBean = new ProductSortEntity.LoanTermBean();
                loanTermBean.setOptionValue("");
                loanTermBean.setOptionName("贷款期限");//期限集合加一个
                loanTermInfos.add(0, loanTermBean);//期限
            }
            @Override
            public void onError(int status, String msg) {


            }
        });
    }
    @OnClick({R.id.rlMonth, R.id.ivSuspen, R.id.rb_bank, R.id.rb_time, R.id.rb_money, R.id.rb_range})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rb_bank:
                intent = new Intent(getActivity(), OrgTypeListAct.class);
                intent.putExtra("type",2);
                startActivityForResult(intent, 2);
                sbp.hide();
//                listItem = orgTypeInfos;
                clearArrow();
                clearTextColor();
                break;
            case R.id.rb_money:
                List<?> listItem = moneyScopeInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.MONEY;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbMoney.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow3.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;
            case R.id.rb_range:
                listItem = productInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.SORT;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbRange.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow2.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;
            case R.id.rb_time:
                listItem = loanTermInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.TIME;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbTime.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow4.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;

            case R.id.ivSuspen:
                intent = new Intent(getContext(), CreditGuideAct.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && null != data) {
            int position = data.getIntExtra("org", 0);
            String name = orgTypeInfos.get(position).getOptionName();
            if (name.length() > 4) {
                rbBank.setText(name.substring(0, 4) + "..");
            } else {
                rbBank.setText(name);
            }
            cBank = orgTypeInfos.get(position).getOptionValue();
            page = 1;
            getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品
        }
    }

    @Override
    public void setListener() {
        lvCreditSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LinearLayout linearLayout = (LinearLayout) view;
                TextView textView = (TextView) linearLayout.getChildAt(0); //获取到点击的TextView
                switch (clickType) {
//                    case ProductSortAdapter.BANK:
//                        if (textView.getText().toString().length() > 4) {
//                            rbBank.setText(textView.getText().toString().substring(0, 4) + "..");
//                        } else {
//                        rbBank.setText(textView.getText().toString() + "");
//                        }
//
//                        cBank = orgTypeInfos.get(position).getOrgId();
//                        break;
                    case ProductSortAdapter.MONEY:
                        if (textView.getText().toString().length() > 4) {
                            rbMoney.setText(textView.getText().toString().substring(0, 4) + "..");
                        } else {
                            rbMoney.setText(textView.getText().toString() + "");
                        }
                        cMoney = moneyScopeInfos.get(position).getOptionValue();
                        page = 1;
                        break;
                    case ProductSortAdapter.SORT:
                        if (textView.getText().toString().length() > 4) {
                            rbRange.setText(textView.getText().toString().substring(0, 3) + "..");
                        } else {
                            rbRange.setText(textView.getText().toString() + "");
                        }
                        cSort = productInfos.get(position).getOptionValue();
                        page = 1;
                        break;
                    case ProductSortAdapter.TIME:
                        if (textView.getText().toString().length() > 4) {
                            rbTime.setText(textView.getText().toString().substring(0, 4) + "..");
                        } else {
                            rbTime.setText(textView.getText().toString() + "");
                        }
                        cTime = loanTermInfos.get(position).getOptionValue();
                        page = 1;
                        break;
                    default:
                        break;

                }
                sbp.hide();
//                lvCreditSort.setEnabled(false);

                sbp.setClickable(false);
                getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品

            }
        });

        sbp.setOnStateChangeListener(new SlideBottomPanel.OnStateChangeListener() {
            @Override
            public void Hidden(boolean isHidden) {
                clearArrow();
                clearTextColor();
            }
        });



    }
    private List<ProductBean> datas = new ArrayList<>();
    //获取推荐产品
    private void getTjproduct(String cityId, String creditMoney, String creditTime,String orgTypeKey, String productOrder, String orderByOfType, String eduLevel, String domicile, String creditStatusKey, String jobIdentityKey, String age, String licenseTimeLength, String carStatusKey, String propertyTypeKey, String salary, String guaranteeSlip, String otherAssets, String fundTimeLength,String propertyName, final int page, int rows) {
        RequestManager.getMangManger().findTjProductList(cityId,creditMoney,creditTime,orgTypeKey,productOrder,orderByOfType,eduLevel, domicile, creditStatusKey, jobIdentityKey, age, licenseTimeLength, carStatusKey, propertyTypeKey, salary, guaranteeSlip, otherAssets, fundTimeLength,propertyName, page, rows, new RequestManager.CallBack() {
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

    public void clearTextColor() {
        rbTime.setTextColor(context.getResources().getColor(R.color.black));
        rbMoney.setTextColor(context.getResources().getColor(R.color.black));
        rbRange.setTextColor(context.getResources().getColor(R.color.black));
//        rbBank.setTextColor(context.getResources().getColor(R.color.black));
    }

    public void clearArrow() {
//        arrow1.setImageResource(R.mipmap.arrow_black);
        arrow2.setImageResource(R.mipmap.arrow_black);
        arrow3.setImageResource(R.mipmap.arrow_black);
        arrow4.setImageResource(R.mipmap.arrow_black);
    }

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initTit();//初识化头部筛选条件
            getSortParam();//获取头部筛选条件
            getTjproduct(tuijianbean.getCityId(),cMoney,cTime,cBank,cSort,"ASC",tuijianbean.getEduLevel(), tuijianbean.getDomicile(), tuijianbean.getCreditStatusKey(), tuijianbean.getJobIdentityKey(), tuijianbean.getAge(), tuijianbean.getLicenseTimeLength(), tuijianbean.getCreditStatusKey(), tuijianbean.getPropertyTypeKey(), tuijianbean.getSalary(), tuijianbean.getGuaranteeSlip(), tuijianbean.getOtherAssets(), tuijianbean.getFundTimeLength(),"", page, rows);//获取推荐产品
        }
    }
}
