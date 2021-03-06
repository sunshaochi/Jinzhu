package com.beyonditsm.financial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;

import com.beyonditsm.financial.activity.PhotoActivity;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.FriendEntity;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.MyProvider;
import com.tandong.sa.json.Gson;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;


/**
 * Created by zhjchen on 1/29/15
 */

/**
 * 融云SDK事件监听处理。
 * 把事件统一处理，开发者可直接复制到自己的项目中去使用。
 * <p>
 * 该类包含的监听事件有：
 * 1、消息接收器：OnReceiveMessageListener。
 * 2、发出消息接收器：OnSendMessageListener。
 * 3、用户信息提供者：GetUserInfoProvider。
 * 4、好友信息提供者：GetFriendsProvider。
 * 5、群组信息提供者：GetGroupInfoProvider。
 * 7、连接状态监听器，以获取连接相关状态：ConnectionStatusListener。
 * 8、地理位置提供者：LocationProvider。
 * 9、自定义 push 通知： OnReceivePushMessageListener。
 * 10、会话列表界面操作的监听器：ConversationListBehaviorListener。
 */
public final class RongCloudEvent implements RongIM.UserInfoProvider, RongIM.ConversationBehaviorListener,
        RongIM.ConversationListBehaviorListener, Handler.Callback {

    private static RongCloudEvent mRongCloudInstance;
    //    private List<UserEntity> friends = new ArrayList<>();
    private List<FriendEntity> friends = new ArrayList<>();
    private Gson gson = new Gson();
    private Context mContext;

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (RongCloudEvent.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudEvent(context);
                }
            }
        }
    }

    /**
     * 构造方法。
     *
     * @param context 上下文。
     */
    private RongCloudEvent(Context context) {
        mContext = context;
        initDefaultListener();
    }

    public boolean handleMessage(android.os.Message message) {
        return false;
    }

    /**
     * RongIM.init(this) 后直接可注册的Listener。
     */
    private void initDefaultListener() {
        String token = SpUtils.getToken(mContext);

        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setConversationListBehaviorListener(this);
//        RongIM.setOnReceivePushMessageListener(new MyReceivePushMessageListener());
        //消息体内是否有 userinfo 这个属性
        RongIM.getInstance().setMessageAttachedUserInfo(true);
    }


    /**
     * 连接成功注册。
     * <p>
     * 在RongIM-connect-onSuccess后调用。
     */
    public void setOtherListener() {

        InputProvider.ExtendProvider[] provider1 = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance()),//相机
                new MyProvider(RongContext.getInstance()),//自定义功能
        };
        InputProvider.ExtendProvider[] provider2 = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance()),//相机
        };
        String roleName = SpUtils.getRoleName(mContext);
        if (roleName.equals("ROLE_CREDIT_MANAGER")) {
            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider1);
            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.CHATROOM, provider1);
        } else {
            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider2);
            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.CHATROOM, provider2);
        }
    }


    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if (message.getContent() instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message.getContent();
            Intent intent = new Intent(context, PhotoActivity.class);
            intent.putExtra("message",message);
            intent.putExtra("photo", imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
            if (imageMessage.getThumUri() != null)
                intent.putExtra("thumbnail", imageMessage.getThumUri());

            context.startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

//    private class MyReceivePushMessageListener implements RongIMClient.OnReceivePushMessageListener {
//
//        /**
//         * 收到 push 消息的处理。
//         *
//         * @param pushNotificationMessage push 消息实体。
//         * @return true 自己来弹通知栏提示，false 融云 SDK 来弹通知栏提示。
//         */
//        @Override
//        public boolean onReceivePushMessage(PushNotificationMessage pushNotificationMessage) {
//            return false;
//        }
//    }


    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static RongCloudEvent getInstance() {
        return mRongCloudInstance;
    }


    /**
     * 用户信息的提供者：GetUserInfoProvider 的回调方法，获取用户信息。
     *
     * @param userId 用户 Id。
     * @return 用户信息，（注：由开发者提供用户信息）。
     */
    @Override
    public UserInfo getUserInfo(String userId) {
        List<FriendBean> friendBeans = FriendDao.findfriend();
        if (friendBeans != null && friendBeans.size() > 0) {
            //增强for把所有的用户信息 return 到融云服务端
            for (FriendBean friend : friendBeans) {
                //判断返回的userId
                if (friend.getUserId().equals(userId)) {
                    if(friend.getUserHead()!=null)
                    return new UserInfo(friend.getUserId(), friend.getUserName(),
                            Uri.parse(friend.getUserHead()));
                    else
                        return new UserInfo(friend.getUserId(), friend.getUserName(),
                                null);
                }
            }
        }
        return null;
    }
}
