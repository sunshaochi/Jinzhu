package com.beyonditsm.financial.activity.wallet;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.entity.WalletQuanBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.CircleImageView;
import com.beyonditsm.financial.view.LoadingView;
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
import java.util.List;

/**
 * Created by wangbin on 16/1/14
 */
@SuppressLint("SetTextI18n")
public class MyWalletActivity extends BaseActivity {
    @ViewInject(R.id.civHead)
    private CircleImageView civHead;//头像
    @ViewInject(R.id.tvName)
    private TextView tvName;//用户身份（代言人）
    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;//用户电话号
    @ViewInject(R.id.tv_ExchangeMoney)
    private TextView tvExangeMoney;//可兑换现金
    @ViewInject(R.id.tv_WeitGetMoney)
    private TextView tvWeitGetMoney;//待奖励
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;//抵扣金额
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;
    @ViewInject(R.id.tv_xj)
    private TextView tv_xj;
    @ViewInject(R.id.dikou)
    private TextView tv_dikou;
//    @ViewInject(R.id.ivPaymentsRed)
//    private ImageView ivPaymentsRedPoint;//收支明细推送红点


    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private UserLoginEntity ule;//用户登录信息
    private UserEntity user;//用户信息
    private PaymentsRedDisReceiver paymentsRedDisReceiver;
    private PaymentsRedHideReceiver paymentsRedHideReceiver;
    private List<WalletQuanBean> walletList=new ArrayList<>();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mywallet);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ParamsUtil.getInstance().setWalletEnter(false);
        setLeftTv("返回");
        setTopTitle("我的钱包");
        //强制关闭键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ule = getIntent().getParcelableExtra("userLogin");
        user = getIntent().getParcelableExtra("userInfo");
        String isUpgrade = SpUtils.getIsUpgrade(getApplicationContext());
        if (!TextUtils.isEmpty(isUpgrade) && "isUpgrade".equals(isUpgrade)) {
//            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        } else {
//            ivPaymentsRedPoint.setVisibility(View.GONE);
        }
        String receiveReward = SpUtils.getReceiveReward(getApplicationContext());
        if (!TextUtils.isEmpty(receiveReward)&&"isReceive".equals(receiveReward)){
//            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        }else{
//            ivPaymentsRedPoint.setVisibility(View.GONE);
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
        getWalletQuan("15216187360");
    }

    private void setUserLogin() {
        if (ule != null) {
            if (!TextUtils.isEmpty(ule.getDescription())) {
//                tvName.setText(ule.getDescription());
                tvName.setText("代言人");
            }
            if (!TextUtils.isEmpty(ule.getUsername())) {
                tvPhone.setText(ule.getUsername());
            }
            if(null != ParamsUtil.getInstance().getUserID() && !"".equals(ParamsUtil.getInstance().getUserID())){
                tvPhone.setText(ParamsUtil.getInstance().getUserID());
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
        Intent intent;
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
//        ivPaymentsRedPoint.setVisibility(View.GONE);
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
        ParamsUtil.getInstance().setWalletEnter(false);
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
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200){
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                }

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
            @SuppressWarnings("unchecked")
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
     * 获取代言人信息
     */
    private void findServantInfo() {

        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
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
//            ivPaymentsRedPoint.setVisibility(View.VISIBLE);
        }
    }

    public static final String HIDE = "hide";
    public class PaymentsRedHideReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            ivPaymentsRedPoint.setVisibility(View.GONE);
        }
    }

    /**
     * 获取抵用卷
     * @param uid
     */
    private void getWalletQuan(String uid){
        RequestManager.getWalletManager().getWalletQuan(uid, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                walletList = gson.fromJson(data.toString(), new TypeToken<List<WalletQuanBean>>() {
                }.getType());
                if (walletList!=null&&walletList.size()>0){
                   for (int i=0;i<walletList.size();i++){
                       WalletQuanBean walletQuanBean = walletList.get(i);
                       if (TextUtils.equals(walletQuanBean.getType(),1+"")){
                           if (!TextUtils.isEmpty(walletQuanBean.getBalance())){
                               tv_xj.setText(walletQuanBean.getBalance());
                           }else {
                               tv_xj.setText(0+"");
                           }
                       }else if (TextUtils.equals(walletQuanBean.getType(),2+"")){
                           if (!TextUtils.isEmpty(walletQuanBean.getBalance())){
                               tv_dikou.setText(walletQuanBean.getBalance());
                           }else {
                               tv_dikou.setText(0+"");
                           }
                       }
                   }
                }else {
                    tv_xj.setText(0+"");
                    tv_dikou.setText(0+"");
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
