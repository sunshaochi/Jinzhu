package com.beyonditsm.financial.view.pullfreshview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 自定义下拉刷新
 * Created by wangbin on 15/12/17.
 */
public class LoadRefreshView extends PullToRefreshListView  {

    public LoadRefreshView(Context context) {
        this(context, null);
        //setBackground(new ColorDrawable(0x222222));
    }

    public LoadRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setBackground(new ColorDrawable(0x222222));
    }

    public LoadRefreshView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);
        //setBackground(new ColorDrawable(0x222222));
    }

    @Override
    protected LoadingLayout createHeaderLoadingLayout(Context context,
                                                      AttributeSet attrs) {
        return new FinalLoadingLayout(context);
    }
}
