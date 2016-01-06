package com.beyonditsm.financial.fragment;

import android.animation.ObjectAnimator;
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
import com.beyonditsm.financial.activity.user.DoTaskPicture;
import com.beyonditsm.financial.activity.user.DoTaskPlaceAct;
import com.beyonditsm.financial.activity.user.FinishTaskPicture;
import com.beyonditsm.financial.activity.user.FinishTaskPlaceAct;
import com.beyonditsm.financial.activity.user.TaskDetail;
import com.beyonditsm.financial.activity.user.TaskLevelAct;
import com.beyonditsm.financial.activity.user.TiJiaoFuJianAct;
import com.beyonditsm.financial.adapter.PrimaryTaskAdapter;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.OrderDetailInfo;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.entity.TaskStrategyEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.Arith;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.leaf.library.widget.MyListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
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
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2015/12/13.
 */
public class MyCreditDetailFragment extends BaseFragment {
    public static final String CREDIT_DETAIL = "credit_detail";
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private ImageView ivBank;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvTotal;
    private TextView tvLimit;
    private TextView tvTime;
    private TextView tvLim;
    private TextView tvYueG;
    private TextView tvHf;
    private TextView tvT;
    private TextView tvFTime;
    private TextView tvRate;
    private TextView tvL;
    private TextView startMsg;
    private LinearLayout llzl;//用户基本资料
    private LinearLayout llzz;//用户资质
    @ViewInject(R.id.llts)
    private LinearLayout llts;//贷款提速

    @ViewInject(R.id.tv_tochat)
    private TextView tv_tochat;//姓名
    @ViewInject(R.id.tvSex)
    private TextView tvSex;//性别
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

    @ViewInject(R.id.tv_zy)
    private TextView tv_zy;//职业身份
    @ViewInject(R.id.tv_sb)
    private TextView tv_sb;//是否有社保
    @ViewInject(R.id.gjj_data)
    private TextView gjj_data;//是否有本地公积金
    @ViewInject(R.id.house_data)
    private TextView house_data;//名下房产类型
    @ViewInject(R.id.car_data)
    private TextView car_data;//小轿车
    @ViewInject(R.id.xy_data)
    private TextView xy_data;//信用状况
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

    @ViewInject(R.id.start_bj)
    private RelativeLayout rlbj;//补件

    private MyCreditBean.RowsEntity rowe;

    private Map<Integer, Boolean> map = new HashMap<>();
    private String accountId;
    private String creditName;

    OrderDetailInfo.DataEntity data;

    private ObjectAnimator obaDown;
    private ObjectAnimator obaOn;
    private ObjectAnimator obaDownzl, obaDownzz, obaDownts;
    private ObjectAnimator obaOnzl, obaOnzz, obaOnts;

    private FinalLoadDialog dialog;

    private AddressUtil addressUtil;
    private TextView tvRemarks;//备注
    private TextView tvCreditAmount;//sh
    private LinearLayout llCreditRemark;

