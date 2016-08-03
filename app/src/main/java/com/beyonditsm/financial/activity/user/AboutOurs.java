package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by gxy on 2016/1/7
 */
public class AboutOurs extends BaseActivity {
    @ViewInject(R.id.clause)
    private WebView clause;//关于我们


    @Override
    public void setLayout() {
        setContentView(R.layout.act_clausejinzhu);
    }

    public void play(){

    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        play();
        setTopTitle("关于我们");
        setLeftTv("返回");
        //如果访问的页面中有javascript，则webview必须设置支持javascript
        clause.getSettings().setJavaScriptEnabled(true);
        clause.getSettings().setAllowFileAccess(true);
        clause.getSettings().setAppCacheEnabled(true);//开启Application caches
        clause.getSettings().setDomStorageEnabled(true);
        clause.getSettings().setDatabaseEnabled(true);

        clause.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        //webview加载web资源
        String clause_url = "file:///android_asset/aboutOurs.html";
        clause.loadUrl(clause_url);
        //覆盖webview使用默认浏览器或第三方浏览器打开网页的行为，使网页用webview打开
        clause.setWebViewClient(new WebViewClient(){
            //返回值是true的时候控制去webview打开，false时调用系统浏览器或第三方浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(clause.canGoBack()) {
                clause.goBack();// 返回前一个页面
            }else {
                finish();
            }
//                System.exit(0);//退出程序
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clause.destroy();
    }
}
