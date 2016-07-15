package com.beyonditsm.financial.view.risenumbertextview;

/**
 * Created by xuleyuan on 2016/6/22
 */
public interface IRiseNumber {
    /**
     * 开始播放动画的方法
     */
    void start();

    /**
     * 设置小数
     *
     * @param number 小数
     */
    void withNumber(float number);

    /**
     * 设置整数
     *
     * @param number 小数
     */
    void withNumber(int number);

    /**
     * 设置动画播放时长
     *
     * @param duration  时长
     */
    void setDuration(long duration);

    /**
     * 设置动画结束监听器
     *
     * @param callback 回调
     */
    void setOnEndListener(RiseNumberTextView.EndListener callback);
}
