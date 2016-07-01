package com.beyonditsm.financial.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.beyonditsm.financial.R;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MyScrollListView extends ListView implements OnTouchListener,OnGestureListener {
    /**
     * 手势识别类
     */
    private GestureDetector gestureDetector;

    /**
     * 滑动时出现的按钮
     */
    private View btnDelete;

    /**
     * listview的每一个item的布局
     */
    private ViewGroup viewGroup;
    /**
     * 选中的项
     */
    private int selectedItem;

    /**
     * 是否已经显示删除按钮
     */
    private boolean isDeleteShow;

    /**
     * 点击删除按钮时删除每一行的事件监听器
     */
    private OnItemDeleteListener onItemDeleteListener;


    public MyScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //得到当前触摸的条目
        if (!isDeleteShow) {
            selectedItem = pointToPosition((int)e.getX(), (int)e.getY());
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //如果删除按钮没有显示，并且手势滑动符合我们的条件
        //此处可以根据需要进行手势滑动的判断，如限制左滑还是右滑，我这里是左滑右滑都可以
        if (!isDeleteShow && Math.abs(velocityX) > Math.abs(velocityY)) {
            //在当前布局上，动态添加我们的删除按钮，设置按钮的各种参数、事件，按钮的点击事件响应我们的删除项监听器
            btnDelete = LayoutInflater.from(getContext()).inflate(R.layout.layout_button, null);
            btnDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //btnHide(btnDelete);
                    viewGroup.removeView(btnDelete);
                    btnDelete = null;
                    isDeleteShow = false;
                    onItemDeleteListener.onItemDelete(selectedItem);
                }
            });
            viewGroup = (ViewGroup)getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            btnDelete.setLayoutParams(layoutParams);
            viewGroup.addView(btnDelete);
            btnShow(btnDelete);
            isDeleteShow = true;
        }else{
            setOnTouchListener(this);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //得到当前触摸的条目
        selectedItem = pointToPosition((int)event.getX(), (int)event.getY());
        //如果删除按钮已经显示，那么隐藏按钮，异常按钮在当前位置的绘制
        if (isDeleteShow) {
            btnHide(btnDelete);
            viewGroup.removeView(btnDelete);
            btnDelete = null;
            isDeleteShow = false;
            return false;
        }else{
            //如果按钮没显示，则触发手势事件
            //由此去触发GestureDetector的事件，可以查看其源码得知，onTouchEvent中进行了手势判断，调用onFling
            return gestureDetector.onTouchEvent(event);
        }
    }
    /**
     * @类名称: OnItemDeleteListener
     * @描述: 删除按钮监听器
     */
    public interface OnItemDeleteListener{
        public void onItemDelete(int selectedItem);
    }

    /**
     * 按钮显示时的动画
     * @param v
     */
    private void btnShow(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_show));
    }
    /**
     * 按钮隐藏时的动画
     * @param v
     */
    private void btnHide(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_hide));
    }
//    /**
//     * 重写该方法，达到使ListView适应ScrollView的效果
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
