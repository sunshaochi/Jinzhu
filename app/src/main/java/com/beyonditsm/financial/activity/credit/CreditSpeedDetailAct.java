package com.beyonditsm.financial.activity.credit;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.speedcredit.CreditSpeedSecond_1Act;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.entity.LoadUseEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.ProductStatuUtil;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极速贷贷款详情页
 * Created by Administrator on 2016/9/27 0027.
 */

public class CreditSpeedDetailAct extends BaseActivity {

    @ViewInject(R.id.ivRequire)
    private ImageView ivRequire;//申请条件
    @ViewInject(R.id.llRequire)
    private LinearLayout llRequire;
    @ViewInject(R.id.ivMaterial)
    private ImageView ivMaterial;//所需材料
    @ViewInject(R.id.llMaterial)
    private LinearLayout llMaterial;
    @ViewInject(R.id.ivDetail)
    private ImageView ivDetail;//详细说明
    @ViewInject(R.id.llDetail)
    private LinearLayout llDetail;

    @ViewInject(R.id.svCridet)
    private ScrollView svCridet;

    @ViewInject(R.id.tvScope)
    private TextView tvScope;//额度范围

    @ViewInject(R.id.tv_purpose)//借款用途
    private TextView tvPurpose;
    @ViewInject(R.id.tvPaytype)//还款方式
    private TextView tvPayType;
    @ViewInject(R.id.tvLoan)//放款时间
    private TextView tvLoan;
    @ViewInject(R.id.tvRate)//综合费率
    private TextView tvRate;
    @ViewInject(R.id.tvCon)
    private TextView tvCon;//申请条件
    @ViewInject(R.id.tvNeed)
    private TextView tvNeed;//申请材料
    @ViewInject(R.id.tvDetail)
    private TextView tvDetail;//详细说明
    @ViewInject(R.id.tvProName)
    private TextView tvProName;
    @ViewInject(R.id.ivBank)
    private ImageView ivBank;
    @ViewInject(R.id.tvLim)
    private TextView tvLim;//期限范围
    @ViewInject(R.id.tv_speedWeek)
    private TextView tvSpeedWeek;
    @ViewInject(R.id.et_speedAmount)//金额
    private EditText etSpeedAmount;
    @ViewInject(R.id.tvTotal)
    private TextView tvTotal;
    @ViewInject(R.id.tv_speedBearing)
    private EditText etSpeedBearing;//最多可承受每周还款金额
    @ViewInject(R.id.tvspeedMonth)//期限后面的单位
    private TextView tvspeedMonth;
    @ViewInject(R.id.tvSpeedBearing)
    private TextView tvSpeedBearing;//每月可承受最多还款

    private Map<Integer, Boolean> map = new HashMap<>();
    private ObjectAnimator obaDown1;
    private ObjectAnimator obaOn1;
    private ObjectAnimator obaDown2;
    private ObjectAnimator obaOn2;
    private ObjectAnimator obaDown3;
    private ObjectAnimator obaOn3;
    private String purposeId;
    private String minrata;//最低利率
    private Map<String, String> maplist = new HashMap<String, String>();
    private String productId;

    public static final String CREDIT_TYPE = "credit_type";
    public static final String PURPOSE = "purpose";
    public static final String MAXREPAYMENTWEEKLY = "maxRepaymentWeekly";//最多可承受还款
    public static final String TOTALAMOUNT = "totalAmount";//输入的金额
    public static final String TOTALPERIODS = "totalPeriods";//输入期限
    public static final String TOTALLOANINTEREST = "totalLoanInterest";//总利息
    public static final String REALMONTHLYRATE = "realMonthlyRate";//最小利率
    public static final String SPEED_CREDIT_PRODUCT_ID = "product_id";

