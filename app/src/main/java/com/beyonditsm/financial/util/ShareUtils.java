package com.beyonditsm.financial.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by xuleyuan on 2016/10/11.
 */

public class ShareUtils {
    private static ShareUtils shareUtils;
    private Context context;
    private UMSocialService mController;

    public static ShareUtils getInstance() {
        if (shareUtils == null) {
            shareUtils = new ShareUtils();
        }
        return shareUtils;
    }

    public ShareUtils init(final Context context, UMSocialService mController) {
        shareUtils.context = context;
        shareUtils.mController = mController;
        return shareUtils;
    }

    public void weixinCircleShare(String content, String title, String yqUrl, UserLoginEntity ule) {
        //appID和appScret不正确，需要修改
        String appID = "wxe07bb5ef24761a83";
        String appSecret = "b1734f22c332f0396a3e8e254f739130";
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context.getApplicationContext(), appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(content + codeUrl + ule.getMyReferralCode());
        circleMedia.setShareContent(content + yqUrl);
        //设置朋友圈title
        circleMedia.setTitle(title);
        UMImage localImage = new UMImage(context, R.mipmap.logo);
        circleMedia.setShareImage(localImage);
        if (ule == null) {
            circleMedia.setTargetUrl(yqUrl);
        } else {
            circleMedia.setTargetUrl(yqUrl + ule.getMyReferralCode());
        }

//        circleMedia.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        mController.setShareMedia(circleMedia);
        //分享到朋友圈
        mController.postShare(context.getApplicationContext(), SHARE_MEDIA.WEIXIN_CIRCLE, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微信朋友圈开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(context.getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(context.getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void weixinShare(String content, String title, String yqUrl, UserLoginEntity ule) {
        //appID和appScret不正确，需要修改
        String appID = "wxe07bb5ef24761a83";
        String appSecret = "b1734f22c332f0396a3e8e254f739130";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context.getApplicationContext(), appID, appSecret);
        wxHandler.addToSocialSDK();

        //设置分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        UMImage localImage = new UMImage(context, R.mipmap.logo);
        weixinContent.setShareImage(localImage);
        if (ule != null) {
            weixinContent.setTargetUrl(yqUrl + ule.getMyReferralCode());
        } else {
            weixinContent.setTargetUrl("www.baidu.com");
        }

//        weixinContent.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        mController.setShareMedia(weixinContent);
        //分享到微信
        mController.postShare(context.getApplicationContext(), SHARE_MEDIA.WEIXIN, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微信开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(context.getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(context.getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void weiboShare(String content, String yqUrl, UserLoginEntity ule) {
        //设置新浪SSO handler(授权)
        //    mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setDefaultShareLocation(false);

        //设置分享内容
        SinaShareContent sinaContent = new SinaShareContent();
        if (ule == null) {
            sinaContent.setShareContent(content + yqUrl);
        } else {
            sinaContent.setShareContent(content + yqUrl + ule.getMyReferralCode());
        }

//        sinaContent.setShareContent(content + yqUrl);
        sinaContent.setShareImage(new UMImage(context, R.mipmap.logo));
        mController.setShareMedia(sinaContent);
        //分享到新浪微博
        mController.postShare(context, SHARE_MEDIA.SINA, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到微博开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(context.getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(context.getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void qqShare(String title, String content, String yqUrl, UserLoginEntity ule) {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        String APP_ID = "1105013184";
        String APP_KEY = "czkOLWHqU2V59fma";
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, APP_ID, APP_KEY);
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
        if (ule == null) {
            qqContent.setTargetUrl(yqUrl);
        } else {
            qqContent.setTargetUrl(yqUrl + ule.getMyReferralCode());
        }

//        qqContent.setAppWebSite(codeUrl);
//        qqContent.setTargetUrl(yqUrl+tvTuijianma.getText().toString()+"");
        qqContent.setShareImage(new UMImage(context, R.mipmap.logo));

        mController.setShareMedia(qqContent);
        //分享到qq
        mController.postShare(context, SHARE_MEDIA.QQ, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "分享到qq开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                if (i == 200) {
                    Toast.makeText(context.getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (i == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(context.getApplicationContext(), "分享失败[" + i + "] " +
                            eMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
