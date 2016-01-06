package com.beyonditsm.financial.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**保存图片到SDcard工具类
 * @author nseer
 *
 */
public class SaveImageToSDcardUtil {

	private byte[] imageBytes;
	private Bitmap bitmap;
	private BufferedOutputStream bos;
	private boolean isSave;
	private static String sdcardurl1 = Environment
			.getExternalStorageDirectory() + "";

	/**
	 * @param bitMap
	 *            要保存的bitmap
	 * @param fileName
	 *            要保存成的文件名称
	 * @param dir
	 *            要保存的文件夹
	 * @return
	 */
	public String bitMapToSDCard(Bitmap bitMap, String fileName, String dir) {
		String sdCardURL = sdcardurl1 + "/" + dir + "/" + fileName;
		String uri = sdcardurl1 + "/" + dir + "/";
		File dirFile = new File(uri);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(sdCardURL);
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			return sdCardURL;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * @param httpImageUrl
	 *            图片的网络地址
	 * 
	 * @param SDCardURI
	 *            保存到sd卡的地址
	 * @param fileName
	 *            文件名
	 * @return true 保存成功，false 保存失败
	 */
	public boolean ToSDCard(Context context,String httpImageUrl, String SDCardURI,
			String fileName) {
		String sdCardURL = SDCardURI + "/" + fileName;
		String dir =  SDCardURI + "/";
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 通过网络获取bete[]
			try {
				URL url = new URL(httpImageUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				InputStream inStream = conn.getInputStream();
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					imageBytes = readStream(inStream);
				}

				bitmap = BitmapFactory.decodeByteArray(imageBytes, 0,
						imageBytes.length);

				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
				File myCaptureFile = new File(sdCardURL);
				bos = new BufferedOutputStream(new FileOutputStream(
						myCaptureFile));
				bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
				bos.flush();
				bos.close();
				isSave = true;
			} catch (Exception e) {
				isSave = false;
				e.printStackTrace();
			}
		} else {
			MyLogUtils.info("没sd卡");
			
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(new File(sdCardURL))));
		return isSave;
	}

	public String ToSDCardForUri(String httpImageUrl, String SDCardURI,
			String fileName) {
		String sdCardURL = sdcardurl1 + "/" + SDCardURI + "/" + fileName;
		String dir = sdcardurl1 + "/" + SDCardURI + "/";
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 通过网络获取bete[]
			MyLogUtils.info(sdCardURL);
			try {
				URL url = new URL(httpImageUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				InputStream inStream = conn.getInputStream();
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					imageBytes = readStream(inStream);
				}

				bitmap = BitmapFactory.decodeByteArray(imageBytes, 0,
						imageBytes.length);

				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
				File myCaptureFile = new File(sdCardURL);
				bos = new BufferedOutputStream(new FileOutputStream(
						myCaptureFile));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
				String path = sdCardURL;
				return path;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MyLogUtils.info("没sd卡");
		}
		return "";
	}

	/**
	 * 根据流获取字节
	 * 
	 * @param inStream
	 * @return
	 */
	private byte[] readStream(InputStream inStream) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outStream.close();
				inStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return outStream.toByteArray();

	}
}
