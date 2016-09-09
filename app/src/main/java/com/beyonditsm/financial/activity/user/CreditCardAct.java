package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.CreditWebView;
import com.beyonditsm.financial.activity.wallet.MyWalletActivity;
import com.beyonditsm.financial.adapter.CreditCardItemAdp;
import com.beyonditsm.financial.entity.CredirCardEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡专题页面
 * Created by Administrator on 2016/5/25.
 */
public class CreditCardAct extends BaseActivity {
    @ViewInject(R.id.iv_wallet)
    private ImageView ivWallet;
    private UserEntity user;//用户信息
    private UserLoginEntity ule;//用户登陆信息
    public static final String BANK_NAME = "bank_name";
    @ViewInject(R.id.lv_creditCard)
    private PullToRefreshListView lvCreditCard;
    @ViewInject(R.id.ll_creditCardBottom)
    private LinearLayout llCreditCardBottom;
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


    @Override
    protected void onResume() {
        super.onResume();
        setWalletPic();
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("信用卡专题");
//        getUserLoginInfo();
//        getUserInfo();
        lvCreditCard.setPullRefreshEnabled(false);
        lvCreditCard.setScrollLoadEnabled(false);
        lvCreditCard.setPullLoadEnabled(true);
        lvCreditCard.setHasMoreData(false);
        lvCreditCard.getRefreshableView().setDivider(null);
        lvCreditCard.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvCreditCard.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lvCreditCard.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        lvCreditCard.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
               if (!lvCreditCard.hasMoreData()){
                   llCreditCardBottom.setVisibility(View.VISIBLE);
               }
            }
        });
        List<CredirCardEntity> list = new ArrayList<>();
        for (int i = 0;i<4;i++){
            CredirCardEntity credirCardEntity = new CredirCardEntity();
//            credirCardEntity.setItem("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3169350942,1785088152&fm=58&s=E812709CDCB0BE09081EDED4030030BC&bpow=1000&bpoh=387");
//            credirCardEntity.setItem("http://img2.imgtn.bdimg.com/it/u=1346329280,89502990&fm=11&gp=0.jpg");
//            credirCardEntity.setItem("http://img0.imgtn.bdimg.com/it/u=1658719497,248833683&fm=11&gp=0.jpg");
//            credirCardEntity.setItem("http://img2.imgtn.bdimg.com/it/u=76984323,1456456319&fm=11&gp=0.jpg");
            credirCardEntity.setItem("http://img1.imgtn.bdimg.com/it/u=286138895,4263255804&fm=11&gp=0.jpg");
            list.add(credirCardEntity);
        }
        CreditCardItemAdp creditCardItemAdp = new CreditCardItemAdp(CreditCardAct.this, list);
        lvCreditCard.getRefreshableView().setAdapter(creditCardItemAdp);
    }

    @SuppressWarnings("deprecation")
    private void setWalletPic() {
        if (ParamsUtil.getInstance().isWalletEnter()){
            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag));
        }else {
            ivWallet.setImageDrawable(getResources().getDrawable(R.mipmap.money_bag_unred));
        }
    }

    @OnClick({R.id.iv_wallet,R.id.iv_application,R.id.iv_receiveReward,R.id.qrcode_layout,R.id.pfCreditCard})
    public void todo(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.iv_receiveReward:
                intent = new Intent(CreditCardAct.this,ReceiveRewardAct.class);
                startActivity(intent);
                break;
            case R.id.iv_application:
                intent = new Intent(CreditCardAct.this, ApplicationAct.class);
                startActivity(intent);
                break;
            case R.id.iv_wallet:
                intent = new Intent(CreditCardAct.this, MyWalletActivity.class);
                intent.putExtra("userLogin",ule);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                break;
            case R.id.qrcode_layout:
                intent = new Intent(CreditCardAct.this, CreditWebView.class);
                intent.putExtra(BANK_NAME,"guangda");
                startActivity(intent);
                break;
            case R.id.pfCreditCard:
                intent = new Intent(CreditCardAct.this, CreditWebView.class);
                intent.putExtra(BANK_NAME,"pufa");
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();

            }

            @Override
            public void onError(int status, String msg) {
            }
        });
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200){
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
