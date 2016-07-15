package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2015/12/10
 */
public class OrderFragment extends BaseFragment {

    @ViewInject(R.id.vp)
    private ViewPager vp;
    private TextView[] title;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.orderfragment,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initTextView();
        initViewPager();
        rlBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("订单");
        title[0].setTextColor(ContextCompat.getColor(getActivity(),R.color.main_color));
        title[0].setTextSize(16);

    }

    private void initTextView() {
        LinearLayout tabLayout= (LinearLayout) getActivity().findViewById(R.id.tabLayout);
        title = new TextView[5];
        for (int i =0;i< title.length;i++){
            TextView textView = (TextView) tabLayout.getChildAt(i);
            title[i] = textView;
            title[i].setEnabled(true);
            title[i].setTag(i);
            title[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp.setCurrentItem((Integer) v.getTag());
                }
            });

        }
    }

    private void initViewPager() {

        vp.setAdapter(new MyAdapter(getChildFragmentManager()));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {

                for (TextView aTitle : title) {
                    aTitle.setTextColor(ContextCompat.getColor(getActivity(),R.color.tv_second_color));
                    aTitle.setTextSize(14);
                }
                title[position].setTextColor(ContextCompat.getColor(getActivity(),R.color.main_color));
                title[position].setTextSize(16);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
    }

    @Override
    public void setListener() {

    }
    class  MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

//        public MyAdapter(FragmentManager fm){
//            super(fm);
//            this.fm = fm;
//            this.fragmentList = fragmentList;
//        }
        @Override
        public Fragment getItem(int position) {
            ManagerOrderFragment fragment=new ManagerOrderFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("position",position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//            fm.beginTransaction().show(fragment).commit();
//            return fragment;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
////            super.destroyItem(container, position, object);
//            Fragment fragment = fragmentList.get(position);
//            fm.beginTransaction().hide(fragment).commit();
//        }
    }
}
