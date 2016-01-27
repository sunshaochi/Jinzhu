package com.beyonditsm.financial.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.MyRecomBean;
import com.beyonditsm.financial.entity.MyRecommeEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的推荐
 * Created by wangbin on 15/11/16.
 */
public class MyRecommAct extends BaseActivity {

    @ViewInject(R.id.fr_tj_list)
    private PullToRefreshListView fr_tj_list;

    private List<MyRecomBean.RowsEntity> datas = new ArrayList<>();
    private MyRecommeEntity fre;
    private UMSocialService mController;
    private Context context;
    private UserLoginEntity ule;

    private String title = "金蛛金服，业内返佣减利最高，欢迎加入抢钱大队";
    private String content = "金蛛金服－－圆你土豪梦想";
    private FrAdapter frAdapter;

//    String codeUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=/register/";
    String codeUrl = "http://www.myjinzhu.com/#/activity/spring-festival";

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_myrecomm);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fre = new MyRecommeEntity();
        setTopTitle("我的推荐");
        setLeftTv("返回");
        ule = getIntent().getParcelableExtra("userLogin");
        context = MyRecommAct.this;
        if (frAdapter == null) {
            frAdapter = new FrAdapter();
            fr_tj_list.getRefreshableView().setAdapter(frAdapter);
        }
        fr_tj_list.setPullRefreshEnabled(false);
        RequestManager.getUserManager().findFriendList(fre, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
//                ResultData<MyRecomBean> myRecomBean = (ResultData<MyRecomBean>) GsonUtils.json(result, MyRecomBean.class);
//                MyRecomBean data = myRecomBean.getData();
//                datas = data.getRows();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    JSONObject easyuiPagination = data.getJSONObject("easyuiPagination");
                    JSONArray rows = easyuiPagination.getJSONArray("rows");
                    for (int i = 0; i < rows.length(); i++) {
                        MyRecomBean.RowsEntity myRecomBean = new MyRecomBean.RowsEntity();
                        myRecomBean.setUserName(rows.getJSONObject(i).getString("userName"));
                        myRecomBean.setMobile(rows.getJSONObject(i).getString("mobile"));
                        datas.add(myRecomBean);
                        LogUtils.i(rows.getJSONObject(i).getString("mobile"));
                        LogUtils.i(datas.size() + "");
                    }
                    if (frAdapter == null) {
                        frAdapter = new FrAdapter();
                        fr_tj_list.getRefreshableView().setAdapter(frAdapter);
                    } else {
                        frAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.getConfig().closeToast();
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
        circleMedia.setShareContent(content + codeUrl );
        //设置朋友圈title
        circleMedia.setTitle(title);
        UMImage localImage = new UMImage(MyRecommAct.this, R.mipmap.logo);
        circleMedia.setShareImage(localImage);
//        circleMedia.setTargetUrl(codeUrl + ule.getMyReferralCode());
        circleMedia.setTargetUrl(codeUrl );
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
//        weixinContent.setTargetUrl(codeUrl + ule.getMyReferralCode());
        weixinContent.setTargetUrl(codeUrl);
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
//        sinaContent.setShareContent(content + codeUrl + ule.getMyReferralCode());
        sinaContent.setShareContent(content + codeUrl );
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
//        qqContent.setTargetUrl(codeUrl + ule.getMyReferralCode());
//        qqContent.setAppWebSite(codeUrl);
        qqContent.setTargetUrl(codeUrl);
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

    class FrAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (datas == null || datas.size() == 0)
                return 1;
            else
                return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.myrecommend_item, null);
                vh = new ViewHolder();
                vh.nameTv = (TextView) convertView.findViewById(R.id.recommend_name);
                vh.phoneTv = (TextView) convertView.findViewById(R.id.recommend_phone);
                vh.ll = convertView.findViewById(R.id.content_recommend);
                vh.iv_ewm = (ImageView) convertView.findViewById(R.id.ivtuijianma);
                vh.ll_tj_bg = (LinearLayout) convertView.findViewById(R.id.ll_tj_bg);
                vh.tvMyReferralCode = (TextView) convertView.findViewById(R.id.tuijianma);
                vh.iv_weixin = (ImageView) convertView.findViewById(R.id.iv_weixin);
                vh.iv_wxpyq = (ImageView) convertView.findViewById(R.id.iv_wxpyq);
                vh.iv_weibo = (ImageView) convertView.findViewById(R.id.iv_weibo);
                vh.iv_qq = (ImageView) convertView.findViewById(R.id.iv_qq);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                vh.ll_tj_bg.setVisibility(View.GONE);
                vh.ll.setVisibility(View.VISIBLE);
                Bitmap bitmap = null;
                if (ule != null)
                    bitmap = FinancialUtil.createQRImage(codeUrl + ule.getMyReferralCode());
                else
                    bitmap = FinancialUtil.createQRImage(codeUrl);
                vh.iv_ewm.setScaleType(ImageView.ScaleType.FIT_XY);
                vh.iv_ewm.setImageBitmap(bitmap);
                if (ule != null)
                    vh.tvMyReferralCode.setText(ule.getMyReferralCode());
                if (datas != null && datas.size() > 0) {
                    LogUtils.i("999999999999999999999999999999");
                    vh.ll_tj_bg.setVisibility(View.VISIBLE);
//                    MyRecomBean.RowsEntity us = datas.get(position);
                    vh.nameTv.setText(datas.get(0).getUserName());
                    vh.phoneTv.setText(datas.get(0).getMobile());
                }
            } else if (position > 0) {
                vh.ll.setVisibility(View.GONE);
                if (datas != null && datas.size() > 0) {
                    LogUtils.i("1111111111111111111111111");
                    vh.ll_tj_bg.setVisibility(View.VISIBLE);
//                    MyRecomBean.RowsEntity us = datas.get(position);
                    vh.nameTv.setText(datas.get(position).getUserName());
                    vh.phoneTv.setText(datas.get(position).getMobile());
                } else {
                    LogUtils.i("66666666666666666666666");
                    vh.ll_tj_bg.setVisibility(View.GONE);
                }
            }
            vh.iv_weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weixinShare();
                }
            });
            vh.iv_wxpyq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weixinCircleShare();
                }
            });
            vh.iv_weibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weiboShare();
                }
            });
            vh.iv_qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qqShare();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        TextView nameTv;
        TextView phoneTv;
        View ll;
        LinearLayout ll_tj_bg;
        ImageView iv_weixin;
        ImageView iv_wxpyq;
        ImageView iv_weibo;
        ImageView iv_qq;
        ImageView iv_ewm;
        TextView tvMyReferralCode;
    }
}
