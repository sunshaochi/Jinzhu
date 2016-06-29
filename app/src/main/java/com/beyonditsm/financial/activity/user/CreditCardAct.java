package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.CreditWebView;
import com.beyonditsm.financial.activity.wallet.MyWalletActivity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 信用卡专题页面
 * Created by Administrator on 2016/5/25.
 */
public class CreditCardAct extends BaseActivity {
    @ViewInject(R.id.wv_CreditCard)
    private WebView wvCreditCard;
    @ViewInject(R.id.iv_wallet)
    private ImageView ivWallet;
    private UserEntity user;//用户信息
    private UserLoginEntity ule;//用户登陆信息
    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditcard);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getScreenAttr();
//
//    }

//    private void getScreenAttr() {
//        DisplayMetrics dm = new DisplayMetrics();
//        dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
//        screenWidth = dm.widthPixels; // 屏幕宽（dip，如：320dip）
//        screenHeight = dm.heightPixels; // 屏幕宽（dip，如：533dip）
//    }
//    public static void setMargins (View v, int l, int t, int r, int b) {
//        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            p.setMargins(l, t, r, b);
//            v.requestLayout();
//        }
//    }
//    public static void setSize (View v, int width, int height) {
//        ViewGroup.LayoutParams lp;
//        lp=v.getLayoutParams();
//        lp.width=width;
//        lp.height=height;
//        v.setLayoutParams(lp);
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        setWalletPic();
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("信用卡专题");
        getUserLoginInfo();
        getUserInfo();
        WebSettings settings = wvCreditCard.getSettings();
        settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        wvCreditCard.removeJavascriptInterface("searchBoxJavaBredge_");
        wvCreditCard.requestFocusFromTouch();
//        wvCreditCard.loadUrl("http://www.baidu.com");
        wvCreditCard.loadUrl("http://172.16.5.16:8100/#/tab/home?redirctUrl=/tab/home/spdcredits/spdCredit");
        wvCreditCard.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        wvCreditCard.reload();


    }

    private void setWalletPic() {
        if (ParamsUtil.getInstance().isWalletEnter()){
            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag_red));
        }else {
            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag));
        }
    }

    @OnClick({R.id.iv_wallet,R.id.iv_application,R.id.iv_receiveReward,R.id.qrcode_layout})
    public void todo(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.iv_receiveReward:
                intent = new Intent(CreditCardAct.this,ReceiveRewardAct.class);
                startActivity(intent);
                break;
            case R.id.iv_application:
                intent = new Intent(CreditCardAct.this, ApplicationAct.class);
                startActivity(intent);
                break;
            case R.id.iv_wallet:
                intent = new Intent(CreditCardAct.this, MyWalletActivity.class);
                intent.putExtra("userLogin",ule);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                break;
            case R.id.qrcode_layout:
                intent = new Intent(CreditCardAct.this, CreditWebView.class);
                startActivity(intent);
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
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

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                ule = rd.getData();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
