package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.UserEntity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 绑定银行卡
 * Created by liwk on 2016/3/15.
 */
public class BindBankCardAct extends BaseActivity {
    @ViewInject(R.id.lv_bankcard)
    private ListView lvBankCard;

    private UserEntity user;//用户实体
    @Override
    public void setLayout() {
        setContentView(R.layout.act_bindbankcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("绑定银行卡");
        user=getIntent().getParcelableExtra("userInfo");
        lvBankCard.setAdapter(new MyAdapter());
    }
    @OnClick({R.id.ll_addBankCard,R.id.tv_setzjpassword})
    private void onclick(View view){
        Intent intent=null;
        switch (view.getId()){
            case R.id.ll_addBankCard:

                break;
            case R.id.tv_setzjpassword:
                intent=new Intent(BindBankCardAct.this,SetPwdActivity.class);
                intent.putExtra("userPhone",user.getAccountName());
                startActivity(intent);
                break;
        }
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return  LayoutInflater.from(BindBankCardAct.this).inflate(R.layout.lv_bindcard_item, null);
        }
    }
}
