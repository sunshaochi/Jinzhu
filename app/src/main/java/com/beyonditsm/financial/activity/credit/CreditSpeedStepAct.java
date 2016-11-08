package com.beyonditsm.financial.activity.credit;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.CreditSpeedFourthFrag;
import com.beyonditsm.financial.fragment.CreditSpeedFirstFrag;
import com.beyonditsm.financial.fragment.CreditSpeedSecondFrag;
import com.beyonditsm.financial.fragment.CreditSpeedThirFrag;
import com.beyonditsm.financial.util.SpUtils;
import com.tandong.sa.eventbus.EventBus;

import static com.beyonditsm.financial.activity.credit.CreditStepAct.orderId;
import static com.beyonditsm.financial.activity.credit.CreditStepAct.upList;


/**
 * 极速贷主流程
 * Created by Administrator on 2016/10/12 0012.
 */

public class CreditSpeedStepAct extends BaseActivity {

    private FragmentManager fragmentManager;
    private CreditSpeedFirstFrag speedFirstFrag;//极速贷第一步
    private CreditSpeedSecondFrag speedSecondFrag;//极速贷第二步
    private CreditSpeedThirFrag speedThirFrag;//极速贷第三步
    private CreditSpeedFourthFrag speedFourthFrag;//极速贷第四步
    private MySpeedRevice myRevice;

    @Override
    public void setLayout() {
        setContentView(R.layout.creditstep);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        if (TextUtils.isEmpty(SpUtils.getRoleName(this))) {
            setTabSelection(0);
        }else {
            setTabSelection(1);
        }
    }


    public void onEventMainThread(CreditStepAct.FirstEvent event) {
        orderId = event.orderId;
        switch (event.flag) {
            case 1:
                setTabSelection(1);
                break;
            case 2:
                setTabSelection(2);
                break;
            case 3:
                setTabSelection(3);
                break;
        }
    }

    public static class FirstEvent {
        public int flag;
        public String orderId;

        public FirstEvent(int change, String orderId) {
            this.orderId = orderId;
            flag = change;
        }
    }
    @SuppressLint("CommitTransaction")
    private void setTabSelection(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (position) {
            case 0:
                setTopTitle("留下联系方式");
                if (speedFirstFrag == null) {
                    speedFirstFrag = new CreditSpeedFirstFrag();
                    fragmentTransaction.add(R.id.credit_fl, speedFirstFrag);
                } else {
                    fragmentTransaction.show(speedFirstFrag);
                }
                break;
            case 1:
                setTopTitle("快速判断资质");
                if (speedSecondFrag == null) {
                    speedSecondFrag = new CreditSpeedSecondFrag();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable(CreditDetailAct.PRODUCTINFO, productInfo);
//                    speedSecondFrag.setArguments(bundle);
                    fragmentTransaction.add(R.id.credit_fl, speedSecondFrag);
                } else {
                    fragmentTransaction.show(speedSecondFrag);
                }

                break;
            case 2:
                setTopTitle("上传资料图片");
//                Bundle bundle = new Bundle();
//                bundle.putInt("act_type", getIntent().getIntExtra("credit_upload", 0));
//                String orderStatus = getIntent().getStringExtra("orderStatus");
//                if (!TextUtils.isEmpty(orderStatus)) {
//                    bundle.putString("orderStatus", orderStatus);
//                }
                if (speedThirFrag == null) {
                    speedThirFrag = new CreditSpeedThirFrag();
//                    thrFrag.setArguments(bundle);
                    fragmentTransaction.add(R.id.credit_fl, speedThirFrag);
                } else {
                    fragmentTransaction.show(speedThirFrag);
                }
                break;
            case 3:
                setTopTitle("信息验证");
                if (speedFourthFrag == null) {
                    speedFourthFrag = new CreditSpeedFourthFrag();
                    fragmentTransaction.add(R.id.credit_fl, speedFourthFrag);
                } else {
                    fragmentTransaction.show(speedFourthFrag);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (speedFirstFrag != null) {
            transaction.hide(speedFirstFrag);
        }
        if (speedSecondFrag != null) {
            transaction.hide(speedSecondFrag);
        }
        if (speedThirFrag != null) {
            transaction.hide(speedThirFrag);
        }
        if (speedFourthFrag != null) {
            transaction.hide(speedFourthFrag);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (myRevice==null){
            myRevice = new MySpeedRevice();
        }

        registerReceiver(myRevice,new IntentFilter(UPDATA));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        orderId = null;
        upList = null;
    }

    public static String UPDATA = "com.beyonditsm.creditstepact";

    public class MySpeedRevice extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTabSelection(1);
        }
    }
}
