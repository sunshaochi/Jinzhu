package com.beyonditsm.financial.view.pullfreshview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beyonditsm.financial.R;

/**
 * Created by wangbin on 15/12/17.
 */
public class FinalLoadingLayout extends LoadingLayout{

    private ImageView ivLoad;
    /** Header的容器 */
    private LinearLayout mHeaderContainer;

    private AnimationDrawable animation;
    /**
     * 构造方法
     *
     * @param context
     *            context
     */
    public FinalLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context
     *            context
     * @param attrs
     *            attrs
     */
    public FinalLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mHeaderContainer = (LinearLayout) findViewById(R.id.pull_to_refresh_header_content);
        ivLoad=(ImageView) findViewById(R.id.ivLoad);
        ivLoad.setBackgroundResource(R.anim.refresh_anim);
        animation=(AnimationDrawable)ivLoad.getBackground();
        animation.setOneShot(false);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet arg1) {
        View container = LayoutInflater.from(context).inflate(
                R.layout.pull_to_refre_header, null);
        return container;
    }

    @Override
    public int getContentSize() {
        if (null != mHeaderContainer) {
            return mHeaderContainer.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    protected void onReset() {
        if(animation.isRunning())//是否正在运行？
        {
            //重置动画
            animation.stop();//停止
            animation.selectDrawable(0);
        }
    }

    @Override
    protected void onReleaseToRefresh() {
        if(animation.isRunning())//是否正在运行？
        {
            animation.stop();//停止

        }
    }

    @Override
    protected void onRefreshing() {
        if(animation.isRunning())//是否正在运行？
        {
            animation.stop();//停止
        }
        animation.start();//启动
    }
}
