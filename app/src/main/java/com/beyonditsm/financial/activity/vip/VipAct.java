package com.beyonditsm.financial.activity.vip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.servicer.ServiceDataAct;
import com.beyonditsm.financial.activity.user.UpdateAct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.entity.VipBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * vip专题页面
 * Created by Administrator on 2016/5/30.
 */
@SuppressWarnings("deprecation")
public class VipAct extends BaseActivity {
    @ViewInject(R.id.tv_accountName)
    private TextView tvAccountName;//用户姓名
    //    @ViewInject(R.id.tv_userVipLevel)
//    private TextView tvUserVipLevel;//用户VIP等级
    @ViewInject(R.id.iv_vipLevel)
    private ImageView ivVipLevel;//vip等级图片
    @ViewInject(R.id.tv_expireDate)
    private TextView tvExpireDate;//到期时间
//    @ViewInject(R.id.ll_isVip)
//    private LinearLayout llIsVip;//是vip时显示
//    @ViewInject(R.id.tv_notVip)
//    private TextView tvNotVip;//不是VIP时显示
    @ViewInject(R.id.tv_v1FeeWaiver)
    private TextView tvV1FeeWaiver;//VIP1减免费
    @ViewInject(R.id.tv_v1OriginalPrice)
    private TextView tvV1OriginalPrice;//VIP1原价
    @ViewInject(R.id.tv_v1PresentPrice)
    private TextView tvV1PresentPrice;//VIP1现价
    @ViewInject(R.id.tv_v2FeeWaiver)
    private TextView tvV2FeeWaiver;//VIP2减免费
    @ViewInject(R.id.tv_v2OriginalPrice)
    private TextView tvV2OriginalPrice;//VIP2原价
    @ViewInject(R.id.tv_v2PresentPrice)
    private TextView tvV2PresentPrice;//VIP2现价
    @ViewInject(R.id.tv_v3FeeWaiver)
    private TextView tvV3FeeWaiver;//VIP3减免费
    @ViewInject(R.id.tv_v3OriginalPrice)
    private TextView tvV3OriginalPrice;//VIP3原价
    @ViewInject(R.id.tv_v3PresentPrice)
    private TextView tvV3PresentPrice;//VIP3现价
    @ViewInject(R.id.tv_vip1Open)
    private LinearLayout tvVip1Open;//vip1开通
    @ViewInject(R.id.tv_vip2Open)
    private LinearLayout tvVip2Open;//vip2开通
    @ViewInject(R.id.tv_vip3Open)
    private LinearLayout tvVip3Open;//vip3开通
//    @ViewInject(R.id.tv_vip1Renew)
//    private TextView tvV1Renew;
//    @ViewInject(R.id.tv_vip2Renew)
//    private TextView tvV2Renew;
//    @ViewInject(R.id.tv_vip3Renew)
//    private TextView tvV3Renew;
//    @ViewInject(R.id.tv_vip2Upgrade)
//    private TextView tvV2Upgrade;
//    @ViewInject(R.id.tv_vip3Upgrade)
//    private TextView tvV3Upgrade;

//    public static final String TYPE = "type";
//    public static final String PRICE = "price";
//    public static final String VIP_LEVEL = "vip_level";

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String v3PresentPrice;
    private String v2PresentPrice;
    private String v1PresentPrice;
    private String roleName;
    private UserLoginEntity ule;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_vip);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("金蛛VIP");
//        userInfo = getIntent().getParcelableExtra("userInfo");
        ule = getIntent().getParcelableExtra("user");
//        roleName = SpUtils.getRoleName(getApplicationContext());
        if (ule != null) {
            roleName = ule.getRoleName();
            findVipInfo();
        }

        setDeleteLine();
    }

    //设置原价删除线
    private void setDeleteLine() {
        tvV1OriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvV2OriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvV3OriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void findVipInfo() {
        RequestManager.getCommManager().findVipInfo(ule.getUsername(), new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<VipBean> rd = (ResultData<VipBean>) GsonUtils.json(result, VipBean.class);
                VipBean bean = rd.getData();
                if (bean != null) {
                    tvAccountName.setText(bean.getUserName());
                    int userVipLevel = bean.getUserVipLevel();
                    switch (userVipLevel) {
                        case 0:
//                        llIsVip.setVisibility(View.GONE);
//                        tvNotVip.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip1);
                            tvVip1Open.setVisibility(View.GONE);
                            tvVip2Open.setVisibility(View.GONE);
                            tvVip3Open.setVisibility(View.GONE);
//                        tvV1Renew.setVisibility(View.VISIBLE);
//                        tvV2Upgrade.setVisibility(View.VISIBLE);
//                        tvV3Upgrade.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip2);
                            tvVip1Open.setVisibility(View.VISIBLE);
                            tvVip1Open.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_grey));
                            tvVip1Open.setEnabled(false);
                            tvVip2Open.setVisibility(View.GONE);
                            tvVip3Open.setVisibility(View.GONE);
//                        tvV2Renew.setVisibility(View.VISIBLE);
//                        tvV3Upgrade.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip3);
                            tvVip1Open.setVisibility(View.VISIBLE);
                            tvVip1Open.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_grey));
                            tvVip1Open.setEnabled(false);
                            tvVip2Open.setVisibility(View.VISIBLE);
                            tvVip2Open.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_grey));
                            tvVip2Open.setEnabled(false);
