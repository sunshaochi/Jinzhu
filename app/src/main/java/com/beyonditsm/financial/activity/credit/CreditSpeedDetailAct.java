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
import com.beyonditsm.financial.activity.speedcredit.CreditSpeedFirstAct;
import com.beyonditsm.financial.activity.speedcredit.CreditSpeedSecond_1Act;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.entity.LoadUseEntity;
import com.beyonditsm.financial.entity.SubmitCreditSpeedEntity;
import com.beyonditsm.financial.fragment.SpeedCreditFrag;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
    @ViewInject(R.id.et_speedAmount)
    private EditText etSpeedAmount;
    @ViewInject(R.id.tvTotal)
    private TextView tvTotal;
    @ViewInject(R.id.tv_speedBearing)
    private EditText etSpeedBearing;//最多可承受每周还款金额

    private Map<Integer, Boolean> map = new HashMap<>();
    private ObjectAnimator obaDown1;
    private ObjectAnimator obaOn1;
    private ObjectAnimator obaDown2;
    private ObjectAnimator obaOn2;
    private ObjectAnimator obaDown3;
    private ObjectAnimator obaOn3;

    public static final String CREDIT_TYPE = "credit_type";
    public static final String SPEED_CREDIT_ORDER_ID = "order_id";
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
    private int minWeek;
    private CreditSpeedEntity creditSpeedEntity;
    private String creditMoney;
    private String creditMonth;
    private ArrayList<CreditSpeedEntity.PropertyTypesBean> propertyTypeList;
    private ArrayList<CreditSpeedEntity.JobIdentitysBean> jobIdentitysList;
    //    private List<String> jobIdentitysList = new ArrayList<>();
//    private List<String> propertyTypeList;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeed_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {

//        propertyTypeList = (List<String>) getIntent().getSerializableExtra(SpeedCreditFrag.PROPERTY_TYPES);
//        jobIdentitysList = (List<String>) getIntent().getSerializableExtra(SpeedCreditFrag.JOB_IDENTITYS);
        propertyTypeList = (ArrayList<CreditSpeedEntity.PropertyTypesBean>) getIntent().getSerializableExtra(SpeedCreditFrag.PROPERTY_TYPES);
        jobIdentitysList = (ArrayList<CreditSpeedEntity.JobIdentitysBean>) getIntent().getSerializableExtra(SpeedCreditFrag.JOB_IDENTITYS);
        creditSpeedEntity = getIntent().getParcelableExtra(SpeedCreditFrag.CREDIT_SPEED);
        if (creditSpeedEntity != null) {
            setTopTitle(creditSpeedEntity.getProductName());
            if (!TextUtils.isEmpty(creditSpeedEntity.getMinVal())) {
                etSpeedAmount.setText(creditSpeedEntity.getMinVal());
            }

            if (!TextUtils.isEmpty(creditSpeedEntity.getRepaymentPeriod())) {
                String timeMinValue = creditSpeedEntity.getRepaymentPeriod().substring(0, 2);
                minWeek = Integer.valueOf(timeMinValue);
                tvSpeedWeek.setText(timeMinValue);
            }

            double v = Double.valueOf(etSpeedAmount.getText().toString().trim()) * (Integer.valueOf(tvSpeedWeek.getText().toString()) * 7) * 0.003;
            tvTotal.setText(String.valueOf(v));
            if (!TextUtils.isEmpty(creditSpeedEntity.getImageLogoPath())) {
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + creditSpeedEntity.getImageLogoPath(), ivBank, options);
            }
            double minVal = Double.valueOf(creditSpeedEntity.getMinVal());
            double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());

            tvScope.setText("额度范围：" + df.format(minVal / 10000) + "-" + df.format(maxVal / 10000) + "万");
            tvProName.setText(creditSpeedEntity.getProductName());

            tvLim.setText("期限范围：" + creditSpeedEntity.getRepaymentPeriod());
            if (!TextUtils.isEmpty(creditSpeedEntity.getPayType())) {
                tvPayType.setText("还款方式：" + creditSpeedEntity.getPayType());
            }
            if (!TextUtils.isEmpty(creditSpeedEntity.getLoanPeriod())) {
                tvLoan.setText(creditSpeedEntity.getLoanPeriod() + "个工作日");
            }
            if (!TextUtils.isEmpty(creditSpeedEntity.getMinRate()) || !TextUtils.isEmpty(creditSpeedEntity.getMaxRate())) {
                if (Double.valueOf(creditSpeedEntity.getMaxRate()) - Double.valueOf(creditSpeedEntity.getMinRate()) == 0) {
                    tvRate.setText(creditSpeedEntity.getMinRate());
                } else {
                    tvRate.setText( creditSpeedEntity.getMinRate() + "%-" + creditSpeedEntity.getMaxRate());
                }
            }


            if (!TextUtils.isEmpty(creditSpeedEntity.getApplyCondition())) {
                tvCon.setText(creditSpeedEntity.getApplyCondition());
            }
            if (!TextUtils.isEmpty(creditSpeedEntity.getApplyMaterial())) {
                tvNeed.setText(creditSpeedEntity.getApplyMaterial());
            }
            if (!TextUtils.isEmpty(creditSpeedEntity.getDetailDescribe())) {
                tvDetail.setText(creditSpeedEntity.getDetailDescribe());
            }
            if (!TextUtils.isEmpty(creditSpeedEntity.getMortgageType())) {
                String repaymentPeriod = creditSpeedEntity.getMortgageType();
                String[] split = repaymentPeriod.split("[,]");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
//                    s=15周；
                    if (s.contains("周")) {
                        mList.add(s);
                    } else {
                        mList.add(s + "周");
                    }

                }
            }

        }
        initAnim();
        setLeftTv("返回");
        map.put(0, false);
        map.put(1, false);
        map.put(2, false);

