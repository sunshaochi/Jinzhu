package com.beyonditsm.financial.activity.wallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.CouponsFragment;
import com.beyonditsm.financial.fragment.RebateFragment;
import com.beyonditsm.financial.view.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 收支明细
 */
public class BalancePaymentsAct extends BaseActivity{
    @ViewInject(R.id.mycredit_viewpager)
    private ViewPager myCreditViewpager;
    @ViewInject(R.id.tl_creditDetail)
    private SlidingTabLayout tlCreditDetail;

    private String[] mTitles  = {"现金券","抵扣券"};
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_balancepayments);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("收支明细");
        initViewpager();

    }
    private void initViewpager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("rowe", rowe);
        CouponsFragment couponsFragment = new CouponsFragment();
        RebateFragment rebateFragment =new RebateFragment();
//        couponsFragment.setArguments(bundle);
//        rebateFragment.setArguments(bundle);
        fragmentList.add(couponsFragment);
        fragmentList.add(rebateFragment);
        myCreditViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        myCreditViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCreditViewpager.setCurrentItem(0);
        tlCreditDetail.setViewPager(myCreditViewpager);
    }

    class  MyAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        private List<Fragment> fragmentList =null;

        public MyAdapter(FragmentManager fm,List<Fragment> fragmentList){
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
