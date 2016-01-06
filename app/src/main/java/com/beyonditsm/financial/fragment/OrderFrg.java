package com.beyonditsm.financial.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 订单管理
 * Created by gxy on 2015/11/26.
 */
public class OrderFrg extends BaseFragment {
    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;
    @ViewInject(R.id.rlMoney)
    private RelativeLayout rlMoney; //选择金额
    @ViewInject(R.id.tvMoney)
    private TextView tvMoney;
    @ViewInject(R.id.rlDate)
    private RelativeLayout rlDate;//选择月份
    @ViewInject(R.id.tvDate)
    private TextView tvDate;
    @ViewInject(R.id.rlSearch)
    private RelativeLayout rlSearch;//搜索

    private RelativeLayout rlBack;
    private TextView waitCommit;
    private TextView alreadyCommit;
    private FrameLayout orderactFl;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Fragment waitFrg, alreadyFrg;//待提交，已提交


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.orderact, null);
        rlBack = (RelativeLayout) view.findViewById(R.id.rl_back);
        waitCommit = (TextView) view.findViewById(R.id.wait_commit);
        alreadyCommit = (TextView) view.findViewById(R.id.already_commit);
        orderactFl = (FrameLayout) view.findViewById(R.id.orderact_fl);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rlBack.setVisibility(View.GONE);
        manager = getChildFragmentManager();
        setAllTabNor();
        setTabSelection(0);
        setCheckItem(0);
    }

    @OnClick({R.id.rl_back, R.id.wait_commit, R.id.already_commit})
    public void todo(View v) {
        switch (v.getId()) {
            //退出
//            case R.id.rl_back:
//                getActivity().finish();
//                break;
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
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        Bundle bundle;
        switch (position) {
            case 0:
                if (waitFrg == null) {
                    waitFrg = new ManagerOrderFrg();
                    transaction.add(R.id.orderact_fl, waitFrg);
                } else {
                    transaction.show(waitFrg);
                }
                break;
            case 1:
                if (alreadyFrg == null) {
                    alreadyFrg = new ManagerOrderCommitFrg();
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

    @Override
    public void setListener() {

    }
}
