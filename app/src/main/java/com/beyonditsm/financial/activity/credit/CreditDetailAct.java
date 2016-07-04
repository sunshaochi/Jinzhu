package com.beyonditsm.financial.activity.credit;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ProductEntity;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 贷款详情
 * Created by wangbin on 15/11/12.
 */
public class CreditDetailAct extends BaseActivity {
    private ScrollView svCridet;
    private ImageView ivRequire;//申请条件
    private LinearLayout llRequire;
    private ImageView ivMaterial;//所需材料
    private LinearLayout llMaterial;
    private ImageView ivDetail;//详细说明
    private LinearLayout llDetail;

    @ViewInject(R.id.etAmount)
    private EditText etAmount;//输入金额
    @ViewInject(R.id.tvM)
    private TextView tvM;//月份
    @ViewInject(R.id.onpay)
    private TextView tvOnePay;

    public static final String PRODUCTINFO = "productinfo";

    public static final String CREDIT_AMOUNT = "credit_mount";
    public static final String CREDIT_TIME = "credit_time";

    private Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

    private ProductInfo productInfo;

    private ImageView ivBank;
    private TextView tvProName;
    //    private TextView tvMount;
    private TextView tvScope;
    //    private TextView tvLimit;
    private TextView tvLim;
    private TextView tvMonthPay;
    private TextView tvPaytype;
    private TextView tvTotal;
    private TextView tvLoan;
    private TextView tvRate;
    private TextView tvCon;//申请条件
    private TextView tvNeed;//所需材料
    private TextView tvDetail;//详细信息
    private int minMon;

    public static String creditMoney;
    public static String creditMonth;
    public static String monthlyPayments;//月供
    private String totalRath;

    private MyAlertDialog dialog;
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");//保留小数


    private ArrayList<String> tList = new ArrayList<String>();

