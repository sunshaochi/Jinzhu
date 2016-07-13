package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.UriToPath;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏
 * Created by wangbin on 15/12/7.
 */
@SuppressWarnings({"deprecation", "StatementWithEmptyBody"})
public class GameActivity extends BaseActivity {
    @ViewInject(R.id.wvGame)
    private WebView wvGame;
    @ViewInject(R.id.ll_gameLayout)
    private LinearLayout llGameLayout;

    private String gUrl;
    private Intent intent;

    public static final String GAME_TYPE = "game_type";
    private static final String APP_CACAHE_DIRNAME = "/gamecache";

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    private MyBroadCastReceiver receiver;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_game);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("信用耕耘");
        setLeftTv("返回");
        String cookie[] = SpUtils.getCookie(this).split("=");
        gUrl = IFinancialUrl.GAME_URL + "?JSESSIONID=" + cookie[1].substring(0, cookie[1].length() - 1);
//        MyLogUtils.info(gUrl);
        wvGame.loadUrl(gUrl);
        wvGame.getSettings().setJavaScriptEnabled(true);
        wvGame.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebSettings settings = wvGame.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
//        String cacheDirPath = getApplicationContext().getDir("gameCache", Context.MODE_PRIVATE).getPath();

        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
        MyLogUtils.info("cacheDirPath+"+cacheDirPath);
        //设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        settings.setAllowFileAccess(true);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);

        wvGame.setWebViewClient(new WebViewClient() {
            // 点击页面中的链接会调用这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                MyLogUtils.info(url);
                if (url.endsWith("order")) {
//                    intent = new Intent(GameActivity.this, MainActivity.class);
//
//                    Intent intentBroad = new Intent(MainActivity.UPDATATAB);
//                    intentBroad.putExtra(GAME_TYPE, 1);
//                    sendBroadcast(intentBroad);
//                    startActivity(intent);
                    intent = new Intent(GameActivity.this,MyCreditAct.class);
                    startActivity(intent);
                } else if (url.endsWith("task")) {
//                    intent = new Intent(GameActivity.this, HardCreditAct.class);
//                    startActivity(intent);
                } else if (url.endsWith("friend")) {
                    intent = new Intent(GameActivity.this, AddressBookAct.class);
                    startActivity(intent);
                } else if (url.endsWith("house")) {
                    intent = new Intent(GameActivity.this, HardCreditAct.class);
                    startActivity(intent);
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }


        });

        wvGame.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

            // Android > 4.1.1 调用这个方法
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                mUploadMessage = uploadMsg;
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//               startActivityForResult(
//                        Intent.createChooser(intent, "Image Chooser"), FILECHOOSER_RESULTCODE);
//            }

        });




    }

    protected void onDestroy() {
        super.onDestroy();
//        setConfigCallback(null);
//        wvGame.destroy();
        llGameLayout.removeView(wvGame);
        wvGame.destroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }
        registerReceiver(receiver, new IntentFilter(RELOAD));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvGame.canGoBack()) {
            wvGame.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    public void setConfigCallback(WindowManager windowManager) {
//        try {
//            Field field = WebView.class.getDeclaredField("mWebViewCore");
//            field = field.getType().getDeclaredField("mBrowserFrame");
//            field.setAccessible(true);
//            Object configCallback = field.get(null);
//
//            if (null == configCallback) {
//                return;
//            }
//            field = field.getType().getDeclaredField("mWindowManager");
//            field.setAccessible(true);
//            field.set(configCallback, windowManager);
//        } catch (Exception e) {
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
//                MyLogUtils.info(;"result + "");
                if (result == null) {
//	            		mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                    MyLogUtils.error(imageUri + "");
//                    Log.e("imageUri",imageUri+"");
                } else {
                    String path = UriToPath.getImageAbsolutePath(this, result);
                    Uri uri = Uri.fromFile(new File(path));
                    mUploadMessage.onReceiveValue(uri);
//                    mUploadMessage.onReceiveValue(result);
//                    mUploadMessage = null;
                }


            }
        }
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    public static final String RELOAD = "com.game.reload";

    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            wvGame.loadUrl(gUrl);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvGame.onPause();
    }


}


