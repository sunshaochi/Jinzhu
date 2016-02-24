package com.beyonditsm.financial.activity.servicer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.user.AddressBookAct;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.fragment.CreditFragment;
import com.beyonditsm.financial.fragment.FriendFrg;
import com.beyonditsm.financial.fragment.HomeFragment;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 服务者主act    15195458338   123456
 * Created by Yang on 2015/11/13 0013.
 */
public class ServiceMainAct extends BaseActivity{
    private ImageView ivMyWallet;//钱包
    private TextView tvMyWallet;
    private ImageView ivCredit;//贷款
    private TextView tvCredit;
    private ImageView ivMes;//沟通
    private ImageView iv_tag;//沟通有未读消息小红点
    private TextView tvMes;
    private ImageView ivMine;//我的
    private TextView tvMine;
    private TextView title_chat;
    private TextView title_friend;
    private RelativeLayout main_title;
    private RelativeLayout add_friend;//添加通讯录好友

    private FragmentManager manager;
    private Fragment myWalletfrg, creditFgt, friendFgt, mineFgt;//钱包，沟通，我的

    private ConversationListFragment listFragment;
    private int title = 1;
    /**
     * 按两次退出键时间小于2秒退出
     */
    private final static long WAITTIME = 2000;
    private long touchTime = 0;
    private List<UserEntity> friends = new ArrayList<>();
    private List<FriendBean> friendBeans = new ArrayList<>();
    private Gson gson = new Gson();

    /**/
    private void assignViews() {
        ivMyWallet = (ImageView) findViewById(R.id.ivMyWallet);
        tvMyWallet = (TextView) findViewById(R.id.tvMyWallet);
        ivCredit = (ImageView) findViewById(R.id.ivCredit);
        tvCredit = (TextView) findViewById(R.id.tvCredit);
        ivMes = (ImageView) findViewById(R.id.ivMes);
        tvMes = (TextView) findViewById(R.id.tvMes);
        iv_tag = (ImageView) findViewById(R.id.iv_tag);
        ivMine = (ImageView) findViewById(R.id.ivMine);
        tvMine = (TextView) findViewById(R.id.tvMine);
        title_chat = (TextView) findViewById(R.id.title_chat);
        title_friend = (TextView) findViewById(R.id.title_friend);
        main_title = (RelativeLayout) findViewById(R.id.main_title);
        add_friend = (RelativeLayout) findViewById(R.id.add_friend);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.servicemainact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        assignViews();
        manager = getSupportFragmentManager();
        setTabSelection(0);
        setCheckItem(0);
//        RongIM.setUserInfoProvider(this, true);
        String token = SpUtils.getToken(ServiceMainAct.this);
        if (!TextUtils.isEmpty(token)) {
            findServantInfo();
//            getFriendList();
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener
                        (new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
            }
        }
    }

