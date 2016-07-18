package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.manager.ManagerMainAct;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.gps.GPSAddressUtils;
import com.beyonditsm.financial.util.gps.LocationListener;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 闪屏图
 * Created by wangbin on 15/11/11.
 */
public class SplashAct extends BaseActivity implements LocationListener{
//    @ViewInject(R.id.ivSplash)
//    private ImageView ivSplash;
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        MyApplication.screenWith = FinancialUtil.getScreenWidth(this);
        MyApplication.screenHeight = FinancialUtil.getScreenHeight(this);

    }

    private Handler handler=new Handler();

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GPSAddressUtils.getInstance().setLocationListener(this);
        GPSAddressUtils.getInstance().getLocation(SplashAct.this);

    }

    /**
     * 建立与融云服务器的连接
     */
    private void connect(final String token) {
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("SplashAct", "--onError" + errorCode);
                }
            });
        }
    }

    @Override
    public void onChanged(boolean isGet, String city) {
        ParamsUtil.getInstance().setChangedCity(city);
        ParamsUtil.getInstance().setCityGet(isGet);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                gotoActivity(MainActivity.class, true);
                boolean isFirst = SpUtils.getIsFirst(getApplicationContext());
                Intent intent;
                JPushInterface.onResume(getApplicationContext());
                if (isFirst) {
                    // 第一次进入应用
                    intent = new Intent(SplashAct.this, GuideActivity.class);
                    SpUtils.setIsFirst(SplashAct.this, false);
                    startActivity(intent);
                    finish();
                } else {

                    if ("".equals(SpUtils.getRoleName(getApplicationContext()))) {
                        gotoActivity(MainActivity.class, true);
                    } else {
                        String roleName = SpUtils.getRoleName(getApplicationContext());
                        switch (roleName) {
                            case "ROLE_CREDIT_MANAGER":
                                sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
                                gotoActivity(ManagerMainAct.class, true);
                                break;
                            case "ROLE_COMMON_CLIENT":
                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
                                gotoActivity(MainActivity.class, true);
                                break;
                            default:
//                            sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
//                            gotoActivity(ServiceMainAct.class, true);
                                sendBroadcast(new Intent(MainActivity.UPDATATAB));
                                gotoActivity(MainActivity.class, true);
                                break;
                        }
                        String token = SpUtils.getToken(getApplicationContext());
                        if (!TextUtils.isEmpty(token)) {

                            connect(token);

                        }
                    }
                }
            }
        }, 2000);
    }
}
