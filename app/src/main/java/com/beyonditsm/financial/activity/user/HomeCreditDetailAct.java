package com.beyonditsm.financial.activity.user;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.vip.VipAct;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.widget.DialogChooseMonth;
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
 * Created by Administrator on 2015/12/18
 */
@SuppressWarnings("deprecation")
public class HomeCreditDetailAct extends BaseActivity {
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
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.ll_speed)
    private LinearLayout llSpeed;
    @ViewInject(R.id.ll_mm)
    private LinearLayout llMM;


    private Map<Integer, Boolean> map = new HashMap<>();


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
    private LoadingView loadView;
    private int minMon;

    public static String creditMoney;
    public static String creditMonth;
    public static String monthlyPayments;//月供
    private String totalRath;

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");//保留小数

//    private ProductInfo productInfo;

    private ArrayList<String> tList = new ArrayList<>();

    private ObjectAnimator obaDown1;
    private ObjectAnimator obaOn1;
    private ObjectAnimator obaDown2;
    private ObjectAnimator obaOn2;
    private ObjectAnimator obaDown3;
    private ObjectAnimator obaOn3;


    public static final String PRODUCTINFO = "productinfo";

    public static final String CREDIT_AMOUNT = "credit_mount";
    public static final String CREDIT_TIME = "credit_time";
    public static final String CREDIT_NAME = "credit_name";

    public static final String CREIT_TYPE = "credit_type";


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private ProductInfo productEntity;
    private TextView tvOnePay;
    private UserLoginEntity ule;


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
        tvOnePay = (TextView) findViewById(R.id.onpay);
        loadView = (LoadingView) findViewById(R.id.loadView);
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
    public void setLayout() {
        setContentView(R.layout.activity_credit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //强制关闭键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        assignViews();
        initAnim();
        getUserLoginInfo();
        String creditName = getIntent().getStringExtra(CREDIT_NAME);
        setTopTitle(creditName);
        if (!TextUtils.isEmpty(creditName)&&creditName.length() > 14) {
            tvTitle.setTextSize(14);
        }

        final String productId = getIntent().getStringExtra(PRODUCTINFO);
        creditMoney = getIntent().getStringExtra(CREDIT_AMOUNT);
        creditMonth = getIntent().getStringExtra(CREDIT_TIME);
        tvM.setText(creditMonth);
        findOrderDetail(productId);

        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findOrderDetail(productId);
            }
        });

        /*把回车键换成搜索*/
//        etAmount.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etAmount.addTextChangedListener(textWatcher);


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            etAmount.setCursorVisible(true);
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    etAmount.setText(s);
                    etAmount.setSelection(s.length());
                }
            }
            if (s.toString().trim().equals(".")) {
                s = "0" + s;
                etAmount.setText(s);
                etAmount.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    etAmount.setText(s.subSequence(0, 1));
                    etAmount.setSelection(1);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            //如果用户输入的数值比额度范围最小值小则计算最小的，比额度范围值最大的大计算最大的
            if (!s.toString().startsWith(".")) {


                if (s.toString().trim().length() == 0) {
                    creditMoney = "0.0";
                }
                if (!TextUtils.isEmpty(etAmount.getText().toString().trim())) {
                    creditMoney = etAmount.getText().toString().trim();
                }
                if(null!=productEntity){
                    if (null!=productEntity.getMinVal() && null != productEntity.getMinVal()){
                        double curVal = Double.valueOf(creditMoney) * 10000;
                        if (TextUtils.isEmpty(creditMonth)) {
                            double minTimeVal = Double.valueOf(productEntity.getTimeMinVal());
                            creditMonth = minTimeVal + "";
                        } else {
                            creditMonth = tvM.getText().toString();
                        }
                        validateCredit(curVal, creditMonth);
                    }

                }

            } else if (s.toString().startsWith(".")) {
                Toast.makeText(HomeCreditDetailAct.this, "不能以小数点开头", Toast.LENGTH_SHORT).show();
                etAmount.setText("");
            }
        }
    };

    /**
     * 点击事件
     *
     */
    @OnClick({R.id.rlRequire, R.id.rlMaterial, R.id.rlDetail, R.id.tvApplay, R.id.rlMonth, R.id.tvCal, R.id.etAmount,R.id.tvBuy})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //搜索
