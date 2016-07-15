package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MessageActivity;
import com.beyonditsm.financial.activity.manager.ManagerUpdateAct;
import com.beyonditsm.financial.activity.manager.OrderAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.SettingAct;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.db.MessageDao;
import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.CreditManagerEntity;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
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

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 信贷经理我的界面  15206127511  123456
 * Created by Yang on 2015/11/15 0015.
 */
public class ManagerMineFrg extends BaseFragment {
    @ViewInject(R.id.msg_top)
    private RelativeLayout msg_top;//右上角消息图标
    @ViewInject(R.id.msg_top_point)
    private ImageView msg_top_point;//右上角消息图标小红点
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;//标题
    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;
    @ViewInject(R.id.tvName)
    private TextView tvCreditManaName;
    private CreditReceiver creditReceiver;

    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private CreditManager cm;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_manager_mine, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rlBack.setVisibility(View.GONE);
        msg_top.setVisibility(View.VISIBLE);
        tvTitle.setText("我");
        getCurrentCreditManagerDetail();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (creditReceiver == null) {
            creditReceiver = new CreditReceiver();
        }

        if(messageReceiver==null){
            messageReceiver=new MessageBroadCastReceiver();
        }
        getActivity().registerReceiver(creditReceiver, new IntentFilter(UPDATE_CREDIT));
        getActivity().registerReceiver(messageReceiver,new IntentFilter(MineFragment.UPDATE_MESSAGE));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (creditReceiver != null) {
            getActivity().unregisterReceiver(creditReceiver);
        }
        if(messageReceiver!=null){
            getActivity().unregisterReceiver(messageReceiver);
        }
    }

    @OnClick({R.id.rlMyOrder, R.id.rlMyChat, R.id.rlSet, R.id.tvExit, R.id.rlManaData,R.id.msg_top})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //订单管理
            case R.id.rlMyOrder:
                intent = new Intent(getActivity(), OrderAct.class);
                getActivity().startActivity(intent);
                break;
            //我的沟通
            case R.id.rlMyChat:

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
                        Set<String> set= new HashSet<>();
                        JPushInterface.setAliasAndTags(getActivity(), "", set, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                            }
                        });
                        JPushInterface.clearAllNotifications(getActivity());

                        MessageDao.deleteAllMes();
//                        FriendDao.deleteAllMes();
                        SpUtils.clearSp(getContext());
                        SpUtils.clearOrderId(getContext());
                        Intent intent = new Intent(getActivity(), LoginAct.class);
                        intent.putExtra(LoginAct.LOGIN_TYPE,1);
                        getActivity().startActivity(intent);
                        getActivity().finish();
//                        isLogin = false;
//                        tvName.setText("去登录");
//                        tvExit.setVisibility(View.GONE);
//                        civHead.setImageResource(R.mipmap.ava_default);
                    }
                }).setNegativeButton("取消", null).show();
              /*  RequestManager.getCommManager().toLoginOut(new RequestManager.CallBack() {
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
            //我的资料
            case R.id.rlManaData:
                intent = new Intent(getActivity(), ManagerUpdateAct.class);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, cm);
                getActivity().startActivity(intent);
                break;
            case R.id.msg_top:
                msg_top_point.setVisibility(View.GONE);
                Intent msgintent = new Intent(getActivity(), MessageActivity.class);
                getActivity().startActivity(msgintent);
                break;
        }
    }

    public void getCurrentCreditManagerDetail() {
        RequestManager.getMangManger().currentCreditManagerDetail(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<CreditManagerEntity> rd = (ResultData<CreditManagerEntity>) GsonUtils.json(result, CreditManagerEntity.class);
                CreditManagerEntity cme = rd.getData();
                if (cme != null) {
                    cm = cme.getCreditManager();
                    tvCreditManaName.setText(cme.getUsername());
                    ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL+cm.getRcHeadPic(),civHead,options);
                    if (RongIM.getInstance() != null) {
                        if (!TextUtils.isEmpty(cm.getAccountId())) {
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(cm.getAccountId(), cme.getUsername(),
                                    null));
                            RongIM.getInstance().setMessageAttachedUserInfo(true);
                            FriendBean bean = new FriendBean();
//                            bean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + cm.getHeadIcon());
                            bean.setUserName(cme.getUsername());
                            bean.setUserId(cm.getAccountId());
                            FriendDao.saveMes(bean);
                        }
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                if(getActivity()!=null)
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }

    public static final String UPDATE_CREDIT = "com.update.credit";
    public static final String CREDIT_DATAS = "credit_datas";

    public class CreditReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            cm = intent.getParcelableExtra(CREDIT_DATAS);
            tvCreditManaName.setText(cm.getManaName());
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