//                        tvV3Renew.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip4);
                            break;
                        case 5:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip5);
                            break;
                        case 6:
                            ivVipLevel.setBackgroundResource(R.mipmap.vip6);
                            break;
                    }

                    String data = VipAct.this.format.format(new Date(bean.getExpireDate()));
                    tvExpireDate.setText(data);
                    List<VipBean.VipListEntity> vipList = bean.getVipList();
                    VipBean.VipListEntity vipList1 = vipList.get(0);
                    tvV1FeeWaiver.setText(vipList1.getFeeWaiver()+"%");
                    tvV1OriginalPrice.setText("￥"+vipList1.getOriginalPrice());
                    v1PresentPrice = vipList1.getPresentPrice();
                    tvV1PresentPrice.setText("￥"+v1PresentPrice);
                    VipBean.VipListEntity vipList2 = vipList.get(1);
                    tvV2FeeWaiver.setText(vipList2.getFeeWaiver()+"%");
                    tvV2OriginalPrice.setText("￥"+vipList2.getOriginalPrice());
                    v2PresentPrice = vipList2.getPresentPrice();
                    tvV2PresentPrice.setText("￥"+v2PresentPrice);
                    VipBean.VipListEntity vipList3 = vipList.get(2);
                    tvV3FeeWaiver.setText(vipList3.getFeeWaiver()+"%");
                    tvV3OriginalPrice.setText("￥"+vipList3.getOriginalPrice());
                    v3PresentPrice = vipList3.getPresentPrice();
                    tvV3PresentPrice.setText("￥"+ v3PresentPrice);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @OnClick({R.id.ll_myInfo, R.id.tv_vip1Open, R.id.tv_vip2Open, R.id.tv_vip3Open})
    public void todo(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_myInfo:
                if ("ROLE_COMMON_CLIENT".equals(roleName)){
                    intent = new Intent(VipAct.this, UpdateAct.class);
                }else{
                    intent = new Intent(VipAct.this, ServiceDataAct.class);
                }
                startActivity(intent);
                break;
            case R.id.tv_vip1Open://vip1开通
//                intent = new Intent(VipAct.this, SelectPayWayAct.class);
//                intent.putExtra(TYPE, "Open");
//                intent.putExtra(PRICE, v1PresentPrice);
//                intent.putExtra(VIP_LEVEL, 1);
//                startActivity(intent);
                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
                break;
            case R.id.tv_vip2Open://vip2开通
//                intent = new Intent(VipAct.this, SelectPayWayAct.class);
//                intent.putExtra(TYPE, "Open");
//                intent.putExtra(PRICE, v2PresentPrice);
//                intent.putExtra(VIP_LEVEL, 2);
//                startActivity(intent);
                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
                break;
            case R.id.tv_vip3Open://vip3开通
//                intent = new Intent(VipAct.this, SelectPayWayAct.class);
//                intent.putExtra(TYPE, "Open");
//                intent.putExtra(PRICE, v3PresentPrice);
//                intent.putExtra(VIP_LEVEL, 3);
//                startActivity(intent);
                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
                break;
//            case R.id.tv_vip1Renew://vip1续费
////                intent = new Intent(VipAct.this, SelectPayWayAct.class);
////                intent.putExtra(TYPE, "Renew");
////                intent.putExtra(PRICE, v1PresentPrice);
////                intent.putExtra(VIP_LEVEL, 1);
////                startActivity(intent);
//                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
//                break;
//            case R.id.tv_vip2Renew://VIP2续费
////                intent = new Intent(VipAct.this, SelectPayWayAct.class);
////                intent.putExtra(TYPE, "Renew");
////                intent.putExtra(PRICE, v2PresentPrice);
////                intent.putExtra(VIP_LEVEL, 2);
////                startActivity(intent);
//                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
//                break;
//            case R.id.tv_vip2Upgrade://vip2升级
////                intent = new Intent(VipAct.this, SelectPayWayAct.class);
////                intent.putExtra(TYPE, "Upgrade");
////                intent.putExtra(PRICE, v2PresentPrice);
////                intent.putExtra(VIP_LEVEL, 2);
////                startActivity(intent);
//                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
//                break;
//            case R.id.tv_vip3Renew://vip3续费
////                intent = new Intent(VipAct.this, SelectPayWayAct.class);
////                intent.putExtra(TYPE, "Renew");
////                intent.putExtra(PRICE, v3PresentPrice);
////                intent.putExtra(VIP_LEVEL, 3);
////                startActivity(intent);
//                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
//                break;
//            case R.id.tv_vip3Upgrade://vip3升级
////                intent = new Intent(VipAct.this, SelectPayWayAct.class);
////                intent.putExtra(TYPE, "Upgrade");
////                intent.putExtra(PRICE, v3PresentPrice);
////                intent.putExtra(VIP_LEVEL, 3);
////                startActivity(intent);
//                MyToastUtils.showShortToast(getApplicationContext(),"界面完善中...");
//                break;
        }
    }
}
