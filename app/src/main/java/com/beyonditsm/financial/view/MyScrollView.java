
package com.beyonditsm.financial.view;
import android.widget.ScrollView;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class MyScrollView extends ScrollView {
    private OnScrollListener onSrollListener;
    public MyScrollView(Context context) {
        super(context);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener{
        void onScroll(MyScrollView myScrollView, int x, int y, int oldx, int oldy);
    }
    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.onSrollListener = onScrollListener;
    }
    public OnScrollListener getOnSrollListener(){
        return onSrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onSrollListener!=null){
            onSrollListener.onScroll(this,l,t,oldl,oldt);
        }
    }
}
//=======
//package com.beyonditsm.financial.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.ScrollView;
//
///**
// * Created by Administrator on 2016/9/18 0018.
// */
//public class MyScrollView extends ScrollView {
//    private OnScrollListener onSrollListener;
//    public MyScrollView(Context context) {
//        super(context);
//    }
//    public MyScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public interface OnScrollListener{
//        void onScroll(MyScrollView myScrollView,int x,int y,int oldx,int oldy);
//    }
//    public void setOnScrollListener(OnScrollListener onScrollListener){
//        this.onSrollListener = onScrollListener;
//    }
//    public OnScrollListener getOnSrollListener(){
//        return onSrollListener;
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (onSrollListener!=null){
//            onSrollListener.onScroll(this,l,t,oldl,oldt);
//        }
//    }
//}
//>>>>>>> jijietong
