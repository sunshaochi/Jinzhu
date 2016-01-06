package com.beyonditsm.financial.view.crop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

/**
 * 裁剪照片
 * 
 * @author wangbin
 * 
 */
public class ClipActivity extends BaseActivity {
    @ViewInject(R.id.id_clipImageLayout)
	private ClipImageLayout mClipImageLayout;
	private ProgressDialog loadingDialog;
	String pathfile;
	private String path;

	@Override
	public void setLayout() {
		setContentView(R.layout.activity_clipimage);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// 这步必须要加
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTopTitle("裁剪照片");
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle("请稍后...");
		path = getIntent().getStringExtra("path");
		if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap = MyBitmapUtils.convertToBitmap(path, 600, 600);
		if (bitmap == null) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		mClipImageLayout.setBitmap(bitmap);
	}
	
	@OnClick({R.id.rl_botom_clip,R.id.rl_cancel})
	public void toClick(View v){
		switch(v.getId()){
		case R.id.rl_botom_clip:
			loadingDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					Bitmap bitmap = mClipImageLayout.clip();
					pathfile = Environment
							.getExternalStorageDirectory().getPath()
							+ "/ClipHeadPhoto/cache/"
							+ System.currentTimeMillis() + ".png";
					MyBitmapUtils.savePhotoToSDCard(bitmap, path);
					loadingDialog.dismiss();
					Intent intent = new Intent();
					intent.putExtra("path", path);
					setResult(RESULT_OK, intent);
					finish();
				}
			}).start();
			break;
		case R.id.rl_cancel:
			finish();
			break;
		}
	}

}
