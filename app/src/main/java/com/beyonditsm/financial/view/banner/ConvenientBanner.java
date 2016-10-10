package com.beyonditsm.financial.view.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.beyonditsm.financial.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ConvenientBanner<T> extends LinearLayout {
    private List<T> mDatas;
    private int[] page_indicatorId;
    private ArrayList<ImageView> mPointViews = new ArrayList<>();
    private boolean canLoop = true;
    private CBLoopViewPager viewPager;
    private CBPagerAdapter pageAdapter;
    private CBPageChangeListener pageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ViewGroup loPageTurningPoint;
    private ViewPagerScroller scroller;
    private boolean turning;
    private AdSwitchTask adSwitchTask;
    private long autoTurningTime;
    private boolean canTurn = false;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, true);
        viewPager = (CBLoopViewPager) view.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) view.findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();

        adSwitchTask = new AdSwitchTask(this);
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<ConvenientBanner> reference;

        AdSwitchTask(ConvenientBanner convenientBanner) {
            this.reference = new WeakReference<ConvenientBanner>(convenientBanner);
        }

        @Override
        public void run() {
            ConvenientBanner convenientBanner = reference.get();

            if (convenientBanner != null) {
                if (convenientBanner.viewPager != null && convenientBanner.turning) {
                    int page = convenientBanner.viewPager.getCurrentItem() + 1;
                    convenientBanner.viewPager.setCurrentItem(page);
                    convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        pageAdapter = new CBPagerAdapter(holderCreator, mDatas);
        viewPager.setAdapter(pageAdapter, canLoop);

        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public ConvenientBanner setPageIndicator(int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null) return this;
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            LayoutParams params = new LayoutParams(25, 25);
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView,params);
        }
        pageChangeListener = new CBPageChangeListener(mPointViews,
                page_indicatorId);
        viewPager.setOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(viewPager.getRealItem());
        if (onPageChangeListener != null)
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);

        return this;
    }


    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public ConvenientBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     *
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     *
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public ConvenientBanner startTurning(long autoTurningTime) {
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    /**
     * 停止翻页
     */
    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    /**
     * 自定义翻页动画
     */
    public ConvenientBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isCanLoop() {
        return viewPager.isCanLoop();
    }

    /**
     * 监听item点击
     *
     * @param onItemClickListener
     */
    public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            viewPager.setOnItemClickListener(null);
            return this;
        }
        viewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     *
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public CBLoopViewPager getViewPager() {
        return viewPager;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        viewPager.setCanLoop(canLoop);
    }
}
