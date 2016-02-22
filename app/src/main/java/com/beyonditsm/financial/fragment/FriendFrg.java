package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.FriendEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Yang on 2015/11/30 0030.
 */
public class FriendFrg extends BaseFragment {
    @ViewInject(R.id.new_friend_lv)
    private ListView newlv;//新好友

    @ViewInject(R.id.my_friend_lv)
    private LoadRefreshView mylv;//我的好友

    private NewFriendAdapter newFriendAdapter;
    private MyFriendAdapter myFriendAdapter;

//    private List<UserEntity> friends = new ArrayList<>();
        private List<FriendEntity> friends = new ArrayList<>();
    private Gson gson = new Gson();
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.friend_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mylv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        newFriendAdapter = new NewFriendAdapter();
        newlv.setAdapter(newFriendAdapter);
        getFriendList();
        initFriendList();
    }

    private void initFriendList() {
        mylv.setPullRefreshEnabled(true);
        mylv.setPullLoadEnabled(false);
        mylv.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        mylv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getFriendList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        mylv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FriendEntity friend = (FriendEntity) myFriendAdapter.getItem(i);
                if (RongIM.getInstance() != null) {
                    if (TextUtils.isEmpty(friend.getManaName())) {
                        RongIM.getInstance().startPrivateChat(context, friend.getAccountId(), friend.getTel());
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.getAccountId(), friend.getTel(),
                                Uri.parse(IFinancialUrl.BASE_IMAGE_URL + friend.getWkCardPic())));
                    } else {
                        RongIM.getInstance().startPrivateChat(context, friend.getAccountId(), friend.getManaName());
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.getAccountId(), friend.getManaName(),
                                Uri.parse(IFinancialUrl.BASE_IMAGE_URL + friend.getWkCardPic())));
                    }
                }
            }
        });
    }

    private void getFriendList() {
        RequestManager.getCommManager().getFriendList(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                mylv.onPullDownRefreshComplete();
                JSONObject obj = new JSONObject(result);
                JSONArray array = obj.getJSONArray("data");
//                friends = gson.fromJson(array.toString(), new TypeToken<List<UserEntity>>() {
//                }.getType());
                friends = gson.fromJson(array.toString(), new TypeToken<List<FriendEntity>>() {
                }.getType());
                if (friends != null && friends.size() > 0) {
                    for (int i = 1; i < friends.size(); i++) {
                        FriendBean friendBean = new FriendBean();
                        friendBean.setUserId(friends.get(i).getAccountId());

//                        friendBean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + friends.get(i).getHeadIcon());
//                        if (TextUtils.isEmpty(friends.get(i).getUserName()))
//                            friendBean.setUserName(friends.get(i).getAccountName());
//                        else
//                            friendBean.setUserName(friends.get(i).getUserName());

                        friendBean.setUserId(friends.get(i).getAccountId());
                        friendBean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + friends.get(i).getRcHendPic());
                        if (!TextUtils.isEmpty(friends.get(i).getManaName()))
                            friendBean.setUserName(friends.get(i).getManaName());

                        FriendDao.saveMes(friendBean);
                    }
                }

//                    ResultData<UserEntity> rds = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
//                    friends = (List<UserEntity>) rds.getData();
                myFriendAdapter = new MyFriendAdapter();
                mylv.getRefreshableView().setAdapter(myFriendAdapter);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @Override
    public void setListener() {

    }

    private class NewFriendAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(context, R.layout.new_friend_item, null);
            return view;
        }
    }


    private class MyFriendAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return friends.size();
        }

        @Override
        public Object getItem(int i) {
            return friends.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(context, R.layout.my_friend_item, null);
                holder.headicon = (ImageView) view.findViewById(R.id.headicon);
                holder.username = (TextView) view.findViewById(R.id.username);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

//            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL +
//                    friends.get(i).getHeadIcon(), holder.headicon, options);
//            if (TextUtils.isEmpty(friends.get(i).getUserName())) {
//                if (TextUtils.isEmpty(friends.get(i).getAccountName())) {
//                    holder.username.setText("" + "(手机号：" + ")");
//                } else {
//                    holder.username.setText("" + "(手机号：" + friends.get(i).getAccountName() + ")");
//                }
//            } else {
//                if (TextUtils.isEmpty(friends.get(i).getAccountName())) {
//                    holder.username.setText(friends.get(i).getUserName() + "(手机号：" + ")");
//                } else {
//                    holder.username.setText(friends.get(i).getUserName() + "(手机号：" + friends.get(i).getAccountName() + ")");
//                }
//            }

            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL +
                    friends.get(i).getWkCardPic(), holder.headicon, options);
            if (TextUtils.isEmpty(friends.get(i).getManaName())) {
                if (TextUtils.isEmpty(friends.get(i).getTel())) {
                    holder.username.setText("" + "(手机号：" + ")");
                } else {
                    holder.username.setText("" + "(手机号：" + friends.get(i).getTel() + ")");
                }
            } else {
                if (TextUtils.isEmpty(friends.get(i).getTel())) {
                    holder.username.setText(friends.get(i).getManaName() + "(手机号：" + ")");
                } else {
                    holder.username.setText(friends.get(i).getManaName() + "(手机号：" + friends.get(i).getTel() + ")");
                }
            }
            return view;
        }

        class ViewHolder {
            TextView username;
            ImageView headicon;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATA);
        if (myRevice == null) {
            myRevice = new MyRevice();
            context.registerReceiver(myRevice, intentFilter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(myRevice);
        myRevice = null;
    }


    public static String UPDATA = "com.beyonditsm.friendfrg";
    private MyRevice myRevice;

    public class MyRevice extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getFriendList();
        }
    }
}
