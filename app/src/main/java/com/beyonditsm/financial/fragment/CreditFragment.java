package com.beyonditsm.financial.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditGuideAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.adapter.ProductSortAdapter;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.entity.LoanTermInfo;
import com.beyonditsm.financial.entity.MoneyScopeInfo;
import com.beyonditsm.financial.entity.OrgTypeInfo;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ProductOrderInfo;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.Uitls;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.slidebottompanel.SlideBottomPanel;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.animation.ViewHelper;
import com.tandong.sa.json.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款
 * Created by wangbin on 15/11/11
 */
public class CreditFragment extends BaseFragment {

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
    private RadioButton rbBank; // 银行
    @ViewInject(R.id.rb_range)
    private RadioButton rbRange;//排序方式
    @ViewInject(R.id.rb_money)
    private RadioButton rbMoney; //按金额
    @ViewInject(R.id.rb_time)
    private RadioButton rbTime; //按时间
    @ViewInject(R.id.arrow1)
    private ImageView arrow1;
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


    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frgment_credit, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getSortParam();
        tvTitle.setText("贷款");
        initTit();
        loadView.setNoContentTxt("暂无此类产品，换个条件试试");
        etAmount.setSelection(etAmount.getText().length());
        rl_back.setVisibility(View.GONE);
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(true);
        plv.setPullLoadEnabled(false);
        plv.setHasMoreData(true);
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());

//        plv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        handlerMove();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//                return false;
//            }
//
//
//        });


        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentP = 1;
//                getCredit(currentP, cMoney, cTime);
                getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),cBank,cSort,cMoney,cTime,currentP,pageSize);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP++;
//                getCredit(currentP, cMoney, cTime);
                getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),cBank,cSort,cMoney,cTime,currentP,pageSize);  

            }
        });
        getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),cBank,cSort,cMoney,cTime,currentP,pageSize);

        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),cBank,cSort,cMoney,cTime,currentP,pageSize);
            }
        });
        /*把回车键换成搜索*/
        etAmount.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    private void getSortParam() {
        RequestManager.getCommManager().findSortParam(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                String a = result;
                ResultData<ProductSortEntity> rd = (ResultData<ProductSortEntity>) GsonUtils.json(result, ProductSortEntity.class);
                ProductSortEntity productSortEntity = rd.getData();
                orgTypeInfos = productSortEntity.getOrgType();
                productInfos = productSortEntity.getProductOrder();
                moneyScopeInfos = productSortEntity.getMoneyScope();
                loanTermInfos = productSortEntity.getLoanTerm();
            }

            @Override
            public void onError(int status, String msg) {


            }
        });
    }

    private void handlerMove() {
        if (!isButtonShowing && !isAnimating) {

            ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getX(ivSuspen), 99)
                    .setDuration(250);
            animator.setTarget(ivSuspen);
            animator.setInterpolator(new AccelerateInterpolator());
//        animator.setInterpolator(mOpenAnimationInterpolator);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    ViewHelper.setX(ivSuspen, value);

                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
            isButtonShowing = true;
        }
    }


    private void initTit() {
        rbBank.setText("机构类型");
        rbRange.setText("综合排序");
        rbMoney.setText("金额范围");
        rbTime.setText("贷款期限");
    }

    @Override
    public void setListener() {
        lvCreditSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) view;
                TextView textView = (TextView) linearLayout.getChildAt(0); //获取到点击的TextView
                MyToastUtils.showShortToast(context, textView.getText().toString() + "");
                switch (clickType){
                    case ProductSortAdapter.BANK:
                        if (textView.getText().toString().length() > 4){
                            rbBank.setText(textView.getText().toString().substring(0,4)+"...");
                        }else {
                            rbBank.setText(textView.getText().toString()+"");
                        }

                        cBank = orgTypeInfos.get(position).getOrgId();
                        break;
                    case ProductSortAdapter.MONEY:
                        if (textView.getText().toString().length() > 4){
                            rbMoney.setText(textView.getText().toString().substring(0,4)+"...");
                        }else {
                            rbMoney.setText(textView.getText().toString()+"");
                        }
                        cMoney = moneyScopeInfos.get(position).getMoneyKey();
                        break;
                    case ProductSortAdapter.SORT:
                        if (textView.getText().toString().length() > 4){
                            rbRange.setText(textView.getText().toString().substring(0,4)+"...");
                        }else {
                            rbRange.setText(textView.getText().toString()+"");
                        }
                        cSort = productInfos.get(position).getOrderKey();
                        break;
                    case ProductSortAdapter.TIME:
                        if (textView.getText().toString().length() > 4){
                            rbTime.setText(textView.getText().toString().substring(0,4)+"...");
                        }else {
                            rbTime.setText(textView.getText().toString()+"");
                        }
                        cTime = loanTermInfos.get(position).getTermKey();
                        break;
                    default:
                        break;

                }
                sbp.hide();
                getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),cBank,cSort,cMoney,cTime,currentP,pageSize);            }
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
                Intent intent = new Intent(getActivity(), HomeCreditDetailAct.class);
