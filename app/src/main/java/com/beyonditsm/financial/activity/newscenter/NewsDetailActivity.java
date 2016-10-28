package com.beyonditsm.financial.activity.newscenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.HotNewsEntity;
import com.beyonditsm.financial.entity.NewsRelativeEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ShareUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.tv_lastTitle)
    private TextView tvLastTitle;
    @ViewInject(R.id.tv_nextTitle)
    private TextView tvNextTitle;
    @ViewInject(R.id.rl_right)
    private RelativeLayout rlRight;
    Intent intent;
    private HotNewsEntity hotNewsEntity;
    private AlertDialog.Builder builder;
    private UMSocialService mController;
    private NewsRelativeEntity newsRelativeEntity;
    private String currentId;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_news_detail);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        intent = getIntent();
        if(TextUtils.isEmpty(currentId)){
            hotNewsEntity = intent.getParcelableExtra("hotnews");
            currentId = hotNewsEntity.getId();
        }
        getMoreArticleInfo(currentId);
        setShareInfo();
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.getConfig().closeToast();
        ShareUtils.getInstance().init(this, mController);

    }

    @Override
    public void setLeftTv(String tv) {
        super.setLeftTv("返回");
    }

    /**
     * 获取文章详情
     * @param curId 文章id
     */
    private void getMoreArticleInfo(String curId) {
        CommManager.getCommManager().findUpAndDownRow(curId, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject value = new JSONObject(result);
                ResultData<NewsRelativeEntity> rd = (ResultData<NewsRelativeEntity>) GsonUtils.json(result, NewsRelativeEntity.class);
                newsRelativeEntity = rd.getData();
                tvLastTitle.setText("上一篇： " + newsRelativeEntity.getUpRow().getTitle());
                tvNextTitle.setText("下一篇： " + newsRelativeEntity.getDownRow().getTitle());
//                NewsDetailActivity.super.setTopTitle(newsRelativeEntity.getCurRow().getTitle());
                setWebView();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void setShareInfo() {
        String[] typeNames = new String[]{"分享到微信好友", "分享到朋友圈", "分享到QQ", "分享到新浪微博"};
        int[] icons = new int[]{R.mipmap.weixin, R.mipmap.weixin_pyq, R.mipmap.qq, R.mipmap.weibo};
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("icon", icons[i]);
            map.put("typeName", typeNames[i]);
            list.add(map);
        }
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_share, (ViewGroup) findViewById(R.id.share_dialog));
        ListView listView = (ListView) dialog.findViewById(R.id.lv_share);
        listView.setAdapter(new SimpleAdapter(NewsDetailActivity.this, list, R.layout.dialog_share_item, new String[]{
                "icon", "typeName"
        }, new int[]{
                R.id.iv_icon, R.id.tv_type
        }));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ShareUtils.getInstance().weixinShare("金蛛金服，业内史无前例的贴息返佣力度，只做最快速、方便、低息的银行贷款。成为代言人，和小伙伴一起赚钱，成为土豪不是梦~", hotNewsEntity.getTitle(), hotNewsEntity.getUrlPath(), null);
                        break;
                    case 1:
                        ShareUtils.getInstance().weixinCircleShare("金蛛金服，业内史无前例的贴息返佣力度，只做最快速、方便、低息的银行贷款。成为代言人，和小伙伴一起赚钱，成为土豪不是梦~", hotNewsEntity.getTitle(), hotNewsEntity.getUrlPath(), null);
                        break;
                    case 2:
                        ShareUtils.getInstance().qqShare("金蛛金服，业内史无前例的贴息返佣力度，只做最快速、方便、低息的银行贷款。成为代言人，和小伙伴一起赚钱，成为土豪不是梦~", hotNewsEntity.getTitle(), hotNewsEntity.getUrlPath(), null);
                        break;
                    case 3:
                        ShareUtils.getInstance().weiboShare("金蛛金服，业内史无前例的贴息返佣力度，只做最快速、方便、低息的银行贷款。成为代言人，和小伙伴一起赚钱，成为土豪不是梦~", hotNewsEntity.getUrlPath(), null);
                        break;
                    default:
                        break;
                }
            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setView(dialog);

    }

    private void setWebView() {

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
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
        if ("".equals(newsRelativeEntity.getCurRow().getUrlPath()) || null == newsRelativeEntity.getCurRow().getUrlPath()) {
            webView.loadData(newsRelativeEntity.getCurRow().getDescription(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
            rlRight.setVisibility(View.GONE);

        } else {
            rlRight.setVisibility(View.VISIBLE);
            webView.loadUrl(newsRelativeEntity.getCurRow().getUrlPath());//这种写法可以正确解码
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != intent)
            intent.removeExtra("hotnews");
    }


    @OnClick({R.id.rl_right, R.id.tv_nextTitle, R.id.tv_lastTitle})
    public void toClick(View v) {
        switch (v.getId()) {
            case R.id.rl_right:
                setShareInfo();
                builder.show();
                break;
            case R.id.tv_nextTitle:
                if (newsRelativeEntity.getDownRow().getStatus() != 0) {
                    getMoreArticleInfo(newsRelativeEntity.getDownRow().getDownId() + "");
                }
                break;
            case R.id.tv_lastTitle:
                if (newsRelativeEntity.getUpRow().getStatus() != 0) {
                    getMoreArticleInfo(newsRelativeEntity.getUpRow().getUpId() + "");
                }
                break;
        }
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

}
