package com.beyonditsm.financial.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * 加载类
 * @author xuleyuan
 *
 */
public class MinePageLoadingView extends RelativeLayout {

	private LinearLayout mNoContent;
	private TextView mNoContentTxt;
	private ImageView mNoContentIco;
	private LinearLayout mLoading;
	private TextView mLoadingTxt;
	private AutoAnimImageView mLoadingIco;
	private TextView mLogOut;
	private TextView tvRetry;
	private LinearLayout mLoadError;
	private OnRetryListener mOnRetryListener;
	private OnLogOutListener mOnLogOutListener;

	public MinePageLoadingView(Context context) {
		super(context, null);
	}

	public MinePageLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MinePageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("NewApi")
	public MinePageLoadingView(Context context, AttributeSet attrs, int defStyleAttr,
							   int defStyleRes) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.mine_page_loading_view, this);
		
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		mNoContent = (LinearLayout) findViewById(R.id.nocontent);
		mNoContentTxt = (TextView) findViewById(R.id.nocontent_txt);
		mNoContentIco = (ImageView) findViewById(R.id.nocontent_ico);

		mLoading = (LinearLayout) findViewById(R.id.loading);
		mLoadingTxt = (TextView) findViewById(R.id.loading_txt);
		mLoadingIco = (AutoAnimImageView) findViewById(R.id.loading_ico);
		tvRetry = (TextView) findViewById(R.id.lv_refresh);
		mLoadError = (LinearLayout) findViewById(R.id.loaderror);
		mLogOut = (TextView) findViewById(R.id.logout);
		mLogOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mOnLogOutListener != null){
					mOnLogOutListener.OnLogOut();
				}
				loadComplete();
			}
		});
		tvRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnRetryListener != null){
					mOnRetryListener.OnRetry();
				}
				loading();
			}
		});

		loading();
	}

	public void noContent() {
		setVisibility(View.VISIBLE);
		mNoContent.setVisibility(View.VISIBLE);
		mLoading.setVisibility(View.GONE);
		mLoadError.setVisibility(View.GONE);
	}

	public void setNoContentTxt(String s) {
		mNoContentTxt.setText(s);
	}

	public void setNoContentTxt(int r) {
		mNoContentTxt.setText(r);
	}

	public void setNoContentIco(int r) {
		mNoContentIco.setImageResource(r);
	}

	public void loading() {
		setVisibility(View.VISIBLE);
		mNoContent.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		mLoadError.setVisibility(View.GONE);
	}

	public void setLoadingTxt(String s) {
		mLoadingTxt.setText(s);
	}

	public void setLoadingTxt(int r) {
		mLoadingTxt.setText(r);
	}

	public void setLoadingIco(int r) {
		mLoadingIco.setImageResource(r);
	}

	public void loadComplete() {
		setVisibility(View.GONE);
	}
	
	public void loadError(){
		setVisibility(View.VISIBLE);
		mNoContent.setVisibility(View.GONE);
		mLoading.setVisibility(View.GONE);
		mLoadError.setVisibility(View.VISIBLE);
	}
	
	public interface OnRetryListener{
		public void OnRetry();
	}
	public interface OnLogOutListener{
		public void OnLogOut();
	}
	public void setOnLogOutListener(OnLogOutListener l){
		mOnLogOutListener = l;
	}
	public void setOnRetryListener(OnRetryListener l){
		mOnRetryListener = l;
	}
}