//            case R.id.etAmount:
//
//                /*监听回车键*/
//                etAmount.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                            FinancialUtil.closeIM(HomeCreditDetailAct.this, etAmount);
//                            if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
//                                etAmount.setText("5");
//                                creditMoney = "5";
//                            } else {
//                                creditMoney = etAmount.getText().toString().trim();
//                            }
//                            final double minVal = Double.valueOf(productEntity.getMinVal());
//                            double maxVal = Double.valueOf(productEntity.getMaxVal());
//                            double curVal = Double.valueOf(creditMoney.toString()) * 10000;
//                            creditMonth = tvM.getText().toString();
//                            validateCredit(minVal, maxVal, curVal, creditMonth);
//
//                            return true;
//                        }
//
//                        return false;
//                    }
//                });
//
//                break;
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
                if (productEntity != null) {
                    final double minVal = Double.valueOf(productEntity.getMinVal());
                    double maxVal = Double.valueOf(productEntity.getMaxVal());
                    double curVal = Double.valueOf(creditMoney) * 10000;
                    if (curVal < minVal || curVal > maxVal) {
                        Toast.makeText(HomeCreditDetailAct.this, "您输入的金额不在额度范围内", Toast.LENGTH_SHORT).show();
                    } else {
                        AppManager.getAppManager().addActivity(HomeCreditDetailAct.this);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PRODUCTINFO, productEntity);
//                        bundle.putString(CreditStepAct.TAG_TYPE,"comm");
                        gotoActivity(CreditStepAct.class, false, bundle);
                    }
                }
                break;
            case R.id.rlMonth:
                int postition;
                if (TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    postition = 0;
                } else {
                    postition = Integer.valueOf(tvM.getText().toString().trim()) - minMon;
                }

                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(HomeCreditDetailAct.this, tList).builder(postition);
                dialogChooseMonth.show();
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvM.setText(adress.substring(0, adress.length() - 2));
                        if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
                            MyToastUtils.showShortToast(getApplicationContext(), "金额不能为空！");
                        } else {
                            creditMoney = etAmount.getText().toString().trim();
                        }

                        creditMonth = tvM.getText().toString();

                        double curVal = Double.valueOf(creditMoney) * 10000;
                        validateCredit(curVal, creditMonth);
