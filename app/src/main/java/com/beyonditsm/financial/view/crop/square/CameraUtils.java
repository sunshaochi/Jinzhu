package com.beyonditsm.financial.view.crop.square;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;

public class CameraUtils {

	
	/**
	 * 获取图片的uri
	 * 
	 * @param context
	 * @param requestCode
	 * @param resultCode
	 * @param
	 * @return
	 */
	public static Uri getBitmapUri(Fragment fragment, Context context,
			int requestCode, int resultCode, Intent result) {
		if (requestCode == Crop.REQUEST_PICK) {
			beginCrop(result.getData(), fragment, context);
		} else if (requestCode == Crop.REQUEST_CAMERA) {
			if (fragment != null) {
				new Crop(null).setType(1).output(Uri.fromFile(Crop.file))
						.asSquare().start(context, fragment);
			} else {
				if(Crop.file!=null){
				new Crop(null).setType(1).output(Uri.fromFile(Crop.file))
						.asSquare().start((Activity) context);
				}
			}
		} else if (requestCode == Crop.REQUEST_CROP) {
			return handleCrop(resultCode, result, context);
		}
		return null;
	}
	
	/**
	 * 获取相册图片
	 * 
	 * @param source
	 */
	private static void beginCrop(Uri source, Fragment fragment, Context context) {
		Uri outputUri = Uri
				.fromFile(new File(context.getCacheDir(), "cropped"));
		if (fragment != null) {
			new Crop(source).setType(0).output(outputUri).asSquare()
					.start(context, fragment);
		} else {
			new Crop(source).setType(0).output(outputUri).asSquare()
					.start((Activity) context);
		}

	}
	
	/**
	 * 跳转
	 * 
	 * @param resultCode
	 * @param result
	 */
	private static Uri handleCrop(int resultCode, Intent result, Context context) {
		if (resultCode == Activity.RESULT_OK) {
			return Crop.getOutput(result);
		} else if (resultCode == Crop.RESULT_ERROR) {
			Toast.makeText(context, Crop.getError(result).getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		return null;
	}
	
}
