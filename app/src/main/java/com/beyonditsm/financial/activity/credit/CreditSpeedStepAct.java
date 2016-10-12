package com.beyonditsm.financial.activity.credit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.CreditSpeedFirstFrag;
import com.beyonditsm.financial.fragment.CreditSpeedSecondFrag;
import com.beyonditsm.financial.fragment.CreditSpeedThirFrag;

/**
 * 极速贷主流程
 * Created by Administrator on 2016/10/12 0012.
 */

public class CreditSpeedStepAct extends BaseActivity {

    private FragmentManager fragmentManager;
    private CreditSpeedFirstFrag speedFirstFrag;
    private CreditSpeedSecondFrag speedSecondFrag;
    private CreditSpeedThirFrag speedThirFrag;
    @Override
    public void setLayout() {
        setContentView(R.layout.creditstep);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        fragmentManager = getSupportFragmentManager();
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
                setTopTitle("申请完毕");
//                if (fourthFrag == null) {
//                    fourthFrag = new CreditFourthFrag();
//                    fragmentTransaction.add(R.id.credit_fl, fourthFrag);
//                } else {
//                    fragmentTransaction.show(fourthFrag);
//                }
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
//        if (fourthFrag != null) {
//            transaction.hide(fourthFrag);
//        }
    }
}
