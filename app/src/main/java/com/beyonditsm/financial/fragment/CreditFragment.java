package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款
 * Created by wangbin on 15/11/11.
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
    @ViewInject(R.id.tvSearch)
    private TextView tvSearch;//搜索

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

    private String cMoney=ConstantValue.CREDIT_MONEY+"";
    private String cTime=ConstantValue.CREDIT_MONTH+"";

//    private AbstractSpinerAdapter mAdapter;
//    private AbstractSpinerAdapter mDateAdapter;
//    private List<CustemObject> moneyList = new ArrayList<CustemObject>();
//    private List<CustemObject> dateList = new ArrayList<CustemObject>();

    private int pageSize = 10;
    private int currentP=1;
    private CreditAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frgment_credit, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("贷款");
        loadView.setNoContentTxt("暂无此类产品，换个条件试试");
        etAmount.setText(5 + "");
        tvM.setText(6+"");
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
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentP = 1;
                getCredit(currentP, cMoney, cTime);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP++;
                getCredit(currentP, cMoney, cTime);
            }
        });
        getCredit(currentP, cMoney, cTime);
        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCredit(currentP, cMoney, cTime);
            }
        });
        /*把回车键换成搜索*/
        etAmount.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    @Override
    public void setListener() {
        /*监听回车键*/
        etAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    FinancialUtil.closeIM(getActivity(),etAmount);
                    loadView.loading();
                    if(TextUtils.isEmpty(etAmount.getText().toString().trim())){
                        etAmount.setText(ConstantValue.CREDIT_MONEY+"");
                    }
                    if(TextUtils.isEmpty(tvM.getText().toString().trim())){
                        tvM.setText(ConstantValue.CREDIT_MONTH+"");
                    }

                    cMoney=etAmount.getText().toString().trim();
                    cTime=tvM.getText().toString().trim();
                    getCredit(currentP, cMoney, cTime);
                    return true;
                }

                return false;
            }
        });
//        String[] moneys = getResources().getStringArray(R.array.money);
//        for (int i = 0; i < moneys.length; i++) {
//            CustemObject object = new CustemObject();
//            object.setData(moneys[i]);
//            moneyList.add(object);
//        }
//
//        final String[] dates = getResources().getStringArray(R.array.date);
//        for (int i = 0; i < dates.length; i++) {
//            CustemObject object = new CustemObject();
//            object.setData(dates[i]);
//            dateList.add(object);
//        }
//
//        mAdapter = new CustemSpinerAdapter(getActivity());
//        mAdapter.refreshData(moneyList, 0);
//        mSpinerPopWindow = new SpinerPopWindow(getActivity());
//        mSpinerPopWindow.setAdatper(mAdapter);
//
//        mDateAdapter = new CustemSpinerAdapter(getActivity());
//        mDateAdapter.refreshData(dateList, 0);
//        mDatePupupWindow = new SpinerPopWindow(getActivity());
//        mDatePupupWindow.setAdatper(mDateAdapter);

        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HomeCreditDetailAct.class);
//                intent.putExtra(CreditDetailAct.PRODUCTINFO,datas.get(position));
                if(TextUtils.isEmpty(cMoney)){
                    cMoney= ConstantValue.CREDIT_MONEY+"";
                }
                if(TextUtils.isEmpty(cTime)){
                    cTime=ConstantValue.CREDIT_MONTH+"";
                }
                intent.putExtra(HomeCreditDetailAct.PRODUCTINFO,datas.get(position).getProductId());
                intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT,cMoney);
                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME,cTime);
                intent.putExtra(HomeCreditDetailAct.CREDIT_NAME,datas.get(position).getProductName());
                getActivity().startActivity(intent);
            }
        });
