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
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.fragment.CreditFirstFrag;
import com.beyonditsm.financial.fragment.CreditOfflineFrag;
import com.beyonditsm.financial.fragment.CreditSecondFrag;
import com.beyonditsm.financial.fragment.CreditFourthFrag;
import com.beyonditsm.financial.fragment.CreditThirFrag;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.tandong.sa.eventbus.EventBus;

import java.util.List;

/**
 * 贷款流程主act
 * Created by Yang on 2015/11/12 0012
 */
public class CreditStepAct extends BaseActivity {


    private CreditFirstFrag firstFrag;//第一步
    private CreditSecondFrag secondFrag;//第二步
    private CreditThirFrag thrFrag;//第三步
    private CreditOfflineFrag offlineFrag;//第三步
    private CreditFourthFrag fourthFrag;//第四步

    public static String orderId;//订单id
    public static List<UpLoadEntity> upList;

    private FragmentManager fragmentManager;

    private ProductInfo productInfo;
    public static final String TAG_TYPE = "tag_type";

    private static CreditStepAct activityInstance;

    @Override
    public void setLayout() {
        setContentView(R.layout.creditstep);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        activityInstance = this;
        orderId = getIntent().getStringExtra("orderId");
        // 1 线上  2线下
        String orderType = getIntent().getStringExtra("orderType");
        productInfo = getIntent().getParcelableExtra(CreditDetailAct.PRODUCTINFO);
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        if (TextUtils.isEmpty(SpUtils.getRoleName(this)))
            setTabSelection(0);
        else
            setTabSelection(1);

        if (getIntent().getIntExtra("credit_upload", 0) == 1) {
            MyLogUtils.error("orderType===="+orderType);
            if (!TextUtils.isEmpty(orderType)&&Integer.valueOf(orderType) == 2) {
                setTabSelection(4);
            } else if (TextUtils.isEmpty(orderType)||Integer.valueOf(orderType) == 1) {
                setTabSelection(2);
            }
        }

    }


    public static CreditStepAct getInstance() {
        return activityInstance;
    }


    public void onEventMainThread(FirstEvent event) {
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
                    bundle.putParcelable(CreditDetailAct.PRODUCTINFO, productInfo);
                    secondFrag.setArguments(bundle);
                    fragmentTransaction.add(R.id.credit_fl, secondFrag);
                } else {
                    fragmentTransaction.show(secondFrag);
                }

                break;
            case 2:
                setTopTitle("上传资质图片");
                Bundle bundle = new Bundle();
                bundle.putInt("act_type", getIntent().getIntExtra("credit_upload", 0));
                String orderStatus = getIntent().getStringExtra("orderStatus");
                if (!TextUtils.isEmpty(orderStatus)) {
                    bundle.putString("orderStatus", orderStatus);
                }
                if (thrFrag == null) {
                    thrFrag = new CreditThirFrag();
                    thrFrag.setArguments(bundle);
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
            case 4:
                setTopTitle("上传资质图片");
                Bundle bundle1 = new Bundle();
                bundle1.putInt("act_type", getIntent().getIntExtra("credit_upload", 0));
                String orderStatus1 = getIntent().getStringExtra("orderStatus");
                if (!TextUtils.isEmpty(orderStatus1)) {
                    bundle1.putString("orderStatus", orderStatus1);
                }
                if (offlineFrag == null) {
                    offlineFrag = new CreditOfflineFrag();
                    offlineFrag.setArguments(bundle1);
                    fragmentTransaction.add(R.id.credit_fl, offlineFrag);
                } else {
                    fragmentTransaction.show(offlineFrag);
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
        if (offlineFrag != null) {
            transaction.hide(offlineFrag);
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
        orderId = null;
        upList = null;
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
