package com.beyonditsm.financial.activity.newscenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.HotNewsEntity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.sp_share)
    private Spinner spShare;
    Intent intent;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_news_detail);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        intent = getIntent();
        HotNewsEntity hotNewsEntity = intent.getParcelableExtra("hotnews");
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
        webView.removeJavascriptInterface("searchBoxJavaBredge_");
//        webView.requestFocusFromTouch();
//        wvCreditCard.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        if ("".equals(hotNewsEntity.getDescription()) || null == hotNewsEntity.getDescription()) {
            webView.loadUrl(hotNewsEntity.getUrlPath());
        } else {
            webView.loadData(hotNewsEntity.getDescription(), "text/html", "UTF-8");
        }
        String[] items = new String[] {"微信","朋友圈","QQ"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.item_spinner, items);
        spShare.setAdapter(adapter);
        spShare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.main_color));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != intent)
            intent.removeExtra("hotnews");
    }

    @Override
    public void setRightVG(boolean isVisible) {
        super.setRightVG(true);
    }

    @Override
    public void setRightBtn(String rightText, View.OnClickListener listener) {
        super.setRightBtn("...", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
