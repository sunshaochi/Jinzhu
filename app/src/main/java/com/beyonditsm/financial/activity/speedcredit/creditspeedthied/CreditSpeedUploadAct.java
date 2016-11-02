package com.beyonditsm.financial.activity.speedcredit.creditspeedthied;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.entity.CreditImageBean;
import com.beyonditsm.financial.entity.CreditUplEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreditSpeedUploadAct extends BaseActivity {
    @ViewInject(R.id.tvDes)
    TextView tvDes;
    @ViewInject(R.id.ivPhoto)
    ImageView ivPhoto;

    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    private String photoSavePath;
    private String photoSaveName;
    private String path;// 图片全路径
    private FinalLoadDialog dialog;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_speed_upload);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        dialog = new FinalLoadDialog(this);
        dialog.setTitle("努力上传中");
        dialog.setCancelable(false);
        File file = new File(Environment.getExternalStorageDirectory(), "upload/cache");

        if (!file.exists())
            file.mkdirs();

        photoSavePath = Environment.getExternalStorageDirectory() + "/upload/cache/";

        setinfo(intent);

    }

    private void setinfo(Intent intent) {
        tvDes.setText(intent.getStringExtra("desc"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            Uri uri;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String time = df.format(new Date());
            switch (requestCode) {
                case PHOTOZOOM:// 相册
                    if (data == null || "".equals(data.toString())) {
                        return;
                    }
                    uri = data.getData();
                    if (null != uri && !"".equals(uri.toString())) {
                        Bitmap compressB = MyBitmapUtils.zoomImgKeepWH(MyBitmapUtils.decodeUriAsBitmap(CreditSpeedUploadAct.this, uri), 300, 300, true);
                        MyBitmapUtils.saveBitmap(compressB, "upload/cache/credit_upload" + time + ".png");
                    }

                    break;
                case PHOTOTAKE:// 拍照
//                    path = photoSavePath + photoSaveName;
                    MyBitmapUtils.saveBitmap(MyBitmapUtils.LoadBigImg(path, 300, 300), "upload/cache/credit_upload" + time + ".png");
                    break;
            }
            path = Environment.getExternalStorageDirectory() + "/upload/cache/credit_upload" + time + ".png";
            uploadFile(path);
            super.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    /**
     * 上传图片
     *
     * @param file 图片地址
     */
    private void uploadFile(final String file) {
        dialog.show();
        Map<String, FileBody> fileMaps = new HashMap<>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().toUpLoadFile(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                Intent intent = new Intent();
                intent.putExtra("url", result);
                setResult(RESULT_OK, intent);
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + result, ivPhoto, options);
                dialog.dismiss();
//                cib.setImageUrl(result);

            }

            @Override
            public void onError(int status, String msg) {
                dialog.cancel();
                MyLogUtils.info(msg);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("photoSavePath",photoSavePath);
        outState.putString("photoSaveName", path);
//        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        if (TextUtils.isEmpty(photoSavePath)) {
//            photoSavePath = savedInstanceState.getString("photoSavePath");
//        }
        if (null != savedInstanceState) {
            if (TextUtils.isEmpty(path)) {
                path = savedInstanceState.getString("photoSaveName");
            }


        }


    }


    @OnClick({R.id.ivPhoto,R.id.tvSave,R.id.tvBack})
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.ivPhoto:

                MySelfSheetDialog dialog = new MySelfSheetDialog(CreditSpeedUploadAct.this);
                dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //执行拍照前，应该先判断SD卡是否存在
                        String SDState = Environment.getExternalStorageState();
                        if (SDState.equals(Environment.MEDIA_MOUNTED)) {


                            photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                            path = photoSavePath + photoSaveName;
                            Uri imageUri;
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
                            openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(openCameraIntent, PHOTOTAKE);
                        } else {
                            MyToastUtils.showShortToast(CreditSpeedUploadAct.this, "SD卡不存在");

                        }

                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(openAlbumIntent, PHOTOZOOM);
                    }
                }).show();


                break;
            case R.id.tvBack:finish();break;
            case R.id.tvSave:finish();break;
        }
    }
}
