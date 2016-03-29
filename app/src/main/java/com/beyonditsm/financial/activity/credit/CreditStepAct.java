package com.beyonditsm.financial.activity.credit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.user.HotCreditDetailAct;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.fragment.CreditFirstFrag;
import com.beyonditsm.financial.fragment.CreditSecondFrag;
import com.beyonditsm.financial.fragment.CreditFourthFrag;
import com.beyonditsm.financial.fragment.CreditThirFrag;
import com.beyonditsm.financial.util.SpUtils;
import com.tandong.sa.eventbus.EventBus;

/**
 * 贷款流程主act
 * Created by Yang on 2015/11/12 0012.
 */
public class CreditStepAct extends BaseActivity {
    private FrameLayout creditFl;

    private CreditFirstFrag firstFrag;//第一步
    private CreditSecondFrag secondFrag;//第二步
    private CreditThirFrag thrFrag;//第三步
    private CreditFourthFrag fourthFrag;//第四步
    private ProductInfo hotProductInfo;

    private void assignViews() {
        creditFl = (FrameLayout) findViewById(R.id.credit_fl);
    }

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private ProductInfo productInfo;


    @Override
    public void setLayout() {
        setContentView(R.layout.creditstep);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        assignViews();
        productInfo = getIntent().getParcelableExtra(CreditDetailAct.PRODUCTINFO);
        hotProductInfo = getIntent().getParcelableExtra(HotCreditDetailAct.HOTPRODUCTINFO);
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        if (TextUtils.isEmpty(SpUtils.getRoleName(this)))
            setTabSelection(0);
        else
            setTabSelection(1);
    }

    public void onEventMainThread(FirstEvent event) {
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

        public FirstEvent(int change) {
            flag = change;
        }
    }

    private void setTabSelection(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (position) {
            case 0:
                setTopTitle("留下联系方式");
                if (firstFrag == null) {
                    firstFrag = new CreditFirstFrag();
                    fragmentTransaction.add(R.id.credit_fl, firstFrag);
                } else {
                    fragmentTransaction.show(firstFrag);
                }
                break;
            case 1:
                setTopTitle("快速判断资质");
                if (secondFrag == null) {
                    secondFrag = new CreditSecondFrag();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CreditDetailAct.PRODUCTINFO,productInfo);
                    bundle.putParcelable(HotCreditDetailAct.HOTPRODUCTINFO,hotProductInfo);
                    secondFrag.setArguments(bundle);
                    fragmentTransaction.add(R.id.credit_fl, secondFrag);
                } else {
                    fragmentTransaction.show(secondFrag);
                }

                break;
            case 2:
                setTopTitle("上传资质图片");
                if (thrFrag == null) {
                    thrFrag = new CreditThirFrag();
                    fragmentTransaction.add(R.id.credit_fl, thrFrag);
                } else {
                    fragmentTransaction.show(thrFrag);
                }
                break;
            case 3:
                setTopTitle("申请完毕");
                if (fourthFrag == null) {
                    fourthFrag = new CreditFourthFrag();
                    fragmentTransaction.add(R.id.credit_fl, fourthFrag);
                } else {
                    fragmentTransaction.show(fourthFrag);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFrag != null) {
            transaction.hide(firstFrag);
        }
        if (secondFrag != null) {
            transaction.hide(secondFrag);
        }
        if (thrFrag != null) {
            transaction.hide(thrFrag);
        }
        if (fourthFrag != null) {
            transaction.hide(fourthFrag);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATA);
        if (myRevice == null) {
            myRevice = new MyRevice();
            registerReceiver(myRevice, intentFilter);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        unregisterReceiver(myRevice);
        myRevice = null;
    }


    public static String UPDATA = "com.beyonditsm.creditstepact";
    private MyRevice myRevice;

    public class MyRevice extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTabSelection(1);
        }
    }
}