    private List<LoadUseEntity> loadUseEntityList;

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");//保留小数

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private ArrayList<String> mList = new ArrayList<>();
    private List<String> list = new ArrayList<>();//期限
    private int minWeek;
    private CreditSpeedEntity creditSpeedEntity;
    private String creditMoney;
    private String creditMonth;
    private ArrayList<CreditSpeedEntity.ResideStatusmapBean> propertyTypeList;
    private ArrayList<CreditSpeedEntity.debtTypemapBean> jobIdentitysList;
    private ArrayList<CreditSpeedEntity.PayTypessBean> payTypessList;
    private String payType;
    //    private List<String> jobIdentitysList = new ArrayList<>();
//    private List<String> propertyTypeList;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeed_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        productId = getIntent().getExtras().getString("productId");
        getinfo(productId);
    }

    private void setinfo() {
        if (creditSpeedEntity != null) {
            setTopTitle(creditSpeedEntity.getProductName());
            if (!TextUtils.isEmpty(creditSpeedEntity.getRepaymentTerm().toString())) {//期限下拉选项
                maplist = creditSpeedEntity.getRepaymentTerm();
                for (String key : maplist.keySet()) {

                    mList.add(maplist.get(key));//期限下拉选项
                }

            }
            if (creditSpeedEntity.getDebtTypemap() != null && creditSpeedEntity.getDebtTypemap().size() > 0) {//借款用途
                jobIdentitysList = (ArrayList<CreditSpeedEntity.debtTypemapBean>) creditSpeedEntity.getDebtTypemap();
            }

            if (creditSpeedEntity.getResideStatusmap() != null && creditSpeedEntity.getResideStatusmap().size() > 0) {//居住状态
                propertyTypeList = (ArrayList<CreditSpeedEntity.ResideStatusmapBean>) creditSpeedEntity.getResideStatusmap();
            }

            if (creditSpeedEntity.getPaytypemap() != null && creditSpeedEntity.getPaytypemap().size() > 0) {//还款方式
                payTypessList = (ArrayList<CreditSpeedEntity.PayTypessBean>) creditSpeedEntity.getPaytypemap();
            }


            if (!TextUtils.isEmpty(creditSpeedEntity.getMinVal())) {//最小额度
                creditMoney=creditSpeedEntity.getMaxVal();
                etSpeedAmount.setText(creditSpeedEntity.getMinVal());//金额
            }

            if ((!TextUtils.isEmpty(creditSpeedEntity.getMinLoanPeriod()))) {//最小期限
//                String[] split = creditSpeedEntity.getMortgageType().split(",");
////                String timeMinValue = creditSpeedEntity.getRepaymentPeriod().substring(0, 2);
//                String minTime = split[0];
////                String timeMinValue = minTime.split("周");
//                String timeMinValue = minTime.substring(0, minTime.length()-1);
               creditMonth = creditSpeedEntity.getMinLoanPeriod();

                tvSpeedWeek.setText(creditMonth);//期限
            }


//            double v = Double.valueOf(etSpeedAmount.getText().toString().trim()) * (Integer.valueOf(tvSpeedWeek.getText().toString()) * 7) * 0.003;
//            tvTotal.setText(String.valueOf(v));
            if (!TextUtils.isEmpty(creditSpeedEntity.getImageLogoPath())) {//图片
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + creditSpeedEntity.getImageLogoPath(), ivBank, options);
            }
            double minVal = Double.valueOf(creditSpeedEntity.getMinVal());//最小额度
            double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());//最大额度

            tvScope.setText("额度范围：" + df.format(minVal / 10000) + "-" + df.format(maxVal / 10000) + "万");
            tvProName.setText(creditSpeedEntity.getProductName());

            if (!TextUtils.isEmpty(creditSpeedEntity.getLoanPeriodType())) {//获取期限类型
                String protype= ProductStatuUtil.getProStatu(creditSpeedEntity.getLoanPeriodType());
                if (!TextUtils.isEmpty(creditSpeedEntity.getMinLoanPeriod())||!TextUtils.isEmpty(creditSpeedEntity.getMaxLoanPeriod())) {//最高最低还款期限
                    if (Double.valueOf(creditSpeedEntity.getMinLoanPeriod()) - Double.valueOf(creditSpeedEntity.getMaxLoanPeriod()) == 0) {
                        tvLim.setText("期限范围：" +creditSpeedEntity.getMinLoanPeriod()+protype+"");
                    } else {
                        tvLim.setText("期限范围：" + creditSpeedEntity.getMinLoanPeriod() + "-" +creditSpeedEntity.getMaxLoanPeriod()+protype);
                    }
                }
                tvspeedMonth.setText(protype);//期限选择框单位

            }


                if (payTypessList != null && payTypessList.size() > 0) {//还款方式
                    String payType = payTypessList.get(0).getName();
                    if (!TextUtils.isEmpty(payType)) {
                        tvPayType.setText("还款方式：" + payType);
                    }
                }

                if (!TextUtils.isEmpty(creditSpeedEntity.getMinRate()) || !TextUtils.isEmpty(creditSpeedEntity.getMaxRate())) {//综合费率
                    if (Double.valueOf(creditSpeedEntity.getMaxRate()) - Double.valueOf(creditSpeedEntity.getMinRate()) == 0) {
                        tvRate.setText(creditSpeedEntity.getMinRate());//综合费率
                    } else {
                        tvRate.setText(creditSpeedEntity.getMinRate() + "%-" + creditSpeedEntity.getMaxRate());
                    }
                }
            Double monthRath = Double.valueOf(creditSpeedEntity.getMinRate());
            getTotlerate(monthRath,Double.parseDouble(creditMoney),Integer.parseInt(creditMonth));//获取总利息

                if (!TextUtils.isEmpty(creditSpeedEntity.getLoanPeriod())) {//放款周期
                    tvLoan.setText(creditSpeedEntity.getLoanPeriod() + "个工作日");
                }


                if (!TextUtils.isEmpty(creditSpeedEntity.getApplyCondition())) {
                    tvCon.setText(creditSpeedEntity.getApplyCondition());//申请条件
                }
                if (!TextUtils.isEmpty(creditSpeedEntity.getApplyMaterial())) {
                    tvNeed.setText(creditSpeedEntity.getApplyMaterial());//所需材料
                }
                if (!TextUtils.isEmpty(creditSpeedEntity.getDetailDescribe())) {
                    tvDetail.setText(creditSpeedEntity.getDetailDescribe());//详细说明
                }



            initAnim();
            setLeftTv("返回");
            map.put(0, false);
            map.put(1, false);
            map.put(2, false);