//        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
//            @Override
//            public void onItemClick(int pos) {
//                setHero(0, pos);
//            }
//        });
//        mDatePupupWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
//            @Override
//            public void onItemClick(int pos) {
//                setHero(1, pos);
//            }
//        });
    }

    @OnClick({R.id.tvSearch,R.id.rlMonth})
    public void toClick(View v) {
        switch (v.getId()) {
//            case R.id.rlMoney:
//                showSpinWindow();
//                break;
//            case R.id.rlDate:
//                showDateSpinWindow();
//                break;
            case R.id.rlMonth://选择月份
                int postition;
                if(TextUtils.isEmpty(tvM.getText().toString().trim())){
                    postition=0;
                }else{
                    postition=Integer.valueOf(tvM.getText().toString().trim())-1;
                }
                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(context,null).builder(postition);
                dialogChooseMonth.show();
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {

                      tvM.setText(adress.substring(0, adress.length() - 2));
                        if(TextUtils.isEmpty(etAmount.getText().toString().trim())){
                            cMoney=ConstantValue.CREDIT_MONEY+"";
                            etAmount.setText(ConstantValue.CREDIT_MONEY+"");
                        }else{
                            cMoney=etAmount.getText().toString().trim();
                        }

                        getCredit(currentP,cMoney,adress.substring(0, adress.length() - 2));
                    }
                });
                break;
            case R.id.tvSearch:
                FinancialUtil.closeIM(getActivity(),etAmount);
                loadView.loading();
                if(TextUtils.isEmpty(etAmount.getText().toString().trim())){
                    etAmount.setText(ConstantValue.CREDIT_MONEY+"");
                }
                if(TextUtils.isEmpty(tvM.getText().toString().trim())){
                    tvM.setText(ConstantValue.CREDIT_MONTH+"");
                }

                cMoney=etAmount.getText().toString().trim();
                cTime=tvM.getText().toString().trim();
                getCredit(currentP,cMoney,cTime);
                break;
        }
    }

//    private SpinerPopWindow mSpinerPopWindow;
//    private SpinerPopWindow mDatePupupWindow;
//
//    private void showSpinWindow() {
//        mSpinerPopWindow.setWidth(rlMoney.getWidth());
//        mSpinerPopWindow.showAsDropDown(rlMoney);
//    }
//
//    private void showDateSpinWindow() {
//        mDatePupupWindow.setWidth(rlDate.getWidth());
//        mDatePupupWindow.showAsDropDown(rlDate);
//    }
//
//
//    private void setHero(int type, int pos) {
//        switch (type) {
//            case 0:
//                if (pos >= 0 && pos <= moneyList.size()) {
//                    CustemObject value = moneyList.get(pos);
//                    tvMoney.setText(value.toString());
//                }
//                break;
//            case 1:
//                if (pos >= 0 && pos <= dateList.size()) {
//                    CustemObject value = dateList.get(pos);
//                    tvDate.setText(value.toString());
//                }
//                break;
//        }
//
//    }

    private List<ProductInfo> datas=new ArrayList<ProductInfo>();
    private void getCredit(final int currentPage,String  creditMoney,String creditTime) {
        FindProductListEntity entity = new FindProductListEntity();
        entity.setPage(currentPage);
        entity.setRows(pageSize);

        RequestManager.getMangManger().findProductList(entity,Double.valueOf(creditMoney)*10000+"",creditTime,new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                loadView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                ResultData<ProductResult> rd = (ResultData<ProductResult>) GsonUtils.json(result, ProductResult.class);
                ProductResult pr = rd.getData();
                List<ProductInfo> list = pr.getRows();

                if(pr.getTotal()==0){
                    loadView.noContent();
                    return;
                }
                if (list==null||list.size()==0) {
                    if(currentPage == 1)
                    loadView.noContent();
                    else
                    plv.setHasMoreData(false);

                    return;
                }
                if(currentPage==1){
                    datas.clear();
                }
                datas.addAll(list);

                double creditTotal=Double.valueOf(etAmount.getText().toString().trim())*10000;
                double creditTime=Double.valueOf(tvM.getText().toString().trim());
                if (adapter == null) {
                    adapter = new CreditAdapter(getActivity(), datas,creditTotal,creditTime);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas,creditTotal,creditTime);
                }
            }

            @Override
            public void onError(int status,String msg) {
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                loadView.loadError();
            }
        });
    }
}
