package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.widget.SpeedCreditViewPager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 急速贷款第二步
 * Created by Administrator on 2016/9/26 0026.
 */

public class CreditSpeedSecondFrag extends  BaseFragment {
    @ViewInject(R.id.vp_speedCredit)
    private SpeedCreditViewPager vpSpeedCredit;
    private GoToNextReceiver goToNextReceiver;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_creditspeedsecond,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initViewPager();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (goToNextReceiver==null) {
            goToNextReceiver = new GoToNextReceiver();
        }

        getActivity().registerReceiver(goToNextReceiver,new IntentFilter(NEXT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (goToNextReceiver!=null){
            getActivity().unregisterReceiver(goToNextReceiver);
        }
    }

    private void initViewPager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        CreditSpeedSecondFrag1 creditSpeedSecondFrag1 = new CreditSpeedSecondFrag1();
        CreditSpeedSecondFrag2 creditSpeedSecondFrag2 = new CreditSpeedSecondFrag2();
        CreditSpeedSecondFrag3 creditSpeedSecondFrag3 = new CreditSpeedSecondFrag3();
        fragmentList.add(creditSpeedSecondFrag1);
        fragmentList.add(creditSpeedSecondFrag2);
        fragmentList.add(creditSpeedSecondFrag3);
        vpSpeedCredit.setAdapter(new MyAdapter(getChildFragmentManager(),fragmentList));
        vpSpeedCredit.setCurrentItem(0);
        vpSpeedCredit.setScrollble(false);
    }

    @Override
    public void setListener() {

    }


    class MyAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        private List<Fragment> fragmentList = null;

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fm = fm;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    public static final String NEXT = "go_to_next";
    public class GoToNextReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int item = intent.getIntExtra("item", 0);
            MyLogUtils.info("点击下一步："+item);
            vpSpeedCredit.setCurrentItem(item);
        }
    }
}
