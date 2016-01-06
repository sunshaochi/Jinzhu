package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ServantEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.view.crop.square.CameraUtils;
import com.beyonditsm.financial.view.crop.square.Crop;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 普通用户允许升级后，补充资料
 * Created by bitch-1 on 2015/12/4.
 */
public class AddInfo extends BaseActivity {
    private EditText skbk, szbk, bkcode, dzyx;//收款银行，收支银行，银行账号，电子邮箱
    private ImageView imgz, imgf, imgg;//身份证正，反，工作证
    private TextView tjtv;//提交按钮

    private String bksk;
    private String bksz;
    private String codebk;
    private String yxdz;
    private String zimg, fimg, gimg;

    private ServantEntity servant;
    private String sevant_role_id = "2c908c7e51418f64015141ed8b460036";
    private File userbanner;
//    private String id;
//    private String accountId;

    private UserEntity user;

    private int count;


    @Override
    public void setLayout() {
        setContentView(R.layout.activity_addinfo);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("补充资料");
        setLeftTv("返回");
        setView();
        servant = new ServantEntity();
        Intent intent = getIntent();
        user=intent.getParcelableExtra("accountId");
    }

    private void setView() {
        skbk = (EditText) findViewById(R.id.skbk);
        szbk = (EditText) findViewById(R.id.szbk);
        bkcode = (EditText) findViewById(R.id.bkcode);
        dzyx = (EditText) findViewById(R.id.dzyx);
        imgz = (ImageView) findViewById(R.id.imgz);
        imgf = (ImageView) findViewById(R.id.imgf);
        imgg = (ImageView) findViewById(R.id.imgg);
    }

    public void setPhone() {//获得图片方法
        MySelfSheetDialog dialog = new MySelfSheetDialog(this);
        dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Crop.pickCameraImage(null, AddInfo.this);
            }
        }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Crop.pickAlbumsImage(null, AddInfo.this);
            }
        }).show();
    }

    @OnClick({R.id.imgz, R.id.imgf, R.id.imgg, R.id.tjtv})
    public void toClick(View v) {

        switch (v.getId()) {
            case R.id.imgz:
                count = 0;
                setPhone();
                break;
            case R.id.imgf:
                count = 1;
                setPhone();
                break;
            case R.id.imgg:
                count = 2;
                setPhone();
                break;
            case R.id.tjtv:
                bksk = skbk.getText().toString().trim();//收款银行
                bksz = szbk.getText().toString().trim();//收支银行
                codebk = bkcode.getText().toString().trim();//卡号
                yxdz = dzyx.getText().toString();//电子邮箱
                if (TextUtils.isEmpty(zimg) || TextUtils.isEmpty(fimg) || TextUtils.isEmpty(gimg)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请上传相关材料照片");
                } else if (TextUtils.isEmpty(bksk)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入收款银行");
                    skbk.requestFocus();
                } else if (TextUtils.isEmpty(bksz)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入收支银行");
                    szbk.requestFocus();
                } else if (TextUtils.isEmpty(codebk)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入银行卡号");
                    bkcode.requestFocus();
                } else if (TextUtils.isEmpty(yxdz)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入电子邮箱");
                    dzyx.requestFocus();
                } else {
                    servant.setEmail(yxdz);//电子邮箱
                    servant.setBankAccNo(codebk);//银行账号
                    servant.setBankNameTitle(bksz);//支行名称
                    servant.setCustomerId(user.getId());//获取用户资料
                    servant.setBankName(bksk);//获取用户资料
                    servant.setAccountId(user.getAccountId());
                    servant.setHeadIcon(user.getHeadIcon());
                    servant.setUserName(user.getUserName());
                    servant.setIdentCard(user.getIdentCard());
                    servant.setDetailAddr(user.getDetailAddr());

                    RequestManager.getCommManager().upgrade(servant, sevant_role_id, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            AppManager.getAppManager().finishActivity(NewWorkAct.class);
                            finish();
                        }

                        @Override
                        public void onError(int status,String msg) {
                            MyToastUtils.showShortToast(AddInfo.this, msg);
                        }
                    });
                }
        }
    }

    /**
     * 图片选择及拍照结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri imageUri = CameraUtils.getBitmapUri(null, this, requestCode, resultCode, data);
        if (imageUri != null) {
            /**
             * 上传头像
             */
            Bitmap bitmap = MyBitmapUtils.decodeUriAsBitmap(AddInfo.this, imageUri);
            userbanner = MyBitmapUtils.saveBitmap(bitmap, "financial.png");
            uploadFile(userbanner.getPath());
            switch (count) {
                case 0:
                    imgz.setImageBitmap(bitmap);

                    break;
                case 1:
                    imgf.setImageBitmap(bitmap);
                    break;
                case 2:
                    imgg.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadFile(final String file) {
        Map<String, FileBody> fileMaps = new HashMap<String, FileBody>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().loadSmalImage(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                switch (count) {
                    case 0:
                        servant.setIdCardPic(result);//身份证正面
                        zimg = result;
                        break;
                    case 1:
                        fimg = result;
                        break;
                    case 2:
                        servant.setWkCardPic( result);//工作证
                        gimg = result;
                        break;
                }
            }

            @Override
            public void onError(int status,String msg) {
                MyLogUtils.info(msg);
            }
        });
    }


}
