package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.beyonditsm.financial.R;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * 会话act
 * Created by Yang on 2015/11/18 0018.
 */
public class ConversationActivity extends BaseActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.conversation);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        getIntentDate(intent);
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        //目标 Id
        String mTargetId = intent.getData().getQueryParameter("targetId");
        //刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
        String mTargetIds = intent.getData().getQueryParameter("targetIds");
        setTopTitle(intent.getData().getQueryParameter("title"));
        //intent.getData().getLastPathSegment();//获得当前会话类型
        // 会话类型
        Conversation.ConversationType mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         目标 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }
}
