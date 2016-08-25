package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.MessageActivity;
import com.beyonditsm.financial.activity.user.CreditPointAct;
import com.beyonditsm.financial.activity.user.HardCreditAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.activity.user.SettingAct;
import com.beyonditsm.financial.activity.user.UpdateAct;
import com.beyonditsm.financial.activity.vip.VipAct;
import com.beyonditsm.financial.activity.wallet.MyWalletActivity;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.db.MessageDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MinePageLoadingView;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * 我的
 * Created by wangbin on 15/11/11.
 */
public class MineFragment extends BaseFragment {
    @ViewInject(R.id.tvName)
    private TextView tvName;
    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;
    //    private String targetId;
    @ViewInject(R.id.tv_score)
    private TextView tv_score;
    @ViewInject(R.id.tvGrade)
    private TextView tvGrade;

    @ViewInject(R.id.tvCredit)
    private TextView tvCredit;//信用分
    @ViewInject(R.id.tvExit)
    private TextView tvExit;//退出

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;//标题
    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;
    @ViewInject(R.id.msg_top)
    private RelativeLayout msg_top;//右上角消息图标
    @ViewInject(R.id.msg_top_point)
    private ImageView msg_top_point;//右上角消息图标小红点
    @ViewInject(R.id.ivMs)
    private ImageView ivRedPoint;
    @ViewInject(R.id.ivWalletRed)
    private ImageView ivWalletRedPoint;
    @ViewInject(R.id.iv_vipLevel)
    private ImageView ivVipLevel;
    @ViewInject(R.id.sv_mine)
    private ScrollView svMine;
    @ViewInject(R.id.mplv_mine)
    private MinePageLoadingView minePageLoadingView;
    private UserEntity user;//用户信息
    public static final String USER_KEY = "user_info";
    private boolean isLogin;

    String grade;//超过百分之多少用户

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private UserLoginEntity ule;
    private DisplayRedReceiver displayRedReceiver;
    private HideRedReceiver hideRedReceiver;
    private WalletRedReceiver walletRedReceiver;
    private HideWalletRedReceiver hideWalletRedReceiver;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }


    @Override
    public void initData(Bundle savedInstanceState) {

        tvTitle.setText("我");
        rlBack.setVisibility(View.GONE);
        msg_top.setVisibility(View.VISIBLE);
        if ("".equals(SpUtils.getOrderId(MyApplication.getInstance()))) {
            ivRedPoint.setVisibility(View.GONE);
            msg_top_point.setVisibility(View.GONE);
        } else {
            ivRedPoint.setVisibility(View.VISIBLE);
            msg_top_point.setVisibility(View.VISIBLE);
        }
        if ("".equals(SpUtils.getRoleName(context))) {
            isLogin = false;
            tvName.setText("去登录");
            tvExit.setVisibility(View.GONE);
        } else {
            isLogin = true;
            tvExit.setVisibility(View.VISIBLE);
//            getUserInfo();
            getUserLoginInfo();
        }

        svMine.smoothScrollTo(0, 20);
//        rlMyData.setFocusable(true);
//        rlMyData.setFocusableInTouchMode(true);
//        rlMyData.requestFocus();
    }

    @Override
    public void setListener() {
        minePageLoadingView.setOnRetryListener(new MinePageLoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getUserInfo();
            }
        });
        minePageLoadingView.setOnLogOutListener(new MinePageLoadingView.OnLogOutListener() {
            @Override
            public void OnLogOut() {
                RequestManager.getCommManager().toLoginOut(new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) {
                        if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                            RongIM.getInstance().logout();
                        }
                    }

                    @Override
                    public void onError(int status, String msg) {

                    }
                });
                Set<String> set = new HashSet<>();
                JPushInterface.setAliasAndTags(getActivity(), "", set, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });

                JPushInterface.clearAllNotifications(getActivity());
//                        FriendDao.deleteAllMes();
                MessageDao.deleteAllMes();
                SpUtils.clearSp(getContext());
                SpUtils.clearOrderId(getContext());
                getActivity().sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
                ivWalletRedPoint.setVisibility(View.GONE);
                msg_top_point.setVisibility(View.GONE);
                ivRedPoint.setVisibility(View.GONE);
                EventBus.getDefault().post(new SwitchEvent());

            }
        });
    }

    @OnClick({R.id.rlMyCode, R.id.rlRecomm, R.id.rlLines, R.id.rlMyCredit, R.id.rlSet, R.id.tvExit,
            R.id.rlWork, R.id.rlMyData, R.id.msg_top, R.id.rlWallet, R.id.rlVip})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //我的资料
            case R.id.rlMyData:
                if (isLogin) {
                    intent = new Intent(getActivity(), UpdateAct.class);
                    intent.putExtra(MineFragment.USER_KEY, user);
                } else {
                    intent = new Intent(getActivity(), LoginAct.class);
                }
                startActivity(intent);
                break;
            //我的信用分
            case R.id.rlMyCode:
                intent = new Intent(getActivity(), CreditPointAct.class);
                if (TextUtils.isEmpty(user.getCreditScore()))
                    intent.putExtra(CreditPointAct.CREDIT, 0);
                else
                    intent.putExtra(CreditPointAct.CREDIT, Integer.valueOf(user.getCreditScore()));
                if (TextUtils.isEmpty(grade))
                    intent.putExtra(CreditPointAct.GRADE, "0");
                else
                    intent.putExtra(CreditPointAct.GRADE, grade);
                getActivity().startActivity(intent);
                break;
            //我的信售额
            case R.id.rlLines:
                intent = new Intent(getActivity(), HardCreditAct.class);
                getActivity().startActivity(intent);
                break;
            //我的贷款
            case R.id.rlMyCredit:
                intent = new Intent(getActivity(), MyCreditAct.class);
