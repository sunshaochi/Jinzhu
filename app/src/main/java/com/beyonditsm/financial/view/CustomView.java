package com.beyonditsm.financial.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.util.DensityUtil;

/**
 * Created by wangbin on 15/11/17.
 */
public class CustomView extends View{
    Paint paint;
    int radius;
    String resultString="";
    int rateEnd=0;
    private getcountsListener listener;
    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    int result;
    Paint paint1,paint2,paint3;
    Paint ratePaint;
    String[] stringArray=new String[]{"200","较差","400","中等","550","良好","700","优秀","850","极好","1000"};
    private void init(Context context)
    {
        paint=new Paint();
        //是否抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.white));
        //实心还是空心
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        //0-255，越小越透明
        paint.setAlpha(60);

        //dp和px之间互转工具类
        result= DensityUtil.dipToPx(context, 300);

        //刻度画笔
        paint1 =new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(getResources().getColor(R.color.white));
        paint1.setStrokeWidth(6);

        //刻度字画笔
        paint2=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setTextSize(26);
        paint2.setColor(getResources().getColor(R.color.white));


        //正中间字体画笔
        paint3=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setTextSize(40);
        paint3.setColor(getResources().getColor(R.color.white));

        ratePaint=new Paint();
        ratePaint.setColor(getResources().getColor(R.color.rateColor));
        ratePaint.setStrokeWidth(8);
        ratePaint.setStyle(Paint.Style.STROKE);
        //背景渐变动画实现
        ValueAnimator coloranim= ObjectAnimator.ofInt(context, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        coloranim.setDuration(30 * 180);
        coloranim.setEvaluator(new ArgbEvaluator());
        coloranim.start();
    }

    int px,py;
    int rotateRadius=210/10;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        px=getMeasuredWidth()/2;
        py=getMeasuredHeight()/2;

        //粗圆环
        RectF rectF=new RectF(getLeft()+40,getTop()+40,getRight()-40,getBottom()-40);
        canvas.drawArc(rectF, 165, 210, false, paint);

        //细圆环
        RectF rectFA=new RectF(getLeft()+10,getTop()+10,getRight()-10,getBottom()-10);
        paint.setStrokeWidth(4);
        canvas.drawArc(rectFA, 165, 210, false, paint);

        //动态通知变化的细圆环
        canvas.drawArc(rectFA,165,rateEnd,false,ratePaint);


        //正中间的字体
        if (!resultString.equals(""))
        {
            float textWidth=paint3.measureText(resultString);
            canvas.drawText(resultString,px-textWidth/2,py,paint3);
        }
        //旋转到正中心。
        canvas.rotate(255, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        //刻度线起始和终止的两个值
        int lineStartY=getTop()+40-30/2;
        int lineEndY=getTop()+40-30/2+30;
        for (int i=1;i<12;i++)
        {
            canvas.save();
            if (i%2!=0)
            {
                //画明显的刻度
                paint1.setAlpha(160);
                canvas.drawLine(px, lineStartY, px, lineEndY, paint1);
                paint2.setAlpha(180);

            }else
            {
                paint1.setAlpha(100);
                canvas.drawLine(px, lineStartY, px, lineEndY, paint1);
                paint2.setAlpha(120);
            }
            float textLength=paint2.measureText(stringArray[i-1]);
            canvas.drawText(stringArray[i-1],px-textLength/2,lineEndY+30,paint2);
            canvas.restore();
            canvas.rotate(rotateRadius,px,py);
        }
        canvas.restore();
        paint.setStrokeWidth(30);
        //开线程刷新rateEnd的值
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (rateEnd<listener.getcount())
                {
                    rateEnd++;
                    postInvalidateDelayed(1);
                }
            }
        }).start();
    }

    public void setcountListener(getcountsListener listener){
        this.listener=listener;
    }

    public interface getcountsListener{
        public abstract int getcount();
    }

}
