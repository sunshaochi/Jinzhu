package com.beyonditsm.financial.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

/**
 * Created by wangbin on 16/1/14.
 */
public class MyWalletActivity extends BaseActivity{
    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;//头像
    @ViewInject(R.id.tvName)
    private TextView tvName;//用户身份（服务者）
    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;//用户电话号
    @ViewInject(R.id.rlMyPayments)
    private RelativeLayout rlMyPayments;//收支明细
    @ViewInject(R.id.rlMyOrder)
    private RelativeLayout rlMyOrder;//订单明细
    @ViewInject(R.id.tv_ExchangeMoney)
    private TextView tvExangeMoney;//可兑换现金
    @ViewInject(R.id.tv_WeitGetMoney)
    private TextView tvWeitGetMoney;//待奖励
    @ViewInject(R.id.tv_DikouMoney)
    private TextView tvDikouMoney;//抵扣金额


    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.touxiang) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.touxiang) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.touxiang) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    private UserLoginEntity ule;//用户登录信息
    private UserEntity user;//用户信息

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mywallet);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("我的钱包");
        ule=getIntent().getParcelableExtra("userLogin");
        user=getIntent().getParcelableExtra("userInfo");
        setUserLogin();
        setUserInfo();
    }

    private void setUserLogin(){
        if(ule!=null){
            if(!TextUtils.isEmpty(ule.getDescription())){
                tvName.setText(ule.getDescription());
            }
            if(!TextUtils.isEmpty(ule.getUsername())){
                tvPhone.setText(ule.getUsername());
            }
        }
    }

    private void setUserInfo(){
        if(user!=null){
            if(!TextUtils.isEmpty(user.getCashTicketAmount())){
                tvExangeMoney.setText(user.getCashTicketAmount());
            }
            if(!TextUtils.isEmpty(user.getUnCashTicketAmount())){
                tvWeitGetMoney.setText(user.getUnCashTicketAmount());
            }
            if(!TextUtils.isEmpty(user.getDeductionTicketAmount())){
                tvDikouMoney.setText(user.getDeductionTicketAmount());
            }
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + user.getHeadIcon(), civHead, options);

        }
    }

    @OnClick({R.id.rlMyPayments,R.id.rlMyOrder,R.id.rlxianjin,R.id.rldikou})
    public void toClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.rlMyPayments:
                intent=new Intent(MyWalletActivity.this,BalancePaymentsAct.class);
                startActivity(intent);
                break;
            case R.id.rlMyOrder:
                intent=new Intent(MyWalletActivity.this,OrderDetailAct.class);
                startActivity(intent);
                break;
            case R.id.rlxianjin:
                intent=new Intent(MyWalletActivity.this,CashExchange.class);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                break;
            case R.id.rldikou:
                intent=new Intent(MyWalletActivity.this,InterestDeduction.class);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                break;
        }
    }
}
