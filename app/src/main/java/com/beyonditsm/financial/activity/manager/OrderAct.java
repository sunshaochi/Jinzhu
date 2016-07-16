package com.beyonditsm.financial.activity.manager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.ManagerOrderFrg;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 信贷经理订单管理界面
 * Created by Yang on 2015/11/16 0016.
 */
public class OrderAct extends BaseActivity {
    private TextView waitCommit;
    private TextView alreadyCommit;

    private FragmentManager manager;

    private Fragment waitFrg, alreadyFrg;//待提交，已提交

    private void assignViews() {
        waitCommit = (TextView) findViewById(R.id.wait_commit);
        alreadyCommit = (TextView) findViewById(R.id.already_commit);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.orderact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        assignViews();
        manager = getSupportFragmentManager();
        setAllTabNor();
        setTabSelection(0);
        setCheckItem(0);
    }

    @OnClick({R.id.rl_back, R.id.wait_commit, R.id.already_commit})
    public void todo(View v) {
        switch (v.getId()) {
            //退出
            case R.id.rl_back:
                finish();
                break;
            //待提交
            case R.id.wait_commit:
                setAllTabNor();
                setTabSelection(0);
                setCheckItem(0);
                break;
            //已提交
            case R.id.already_commit:
                setAllTabNor();
                setTabSelection(1);
                setCheckItem(1);
                break;
        }
    }

    private void setTabSelection(int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        Bundle bundle;
        switch (position) {
            case 0:
                if (waitFrg == null) {
                    waitFrg = new ManagerOrderFrg();
                    bundle = new Bundle();
                    bundle.putInt("flag",1);
                    waitFrg.setArguments(bundle);
                    transaction.add(R.id.orderact_fl, waitFrg);
                } else {
                    transaction.show(waitFrg);
                }
                break;
            case 1:
                if (alreadyFrg == null) {
                    alreadyFrg = new ManagerOrderFrg();
                    bundle = new Bundle();
                    bundle.putInt("flag",2);
                    alreadyFrg.setArguments(bundle);
                    transaction.add(R.id.orderact_fl, alreadyFrg);
                } else {
                    transaction.show(alreadyFrg);
                }

                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (waitFrg != null) {
            transaction.hide(waitFrg);
        }
        if (alreadyFrg != null) {
            transaction.hide(alreadyFrg);
        }
    }

    /**
     * tab全部切换普通
     */
    private void setAllTabNor() {
        waitCommit.setBackgroundResource(R.drawable.order_titletv_leftbg);
        waitCommit.setTextColor(Color.parseColor("#ffffff"));
        alreadyCommit.setBackgroundResource(R.drawable.order_titletv_rightbg);
        alreadyCommit.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setCheckItem(int position) {
        switch (position) {
            case 0:
                waitCommit.setBackgroundResource(R.drawable.white_bg);
                waitCommit.setTextColor(Color.parseColor("#3e444f"));
                break;
            case 1:
                alreadyCommit.setBackgroundResource(R.drawable.white_bg);
                alreadyCommit.setTextColor(Color.parseColor("#3e444f"));
                break;
        }
    }
}
