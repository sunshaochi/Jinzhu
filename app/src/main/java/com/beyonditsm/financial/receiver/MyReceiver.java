package com.beyonditsm.financial.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.MessageActivity;
import com.beyonditsm.financial.activity.manager.ManagerMainAct;
import com.beyonditsm.financial.db.MessageDao;
import com.beyonditsm.financial.entity.MessageBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private String orderId;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        	processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            String jsonType = bundle.getString(JPushInterface.EXTRA_EXTRA);
            MyLogUtils.info("推送数据：" + jsonType);
            context.sendBroadcast(new Intent(MineFragment.UPDATE_MESSAGE));
            context.sendBroadcast(new Intent(MainActivity.DISPLAY_REDPOINT));
//            context.sendOrderedBroadcast(new Intent("com.update.message"),null);
////            context.sendBroadcast(new Intent(MineFragment.DISPLAY_POINT));
            if (!TextUtils.isEmpty(jsonType)) {
                try {
                    MessageBean mb = GsonUtils.json2Bean(jsonType, MessageBean.class);
                    mb.setTime(FinancialUtil.getCurrentTime());
                    mb.setMsg_id(bundle.getString(JPushInterface.EXTRA_MSG_ID));
                    MessageDao.saveMes(mb);
                    context.sendBroadcast(new Intent(MessageActivity.MESSAGE));
                    JSONObject object = new JSONObject(jsonType);
                    orderId = object.getString("orderId");
                    MyLogUtils.info("orderId+" + orderId);
                    if (!TextUtils.isEmpty(orderId)){
                        SpUtils.setOrderId(MyApplication.getInstance(),orderId);
                    }
//                    Intent intent1 = new Intent(MyCreditAct.PUSH_MESSAGE);
//                    intent1.putExtra("id", orderId);
//                    context.sendBroadcast(intent1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            /*消息推送扩展返回参数：{type:1}注：1：向信贷经理推送抢单提醒，跳到信贷经理抢单页面，
            2：机构审核订单通过后向用户推送消息，3：机构驳回订单通过后向用户推送消息，
            4：机构要求客户补件向用户推送消息 2、3、4都跳到用户订单列表页面*/
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            String jsonType = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (!TextUtils.isEmpty(jsonType)) {


                try {
                    JSONObject obj = new JSONObject(jsonType);
                    String type = obj.optString("type");
                    Intent i = null;
                    if ("1".equals(type)) {
                        i = new Intent(context, ManagerMainAct.class);
                    } else {
                        i = new Intent(context, MainActivity.class);
                    }

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //打开自定义的Activity
//        	Intent i = new Intent(context, TestActivity.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
//	}
}
