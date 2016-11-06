package com.beyonditsm.financial.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.credit.MyCreditDAct;
import com.beyonditsm.financial.activity.credit.SubFlowAct;
import com.beyonditsm.financial.activity.speedcredit.CreditSpeedSecond_2Act;
import com.beyonditsm.financial.activity.speedcredit.CreditSpeedSecond_3Act;
import com.beyonditsm.financial.activity.speedcredit.creditspeedthied.CreditSpeedThird_2Act;
import com.beyonditsm.financial.activity.user.FinishTaskPicture;
import com.beyonditsm.financial.activity.user.FinishTaskPlaceAct;
import com.beyonditsm.financial.activity.user.TaskLevelAct;
import com.beyonditsm.financial.activity.user.TiJiaoFuJianAct;
import com.beyonditsm.financial.adapter.PrimaryTaskAdapter;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.OrderDetailInfo;
import com.beyonditsm.financial.entity.SpeedOrderInfo;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.Arith;
import com.beyonditsm.financial.util.CheckUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.leaf.library.widget.MyListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.animation.ObjectAnimator;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by xuleyuan on 2016/11/4.
 */

public class SpeedCreditDetailFragment extends BaseFragment {

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");//保留小数
    java.text.DecimalFormat df2 = new java.text.DecimalFormat("#0.00");//保留小数


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private ImageView ivBank;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvTotal;
    private TextView tvLimit;
    private TextView tvTime;
    private TextView tvYueG;//总利息
    private TextView tvHf; //还款方式
    private TextView tvT;
    private TextView tvFTime;
    private TextView tvRate;
    private TextView tvL;
    private LinearLayout llzl;//用户基本资料
    private LinearLayout llzz;//用户资质
    @ViewInject(R.id.llts)
    private LinearLayout llts;//贷款提速

    @ViewInject(R.id.tv_tochat)
    private TextView tv_tochat;//姓名
    @ViewInject(R.id.tvSex)
    private TextView tvSex;//性别
    @ViewInject(R.id.tv_phone)
    private TextView tvPhone; //联系电话
    @ViewInject(R.id.tv_collage)
    private TextView tvCollage; //学历
    @ViewInject(R.id.IdCard)
    private TextView IdCard;//身份证号
    @ViewInject(R.id.alwaysaddress)
    private TextView alwaysaddress;//常住地
    @ViewInject(R.id.address)
    private TextView address;//详细地址
    @ViewInject(R.id.tv_wyl)
    private TextView tv_wyl;//婚姻状况
    @ViewInject(R.id.tv_jgl)
    private TextView tv_jgl;//籍贯
    @ViewInject(R.id.user_data)
    private TextView user_data;//户籍地址

//    @ViewInject(R.id.)

    @ViewInject(R.id.tv_zy)
    private TextView tv_zy;//单位名称

