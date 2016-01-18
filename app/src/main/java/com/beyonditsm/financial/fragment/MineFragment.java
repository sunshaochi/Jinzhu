package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MessageActivity;
import com.beyonditsm.financial.activity.user.CreditPointAct;
import com.beyonditsm.financial.activity.user.HardCreditAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.activity.user.NewWorkAct;
import com.beyonditsm.financial.activity.user.SettingAct;
import com.beyonditsm.financial.activity.user.UpdateAct;
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
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
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
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private UserLoginEntity ule;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("我");
        rlBack.setVisibility(View.GONE);
        msg_top.setVisibility(View.VISIBLE);
        if ("".equals(SpUtils.getRoleName(getActivity()))) {
            isLogin = false;
            tvName.setText("去登录");
            tvExit.setVisibility(View.GONE);
        } else {
            isLogin = true;
            tvExit.setVisibility(View.VISIBLE);
//            getUserInfo();
            getUserLoginInfo();
        }
    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.rlMyCode, R.id.rlRecomm, R.id.rlLines, R.id.rlMyCredit, R.id.rlSet, R.id.tvExit,
            R.id.rlWork, R.id.rlMyData,R.id.msg_top,R.id.rlWallet})
    public void toClick(View v) {
        Intent intent = null;
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
//                intent = new Intent(getActivity(), CultivationAct.class);
                intent = new Intent(getActivity(), HardCreditAct.class);
                getActivity().startActivity(intent);
                break;
            //我的贷款
            case R.id.rlMyCredit:
                intent = new Intent(getActivity(), MyCreditAct.class);
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
                intent = new Intent(getActivity(), NewWorkAct.class);
//                intent.putExtra("id",user.getId());
                intent.putExtra("accountId", user);
                if (ule != null)
                    intent.putExtra(NewWorkAct.ROLE, ule.getRoleName());
                getActivity().startActivity(intent);
                break;
            //我的钱包
            case R.id.rlWallet:
                intent=new Intent(getActivity(), MyWalletActivity.class);
                intent.putExtra("userLogin",ule);
                intent.putExtra("userInfo",user);
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
                                if (RongIM.getInstance() != null) {
                                    RongIM.getInstance().logout();
                                }
                            }

                            @Override
                            public void onError(int status, String msg) {

                            }
                        });
                        Set<String> set=new HashSet<String>();
                        JPushInterface.setAliasAndTags(getActivity(), "", set, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                            }
                        });
                        JPushInterface.clearAllNotifications(getActivity());
//                        FriendDao.deleteAllMes();
                        MessageDao.deleteAllMes();
                        SpUtils.clearSp(getContext());
                        Intent intent = new Intent(getActivity(), LoginAct.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
//                        isLogin = false;
//                        tvName.setText("去登录");
//                        tvExit.setVisibility(View.GONE);
//                        civHead.setImageResource(R.mipmap.ava_default);
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.msg_top://消息
                msg_top_point.setVisibility(View.GONE);
                Intent msgintent = new Intent(getActivity(), MessageActivity.class);
                getActivity().startActivity(msgintent);
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {
                    tvName.setText(user.getUserName());
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
                    if (RongIM.getInstance() != null) {
                        if(!TextUtils.isEmpty(user.getAccountId())){
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
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 获取信用分超过多少的用户
     *
     * @param score
     */
    private void getScorePer(String score) {
        RequestManager.getUserManager().getScorePer(score, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                grade = object.optString("data");
                double dGrade=Double.valueOf(grade);
                tvGrade.setText((int)dGrade + "%");

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

        if (scoreReceiver==null){
            scoreReceiver=new ScoreBroadCastReceiver();
        }

        if(messageReceiver==null){
            messageReceiver=new MessageBroadCastReceiver();
        }
        getActivity().registerReceiver(receiver, new IntentFilter(UPDATE_USER));
        getActivity().registerReceiver(scoreReceiver,new IntentFilter(UPDATE_SCORE));
        getActivity().registerReceiver(messageReceiver,new IntentFilter(UPDATE_MESSAGE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
        if(scoreReceiver!=null){
            getActivity().unregisterReceiver(scoreReceiver);
        }
        if(messageReceiver!=null){
            getActivity().unregisterReceiver(messageReceiver);
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
    public static final String UPDATE_SCORE="com.update.score";

    public class ScoreBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            getUserInfo();
        }
    }


    private MessageBroadCastReceiver messageReceiver;
    public static final String UPDATE_MESSAGE="com.update.message";
    public class MessageBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            msg_top_point.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                ule = rd.getData();
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
}
