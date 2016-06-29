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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
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
    @ViewInject(R.id.tvSearch)
    private TextView tvSearch;//搜索
    @ViewInject(R.id.loadView)
    private LoadingView loadView;

    private String cMoney = "";
    private String cTime = "";
    private int pageSize = 10;
    private int currentP = 1;
    private CreditAdapter adapter;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_application);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("立即申请");
        setLisitener();
        loadView.setNoContentTxt("暂无此类产品，换个条件试试");
        etAmount.setSelection(etAmount.getText().length());
//        rl_back.setVisibility(View.GONE);
        plv.setPullRefreshEnabled(true);
        plv.setScrollLoadEnabled(false);
        plv.setPullLoadEnabled(true);
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
//                getCredit(currentP, cMoney, cTime);
                getCredit(currentP, etAmount.getText().toString(), tvM.getText().toString());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentP++;
//                getCredit(currentP, cMoney, cTime);
                getCredit(currentP, etAmount.getText().toString(), tvM.getText().toString());
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

    private void setLisitener() {
        /*监听回车键*/
        etAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    if (TextUtils.isEmpty(etAmount.getText().toString().trim()) || TextUtils.isEmpty(tvM.getText().toString().trim())) {
                        MyToastUtils.showShortToast(ApplicationAct.this, "请检查贷款金额和贷款期限是否输入完整");
                        return false;
                    } else {
                        cTime = tvM.getText().toString().trim();
                        cMoney = etAmount.getText().toString().trim();
                    }
                    FinancialUtil.closeIM(ApplicationAct.this, etAmount);
                    loadView.loading();
                    currentP = 1;
                    getCredit(currentP, cMoney, cTime);
                    return true;
                }

                return false;
            }
        });
        etAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) v;
                et.setHint(null);
                et.setCursorVisible(true);
            }
        });
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //小数点后只能输入两位数
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        etAmount.setText(s);
                        etAmount.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    etAmount.setText(s);
                    etAmount.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etAmount.setText(s.subSequence(0, 1));
                        etAmount.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(".")) {
                    MyToastUtils.showShortToast(ApplicationAct.this, "不能以小数点开头");
                    etAmount.setText("");
                }
            }
        });
        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ApplicationAct.this, HomeCreditDetailAct.class);
//                intent.putExtra(CreditDetailAct.PRODUCTINFO,datas.get(position));
                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) && !TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(ApplicationAct.this, "请输入金额");
                    etAmount.requestFocus();
                    return;
                } else if (!TextUtils.isEmpty(etAmount.getText().toString().trim()) && TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(ApplicationAct.this, "请选择月份");
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
                    intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, etAmount.getText().toString());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, tvM.getText().toString());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.tvSearch, R.id.rlMonth, R.id.ivSuspen})
    public void toClick(View v){
        switch (v.getId()){
            case R.id.rlMonth://选择月份
                int postition;
                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    postition = 0;
                } else {
                    postition = Integer.valueOf(tvM.getText().toString().trim()) - 1;
                }
                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(ApplicationAct.this, null).builder(postition);
                dialogChooseMonth.show();
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {

                        cTime = adress.substring(0, adress.length() - 2);
                        tvM.setText(cTime);
                        if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
//                            cMoney = ConstantValue.CREDIT_MONEY + "";
//                            etAmount.setText(ConstantValue.CREDIT_MONEY + "");
                            MyToastUtils.showShortToast(ApplicationAct.this, "请输入金额");
                            return;
                        } else {
                            cMoney = etAmount.getText().toString().trim();
                        }
                        currentP = 1;
//                        MyLogUtils.info("cccccccmoney+"+cMoney+"+cccccccTime+"+cTime);
                        getCredit(currentP, cMoney, cTime);
                    }
                });
                break;
        }
    }
    private List<ProductInfo> datas = new ArrayList<ProductInfo>();

    private void getCredit(final int currentPage, String creditMoney, String creditTime) {
        FindProductListEntity entity = new FindProductListEntity();
        entity.setPage(currentPage);
        entity.setRows(pageSize);
        MyLogUtils.info("金额：" + creditMoney + "期限：" + creditTime);
        if (TextUtils.isEmpty(creditMoney)) {
            creditMoney = "";
        } else {
            creditMoney = Double.valueOf(creditMoney) * 10000 + "";
        }
        RequestManager.getMangManger().findProductList(entity, creditMoney, creditTime, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                loadView.loadComplete();
                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
                ResultData<ProductResult> rd = (ResultData<ProductResult>) GsonUtils.json(result, ProductResult.class);
                ProductResult pr = rd.getData();
                List<ProductInfo> list = pr.getRows();

                if (pr.getTotal() == 0) {
                    loadView.noContent();
                    return;
                }
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
                        adapter = new CreditAdapter(ApplicationAct.this, datas, creditTotal, creditTime);
                        plv.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.setNotifyChange(datas, creditTotal, creditTime);
                    }
                }else if (TextUtils.isEmpty(etAmount.getText().toString().trim())||TextUtils.isEmpty(tvM.getText().toString().trim())){
                    MyToastUtils.showShortToast(ApplicationAct.this, "请输入贷款金额或贷款期限");
                    return;
                } else {
                    double creditTotal = Double.valueOf(etAmount.getText().toString().trim()) * 10000;

                    double creditTime = Double.valueOf(tvM.getText().toString().trim());
                    if (adapter == null) {
                        adapter = new CreditAdapter(ApplicationAct.this, datas, creditTotal, creditTime);
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
}