    @ViewInject(R.id.tv_companyPhone)
    private TextView tv_companyPhone;//单位电话
    @ViewInject(R.id.tv_sb)
    private TextView tv_sb;//单位地址
    @ViewInject(R.id.tv_company_property)
    private TextView tv_company_property;//单位性质
    @ViewInject(R.id.gjj_data)
    private TextView gjj_data;//是否有本地公积金
    @ViewInject(R.id.house_data)
    private TextView house_data;//名下房产类型
    @ViewInject(R.id.car_data)
    private TextView car_data;//小轿车
    @ViewInject(R.id.mlv)
    private MyListView mlv;
    @ViewInject(R.id.ivzz)
    private ImageView ivzz;//用户资质
    @ViewInject(R.id.ivzl)
    private ImageView ivzl;//用户基本资料
    @ViewInject(R.id.ivts)
    private ImageView ivts;//贷款提速
    @ViewInject(R.id.sv)
    private ScrollView sv;
    @ViewInject(R.id.rlts)
    private RelativeLayout rlts;//提速
    @ViewInject(R.id.start_rl)
    private RelativeLayout start_rl;//联系信贷经理点击框
    @ViewInject(R.id.onpay)
    private TextView onpay;
    @ViewInject(R.id.total)
    private TextView total;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.start_bj)
    private RelativeLayout rlbj;//补件
    @ViewInject(R.id.ll_creditDetail)
    private LinearLayout llCreditDetail;
    @ViewInject(R.id.rl_relativeInfo) //关系人的绝对布局
    private RelativeLayout rlRelativeInfo;
    @ViewInject(R.id.iv_relativeInfo)
    private ImageView ivRelativeInfo; //关系人的图标
    @ViewInject(R.id.ll_relativeInfo)
    private LinearLayout llRelativeInfo; //关系人的线性布局
    @ViewInject(R.id.tv_family1_name)
    private TextView tvFamily1Name; //直系亲属1姓名
    @ViewInject(R.id.tv_family1_relation)
    private TextView tvFamily1Relation; //直系亲属1关系
    @ViewInject(R.id.tv_family1_phone)
    private TextView tvFamily1Phone; //直系亲属1电话

    @ViewInject(R.id.tv_family2_name)
    private TextView tvFamily2Name; //直系亲属2姓名
    @ViewInject(R.id.tv_family2_relation)
    private TextView tvFamily2Relation; //直系亲属2关系
    @ViewInject(R.id.tv_family2_phone)
    private TextView tvFamily2Phone; //直系亲属2电话

    @ViewInject(R.id.tv_colleagueName)
    private TextView tvColleagueName; //同事姓名
    @ViewInject(R.id.tv_colleagueRelation)
    private TextView tvColleagueRelation; //同事关系关系
    @ViewInject(R.id.tv_colleaguePhone)
    private TextView tvColleaguePhone; //同事电话

    @ViewInject(R.id.tv_ecName)
    private TextView tvEcName; //紧急联系人姓名
    @ViewInject(R.id.tv_ecRelation)
    private TextView tvEcRelation; //紧急联系人关系关系
    @ViewInject(R.id.tv_ecPhone)
    private TextView tvEcPhone; //紧急联系人电话
    @ViewInject(R.id.tv_md_city)
    private TextView mdCity;
    @ViewInject(R.id.tv_md_address)
    private TextView mdAddress;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.front)
    private ImageView icardFront;
    @ViewInject(R.id.back)
    private ImageView icardBack;
    @ViewInject(R.id.rlUpCredit)
    private RelativeLayout rlUpCredit;
    @ViewInject(R.id.tv_work_property)
    private TextView tvWorkProperty;
    private MyCreditBean.RowsEntity rowe;


    private String STEP = "";
    private Map<Integer, Boolean> map = new HashMap<>();
    private String accountId;
    private String creditName;
    public static String monthlyPayments;//月供
    private String totalRath;

    OrderDetailInfo.DataEntity data;
    SpeedOrderInfo info;
    private ObjectAnimator obaDownzl, obaDownzz, obaDownts;
    private ObjectAnimator obaOnzl, obaOnzz, obaOnts;

    private FinalLoadDialog dialog;

    private AddressUtil addressUtil;
    private TextView tvCreditAmount;//sh
    private LinearLayout llCreditRemark;

    @Override
    public View initView(LayoutInflater inflater) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_speed_credit_detail, null);
        ivBank = (ImageView) view.findViewById(R.id.ivBank);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvLimit = (TextView) view.findViewById(R.id.tvLimit);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvYueG = (TextView) view.findViewById(R.id.tvYueG);
        tvHf = (TextView) view.findViewById(R.id.tvHf);
        tvT = (TextView) view.findViewById(R.id.tvT);
        tvFTime = (TextView) view.findViewById(R.id.tvFTime);
        tvRate = (TextView) view.findViewById(R.id.tvRate);
        llzl = (LinearLayout) view.findViewById(R.id.llzl);
        llzz = (LinearLayout) view.findViewById(R.id.llzz);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvCreditAmount = (TextView) view.findViewById(R.id.tv_credit_amount);
        llCreditRemark = (LinearLayout) view.findViewById(R.id.ll_credit_remark);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        addressUtil = new AddressUtil(getActivity());
        rowe = getArguments().getParcelable("rowe");
        if (rowe != null) {
            tvName.setText(rowe.getProductName());
        }
        dialog = new FinalLoadDialog(getActivity());
        ShortLoanOrderDetail(rowe.getId());

