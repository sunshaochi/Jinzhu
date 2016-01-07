package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.RongCloudEvent;
import com.beyonditsm.financial.activity.manager.ManagerMainAct;
import com.beyonditsm.financial.activity.servicer.ServiceMainAct;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.util.LogUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

;

/**
 * 闪屏图
 * Created by wangbin on 15/11/11.
 */
public class SplashAct extends BaseActivity {
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
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity(MainActivity.class, true);
                boolean isFirst = SpUtils.getIsFirst(getApplicationContext());
                Intent intent = null;
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
                        if (roleName.equals("ROLE_CREDIT_MANAGER")) {
                            sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
                            gotoActivity(ManagerMainAct.class, true);
                        } else if (roleName.equals("ROLE_COMMON_CLIENT")) {
                            sendBroadcast(new Intent(MainActivity.UPDATATAB));
                            gotoActivity(MainActivity.class, true);
                        } else {
                            sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
                            gotoActivity(ServiceMainAct.class, true);
                        }
                        String token = SpUtils.getToken(getApplicationContext());
                        if (!TextUtils.isEmpty(token)) {
                            if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                                RongIM.connect(token, new RongIMClient.ConnectCallback() {
                                    @Override
                                    public void onTokenIncorrect() {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        RongCloudEvent.getInstance().setOtherListener();
                                        LogUtils.i("重连成功");
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                    }
                                });
                            }

                        }
                    }
                }
            }
        }, 2000);
    }


//    private  void getProvince(){
//        RequestManager.getCommManager().findProvince(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                JSONObject jsonObject=new JSONObject(result);
//                ProvinceDao.addProvince((List<ProvinceInfo>) gson.fromJson(jsonObject.optJSONArray("data").toString(), new TypeToken<List<ProvinceInfo>>() {
//            }.getType()));
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }
}
