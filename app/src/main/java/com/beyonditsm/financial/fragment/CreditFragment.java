package com.beyonditsm.financial.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.view.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款
 * Created by wangbin on 15/11/11
 */
public class CreditFragment extends BaseFragment {

    @ViewInject(R.id.tl_creditType)
    SlidingTabLayout tlCreditType;
    @ViewInject(R.id.credit_viewpager)
    ViewPager creditViewpager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;

    private final String[] mTitles = {"惠心贷", "极速贷"};

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frgment_credit, null);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            creditViewpager.setCurrentItem(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        creditViewpager.setCurrentItem(0);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("贷款");
        rl_back.setVisibility(View.GONE);
        initViewPager();

    }



    private void initViewPager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        DefaultCreditFrag defaultCreditFrag = new DefaultCreditFrag();
        SpeedCreditFrag speedCreditFrag = new SpeedCreditFrag();
        fragmentList.add(defaultCreditFrag);
        fragmentList.add(speedCreditFrag);
        creditViewpager.setAdapter(new MyAdapter(getChildFragmentManager(),fragmentList));
        creditViewpager.setCurrentItem(0);
        tlCreditType.setViewPager(creditViewpager);
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
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }
}