//                intent.putExtra(CreditDetailAct.PRODUCTINFO,datas.get(position));
                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) && !TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(getActivity(), "请输入金额");
                    etAmount.requestFocus();
                    return;
                } else if (!TextUtils.isEmpty(etAmount.getText().toString().trim()) && TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(getActivity(), "请选择月份");
                    return;
                }
                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) && TextUtils.isEmpty(tvM.getText().toString().trim())) {
//                    cMoney= ConstantValue.CREDIT_MONEY+"";
                    intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY + "");
                    intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, ConstantValue.CREDIT_MONTH + "");
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());
                } else {
                    intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
//                    intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, cMoney);
//                    intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, cTime);
                    //// TODO: 2016/7/16 Here Some Bug
                    intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, etAmount.getText().toString());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, tvM.getText().toString());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());
                }
                getActivity().startActivity(intent);
            }
        });

    }


    @OnClick({R.id.tvSearch, R.id.rlMonth, R.id.ivSuspen, R.id.rb_bank, R.id.rb_time, R.id.rb_money, R.id.rb_range})
    public void toClick(View v) {
        switch (v.getId()) {
//            case R.id.rlMoney:
//                showSpinWindow();
//                break;
//            case R.id.rlDate:
//                showDateSpinWindow();
//                break;
            case R.id.rb_bank:

                listItem = orgTypeInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.BANK;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                rbBank.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow1.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                sbp.reOpen();
//                showActionSheet(new String []{"全部","光大银行","浦发银行","宜人贷"},rbBank,arrow1);
                break;
            case R.id.rb_money:
                listItem = moneyScopeInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.MONEY;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context, clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbMoney.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow3.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
//                showActionSheet(new String []{"全部","0-10万","10-15万","15万以上"},rbMoney,arrow3);
                break;
            case R.id.rb_range:
                listItem = productInfos;
                clearArrow();
                clearTextColor();
                clickType = ProductSortAdapter.SORT;
                lvCreditSort.setAdapter(new ProductSortAdapter(listItem, context,clickType));
                Uitls.setListViewHeightBasedOnChildren(lvCreditSort);
                sbp.setmPanelHeight(Uitls.getListViewHeight(lvCreditSort));
                rbRange.setTextColor(context.getResources().getColor(R.color.tv_money_color));
                arrow2.setImageResource(R.mipmap.arrow_orienge_up);
                sbp.reOpen();
//                showActionSheet(new String []{"综合排序","按利率","按月供"},rbRange,arrow2);
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
//                showActionSheet(new String []{"全部","0-6个月","6-12个月","12个月以上"},rbTime,arrow4);
                break;
            case R.id.rlMonth://选择月份
//                int postition;
//                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
//                    postition = 0;
//                } else {
//                    postition = Integer.valueOf(tvM.getText().toString().trim()) - 1;
//                }
//                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(context, null).builder(postition);
//                dialogChooseMonth.show();
//                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
//                    @Override
//                    public void getAdress(String adress) {
//
//                        cTime = adress.substring(0, adress.length() - 2);
//                        tvM.setText(cTime);
//                        if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
////                            cMoney = ConstantValue.CREDIT_MONEY + "";
////                            etAmount.setText(ConstantValue.CREDIT_MONEY + "");
//                            MyToastUtils.showShortToast(getActivity(), "请输入金额");
//                            return;
//                        } else {
//                            cMoney = etAmount.getText().toString().trim();
//                        }
//                        currentP = 1;
////                        MyLogUtils.info("cccccccmoney+"+cMoney+"+cccccccTime+"+cTime);
//                        getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),rbBank.getText().toString()+"",rbRange.getText().toString()+"",rbMoney.getText().toString()+"",rbTime.getText().toString()+"",currentP,pageSize);
//
//                    }
//                });
                break;
            case R.id.tvSearch:
//                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) || TextUtils.isEmpty(tvM.getText().toString().trim())) {
//                    MyToastUtils.showShortToast(getActivity(), "请检查贷款金额和贷款期限是否输入完整");
//                    return;
//                }
//                FinancialUtil.closeIM(getActivity(), etAmount);
//                loadView.loading();
//                if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
//                    etAmount.setText(ConstantValue.CREDIT_MONEY + "");
//                }
//                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
//                    tvM.setText(ConstantValue.CREDIT_MONTH + "");
//                }
//                currentP = 1;
//                cMoney = etAmount.getText().toString().trim();
//                cTime = tvM.getText().toString().trim();
//                getCredit(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()),rbBank.getText().toString()+"",rbRange.getText().toString()+"",rbMoney.getText().toString()+"",rbTime.getText().toString()+"",currentP,pageSize);

                break;
            case R.id.ivSuspen:
                Intent intent = new Intent(getContext(), CreditGuideAct.class);
                startActivity(intent);
                break;
        }
    }


    private List<ProductInfo> datas = new ArrayList<ProductInfo>();


    private void getCredit(String area, String orgType, String productOrder, String moneyScope, String loanTerm, final int currentPage, int rows) {

        RequestManager.getMangManger().findProductByParam( area, orgType, productOrder,moneyScope,loanTerm,currentPage,rows,new RequestManager.CallBack() {


            @Override
            public void onSucess(String result) {
                loadView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                ResultData<ProductResult> rd = (ResultData<ProductResult>) GsonUtils.json(result, ProductResult.class);
                ProductResult pr = rd.getData();
                List<ProductInfo> list = pr.getRows();

//                if (pr.getTotal() == 0) {
//                    loadView.noContent();
//                    return;
//                }
                if (list == null || list.size() == 0) {
                    if (currentPage == 1)
                        loadView.noContent();
                    else
                        plv.setHasMoreData(false);

                    return;
                }
                if (currentPage == 1) {
                    datas.clear();
                }
                datas.addAll(list);

                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) && TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    String creditTotal = "";

                    String creditTime = "";
                    if (adapter == null) {
                        adapter = new CreditAdapter(getActivity(), datas, creditTotal, creditTime);
                        plv.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.setNotifyChange(datas, creditTotal, creditTime);
                    }
                } else if (TextUtils.isEmpty(etAmount.getText().toString().trim()) || TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(getActivity(), "请输入贷款金额或贷款期限");
                    return;
                } else {
                    double creditTotal = Double.valueOf(etAmount.getText().toString().trim()) * 10000;

                    double creditTime = Double.valueOf(tvM.getText().toString().trim());
                    if (adapter == null) {
                        adapter = new CreditAdapter(getActivity(), datas, creditTotal, creditTime);
                        plv.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas, creditTotal, creditTime);
                    }
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
        rbBank.setTextColor(context.getResources().getColor(R.color.black));
    }

    public void clearArrow() {
        arrow1.setImageResource(R.mipmap.arrow_black);
        arrow2.setImageResource(R.mipmap.arrow_black);
        arrow3.setImageResource(R.mipmap.arrow_black);
        arrow4.setImageResource(R.mipmap.arrow_black);
    }
}
