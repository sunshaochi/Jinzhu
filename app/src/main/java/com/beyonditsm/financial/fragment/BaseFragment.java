package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 *
 * Fragment基类
 * Created by wangbin on 15/11/11.
 */
public abstract  class BaseFragment extends Fragment{


    /**
     * 返回的view对象
     */
    protected View view;
    /**
     * 获取上下文
     */
    public Context context;
    private boolean isDestroyed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = initView(inflater);
        }
        // 注入控件
        ViewUtils.inject(this, view);
        IntentFilter filter = new IntentFilter();
        filter.addAction("UNLOGIN");
        context.registerReceiver(mybroad, filter);
        setListener();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyed = true;
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
    }

    /**
     * 初始化View 对象
     *
     * @param inflater
     *            view填充器 需要布局文件
     * @return 返回view 对象
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 初始化数据
     *
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 对view设置监听事件
     */
    public abstract void setListener();

    BroadcastReceiver mybroad=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

        }
    };
}
