package com.beyonditsm.financial.activity.credit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.user.creditcard.CreditCardAct;
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
        String bankName = getIntent().getStringExtra(CreditCardAct.BANK_NAME);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
        webView.removeJavascriptInterface("searchBoxJavaBredge_");
//        webView.requestFocusFromTouch();
//        wvCreditCard.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        if ("guangda".equals(bankName)){
            webView.loadUrl("https://xyk.cebbank.com/cebmms/apply/ps/apply-card-list.htm?pro_code=FHTG17000SJC01FZQH");
        }else{
            webView.loadUrl("https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=P730548");
        }

//        webView.reload();

    }
}
