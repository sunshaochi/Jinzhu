package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.wallet.MyWalletActivity;
import com.beyonditsm.financial.entity.MyRecomBean;
import com.beyonditsm.financial.entity.MyRecommeEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 我的推荐
 * Created by wangbin on 15/11/16
 */
public class MyRecommAct extends BaseActivity {
    @ViewInject(R.id.ivtuijianma)
    private ImageView ivTuiJianMa;
    @ViewInject(R.id.tuijianma)
    private TextView tvTuijianma;
    @ViewInject(R.id.iv_weixin)
    private ImageView ivWeixin;
    @ViewInject(R.id.iv_wxpyq)
    private ImageView ivWxpyq;
    @ViewInject(R.id.iv_weibo)
    private ImageView ivWeibo;
    @ViewInject(R.id.iv_qq)
    private ImageView ivQQ;
    //    @ViewInject(R.id.tvRoleName)
//    private TextView RoleName;
//    @ViewInject(R.id.tv_BecomeServant)
//    private TextView BecomeServant;
    @ViewInject(R.id.tvServant)
    private TextView tvServant;
    @ViewInject(R.id.rl_servant)
    private RelativeLayout rlServant;
    //    @ViewInject(R.id.tv_recommedUserCount)
//    private TextView tvRecommedUserCount;
    @ViewInject(R.id.recommendedLoanAmount)
    private TextView recommendedLoanAmount;
    @ViewInject(R.id.recommendedCCardAmount)
    private TextView recommendedCCardAmount;
    @ViewInject(R.id.handledRewardAmount)
    private TextView handledRewardAmount;
    @ViewInject(R.id.unhandledRewardAmount)
    private TextView unhandledRewardAmount;
    @ViewInject(R.id.already)
    private ImageView alreadyImg;
    @ViewInject(R.id.btn_receiveReward)
    private ImageView btnReward;

    private MyRecommeEntity fre;
    private UMSocialService mController;
    private Context context;
    private UserLoginEntity ule;
    private UserEntity user;//用户信息
    private int RewardAmount;
    //    private String title = "金蛛金服，业内返佣减利最高，欢迎加入抢钱大队";
    private String title = "成为金蛛代言人，躺着就能赚钱！";
    //    private String content = "金蛛金服－－圆你土豪梦想";
    private String content = "金蛛金服，业内史无前例的贴息返佣力度，只做最快速、方便、低息的银行贷款。成为代言人，和小伙伴一起赚钱，成为土豪不是梦~";

    String yqUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=/register/";
    //    String codeUrl = "http://www.myjinzhu.com/#/activity/spring-festival";
//    String codeUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=%2Ftab%2Fhome%2Factivity%2Ffestival";

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_myrecomm);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fre = new MyRecommeEntity();
        setTopTitle("我的推荐");
        setLeftTv("返回");
        rlServant.setVisibility(View.VISIBLE);
        tvServant.setText("代言人指南");
        rlServant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                logout();
                Intent intent = new Intent(MyRecommAct.this, ServantSpecialAct.class);
                startActivity(intent);
            }
        });
        ule = getIntent().getParcelableExtra("userLogin");
        getIntent().removeExtra("userLogin");
//        if (ule == null){
//            get
//        }
        context = MyRecommAct.this;
//        if (frAdapter == null) {
//            frAdapter = new FrAdapter();
//            fr_tj_list.getRefreshableView().setAdapter(frAdapter);
//        }
//        fr_tj_list.setPullRefreshEnabled(false);
//        findFriendList();

//        getRoleInfo();
//        getServantCondInfo();
        getServantRmdIfo();

        setQRImg();

        shareListener();

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.getConfig().closeToast();
    }

    private void setQRImg() {
        Bitmap bitmap;
        if (ule != null)
            bitmap = FinancialUtil.createQRImage(yqUrl + ule.getMyReferralCode());
        else
            bitmap = FinancialUtil.createQRImage(yqUrl);
        ivTuiJianMa.setScaleType(ImageView.ScaleType.FIT_XY);
        ivTuiJianMa.setImageBitmap(bitmap);

        if (ule != null) {
//            tvTuijianma.setText(ule.getMyReferralCode());
            tvTuijianma.setText(ule.getMyReferralCode());
        } else if (user != null) {
            tvTuijianma.setText(user.getUsername());
        }
        if (null != ParamsUtil.getInstance().getUserID() && !"".equals(ParamsUtil.getInstance().getUserID())) {
            tvTuijianma.setText(ParamsUtil.getInstance().getUserID());
        }

    }

    private void shareListener() {
        ivWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinShare();
            }
        });
