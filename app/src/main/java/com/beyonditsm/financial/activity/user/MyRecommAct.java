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

    @ViewInject(R.id.fr_tj_list)
    private PullToRefreshListView fr_tj_list;
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

    private List<MyRecomBean.RowsEntity> datas = new ArrayList<>();
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
    private FrAdapter frAdapter;

    String yqUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=/register/";
    //    String codeUrl = "http://www.myjinzhu.com/#/activity/spring-festival";
    String codeUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=%2Ftab%2Fhome%2Factivity%2Ffestival";

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

        if (ule != null ) {
//            tvTuijianma.setText(ule.getMyReferralCode());
            tvTuijianma.setText(ule.getMyReferralCode());
        }else if (user != null){
            tvTuijianma.setText(user.getUsername());
        }
        if(null != ParamsUtil.getInstance().getUserID() && !"".equals(ParamsUtil.getInstance().getUserID())){
            tvTuijianma.setText(ParamsUtil.getInstance().getUserID());
        }

    }

//    private void getRoleName() {
////        String roleName = SpUtils.getRoleName(this);
//        if ("ROLE_COMMON_CLIENT".equals(roleName)) {//普通用户
//            llServantCondInfo.setVisibility(View.GONE);
////            BecomeServant.setVisibility(View.VISIBLE);
//            RoleName.setVisibility(View.GONE);
////            BecomeServant.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    applyServant();
////                }
////            });
//
//        } else if ("ROLE_SERVANT_PRIMARY".equals(roleName)) {//初级代言人
//            RoleName.setVisibility(View.VISIBLE);
//        } else if ("ROLE_SERVANT_MIDDLE".equals(roleName)) {//中级代言人
//            RoleName.setVisibility(View.VISIBLE);
//            llMiddleServant.setVisibility(View.GONE);
//        } else {//高级代言人
//            RoleName.setVisibility(View.VISIBLE);
//            llServantCondInfo.setVisibility(View.GONE);
//        }
//
//    }

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

    //获得推荐的好友列表
    private void findFriendList() {
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
    }
    /**
     * 点击事件
     *
     */
    @OnClick({ R.id.btn_receiveReward, R.id.ll_wallet,R.id.iv_wxpyq})
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
                            if (handledAmount > 0){
                                MyToastUtils.showShortDebugToast(MyRecommAct.this,"领取奖励成功，奖励已发放到您的钱包中");
                                handledRewardAmount.setText("¥ "+(RewardAmount + handledAmount) +" 元");
                                sendBroadcast(new Intent(MineFragment.WALLET_POINT));
                            }else{
                                MyToastUtils.showShortDebugToast(MyRecommAct.this,"您已在其他终端领取，不可重复领取");
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
                        MyToastUtils.showShortToast(MyRecommAct.this,msg);
                    }
                });
                break;
            //累计收益进入我的钱包
            case R.id.ll_wallet:
                Intent intent2 = new Intent(MyRecommAct.this,  MyWalletActivity.class);
                intent2.putExtra("userLogin",ule);
                intent2.putExtra("userInfo",user);
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
        circleMedia.setShareContent(content + codeUrl);
        //设置朋友圈title
        circleMedia.setTitle(title);
        UMImage localImage = new UMImage(MyRecommAct.this, R.mipmap.logo);
        circleMedia.setShareImage(localImage);
