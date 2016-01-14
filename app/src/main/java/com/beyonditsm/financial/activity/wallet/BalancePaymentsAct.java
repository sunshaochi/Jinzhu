package com.beyonditsm.financial.activity.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.fragment.CouponsFragment;
import com.beyonditsm.financial.fragment.RebateFragment;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 收支明细
 */
public class BalancePaymentsAct extends BaseActivity{
    @ViewInject(R.id.mycredit_viewpager)
    private ViewPager myCreditViewpager;

    private ImageView[] imageArras;
    private TextView[] textArras;
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_balancepayments);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("收支明细");

        initTextView();
        initImageView();
        initViewpager();

        textArras[0].setTextColor(getResources().getColor(R.color.main_color));
    }
    private void initViewpager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
//        bundle.putParcelable("rowe", rowe);
        CouponsFragment couponsFragment = new CouponsFragment();
        RebateFragment rebateFragment =new RebateFragment();
//        statusFragment.setArguments(bundle);
//        detailFragment.setArguments(bundle);
        fragmentList.add(couponsFragment);
        fragmentList.add(rebateFragment);
        myCreditViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        myCreditViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < textArras.length; i++) {
                    textArras[i].setTextColor(getResources().getColor(R.color.tv_second_color));
                    imageArras[i].setBackgroundColor(Color.TRANSPARENT);
                }
                textArras[position].setTextColor(getResources().getColor(R.color.main_color));
                imageArras[position].setBackgroundColor(getResources().getColor(R.color.main_color));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCreditViewpager.setCurrentItem(0);
    }
    private void initImageView() {
        LinearLayout tabTextLayout = (LinearLayout) findViewById(R.id.tabImageLayout);
        imageArras = new ImageView[2];
        for (int i =0;i< imageArras.length;i++){
            ImageView imageView = (ImageView) tabTextLayout.getChildAt(i);
            imageArras[i] = imageView;
            imageArras[i].setTag(i);
            imageArras[i].setBackgroundColor(Color.TRANSPARENT);
        }
        imageArras[0].setBackgroundColor(getResources().getColor(R.color.main_color));
    }
    private void initTextView() {
        LinearLayout tabTextLayout = (LinearLayout) findViewById(R.id.tabTextLayout);
        textArras = new TextView[2];
        for (int i =0;i< textArras.length;i++){
            TextView textView = (TextView) tabTextLayout.getChildAt(i);
            textArras[i] = textView;
            textArras[i].setEnabled(true);
            textArras[i].setTag(i);
            textArras[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCreditViewpager.setCurrentItem((Integer) v.getTag());
                }
            });
        }
    }
    class  MyAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        private List<Fragment> fragmentList =null;
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

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
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }
}