//        queryLoanUse();

            etSpeedAmount.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 通过id获取详情
     *
     * @param productid
     */
    private void getinfo(String productid) {
        RequestManager.getCommManager().getProductDetail(productid, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<CreditSpeedEntity> rd = (ResultData<CreditSpeedEntity>) GsonUtils.json(result, CreditSpeedEntity.class);
                creditSpeedEntity = rd.getData();
                MyLogUtils.info("hahahahah" + creditSpeedEntity);
                setinfo();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    //获取单期利息
    private void getTotlerate(double rate,double repaymentMoney ,int month) {
        rate = rate/100;
        double sum = repaymentMoney* rate * month * 7;
        BigDecimal big = new BigDecimal(sum);
        Double monthpay=big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入
        tvTotal.setText(monthpay+"");
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            etSpeedAmount.setCursorVisible(true);//获取光标
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    etSpeedAmount.setText(s);
                    etSpeedAmount.setSelection(s.length());
                }
            }
            if (s.toString().trim().equals(".")) {
                s = "0" + s;
                etSpeedAmount.setText(s);
                etSpeedAmount.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    etSpeedAmount.setText(s.subSequence(0, 1));
                    etSpeedAmount.setSelection(1);
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
                if (!TextUtils.isEmpty(etSpeedAmount.getText().toString().trim())) {
                    creditMoney = etSpeedAmount.getText().toString().trim();
                }
                if (null != creditSpeedEntity) {
                    if (null != creditSpeedEntity.getMinVal() && null != creditSpeedEntity.getMaxVal()) {
                        double curVal = Double.valueOf(creditMoney);
                        if (TextUtils.isEmpty(creditMonth)) {
                            minWeek = Integer.valueOf(creditMonth);
                            creditMonth = minWeek + "";
                        } else {
                            creditMonth = tvSpeedWeek.getText().toString();
                        }
                        validateCredit(curVal, creditMonth);
                    }

                }

            } else if (s.toString().startsWith(".")) {
                Toast.makeText(CreditSpeedDetailAct.this, "不能以小数点开头", Toast.LENGTH_SHORT).show();
                etSpeedAmount.setText("");
            }
        }
    };

    /**
     * 计算利息
     *
     * @param curVal      金额
     * @param creditMonth 期数
     */
    private void validateCredit(double curVal, String creditMonth) {
//        Double minVal = Double.valueOf(creditSpeedEntity.getMinVal());
//        Double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());
//        if (curVal < minVal|| curVal > maxVal) {
//            //如果用户输入的数值比额度范围最小值小则计算最小的，比额度范围值最大的大计算最大的
//            if (curVal < minVal) {
//                Toast.makeText(CreditSpeedDetailAct.this, "金额范围最小值为"+minVal, Toast.LENGTH_SHORT).show();
////                etSpeedAmount.setText(df.format(minVal) + "");
//                etSpeedAmount.setSelection(etSpeedAmount.getText().length());
//                double v = minVal * Integer.valueOf(creditMonth) * 7 * 0.3 / 100;
//                tvTotal.setText(String.valueOf(v));
//            }
//            if (curVal > maxVal) {
//                Toast.makeText(CreditSpeedDetailAct.this, "金额范围最大值为"+maxVal, Toast.LENGTH_SHORT).show();
////                etSpeedAmount.setText(df.format(maxVal) + "");
//                etSpeedAmount.setSelection(etSpeedAmount.getText().length());
//                double v = maxVal * Integer.valueOf(creditMonth) * 7 * 0.3 / 100;
//                tvTotal.setText(String.valueOf(v));
//            }
//
//        } else {
//        double v = curVal * Integer.valueOf(creditMonth) * 7 * Double.valueOf(creditSpeedEntity.getMinRate()) / 100;
//        tvTotal.setText(String.valueOf(v));


//        }

        Double monthRath = (Double.valueOf(creditSpeedEntity.getMinRate()) + Double.valueOf(creditSpeedEntity.getMaxRate())) / 2;
        getTotlerate(monthRath,Double.parseDouble(creditMoney),Integer.parseInt(creditMonth));//获取总利息
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

    /**
     * 点击事件
     *
     * @param v View
     */
    @OnClick({R.id.rlRequire, R.id.rlMaterial, R.id.rlDetail, R.id.tvApplay, R.id.rlMonth, R.id.tvCal, R.id.rl_purpose, R.id.rl_speedMonth})
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
//                AppManager.getAppManager().addActivity(CreditSpeedDetailAct.this);
//                Bundle bundle = new Bundle();
////                bundle.putParcelable(PRODUCTINFO, productInfo);
//                bundle.putString(CREDIT_TYPE,"speed");
//                gotoActivity(CreditStepAct.class, false, bundle);


                if (isHaveData()) {


//                    if (TextUtils.isEmpty(SpUtils.getRoleName(CreditSpeedDetailAct.this))) {
//                        gotoActivity(CreditSpeedFirstAct.class, false);
//                    } else {

                    Bundle bundle = new Bundle();
                    bundle.putString(SPEED_CREDIT_PRODUCT_ID, creditSpeedEntity.getProductId());//产品id
                    bundle.putString(PURPOSE, purposeId + "");
                    bundle.putString(MAXREPAYMENTWEEKLY, etSpeedBearing.getText().toString() + "");//最多可承受还款
                    bundle.putString(TOTALAMOUNT, etSpeedAmount.getText().toString().trim() + "");//输入金额
                    bundle.putString(TOTALPERIODS, tvSpeedWeek.getText().toString() + "");//输入期限
                    bundle.putString(TOTALLOANINTEREST, tvTotal.getText().toString() + "");//总利息
                    bundle.putString(REALMONTHLYRATE, creditSpeedEntity.getMinRate() + "");//最小利率
                    ParamsUtil.getInstance().setPropertyTypeList(propertyTypeList);//居住状况


                    gotoActivity(CreditSpeedSecond_1Act.class, false, bundle);
//                    }


//                    SubmitCreditSpeedEntity submitCreditSpeedEntity = new SubmitCreditSpeedEntity();
//
//                    submitCreditSpeedEntity.setPurpose(purposeId+"");
//                    submitCreditSpeedEntity.setMaxRepaymentWeekly(etSpeedBearing.getText().toString());
//                    submitCreditSpeedEntity.setTotalAmount(etSpeedAmount.getText().toString().trim());
//                    submitCreditSpeedEntity.setTotalPeriods(tvSpeedWeek.getText().toString());
//                    submitCreditSpeedEntity.setTotalLoanInterest(tvTotal.getText().toString());
//                    submitCreditSpeedEntity.setRealMonthlyRate(creditSpeedEntity.getMinRate());
//                    submitOreder(creditSpeedEntity.getProductId(),submitCreditSpeedEntity);
                }


                break;

            case R.id.rl_purpose://用途

                MySelfSheetDialog mySelfSheetDialog = new MySelfSheetDialog(CreditSpeedDetailAct.this).builder();
                if (jobIdentitysList.size() != 0) {
                    for (int i = 0; i < jobIdentitysList.size(); i++) {
                        mySelfSheetDialog.addSheetItem(jobIdentitysList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvPurpose.setText(jobIdentitysList.get(which - 1).getName());
                                purposeId = jobIdentitysList.get(which - 1).getId();
                            }
                        });
                    }
                }
                mySelfSheetDialog.show();
                break;
            //期限
            case R.id.rl_speedMonth:
