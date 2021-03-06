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
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.fragment.CreditFirstFrag;
import com.beyonditsm.financial.fragment.CreditFourthFrag;
import com.beyonditsm.financial.fragment.CreditOfflineFrag;
import com.beyonditsm.financial.fragment.CreditSecondFrag;
import com.beyonditsm.financial.fragment.CreditSpeedSecondFrag;
import com.beyonditsm.financial.fragment.CreditThirFrag;
import com.tandong.sa.eventbus.EventBus;

import java.util.List;

/**
 * 贷款流程主act（第一步，二步，三步，四步。）
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

    private ProductBean productInfo;
    public static final String TAG_TYPE = "tag_type";

    private static CreditStepAct activityInstance;
    private CreditSpeedSecondFrag creditSpeedSecondFrag;
    private String credit_type;

    @Override
    public void setLayout() {
        setContentView(R.layout.creditstep);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        activityInstance = this;
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();

        orderId = getIntent().getStringExtra("orderId");//我的贷款详情传过来的
        String orderType = getIntent().getStringExtra("orderType"); // 1 线上  2线下//我的贷款详情传递过来的

        productInfo = getIntent().getParcelableExtra(HomeCreditDetailAct.PRODUCTINFO);//普通产品从贷款详情带过来
        credit_type = getIntent().getStringExtra(CreditSpeedDetailAct.CREDIT_TYPE);//急速度产品


//        if (TextUtils.isEmpty(SpUtils.getRoleName(this))) {
//            setTabSelection(0);//未登陆
//        } else
        if (TextUtils.isEmpty(credit_type)){
            setTabSelection(1);//快速判断资质普通产品过来走的这步
        }else if (!TextUtils.isEmpty(credit_type)&&"speed".equals(credit_type)){
            setTabSelection(5);
        }

        if (getIntent().getIntExtra("credit_upload", 0) == 1) {
//            MyLogUtils.error("orderType===="+orderType);
            if(!TextUtils.isEmpty(orderType)){
                if (Integer.valueOf(orderType) == 2) {//线下
                    setTabSelection(4);
                } else  {//线上
                    setTabSelection(2);
                }
            }else{
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
                    bundle.putParcelable(HomeCreditDetailAct.PRODUCTINFO, productInfo);
                    secondFrag.setArguments(bundle);//把产品info传过去
                    fragmentTransaction.add(R.id.credit_fl, secondFrag);
                } else {
                    fragmentTransaction.show(secondFrag);
                }

                break;
            case 2:
                setTopTitle("上传资质图片");
                Bundle bundle = new Bundle();
                bundle.putInt("act_type", getIntent().getIntExtra("credit_upload", 0));//我的贷款里面传过来的
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
            //极速贷
            case 5:
                setTopTitle("快速判断资质");
                if (creditSpeedSecondFrag==null){
                    creditSpeedSecondFrag = new CreditSpeedSecondFrag();
                    fragmentTransaction.add(R.id.credit_fl,creditSpeedSecondFrag);
                }else{
                    fragmentTransaction.show(creditSpeedSecondFrag);
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
        activityInstance = null;
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
