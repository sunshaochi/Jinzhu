package com.beyonditsm.financial.activity.user.creditcard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.CreditWebView;
import com.beyonditsm.financial.activity.user.ApplicationAct;
import com.beyonditsm.financial.adapter.CreditCardItemAdp;
import com.beyonditsm.financial.entity.CreditCardEntity;
import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡专题页面
 * Created by xuleyuan on 2016/5/25.
 */
public class CreditCardAct extends BaseActivity implements CreditCardInterface {
    //    @ViewInject(R.id.iv_wallet)
//    private ImageView ivWallet;
    private UserEntity user;//用户信息
    private UserLoginEntity ule;//用户登陆信息
    public static final String BANK_NAME = "bank_name";
    @ViewInject(R.id.lv_creditCard)
    private LoadRefreshView lvCreditCard;
    @ViewInject(R.id.ll_creditCardBottom)
    private LinearLayout llCreditCardBottom;
    @ViewInject(R.id.loadView)
    private LoadingView loadingView;
    private List<CreditCardEntity.CreditCardsBean> cardList;
    private CreditCardItemAdp adapter;
    private int currentPage;

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


//    @Override
//    protected void onResume() {
//        super.onResume();
//        setWalletPic();
//    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("信用卡专题");
        currentPage = 1;
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCreditCard(currentPage, SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));
            }
        });
        lvCreditCard.setPullRefreshEnabled(true);
        lvCreditCard.setScrollLoadEnabled(true);
        lvCreditCard.setPullLoadEnabled(false);
        lvCreditCard.setHasMoreData(true);
        lvCreditCard.getRefreshableView().setDivider(null);
        lvCreditCard.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvCreditCard.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getCreditCard(currentPage, SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getCreditCard(currentPage, SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));
            }
        });
        getCreditCard(currentPage, SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));
    }

//    @SuppressWarnings("deprecation")
//    private void setWalletPic() {
//        if (ParamsUtil.getInstance().isWalletEnter()){
//            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag));
//        }else {
//            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag_unred));
//        }
//    }

    //    @OnClick({R.id.iv_wallet,R.id.iv_application,R.id.iv_receiveReward,R.id.qrcode_layout,R.id.pfCreditCard})
//    public void todo(View view){
//        Intent intent;
//        switch (view.getId()){
//            case R.id.iv_receiveReward:
//                intent = new Intent(CreditCardAct.this,ReceiveRewardAct.class);
//                startActivity(intent);
//                break;
//            case R.id.iv_application:
//                intent = new Intent(CreditCardAct.this, ApplicationAct.class);
//                startActivity(intent);
//                break;
//            case R.id.iv_wallet:
//                intent = new Intent(CreditCardAct.this, MyWalletActivity.class);
//                intent.putExtra("userLogin",ule);
//                intent.putExtra("userInfo",user);
//                startActivity(intent);
//                break;
//            case R.id.qrcode_layout:
//                intent = new Intent(CreditCardAct.this, CreditWebView.class);
//                intent.putExtra(BANK_NAME,"guangda");
//                startActivity(intent);
//                break;
//            case R.id.pfCreditCard:
//                intent = new Intent(CreditCardAct.this, CreditWebView.class);
//                intent.putExtra(BANK_NAME,"pufa");
//                startActivity(intent);
//                break;
//        }
//    }
    private List<CreditCardEntity.CreditCardsBean> datas = new ArrayList<>();

    /**
     * 获取信用卡列表数据的方法
     *
     * @param Page 当前需要请求的页
     * @param area 用户所在区域
     */
    public void getCreditCard(final int Page, String area) {
        if (area == null || "".equals(area)) {
            area = "全国";
        }
        HotProduct hp = new HotProduct();
        hp.setPage(Page);
        hp.setRows(5);
        RequestManager.getCommManager().getCreditCards(hp, area, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                lvCreditCard.onPullDownRefreshComplete();
                lvCreditCard.onPullUpRefreshComplete();
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                JSONArray rows = data.getJSONArray("rows");
                Gson gson = new Gson();
                cardList = gson.fromJson(rows.toString(), new TypeToken<List<CreditCardEntity.CreditCardsBean>>() {
                }.getType());
//                if (data == null) {
//                    loadingView.noContent();
//                    return;
//                }
                MyLogUtils.degug("cardListInfo: "+cardList+"");
                if (cardList == null || cardList.size() == 0) {
//                    adapter.setDatas(datas ,isLast(cardList));
//                    adapter.setOnCreditCardListner(CreditCardAct.this);
//                    adapter.notifyDataSetChanged();
                    if (Page == 1) {
                        loadingView.noContent();
                    } else {
                        lvCreditCard.setHasMoreData(false);
                    }
                    return;
                }
                if (Page == 1) {
                    datas.clear();
                }
                datas.addAll(cardList);
                if (adapter == null) {
                    adapter = new CreditCardItemAdp(CreditCardAct.this, datas, isLast(cardList));
                    adapter.setOnCreditCardListner(CreditCardAct.this);
                    lvCreditCard.getRefreshableView().setAdapter(adapter);

                } else {
                    adapter.setDatas(datas ,isLast(cardList));
                    adapter.setOnCreditCardListner(CreditCardAct.this);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(int status, String msg) {
                lvCreditCard.onPullDownRefreshComplete();
                lvCreditCard.onPullUpRefreshComplete();
                loadingView.loadError();
            }
        });
    }

    /*判断是否是最后一页数据*/
    private boolean isLast(List<CreditCardEntity.CreditCardsBean> cardList) {
        return cardList == null || cardList.size() < 5;
    }

    /*该方法请求后台用于记录用户点击信用卡数据*/
    public void applyCreditCardClick(String creditCardId) {
        RequestManager.getCommManager().applyCreditCardClick(creditCardId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 当点击信用卡申请调用的方法
     *
     * @param id        信用卡id
     * @param mobileUrl 对应的信用卡Web链接
     */
    @Override
    public void onApply(String id, String mobileUrl) {
        Intent intent = new Intent(CreditCardAct.this, CreditWebView.class);
                intent.putExtra(BANK_NAME,mobileUrl);
                startActivity(intent);
        applyCreditCardClick(id);
    }

    /**
     * 当点击最后视图的申请贷款按钮调用的方法
     */
    @Override
    public void onClickApplyCredit() {
        startActivity(new Intent(CreditCardAct.this, ApplicationAct.class));
    }
}
