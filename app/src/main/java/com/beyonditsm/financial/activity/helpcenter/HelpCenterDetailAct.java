package com.beyonditsm.financial.activity.helpcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ActicleListBean;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 帮助中心详情页面
 * Created by Administrator on 2016/10/11 0011.
 */

public class HelpCenterDetailAct extends BaseActivity {
    @ViewInject(R.id.wv_helpCenterDetail)
    private WebView wvHelpCenterDetail;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_helpcenterdetail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        setTopTitle(title);
        setLeftTv("返回");
        ActicleListBean acticleListBean = getIntent().getParcelableExtra("helpSecond");
        setTopTitle(acticleListBean.getTitle());
        WebSettings settings = wvHelpCenterDetail.getSettings();
        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
        wvHelpCenterDetail.removeJavascriptInterface("searchBoxJavaBredge_");
//        webView.requestFocusFromTouch();
//        wvCreditCard.loadUrl("http://www.baidu.com");
        wvHelpCenterDetail.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        if (!TextUtils.isEmpty(acticleListBean.getUrl())) {
            wvHelpCenterDetail.loadUrl(acticleListBean.getUrl());
        } else {
            wvHelpCenterDetail.loadData(acticleListBean.getContent(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
        }
    }
}
