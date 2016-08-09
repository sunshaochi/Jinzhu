package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.OrgTypeListAct;
import com.beyonditsm.financial.activity.credit.CreditGuideAct;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.adapter.ProductSortAdapter;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.Uitls;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.slidebottompanel.SlideBottomPanel;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请贷款
 * Created by liwk on 2016/5/26
 */
public class ApplicationAct extends BaseActivity {
    @ViewInject(R.id.plv)
    private LoadRefreshView plv;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.etAmount)
    private EditText etAmount;//输入金额
    @ViewInject(R.id.tvM)
    private TextView tvM;//月份
    @ViewInject(R.id.ivSuspen)
    private ImageView ivSuspen;
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
//    @ViewInject(R.id.rlMoney)
//    private RelativeLayout rlMoney;
//    @ViewInject(R.id.tvMoney)
//    private TextView tvMoney;
//    @ViewInject(R.id.rlDate)
//    private RelativeLayout rlDate;
//    @ViewInject(R.id.tvDate)
//    private TextView tvDate;

    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.ll_searchTitle)
    private LinearLayout llSearchTitle;
    //    private String cMoney=ConstantValue.CREDIT_MONEY+"";
//    private String cTime=ConstantValue.CREDIT_MONTH+"";
    private String cBank = "";
    private String cSort = "";
    private String cMoney = "";
    private String cTime = "";


    private int currentP = 1;
    private int pageSize = 10;
    private CreditAdapter adapter;
    private boolean isButtonShowing = false;
    private boolean isAnimating = false;

    private List<?> listItem;
    private List<ProductSortEntity.OrgTypeBean> orgTypeInfos;
    private List<ProductSortEntity.ProductOrderBean> productInfos;
    private List<ProductSortEntity.MoneyScopeBean> moneyScopeInfos;
    private List<ProductSortEntity.LoanTermBean> loanTermInfos;

    private short clickType;
    public static final int ORGQUEST = 1;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_application);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("立即申请");
        setLisitener();
        String city = SpUtils.getCity(MyApplication.getInstance().getApplicationContext());
        llSearchTitle.setVisibility(View.GONE);
        if (TextUtils.isEmpty(city)) {
            getSortParam(ParamsUtil.getInstance().getChangedCity());
        } else {
            getSortParam(city);
            initTit();
        }

        EventBus.getDefault().register(this);
        tvTitle.setText("贷款");
        initTit();
        loadView.setNoContentTxt("暂无此类产品，换个条件试试");
//        etAmount.setSelection(etAmount.getText().length());
        rl_back.setVisibility(View.GONE);
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
                currentP = 1;
                getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP++;
                getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);
            }
        });
        getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);
        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getSortParam(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()) + "");
                getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()) + "", cBank, cSort, cMoney, cTime, currentP, pageSize);
            }
        });
