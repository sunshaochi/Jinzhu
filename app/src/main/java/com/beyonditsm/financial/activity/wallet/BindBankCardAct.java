package com.beyonditsm.financial.activity.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.BindBankCardAdp;
import com.beyonditsm.financial.entity.QueryBankCardEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定银行卡
 * Created by liwk on 2016/3/15.
 */
public class BindBankCardAct extends BaseActivity {
    @ViewInject(R.id.lv_bankcard)
    private ListViewForScrollView lvBankCard;
//    private MyScrollListView lvBankCard;
    @ViewInject(R.id.ll_addBankCard)
    private LinearLayout llAddBankCard;
    @ViewInject(R.id.tv_setzjpassword)
    private TextView tvSetzjPassword;
    @ViewInject(R.id.tv_surebind)
    private TextView tvSureBind;
    @ViewInject(R.id.sv_bindBankCard)
    private ScrollView svBindBankCard;
    private UserEntity user;//用户实体
    private List<QueryBankCardEntity> bankCardList = new ArrayList<>();
    private BindBankCardAdp adapter;
    private MyBroadcastReceiver receiver;
    private int status = 1;
    private String cardNo;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_bindbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("绑定银行卡");
        setLeftTv("返回");
        user=getIntent().getParcelableExtra("userInfo");
        svBindBankCard.smoothScrollTo(0, 0);
        findBankCard();

    }
    @OnClick({R.id.ll_addBankCard,R.id.tv_setzjpassword,R.id.tv_surebind})
    public void todo(View view){
        Intent intent=null;
        switch (view.getId()){
            case R.id.ll_addBankCard:
                intent = new Intent(BindBankCardAct.this,AddBankCardAct.class);
                intent.putExtra("userinfo",user);
                startActivity(intent);
                break;
            case R.id.tv_setzjpassword:
                intent=new Intent(BindBankCardAct.this,SetPwdActivity.class);
                intent.putExtra("userPhone",user.getAccountName());
                startActivity(intent);
                break;
            case R.id.tv_surebind:
                finish();
                break;
        }
    }
    /*查询绑定银行卡*/
    private void findBankCard(){
        RequestManager.getWalletManager().findBankCard(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONArray data = object.getJSONArray("data");
                Gson gson = new Gson();
                List<QueryBankCardEntity> list = gson.fromJson(data.toString(), new TypeToken<List<QueryBankCardEntity>>() {
                }.getType());
//                if (list==null) {
//                    lvBankCard.setVisibility(View.GONE);
//                }

                if (adapter==null){
                    adapter = new BindBankCardAdp(BindBankCardAct.this, list);
                    lvBankCard.setAdapter(adapter);
                }else{
                    adapter.setNotifyChange(list);
                }

            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(BindBankCardAct.this,msg);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver==null) {
            receiver = new MyBroadcastReceiver();
        }
        registerReceiver(receiver,new IntentFilter(ADDBANKCARD));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    public static final String ADDBANKCARD = "com.bind.bankcard";
    class  MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            findBankCard();
        }
    }

}
