package com.beyonditsm.financial.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.beyonditsm.financial.R;

import io.rong.imkit.RongContext;
import io.rong.imkit.widget.provider.InputProvider;

/**
 * 自定义融云扩展功能
 * Created by Yang on 2015/11/20 0020.
 */
public class MyProvider extends InputProvider.ExtendProvider {

    private static final int MYRESULT = 0;
    private Context context;

    public MyProvider(RongContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public Drawable obtainPluginDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.pinggu_nor);
    }

    @Override
    public CharSequence obtainPluginTitle(Context context) {
        return "客户评估";
    }

    @Override
    public void onPluginClick(View view) {
//        startActivityForResult(intent, MYRESULT);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0 && resultCode == 0) {
//            if (data != null) {
//                String showMessage = "我是自定义功能";
//                final TextMessage content = TextMessage.obtain(showMessage);
//                if (RongIM.getInstance().getRongIMClient() != null)
//                    RongIM.getInstance().getRongIMClient().sendMessage(getCurrentConversation().getConversationType(),
//                            getCurrentConversation().getTargetId(), content, null, null, new RongIMClient.SendMessageCallback() {
//                                @Override
//                                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
//                                    Log.d("ExtendProvider", "onError--" + errorCode);
//                                }
//
//                                @Override
//                                public void onSuccess(Integer integer) {
//                                    Log.d("ExtendProvider", "onSuccess--" + integer);
//                                }
//                            });
//            }
//        }
//    }
}

