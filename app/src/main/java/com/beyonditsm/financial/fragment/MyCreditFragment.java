package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;

/**
 * 我de信用
 * Created by wangbin on 15/11/11.
 */
public class MyCreditFragment extends BaseFragment {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;

    @ViewInject(R.id.llNoLogin)
    private LinearLayout llNoLogin;//没有登录
    @ViewInject(R.id.llLogin)
    private RelativeLayout llLogin;//已登录

//    @ViewInject(R.id.ivLeft)
//    private ImageView ivLeft;
    @ViewInject(R.id.ivRight)
    private ImageView ivRight;
    @ViewInject(R.id.tvCre)
    private  TextView tvCre;//信用耕耘
    @ViewInject(R.id.tvTotal)
    private TextView tvTotal;//我的信用分值
    @ViewInject(R.id.tvPer)
    private TextView tvPer;//百分比


    private int credit_bg[]={R.mipmap.red_bg,R.mipmap.orange_bg,R.mipmap.blue_bg,R.mipmap.green_bg};
    private String credit_color[]={"#ff3d5b","#c57b24","#2b98e0","#1fcb50"};

    int i = 0;//切换到第几个背景和文字颜色
    int credit=0;//初始信用分
    int per;//初始百分比

    private int totalCurrent=750;//总信用分值
    private int interval;//信用分值，每次加的时间间隔

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_mycredit, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//EventBus注册
        tvTitle.setText("我的信用");
        rl_back.setVisibility(View.GONE);
        if(TextUtils.isEmpty(SpUtils.getCookie(getActivity()))){
            llNoLogin.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }else {
            llNoLogin.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
//            cv.setCounts(105);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (i<4){
                        Message message=handler.obtainMessage();
                        message.what=0;
                        message.arg1=i;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }
            }).start();

            if(totalCurrent<500){
                interval=5;
            }else{
                interval=2;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (credit<totalCurrent){
                        credit++;
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.arg1 = credit;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            //百分比
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(per<78) {
                        per++;
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        message.arg1 = per;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            final RotateAnimation animation =new RotateAnimation(0f,270f, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(2000);
            ivRight.setAnimation(animation);

        }

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    llLogin.setBackgroundResource(credit_bg[msg.arg1]);
                    tvCre.setTextColor(Color.parseColor(credit_color[msg.arg1]));
                    break;
                case 1:
                    tvTotal.setText(msg.arg1+"");
                    break;
                case 2:
                    tvPer.setText(msg.arg1+"%");
                    break;
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setListener() {

    }

    public void onEvent(LoginAct.LoginEvent event){
        if(event.sucess==1){
            llLogin.setVisibility(View.VISIBLE);
            llNoLogin.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ivLogin,R.id.tvCre})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ivLogin:
                intent = new Intent(getActivity(), LoginAct.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tvCre:
                intent=new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


}
