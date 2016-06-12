package com.beyonditsm.financial.activity.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.servicer.ServiceMainAct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;

/**
 * Created by wangbin on 16/1/14.
 */
public class MyWalletActivity extends BaseActivity {
    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;//头像
    @ViewInject(R.id.tvName)
    private TextView tvName;//用户身份（服务者）
    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;//用户电话号
    @ViewInject(R.id.rlMyPayments)
    private RelativeLayout rlMyPayments;//收支明细
    @ViewInject(R.id.rlMyOrder)
    private RelativeLayout rlMyOrder;//订单明细
    @ViewInject(R.id.tv_ExchangeMoney)
    private TextView tvExangeMoney;//可兑换现金
    @ViewInject(R.id.tv_WeitGetMoney)
    private TextView tvWeitGetMoney;//待奖励
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;//抵扣金额
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.ivPaymentsRed)
    private ImageView ivPaymentsRedPoint;//收支明细推送红点


    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private UserLoginEntity ule;//用户登录信息
    private UserEntity user;//用户信息
    private PaymentsRedDisReceiver paymentsRedDisReceiver;
    private PaymentsRedHideReceiver paymentsRedHideReceiver;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mywallet);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ParamsUtil.getInstance().setWalletEnter(false);
        setLeftTv("返回");
        setTopTitle("我的钱包");
        ule = getIntent().getParcelableExtra("userLogin");
        user = getIntent().getParcelableExtra("userInfo");
        String isUpgrade = SpUtils.getIsUpgrade(getApplicationContext());
        if (!TextUtils.isEmpty(isUpgrade) && "isUpgrade".equals(isUpgrade)) {
            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        } else {
            ivPaymentsRedPoint.setVisibility(View.GONE);
        }
        String receiveReward = SpUtils.getReceiveReward(getApplicationContext());
        if (!TextUtils.isEmpty(receiveReward)&&"isReceive".equals(receiveReward)){
            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        }else{
            ivPaymentsRedPoint.setVisibility(View.GONE);
        }
        if (ule == null) {
            getUserLoginInfo();
            setUserLogin();
        } else {
            setUserLogin();
        }
        if (user == null) {
            if (ule != null) {
                if (ule.getDescription().contains("用户")) {
                    getUserInfo();
                    setUserInfo();
                }
                if (ule.getDescription().contains("服务者")) {
                    findServantInfo();
                    setUserInfo();
                }
            }
        } else {
            setUserInfo();
        }
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getUserLoginInfo();
                getUserInfo();
                findServantInfo();
            }
        });
    }

    private void setUserLogin() {
        if (ule != null) {
            if (!TextUtils.isEmpty(ule.getDescription())) {
                tvName.setText(ule.getDescription());
            }
            if (!TextUtils.isEmpty(ule.getUsername())) {
                tvPhone.setText(ule.getUsername());
            }
        }
    }

    private void setUserInfo() {
        if (user != null) {
            if (!TextUtils.isEmpty(user.getCashTicketAmount())) {
                double dCashA = Double.valueOf(user.getCashTicketAmount());
                tvExangeMoney.setText((long) dCashA + "");
            }
            if (!TextUtils.isEmpty(user.getUnCashTicketAmount())) {
                double unCashA = Double.valueOf(user.getUnCashTicketAmount());
                tvWeitGetMoney.setText((long) unCashA + "");
            }
            if (!TextUtils.isEmpty(user.getDeductionTicketAmount())) {
                double deductionA = Double.valueOf(user.getDeductionTicketAmount());
                tvDikouMoney.setText((long) deductionA + "");
            }
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon(), civHead, options);

        }
    }

    @OnClick({R.id.rlMyPayments, R.id.rlMyOrder, R.id.rlxianjin, R.id.rldikou, R.id.rlBindBankCard})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //收支明细
            case R.id.rlMyPayments:

                hideRedPoint();
                intent = new Intent(MyWalletActivity.this, BalancePaymentsAct.class);
                startActivity(intent);
                break;
            //订单明细
            case R.id.rlMyOrder:
                intent = new Intent(MyWalletActivity.this, OrderDetailAct.class);
                startActivity(intent);
                break;
            //现金券兑换
            case R.id.rlxianjin:
                intent = new Intent(MyWalletActivity.this, CashExchange.class);
                intent.putExtra("userInfo", user);
                startActivity(intent);
                break;
            //抵扣券
            case R.id.rldikou:
                intent = new Intent(MyWalletActivity.this, InterestDeduction.class);
                intent.putExtra("userInfo", user);
                startActivity(intent);
                break;
            //绑定银行卡
            case R.id.rlBindBankCard:
                intent = new Intent(MyWalletActivity.this, BindBankCardAct.class);
                intent.putExtra("userInfo", user);
                startActivity(intent);
                break;
        }
    }

    private void hideRedPoint() {

//        sendBroadcast(new Intent(MyCreditAct.HIDE_MESSAGE));
//        sendBroadcast(new Intent(MineFragment.HIDE_WALLET_POINT));
//        sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
//        SpUtils.clearIsUpgrade(getApplicationContext());
        SpUtils.clearReceiveReward(getApplicationContext());
        ivPaymentsRedPoint.setVisibility(View.GONE);
        sendBroadcast(new Intent(ServiceMineFrg.HIDE_RED_POINT));
        sendBroadcast(new Intent(ServiceMainAct.HIDE));
        sendBroadcast(new Intent(MineFragment.HIDE_WALLET_POINT));
        sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ule != null) {
            if (ule.getDescription().contains("服务者")) {
                findServantInfo();
            }
            if (ule.getDescription().contains("用户")) {
                getUserInfo();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (paymentsRedDisReceiver==null){
            paymentsRedDisReceiver = new PaymentsRedDisReceiver();
        }
        if (paymentsRedHideReceiver==null){
            paymentsRedHideReceiver = new PaymentsRedHideReceiver();
        }
        registerReceiver(paymentsRedDisReceiver,new IntentFilter(DISPLAY));
        registerReceiver(paymentsRedHideReceiver,new IntentFilter(HIDE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (paymentsRedDisReceiver!=null){
            unregisterReceiver(paymentsRedDisReceiver);
        }
        if (paymentsRedHideReceiver!=null){
            unregisterReceiver(paymentsRedHideReceiver);
        }
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                ule = rd.getData();
            }

            @Override
            public void onError(int status, String msg) {
                loadingView.loadError();
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                loadingView.loadComplete();
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {
                    if (!TextUtils.isEmpty(user.getCashTicketAmount())) {
                        double dCashA = Double.valueOf(user.getCashTicketAmount());
                        tvExangeMoney.setText((long) dCashA + "");
                    }
                    if (!TextUtils.isEmpty(user.getUnCashTicketAmount())) {
                        double unCashA = Double.valueOf(user.getUnCashTicketAmount());
                        tvWeitGetMoney.setText((long) unCashA + "");
                    }
                    if (!TextUtils.isEmpty(user.getDeductionTicketAmount())) {
                        double deductionA = Double.valueOf(user.getDeductionTicketAmount());
                        tvDikouMoney.setText((long) deductionA + "");
                    }

                }
            }

            @Override
            public void onError(int status, String msg) {
                loadingView.loadError();
            }
        });
    }

    /**
     * 获取服务者信息
     */
    private void findServantInfo() {

        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {
                    if (!TextUtils.isEmpty(user.getCashTicketAmount())) {
                        double dCashA = Double.valueOf(user.getCashTicketAmount());
                        tvExangeMoney.setText((long) dCashA + "");
                    }
                    if (!TextUtils.isEmpty(user.getUnCashTicketAmount())) {
                        double unCashA = Double.valueOf(user.getUnCashTicketAmount());
                        tvWeitGetMoney.setText((long) unCashA + "");
                    }
                    if (!TextUtils.isEmpty(user.getDeductionTicketAmount())) {
                        double deductionA = Double.valueOf(user.getDeductionTicketAmount());
                        tvDikouMoney.setText((long) deductionA + "");
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                loadingView.loadError();
            }
        });
    }

    public static final String DISPLAY = "display";
    public class PaymentsRedDisReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        }
    }

    public static final String HIDE = "hide";
    public class PaymentsRedHideReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ivPaymentsRedPoint.setVisibility(View.GONE);
        }
    }
}