//        obaDown = ObjectAnimator.ofFloat(ivSlide, "rotation", 0,
//                180);
//        obaDown.setDuration(300);
//        obaOn = ObjectAnimator.ofFloat(ivSlide, "rotation",-180,
//                0);
//        obaOn.setDuration(300);

        obaDownzl = ObjectAnimator.ofFloat(ivzl, "rotation", 0,
                180);
        obaDownzl.setDuration(300);
        obaOnzl = ObjectAnimator.ofFloat(ivzl, "rotation", -180,
                0);
        obaOnzl.setDuration(300);
        obaDownzz = ObjectAnimator.ofFloat(ivzz, "rotation", 0,
                180);
        obaDownzz.setDuration(300);
        obaOnzz = ObjectAnimator.ofFloat(ivzz, "rotation", -180,
                0);
        obaOnzz.setDuration(300);
        obaDownts = ObjectAnimator.ofFloat(ivts, "rotation", 0,
                180);
        obaDownts.setDuration(300);
        obaOnts = ObjectAnimator.ofFloat(ivts, "rotation", -180,
                0);
        obaOnts.setDuration(300);

        map.put(0, false);
        map.put(1, false);
        map.put(2, false);

        llzl.setVisibility(View.GONE);
        llzz.setVisibility(View.GONE);
        llts.setVisibility(View.GONE);
    }

    @Override
    public void setListener() {
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (taskEntityList.get(position).getTaskStatus() != -1) {
                    if ((taskEntityList.get(position).getTaskStatus() == 0) || (taskEntityList.get(position).getTaskStatus() == 1)) {
                        findTaskDetail(taskEntityList.get(position), position);

                    }
                }
            }
        });
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                ShortLoanOrderDetail(rowe.getId());
            }
        });
    }

    @OnClick({R.id.rlzl, R.id.rlzz, R.id.rlts, R.id.bj, R.id.tvUpload, R.id.tvUpCredit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlzl://用户基本资料
//                if (!map.get(0)) {
//                    obaDown.start();
//                    zz_ll.setVisibility(View.VISIBLE);
//                    map.put(0, true);
//                    scrollDown();
//                } else {
//                    obaOn.start();
//                    zz_ll.setVisibility(View.GONE);
//                    map.put(1, false);
//                }
                if (!map.get(0)) {
                    obaDownzl.start();
                    llzl.setVisibility(View.VISIBLE);
                    map.put(0, true);
                    scrollDown();
                } else {
                    obaOnzl.start();
                    llzl.setVisibility(View.GONE);
                    map.put(0, false);
                }
                break;
            case R.id.rlzz://用户资质
                if (!map.get(1)) {
                    obaDownzz.start();
                    llzz.setVisibility(View.VISIBLE);
                    map.put(1, true);
                    scrollDown();
                } else {
                    obaOnzz.start();
                    llzz.setVisibility(View.GONE);
                    map.put(1, false);
                }
                break;
            case R.id.rlts://贷款提速
                if (!map.get(2)) {
                    obaDownts.start();
                    llts.setVisibility(View.VISIBLE);
                    mlv.setVisibility(View.VISIBLE);
                    map.put(2, true);
                    scrollDown();
                } else {
                    obaOnts.start();
                    llts.setVisibility(View.VISIBLE);
                    mlv.setVisibility(View.GONE);
                    map.put(2, false);
                }
                break;
            case R.id.bj://提交其它附件(补件)
                Intent intent = new Intent();
                intent.setClass(getActivity(), TiJiaoFuJianAct.class);
                intent.putExtra("orderNo", rowe.getOrderNo());
                intent.putExtra("orderId", rowe.getId());
                getActivity().startActivity(intent);

                break;
            case R.id.tvUpload://上传
//                SpUtils.clearOrderId(MyApplication.getInstance());
//                getActivity().sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
//                getActivity().sendBroadcast(new Intent(MyCreditDAct.HIDE_RED));
//                getActivity().sendBroadcast(new Intent(MyCreditAct.HIDE_MESSAGE));
//                getActivity().sendBroadcast(new Intent(MineFragment.HIDE_POINT));
                Intent intent2 = new Intent(getContext(), CreditStepAct.class);
                intent2.putExtra("credit_upload", 1);
                intent2.putExtra("orderId", rowe.getId());
                intent2.putExtra("orderStatus", data.getOrderSts());
                intent2.putExtra("orderType", rowe.getOrderType());
                getActivity().startActivity(intent2);
                break;
            case R.id.tvUpCredit://增信上传
                switch (STEP) {
                    case "1":
                        Intent intent3 = new Intent(getContext(), CreditSpeedSecond_2Act.class);
                        intent3.putExtra("order_id", rowe.getId());
                        getActivity().startActivity(intent3);
                        break;
                    case "2":
                        Intent intent4 = new Intent(getContext(), CreditSpeedSecond_3Act.class);
                        intent4.putExtra("orderId", rowe.getId());
                        getActivity().startActivity(intent4);
                        break;
                    case "3":
                        Intent intent5 = new Intent(getContext(), CreditSpeedThird_2Act.class);
                        intent5.putExtra("orderId", rowe.getId());
                        getActivity().startActivity(intent5);
                        break;

                }

                break;
        }
    }

    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public class PatchEvent {
        public String _remark;
        public int _type;

        public PatchEvent(String remark, int type) {
            _remark = remark;
            _type = type;
        }
    }

    /**
     * 获取订单详情数据
     *
     * @param orderId 订单id
     */
    private void ShortLoanOrderDetail(String orderId) {
        CommManager.getCommManager().shortLoanOrderDetail(orderId, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                Gson gson = new Gson();
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                if (data.getJSONArray("payType") != null){
                    StringBuilder sb = new StringBuilder();
                    JSONArray payJSON = data.getJSONArray("payType");
                    for(int i =0;i<payJSON.length();i++){
                        JSONObject jsonObject1 = payJSON.getJSONObject(i);
                        sb.append(jsonObject1.getString("name"));
                        sb.append(" ,");
                    }
                    tvHf.setText("还款方式：" + sb.toString().substring(0,sb.length()-1));
                }

                info = gson.fromJson(data.toString(), SpeedOrderInfo.class);
                if (info != null) {
//                    data = info.getData();
                    if (data != null) {
                        creditName = info.getProductName();
//                        accountId = info.getCreditAccountId();
                        if (!TextUtils.isEmpty(accountId))
                            start_rl.setVisibility(View.VISIBLE);

                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + info.getLogoPath(), ivBank, options);
                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + info.getIdCardFront(), icardFront, options);
                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + info.getIdCardBack(), icardBack, options);
                        mdCity.setText(info.getStoreCity() + "");
                        mdAddress.setText(info.getStoreAddr() + "");
                        STEP = info.getStepCode()+"";
//
                        if (STEP.equals("1") || STEP.equals("2") || STEP.equals("3")){
                            MyCreditDAct act = (MyCreditDAct) getActivity();
                            act.setCancel();
                        }

                        if (TextUtils.isEmpty(info.getStepCode() + "") || (info.getStepCode()+"").equals("null") || (info.getStepCode()+"").equals("4") || (info.getOrderSts() + "").equals("审批不通过")) {
                            rlUpCredit.setVisibility(View.GONE);
                        } else {
                            rlUpCredit.setVisibility(View.VISIBLE);
                        }


                        total.setText("贷款金额：");
                        time.setText("贷款期限：");
                        if (!TextUtils.isEmpty(String.valueOf(info.getTotalAmount()))) {
                            tvTotal.setText(df2.format(info.getTotalAmount() / 10000) + "万");
                        }
                        if (!TextUtils.isEmpty(String.valueOf(info.getMinVal())) && !TextUtils.isEmpty(String.valueOf(info.getMaxVal()))) {
                            tvLimit.setText("额度范围：" + df.format(info.getMinVal() / 10000) + "~" + df.format(info.getMaxVal() / 10000) + "万");
                        }
                        if (!TextUtils.isEmpty(String.valueOf(info.getRepaymentPeriod()))) {
                            tvL.setText("期限范围：" + info.getRepaymentPeriod() + "");
                        }
                        if (!TextUtils.isEmpty(String.valueOf(info.getTotalPeriods()))) {
                            tvTime.setText(info.getTotalPeriods() + "");
                        }
                        if (!TextUtils.isEmpty(String.valueOf(info.getTotalLoanInterest()))) {
                            tvYueG.setText(info.getTotalLoanInterest() + " 元");
                        }

//                        tvT.setText("¥" + info.getTotalAmount());

                        if (!TextUtils.isEmpty(info.getWorkProp())) {
                            tvWorkProperty.setText(info.getWorkProp());
                        }

//                        Double totalMPay = Arith.sub(info.getPeriodsAmount() * info.getTotalPeriods(), info.getTotalAmount());
//                        tvT.setText(df2.format(totalMPay));
                        if (!TextUtils.isEmpty(String.valueOf(info.getLoanPeriod()))) {
                            tvFTime.setText(info.getLoanPeriod() + "个工作日");
                        }
                        if (!TextUtils.isEmpty(info.getRealMonthlyRate() + "")) {
//                            if (Double.valueOf(info.getMonthlyRateMin()) - Double.valueOf(info.getMonthlyRateMax()) == 0) {
                            tvT.setText(info.getRealMonthlyRate() + "%");
//                            }
//                            } else {
//                                tvRate.setText("综合费率：" + info.getRealMonthlyRate() + "%" + "~" + info.getMonthlyRateMax() + "%");
//                            }
                        }
//                        if (!TextUtils.isEmpty(info.getDisposableRateMax())&&!TextUtils.isEmpty(info.getDisposableRateMin())) {
//                            if (Double.valueOf(info.getDisposableRateMax()) - Double.valueOf(info.getDisposableRateMin()) == 0) {
//                                onpay.setText("一次性收费：" + info.getDisposableRateMin() + "%");
//                            } else {
//                                onpay.setText("一次性收费：" + info.getDisposableRateMin() + "%" + "~" + info.getDisposableRateMax() + "%");
//                            }
//                        }
                        if (!TextUtils.isEmpty(info.getName()))
                            tv_tochat.setText(info.getName());
//                        if (!TextUtils.isEmpty(info.getIdCardNo())) {
//
//                                tvSex.setText(info.getIdCardNo());
//
//                        }
                        if (!TextUtils.isEmpty(info.getIdCardNo())) {
                            IdCard.setText(info.getIdCardNo());
                        }

                        if (!TextUtils.isEmpty(info.getMobile())) {
                            tvPhone.setText(info.getMobile() + "");
                        }

                        if (!TextUtils.isEmpty(info.getQualifications())) {
                            tvCollage.setText(info.getQualifications() + "");
                        }

                        if (!TextUtils.isEmpty(info.getMarragests())) {
                            tv_wyl.setText(info.getMarragests() + "");
                        }


                        if (!TextUtils.isEmpty(info.getPermanentadds())) {
                            alwaysaddress.setText(info.getPermanentadds());
                        }
                        if (!TextUtils.isEmpty(info.getResSts())) {
                            address.setText(info.getResSts());
                        }

                        if (!TextUtils.isEmpty(info.getDomicileAdds())) {
                            user_data.setText(info.getDomicileAdds());
                        }

//                        if (!TextUtils.isEmpty(info.getNativePlaceAddr())) {
//                            user_info.setText(info.getNativePlaceAddr());
//                        }
                        if (!TextUtils.isEmpty(info.getCompanyName() + "")) {
                            tv_zy.setText(info.getCompanyName() + "");
                        }
                        if (!TextUtils.isEmpty(info.getCompanyPhone() + "")) {
                            tv_companyPhone.setText(info.getCompanyPhone() + "");
                        }

                        if (!TextUtils.isEmpty(info.getCompanyAdds() + "")) {
                            tv_sb.setText(info.getCompanyAdds() + "");
                        }
                        if (!TextUtils.isEmpty(info.getCompanyNature() + "")) {
                            tv_company_property.setText(info.getCompanyNature() + "");
                        }

                        if (!TextUtils.isEmpty(info.getSalary() + "")) {
                            gjj_data.setText(info.getSalary() + "");
                        }

                        if (!TextUtils.isEmpty(info.getRelatives1Name() + "")) {
                            tvFamily1Name.setText(info.getRelatives1Name() + "");
                        }

                        if (!TextUtils.isEmpty(info.getRelatives1Rs() + "")) {
                            tvFamily1Relation.setText(info.getRelatives1Rs() + "");
                        }

                        if (!TextUtils.isEmpty(info.getRelatives1Mobile() + "")) {
                            tvFamily1Phone.setText(info.getRelatives1Mobile() + "");
                        }


                        if (!TextUtils.isEmpty(info.getRelatives2Name() + "")) {
                            tvFamily2Name.setText(info.getRelatives2Name() + "");
                        }

                        if (!TextUtils.isEmpty(info.getRelatives2Rs() + "")) {
                            tvFamily2Relation.setText(info.getRelatives2Rs() + "");
                        }

                        if (!TextUtils.isEmpty(info.getRelatives2Mobile() + "")) {
                            tvFamily2Phone.setText(info.getRelatives2Mobile() + "");
                        }


                        if (!TextUtils.isEmpty(info.getColleagueNAME() + "")) {
                            tvColleagueName.setText(info.getColleagueNAME() + "");
                        }

                        if (!TextUtils.isEmpty(info.getColleagueRs() + "")) {
                            tvColleagueRelation.setText(info.getColleagueRs() + "");
                        }

                        if (!TextUtils.isEmpty(info.getColleagueMobile() + "")) {
                            tvColleaguePhone.setText(info.getColleagueMobile() + "");
                        }

                        if (!TextUtils.isEmpty(info.getEcName() + "")) {
                            tvEcName.setText(info.getEcName() + "");
                        }

                        if (!TextUtils.isEmpty(info.getEsRs() + "")) {
                            tvEcRelation.setText(info.getEsRs() + "");
                        }

                        if (!TextUtils.isEmpty(info.getEcMobile() + "")) {
                            tvEcPhone.setText(info.getEcMobile() + "");
                        }
                        //    findTaskBytaskIds(info.getTaskManageId());
                        //如果taskManageId为空则不显示贷款提速
//                        if (TextUtils.isEmpty(info.getTaskManageId())) {
//                            llts.setVisibility(View.GONE);
//                            rlts.setVisibility(View.GONE);
//                            mlv.setVisibility(View.GONE);
//                        } else {
//                            llts.setVisibility(View.VISIBLE);
//                            rlts.setVisibility(View.VISIBLE);
//                            mlv.setVisibility(View.GONE);
//                            findTaskBytaskIds(info.getTaskManageId());
//                        }

//                        if (!TextUtils.isEmpty(info.getRemark())) {
//                            EventBus.getDefault().post(new SpeedCreditDetailFragment().PatchEvent(info.getRemark(), 1));
//                        }

                        tvStatus.setText(info.getOrderSts() + "");
//                        if ("ORGANIZATION_APPROVAL".equals(status)) {//机构审批 机构正在进行审批
//                            tvStatus.setText("机构审批中");
//                        } else if ("CREDIT_MANAGER_GRAB".equals(status)) {//信贷经理抢单 当前节点信贷经理可以抢单
//                            tvStatus.setText("待抢单");
//                        } else if ("CREDIT_MANAGER_APPROVAL".equals(status)) {//信贷经理审批中 当前节点信贷经理已抢到单
//                            tvStatus.setText("已抢单");
//                        } else if ("PASS".equals(status)) {//审批通过 机构审批通过
//                            tvStatus.setText("审批通过");
//                            tvStatus.setBackgroundResource(R.drawable.cre_btn_green);
//                            llCreditRemark.setVisibility(View.GONE);
//                            total.setText("放款金额：");
//                            time.setText("还款期限：");
//                            if (!TextUtils.isEmpty(info.getTotalAmount() + ""))
//                                tvTotal.setText(df2.format(Double.valueOf(info.getTotalAmount() + "") / 10000) + "万");
//                            if (!TextUtils.isEmpty(String.valueOf(info.getMinVal())) && !TextUtils.isEmpty(String.valueOf(info.getMaxVal()))) {
//                                tvLimit.setText("额度范围：" + df.format(info.getMinVal() / 10000) + "~" + df.format(info.getMaxVal() / 10000) + "万");
//                            }
//                            if (!TextUtils.isEmpty(String.valueOf(info.getRepaymentPeriod()))) {
//                                tvL.setText("期限范围：" + info.getRepaymentPeriod() + "");
//                            }
////                            if (!TextUtils.isEmpty(data.getCreditAmount())) {
////                                tvCreditAmount.setText(df.format(Double.valueOf(data.getCreditAmount()) / 10000) + "万");
////                            }
////                            if (!TextUtils.isEmpty(data.getBankPracticalPeriods())) {
////                                tvTime.setText(data.getBankPracticalPeriods());
////                            }
////                            if (!TextUtils.isEmpty(data.getPracticalLoan()) && !TextUtils.isEmpty(data.getBankPracticalPeriods()) && !TextUtils.isEmpty(data.getRealMonthlyInterestRate())) {
////                                String loan = data.getPracticalLoan();
////                                String rate = data.getRealMonthlyInterestRate();
////                                String periods = data.getBankPracticalPeriods();
//////                                getMOnthPay(loan, rate, periods);
////                            }
//                            /**
//                             * 等待后台审批 用户提交订单后的状态， 当前节点由后台管理（我们平台的工作人员）进行第一轮审批，审批完成后信贷经理才可以抢单
//                             */
//                        } else if ("WAIT_BACKGROUND_APPROVAL".equals(status)) {
//                            tvStatus.setText("待审批");
//                        } else if ("SUPPLEMENT_DATA".equals(status)) {//补件中 由信贷经理或机构发起的补件 由信贷经理与客户联系，要求客户补件
//                            tvStatus.setText("补件中");
//                            rlbj.setVisibility(View.VISIBLE);
//                        } else if ("NO_PASS".equals(status)) {//不通过 机构审批不通过
//                            tvStatus.setText("审批不通过");
//                        } else if ("CANCEL_REQUET".equals(status)) {//取消申请 客户取消申请
//                            tvStatus.setText("已取消");
//                        } else if ("DRAFT".equals(status)) {//草稿
//                            tvStatus.setText("资料待上传");
//                        } else if ("REJECT".equals(status)) {
//                            tvStatus.setText("已驳回");
//                        }

//                        if (!"SUPPLEMENT_DATA".equals(status)) {
//                            EventBus.getDefault().post(new SpeedCreditDetailFragment().PatchEvent("", 2));
//                            rlbj.setVisibility(View.GONE);
//                        }
                        CheckUtil.CheckOutNullString(llCreditDetail);

                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getContext(), msg);
                loadingView.loadError();
            }
        });
    }

    /**
     * 计算月供
     *
     * @param repaymentMoney 贷款金额
     * @param rate           月利率
     * @param month          月份
     */
    private void getMOnthPay(String repaymentMoney, String rate, String month) {

        RequestManager.getUserManager().getMonthPay(Double.valueOf(repaymentMoney) + "",
                Double.valueOf(rate) / 100 + "", month, new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) throws JSONException {
                        JSONObject json = new JSONObject(result);
                        JSONObject jsonData = json.getJSONObject("data");
                        monthlyPayments = jsonData.getDouble("monthlyPayments") + "";
                        totalRath = jsonData.getDouble("totalRath") + "";
                        tvT.setText(totalRath);
                        tvYueG.setText(monthlyPayments);
                    }

                    @Override
                    public void onError(int status, String msg) {
                        MyToastUtils.showShortToast(getActivity(), msg);
                    }
                });
    }

    public static final String UPDATE_ORDER = "com.update.order";
    private SpeedCreditDetailFragment.OrderBroadCastReceiver order_receiver;

    private class OrderBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ShortLoanOrderDetail(rowe.getId());
        }
    }

    private List<TaskEntity> taskEntityList, finishList;//任务列表
    private PrimaryTaskAdapter adapter;

    /**
     * 根据任务id查询任务列表内容
     *
     * @param taskId 任务id
     */
    private void findTaskBytaskIds(String taskId) {
        RequestManager.getUserManager().findTaskBytaskIds(taskId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                taskEntityList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskEntity>>() {
                }.getType());

                if (taskEntityList.size() != 0 && adapter == null) {
                    adapter = new PrimaryTaskAdapter(taskEntityList, context);
                    mlv.setAdapter(adapter);
                } else {
                    if (taskEntityList.size() != 0) {
                        adapter.notifyChange(taskEntityList);
                    }
                }
                //    Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int status, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.start_msg})
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.start_msg:
                if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                    if (!TextUtils.isEmpty(accountId)) {
                        if (!TextUtils.isEmpty(creditName)) {
                            RongIM.getInstance().startPrivateChat(getContext(), accountId, creditName);
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(accountId, creditName, null));
                            FriendBean friendBean = new FriendBean();
                            friendBean.setUserName(creditName);
                            friendBean.setUserId(accountId);
                            FriendDao.saveMes(friendBean);
                        } else {
                            RongIM.getInstance().startPrivateChat(getContext(), accountId, "信贷经理");
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(accountId, "信贷经理", null));
                            FriendBean friendBean = new FriendBean();
                            friendBean.setUserName("信贷经理");
                            friendBean.setUserId(accountId);
                            FriendDao.saveMes(friendBean);
                        }
                    }
                }
                break;
        }
    }

    /**
     * 跳转到对应的已做过任务的activity
     *
     * @param list     任务列表
     * @param position 点击的位置
     */
    private void selectToFinishAct(List<TaskEntity> list, int position) {
        Intent intent;
        intent = new Intent();
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
        intent.putExtra("position", position);
        if (list.get(0).getValueType() == 0) {
            intent.setClass(getContext(), FinishTaskPlaceAct.class);
        } else if (list.get(0).getValueType() == 1) {
            intent.setClass(getContext(), FinishTaskPicture.class);
        }
        startActivity(intent);
    }

    /**
     * 根据任务查询任务详情（审核中，已完成）
     *
     * @param taskEntity 任务实体类
     */
    private void findTaskDetail(TaskEntity taskEntity, final int position) {
        dialog.show();
        RequestManager.getUserManager().findProTaskDetail(taskEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                dialog.cancel();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                finishList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskEntity>>() {
                }.getType());
                selectToFinishAct(finishList, position);
            }

            @Override
            public void onError(int status, String msg) {
                dialog.cancel();
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }
        if (order_receiver == null) {
            order_receiver = new SpeedCreditDetailFragment.OrderBroadCastReceiver();
        }
        getActivity().registerReceiver(order_receiver, new IntentFilter(UPDATE_ORDER));
        getActivity().registerReceiver(receiver, new IntentFilter(TaskLevelAct.UPDATE_LIST));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
        if (order_receiver != null) {
            getActivity().unregisterReceiver(order_receiver);
        }
    }

    private SpeedCreditDetailFragment.MyBroadCastReceiver receiver;

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            findTaskBytaskIds(data.getTaskManageId());
        }
    }
}
