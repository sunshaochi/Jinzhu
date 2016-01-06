package com.beyonditsm.financial.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.util.FinancialUtil;


public class ScaleAllImageView extends ImageView{

	
	
	private static final String TAG = "ScaleImageView";
    private Bitmap currentBitmap;
    private ImageChangeListener imageChangeListener;
    private boolean scaleToWidth = false; // this flag determines if should
                                          // measure height manually dependent
                                          // of width
    
    
    private Paint paint;  
    private int roundWidth = 15;    // 弧度高
    private int roundHeight = 15;   // 弧度宽
    private Paint paint2; 
    
    

    public ScaleAllImageView(Context context) {
        super(context);
       
        init(context,null);
    }

    public ScaleAllImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public ScaleAllImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {  
    	  this.setScaleType(ScaleType.CENTER_INSIDE);
    	  
    	  // 根据不同分辨率 来 控制弧度大小
          int width = FinancialUtil.getScreenMeture(context).x;
          if(width>1000){
          	roundWidth = roundHeight = 15;
          }else if(width>=720 && width <= 1000){
          	roundWidth = roundHeight = 10;
          }else if(width<720){
          	roundWidth = roundHeight = 7;
          }
        if(attrs != null) {     
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView);
            roundWidth= a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundWidth, roundWidth);  
            roundHeight= a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundHeight, roundHeight);  
        }else {  
            float density = context.getResources().getDisplayMetrics().density;  
            roundWidth = (int) (roundWidth*density);  
            roundHeight = (int) (roundHeight*density);  
        }   
          
        paint = new Paint();  
        paint.setColor(Color.WHITE);  
        paint.setAntiAlias(true);  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));  
          
        paint2 = new Paint();  
        paint2.setXfermode(null);  
    }  

    
    @Override  
    public void draw(Canvas canvas) {  
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);  
        Canvas canvas2 = new Canvas(bitmap);  
        super.draw(canvas2);  
        drawLeftUp(canvas2);       // 左上角
        drawRightUp(canvas2);      // 右上角
      drawLeftDown(canvas2);   // 左下角
      drawRightDown(canvas2);  // 右下角
        canvas.drawBitmap(bitmap, 0, 0, paint2);  
        bitmap.recycle();  
    }  
      
    private void drawLeftUp(Canvas canvas) {  
        Path path = new Path();  
        path.moveTo(0, roundHeight);  
        path.lineTo(0, 0);  
        path.lineTo(roundWidth, 0);  
        path.arcTo(new RectF( 0, 0, roundWidth*2, roundHeight*2), -90, -90);  
        path.close();  
        canvas.drawPath(path, paint);  
    }  
      
    private void drawLeftDown(Canvas canvas) {  
        Path path = new Path();  
        path.moveTo(0, getHeight()-roundHeight);  
        path.lineTo(0, getHeight());  
        path.lineTo(roundWidth, getHeight());  
        path.arcTo(new RectF(  
                0,   
                getHeight()-roundHeight*2,   
                0+roundWidth*2,   
                getHeight()),  
                90,   
                90);  
        path.close();  
        canvas.drawPath(path, paint);  
    }  
      
    private void drawRightDown(Canvas canvas) {  
        Path path = new Path();  
        path.moveTo(getWidth()-roundWidth, getHeight());  
        path.lineTo(getWidth(), getHeight());  
        path.lineTo(getWidth(), getHeight()-roundHeight);  
        path.arcTo(new RectF(  
                getWidth()-roundWidth*2,   
                getHeight()-roundHeight*2,   
                getWidth(),   
                getHeight()), 0, 90);  
        path.close();  
        canvas.drawPath(path, paint);  
    }  
      
    private void drawRightUp(Canvas canvas) {  
        Path path = new Path();  
        path.moveTo(getWidth(), roundHeight);  
        path.lineTo(getWidth(), 0);  
        path.lineTo(getWidth()-roundWidth, 0);  
        path.arcTo(new RectF(  
                getWidth()-roundWidth*2,   
                0,   
                getWidth(),   
                0+roundHeight*2),   
                -90,   
                90);  
        path.close();  
        canvas.drawPath(path, paint);  
    }  
    
    
    
    
    
    
    
    
    
    public void recycle() {
        setImageBitmap(null);
        if ((this.currentBitmap == null) || (this.currentBitmap.isRecycled()))
            return;
        this.currentBitmap.recycle();
        this.currentBitmap = null;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        currentBitmap = bm;
        super.setImageBitmap(currentBitmap);
        if (imageChangeListener != null)
            imageChangeListener.changed((currentBitmap == null));
    }

    @Override
    public void setImageDrawable(Drawable d) {
        super.setImageDrawable(d);
        if (imageChangeListener != null)
            imageChangeListener.changed((d == null));
    }

    @Override
    public void setImageResource(int id) {
        super.setImageResource(id);
    }

    public interface ImageChangeListener {
        // a callback for when a change has been made to this imageView
        void changed(boolean isEmpty);
    }

    public ImageChangeListener getImageChangeListener() {
        return imageChangeListener;
    }

    public void setImageChangeListener(ImageChangeListener imageChangeListener) {
        this.imageChangeListener = imageChangeListener;
    }

    private int imageWidth;
    private int imageHeight;

    public void setImageWidth(int w) {
        imageWidth = w;
    }

    public void setImageHeight(int h) {
        imageHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    	Log.e(TAG, "-------------------onMeasure -----------------");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        /**
         * if both width and height are set scale width first. modify in future
         * if necessary
         */

        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            scaleToWidth = true;
        } else if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            scaleToWidth = false;
        } else
            throw new IllegalStateException("width or height needs to be set to match_parent or a specific dimension");

        if (imageWidth == 0) {
            // nothing to measure
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        } else {
            if (scaleToWidth) {
                int iw = imageWidth;
                int ih = imageHeight;
                int heightC = width * ih / iw;
                if (height > 0)
                    if (heightC > height) {
                        // dont let hegiht be greater then set max
                        heightC = height;
                        width = heightC * iw / ih;
                    }

                this.setScaleType(ScaleType.CENTER_CROP);
                setMeasuredDimension(width, heightC);

            } else {
                // need to scale to height instead
                int marg = 0;
                if (getParent() != null) {
                    if (getParent().getParent() != null) {
                        marg += ((RelativeLayout) getParent().getParent()).getPaddingTop();
                        marg += ((RelativeLayout) getParent().getParent()).getPaddingBottom();
                    }
                }

                int iw = imageWidth;
                int ih = imageHeight;

                width = height * iw / ih;
                height -= marg;
                setMeasuredDimension(width, height);
               // invalidate();
            }

        }
    }


}
