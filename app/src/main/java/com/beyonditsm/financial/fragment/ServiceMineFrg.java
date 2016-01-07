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
import com.beyonditsm.financial.activity.servicer.ServiceDataAct;
import com.beyonditsm.financial.activity.user.CreditPointAct;
import com.beyonditsm.financial.activity.user.HardCreditAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.activity.user.NewWorkAct;
import com.beyonditsm.financial.activity.user.SettingAct;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.db.MessageDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
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
 * 服务者我的界面  15195458338 123456
 * Created by Yang on 2015/11/15 0015.
 */
public class ServiceMineFrg extends BaseFragment {
    @ViewInject(R.id.rlMyData)
    private RelativeLayout Mydata;//我的资料
    @ViewInject(R.id.rlMyTuiJian)
    private RelativeLayout tj;//我的推荐
    @ViewInject(R.id.rlMyCredit)
    private RelativeLayout Credit;//我的贷款
    @ViewInject(R.id.rlSet)
    private RelativeLayout setting;//设置
    @ViewInject(R.id.rlWork)
    private RelativeLayout rlwork;//打工挣钱
    @ViewInject(R.id.tvExit)
    private TextView exit;//注销
    @ViewInject(R.id.tvCredit)//信用分
    private TextView tvCredit;
    @ViewInject(R.id.tvScore)
    private TextView tvScore;//信用分超过多少的人

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;//标题
    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;
    @ViewInject(R.id.msg_top)
    private RelativeLayout msg_top;//右上角消息图标
    @ViewInject(R.id.msg_top_point)
    private ImageView msg_top_point;//右上角消息图标小红点


    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;
    @ViewInject(R.id.tvName)
    private TextView tv_name;

    public static final String SERVANT_INFO = "servant_info";
    public static final String USER_INFO = "user_info";

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private ServiceMineReceiver mineReceiver;
//    private ServantEntity se;
    private String grade;
    private UserLoginEntity ule;
    private UserEntity ue;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.service_mine, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("我");
        rlBack.setVisibility(View.GONE);
        msg_top.setVisibility(View.VISIBLE);
        findServantInfo();
        getUserLoginInfo();
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.rlServiceData, R.id.rlMyTuiJian, R.id.rlSet, R.id.tvExit, R.id.rlMyCredit,
            R.id.rlMyCode, R.id.rlLines,R.id.rlWork,R.id.msg_top})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //我的资料
            case R.id.rlServiceData:
                intent = new Intent(getActivity(), ServiceDataAct.class);
                intent.putExtra(SERVANT_INFO, ue);
//                intent.putExtra(USER_INFO,UserEntity.class);
                getActivity().startActivity(intent);
                break;
            //我的贷款
            case R.id.rlMyCredit:
                intent = new Intent(getActivity(), MyCreditAct.class);
                getActivity().startActivity(intent);
                break;
            //我的信用分
            case R.id.rlMyCode:
                intent = new Intent(getActivity(), CreditPointAct.class);
                if (TextUtils.isEmpty(ue.getCreditScore())){
                    intent.putExtra(CreditPointAct.CREDIT,0);
                }else{
                    intent.putExtra(CreditPointAct.CREDIT,Integer.valueOf(ue.getCreditScore()));
                }
                if(TextUtils.isEmpty(grade))
                    intent.putExtra(CreditPointAct.GRADE,"0");
                else
                    intent.putExtra(CreditPointAct.GRADE, grade);
                getActivity().startActivity(intent);
                break;
            //我的信售额
            case R.id.rlLines:
                intent = new Intent(getActivity(), HardCreditAct.class);
                getActivity().startActivity(intent);
                break;
            //我的推荐
            case R.id.rlMyTuiJian:
                intent = new Intent(getActivity(), MyRecommAct.class);
                intent.putExtra("userLogin",ule);
                getActivity().startActivity(intent);
                break;
            //打工挣钱
            case R.id.rlWork:
                intent = new Intent(getActivity(), NewWorkAct.class);
//                intent.putExtra("id",user.getId());
                intent.putExtra("accountId", ue);
                if (ule != null)
                    intent.putExtra(NewWorkAct.ROLE, ule.getRoleName());
                getActivity().startActivity(intent);
                break;
            //设置
            case R.id.rlSet:
                intent = new Intent(getActivity(), SettingAct.class);
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
                        MessageDao.deleteAllMes();
//                        FriendDao.deleteAllMes();
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
               /* RequestManager.getCommManager().toLoginOut(new RequestManager.CallBack() {
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
                SpUtils.clearSp(getContext());
                intent = new Intent(getActivity(), LoginAct.class);
                getActivity().startActivity(intent);
                getActivity().finish();*/
                break;
            case R.id.msg_top:
                Intent msgintent = new Intent(getActivity(), MessageActivity.class);
                getActivity().startActivity(msgintent);
                break;
        }
    }
    //获取服务者信息
    private void findServantInfo() {

        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                ue = rd.getData();
                if (ue != null) {
                    if (ue.getServantId() != null) {
                        if (ue.getAccountName() != null) {
                            tv_name.setText(ue.getAccountName());
                        }
                        if (!TextUtils.isEmpty(ue.getCreditScore())){
                            tvCredit.setText(ue.getCreditScore());
                        }
                        if(TextUtils.isEmpty(ue.getCreditScore())){
                            getScorePerson("0");
                        }else{
                            getScorePerson(ue.getCreditScore()+"");
                        }

                            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + ue.getHeadIcon(), civHead, options);

                        if (RongIM.getInstance() != null) {
                            if(!TextUtils.isEmpty(ue.getAccountId())){
                                RongIM.getInstance().setCurrentUserInfo(new UserInfo(ue.getAccountId(), ue.getUserName(),
                                        Uri.parse(IFinancialUrl.BASE_IMAGE_URL + ue.getHeadIcon())));
                                RongIM.getInstance().setMessageAttachedUserInfo(true);
                                FriendBean bean = new FriendBean();
                                bean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + ue.getHeadIcon());
                                bean.setUserName(ue.getUserName());
                                bean.setUserId(ue.getAccountId());
                                FriendDao.saveMes(bean);
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }
    //获取信用分超过多少的人
    private void getScorePerson(String score){
        RequestManager.getUserManager().getScorePer(score, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                grade = object.optString("data");
                tvScore.setText(grade + "%");

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo(){
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
    public void onStart() {
        super.onStart();
        if (mineReceiver == null) {
            mineReceiver = new ServiceMineReceiver();
        }
        if(messageReceiver==null){
            messageReceiver=new MessageBroadCastReceiver();
        }
        getActivity().registerReceiver(mineReceiver, new IntentFilter(UPDATE_SERVANT));
        getActivity().registerReceiver(messageReceiver,new IntentFilter(MineFragment.UPDATE_MESSAGE));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mineReceiver != null) {
            getActivity().unregisterReceiver(mineReceiver);
        }
        if(messageReceiver!=null){
            getActivity().unregisterReceiver(messageReceiver);
        }
    }

    public static final String UPDATE_SERVANT = "com.update.servant";

    public class ServiceMineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ue = intent.getParcelableExtra(USER_INFO);
//            UserEntity ue = intent.getParcelableExtra(USER_INFO);

            if (ue!=null&&!TextUtils.isEmpty(ue.getUserName())) {
                tv_name.setText(ue.getUserName());
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + ue.getHeadIcon(), civHead, options);

            }
        }
    }

    private MessageBroadCastReceiver messageReceiver;
    public class MessageBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            msg_top_point.setVisibility(View.VISIBLE);
        }
    }
}