//        ivWxpyq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                weixinCircleShare();
//            }
//        });
        ivWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weiboShare();
            }
        });
        ivQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqShare();
            }
        });
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.btn_receiveReward, R.id.ll_wallet, R.id.iv_wxpyq})
    public void toClick(View v) {
        switch (v.getId()) {
            //按钮下方的代言人
//            case R.id.enter_service:
//                Intent intent = new Intent(MyRecommAct.this, ServantSpecialAct.class);
//                startActivity(intent);
//                break;
            //立即领取按钮
            case R.id.btn_receiveReward:
                RequestManager.getCommManager().getServantReward(new RequestManager.CallBack() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucess(String result) {

                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONObject data = obj.getJSONObject("data");
                            int handledAmount = data.getInt("handledAmount");
                            if (handledAmount > 0) {
                                MyToastUtils.showShortDebugToast(MyRecommAct.this, "领取奖励成功，奖励已发放到您的钱包中");
                                handledRewardAmount.setText("¥ " + (RewardAmount + handledAmount) + " 元");
                                sendBroadcast(new Intent(MineFragment.WALLET_POINT));
                            } else {
                                MyToastUtils.showShortDebugToast(MyRecommAct.this, "您已在其他终端领取，不可重复领取");
                            }

                            alreadyImg.setVisibility(View.VISIBLE);
                            btnReward.setVisibility(View.GONE);
                            unhandledRewardAmount.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        MyToastUtils.showShortToast(MyRecommAct.this,result.getBytes("handled"));
                    }

                    @Override
                    public void onError(int status, String msg) {
                        MyToastUtils.showShortToast(MyRecommAct.this, msg);
                    }
                });
                break;
            //累计收益进入我的钱包
            case R.id.ll_wallet:
                Intent intent2 = new Intent(MyRecommAct.this, MyWalletActivity.class);
                intent2.putExtra("userLogin", ule);
                intent2.putExtra("userInfo", user);
                startActivity(intent2);
                break;
            case R.id.iv_wxpyq:
                weixinCircleShare();
                break;
        }
    }

    private void weixinCircleShare() {
        //appID和appScret不正确，需要修改
        String appID = "wxe07bb5ef24761a83";
        String appSecret = "b1734f22c332f0396a3e8e254f739130";
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getApplicationContext(), appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(content + codeUrl + ule.getMyReferralCode());
        circleMedia.setShareContent(content + yqUrl);
        //设置朋友圈title
        circleMedia.setTitle(title);
        UMImage localImage = new UMImage(MyRecommAct.this, R.mipmap.logo);
        circleMedia.setShareImage(localImage);
        circleMedia.setTargetUrl(yqUrl + ule.getMyReferralCode());
//        circleMedia.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        mController.setShareMedia(circleMedia);
        //分享到朋友圈
        mController.postShare(getApplicationContext(), SHARE_MEDIA.WEIXIN_CIRCLE, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微信朋友圈开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void weixinShare() {
        //appID和appScret不正确，需要修改
        String appID = "wxe07bb5ef24761a83";
        String appSecret = "b1734f22c332f0396a3e8e254f739130";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getApplicationContext(), appID, appSecret);
        wxHandler.addToSocialSDK();

        //设置分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        UMImage localImage = new UMImage(MyRecommAct.this, R.mipmap.logo);
        weixinContent.setShareImage(localImage);
        weixinContent.setTargetUrl(yqUrl + ule.getMyReferralCode());
//        weixinContent.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        mController.setShareMedia(weixinContent);
        //分享到微信
        mController.postShare(getApplicationContext(), SHARE_MEDIA.WEIXIN, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微信开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void weiboShare() {
        //设置新浪SSO handler(授权)
        //    mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setDefaultShareLocation(false);

        //设置分享内容
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(content + yqUrl + ule.getMyReferralCode());
//        sinaContent.setShareContent(content + yqUrl);
        sinaContent.setShareImage(new UMImage(MyRecommAct.this, R.mipmap.logo));
        mController.setShareMedia(sinaContent);
        //分享到新浪微博
        mController.postShare(MyRecommAct.this, SHARE_MEDIA.SINA, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微博开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void qqShare() {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        String APP_ID = "1105013184";
        String APP_KEY = "czkOLWHqU2V59fma";
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(MyRecommAct.this, APP_ID, APP_KEY);
        qqSsoHandler.addToSocialSDK();

        //分享到空间
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(MyRecommAct.this, APP_ID,
//                APP_KEY);
//        qZoneSsoHandler.addToSocialSDK();

        //设置分享到qq的内容
        QQShareContent qqContent = new QQShareContent();
        qqContent.setTitle(title);
        qqContent.setShareContent(content);
//        qqContent.setShareContent(content+codeUrl+ule.getMyReferralCode());
        qqContent.setTargetUrl(yqUrl + ule.getMyReferralCode());
//        qqContent.setAppWebSite(codeUrl);
//        qqContent.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        qqContent.setShareImage(new UMImage(MyRecommAct.this, R.mipmap.logo));

        mController.setShareMedia(qqContent);
        //分享到qq
        mController.postShare(MyRecommAct.this, SHARE_MEDIA.QQ, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到qq开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    //获取代言人推荐信息
    private void getServantRmdIfo() {
        RequestManager.getCommManager().getServantRmdInfo(new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                int LoanAmount = data.getInt("recommendedLoanAmount");
                int CCardAmount = data.getInt("recommendedCCardAmount");
                RewardAmount = data.getInt("handledRewardAmount");
                int unhandledAmount = data.getInt("unhandledRewardAmount");
                JSONArray rewards = data.getJSONArray("rewards");
//                tvRecommedUserCount.setText(recommedUserCnt + "");
                float num = (float) LoanAmount / 10000;
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                String s = df.format(num);//返回的是String类型
                recommendedLoanAmount.setText("¥ " + s + " 万元");
                recommendedCCardAmount.setText(CCardAmount + "");
                handledRewardAmount.setText("¥ " + RewardAmount + " 元");
                unhandledRewardAmount.setText("待领取 " + unhandledAmount + " 元");
                if (unhandledAmount > 0) {
                    alreadyImg.setVisibility(View.GONE);
                    unhandledRewardAmount.setVisibility(View.VISIBLE);
                    btnReward.setVisibility(View.VISIBLE);
                } else {
                    alreadyImg.setVisibility(View.VISIBLE);
                    unhandledRewardAmount.setVisibility(View.GONE);
                    btnReward.setVisibility(View.GONE);
                }
                for (int i =0;i<rewards.length();i++){
                    JSONObject jsonObject = rewards.getJSONObject(i);
                    if(jsonObject.getInt("level") == 1){
                        ParamsUtil.getInstance().setFirstWard(jsonObject.getDouble("percentage"));
                        ParamsUtil.getInstance().setFirstCardWard(jsonObject.getDouble("fixedBonus"));
                    }else if(jsonObject.getInt("level") == 2){
                        ParamsUtil.getInstance().setSecWard(jsonObject.getDouble("percentage"));
                        ParamsUtil.getInstance().setSecCardWard(jsonObject.getDouble("fixedBonus"));
                    }else if(jsonObject.getInt("level") == 3){
                        ParamsUtil.getInstance().setThirdWard(jsonObject.getDouble("percentage"));
                        ParamsUtil.getInstance().setThirdCardWard(jsonObject.getDouble("fixedBonus"));
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    @Override
    protected void onResume() {
        ParamsUtil.getInstance().setCurrentAct(MyRecommAct.this);
        super.onResume();
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();

            }

            @Override
            public void onError(int status, String msg) {
            }
        });
    }

    //获取奖励（修改资料）
    private void getReceiveReward(UserEntity userEntity) {
        RequestManager.getCommManager().getReceiveReward(userEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(), "恭喜您已成功领取奖励，请至我的钱包查收");
                ParamsUtil.getInstance().setWalletEnter(true);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), "已领取过奖励，用户信息修改成功");
                finish();
            }
        });
    }
}