//        /*把回车键换成搜索*/
//        etAmount.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    private void initTit() {
        rbBank.setText("机构类型");
        rbRange.setText("综合排序");
        rbMoney.setText("金额范围");
        rbTime.setText("贷款期限");
        cBank = "";
        cSort = "";
        cTime = "";
        cMoney = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTit();
        getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);
        String city = SpUtils.getCity(MyApplication.getInstance().getApplicationContext());
        if (TextUtils.isEmpty(city)) {
            getSortParam(ParamsUtil.getInstance().getChangedCity());
        } else {
            getSortParam(city);
            initTit();
        }
    }

    private void getSortParam(String cityName) {
        RequestManager.getCommManager().findSortParam(cityName, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {

                ResultData<ProductSortEntity> rd = (ResultData<ProductSortEntity>) GsonUtils.json(result, ProductSortEntity.class);
                ProductSortEntity productSortEntity = rd.getData();
                llSearchTitle.setVisibility(View.VISIBLE);
                orgTypeInfos = productSortEntity.getOrgType();
                ProductSortEntity.OrgTypeBean orgTypeBean = new ProductSortEntity.OrgTypeBean();
                orgTypeBean.setOrgId("");
                orgTypeBean.setOrgName("全部机构");
                orgTypeInfos.add(0, orgTypeBean);
                ParamsUtil.getInstance().setOrgTypeInfos(orgTypeInfos);
                productInfos = productSortEntity.getProductOrder();
                ProductSortEntity.ProductOrderBean productOrderBean = new ProductSortEntity.ProductOrderBean();
                productOrderBean.setOrderKey("");
                productOrderBean.setOrderVal("综合排序");
                productInfos.add(0, productOrderBean);
                moneyScopeInfos = productSortEntity.getMoneyScope();
                ProductSortEntity.MoneyScopeBean moneyScopeBean = new ProductSortEntity.MoneyScopeBean();
                moneyScopeBean.setMoneyKey("");
                moneyScopeBean.setMoneyVal("金额范围");
                moneyScopeInfos.add(0, moneyScopeBean);
                loanTermInfos = productSortEntity.getLoanTerm();
                ProductSortEntity.LoanTermBean loanTermBean = new ProductSortEntity.LoanTermBean();
                loanTermBean.setTermKey("");
                loanTermBean.setTermVal("贷款期限");
                loanTermInfos.add(0, loanTermBean);
            }

            @Override
            public void onError(int status, String msg) {


            }
        });
    }
    private List<ProductInfo> datas = new ArrayList<>();

    private void getCredit(String userName, final String area, final String orgType, String productOrder, String moneyScope, String loanTerm, final int currentPage, int rows) {

        RequestManager.getMangManger().findProductByParam(userName, area, orgType, productOrder, moneyScope, loanTerm, currentPage, rows, new RequestManager.CallBack() {

            @Override
            public void onSucess(String result) {
                MyLogUtils.info("获取列表时传的机构类型：" + orgType);
                loadView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                ResultData<ProductResult> rd = (ResultData<ProductResult>) GsonUtils.json(result, ProductResult.class);
                ProductResult pr = rd.getData();
                List<ProductInfo> list = pr.getRows();

                if (list == null || list.size() == 0) {
                    if (currentPage == 1) {
                        loadView.noContent();
                    } else {
                        plv.setHasMoreData(false);
                    }
                    return;
                }
                if (currentPage == 1) {
                    datas.clear();
                }

                datas.addAll(list);

                if (adapter == null) {
                    adapter = new CreditAdapter(ApplicationAct.this, datas);
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
    private void setLisitener() {
        lvCreditSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        cMoney = moneyScopeInfos.get(position).getMoneyKey();
                        currentP = 1;
                        break;
                    case ProductSortAdapter.SORT:
                        if (textView.getText().toString().length() > 4) {
                            rbRange.setText(textView.getText().toString().substring(0, 3) + "..");
                        } else {
                            rbRange.setText(textView.getText().toString() + "");
                        }
                        cSort = productInfos.get(position).getOrderKey();
                        currentP = 1;
                        break;
                    case ProductSortAdapter.TIME:
                        if (textView.getText().toString().length() > 4) {
                            rbTime.setText(textView.getText().toString().substring(0, 4) + "..");
                        } else {
                            rbTime.setText(textView.getText().toString() + "");
                        }
                        cTime = loanTermInfos.get(position).getTermKey();
                        currentP = 1;
                        break;
                    default:
                        break;

                }
                sbp.hide();
//                lvCreditSort.setEnabled(false);

                sbp.setClickable(false);
                getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);

            }
        });

        sbp.setOnStateChangeListener(new SlideBottomPanel.OnStateChangeListener() {
            @Override
            public void Hidden(boolean isHidden) {
                clearArrow();
                clearTextColor();
            }
        });

        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ApplicationAct.this, HomeCreditDetailAct.class);
                intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
                intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY + "");
                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, ConstantValue.CREDIT_MONTH + "");
                intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());
                startActivity(intent);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ORGQUEST && null != data) {
            int position = data.getIntExtra("org", 0);
            String name = orgTypeInfos.get(position).getOrgName();
            if (name.length() > 4) {
                rbBank.setText(name.substring(0, 4) + "..");
            } else {
                rbBank.setText(name);
            }
            cBank = orgTypeInfos.get(position).getOrgId();
            currentP = 1;
            getCredit(ParamsUtil.getInstance().getUle().getUsername(), SpUtils.getCity(MyApplication.getInstance().getApplicationContext()), cBank, cSort, cMoney, cTime, currentP, pageSize);
        }
    }
    public void clearTextColor() {
        rbTime.setTextColor(getResources().getColor(R.color.black));
        rbMoney.setTextColor(getResources().getColor(R.color.black));
        rbRange.setTextColor(getResources().getColor(R.color.black));
//        rbBank.setTextColor(context.getResources().getColor(R.color.black));
    }

    public void clearArrow() {
//        arrow1.setImageResource(R.mipmap.arrow_black);
        arrow2.setImageResource(R.mipmap.arrow_black);
        arrow3.setImageResource(R.mipmap.arrow_black);
        arrow4.setImageResource(R.mipmap.arrow_black);
    }

    @OnClick({R.id.rlMonth, R.id.ivSuspen, R.id.rb_bank, R.id.rb_time, R.id.rb_money, R.id.rb_range})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rb_bank:
                intent = new Intent(ApplicationAct.this, OrgTypeListAct.class);
                startActivityForResult(intent, ORGQUEST);
                sbp.hide();
//                listItem = orgTypeInfos;
                clearArrow();
                clearTextColor();
                break;
            case R.id.rb_money:
                listItem = moneyScopeInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.MONEY;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, ApplicationAct.this, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbMoney.setTextColor(getResources().getColor(R.color.tv_money_color));
                arrow3.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;
            case R.id.rb_range:
                listItem = productInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.SORT;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, ApplicationAct.this, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbRange.setTextColor(getResources().getColor(R.color.tv_money_color));
                arrow2.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;
            case R.id.rb_time:
                listItem = loanTermInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.TIME;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, ApplicationAct.this, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbTime.setTextColor(getResources().getColor(R.color.tv_money_color));
                arrow4.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
                lvCreditSort.setEnabled(true);
                break;

            case R.id.ivSuspen:
                intent = new Intent(ApplicationAct.this, CreditGuideAct.class);
                startActivity(intent);
                break;
        }
    }




}