    //获取服务者信息
    private void findServantInfo() {

        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                UserEntity ue = rd.getData();
                if (ue != null) {
                    if (ue.getServantId() != null) {
                        if (RongIM.getInstance() != null) {
                            if (!TextUtils.isEmpty(ue.getAccountId())) {
                                RongIM.getInstance().setCurrentUserInfo(new UserInfo(ue.getAccountId(), ue.getUserName(),
                                        Uri.parse(IFinancialUrl.BASE_IMAGE_URL + ue.getHeadIcon())));
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(ue.getAccountId(), ue.getUserName(),
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
            }
        });
    }

//    private void getFriendList() {
//        //获取好友列表
//        RequestManager.getCommManager().getFriendList(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//
//                JSONObject obj = new JSONObject(result);
//                JSONArray array = obj.getJSONArray("data");
//                friends = gson.fromJson(array.toString(), new TypeToken<List<UserEntity>>() {
//                }.getType());
//                if (friends != null && friends.size() > 0) {
//                    for (int i = 0; i < friends.size(); i++) {
//                        FriendBean friendBean = new FriendBean();
//                        friendBean.setUserId(friends.get(i).getAccountId());
//                        friendBean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + friends.get(i).getHeadIcon());
//                        if (TextUtils.isEmpty(friends.get(i).getUserName()))
//                            friendBean.setUserName(friends.get(i).getAccountName());
//                        else
//                            friendBean.setUserName(friends.get(i).getUserName());
//                        FriendDao.saveMes(friendBean);
//                    }
//                    friendBeans = FriendDao.findfriend();
//                }
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }

    /**
     * 点击事件
     *
     * @param
     */
    @OnClick({ R.id.llMyWallet,R.id.llCredit, R.id.llChat, R.id.llMine, R.id.add_friend, R.id.title_chat,
            R.id.title_friend})
    public void toClick(View v) {
        switch (v.getId()) {
//            //首页
            case R.id.llMyWallet:
                setAllTabNor();
                setTabSelection(0);
                setCheckItem(0);
                break;
            //贷款
            case R.id.llCredit:
                setAllTabNor();
                setTabSelection(3);
                setCheckItem(3);
                break;
            //沟通
            case R.id.llChat:
                switch (title) {
                    case 1:
                        setAllTabNor();
                        setTabSelection(1);
                        setCheckItem(1);
                        setTitleTabNor();
                        setTitleCheckItem(0);
                        break;
                    case 2:
                        setAllTabNor();
                        setTabSelection(4);
                        setCheckItem(1);
                        setTitleTabNor();
                        setTitleCheckItem(1);
                        break;
                }
//                setAllTabNor();
//                setTabSelection(1);
//                setCheckItem(1);
                break;
            //我的
            case R.id.llMine:
                setAllTabNor();
                setTabSelection(2);
                setCheckItem(2);
                break;
            //顶部沟通按钮
            case R.id.title_chat:
                title = 1;
                setAllTabNor();
                setTabSelection(1);
                setCheckItem(1);
                setTitleTabNor();
                setTitleCheckItem(0);
                break;
            //顶部好友按钮
            case R.id.title_friend:
                title = 2;
                setAllTabNor();
                setTabSelection(4);
                setCheckItem(1);
                setTitleTabNor();
                setTitleCheckItem(1);
                break;
            //添加好友
            case R.id.add_friend:
                Intent intent = new Intent(ServiceMainAct.this, AddressBookAct.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 标题tab全部切换普通
     */
    private void setTitleTabNor() {
        title_chat.setBackgroundResource(R.drawable.order_titletv_leftbg);
        title_chat.setTextColor(Color.parseColor("#ffffff"));
        title_friend.setBackgroundResource(R.drawable.order_titletv_rightbg);
        title_friend.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setTitleCheckItem(int position) {
        switch (position) {
            case 0:
                title_chat.setBackgroundResource(R.drawable.white_bg);
                title_chat.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case 1:
                title_friend.setBackgroundResource(R.drawable.white_bg);
                title_friend.setTextColor(getResources().getColor(R.color.main_color));
                break;
        }
    }

    /**
     * 底部全部切换普通
     */
    private void setAllTabNor() {
        ivMyWallet.setBackgroundResource(R.mipmap.tab_package_nor);
        tvMyWallet.setTextColor(Color.parseColor("#666666"));
        ivCredit.setBackgroundResource(R.mipmap.tab_credit_nor);
        tvCredit.setTextColor(Color.parseColor("#666666"));
        ivMes.setBackgroundResource(R.mipmap.tab_chat_nor);
        tvMes.setTextColor(Color.parseColor("#666666"));
        ivMine.setBackgroundResource(R.mipmap.tab_me_nor);
        tvMine.setTextColor(Color.parseColor("#666666"));
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= WAITTIME) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            if(RongIM.getInstance()!=null)
                RongIM.getInstance().disconnect();
//            finish();
            try {
                Thread.sleep(500);
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收未读消息的监听器。
     */
    private class MyReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         * @param count 未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {
            if (count > 0) {
                iv_tag.setVisibility(View.VISIBLE);
            } else {
                iv_tag.setVisibility(View.GONE);
            }
            LogUtils.i("未读消息数" + count);
        }
    }

    /**
     * 选中
     *
     * @param position
     */
    private void setTabSelection(final int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                main_title.setVisibility(View.GONE);
                if (myWalletfrg == null) {
                    myWalletfrg = new HomeFragment();
                    transaction.add(R.id.main_frame, myWalletfrg);
                } else {
                    transaction.show(myWalletfrg);
                }
                break;
            case 1:
                main_title.setVisibility(View.VISIBLE);
//                if (chatFrg == null) {
                listFragment = ConversationListFragment.getInstance();
                Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                        .build();
                listFragment.setUri(uri);
                transaction.add(R.id.main_frame, listFragment);
//                } else {
//                    transaction.show(listFragment);
//                }
                break;
            case 2:
                main_title.setVisibility(View.GONE);
                if (mineFgt == null) {
                    mineFgt = new ServiceMineFrg();
                    transaction.add(R.id.main_frame, mineFgt);
                } else {
                    transaction.show(mineFgt);
                }
                break;
            case 3:
                main_title.setVisibility(View.GONE);
                if (creditFgt == null) {
                    creditFgt = new CreditFragment();
                    transaction.add(R.id.main_frame, creditFgt);
                } else {
                    transaction.show(creditFgt);
                }
                break;
            case 4:
                main_title.setVisibility(View.VISIBLE);
                if (friendFgt == null) {
                    friendFgt = new FriendFrg();
                    transaction.add(R.id.main_frame, friendFgt);
                } else {
                    transaction.show(friendFgt);
                }
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction transaction) {
//        if (myWalletfrg != null) {
//            transaction.hide(myWalletfrg);
//        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        if (mineFgt != null) {
            transaction.hide(mineFgt);
        }
        if (creditFgt != null) {
            transaction.hide(creditFgt);
        }
        if (friendFgt != null) {
            transaction.hide(friendFgt);
        }
    }

    private void setCheckItem(int position) {
        switch (position) {
//            case 0:
//                ivMyWallet.setBackgroundResource(R.mipmap.tab_package_h);
//                tvMyWallet.setTextColor(Color.parseColor("#f49626"));
//                break;
            case 1:
                ivMes.setBackgroundResource(R.mipmap.tab_chat_h);
                tvMes.setTextColor(Color.parseColor("#f49626"));
                break;
            case 2:
                ivMine.setBackgroundResource(R.mipmap.tab_me_h);
                tvMine.setTextColor(Color.parseColor("#f49626"));
                break;
            case 3:
                ivCredit.setBackgroundResource(R.mipmap.tab_credit_h);
                tvCredit.setTextColor(Color.parseColor("#f49626"));
                break;
        }
    }

    @Override
    protected void onStart() {
        if (myReceiver == null)
            myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATATAB);
        this.registerReceiver(myReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        this.unregisterReceiver(myReceiver);
        myReceiver = null;
        super.onDestroy();
    }

    private MyReceiver myReceiver;
    public final static String UPDATATAB = "com.beyond.servicemain";
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String token = SpUtils.getToken(ServiceMainAct.this);
            if (!TextUtils.isEmpty(token)) {
//                getFriendList();
                if (RongIM.getInstance() != null) {
                    RongCloudEvent.getInstance().getFriendList();
                    RongIM.getInstance().setOnReceiveUnreadCountChangedListener
                            (new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
                }
            }
        }
    }

//    @Override
//    public UserInfo getUserInfo(String s) {
//        if (friendBeans != null && friendBeans.size() > 0) {
//            //增强for把所有的用户信息 return 到融云服务端
//            for (FriendBean friend : friendBeans) {
//                //判断返回的userId
//                if (friend.getUserId().equals(s)) {
////                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.getUserId(), friend.getUserName(),
////                            Uri.parse(friend.getUserHead())));
//                    return new UserInfo(friend.getUserId(), friend.getUserName(),
//                            Uri.parse(friend.getUserHead()));
//                }
//            }
//        }
//        return null;
//    }
}