//                ivRedPoint.setVisibility(View.GONE);
//                getActivity().sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
                getActivity().startActivity(intent);
                break;
            //推荐好友
            case R.id.rlRecomm:
                intent = new Intent(getActivity(), MyRecommAct.class);
                intent.putExtra("userLogin", ule);
                getActivity().startActivity(intent);
                break;
            //打工挣钱
            case R.id.rlWork:
//                intent = new Intent(getActivity(), NewWorkAct.class);
////                intent.putExtra("id",user.getId());
//                intent.putExtra("accountId", user);
//                if (ule != null)
//                    intent.putExtra(NewWorkAct.ROLE, ule.getRoleName());
//                getActivity().startActivity(intent);
                break;
            //我的钱包
            case R.id.rlWallet:
                intent = new Intent(getActivity(), MyWalletActivity.class);
                intent.putExtra("userLogin", ule);
                intent.putExtra("userInfo", user);
                getActivity().startActivity(intent);
                break;
            //设置
            case R.id.rlSet:
                intent = new Intent(getActivity(), SettingAct.class);
                intent.putExtra(USER_KEY, user);
                getActivity().startActivity(intent);
                break;
            //注销
            case R.id.tvExit:
                MyAlertDialog dialog = new MyAlertDialog(getActivity());
                dialog.builder().setTitle("提示").setMsg("确认退出吗？").setPositiveButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestManager.getCommManager().toLoginOut(new RequestManager.CallBack() {
                            @Override
                            public void onSucess(String result) {
                                if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                                    RongIM.getInstance().logout();
                                }
                            }

                            @Override
                            public void onError(int status, String msg) {

                            }
                        });
                        Set<String> set = new HashSet<>();
                        JPushInterface.setAliasAndTags(getActivity(), "", set, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                            }
                        });

                        JPushInterface.clearAllNotifications(getActivity());
