package com.beyonditsm.financial.view.wheelView;

import android.content.Context;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2015/11/27.
 */
public class CoustomerWheelScroller {

    public interface ScrollingListener{
        void onScroll(int distance);
        void onStarted();
        void onFinished();
        void onJustify();
    }
    private static final int SCROLLING_DURATION = 400;
    public static final int MIN_DELTA_FOR_SCROLLING = 1;
    private ScrollingListener listener;
    private Context context;


    private  GestureDetector gestureDetector;
    private  Scroller scroller;
    private int lastScrollY;
    private final int MESSAGE_SCROLL = 0;
    private final int MESSAGE_JUSTIFY = 1;
    private float lastTouchedY;
    private boolean isScrollingPerformed;

    public CoustomerWheelScroller(Context context,ScrollingListener listener){
        gestureDetector = new GestureDetector(context,gestureListener);
        gestureDetector.setIsLongpressEnabled(false);

        scroller = new Scroller(context);
        this.context= context;
        this.listener = listener;
    }
    public void setInterpolator(Interpolator interpolator) {
        scroller.forceFinished(true);
        scroller = new Scroller(context, interpolator);
    }
    public void scroll(int distance,int time){
        scroller.forceFinished(true);
        lastScrollY = 0;
        scroller.startScroll(0, 0, 0, distance, time != 0 ? time : SCROLLING_DURATION);
        setNextMessage(MESSAGE_SCROLL);
        startScrolling();
    }

    public void stopScrolling(){
        scroller.forceFinished(true);
    }
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchedY = event.getY();
                scroller.forceFinished(true);
                clearMessages();
                break;

            case MotionEvent.ACTION_MOVE:
                // perform scrolling
                int distanceY = (int)(event.getY() - lastTouchedY);
                if (distanceY != 0) {
                    startScrolling();
                    listener.onScroll(distanceY);
                    lastTouchedY = event.getY();
                }
                break;
        }

        if (!gestureDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_UP) {
            justify();
        }

        return true;
    }
    private void setNextMessage(int message) {
        clearMessages();
        mHanler.sendEmptyMessage(message);
    }

    private void clearMessages() {
        mHanler.removeMessages(MESSAGE_SCROLL);
        mHanler.removeMessages(MESSAGE_JUSTIFY);
    }

    private android.os.Handler mHanler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            scroller.computeScrollOffset();
            int currY = scroller.getCurrY();
            int delta = lastScrollY -currY;
            lastScrollY = currY;
            if (delta!=0){
                listener.onScroll(delta);
            }
            if (Math.abs(currY-scroller.getFinalY())<MIN_DELTA_FOR_SCROLLING){
                currY = scroller.getFinalY();
                scroller.forceFinished(true);
            }
            if (!scroller.isFinished()){
                mHanler.sendEmptyMessage(msg.what);
            }else if (msg.what==MESSAGE_SCROLL){
                justify();
            }else{
                finishScrolling();
            }
        }
    };

    private void startScrolling() {
        if (!isScrollingPerformed) {
            isScrollingPerformed = true;
            listener.onStarted();
        }
    }
    private void finishScrolling() {
        if (isScrollingPerformed) {
            listener.onFinished();
            isScrollingPerformed = false;
        }
    }

    private void justify() {
        listener.onJustify();
        setNextMessage(MESSAGE_JUSTIFY);
    }

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return true;
        }
    };
}
