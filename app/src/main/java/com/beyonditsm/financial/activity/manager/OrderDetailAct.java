package com.beyonditsm.financial.activity.manager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.user.FinishTaskPicture;
import com.beyonditsm.financial.activity.user.FinishTaskPlaceAct;
import com.beyonditsm.financial.activity.user.TaskLevelAct;
import com.beyonditsm.financial.adapter.PrimaryTaskAdapter;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.entity.OrderDetailInfo;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.fragment.ManagerOrderFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.Arith;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.leaf.library.widget.MyListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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

/**
 * 信贷经理订单详情界面
 * Created by Yang on 2015/11/17 0017.
 */
public class OrderDetailAct extends BaseActivity {
    @ViewInject(R.id.sv)
    private ScrollView sv;
    private TextView IdCard;//身份证号
    private TextView address;//详细地址
    @ViewInject(R.id.alwaysaddress)
    private TextView alwaysaddress;//常住地
    private Button commit;
    private Button bujian_btn;
    private ImageView ivBank;
    private TextView tvProName;//贷款名称
    @ViewInject(R.id.tvAmount)
    private TextView tvAmount;//贷款数目
    //    private TextView tvMount;
    private TextView tvScope;//额度范围
    private TextView tvLim;//期限范围
    private TextView tvMonthPay;//月供
    private TextView tvPaytype;//还款方式
    private TextView tvTotal;//总费用
    private TextView tvLoan;//放款时间
    private TextView tvRate;//利率
    private GrabOrderBean.RowsEntity data;
    @ViewInject(R.id.ivzl)
    private ImageView ivzl;//资料
    //用户资质
    @ViewInject(R.id.ivzz)
    private ImageView ivzz;//资质
    @ViewInject(R.id.tv_zy)
    private TextView tv_zy;//职业
    @ViewInject(R.id.tv_sb)
    private TextView tv_sb;//社保
    @ViewInject(R.id.gjj_data)
    private TextView gjj_data;//公积金
    @ViewInject(R.id.house_data)
    private TextView house_data;//房产
    @ViewInject(R.id.car_data)
    private TextView car_data;//车
    @ViewInject(R.id.tvMonth)
    private TextView tvMonth;//期限
    //贷款提速
    @ViewInject(R.id.rlts)
    private RelativeLayout rlts;
    @ViewInject(R.id.ivts)
    private ImageView ivts;//提速
    @ViewInject(R.id.mlv)
    private MyListView mlv;
    @ViewInject(R.id.llts)
    private LinearLayout llts;

    @ViewInject(R.id.llzl)
    private LinearLayout llzl;
    @ViewInject(R.id.llzz)
    private LinearLayout llzz;

    @ViewInject(R.id.tv_tochat)
    private TextView tv_tochat;//姓名
    @ViewInject(R.id.nicknmame)
    private TextView tvSex;//性别
    @ViewInject(R.id.tv_wyl)
    private TextView tv_wyl;//婚姻状况
    @ViewInject(R.id.tv_jgl)
    private TextView tv_jgl;//籍贯
    @ViewInject(R.id.user_data)
    private TextView user_data;//户籍地址
      @ViewInject(R.id.loadView)
    private LoadingView loadView;//加载view

    @ViewInject(R.id.onepay)
    private TextView tvOnePay;

    private String orderId;
    private String orderSts;
    private Map<Integer, Boolean> map = new HashMap<>();
    private ObjectAnimator obaDownzl, obaDownzz, obaDownts;
    private ObjectAnimator obaOnzl, obaOnzz, obaOnts;
    private String accountId;
    private List<TaskEntity> taskEntityList, finishList;//任务列表
    private PrimaryTaskAdapter adapter;

    OrderDetailInfo.DataEntity datas;
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数
    private String remark;
    private AddressUtil addressUtil;


    private void assignViews() {
        IdCard = (TextView) findViewById(R.id.IdCard);
        address = (TextView) findViewById(R.id.address);
        //    download = (Button) findViewById(R.id.download);
        bujian_btn = (Button) findViewById(R.id.bujian_btn);
        commit = (Button) findViewById(R.id.commit_btn);
        ivBank = (ImageView) findViewById(R.id.ivBank);
        tvProName = (TextView) findViewById(R.id.tvProName);
//        tvMount = (TextView) findViewById(R.id.tvMount);
        tvScope = (TextView) findViewById(R.id.tvScope);
        tvLim = (TextView) findViewById(R.id.tvLim);
        tvMonthPay = (TextView) findViewById(R.id.tvMonthPay);
        tvPaytype = (TextView) findViewById(R.id.tvPaytype);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvLoan = (TextView) findViewById(R.id.tvLoan);
        tvRate = (TextView) findViewById(R.id.tvRate);
    }

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @Override
    public void setLayout() {
        setContentView(R.layout.orderdetailact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        addressUtil=new AddressUtil(this);

        setTopTitle("产品详情");
        setLeftTv("返回");
        setRightBtn("查看附件", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailAct.this, GetPictureAct.class);
                intent.putExtra("account_id", datas.getAccountId());
                intent.putExtra("order_id", datas.getOrderNo());
                intent.putExtra("remark", remark);
                startActivity(intent);
            }
        });
        assignViews();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            data = bundle.getParcelable(ConstantValue.ORDER);
            if (data != null) {
                orderId = data.getId();
            }
            getOrderDetail(orderId);
