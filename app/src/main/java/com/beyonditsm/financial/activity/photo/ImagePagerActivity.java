package com.beyonditsm.financial.activity.photo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.fragment.ImageDetailFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SaveImageToSDcardUtil;
import com.beyonditsm.financial.view.HackyViewPager;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ImagePagerActivity extends FragmentActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private TextView indicator;

    private SaveImageToSDcardUtil sts;
    private int position;
    ArrayList<String> urls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);
        Button bcimg = (Button) findViewById(R.id.bcimg);
        int pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        position = pagerPosition;
        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        sts = new SaveImageToSDcardUtil();
        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
                .getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                position = arg0;
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);

        bcimg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        @SuppressLint("SdCardPath") boolean isSave = sts.ToSDCard(ImagePagerActivity.this,
                                IFinancialUrl.SECURITY_IMAGE_URL + urls.get(position),
                                "/sdcard/education", System.currentTimeMillis() + ".jpg");
                        if (isSave) {
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                }).start();

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyToastUtils.showShortToast(getApplicationContext(), "保存成功");
                    break;
                case 1:
                    MyToastUtils.showShortToast(getApplicationContext(), "保存失败");
                    break;
            }
        }

    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }

    }

}