//        queryLoanUse();

        etSpeedAmount.addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            etSpeedAmount.setCursorVisible(true);
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
        double v = curVal * Integer.valueOf(creditMonth) * 7 * 0.3 / 100;
        tvTotal.setText(String.valueOf(v));

//        }
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
                    SubmitCreditSpeedEntity submitCreditSpeedEntity = new SubmitCreditSpeedEntity();

                    submitCreditSpeedEntity.setPurpose(tvPurpose.getText().toString());
                    submitCreditSpeedEntity.setMaxRepaymentWeekly(etSpeedBearing.getText().toString());
                    submitCreditSpeedEntity.setTotalAmount(etSpeedAmount.getText().toString().trim());
                    submitCreditSpeedEntity.setTotalPeriods(tvSpeedWeek.getText().toString());
                    submitCreditSpeedEntity.setTotalLoanInterest(tvTotal.getText().toString());
                    submitCreditSpeedEntity.setRealMonthlyRate(tvRate.getText().toString());
                    submitOreder(creditSpeedEntity.getProductId(),submitCreditSpeedEntity);
                }



                break;

            case R.id.rl_purpose:

                MySelfSheetDialog mySelfSheetDialog = new MySelfSheetDialog(CreditSpeedDetailAct.this).builder();
                if (jobIdentitysList.size() != 0) {
                    for (int i = 0; i < jobIdentitysList.size(); i++) {
                        mySelfSheetDialog.addSheetItem(jobIdentitysList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvPurpose.setText(jobIdentitysList.get(which - 1).getName());
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

    private void submitOreder(String productId,SubmitCreditSpeedEntity submitEntity) {
        RequestManager.getCommManager().submitSpeedOrder(productId,submitEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                String orderId = jsonObject.getString("data");
                Bundle bundle = new Bundle();
                bundle.putSerializable(SpeedCreditFrag.PROPERTY_TYPES,(Serializable) propertyTypeList);
                bundle.putString(SPEED_CREDIT_ORDER_ID,orderId);
                if (TextUtils.isEmpty(SpUtils.getRoleName(CreditSpeedDetailAct.this))) {
                    gotoActivity(CreditSpeedFirstAct.class, false);
                } else {
                    gotoActivity(CreditSpeedSecond_1Act.class, false,bundle);
                }

            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(CreditSpeedDetailAct.this,msg);
            }
        });
    }
}