//            fillData();
        }
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

        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (taskEntityList.get(position).getTaskStatus() == -1) {
                    Toast.makeText(getApplicationContext(), "用户未完成该任务，无法查看详情！", Toast.LENGTH_SHORT).show();
                    //    findTaskStrategy(taskEntityList.get(position), position);
                } else if ((taskEntityList.get(position).getTaskStatus() == 0) || (taskEntityList.get(position).getTaskStatus() == 1)) {
                    findTaskDetail(taskEntityList.get(position), position);

                }
            }
        });

        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getOrderDetail(orderId);
            }
        });
    }


    /**
     * 初始化数据
     */
//    private void fillData() {
//        tvTochat.setText(data.getUserName());//用户名
////        nicknmame.setText("昵称：");//昵称
//        if (data.getUserSex().equals("1")) {
//            tvSex.setText("男");
//        } else if(data.getUserSex().equals("0")){
//            tvSex.setText("女");
//        }
//        tvSex.setText(data.getUserSex());//性别
//        IdCard.setText(data.getIdentCard());//身份证号
//        alwaysaddress.setText(data.getNativePlaceAddr());//常住地
//        address.setText(data.getDetailAddr());//详细地址
//        //婚姻状况
//        if(Integer.parseInt(data.getMarrySts())==0){
//            tvWyl.setText("未婚");
//        }else  if(Integer.parseInt(data.getMarrySts())==1){
//            tvWyl.setText("已婚");
//        }
//        tvjgl.setText(data.getNativePlace());//籍贯
//        userData.setText(data.getNativePlaceAddr());//户籍地址
//        tv_zy.setText(data.getJobName());//职业
//        company.setText("");//公司名称
//        zw.setText("");//职务
//        age.setText(data.getUserAge());//年龄
//        if(Integer.parseInt(data.getSecailSecurity())==0) {
//            tv_sb.setText("无");//社保
//        }
//        else   if(Integer.parseInt(data.getSecailSecurity()) ==0) {
//            tv_sb.setText("有");//社保
//        }
//        if(Integer.parseInt(data.getProFund())==0) {
//            gjj_data.setText("无");//公积金
//        }
//        else   if(Integer.parseInt(data.getProFund())==0) {
//            gjj_data.setText("有");//公积金
//        }
//        house_data.setText("");//房产类型
//        car_data.setText("");//车产类型
//        xy_data.setText("");//信用状况
//
//    }
    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @OnClick({R.id.rlzl, R.id.rlzz, R.id.rlts, R.id.tv_tochat, R.id.commit_btn, R.id.bujian_btn})
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.rlzl:
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
            case R.id.rlzz:
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
            case R.id.rlts:
                if (!map.get(2)) {
                    obaDownts.start();
                    llts.setVisibility(View.VISIBLE);
                    rlts.setVisibility(View.VISIBLE);
                    mlv.setVisibility(View.VISIBLE);
                    map.put(2, true);
                    scrollDown();
                } else {
                    obaOnts.start();
                    llts.setVisibility(View.VISIBLE);
                    rlts.setVisibility(View.VISIBLE);
                    mlv.setVisibility(View.GONE);
                    map.put(2, false);
                }
                break;
            case R.id.tv_tochat:
                if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                    if (!TextUtils.isEmpty(accountId)) {
                        RongIM.getInstance().startPrivateChat(this, accountId, tv_tochat.getText().toString());
                    }
                }
                break;
            case R.id.commit_btn:
                RequestManager.getMangManger().commitOrder(orderId, new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) throws JSONException {

                        Intent intent = new Intent(ManagerOrderFragment.UPDATA);
                        intent.putExtra("orderSts", "CREDIT_MANAGER_APPROVAL");
                        sendBroadcast(intent);
                        MyToastUtils.showShortToast(OrderDetailAct.this, "提交成功");
                        finish();
                    }

                    @Override
                    public void onError(int status, String msg) {
                        MyToastUtils.showShortToast(OrderDetailAct.this, msg);
                    }
                });

                break;
            case R.id.bujian_btn:
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", data);
                gotoActivity(DribblewareAct.class, false, bundle);
                break;
        }
    }


    /**
     * 获取订单详情
     */
    private void getOrderDetail(String orderId) {
        RequestManager.getMangManger().checkOrderDetail(orderId, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) throws JSONException {
                OrderDetailInfo info = GsonUtils.json2Bean(result, OrderDetailInfo.class);
                if (info != null) {
//                    OrderDetailInfo.DataEntity datas = info.getData();
                    datas = info.getData();
                    if (datas != null) {
                        accountId = datas.getAccountId();
                        orderSts = datas.getOrderSts();
                        remark = datas.getRemark();
                        if ("CREDIT_MANAGER_APPROVAL".equals(orderSts)) {
                            commit.setVisibility(View.VISIBLE);
                            bujian_btn.setVisibility(View.VISIBLE);
                        }  else {
                            commit.setVisibility(View.GONE);
                            bujian_btn.setVisibility(View.GONE);
                        }
//                        if (!TextUtils.isEmpty(datas.getExpectedFrequency()))
//                            //    tvWyl.setText(bean.getExpectedFrequency() + "%");//用户违约率
                            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + datas.getImageLogoPath(), ivBank, options);
                        tvProName.setText(datas.getProductName());
                        if(!"PASS".equals(orderSts)) {
                            if (datas.getTotalAmount() != null)
                                tvAmount.setText("¥" +df.format(datas.getTotalAmount() / 10000) + "万");//金额
                        }else {
                            if(!TextUtils.isEmpty(datas.getPracticalLoan())){
                                tvAmount.setText("¥"+ df.format(Double.valueOf(datas.getPracticalLoan())/10000)+"万");
                            }
                        }

                        if (datas.getMinVal() != null && datas.getMaxVal() != null) {
                            tvScope.setText("额度范围：" + df.format(datas.getMinVal() / 10000) + "~" + df.format(datas.getMaxVal() / 10000) + "万");
                        }
//                        tvScope.setText("额度范围：" + datas.getMinVal() + "~" + datas.getMaxVal());//额度范围
                        if (!TextUtils.isEmpty(datas.getTotalPeriods() + ""))
                            tvMonth.setText(datas.getTotalPeriods() + "个月");//期限
                        if (datas.getTimeMaxVal() != null && datas.getTimeMinVal() != null) {
                            tvLim.setText("期限范围：" + datas.getTimeMinVal() + "~" + datas.getTimeMaxVal() + "月");//期限范围
                        }
                        if (datas.getPeriodsAmount() != null) {
                            tvMonthPay.setText("¥" + datas.getPeriodsAmount());//月供
                        }
                        if (!TextUtils.isEmpty(datas.getPayTypeName())) {
                            tvPaytype.setText("还款方式：" + datas.getPayTypeName());//还款方式
                        }

                        Double totalMPay = Arith.sub(datas.getPeriodsAmount() * datas.getTotalPeriods(), datas.getTotalAmount());
                        tvTotal.setText("¥" + df.format(totalMPay));
//                        if (!TextUtils.isEmpty(datas.getTotalAmount()+""))
//                            tvTotal.setText("¥"+datas.getTotalAmount()+"");//总利息
                        if (datas.getLoanPeriod() != null) {
                            tvLoan.setText(datas.getLoanPeriod() + "个工作日");//放款时间
                        }
                        if (!TextUtils.isEmpty(datas.getMonthlyRateMin()) && !TextUtils.isEmpty(datas.getMonthlyRateMax())) {
                            if (Double.valueOf(datas.getMonthlyRateMax()).equals(Double.valueOf(datas.getMonthlyRateMin()))){
                                tvRate.setText("利率：" + datas.getMonthlyRateMax() + "%");
                            }else {
                                tvRate.setText("利率：" + datas.getMonthlyRateMin() + "%" + "~" + datas.getMonthlyRateMax() + "%");//利率
                            }
                        }
                        if (!TextUtils.isEmpty(datas.getDisposableRateMax()) && !TextUtils.isEmpty(datas.getDisposableRateMin())) {
                            if (Double.valueOf(datas.getDisposableRateMax()).equals(Double.valueOf(datas.getDisposableRateMin()))){
                                tvOnePay.setText("一次性收费：" + datas.getDisposableRateMax() + "%");
                            }else {
                                tvOnePay.setText("一次性收费：" + datas.getDisposableRateMin() + "%~" + datas.getDisposableRateMax() + "%");
                            }
                        }
                        if("PASS".equals(orderSts)){
                            if (!TextUtils.isEmpty(data.getPracticalLoan()))
                                tvAmount.setText("¥" + df.format(Double.valueOf(data.getPracticalLoan()) / 10000) + "万");
                            if (!TextUtils.isEmpty(datas.getRealMonthlyInterestRate()) ) {
                                    tvRate.setText("利率：" + datas.getRealMonthlyInterestRate() + "%" );//利率
                            }
                            if (!TextUtils.isEmpty(datas.getRealOneTimeRate())) {
                                    tvOnePay.setText("一次性收费：" + datas.getRealOneTimeRate()+"%");
                            }
                        }


                        if (!TextUtils.isEmpty(datas.getUserName()))
                            tv_tochat.setText(datas.getUserName());
                        if (datas.getUserSex() == 1) {
                            tvSex.setText("男");
                        } else {
                            tvSex.setText("女");
                        }
                        if (!TextUtils.isEmpty(datas.getIdentCard())) {
                            IdCard.setText(datas.getIdentCard());
                        }

                        if (!TextUtils.isEmpty(datas.getProvince())&&!TextUtils.isEmpty(datas.getCity())
                                &&!TextUtils.isEmpty(datas.getDistrict())) {
                            alwaysaddress.setText(addressUtil.getProName(datas.getProvince())+
                                    addressUtil.getCityName(datas.getProvince(),datas.getCity())+
                                    addressUtil.getCountryName(datas.getCity(),datas.getDistrict()));
                        }
                        if (!TextUtils.isEmpty(datas.getDetailAddress())) {
                            address.setText(datas.getDetailAddress());
                        }

                        if ("1".equals(datas.getMarrStatus())) {
                            tv_wyl.setText("已婚");
                        } else {
                            tv_wyl.setText("未婚");
                        }

                        if (!TextUtils.isEmpty(datas.getNativePlace())) {
                            tv_jgl.setText(datas.getNativePlace());
                        }

                        if (!TextUtils.isEmpty(datas.getNativePlaceAddr())) {
                            user_data.setText(datas.getNativePlaceAddr());
                        }
                        if (!TextUtils.isEmpty(datas.getJobIdentityName())) {
                            tv_zy.setText(datas.getJobIdentityName());
                        }
                        if (datas.getSecailSecurity() == 1) {
                            tv_sb.setText("有");
                        } else {
                            tv_sb.setText("无");
                        }

                        if (datas.getProFund() == 1) {
                            gjj_data.setText("有");
                        } else {
                            gjj_data.setText("无");
                        }
                        if (!TextUtils.isEmpty(datas.getPropertyTypeName())) {
                            house_data.setText(datas.getPropertyTypeName());
                        }

                        if (!TextUtils.isEmpty(datas.getCarStatusName())) {
                            car_data.setText(datas.getCarStatusName());
                        }



                        //如果taskManageId为空则不显示贷款提速
                        if (TextUtils.isEmpty(datas.getTaskManageId())) {
                            llts.setVisibility(View.GONE);
                            rlts.setVisibility(View.GONE);
                            mlv.setVisibility(View.GONE);
                        } else {
                            llts.setVisibility(View.VISIBLE);
                            rlts.setVisibility(View.VISIBLE);
                            mlv.setVisibility(View.GONE);
                            findTaskBytaskIds(datas.getTaskManageId());
                        }

                    }
                    loadView.loadComplete();
                }


            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
            }
        });
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
            intent.setClass(OrderDetailAct.this, FinishTaskPlaceAct.class);
        } else if (list.get(0).getValueType() == 1) {
            intent.setClass(OrderDetailAct.this, FinishTaskPicture.class);
        }
        startActivity(intent);
    }



    /**
     * 根据任务查询任务详情（审核中，已完成）
     *
     * @param taskEntity
     */
    @SuppressWarnings("JavaDoc")
    private void findTaskDetail(TaskEntity taskEntity, final int position) {
        RequestManager.getUserManager().findProTaskDetail(taskEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                finishList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskEntity>>() {
                }.getType());
                selectToFinishAct(finishList, position);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    /**
     * 根据任务id查询任务列表内容
     *
     * @param taskId
     */
    @SuppressWarnings("JavaDoc")
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
                    adapter = new PrimaryTaskAdapter(taskEntityList, getApplicationContext());
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
                Toast.makeText(OrderDetailAct.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }
        registerReceiver(receiver, new IntentFilter(TaskLevelAct.UPDATE_LIST));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    private MyBroadCastReceiver receiver;

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            findTaskBytaskIds(datas.getTaskManageId());
        }
    }

}
