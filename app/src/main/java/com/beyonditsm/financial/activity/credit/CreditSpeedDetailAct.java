package com.beyonditsm.financial.activity.credit;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */

public class CreditSpeedDetailAct extends BaseActivity {

    @ViewInject(R.id.ivRequire)
    private ImageView ivRequire;//申请条件
    @ViewInject(R.id.llRequire)
    private LinearLayout llRequire;
    @ViewInject(R.id.ivMaterial)
    private ImageView ivMaterial;//所需材料
    @ViewInject(R.id.llMaterial)
    private LinearLayout llMaterial;
    @ViewInject(R.id.ivDetail)
    private ImageView ivDetail;//详细说明
    @ViewInject(R.id.llDetail)
    private LinearLayout llDetail;

    @ViewInject(R.id.svCridet)
    private ScrollView svCridet;

    private Map<Integer, Boolean> map = new HashMap<>();
    private ObjectAnimator obaDown1;
    private ObjectAnimator obaOn1;
    private ObjectAnimator obaDown2;
    private ObjectAnimator obaOn2;
    private ObjectAnimator obaDown3;
    private ObjectAnimator obaOn3;

    public static final String CREDIT_TYPE = "credit_type";

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeed_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initAnim();
        setLeftTv("返回");
        map.put(0, false);
        map.put(1, false);
        map.put(2, false);
    }


    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                svCridet.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /*初始化动画*/
    private void initAnim() {
        obaDown1 = ObjectAnimator.ofFloat(ivRequire, "rotation", 0,
                180);
        obaDown1.setDuration(300);
        obaOn1 = ObjectAnimator.ofFloat(ivRequire, "rotation", -180,
                0);
        obaOn1.setDuration(300);

        obaDown2 = ObjectAnimator.ofFloat(ivMaterial, "rotation", 0,
                180);
        obaDown2.setDuration(300);
        obaOn2 = ObjectAnimator.ofFloat(ivMaterial, "rotation", -180,
                0);
        obaOn2.setDuration(300);

        obaDown3 = ObjectAnimator.ofFloat(ivDetail, "rotation", 0,
                180);
        obaDown3.setDuration(300);
        obaOn3 = ObjectAnimator.ofFloat(ivDetail, "rotation", -180,
                0);
        obaOn3.setDuration(300);
    }

    /**
     * 点击事件
     *
     * @param v View
     */
    @OnClick({R.id.rlRequire, R.id.rlMaterial, R.id.rlDetail, R.id.tvApplay, R.id.rlMonth, R.id.tvCal})
    public void toClick(View v) {
        switch (v.getId()) {
            //申请条件
            case R.id.rlRequire:

                if (!map.get(0)) {
                    obaDown1.start();
                    llRequire.setVisibility(View.VISIBLE);
//                    ivRequire.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(0, true);
                    scrollDown();
                } else {
                    obaOn1.start();
                    llRequire.setVisibility(View.GONE);
//                    ivRequire.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(0, false);
                }
                break;
            //所需材料
            case R.id.rlMaterial:
                if (!map.get(1)) {
                    obaDown2.start();
                    llMaterial.setVisibility(View.VISIBLE);
//                    ivMaterial.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(1, true);
                    scrollDown();
                } else {
                    obaOn2.start();
                    llMaterial.setVisibility(View.GONE);
//                    ivMaterial.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(1, false);
                }
                break;
            //详细说明
            case R.id.rlDetail:
                if (!map.get(2)) {
                    obaDown3.start();
                    llDetail.setVisibility(View.VISIBLE);
//                    ivDetail.setBackgroundResource(R.mipmap.arrow_up);
                    map.put(2, true);
                    scrollDown();
                } else {
                    obaOn3.start();
                    llDetail.setVisibility(View.GONE);
//                    ivDetail.setBackgroundResource(R.mipmap.arrow_down);
                    map.put(2, false);
                }
                break;
            //免费申请
            case R.id.tvApplay:
                AppManager.getAppManager().addActivity(CreditSpeedDetailAct.this);
                Bundle bundle = new Bundle();
//                bundle.putParcelable(PRODUCTINFO, productInfo);
                bundle.putString(CREDIT_TYPE,"speed");
                gotoActivity(CreditStepAct.class, false, bundle);
                break;
        }
    }
}