//        circleMedia.setTargetUrl(codeUrl + ule.getMyReferralCode());
        circleMedia.setTargetUrl(codeUrl);
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
        sinaContent.setShareContent(content + codeUrl);
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
            ViewHolder vh;
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
                Bitmap bitmap;
                if (ule != null)
                    bitmap = FinancialUtil.createQRImage(yqUrl + ule.getMyReferralCode());
                else
                    bitmap = FinancialUtil.createQRImage(yqUrl);
                vh.iv_ewm.setScaleType(ImageView.ScaleType.FIT_XY);
                vh.iv_ewm.setImageBitmap(bitmap);
                if (ule != null)
                    vh.tvMyReferralCode.setText(ule.getMyReferralCode());
                if (datas != null && datas.size() > 0) {
                    vh.ll_tj_bg.setVisibility(View.VISIBLE);
//                    MyRecomBean.RowsEntity us = datas.get(position);
                    vh.nameTv.setText(datas.get(0).getUserName());
                    vh.phoneTv.setText(datas.get(0).getMobile());
                }
            } else if (position > 0) {
                vh.ll.setVisibility(View.GONE);
                if (datas != null && datas.size() > 0) {
                    vh.ll_tj_bg.setVisibility(View.VISIBLE);
//                    MyRecomBean.RowsEntity us = datas.get(position);
                    vh.nameTv.setText(datas.get(position).getUserName());
                    vh.phoneTv.setText(datas.get(position).getMobile());
                } else {
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

//    /**
//     * 代言人条件获取
//     */
//    private void getServantCondInfo() {
//        RequestManager.getCommManager().getServantCondInfo(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                JSONObject object = new JSONObject(result);
//                JSONArray data = object.getJSONArray("data");
//                Gson gson = new Gson();
//                List<ServantCondEntity> list = gson.fromJson(data.toString(), new TypeToken<List<ServantCondEntity>>() {
//                }.getType());
//                String mServantName = list.get(1).getServantName();
//                String hServantName = list.get(2).getServantName();
//                if (!TextUtils.isEmpty(mServantName)&&!TextUtils.isEmpty(hServantName)){
//                    tvmServantName.setText(mServantName);
//                    tvhServantName.setText(hServantName);
//                }
//                String mServantDesc = list.get(1).getServantDesc();
//                String hServantDesc = list.get(2).getServantDesc();
//                if (!TextUtils.isEmpty(mServantDesc)&&!TextUtils.isEmpty(hServantDesc)){
//                    tvmServantDesc.setText(mServantDesc);
//                    tvhServantDesc.setText(hServantDesc);
//                }
//                int mServantPassCond = list.get(1).getServantPassCond();
//                int hServantPassCond = list.get(2).getServantPassCond();
//                if (!TextUtils.isEmpty(String.valueOf(mServantPassCond))&&!TextUtils.isEmpty(String.valueOf(hServantPassCond))){
//                    tvmServantPassCond.setText(mServantPassCond+"");
//                    tvhServantPassCond.setText(hServantPassCond+"");
//                }
//                int mServantTotalCond = list.get(1).getServantTotalCond();
//                int hServantTotalCond = list.get(2).getServantTotalCond();
//                if (!TextUtils.isEmpty(String.valueOf(mServantTotalCond))&&!TextUtils.isEmpty(String.valueOf(hServantTotalCond))){
//                    tvmServantTotalCond.setText(mServantTotalCond+"");
//                    tvhServantTotalCond.setText(hServantTotalCond+"");
//                }
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }
//
//    /*我要成为代言人*/
//    private void applyServant() {
//        RequestManager.getCommManager().applyServant(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                BecomeServant.setVisibility(View.GONE);
//                JSONObject object = new JSONObject(result);
//                String message = object.getString("message");
//                MyToastUtils.showShortToast(getApplicationContext(), "恭喜成为代言人，请重新登录");
//                SpUtils.setRoleName(getApplicationContext(), "ROLE_SERVANT_PRIMARY");
////                getRoleInfo();
////                Intent intent = new Intent(MyRecommAct.this, LoginAct.class);
////                startActivity(intent);
//                logout();
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }

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
//                tvRecommedUserCount.setText(recommedUserCnt + "");
                float num= (float)LoanAmount/10000;
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                String s = df.format(num);//返回的是String类型
                recommendedLoanAmount.setText("¥ "+s + " 万元");
                recommendedCCardAmount.setText(CCardAmount + "");
                handledRewardAmount.setText("¥ "+RewardAmount + " 元");
                unhandledRewardAmount.setText("待领取 "+unhandledAmount + " 元");
                if (unhandledAmount>0){
                    alreadyImg.setVisibility(View.GONE);
                    unhandledRewardAmount.setVisibility(View.VISIBLE);
                    btnReward.setVisibility(View.VISIBLE);
                }else {
                    alreadyImg.setVisibility(View.VISIBLE);
                    unhandledRewardAmount.setVisibility(View.GONE);
                    btnReward.setVisibility(View.GONE);
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
    private void getReceiveReward(UserEntity userEntity){
        RequestManager.getCommManager().getReceiveReward(userEntity,new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(),"恭喜您已成功领取奖励，请至我的钱包查收");
                ParamsUtil.getInstance().setWalletEnter(true);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(),"已领取过奖励，用户信息修改成功");
                finish();
            }
        });
    }
    //    //获取角色信息
//    private void getRoleInfo() {
//        RequestManager.getCommManager().getRoleInfo(new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
//                JSONObject object = new JSONObject(result);
//                JSONObject data = object.getJSONObject("data");
//                roleName = data.getString("name");
//                description = data.getString("description");
//                SpUtils.setRoleName(getApplicationContext(), roleName);
//                if (!TextUtils.isEmpty(description)) {
//                    RoleName.setText(description);
//                }
////                getServantCondInfo();
//                getRoleName();
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }
}