//                        getMOnthPay(creditMoney, productInfo.getMonthlyRathAvg(), creditMonth);
                    }
                });
                break;
            //计算
            case R.id.tvCal:
                if (TextUtils.isEmpty(etAmount.getText().toString().trim()) || TextUtils.isEmpty(tvM.getText().toString().trim())) {
                    MyToastUtils.showShortToast(HomeCreditDetailAct.this, "贷款金额和贷款期限不能为空");
                    return;
                } else {
                    if (etAmount.getText().toString().trim().endsWith(".")) {
                        int length = etAmount.getText().toString().length();
                        etAmount.setText(etAmount.getText().toString().substring(0, length - 1));
                    }
                    creditMoney = etAmount.getText().toString().trim();
                    creditMonth = tvM.getText().toString();
                }
                double curVal = Double.valueOf(creditMoney) * 10000;
                validateCredit(curVal, creditMonth);


                break;

            case R.id.tvBuy://购买VIP
                if (!TextUtils.isEmpty(SpUtils.getRoleName(getApplicationContext()))){
                    intent = new Intent(HomeCreditDetailAct.this, VipAct.class);
                    intent.putExtra("user", ule);
                }else{
                    intent = new Intent(HomeCreditDetailAct.this,LoginAct.class);
                }

                startActivity(intent);
                break;
        }
    }

    /**
     * 验证是否在额度范围
     *
     * @param curVal 当前金额
     */
    private void validateCredit(double curVal, String creditMonth) {
        String monthRath = (Double.valueOf(productEntity.getMonthlyRathMin()) + Double.valueOf(productEntity.getMonthlyRathMax())) / 2 + "";

        getMOnthPay(curVal / 10000 + "", monthRath, creditMonth);

      /*  //如果最小额度在0-1之间
        if((minVal/10000)>0&&(minVal/10000)<1) {
            if (curVal == 0) {

            } else {
                if (curVal < minVal || curVal > maxVal) {
                    //如果用户输入的数值比额度范围最小值小则计算最小的，比额度范围值最大的大计算最大的
                    if (curVal < minVal) {
                        Toast.makeText(HomeCreditDetailAct.this, "您输入的金额数字不在额度范围内,默认为您设置为最小额度", Toast.LENGTH_SHORT).show();
                        etAmount.setText(df.format(minVal / 10000) + "");
                        etAmount.setSelection(etAmount.getText().length());
                        getMOnthPay(minVal / 10000 + "", monthRath, creditMonth);
                    }
                    if (curVal > maxVal) {
                        Toast.makeText(HomeCreditDetailAct.this, "您输入的金额数字不在额度范围内,默认为您设置为最大额度", Toast.LENGTH_SHORT).show();
                        etAmount.setText(df.format(maxVal / 10000) + "");
                        etAmount.setSelection(etAmount.getText().length());
                        getMOnthPay(maxVal / 10000 + "", monthRath, creditMonth);
                    }

                } else {
                    getMOnthPay(curVal / 10000 + "", monthRath, creditMonth);

                }
            }
        }else {
            if(curVal>0&&curVal<minVal){

            }else {
                if (curVal < minVal || curVal > maxVal) {
                    //如果用户输入的数值比额度范围最小值小则计算最小的，比额度范围值最大的大计算最大的
                    if (curVal < minVal) {
                        Toast.makeText(HomeCreditDetailAct.this, "您输入的金额数字不在额度范围内,默认为您设置为最小额度", Toast.LENGTH_SHORT).show();
                        etAmount.setText(df.format(minVal / 10000) + "");
                        etAmount.setSelection(etAmount.getText().length());
                        getMOnthPay(minVal / 10000 + "", monthRath, creditMonth);
                    }
                    if (curVal > maxVal) {
                        Toast.makeText(HomeCreditDetailAct.this, "您输入的金额数字不在额度范围内,默认为您设置为最大额度", Toast.LENGTH_SHORT).show();
                        etAmount.setText(df.format(maxVal / 10000) + "");
                        etAmount.setSelection(etAmount.getText().length());
                        getMOnthPay(maxVal / 10000 + "", monthRath, creditMonth);
                    }

                } else {
                    getMOnthPay(curVal / 10000 + "", monthRath, creditMonth);

                }
            }
        }*/
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
                        tvTotal.setText(totalRath);
                        tvMonthPay.setText(monthlyPayments);
                    }

                    @Override
                    public void onError(int status, String msg) {
                        MyToastUtils.showShortToast(getApplicationContext(), msg);
                    }
                });
    }

    private void findOrderDetail(String productId) {
        RequestManager.getUserManager().findOrderDetailById(productId, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                ResultData<ProductInfo> pInfo = (ResultData<ProductInfo>) GsonUtils.json(result, ProductInfo.class);
                productEntity = pInfo.getData();
                if (productEntity != null) {
                    Double minMone = Double.valueOf(productEntity.getMinVal());
                    Double maxMone = Double.valueOf(productEntity.getMaxVal());
                    Double minMont = Double.valueOf(productEntity.getTimeMinVal());
                    Double maxMont = Double.valueOf(productEntity.getTimeMaxVal());

                    if (TextUtils.isEmpty(creditMonth) && TextUtils.isEmpty(creditMoney)) {
                        creditMoney = df.format(minMone / 10000) + "";
                        creditMonth = productEntity.getTimeMinVal();
                        tvM.setText(creditMonth);
                    } else {
                        if (Double.valueOf(creditMoney) * 10000 > maxMone) {
                            creditMoney = df.format(maxMone / 10000) + "";
                        }
                        if (Double.valueOf(creditMoney) * 10000 < minMone) {
                            creditMoney = df.format(minMone / 10000) + "";
                        }
//                        if (Double.valueOf(creditMoney) * 10000 < minMone || Double.valueOf(creditMoney) * 10000 > maxMone) {
//                            creditMoney = df.format(maxMone / 10000) + "";
//                        }
                        if (Double.valueOf(creditMonth) > maxMont) {
                            creditMonth = productEntity.getTimeMaxVal();
                            tvM.setText(creditMonth);
                        }
                        if (Double.valueOf(creditMonth) < minMont) {
                            creditMonth = productEntity.getTimeMinVal();
                            tvM.setText(creditMonth);
                        }
//                        if (Double.valueOf(creditMonth) < minMont || Double.valueOf(creditMonth) > maxMont) {
//                            creditMonth = maxMont + "";
//                        }
                    }

                    etAmount.setText(creditMoney);
//                    tvM.setText(creditMonth);
                    etAmount.setSelection(etAmount.getText().length());
                    minMon = Integer.valueOf(productEntity.getTimeMinVal());

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
                    if (!TextUtils.isEmpty(productEntity.getPayTypeName())) {
                        tvPaytype.setText("还款方式：" + productEntity.getPayTypeName());
                    }
//            tvTotal.setText(productInfo.getTotalInterest());
                    if (!TextUtils.isEmpty(productEntity.getLoanPeriod())) {
                        tvLoan.setText(productEntity.getLoanPeriod() + "个工作日");
                    }
                    if (!TextUtils.isEmpty(productEntity.getMonthlyRathMin()) || !TextUtils.isEmpty(productEntity.getMonthlyRathMax())) {
                        if (Double.valueOf(productEntity.getMonthlyRathMin()) - Double.valueOf(productEntity.getMonthlyRathMax()) == 0) {
                            tvRate.setText("月利率：" + productEntity.getMonthlyRathMin() + "%");
                        } else {
                            tvRate.setText("月利率：" + productEntity.getMonthlyRathMin() + "%~" + productEntity.getMonthlyRathMax() + "%");
                        }
                    }
                    if (!TextUtils.isEmpty(productEntity.getApplyCondition())) {
                        tvCon.setText(productEntity.getApplyCondition());
                    }
                    if (!TextUtils.isEmpty(productEntity.getApplyMaterial())) {
                        tvNeed.setText(productEntity.getApplyMaterial());
                    }
                    if (!TextUtils.isEmpty(productEntity.getDetailDescribe())) {
                        tvDetail.setText(productEntity.getDetailDescribe());
                    }
                    if (!TextUtils.isEmpty(productEntity.getDisposableRateMax()) || !TextUtils.isEmpty(productEntity.getDisposableRateMin())) {
                        if (TextUtils.isEmpty(productEntity.getDisposableRateMax())){
                            productEntity.setDisposableRateMax("0.0");
                        }
                        if (TextUtils.isEmpty(productEntity.getDisposableRateMin())){
                            productEntity.setDisposableRateMin("0.0");
                        }
                        if (Double.valueOf(productEntity.getDisposableRateMax()) - Double.valueOf(productEntity.getDisposableRateMin()) == 0) {
                            tvOnePay.setText("一次性收费：" + productEntity.getDisposableRateMax() + "%");
                        } else {
                            tvOnePay.setText("一次性收费：" + productEntity.getDisposableRateMin() + "%" + "~" + productEntity.getDisposableRateMax() + "%");
                        }

                    }
                    String monthRath = (Double.valueOf(productEntity.getMonthlyRathMin()) + Double.valueOf(productEntity.getMonthlyRathMax())) / 2 + "";
                    getMOnthPay(creditMoney, monthRath, creditMonth);
                }
            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
            }
        });
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200){
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