//                int postition;
//                if (TextUtils.isEmpty(tvSpeedWeek.getText().toString().trim())) {
//                    postition = 0;
//                } else {
//                    postition = Integer.valueOf(tvSpeedWeek.getText().toString().trim())-minWeek;
//                }
                DialogChooseMonth dialogChooseMonth = new DialogChooseMonth(CreditSpeedDetailAct.this, mList).builder(0);
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChooseMonth.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvSpeedWeek.setText(adress.substring(0, adress.length() - 1));

                        if (TextUtils.isEmpty(etSpeedAmount.getText().toString().trim())) {
                            MyToastUtils.showShortToast(getApplicationContext(), "金额不能为空！");
                        } else {
                            creditMoney = etSpeedAmount.getText().toString().trim();
                        }

                        creditMonth = tvSpeedWeek.getText().toString();

                        double curVal = Double.valueOf(creditMoney);
                        validateCredit(curVal, creditMonth);
                    }
                });
                dialogChooseMonth.show();
                break;
        }
    }

    private boolean isHaveData() {
        if (TextUtils.isEmpty(etSpeedAmount.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedDetailAct.this, "请输入金额");
            etSpeedAmount.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvPurpose.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedDetailAct.this, "还没选择借款用途");
            tvPurpose.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etSpeedBearing.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedDetailAct.this, "请输入每周可承受最多还款");
            etSpeedBearing.requestFocus();
            return false;
        }
        Double minVal = Double.valueOf(creditSpeedEntity.getMinVal());
        Double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());
        Double curVal = Double.valueOf(etSpeedAmount.getText().toString());
        //如果用户输入的数值比额度范围最小值小则计算最小的，比额度范围值最大的大计算最大的
        if (curVal < minVal) {
            Toast.makeText(CreditSpeedDetailAct.this, "金额范围最小值为" + minVal, Toast.LENGTH_SHORT).show();
//                etSpeedAmount.setText(df.format(minVal) + "");
            etSpeedAmount.requestFocus();
//                double v = minVal * Integer.valueOf(creditMonth) * 7 * 0.3 / 100;
//                tvTotal.setText(String.valueOf(v));
            return false;
        }
        if (curVal > maxVal) {
            Toast.makeText(CreditSpeedDetailAct.this, "金额范围最大值为" + maxVal, Toast.LENGTH_SHORT).show();
//                etSpeedAmount.setText(df.format(maxVal) + "");
            etSpeedAmount.requestFocus();
//                etSpeedAmount.setSelection(etSpeedAmount.getText().length());
//                double v = maxVal * Integer.valueOf(creditMonth) * 7 * 0.3 / 100;
//                tvTotal.setText(String.valueOf(v));
            return false;
        }
        return true;
    }

//    private void submitOreder(String productId,SubmitCreditSpeedEntity submitEntity) {
//        RequestManager.getCommManager().submitSpeedOrder(productId,submitEntity, new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                JSONObject jsonObject = new JSONObject(result);
//                String orderId = jsonObject.getString("data");
//                Bundle bundle = new Bundle();
//                bundle.putString(SPEED_CREDIT_ORDER_ID,orderId);
//                if (TextUtils.isEmpty(SpUtils.getRoleName(CreditSpeedDetailAct.this))) {
//                    gotoActivity(CreditSpeedFirstAct.class, false);
//                } else {
//                    gotoActivity(CreditSpeedSecond_1Act.class, false,bundle);
//                }
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                MyToastUtils.showShortToast(CreditSpeedDetailAct.this,msg);
//            }
//        });
//    }
}
