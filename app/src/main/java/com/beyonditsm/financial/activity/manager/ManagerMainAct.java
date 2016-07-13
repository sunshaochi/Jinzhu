package com.beyonditsm.financial.activity.manager;

import android.annotation.SuppressLint;
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
import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.CreditManagerEntity;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.fragment.FriendFrg;
import com.beyonditsm.financial.fragment.GrabOrderFrg;
import com.beyonditsm.financial.fragment.ManagerMineFrg;
import com.beyonditsm.financial.fragment.OrderFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 信贷经理主界面  15206127511  123456
 * Created by Yang on 2015/11/16 0016.
 */
public class ManagerMainAct extends BaseActivity{
    private ImageView ivQd;
    private TextView tvQd;
    private ImageView ivDd;
    private TextView tvDd;
    private ImageView ivMes;
    private TextView tvMes;
    private ImageView iv_tag;//沟通有未读消息小红点
    private ImageView ivMine;
    private TextView tvMine;
    private TextView title_chat;
    private TextView title_friend;
    private RelativeLayout main_title;

    private Fragment QdFrg, DdFrg, friendFgt, MineFrg;//抢单  订单   聊天  我的
    private ConversationListFragment listFragment;
    private int title = 1;
    /**
     * 按两次退出键时间小于2秒退出
     */
    private final static long WAITTIME = 2000;
    private long touchTime = 0;

    private void assignViews() {
        ivQd = (ImageView) findViewById(R.id.ivQd);
        tvQd = (TextView) findViewById(R.id.tvQd);
        ivDd = (ImageView) findViewById(R.id.ivdd);
        tvDd = (TextView) findViewById(R.id.tvdd);
        ivMes = (ImageView) findViewById(R.id.ivMes);
        tvMes = (TextView) findViewById(R.id.tvMes);
        iv_tag = (ImageView) findViewById(R.id.iv_tag);
        ivMine = (ImageView) findViewById(R.id.ivMine);
        tvMine = (TextView) findViewById(R.id.tvMine);
        title_chat = (TextView) findViewById(R.id.title_chat);
        title_friend = (TextView) findViewById(R.id.title_friend);
        main_title = (RelativeLayout) findViewById(R.id.main_title);
    }


    private FragmentManager fragmentManager;