    private ObjectAnimator obaDown1;
    private ObjectAnimator obaOn1;
    private ObjectAnimator obaDown2;
    private ObjectAnimator obaOn2;
    private ObjectAnimator obaDown3;
    private ObjectAnimator obaOn3;


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private void assignViews() {
        svCridet = (ScrollView) findViewById(R.id.svCridet);
        ivBank = (ImageView) findViewById(R.id.ivBank);
        ivRequire = (ImageView) findViewById(R.id.ivRequire);
        llRequire = (LinearLayout) findViewById(R.id.llRequire);
        ivMaterial = (ImageView) findViewById(R.id.ivMaterial);
        llMaterial = (LinearLayout) findViewById(R.id.llMaterial);
        ivDetail = (ImageView) findViewById(R.id.ivDetail);
        llDetail = (LinearLayout) findViewById(R.id.llDetail);
        ivBank = (ImageView) findViewById(R.id.ivBank);
        tvProName = (TextView) findViewById(R.id.tvProName);
//        tvMount = (TextView) findViewById(R.id.tvMount);
        tvScope = (TextView) findViewById(R.id.tvScope);
//        tvLimit = (TextView) findViewById(R.id.tvLimit);
        tvLim = (TextView) findViewById(R.id.tvLim);
        tvMonthPay = (TextView) findViewById(R.id.tvMonthPay);
        tvPaytype = (TextView) findViewById(R.id.tvPaytype);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvLoan = (TextView) findViewById(R.id.tvLoan);
        tvRate = (TextView) findViewById(R.id.tvRate);
        tvCon = (TextView) findViewById(R.id.tvCon);
        tvNeed = (TextView) findViewById(R.id.tvNeed);
        tvDetail = (TextView) findViewById(R.id.tvDetail);

    }


    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                svCridet.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /*初始化动画*/
    private void initAnim() {
        obaDown1 = ObjectAnimator.ofFloat(ivRequire, "rotation", 0,
                180);
        obaDown1.setDuration(300);
        obaOn1 = ObjectAnimator.ofFloat(ivRequire, "rotation", -180,
                0);
        obaOn1.setDuration(300);

        obaDown2 = ObjectAnimator.ofFloat(ivMaterial, "rotation", 0,
                180);
        obaDown2.setDuration(300);
        obaOn2 = ObjectAnimator.ofFloat(ivMaterial, "rotation", -180,
                0);
        obaOn2.setDuration(300);

        obaDown3 = ObjectAnimator.ofFloat(ivDetail, "rotation", 0,
                180);
        obaDown3.setDuration(300);
        obaOn3 = ObjectAnimator.ofFloat(ivDetail, "rotation", -180,
                0);
        obaOn3.setDuration(300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        creditMonth = null;
        creditMoney = null;
        monthlyPayments = null;
    }

    @Override
    protected void onResume() {
        ParamsUtil.getInstance().setCurrentAct(CreditDetailAct.this);
        super.onResume();
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_credit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        assignViews();
        initAnim();
        productInfo = getIntent().getParcelableExtra(PRODUCTINFO);

        creditMoney = getIntent().getStringExtra(CREDIT_AMOUNT);
        creditMonth = getIntent().getStringExtra(CREDIT_TIME);
        Double minMone = Double.valueOf(productInfo.getMinVal());
        Double maxMone = Double.valueOf(productInfo.getMaxVal());
        Double minMont = Double.valueOf(productInfo.getTimeMinVal());
        Double maxMont = Double.valueOf(productInfo.getTimeMaxVal());
        if (Double.valueOf(creditMoney) * 10000 < minMone || Double.valueOf(creditMoney) * 10000 > maxMone) {
            creditMoney = df.format(maxMone / 10000) + "";
        }

        if (Double.valueOf(creditMonth) < minMont || Double.valueOf(creditMonth) > maxMont) {
            creditMonth = maxMont + "";
        }

        etAmount.setText(creditMoney);
        tvM.setText(creditMonth);

        etAmount.setSelection(etAmount.getText().length());


        minMon = Integer.valueOf(productInfo.getTimeMinVal());

        getMOnthPay(creditMoney, productInfo.getMonthlyRathAvg(), creditMonth);
        int maxMon = Integer.valueOf(productInfo.getTimeMaxVal());
        for (int j = minMon; j <= maxMon; j++) {
            tList.add(j + "个月");
        }

        setTopTitle(productInfo.getProductName());
        setLeftTv("返回");
        map.put(0, false);
        map.put(1, false);
        map.put(2, false);
        if (productInfo != null) {
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + productInfo.getImageLogoPath(), ivBank, options);
            tvProName.setText(productInfo.getProductName());
//            tvMount.setText(productInfo.getProductAmount());
//            tvMount.setText(getIntent().getStringExtra(CREDIT_AMOUNT));


            double minVal = Double.valueOf(productInfo.getMinVal());
            double maxVal = Double.valueOf(productInfo.getMaxVal());

            tvScope.setText("额度范围：" + df.format(minVal / 10000) + "万~" + df.format(maxVal / 10000) + "万");
//            tvLimit.setText(getIntent().getStringExtra(CREDIT_TIME));
            tvLim.setText("期限范围：" + productInfo.getTimeMinVal() + "~" + productInfo.getTimeMaxVal() + "月");
//            tvMonthPay.setText("¥" + productInfo.getMonthlyPay());
            tvPaytype.setText("还款方式：" + productInfo.get_payType());
//            tvTotal.setText(productInfo.getTotalInterest());
            tvLoan.setText(productInfo.getLoanPeriod() + "个工作日");
            tvRate.setText("利率：" + df.format(Double.valueOf(productInfo.getMonthlyRathAvg())) + "%");
            tvCon.setText(productInfo.getApplyCondition());
            tvNeed.setText(productInfo.getApplyMaterial());
            tvDetail.setText(productInfo.getDetailDescribe());
        }


    }


    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.rlRequire, R.id.rlMaterial, R.id.rlDetail, R.id.tvApplay, R.id.rlMonth, R.id.tvCal})
    public void toClick(View v) {
        switch (v.getId()) {
            //申请条件
            case R.id.rlRequire:

                if (!map.get(0)) {
                    obaDown1.start();
                    llRequire.setVisibility(View.VISIBLE);
//                    ivRequire.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(0, true);
                    scrollDown();
                } else {
                    obaOn1.start();
                    llRequire.setVisibility(View.GONE);
//                    ivRequire.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(0, false);
                }
                break;
            //所需材料
            case R.id.rlMaterial:
                if (!map.get(1)) {
                    obaDown2.start();
                    llMaterial.setVisibility(View.VISIBLE);
//                    ivMaterial.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(1, true);
                    scrollDown();
                } else {
                    obaOn2.start();
                    llMaterial.setVisibility(View.GONE);
//                    ivMaterial.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(1, false);
                }
                break;
            //详细说明
            case R.id.rlDetail:
                if (!map.get(2)) {
                    obaDown3.start();
                    llDetail.setVisibility(View.VISIBLE);
//                    ivDetail.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(2, true);
                    scrollDown();
                } else {
                    obaOn3.start();
                    llDetail.setVisibility(View.GONE);
//                    ivDetail.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(2, false);
                }
                break;
            //免费申请
            case R.id.tvApplay:
                AppManager.getAppManager().addActivity(CreditDetailAct.this);
                Bundle bundle = new Bundle();
                bundle.putParcelable(PRODUCTINFO, productInfo);
                gotoActivity(CreditStepAct.class, false, bundle);
                break;
            case R.id.rlMonth:
                int postition;
                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    postition = 0;
                } else {
                    postition = Integer.valueOf(tvM.getText().toString().trim()) - minMon;
                }

                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(CreditDetailAct.this, tList).builder(postition);
                dialogChooseMonth.show();
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvM.setText(adress.substring(0, adress.length() - 2));
                        if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
                            etAmount.setText("5");
                            creditMoney = "5";
                        } else {
                            creditMoney = etAmount.getText().toString().trim();
                        }
                        creditMonth = tvM.getText().toString();
                        final double minVal = Double.valueOf(productInfo.getMinVal());
                        double maxVal = Double.valueOf(productInfo.getMaxVal());
                        double curVal = Double.valueOf(creditMoney.toString()) * 10000;
                        validateCredit(minVal, maxVal, curVal, creditMonth);