//                        FriendDao.deleteAllMes();
                        MessageDao.deleteAllMes();
                        SpUtils.clearSp(getContext());
                        SpUtils.clearOrderId(getContext());
                        getActivity().sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
                        ivWalletRedPoint.setVisibility(View.GONE);
                        msg_top_point.setVisibility(View.GONE);
                        ivRedPoint.setVisibility(View.GONE);
                        EventBus.getDefault().post(new SwitchEvent());
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.msg_top://消息
                msg_top_point.setVisibility(View.GONE);
                Intent msgintent = new Intent(getActivity(), MessageActivity.class);
                getActivity().startActivity(msgintent);
                break;

            case R.id.rlVip://金蛛VIP
                intent = new Intent(getActivity(), VipAct.class);
                intent.putExtra("user", ule);
                getActivity().startActivity(intent);
                break;
        }
    }

    public class SwitchEvent {
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {
                    if (!TextUtils.isEmpty(user.getAccountName())) {
                        tvName.setText(user.getAccountName());
                    }
                    tv_score.setText(user.getCreditScore());
//                    tvGrade.setText(user.getCreditGrade());
                    if (!TextUtils.isEmpty(user.getCreditScore())) {
                        tvCredit.setText(user.getCreditScore());
                    }
                    if (TextUtils.isEmpty(user.getCreditScore())) {
                        getScorePer("0");
                    } else {
                        getScorePer(user.getCreditScore());
                    }
                    ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon(), civHead, options);
                    if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                        if (!TextUtils.isEmpty(user.getAccountId())) {
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(user.getAccountId(), user.getUserName(),
                                    Uri.parse(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon())));
                            RongIM.getInstance().setMessageAttachedUserInfo(true);
                            FriendBean bean = new FriendBean();
                            bean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon());
                            bean.setUserName(user.getUserName());
                            bean.setUserId(user.getAccountId());
                            FriendDao.saveMes(bean);
                        }
                    }
                    minePageLoadingView.loadComplete();
                } else {
                    minePageLoadingView.loadError();
                }
            }

            @Override
            public void onError(int status, String msg) {
                minePageLoadingView.loadError();
            }
        });
    }

    /**
     * 获取信用分超过多少的用户
     *
     * @param score 信用分
     */
    private void getScorePer(String score) {
        RequestManager.getUserManager().getScorePer(score, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                grade = object.optString("data");
                double dGrade = Double.valueOf(grade);
                tvGrade.setText((int) dGrade + "%");

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }

        if (scoreReceiver == null) {
            scoreReceiver = new ScoreBroadCastReceiver();
        }

        if (messageReceiver == null) {
            messageReceiver = new MessageBroadCastReceiver();
        }
        if (displayRedReceiver == null) {
            displayRedReceiver = new DisplayRedReceiver();
        }
        if (hideRedReceiver == null) {
            hideRedReceiver = new HideRedReceiver();
        }
        if (walletRedReceiver == null) {
            walletRedReceiver = new WalletRedReceiver();
        }
        if (hideWalletRedReceiver == null) {
            hideWalletRedReceiver = new HideWalletRedReceiver();
        }

        getActivity().registerReceiver(receiver, new IntentFilter(UPDATE_USER));
        getActivity().registerReceiver(scoreReceiver, new IntentFilter(UPDATE_SCORE));
        getActivity().registerReceiver(messageReceiver, new IntentFilter(UPDATE_MESSAGE));
        getActivity().registerReceiver(displayRedReceiver, new IntentFilter(DISPLAY_POINT));
        getActivity().registerReceiver(hideRedReceiver, new IntentFilter(HIDE_POINT));
        getActivity().registerReceiver(walletRedReceiver, new IntentFilter(WALLET_POINT));
        getActivity().registerReceiver(hideWalletRedReceiver, new IntentFilter(HIDE_WALLET_POINT));
        getUserLoginInfo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(USER_KEY, user);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState)
        {user = savedInstanceState.getParcelable(USER_KEY);}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
        if (scoreReceiver != null) {
            getActivity().unregisterReceiver(scoreReceiver);
        }
        if (messageReceiver != null) {
            getActivity().unregisterReceiver(messageReceiver);
        }
        if (displayRedReceiver != null) {
            getActivity().unregisterReceiver(displayRedReceiver);
        }
        if (hideRedReceiver != null) {
            getActivity().unregisterReceiver(hideRedReceiver);
        }
        if (walletRedReceiver != null) {
            getActivity().unregisterReceiver(walletRedReceiver);
        }
        if (hideWalletRedReceiver != null) {
            getActivity().unregisterReceiver(hideWalletRedReceiver);
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String UPDATE_USER = "com.update.user";

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            user = intent.getParcelableExtra(USER_KEY);
            if (user != null) {
                tvName.setText(user.getUserName());
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon(), civHead, options);
            } else {
                tvExit.setVisibility(View.VISIBLE);
                isLogin = true;
                tvName.setText("");
                getUserInfo();
            }
        }
    }

    private ScoreBroadCastReceiver scoreReceiver;
    public static final String UPDATE_SCORE = "com.update.score";

    public class ScoreBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getUserInfo();
        }
    }


    private MessageBroadCastReceiver messageReceiver;
    public static final String UPDATE_MESSAGE = "com.update.message";

    public class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            msg_top_point.setVisibility(View.VISIBLE);
            MyLogUtils.info("右上角消息红点显示");

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
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200) {
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                    if (ule != null) {
                        int vipLevel = ule.getVipLevel();
                        if (vipLevel == 0) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip_nomal);
                        } else if (vipLevel == 1) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip1);
                        } else if (vipLevel == 2) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip2);
                        } else if (vipLevel == 3) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip3);
                        } else if (vipLevel == 4) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip4);
                        } else if (vipLevel == 5) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip5);
                        } else if (vipLevel == 6) {
                            ivVipLevel.setBackgroundResource(R.mipmap.vip6);
                        }
                    }
                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();

    }

    public static final String DISPLAY_POINT = "com.display.point";

    private class DisplayRedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //我的贷款红点显示
            ivRedPoint.setVisibility(View.VISIBLE);
            MyLogUtils.info("我的贷款红点显示");
        }
    }

    public static final String HIDE_POINT = "com.hide.point";

    private class HideRedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ivRedPoint.setVisibility(View.GONE);
            MyLogUtils.info("MineFragment:红点隐藏");
        }
    }

    public static final String WALLET_POINT = "com.wallet.point";

    private class WalletRedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ivWalletRedPoint.setVisibility(View.VISIBLE);
            MyLogUtils.info("MineFragment:我的钱包红点显示");
            context.sendBroadcast(new Intent(MyWalletActivity.DISPLAY));
        }
    }

    public static final String HIDE_WALLET_POINT = "hide_wallet_point";

    private class HideWalletRedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ivWalletRedPoint.setVisibility(View.GONE);
            MyLogUtils.info("MineFragment:我的钱包红点隐藏");
        }
    }
}
