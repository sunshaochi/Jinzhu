package com.beyonditsm.financial.activity.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.view.crop.square.CameraUtils;
import com.beyonditsm.financial.view.crop.square.Crop;
import com.beyonditsm.financial.widget.DialogChooseAdress;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 我的资料
 * Created by wangbin on 15/11/12.
 */
public class UpdateAct extends BaseActivity {

    private UserEntity userInfo;//传过来的user信息

    private String path;// 图片全路径
    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取
    private String photoSavePath;
    private String photoSaveName;
    Uri imageUri = null;

    private static final int CAMERA_REQUEST_CODE=2;
    private boolean isGetPermission=false;
    String appHome = Environment.getExternalStorageDirectory().getAbsolutePath() + "/financial_tx";

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;//头像
    @ViewInject(R.id.tvSex)
    private TextView tvSex;//性别
    @ViewInject(R.id.cb_select_sex)
    private CheckBox cbSelectSex;//选择性别

    @ViewInject(R.id.tvAge)
    private TextView tvAge;//年龄
    @ViewInject(R.id.tvName)
    private TextView tvName;//用户姓名
    @ViewInject(R.id.tvCard)
    private TextView tvCard;//身份证号
    @ViewInject(R.id.tvMarry)
    private TextView tvMarry;//婚姻状况
    @ViewInject(R.id.tvnative)
    private TextView tvnative;//籍贯
    @ViewInject(R.id.tvHouseHold)
    private TextView tvHouseHold;//户籍地址
    @ViewInject(R.id.tvLocal)
    private TextView tvLocal;
//    @ViewInject(R.id.loadingView)
//    private LoadingView loadingView;
    @ViewInject(R.id.Service_ll)
    private LinearLayout llServant;

    private AddressUtil addressUtil;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_update);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的资料");
        setLeftTv("返回");
        addressUtil=new AddressUtil(this);
        userInfo = getIntent().getParcelableExtra(MineFragment.USER_KEY);
        //把用户信息放进去
        if (userInfo != null)
            setUserMes(userInfo);
        else
            getUserInfo();

        //注册EventBus
        EventBus.getDefault().register(this);
//        photoSavePath = Environment.getExternalStorageDirectory().getPath() + "/ClipHeadPhoto/cache/";
//        File tempFile = new File(photoSavePath);
//        if (!tempFile.exists()) {
//            tempFile.mkdirs();
//        }

//        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
//            @Override
//            public void OnRetry() {
//                getUserInfo();
//            }
//        });
        cbSelectSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    userInfo.setUserSex(0);
                    updateData(userInfo,5);
                }else{
                    userInfo.setUserSex(1);
                    updateData(userInfo,5);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(UserEvent event) {
        userInfo = event.ue;
        switch (event.position) {
            case 0://真实姓名
                tvName.setText(event.ue.getUserName());
                break;
            case 1://身份证号
                tvCard.setText(userInfo.getIdentCard());
                if (userInfo.getUserSex() == 0) {
//                    tvSex.setText("女");
                    cbSelectSex.setChecked(true);
                } else {
//                    tvSex.setText("男");
                    cbSelectSex.setChecked(false);
                }
                if (userInfo.getUserAge() != null)
                    tvAge.setText(userInfo.getUserAge() + "");
                tvnative.setText(userInfo.getNativePlace());
                break;
            case 8://修改年龄
                tvAge.setText(userInfo.getUserAge() + "");
                break;
            case 10://修改性别
                if (userInfo.getUserSex() == 0) {
//                    tvSex.setText("女");
                    cbSelectSex.setChecked(true);
                } else {
//                    tvSex.setText("男");
                    cbSelectSex.setChecked(false);
                }
                break;
        }
    }

    @OnClick({R.id.rlHead, R.id.rlName, R.id.rlCard, R.id.rlNative, R.id.rlHouseHold, R.id.rlBank,
            R.id.rlSonBank, R.id.rlBankNumber, R.id.rlEmail, R.id.rlAge, R.id.rlMarry, R.id.rlLocal})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //头像
            case R.id.rlHead:
                MySelfSheetDialog dialog = new MySelfSheetDialog(this);
                dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickCameraImage(null, UpdateAct.this);
//                        photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
//                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
////                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        startActivityForResult(openCameraIntent, PHOTOTAKE);
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickAlbumsImage(null, UpdateAct.this);
//                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                        startActivityForResult(openAlbumIntent, PHOTOZOOM);
                    }
                }).show();
                break;
            case R.id.rlName://真实姓名
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 0);
                intent.putExtra(MineFragment.USER_KEY, userInfo);
                startActivity(intent);
                break;
            case R.id.rlCard://身份证号
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 1);
                intent.putExtra(MineFragment.USER_KEY, userInfo);
                startActivity(intent);
                break;
            case R.id.rlLocal://常住地
                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(this).builder();
                dialogChooseAdress.show();
                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        String proCode=addressUtil.getProCode(adress.get(0));
                        String cityCode=addressUtil.getCityCode(proCode, adress.get(1));
                        String districtCode=addressUtil.getCountryCode(cityCode,adress.get(2));
                        userInfo.setProvince(proCode);
                        userInfo.setCity(cityCode);
                        userInfo.setDistrict(districtCode);
                        updateData(userInfo, 3);
                    }
                });
                break;
            case R.id.rlNative://籍贯
                DialogChooseProvince dialogChooseProvince = new DialogChooseProvince(this).builder();
                dialogChooseProvince.show();
                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseProvince.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        userInfo.setNativePlace(adress);
                        updateData(userInfo, 4);
