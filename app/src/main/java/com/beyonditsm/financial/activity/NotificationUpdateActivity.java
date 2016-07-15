package com.beyonditsm.financial.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.service.DownloadService;

public class NotificationUpdateActivity extends Activity {
    private TextView tv_progress;
	private DownloadService.DownloadBinder binder;
	private boolean isBinded;
	private ProgressBar mProgressBar;
    //
	private boolean isDestroy = true;
	private MyApplication app;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		app = (MyApplication) getApplication();
		// btn_update = (Button) findViewById(R.id.update);
        Button btn_cancel = (Button) findViewById(R.id.cancel);
		tv_progress = (TextView) findViewById(R.id.currentPos);
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar1);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(binder!=null) {
					binder.cancel();
					binder.cancelNotification();
				}
				finish();
			}
		});
		MyApplication.downloadApkUrl=getIntent().getStringExtra("path");
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (DownloadService.DownloadBinder) service;
			System.out.println("服务启动!!!");
			// 开始下载
			isBinded = true;
			binder.addCallback(callback);
			binder.start();

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
		System.out.println(" notification  onresume");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
	}


	@Override
	protected void onStop() {
		super.onStop();
		isDestroy = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			if ("finish".equals(result)) {
				finish();
				return;
			}
			int i = (Integer) result;
			mProgressBar.setProgress(i);
			// tv_progress.setText("当前进度 =>  "+i+"%");
			// tv_progress.postInvalidate();
			mHandler.sendEmptyMessage(i);
		}

	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			tv_progress.setText("当前进度 ： " + msg.what + "%");

		}
	};

	public interface ICallbackResult {
		void OnBackResult(Object result);
	}
}