//                        getMOnthPay(creditMoney, productInfo.getMonthlyRathAvg(), creditMonth);
                    }
                });
                break;
            //计算
            case R.id.tvCal:
                if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
                    etAmount.setText("5");
                    creditMoney = "5";
                } else {
                    creditMoney = etAmount.getText().toString().trim();
                }
                final double minVal = Double.valueOf(productInfo.getMinVal());
                double maxVal = Double.valueOf(productInfo.getMaxVal());
                double curVal = Double.valueOf(creditMoney.toString()) * 10000;
                creditMonth = tvM.getText().toString();
                validateCredit(minVal, maxVal, curVal, creditMonth);


                break;
        }
    }

    /**
     * 验证是否在额度范围
     *
     * @param minVal
     * @param maxVal
     * @param curVal
     */
    private void validateCredit(final double minVal, double maxVal, double curVal, String creditMonth) {
        if (curVal < minVal || curVal > maxVal) {
            etAmount.setText(df.format(maxVal / 10000) + "");
            etAmount.setSelection(etAmount.getText().length());
            getMOnthPay(maxVal / 10000 + "", productInfo.getMonthlyRathAvg(), creditMonth);
//            dialog=new MyAlertDialog(CreditDetailAct.this);
//            dialog.builder().setTitle("提示").setMsg("您输入的金额超出此产品金额范围，产品的贷款金额范围为"+
//                    df.format(minVal/10000) + "万~" + df.format(maxVal/10000)+"万").setPositiveButton("确定", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    etAmount.setText(df.format(minVal/10000));
//                    etAmount.setSelection(etAmount.getText().length());
//                }
//            }).show();
        } else {
            getMOnthPay(curVal / 10000 + "", productInfo.getMonthlyRathAvg(), creditMonth);

        }
    }

    /**
     * 计算月供
     *
     * @param repaymentMoney 贷款金额
     * @param rate           月利率
     * @param month          月份
     */
    private void getMOnthPay(String repaymentMoney, String rate, String month) {

        RequestManager.getUserManager().getMonthPay(Double.valueOf(repaymentMoney) * 10000 + "",
                Double.valueOf(rate) / 100 + "", month, new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) throws JSONException {
                        JSONObject json = new JSONObject(result);
                        JSONObject jsonData = json.getJSONObject("data");
                        monthlyPayments = jsonData.getDouble("monthlyPayments") + "";
                        totalRath = jsonData.getDouble("totalRath") + "";
                        tvTotal.setText("￥" + totalRath);
                        tvMonthPay.setText("￥" + monthlyPayments);
                    }

                    @Override
                    public void onError(int status, String msg) {

                        MyToastUtils.showShortToast(getApplicationContext(), msg);
                    }
                });
    }

    private ProductEntity productEntity;


    /**
     * 根据产品id获取信息
     *
     * @param productId
     */
    private void findOrderDetail(String productId) {
        RequestManager.getUserManager().findOrderDetailById(productId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<ProductEntity> pInfo = (ResultData<ProductEntity>) GsonUtils.json(result, ProductEntity.class);
                productEntity = pInfo.getData();
                if (productEntity != null) {
                    Double minMone = Double.valueOf(productEntity.getMinVal());
                    Double maxMone = Double.valueOf(productEntity.getMaxVal());
                    Double minMont = Double.valueOf(productEntity.getTimeMinVal());
                    Double maxMont = Double.valueOf(productEntity.getTimeMaxVal());
                    if (Double.valueOf(creditMoney) * 10000 < minMone || Double.valueOf(creditMoney) * 10000 > maxMone) {
                        creditMoney = df.format(maxMone / 10000) + "";
                    }

                    if (Double.valueOf(creditMonth) < minMont || Double.valueOf(creditMonth) > maxMont) {
                        creditMonth = maxMont + "";
                    }
                    etAmount.setText(creditMoney);
                    tvM.setText(creditMonth);

                    etAmount.setSelection(etAmount.getText().length());


                    minMon = Integer.valueOf(productEntity.getTimeMinVal());

                    getMOnthPay(creditMoney, productEntity.getMonthlyRate(), creditMonth);
                    int maxMon = Integer.valueOf(productEntity.getTimeMaxVal());
                    for (int j = minMon; j <= maxMon; j++) {
                        tList.add(j + "个月");
                    }


                    setTopTitle(productEntity.getProductName());
                    setLeftTv("返回");
                    map.put(0, false);
                    map.put(1, false);
                    map.put(2, false);

                    ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + productEntity.getImageLogoPath(), ivBank, options);
                    tvProName.setText(productEntity.getProductName());
//            tvMount.setText(productEntity.getProductAmount());
//            tvMount.setText(getIntent().getStringExtra(CREDIT_AMOUNT));


                    double minVal = Double.valueOf(productEntity.getMinVal());
                    double maxVal = Double.valueOf(productEntity.getMaxVal());

                    tvScope.setText("额度范围：" + df.format(minVal / 10000) + "万~" + df.format(maxVal / 10000) + "万");
//            tvLimit.setText(getIntent().getStringExtra(CREDIT_TIME));
                    tvLim.setText("期限范围：" + productEntity.getTimeMinVal() + "~" + productEntity.getTimeMaxVal() + "月");
//            tvMonthPay.setText("¥" + productInfo.getMonthlyPay());
                    tvPaytype.setText("还款方式：" + productEntity.getPayType());
//            tvTotal.setText(productInfo.getTotalInterest());
                    tvLoan.setText(productEntity.getLoanPeriod() + "个工作日");
                    if (Double.valueOf(productEntity.getMonthlyRathMin()) != Double.valueOf(productEntity.getMonthlyRathMax()))
                        tvRate.setText("利率：" + productEntity.getMonthlyRathMin() + "%" + "~" + productEntity.getMonthlyRathMax() + "%");
                    else
                        tvRate.setText("利率：" + productEntity.getMonthlyRathMin());
                    tvCon.setText(productEntity.getApplyCondition());
                    tvNeed.setText(productEntity.getApplyMaterial());
                    tvDetail.setText(productEntity.getDetailDescribe());
                    if(TextUtils.equals(productEntity.getDisposableRateMin().toString(),productEntity.getDisposableRateMax().toString()))
                        tvOnePay.setText("一次付费率：" + productEntity.getDisposableRateMin() + "%");
                    else
                        tvOnePay.setText("一次付费率：" + productEntity.getDisposableRateMin() + "%" + "~" + productEntity.getDisposableRateMax() + "%");
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