//                        tvnative.setText(adress);
                    }
                });
                break;
            case R.id.rlHouseHold://户籍地址
//                intent = new Intent(this, EditAct.class);
//                intent.putExtra(EditAct.USER_TYPE, 3);
//                startActivity(intent);
                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        userInfo.setNativePlaceAddr(adress.get(0)+adress.get(1)+adress.get(2));
                        updateData(userInfo, 2);
                    }
                });
                break;
            case R.id.rlBank://收支银行
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 4);
                startActivity(intent);
                break;
            case R.id.rlSonBank://收支支行
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 5);
                startActivity(intent);
                break;
            case R.id.rlBankNumber://银行账号
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 6);
                startActivity(intent);
                break;
            case R.id.rlEmail://邮箱
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 7);
                startActivity(intent);
                break;
            case R.id.rlAge://年龄
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 8);
                intent.putExtra(MineFragment.USER_KEY, userInfo);
                startActivity(intent);
                break;
            case R.id.rlMarry://婚姻状况
                MySelfSheetDialog mDialog = new MySelfSheetDialog(UpdateAct.this);
                mDialog.builder().addSheetItem("未婚", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        userInfo.setMarrySts(0);
                        updateData(userInfo, 0);
                    }
                }).addSheetItem("已婚", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        userInfo.setMarrySts(1);
                        updateData(userInfo, 0);
                    }
                }).show();
                break;
//            case R.id.rlSex://性别
//                intent = new Intent(this, SelectSexAct.class);
//                intent.putExtra(SelectSexAct.SEX, userInfo.getUserSex());
//                intent.putExtra(MineFragment.USER_KEY, userInfo);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 填入信息
     *
     * @param userInfo
     */
    private void setUserMes(UserEntity userInfo) {
        if (userInfo != null) {
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + userInfo.getHeadIcon(), civHead, options);
            if (userInfo.getUserSex() != null) {
                if (userInfo.getUserSex() == 0) {
//                    tvSex.setText("女");
                    cbSelectSex.setChecked(true);
                } else {
//                    tvSex.setText("男");
                    cbSelectSex.setChecked(false);
                }
            }

            if (userInfo.getUserAge() != null) {
                tvAge.setText(userInfo.getUserAge() + "");
            }
            if (!TextUtils.isEmpty(userInfo.getUserName())) {
                tvName.setText(userInfo.getUserName());
            }
            if (!TextUtils.isEmpty(userInfo.getIdentCard())) {
                tvCard.setText(userInfo.getIdentCard());
            }
            if (userInfo.getMarrySts() != null) {
                if (userInfo.getMarrySts() == 0) {
                    tvMarry.setText("未婚");
                } else {
                    tvMarry.setText("已婚");
                }
            }
            if (!TextUtils.isEmpty(userInfo.getNativePlace())) {//籍贯地址
                tvnative.setText(userInfo.getNativePlace());
            }
            if (!TextUtils.isEmpty(userInfo.getNativePlaceAddr())) {
                tvHouseHold.setText(userInfo.getNativePlaceAddr());
            }
            if (!TextUtils.isEmpty(userInfo.getProvince())&&!TextUtils.isEmpty(userInfo.getCity())&&!TextUtils.isEmpty(userInfo.getDistrict())) {
                tvLocal.setText(addressUtil.getProName(userInfo.getProvince())
                        +addressUtil.getCityName(userInfo.getProvince(),userInfo.getCity())
                        +addressUtil.getCountryName(userInfo.getCity(),userInfo.getDistrict()));
            }

        }
    }

    /**
     * 更新资料
     *
     * @param ue
     */
    private void updateData(final UserEntity ue, final int iType) {
        RequestManager.getCommManager().updateData(ue, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                switch (iType) {
                    case 0://婚姻状况
                        if (ue.getMarrySts() == 0) {
                            tvMarry.setText("未婚");
                        } else {
                            tvMarry.setText("已婚");
                        }
                        break;
                    case 1://上传头像

                        break;
                    case 2://更新户籍地址
                        tvHouseHold.setText(ue.getNativePlaceAddr());
                        break;
                    case 3://常住地
                        tvLocal.setText(addressUtil.getProName(ue.getProvince())
                                +addressUtil.getCityName(ue.getProvince(),ue.getCity())
                                +addressUtil.getCountryName(ue.getCity(),ue.getDistrict()));
                        break;
                    case 4://籍贯
                        tvnative.setText(ue.getNativePlace());
                        break;
                    case 5://性别
                        if (ue.getUserSex()==1){
                            cbSelectSex.setChecked(false);
                        }else{
                            cbSelectSex.setChecked(true);
                        }
                        break;
                }
                Intent intent = new Intent(MineFragment.UPDATE_USER);
                intent.putExtra(MineFragment.USER_KEY, ue);
                sendBroadcast(intent);

                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
//                loadingView.loadComplete();
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                userInfo = rd.getData();
                if (userInfo != null) {
                    Intent intent = new Intent(MineFragment.UPDATE_USER);
                    intent.putExtra(MineFragment.USER_KEY, userInfo);
                    sendBroadcast(intent);
                    setUserMes(userInfo);
                }
            }

            @Override
            public void onError(int status,String msg) {
//                loadingView.loadError();
                MyToastUtils.showShortToast(UpdateAct.this, msg);
            }
        });
    }

    /**
     * 返回的Path
     */
    private String temppath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri imageUri = CameraUtils.getBitmapUri(null, this, requestCode, resultCode, data);
        MyLogUtils.info("imageUri+"+imageUri);
        if (imageUri != null) {
            /**
             * 上传头像
             */
            Bitmap bitmap = MyBitmapUtils.decodeUriAsBitmap(UpdateAct.this, imageUri);
            File userbanner = MyBitmapUtils.saveBitmap(bitmap, "userhead.png");
            MyLogUtils.info("userbanner.getPath+"+userbanner.getPath());
            uploadFile(userbanner.getPath());
        }

