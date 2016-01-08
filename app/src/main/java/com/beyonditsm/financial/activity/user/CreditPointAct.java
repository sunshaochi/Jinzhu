package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 信用分
 * Created by wangbin on 15/12/9.
 */
public class CreditPointAct extends BaseActivity {
    @ViewInject(R.id.rlTop)
    private RelativeLayout rlTop;
    @ViewInject(R.id.llLogin)
    private RelativeLayout llLogin;//已登录
//    @ViewInject(R.id.tvCre)
//    private  TextView tvCre;//信用耕耘
    @ViewInject(R.id.tvTotal)
    private TextView tvTotal;//我的信用分值
    @ViewInject(R.id.tvPer)
    private TextView tvPer;//百分比
    @ViewInject(R.id.ivRight)
    private ImageView ivRight;
//    @ViewInject(R.id.tvGame)
//    private TextView tvGame;

    private UserEntity user;//用户信息

    public static final String CREDIT="credit";
    public static final String GRADE="grade";//信用分超过的百分比用户


    private int credit_bg[] = {R.mipmap.red_bg, R.mipmap.orange_bg, R.mipmap.blue_bg, R.mipmap.green_bg};
    private String credit_color[] = {"#ff3d5b", "#c57b24", "#2b98e0", "#1fcb50"};

    int i = 0;//切换到第几个背景和文字颜色
    int credit = 0;//初始信用分
    int per;//初始百分比

    private double totalPer;
    private int ttPer;

    private int totalCurrent = 750;//总信用分值
    private int interval;//信用分值，每次加的时间间隔


    String grade;//超过百分之多少用户
    String score;//信用分值

    private  int m=0;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_point);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的信用");
        setLeftTv("返回");
        rlTop.setBackgroundResource(R.color.gray_color);
        totalCurrent=getIntent().getIntExtra(CREDIT, 0);
        totalPer=Double.valueOf(getIntent().getStringExtra(GRADE));
        doAnimation();
    }

    private void doAnimation(){
        ttPer=(int)totalPer;
        if(ttPer>-1&&ttPer<=25){
             i=0;
        }else if(ttPer>25&&ttPer<=50){
            i=1;
        }else if(ttPer>50&&ttPer<=75){
            i=2;
        }else{
            i=3;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (m<=i){
                    Message message=handler.obtainMessage();
                    message.what=0;
                    message.arg1=i;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    m++;
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
                while(per<ttPer) {
                    per++;
                    Message message = handler.obtainMessage();
                    message.what = 2;
                    message.arg1 = per;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(20);
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

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    llLogin.setBackgroundResource(credit_bg[msg.arg1]);
//                    tvCre.setTextColor(Color.parseColor(credit_color[msg.arg1]));
//                    tvGame.setTextColor(Color.parseColor(credit_color[msg.arg1]));
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

    @OnClick({R.id.rlCre,R.id.rlGame})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rlCre://信用提速
                intent = new Intent(CreditPointAct.this, HardCreditAct.class);
                intent.putExtra("flag",0);
                startActivity(intent);
                break;
            case R.id.rlGame://玩转金融
                intent = new Intent(CreditPointAct.this, GameActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {

                    if (!TextUtils.isEmpty(user.getCreditScore())) {
                        score=user.getCreditScore();//totalCurrent
                        totalCurrent=Integer.valueOf(score);
                    }
                    if (TextUtils.isEmpty(user.getCreditScore())) {
                        getScorePer("0");
                    } else {
                        getScorePer(user.getCreditScore());
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 获取信用分超过多少的用户
     *
     * @param score
     */
    private void getScorePer(String score) {
        RequestManager.getUserManager().getScorePer(score, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                grade = object.optString("data");
                totalPer=Double.valueOf(grade);
                doAnimation();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new MyBroadCastReceiver();
        }
        registerReceiver(receiver,new IntentFilter(UPDATE_MYSCORE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String UPDATE_MYSCORE="com.update.myscore";
    public class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            getUserInfo();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }
}