    @Override
    public View initView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_mycredit_detail, null);
        ivBank = (ImageView) view.findViewById(R.id.ivBank);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvLimit = (TextView) view.findViewById(R.id.tvLimit);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvLim = (TextView) view.findViewById(R.id.tvLim);
        tvYueG = (TextView) view.findViewById(R.id.tvYueG);
        tvHf = (TextView) view.findViewById(R.id.tvHf);
        tvT = (TextView) view.findViewById(R.id.tvT);
        tvFTime = (TextView) view.findViewById(R.id.tvFTime);
        tvRate = (TextView) view.findViewById(R.id.tvRate);
        startMsg = (TextView) view.findViewById(R.id.start_msg);
        llzl = (LinearLayout) view.findViewById(R.id.llzl);
        llzz = (LinearLayout) view.findViewById(R.id.llzz);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvRemarks = (TextView) view.findViewById(R.id.tv_remarks);
        tvCreditAmount = (TextView) view.findViewById(R.id.tv_credit_amount);
        llCreditRemark = (LinearLayout) view.findViewById(R.id.ll_credit_remark);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        addressUtil=new AddressUtil(getActivity());
        rowe = getArguments().getParcelable("rowe");
        tvName.setText(rowe.getProductName());
        dialog = new FinalLoadDialog(getActivity());
        getOrderDetail(rowe.getId());

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
                if (taskEntityList.get(position).getTaskStatus() == -1) {
                    findTaskStrategy(taskEntityList.get(position), position);
                } else if ((taskEntityList.get(position).getTaskStatus() == 0) || (taskEntityList.get(position).getTaskStatus() == 1)) {
                    findTaskDetail(taskEntityList.get(position), position);

                }
            }
        });
    }

    @OnClick({R.id.rlzl, R.id.rlzz, R.id.rlts, R.id.bj})
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
            case R.id.bj://提交其它附件
                Intent intent = new Intent();
                intent.setClass(getActivity(), TiJiaoFuJianAct.class);
                intent.putExtra("orderNo", rowe.getOrderNo());
                intent.putExtra("orderId",rowe.getId());
                getActivity().startActivity(intent);

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
     * @param orderId
     */
    private void getOrderDetail(String orderId) {
        RequestManager.getCommManager().checkMyOrderDetail(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                OrderDetailInfo info = GsonUtils.json2Bean(result, OrderDetailInfo.class);
                if (info != null) {
                    data = info.getData();
                    if (data != null) {
                        creditName = data.getProductName();
                        accountId = data.getCreditAccountId();
                        if (!TextUtils.isEmpty(accountId))
                            start_rl.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + data.getImageLogoPath(), ivBank, options);
                        tvTotal.setText("¥" + df.format(data.getTotalAmount() / 10000) + "万");
                        tvLimit.setText("额度范围：" + df.format(Double.valueOf(data.getMinVal()) / 10000) + "~" + df.format(Double.valueOf(data.getMaxVal()) / 10000) + "万");
                        tvL.setText("期限范围：" + data.getTimeMinVal() + "~" + data.getTimeMaxVal() + "月");
                        tvTime.setText(data.getTotalPeriods() + "");
                        tvYueG.setText("¥" + data.getPeriodsAmount());
                        tvHf.setText("还款方式：" + data.getPayTypeName());
//                        tvT.setText("¥" + data.getTotalAmount());

                        Double totalMPay = Arith.sub(Double.valueOf(data.getPeriodsAmount()) * data.getTotalPeriods(), Double.valueOf(data.getTotalAmount()));
                        tvT.setText("¥" + df.format(totalMPay));
                        tvFTime.setText(data.getLoanPeriod() + "个工作日");
                        if (Double.valueOf(data.getMonthlyRateMin()) - Double.valueOf(data.getMonthlyRateMax()) == 0) {
                            tvRate.setText("利率:" + data.getMonthlyRateMin() + "%");
                        } else {
                            tvRate.setText("利率：" + data.getMonthlyRateMin() + "%" + "~" + data.getMonthlyRateMax() + "%");
                        }
                        if (Double.valueOf(data.getDisposableRateMax())-Double.valueOf(data.getDisposableRateMin())==0){
                            onpay.setText("一次性收费：" + data.getDisposableRateMin() + "%");
                        }else {
                            onpay.setText("一次性收费：" + data.getDisposableRateMin() + "%" + "~" + data.getDisposableRateMax() + "%");
                        }
                        if (!TextUtils.isEmpty(data.getUserName()))
                            tv_tochat.setText(data.getUserName());
                        if (data.getUserSex() == 1) {
                            tvSex.setText("男");
                        } else {
                            tvSex.setText("女");
                        }
                        if (!TextUtils.isEmpty(data.getIdentCard())) {
                            IdCard.setText(data.getIdentCard());
                        }

                        if (!TextUtils.isEmpty(data.getProvince())&&!TextUtils.isEmpty(data.getCity())
                                &&!TextUtils.isEmpty(data.getDistrict())) {
                            alwaysaddress.setText(addressUtil.getProName(data.getProvince())+
                            addressUtil.getCityName(data.getProvince(),data.getCity())+
                            addressUtil.getCountryName(data.getCity(),data.getDistrict()));
                        }
                        if (!TextUtils.isEmpty(data.getDetailAddress())) {
                            address.setText(data.getDetailAddress());
                        }

                        if ("1".equals(data.getMarrStatus())) {
                            tv_wyl.setText("已婚");
                        } else {
                            tv_wyl.setText("未婚");
                        }

                        if (!TextUtils.isEmpty(data.getNativePlace())) {
                            tv_jgl.setText(data.getNativePlace());
                        }

                        if (!TextUtils.isEmpty(data.getNativePlaceAddr())) {
                            user_data.setText(data.getNativePlaceAddr());
                        }
                        if (!TextUtils.isEmpty(data.getJobName())) {
                            tv_zy.setText(data.getJobName());
                        }
                        if (data.getSecailSecurity() == 1) {
                            tv_sb.setText("有");
                        } else {
                            tv_sb.setText("无");
                        }

                        if (data.getProFund() == 1) {
                            gjj_data.setText("有");
                        } else {
                            gjj_data.setText("无");
                        }
                        if (!TextUtils.isEmpty(data.getHaveHours())) {
                            house_data.setText(data.getHaveHours());
                        }

                        if (!TextUtils.isEmpty(data.getCarStatusName())) {
                            car_data.setText(data.getCarStatusName());
                        }
                        //    findTaskBytaskIds(data.getTaskManageId());
                        //如果taskManageId为空则不显示贷款提速
                        if (TextUtils.isEmpty(data.getTaskManageId())) {
                            llts.setVisibility(View.GONE);
                            rlts.setVisibility(View.GONE);
                            mlv.setVisibility(View.GONE);
                        } else {
                            llts.setVisibility(View.VISIBLE);
                            rlts.setVisibility(View.VISIBLE);
                            mlv.setVisibility(View.GONE);
                            findTaskBytaskIds(data.getTaskManageId());
                        }

                        if (!TextUtils.isEmpty(data.getRemark())) {
                            EventBus.getDefault().post(new PatchEvent(data.getRemark(), 1));
                        }

                        String status = data.getOrderSts();

                        if ("CREDIT_MANAGER_APPROVAL".equals(status) ||
                                "CREDIT_MANAGER_GRAB".equals(status) || "ORGANIZATION_APPROVAL".equals(status)) {
                            tvStatus.setText("审批中");
                        } else if ("PASS".equals(status)) {
                            tvStatus.setText("审批通过");
                            llCreditRemark.setVisibility(View.VISIBLE);
                            if (!TextUtils.isEmpty(data.getPracticalLoan()))
                                tvTotal.setText("¥" + df.format(Double.valueOf(data.getPracticalLoan()) / 10000) + "万");
                            if (!TextUtils.isEmpty(data.getRealMonthlyInterestRate()))
                                tvRate.setText("利率：" + data.getRealMonthlyInterestRate() + "%");
                            if (!TextUtils.isEmpty(data.getRealOneTimeRate()))
                                onpay.setText("一次性收费：" + data.getRealOneTimeRate() + "%");
                            if (!TextUtils.isEmpty(data.getCreditAmount())){
                                tvCreditAmount.setText("¥"+df.format(Double.valueOf(data.getCreditAmount())/10000)+"万");
                            }
                        } else if ("WAIT_BACKGROUND_APPROVAL".equals(status)
                                ) {
                            tvStatus.setText("待审批");
                        } else if ("SUPPLEMENT_DATA".equals(status)) {
                            tvStatus.setText("补件中");
                            rlbj.setVisibility(View.VISIBLE);
                        } else if ("NO_PASS".equals(status)) {
                            tvStatus.setText("审批不通过");
                        }else if ("CANCEL_REQUET".equals(status)){
                            tvStatus.setText("已取消");
                        }

                        if (!"SUPPLEMENT_DATA".equals(status)) {
                            EventBus.getDefault().post(new PatchEvent("", 2));
                            rlbj.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getContext(), msg);
            }
        });
    }

    public static final String UPDATE_ORDER = "com.update.order";
    private OrderBroadCastReceiver order_receiver;

    private class OrderBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getOrderDetail(rowe.getId());
        }
    }

    private List<TaskEntity> taskEntityList, finishList;//任务列表
    private List<TaskStrategyEntity> taskStrategyEntityList;//任务策略列表
    private PrimaryTaskAdapter adapter;

    /**
     * 根据任务id查询任务列表内容
     *
     * @param taskId
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
                if (RongIM.getInstance() != null) {
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
        Intent intent = null;
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
     * 跳转到对应的做任务的界面
     *
     * @param listTask 任务列表
     * @param list     任务策略列表
     * @param position 点击的位置
     */
    private void selectToAct(List<TaskEntity> listTask, List<TaskStrategyEntity> list, int position) {
        Intent intent = null;
        intent = new Intent();
        if (list.size() != 0) {
            //根据任务策略的第一条数据中任务组件跳转
            if (list.get(0).getModelType() == 5) {//上传
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskPicture只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.setClass(getActivity(), DoTaskPicture.class);


            } else if (list.get(0).getModelType() == 1) {//输入
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskPlaceAct只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.setClass(getActivity(), DoTaskPlaceAct.class);

            } else if (list.get(0).getModelType() == 3) {//单选
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(getActivity(), TaskDetail.class);

            } else if (list.get(0).getModelType() == 2) {//下拉
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(getActivity(), TaskDetail.class);

            } else if (list.get(0).getModelType() == 4) {//多选
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(getActivity(), TaskDetail.class);

            }
            startActivity(intent);
        }
    }

    /**
     * 根据任务查询任务详情（审核中，已完成）
     *
     * @param taskEntity
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

    /**
     * 根据任务查询任务策略
     *
     * @param taskEntity
     */
    private void findTaskStrategy(TaskEntity taskEntity, final int position) {
        dialog.show();
        RequestManager.getUserManager().findProTaskStrategy(taskEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                dialog.cancel();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                taskStrategyEntityList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskStrategyEntity>>() {
                }.getType());

                selectToAct(taskEntityList, taskStrategyEntityList, position);

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
            order_receiver = new OrderBroadCastReceiver();
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

    private MyBroadCastReceiver receiver;

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            findTaskBytaskIds(data.getTaskManageId());
        }
    }

}