//        Uri uri = null;
//        Intent intent = null;
//        switch (requestCode) {
//            case PHOTOZOOM:// 相册
//                if (data == null) {
//                    return;
//                }
//                uri = data.getData();
//                Bitmap userbitmap = MyBitmapUtils.decodeUriAsBitmap(this, uri);
//                File user_head = MyBitmapUtils.saveBitmap(userbitmap, "user_head.png");
//                intent = new Intent(this, ClipActivity.class);
//                intent.putExtra("path", Environment.getExternalStorageDirectory() + "/" + "user_head.png");
//                startActivityForResult(intent, IMAGE_COMPLETE);
//                break;
//            case PHOTOTAKE:// 拍照
////                path = photoSavePath + photoSaveName;
////                uri = Uri.fromFile(new File(path));
//                Intent intent2 = new Intent(this, ClipActivity.class);
//                intent2.putExtra("path", imageUri.getPath());
//                startActivityForResult(intent2, IMAGE_COMPLETE);
//                break;
//
//            case IMAGE_COMPLETE:// 完成
//                temppath = data.getStringExtra("path");
//                uploadFile(temppath);
//                break;
//        }
        super.onActivityResult(requestCode, resultCode, data);
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
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(userInfo.getAccountId(), userInfo.getUserName(),
                        Uri.parse(IFinancialUrl.BASE_IMAGE_URL + result)));
                FriendBean friendBean = new FriendBean();
                friendBean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + result);
                friendBean.setUserId(userInfo.getAccountId());
                friendBean.setUserName(userInfo.getUserName());
                FriendDao.updateUser(friendBean);

                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + result, civHead, options);
                userInfo.setHeadIcon(result);
                updateData(userInfo, 1);
            }

            @Override
            public void onError(int status, String msg) {
                MyLogUtils.info(msg);
            }
        });
    }


    /**
     * 压缩头像
     *
     * @param srcPath
     * @return
     */
    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        // 方法1 Android获得屏幕的宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 150;// 这里设置高度为800f
        float ww = 150;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {
        File destDir = new File(appHome);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 15) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 80;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(appHome + "/tx.png");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bitmap.compress(format, quality, stream);
        return bitmap;
    }

    /**
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //6.0系统（API23）下检查并申请权限
    private void findCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //申请相机权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }
    //6.0系统用户选择权限允许或者取消之后回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }
    private void doNext(int requestCode,int[] grantResults) {
        if (requestCode==CAMERA_REQUEST_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){//权限授予
                isGetPermission = true;
                SpUtils.setISpermission(getApplicationContext(), isGetPermission);
                MyLogUtils.info("是否获取到权限："+isGetPermission);
            }else{//权限否认
                isGetPermission = false;
                MyLogUtils.info("是否获取到权限："+isGetPermission);
                MyToastUtils.showShortToast(getApplicationContext(),"没有权限");
            }
        }
    }
}
