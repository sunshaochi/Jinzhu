package com.beyonditsm.financial.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.user.AddressBookAct;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.fragment.CreditFragment;
import com.beyonditsm.financial.fragment.FriendFrg;
import com.beyonditsm.financial.fragment.HomeFragment;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GeneralUtils;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity {

    private ImageView ivMyCredit;//我的信用
    private TextView tvMyCredit;
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
    private Fragment myCreditFgt, creditFgt, friendFgt, mineFgt;//我的信用，贷款，朋友，我的
    private ConversationListFragment listFragment;
    private ProgressDialog mDialog;
    private List<UserEntity> friends = new ArrayList<>();
    private List<FriendBean> friendBeans = new ArrayList<>();
    /**
     * 按两次退出键时间小于2秒退出
     */
    private final static long WAITTIME = 2000;
    private long touchTime = 0;
    /**
     * 会话列表的fragment
     */
    private Fragment mConversationFragment = null;
    private int title = 1;
    private Gson gson = new Gson();

    private int game_type;

    private GeneralUtils gUtils;
    private ImageView ivRedPoint;
    private DisplayRedPointReceiver displayRedReceiver;
    private HideRedPointReceiver hideRedPointReceiver;

    private static final int READ_CONTACTS_REQUEST_CODE = 2;

    /**/
    private void assignViews() {
        ivMyCredit = (ImageView) findViewById(R.id.ivMyCredit);
        tvMyCredit = (TextView) findViewById(R.id.tvMyCredit);
        ivCredit = (ImageView) findViewById(R.id.ivCredit);
        tvCredit = (TextView) findViewById(R.id.tvCredit);
        ivMes = (ImageView) findViewById(R.id.ivMes);
        tvMes = (TextView) findViewById(R.id.tvMes);
        ivMine = (ImageView) findViewById(R.id.ivMine);
        iv_tag = (ImageView) findViewById(R.id.iv_tag);
        tvMine = (TextView) findViewById(R.id.tvMine);
        title_chat = (TextView) findViewById(R.id.title_chat);
        title_friend = (TextView) findViewById(R.id.title_friend);
        main_title = (RelativeLayout) findViewById(R.id.main_title);
        add_friend = (RelativeLayout) findViewById(R.id.add_friend);
        ivRedPoint = (ImageView) findViewById(R.id.ivMs);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        getCity();
//        RongIM.setUserInfoProvider(this, true);
        gUtils = new GeneralUtils();


        //注册EventBus
        EventBus.getDefault().register(this);
        assignViews();


        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
        if ("".equals(orderId)) {
            ivRedPoint.setVisibility(View.GONE);
        } else {
            ivRedPoint.setVisibility(View.VISIBLE);
        }
        manager = getSupportFragmentManager();
//        String def = getIntent().getStringExtra("def");
//        if (!TextUtils.isEmpty(def)&&Integer.valueOf(def)==0){
//            setTabSelection(Integer.valueOf(def));
//            setCheckItem(Integer.valueOf(def));
//        }
        setTabSelection(0);
        setCheckItem(0);
        mDialog = new ProgressDialog(this);
        String token = SpUtils.getToken(MainActivity.this);
        if (!TextUtils.isEmpty(token)) {
            getUserInfo();
//            getFriendList();
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener
                        (new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
            }
        }

//        if(NetUtil.isWifiConnection(getApplicationContext())){
        gUtils.toVersion(MainActivity.this, FinancialUtil.getAppVer(MainActivity.this), 1);
//        }

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                UserEntity user = rd.getData();
                if (user != null) {
                    if (RongIM.getInstance() != null) {
                        if (!TextUtils.isEmpty(user.getAccountId())) {
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(user.getAccountId(), user.getUserName(),
                                    Uri.parse(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon())));
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getAccountId(), user.getUserName(),
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
     * 获取好友列表
     */
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
    @OnClick({R.id.llMyCredit, R.id.llCredit, R.id.llChat, R.id.llMine, R.id.add_friend, R.id.title_chat
            , R.id.title_friend})
    public void toClick(View v) {
        switch (v.getId()) {
            //我的信用
            case R.id.llMyCredit:
                setAllTabNor();
                setTabSelection(0);
                setCheckItem(0);
                break;
            //贷款
            case R.id.llCredit:
                setAllTabNor();
                setTabSelection(1);
                setCheckItem(1);
                break;
            //沟通
            case R.id.llChat:
                if (TextUtils.isEmpty(SpUtils.getRoleName(MainActivity.this).toString())) {
                    gotoActivity(LoginAct.class, false);
                } else {
                    switch (title) {
                        case 1:
                            setAllTabNor();
                            setTitleTabNor();
                            setTabSelection(2);
                            setTitleCheckItem(0);
                            setCheckItem(2);
                            break;
                        case 2:
                            setAllTabNor();
                            setTitleTabNor();
                            setTabSelection(4);
                            setTitleCheckItem(1);
                            setCheckItem(2);
                            break;
                    }
                }
                break;
            //我的
            case R.id.llMine:
                if (TextUtils.isEmpty(SpUtils.getRoleName(MainActivity.this).toString())) {
                    gotoActivity(LoginAct.class, false);
                } else {
                    setAllTabNor();
                    setTabSelection(3);
                    setCheckItem(3);
                }
                break;
            //添加通讯录好友
            case R.id.add_friend:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    findContactsPermission();
                 if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, AddressBookAct.class);
                    startActivity(intent);
                } else {
                    MyToastUtils.showShortToast(getApplicationContext(), "请在设置中勾选应用的通讯录权限");
                }

                break;
            //顶部沟通按钮
            case R.id.title_chat:
                title = 1;
                setAllTabNor();
                setTitleTabNor();
                setTabSelection(2);
                setTitleCheckItem(0);
                setCheckItem(2);
                break;
            //顶部好友按钮
            case R.id.title_friend:
                title = 2;
                setAllTabNor();
                setTitleTabNor();
                setTabSelection(4);
                setTitleCheckItem(1);
                setCheckItem(2);
                break;
        }
    }

    //6.0系统（API23）下检查并申请权限
    private void findContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //申请读取联系人权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_REQUEST_CODE);
        } else {
            Intent intent = new Intent(MainActivity.this, AddressBookAct.class);
            startActivity(intent);
        }
    }

    //6.0系统用户选择允许或者取消之后回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_CONTACTS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//权限授予
                Intent intent = new Intent(MainActivity.this, AddressBookAct.class);
                startActivity(intent);
            } else {//权限否认
                MyToastUtils.showShortToast(getApplicationContext(), "没有权限");
            }
        }
    }

    /**
     * 底部全部切换普通
     */
    private void setAllTabNor() {
        ivMyCredit.setBackgroundResource(R.drawable.tab_home_nor);
        tvMyCredit.setTextColor(Color.parseColor("#666666"));
        ivCredit.setBackgroundResource(R.mipmap.tab_credit_nor);
        tvCredit.setTextColor(Color.parseColor("#666666"));
        ivMes.setBackgroundResource(R.mipmap.tab_chat_nor);
        tvMes.setTextColor(Color.parseColor("#666666"));
        ivMine.setBackgroundResource(R.mipmap.tab_me_nor);
        tvMine.setTextColor(Color.parseColor("#666666"));
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
                if (myCreditFgt == null) {
                    myCreditFgt = new HomeFragment();
                    transaction.add(R.id.main_frame, myCreditFgt);
                } else {
                    transaction.show(myCreditFgt);
                }
                break;
            case 1:
                main_title.setVisibility(View.GONE);
                if (creditFgt == null) {
                    creditFgt = new CreditFragment();
                    transaction.add(R.id.main_frame, creditFgt);
                } else {
                    transaction.show(creditFgt);
                }
                break;
            case 2:
                main_title.setVisibility(View.VISIBLE);
//                if (listFragment == null) {
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
//                    transaction.show(chatFrg);
//                }
                break;
            case 3:
                main_title.setVisibility(View.GONE);
                if (mineFgt == null) {
                    mineFgt = new MineFragment();
                    transaction.add(R.id.main_frame, mineFgt);
                } else {
                    transaction.show(mineFgt);
                }
                break;
            case 4:
                main_title.setVisibility(View.VISIBLE);
                if (friendFgt == null) {
                    friendFgt = new FriendFrg();
                    transaction.add(R.id.main_frame, friendFgt, "common");
                } else {
                    transaction.show(friendFgt);
                }
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (myCreditFgt != null) {
            transaction.hide(myCreditFgt);
        }
        if (creditFgt != null) {
            transaction.hide(creditFgt);
        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        if (mineFgt != null) {
            transaction.hide(mineFgt);
        }
        if (friendFgt != null) {
            transaction.hide(friendFgt);
        }
    }

    public void onEvent(HomeFragment.ToSwitchEvent event) {
        setAllTabNor();
        setTabSelection(1);
        setCheckItem(1);
    }

    private void setCheckItem(int position) {
        switch (position) {
            case 0:
                ivMyCredit.setBackgroundResource(R.drawable.tab_home_h);
                tvMyCredit.setTextColor(Color.parseColor("#f49626"));
                break;
            case 1:
                ivCredit.setBackgroundResource(R.mipmap.tab_credit_h);
                tvCredit.setTextColor(Color.parseColor("#f49626"));
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

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= WAITTIME) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            if (RongIM.getInstance() != null)
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

    @Override
    protected void onStart() {
        if (myReceiver == null)
            myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATATAB);
        this.registerReceiver(myReceiver, intentFilter);
        if (displayRedReceiver == null) {
            displayRedReceiver = new DisplayRedPointReceiver();
        }
        if (hideRedPointReceiver == null) {
            hideRedPointReceiver = new HideRedPointReceiver();
        }
        registerReceiver(hideRedPointReceiver, new IntentFilter(HIDE_REDPOINT));
        registerReceiver(displayRedReceiver, new IntentFilter(DISPLAY_REDPOINT));
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        this.unregisterReceiver(myReceiver);
        myReceiver = null;
        if (displayRedReceiver != null) {
            unregisterReceiver(displayRedReceiver);
        }
        if (hideRedPointReceiver != null) {
            unregisterReceiver(hideRedPointReceiver);
        }
        super.onDestroy();
    }

    private MyReceiver myReceiver;
    public final static String UPDATATAB = "com.beyond.main";

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            setAllTabNor();
            if (intent.getIntExtra(GameActivity.GAME_TYPE, 0) == 0) {
                setTabSelection(0);
                setCheckItem(0);
                String token = SpUtils.getToken(MainActivity.this);
                if (!TextUtils.isEmpty(token)) {
                    if (RongIM.getInstance() != null) {
                        RongCloudEvent.getInstance().getFriendList();
                        RongIM.getInstance().setOnReceiveUnreadCountChangedListener
                                (new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
                    }
                }
            } else {
                setTabSelection(1);
                setCheckItem(1);
            }

        }
    }

    public static final String DISPLAY_REDPOINT = "com.push.message";

    private class DisplayRedPointReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ivRedPoint.setVisibility(View.VISIBLE);
            MyLogUtils.info("MainActivity:红点显示");
        }
    }

    public static final String HIDE_REDPOINT = "com.hide.redpoint";

    private class HideRedPointReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ivRedPoint.setVisibility(View.GONE);
            MyLogUtils.info("MainActivity:红点隐藏");
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


//    private void getCity(){
//        RequestManager.getCommManager().findCity(null,new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }
}
