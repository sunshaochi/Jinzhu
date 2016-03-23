package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.view.crop.square.CameraUtils;
import com.beyonditsm.financial.view.crop.square.Crop;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传身份证照片
 * Created by Administrator on 2016/3/2.
 */
public class UpIdCardAct extends BaseActivity {
    @ViewInject(R.id.iv_positive)
    private ImageView ivPositive;
    @ViewInject(R.id.iv_negative)
    private ImageView ivNegative;
    @ViewInject(R.id.iv_hold)
    private ImageView ivHold;
    @ViewInject(R.id.commit_idCard)
    private Button commitIDCard;
    private FinalLoadDialog dialog;

    private List<String> list = new ArrayList<>();
    private Map<String, List<String>> map = new HashMap<>();
    private String orderNo;

    public static int flag;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_upidcard);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("上传身份证照片");
        dialog = new FinalLoadDialog(this);
        dialog.setTitle("上传身份证照片中");
        dialog.setCancelable(false);
        final String isSupplementFile = getIntent().getStringExtra("isSupplementFile");
        orderNo = getIntent().getStringExtra("orderNo");
        if (orderNo == null) {
            orderNo = null;
        } else {
            orderNo = orderNo;
        }
        commitIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("idCard".equals(isSupplementFile)) {
                    if (list.size() > 0) {
                        MyLogUtils.info("list.size()+" + list.size());
                        if (list.size() > 2) {
                            uploadFile(isSupplementFile);
                        } else {
                            MyToastUtils.showShortToast(UpIdCardAct.this, "请选择至少三张照片！");
                        }
                    } else {
                        MyToastUtils.showShortToast(UpIdCardAct.this, "请选择身份证照片！");
                    }
                }
            }
        });
    }

    public void upIdCard(View view) {
        MySelfSheetDialog dialog1 = new MySelfSheetDialog(this);
        switch (view.getId()) {

            case R.id.iv_positive://正面
                flag = 0;

                dialog1.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickCameraImage(null,UpIdCardAct.this);
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickAlbumsImage(null,UpIdCardAct.this);
                    }
                }).show();

                break;
            case R.id.iv_negative://反面
                flag = 1;
                dialog1.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickCameraImage(null,UpIdCardAct.this);
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickAlbumsImage(null,UpIdCardAct.this);
                    }
                }).show();
                break;
            case R.id.iv_hold://手持
                flag = 2;
                dialog1.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickCameraImage(null,UpIdCardAct.this);
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickAlbumsImage(null,UpIdCardAct.this);
                    }
                }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*上传头像样式选择图片*/
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri imageUri = CameraUtils.getBitmapUri(null, this, requestCode, resultCode, data);
        if (imageUri != null) {
            switch (flag){
                case 0:
                    Bitmap bitmap = MyBitmapUtils.decodeUriAsBitmap(UpIdCardAct.this, imageUri);
                    File idcardPositive = MyBitmapUtils.saveBitmap(bitmap, "idcardPositive.png");
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    ivPositive.setImageBitmap(bitmap);
                    list.add(idcardPositive.getPath());
                    break;
                case 1:
                    Bitmap bitmap1 = MyBitmapUtils.decodeUriAsBitmap(UpIdCardAct.this, imageUri);
                    File idcardNegative = MyBitmapUtils.saveBitmap(bitmap1, "idcardNegative.png");
                    BitmapFactory.Options options1 = new BitmapFactory.Options();
                    options1.inSampleSize = 2;
                    ivNegative.setImageBitmap(bitmap1);
                    list.add(idcardNegative.getPath());
                    break;
                case 2:
                    Bitmap bitmap2 = MyBitmapUtils.decodeUriAsBitmap(UpIdCardAct.this, imageUri);
                    File idcardHold = MyBitmapUtils.saveBitmap(bitmap2, "idcardHold.png");
                    BitmapFactory.Options options2 = new BitmapFactory.Options();
                    options2.inSampleSize = 2;
                    ivHold.setImageBitmap(bitmap2);
                    list.add(idcardHold.getPath());
                    break;
            }
        }
        /*上传附件样式选择图片*/
//        Intent intent = new Intent(CreditSecondFrag.IMAGE);
//        /**第一次添加照片 */
//        if (requestCode == ADD_PHOTO_CODE && resultCode == RESULT_OK) {
//            if (data != null) {
////                gvPhoto.setVisibility(View.VISIBLE);
//                selecteds = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
//                path = selecteds.get(0).getPath();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2;
//                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//                switch (flag) {
//                    case 0:
//                        ivPositive.setImageBitmap(bitmap);
//                        list.add(path);
//                        break;
//                    case 1:
////                            path = selecteds.get(0).getPath();
//                        ivNegative.setImageBitmap(bitmap);
//                        list.add(path);
//                        break;
//                    case 2:
////                            path = selecteds.get(0).getPath();
//                        ivHold.setImageBitmap(bitmap);
//                        list.add(path);
//                        break;
//                }
//                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                sendBroadcast(intent);
////                tvSelect.setVisibility(View.GONE);
//            }
//        }
//        /**预览删除照片*/
//        if (requestCode == PHOTO_CODE0 && resultCode == RESULT_OK) {
//            if (data != null) {
//                if (selecteds.size() > 0) {
//                    selecteds = (List<ImageBean>) data.getSerializableExtra("M_LIST");
//                    intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                    sendBroadcast(intent);
//                }
//            }
//        }

    }


    /**
     * 上传身份证照片
     *
     * @param
     */
    private void uploadFile(final String isSupplementFile) {
        dialog.show();
        Map<String, List<FileBody>> fileMaps = new HashMap<String, List<FileBody>>();
        List<FileBody> lists = new ArrayList<FileBody>();
        for (int i = 0; i < list.size(); i++) {
            MyLogUtils.info("取出list里的值："+list.get(i));
            FileBody fb = new FileBody(new File(list.get(i)));
            lists.add(fb);
        }
        fileMaps.put("myfiles", lists);

        RequestManager.getCommManager().submitFujian(orderNo, isSupplementFile, fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                dialog.cancel();
                try {
                    JSONObject data = new JSONObject(result);

                    String orderNo = data.optString("orderNo");
//                    Intent intent = new Intent(CreditSecondFrag.IMAGE);
//                    intent.putExtra(PicSelectActivity.IMAGES, orderNo);
//                    if ("idCard".equals(isSupplementFile)) {
//                        intent.putExtra("isLoadCard", true);
//                    }
//                    sendBroadcast(intent);
                    LogUtils.i("orderNo:" + orderNo);
                    MyToastUtils.showShortToast(UpIdCardAct.this, "上传成功");
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int status, String msg) {
                dialog.cancel();
                MyLogUtils.info(msg);
                return;
            }
        });

    }
}
