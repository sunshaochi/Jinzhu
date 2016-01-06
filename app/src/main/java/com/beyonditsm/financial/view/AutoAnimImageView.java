package com.beyonditsm.financial.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class AutoAnimImageView extends ImageView {

	public AutoAnimImageView(Context context) {
		super(context);
	}

	public AutoAnimImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public AutoAnimImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AutoAnimImageView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
	}
	
	private void init(){
		getViewTreeObserver().addOnPreDrawListener(mOnPreDrawListener);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		init();
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		if(visibility != View.VISIBLE){
			getViewTreeObserver().removeOnPreDrawListener(mOnPreDrawListener);
		} else {
			getViewTreeObserver().addOnPreDrawListener(mOnPreDrawListener);
		}
		
		super.onVisibilityChanged(changedView, visibility);
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		
		getViewTreeObserver().removeOnPreDrawListener(mOnPreDrawListener);
	}
	
	private OnPreDrawListener mOnPreDrawListener = new OnPreDrawListener(){
		@Override
		public boolean onPreDraw() {
			if (getBackground() instanceof AnimationDrawable) {
				((AnimationDrawable) getBackground()).start();
				return true;
			}
			if(getDrawable() instanceof AnimationDrawable){
				((AnimationDrawable) getDrawable()).start();
				return true;
			}
			return false;
		}
	};
}
