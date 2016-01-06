package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.view.viewpagerindicator.CirclePageIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 引导页
 *
 * @author wangbin
 */
public class GuideActivity extends BaseActivity {
    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator ;
    @ViewInject(R.id.pager)
    private ViewPager pager;
    private GuidePagerAdapter adapter;


    private final static int[] guideImages = {R.drawable.guide1, R.drawable.guide2,
            R.drawable.guide3, R.drawable.guide4};
    private final static int[] tiaoguoImages = {R.drawable.tiaoguo01, R.drawable.tiaoguo02,
            R.drawable.tiaoguo03,R.drawable.tiaoguo03};


    @Override
    public void setLayout() {
        setContentView(R.layout.activity_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        adapter = new GuidePagerAdapter();
        pager.setAdapter(adapter);
//        indicator.setViewPager(pager);
    }


    /**
     * 引导界面适配器
     */
    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guideImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {

            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuideActivity.this, R.layout.guide_vp_item, null);
            ImageView finish_btn = (ImageView) view.findViewById(R.id.finish_btn);
            TextView tv_next = (TextView) view.findViewById(R.id.tv_next);
            if (position == guideImages.length - 1) {
                tv_next.setVisibility(View.VISIBLE);
                finish_btn.setVisibility(View.GONE);
            } else {
                tv_next.setVisibility(View.GONE);
                finish_btn.setVisibility(View.VISIBLE);
            }
            finish_btn.setBackgroundResource(tiaoguoImages[position]);
            view.setBackgroundResource(guideImages[position]);
            tv_next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            finish_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            ((ViewPager) container).addView(view);

            return view;
        }

    }

}
