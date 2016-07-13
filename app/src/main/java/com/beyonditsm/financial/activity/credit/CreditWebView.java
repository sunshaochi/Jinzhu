package com.beyonditsm.financial.activity.credit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CreditWebView extends BaseActivity {

    @ViewInject(R.id.credit_webView)
    private WebView webView;
    @Override
    public void setLayout() {
        setContentView(R.layout.credit_web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("申请信用卡");
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
        webView.removeJavascriptInterface("searchBoxJavaBredge_");
//        webView.requestFocusFromTouch();
//        wvCreditCard.loadUrl("http://www.baidu.com");
        webView.loadUrl("https://mbank.spdbccc.com.cn/creditcard/indexActivity.htm?data=756023011");
//        webView.setWebViewClient(new WebViewClient(){
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.reload();

    }
}