    @Override
    public void setLayout() {
        setContentView(R.layout.managermainact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        assignViews();
        fragmentManager = getSupportFragmentManager();
        int position = getIntent().getIntExtra("position", 0);
        if (position==1){
            setAllTabNor();
            setTabSelection(position);
            setCheckItem(position);
        }
        setTabSelection(0);
        setCheckItem(0);
//        RongIM.setUserInfoProvider(this, true);
        String token = SpUtils.getToken(ManagerMainAct.this);
        if (!TextUtils.isEmpty(token)) {
            getCurrentCreditManagerDetail();
//            getFriendList();
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener
                        (new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
            }
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
                    CreditManager cm = cme.getCreditManager();
                    if (RongIM.getInstance() != null) {
                        if (!TextUtils.isEmpty(cm.getAccountId())) {
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(cm.getAccountId(), cme.getUsername(),
                                    null));
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(cm.getAccountId(), cme.getUsername(),
                                    null));
                            RongIM.getInstance().setMessageAttachedUserInfo(true);
                            FriendBean bean = new FriendBean();
                            bean.setUserName(cme.getUsername());
                            bean.setUserId(cm.getAccountId());
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

    @OnClick({R.id.llQd, R.id.lldd, R.id.llChat, R.id.llMine, R.id.add_friend, R.id.title_chat,
            R.id.title_friend})
    public void todo(View view) {
        switch (view.getId()) {
            case R.id.llQd:
                setAllTabNor();
                setTabSelection(0);
                setCheckItem(0);
                break;
            case R.id.lldd:
                setAllTabNor();
                setTabSelection(1);
                setCheckItem(1);
                break;
            case R.id.llChat:
                switch (title) {
                    case 1:
                        setAllTabNor();
                        setTabSelection(2);
                        setCheckItem(2);
                        setTitleTabNor();
                        setTitleCheckItem(0);
                        break;
                    case 2:
                        setAllTabNor();
                        setTabSelection(4);
                        setCheckItem(2);
                        setTitleTabNor();
                        setTitleCheckItem(1);
                        break;
                }
//                setAllTabNor();
//                setTabSelection(2);
//                setCheckItem(2);
                break;
            case R.id.llMine:
                setAllTabNor();
                setTabSelection(3);
                setCheckItem(3);
                break;
            case R.id.add_friend:
                Intent intent=new Intent(ManagerMainAct.this, AddressBookAct.class);
                startActivity(intent);
                break;
            //顶部沟通按钮
            case R.id.title_chat:
                title = 1;
                setAllTabNor();
                setTabSelection(2);
                setCheckItem(2);
                setTitleTabNor();
                setTitleCheckItem(0);
                break;
            //顶部好友按钮
            case R.id.title_friend:
                title = 2;
                setAllTabNor();
                setTabSelection(4);
                setCheckItem(2);
                setTitleTabNor();
                setTitleCheckItem(1);
                break;
        }
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

    @SuppressLint("CommitTransaction")
    private void setTabSelection(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (position) {
            case 0:
                main_title.setVisibility(View.GONE);
                if (QdFrg == null) {
                    QdFrg = new GrabOrderFrg();
                    fragmentTransaction.add(R.id.managermain_fl, QdFrg);
                } else {
                    fragmentTransaction.show(QdFrg);
                }
                break;
            case 1:
                main_title.setVisibility(View.GONE);
                if (DdFrg == null) {
                    DdFrg = new OrderFragment();
                    fragmentTransaction.add(R.id.managermain_fl, DdFrg);
                } else {
                    fragmentTransaction.show(DdFrg);
                }
                break;
            case 2:
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
                fragmentTransaction.add(R.id.managermain_fl, listFragment);
//                } else {
//                    fragmentTransaction.show(chatFrg);
//                }
                break;
            case 3:
                main_title.setVisibility(View.GONE);
                if (MineFrg == null) {
                    MineFrg = new ManagerMineFrg();
                    fragmentTransaction.add(R.id.managermain_fl, MineFrg);
                } else {
                    fragmentTransaction.show(MineFrg);
                }
                break;
            case 4:
                main_title.setVisibility(View.VISIBLE);
                if (friendFgt == null) {
                    friendFgt = new FriendFrg();
                    fragmentTransaction.add(R.id.managermain_fl, friendFgt,"mana");
                } else {
                    fragmentTransaction.show(friendFgt);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (QdFrg != null) {
            transaction.hide(QdFrg);
        }
        if (DdFrg != null) {
            transaction.hide(DdFrg);
        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        if (MineFrg != null) {
            transaction.hide(MineFrg);
        }
        if (friendFgt != null) {
            transaction.hide(friendFgt);
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

    @SuppressWarnings("deprecation")
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
        ivQd.setBackgroundResource(R.mipmap.tab_package_nor);
        tvQd.setTextColor(Color.parseColor("#666666"));
        ivDd.setBackgroundResource(R.mipmap.tab_order_nor);
        tvDd.setTextColor(Color.parseColor("#666666"));
        ivMes.setBackgroundResource(R.mipmap.tab_chat_nor);
        tvMes.setTextColor(Color.parseColor("#666666"));
        ivMine.setBackgroundResource(R.mipmap.tab_me_nor);
        tvMine.setTextColor(Color.parseColor("#666666"));
    }

    private void setCheckItem(int position) {
        switch (position) {
            case 0:
                ivQd.setBackgroundResource(R.mipmap.tab_package_h);
                tvQd.setTextColor(Color.parseColor("#f49626"));
                break;
            case 1:
                ivDd.setBackgroundResource(R.mipmap.tab_order_h);
                tvDd.setTextColor(Color.parseColor("#f49626"));
                break;
            case 2:
                ivMes.setBackgroundResource(R.mipmap.tab_chat_h);
                tvMes.setTextColor(Color.parseColor("#f49626"));
                break;
            case 3:
                ivMine.setBackgroundResource(R.mipmap.tab_me_h);
                tvMine.setTextColor(Color.parseColor("#f49626"));
                break;
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
    public final static String UPDATATAB = "com.beyond.managermain";
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String token = SpUtils.getToken(ManagerMainAct.this);
            if (!TextUtils.isEmpty(token)) {
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
