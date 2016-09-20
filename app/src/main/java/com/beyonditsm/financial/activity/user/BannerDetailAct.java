package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.HomeFragment;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class BannerDetailAct extends BaseActivity {
    @ViewInject(R.id.wv_bannerdetail)
    private WebView wvBannerDetail;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_bannerdetail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        String bannerName = getIntent().getStringExtra(HomeFragment.BANNER_NAME);
        String Address = getIntent().getStringExtra(HomeFragment.HREF_ADDR);
        setTopTitle(bannerName);
        setLeftTv("返回");
        //如果访问的页面中有javascript，则webview必须设置支持javascript
        wvBannerDetail.getSettings().setJavaScriptEnabled(true);
        wvBannerDetail.getSettings().setAllowFileAccess(true);
        wvBannerDetail.getSettings().setAppCacheEnabled(true);//开启Application caches
        wvBannerDetail.getSettings().setDomStorageEnabled(true);
        wvBannerDetail.getSettings().setDatabaseEnabled(true);

        wvBannerDetail.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        wvBannerDetail.loadUrl(Address);
        //覆盖webview使用默认浏览器或第三方浏览器打开网页的行为，使网页用webview打开
        wvBannerDetail.setWebViewClient(new WebViewClient(){
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
            if(wvBannerDetail.canGoBack()) {
                wvBannerDetail.goBack();// 返回前一个页面
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
        if (wvBannerDetail != null) {
            ViewGroup parent = (ViewGroup) wvBannerDetail.getParent();
            if (parent != null) {
                parent.removeView(wvBannerDetail);
            }
            wvBannerDetail.removeAllViews();
            wvBannerDetail.destroy();
        }
        super.onDestroy();
    }
}
