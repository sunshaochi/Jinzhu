package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.PicSelectActivity;
import com.beyonditsm.financial.adapter.GvPhotoAdapter;
import com.beyonditsm.financial.entity.ImageBean;
import com.beyonditsm.financial.fragment.CreditSecondFrag;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.leaf.library.widget.MyGridView;
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
 * Created by Yang on 2015/12/16 0016.
 */
public class UpLoadFileAct extends BaseActivity {
    @ViewInject(R.id.my_gv)
    private MyGridView gvPhoto;
    @ViewInject(R.id.commit_file)
    private Button commit_file;
    /**
     * 照片List
     */
    private List<ImageBean> selecteds = new ArrayList<ImageBean>();
    private List<String> imageList = new ArrayList<String>();
    private GvPhotoAdapter adapter;
    private String uploadStr = null;
    String order =null;

    @Override
    public void setLayout() {
        setContentView(R.layout.uploadfile);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("上传附件");
        setLeftTv("返回");
        final String isSupplementFile = getIntent().getStringExtra("isSupplementFile");
        adapter = new GvPhotoAdapter(selecteds, 9, UpLoadFileAct.this);
        gvPhoto.setAdapter(adapter);
        commit_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(isSupplementFile)) {
                    if (selecteds.size() > 0) {
                        uploadFile(isSupplementFile);
                    } else {
                        MyToastUtils.showShortToast(UpLoadFileAct.this, "请选择附件照片！");
                    }
                } else if ("idCard".equals(isSupplementFile)) {
                    if (selecteds.size() > 0) {
                        if (selecteds.size() > 2) {
                            uploadFile(isSupplementFile);
                        } else {
                            MyToastUtils.showShortToast(UpLoadFileAct.this, "请选择至少三张照片！");
                        }
                    } else {
                        MyToastUtils.showShortToast(UpLoadFileAct.this, "请选择身份证照片！");
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(CreditSecondFrag.IMAGE);
        /**第一次添加照片 */
        if (requestCode == 0x123 && resultCode == RESULT_OK) {
            if (data != null) {
                gvPhoto.setVisibility(View.VISIBLE);
                selecteds = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                if (adapter == null) {
                    adapter = new GvPhotoAdapter(selecteds, 3, UpLoadFileAct.this);
                    gvPhoto.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
                sendBroadcast(intent);
//                tvSelect.setVisibility(View.GONE);
            }
        }

        /**继续添加照片 */
        if (requestCode == GvPhotoAdapter.ADD_PHOTO_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<ImageBean> addSelect = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                selecteds.addAll(addSelect);
                adapter.notifyDataSetChanged();
                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
                sendBroadcast(intent);
            }
        }
        /**预览删除照片*/
        if (requestCode == GvPhotoAdapter.PHOTO_CODE0 && resultCode == RESULT_OK) {
            if (data != null) {
                if (selecteds.size() > 0) {
                    selecteds = (List<ImageBean>) data.getSerializableExtra("M_LIST");
                    adapter.notifyDataChange(selecteds);
                    intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
                    sendBroadcast(intent);
                }
            }
        }

    }

    /**
     * 上传图片
     *
     * @param
     */
    private void uploadFile(String isSupplementFile) {
        Map<String, List<FileBody>> fileMaps = new HashMap<String, List<FileBody>>();
        List<FileBody> lists = new ArrayList<FileBody>();
        for (int i = 0; i < selecteds.size(); i++) {
            FileBody fb = new FileBody(new File(selecteds.get(i).getPath()));
            lists.add(fb);
        }
        fileMaps.put("myfiles", lists);

        RequestManager.getCommManager().submitFujian(order, isSupplementFile, fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                try {
                    JSONObject data = new JSONObject(result);

                    String orderNo = data.optString("orderNo");
                    order = orderNo;
                    Intent intent = new Intent(CreditSecondFrag.IMAGE);
                    intent.putExtra(PicSelectActivity.IMAGES, orderNo);
                    sendBroadcast(intent);
                    LogUtils.i("orderNo:" + orderNo);
                    MyToastUtils.showShortToast(UpLoadFileAct.this, "上传成功");
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int status, String msg) {
                MyLogUtils.info(msg);
                return;
            }
        });

    }
}
