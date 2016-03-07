package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.PicSelectActivity;
import com.beyonditsm.financial.entity.ImageBean;
import com.beyonditsm.financial.fragment.CreditSecondFrag;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传身份证照片
 * Created by Administrator on 2016/3/2.
 */
public class UpIdCardAct extends BaseActivity{
    @ViewInject(R.id.iv_positive)
    private ImageView ivPositive;
    @ViewInject(R.id.iv_negative)
    private ImageView ivNegative;
    @ViewInject(R.id.iv_hold)
    private ImageView ivHold;
    @ViewInject(R.id.commit_idCard)
    private Button commitIDCard;
    private FinalLoadDialog dialog;

    private List<ImageBean> selecteds = new ArrayList<ImageBean>();
    private List<String> list = new ArrayList<>();
    private String orderNo;

    private String path;// 图片全路径
    public static final int ADD_PHOTO_CODE = 0x300;
    public static final int PHOTO_CODE0 = 0x456;
    public static int flag;
    private int maxCount = 2;
    private int selectCount = 1;

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
        if (orderNo==null){
            orderNo= null;
        }else{
            orderNo = orderNo;
        }
        commitIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("idCard".equals(isSupplementFile)) {
                    if (list.size() > 0) {
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

    public void upIdCard(View view){
        switch (view.getId()){
            case R.id.iv_positive://正面
                flag = 0;
                    Intent intent1 = new Intent(this, PicSelectActivity.class);
                    intent1.putExtra(PicSelectActivity.SELECT_COUNT, selectCount);
                    intent1.putExtra(PicSelectActivity.TOTAL_COUNT, maxCount);
                    startActivityForResult(intent1, ADD_PHOTO_CODE);

                break;
            case R.id.iv_negative://反面
                flag = 1;
                Intent intent2 = new Intent(this,PicSelectActivity.class);
                intent2.putExtra(PicSelectActivity.SELECT_COUNT,selectCount);
                intent2.putExtra(PicSelectActivity.TOTAL_COUNT,maxCount);
                startActivityForResult(intent2,ADD_PHOTO_CODE);
                break;
            case R.id.iv_hold://手持
                flag =2;
                Intent intent3 = new Intent(this,PicSelectActivity.class);
                intent3.putExtra(PicSelectActivity.SELECT_COUNT,selectCount);
                intent3.putExtra(PicSelectActivity.TOTAL_COUNT,maxCount);
                startActivityForResult(intent3,ADD_PHOTO_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(CreditSecondFrag.IMAGE);
        /**第一次添加照片 */
        if (requestCode == ADD_PHOTO_CODE && resultCode == RESULT_OK) {
            if (data != null) {
//                gvPhoto.setVisibility(View.VISIBLE);
                selecteds = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                path = selecteds.get(0).getPath();
                switch (flag){
                    case 0:
                        ivPositive.setImageBitmap(BitmapFactory.decodeFile(path));
                        list.add(path);
                        break;
                    case 1:
                        ivNegative.setImageBitmap(BitmapFactory.decodeFile(path));
                        list.add(path);
                        break;
                    case 2:
                        ivHold.setImageBitmap(BitmapFactory.decodeFile(path));
                        list.add(path);
                        break;
                }
                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
                sendBroadcast(intent);
//                tvSelect.setVisibility(View.GONE);
            }
        }

//        /**继续添加照片 */
//        if (requestCode == GvPhotoAdapter.ADD_PHOTO_CODE && resultCode == RESULT_OK) {
//            if (data != null) {
//                List<ImageBean> addSelect = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
//                selecteds.addAll(addSelect);
//                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                sendBroadcast(intent);
//            }
//        }
        /**预览删除照片*/
        if (requestCode == PHOTO_CODE0 && resultCode == RESULT_OK) {
            if (data != null) {
                if (selecteds.size() > 0) {
                    selecteds = (List<ImageBean>) data.getSerializableExtra("M_LIST");
                    intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
                    sendBroadcast(intent);
                }
            }
        }

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
        for (int i = 0; i < selecteds.size(); i++) {
            FileBody fb = new FileBody(new File(selecteds.get(i).getPath()));
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
                    Intent intent = new Intent(CreditSecondFrag.IMAGE);
                    intent.putExtra(PicSelectActivity.IMAGES, orderNo);
                    if ("idCard".equals(isSupplementFile)) {
                        intent.putExtra("isLoadCard", true);
                    }
                    sendBroadcast(intent);
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
