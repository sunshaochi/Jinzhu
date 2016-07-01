//package com.beyonditsm.financial.activity.vip;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.beyonditsm.financial.R;
//import com.beyonditsm.financial.activity.BaseActivity;
//import com.beyonditsm.financial.util.MyLogUtils;
//import com.lidroid.xutils.view.annotation.ViewInject;
//import com.lidroid.xutils.view.annotation.event.OnClick;
//
///**
// * 选择支付方式界面
// * Created by Administrator on 2016/5/31.
// */
//public class SelectPayWayAct extends BaseActivity {
//    @ViewInject(R.id.tv_vipTitle)
//    private TextView tvTitle;
//    @ViewInject(R.id.tv_phoneNumber)
//    private TextView tvPhoneNumber;
//    @ViewInject(R.id.tv_forWhat)
//    private TextView tvForWhat;
//    @ViewInject(R.id.tv_target)
//    private TextView tvTarget;
//    @ViewInject(R.id.tv_payMoney)
//    private TextView tvPayMoney;
//
//    @Override
//    public void setLayout() {
//        setContentView(R.layout.act_selectpay);
//    }
//
//    @Override
//    public void init(Bundle savedInstanceState) {
//        setTopTitle("选择支付方式");
//        String type = getIntent().getStringExtra(VipAct.TYPE);
//        String price = getIntent().getStringExtra(VipAct.PRICE);
//        int vipLevel = getIntent().getIntExtra(VipAct.VIP_LEVEL, 0);
//        int i = vipLevel + 1;
//        MyLogUtils.info("lv." + vipLevel);
//        if ("Open".equals(type)) {
//            tvTitle.setText("金蛛VIP年费开通");
//            tvTarget.setText("VIP" + vipLevel);
//        } else if ("Renew".equals(type)) {
//            tvTitle.setText("金蛛VIP年费续费");
//            tvTarget.setText("VIP" + vipLevel);
//        } else if ("Upgrade".equals(type)) {
//            tvTitle.setText("金蛛VIP年费升级");
//            tvForWhat.setText("升级后：");
//            tvTarget.setText("VIP"+i);
//        }
//        tvPayMoney.setText(price + "元");
//
//
//    }
//
//    @OnClick({R.id.btn_zhifubao, R.id.btn_weixin, R.id.btn_yinhangka})
//    public void todo(View v) {
//        switch (v.getId()) {
//            case R.id.btn_zhifubao:
//
//                break;
//            case R.id.btn_weixin:
//
//                break;
//            case R.id.btn_yinhangka:
//
//                break;
//        }
//    }
//}
