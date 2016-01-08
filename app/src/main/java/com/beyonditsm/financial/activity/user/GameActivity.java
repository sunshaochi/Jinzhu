package com.beyonditsm.financial.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;

/**
 * 游戏
 * Created by wangbin on 15/12/7.
 */
public class GameActivity extends BaseActivity {
    @ViewInject(R.id.wvGame)
    private WebView wvGame;
//    @ViewInject(R.id.llGame)
//    private LinearLayout llGame;

//    private String game_url="http://139.196.111.82:5011/";

    private String gUrl;
    private Intent intent;

    public static final String GAME_TYPE="game_type";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_game);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("信用耕耘");
        setLeftTv("返回");
        setConfigCallback((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        String cookie[]=SpUtils.getCookie(this).split("=");
         gUrl= IFinancialUrl.GAME_URL+"?JSESSIONID="+cookie[1].substring(0,cookie[1].length()-1);

        // 设置可以访问文件
        wvGame.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        wvGame.getSettings().setJavaScriptEnabled(true);
        wvGame.getSettings().setAllowFileAccess(true);
        wvGame.getSettings().setAppCacheEnabled(true);
        wvGame.getSettings().setDomStorageEnabled(true);
        wvGame.getSettings().setDatabaseEnabled(true);

        wvGame.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        //开启 Application Caches 功能
        wvGame.getSettings().setAppCacheEnabled(true);

//        syncCookie(this,gUrl);
        wvGame.loadUrl(gUrl);

        wvGame.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wvGame.setWebViewClient(new WebViewClient() {

            // 点击页面中的链接会调用这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLogUtils.info(url);
                if (url.endsWith("order")) {
                    intent = new Intent(GameActivity.this, MainActivity.class);

                    Intent intentBroad = new Intent(MainActivity.UPDATATAB);
                    intentBroad.putExtra(GAME_TYPE, 1);
                    sendBroadcast(intentBroad);
                    startActivity(intent);
                } else if (url.endsWith("task")) {
                    intent = new Intent(GameActivity.this, HardCreditAct.class);
                    startActivity(intent);
                } else if (url.endsWith("friend")) {
                    intent = new Intent(GameActivity.this, AddressBookAct.class);
                    startActivity(intent);
                }
                else if (url.endsWith("house")) {
                    intent = new Intent(GameActivity.this, HardCreditAct.class);
                    startActivity(intent);
                }


                return true;
            }

        });

    }

    protected void onDestroy() {
        super.onDestroy();
        setConfigCallback(null);
        wvGame.destroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvGame.canGoBack()) {
            wvGame.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }
            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch(Exception e) {
        }
    }

    /**
     * Sync Cookie
     */
//    private void syncCookie(Context context, String url){
//        try{
//
//            CookieSyncManager.createInstance(context);
//
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//            cookieManager.removeSessionCookie();// 移除
//            cookieManager.removeAllCookie();
//            String oldCookie = cookieManager.getCookie(url);
//            if(oldCookie != null){
////                Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
//            }
//
//            StringBuilder sbCookie = new StringBuilder();
//            String cookie[]=SpUtils.getCookie(context).split("=");
//            sbCookie.append(String.format("JSESSIONID=%s",cookie[1].substring(0,cookie[1].length()-1)));
//            sbCookie.append(String.format(";domain=%s",  IFinancialUrl.BASE_URL));
//            sbCookie.append(String.format(";path=%s","/"));
//
//            String cookieValue = sbCookie.toString();
//            cookieManager.setCookie(url, cookieValue);
//            CookieSyncManager.getInstance().sync();
//
//            String newCookie = cookieManager.getCookie(url);
//            if(newCookie != null){
//                MyLogUtils.degug("Nat: webView.syncCookie.newCookie"+ newCookie);
//            }
//        }catch(Exception e){
////            Log.e("Nat: webView.syncCookie failed", e.toString());
//        }
//    }
}
