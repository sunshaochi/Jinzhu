package com.beyonditsm.financial.activity.servicer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.manager.SelectSexAct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
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

import org.json.JSONException;

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
 * Created by Administrator on 2015/11/26.
 */
public class ServiceDataAct extends BaseActivity {

    //头像
    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;

    //性别
    @ViewInject(R.id.tvSex)
    private TextView tvSex;

    //年龄

    @ViewInject(R.id.tvAge)
    private TextView tvAge;

    //常住地
    @ViewInject(R.id.tvHome)
    private TextView tvHome;

    //真实姓名
    @ViewInject(R.id.tvName)
    private TextView tvName;

    //身份证号
    @ViewInject(R.id.tvCard)
    private TextView tvCard;

    //所在城市

    @ViewInject(R.id.tvCity)
    private TextView tvCity;

    //户籍地址
    @ViewInject(R.id.tvHouseHold)
    private TextView tvHouseHold;

    //收支银行
    @ViewInject(R.id.tvBank)
    private TextView tvBank;

    //收支支行
    @ViewInject(R.id.tv_subbranch)
    private TextView tvSubbranch;

    //银行账号
    @ViewInject(R.id.tv_bankAcount)
    private TextView tvBankAcount;

    //电子邮箱
    @ViewInject(R.id.tvEmail)
    private TextView tvEmail;

    private String path;// 图片全路径
    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取
    private String photoSavePath;
    private String photoSaveName;
    Uri imageUri = null;
    String appHome = Environment.getExternalStorageDirectory().getAbsolutePath() + "/financial_tx";


    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
//    private ServantEntity ;
    private UserEntity servantInfo;
    private AddressUtil addressUtil;

    @Override
    public void setLayout() {
        setContentView(R.layout.servicedataact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("我的资料");
        addressUtil = new AddressUtil(this);
        servantInfo = getIntent().getParcelableExtra(ServiceMineFrg.SERVANT_INFO);
        MyLogUtils.error("收款支行"+servantInfo.getBankName()+"银行账号"+servantInfo.getBankAccNo());
//        userInfo = getIntent().getParcelableExtra(ServiceMineFrg.USER_INFO);
//        MyLogUtils.info("------------------------"+userInfo);
        if (servantInfo != null) {
            setServantInfo(servantInfo);
        }else{
            findServantDatas();
        }
        EventBus.getDefault().register(this);
        photoSavePath = Environment.getExternalStorageDirectory() + "/ClipHeadPhoto/cache/";
    }

    private void setServantInfo(UserEntity usrInfo) {
        if (usrInfo!=null) {

            //头像
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + usrInfo.getHeadIcon(), civHead, options);

            //用户姓名
            if (!TextUtils.isEmpty(usrInfo.getUserName())) {
                tvName.setText(usrInfo.getUserName());
            }
            //身份证号
            if (!TextUtils.isEmpty(usrInfo.getIdentCard())) {
                tvCard.setText(usrInfo.getIdentCard());
            }
            //性别
            if (usrInfo.getUserSex() != null) {
                if (usrInfo.getUserSex() == 0) {
                    tvSex.setText("女");
                } else {
                    tvSex.setText("男");
                }
            }
            //年龄
            if (usrInfo.getUserAge() != null) {
                tvAge.setText(usrInfo.getUserAge()+"");
            }
            //户籍地址
            if (!TextUtils.isEmpty(usrInfo.getNativePlaceAddr())) {
                tvHouseHold.setText(usrInfo.getNativePlaceAddr());
            }
            //银行账号
            if (!TextUtils.isEmpty(usrInfo.getBankAccNo())) {
                tvBankAcount.setText(usrInfo.getBankAccNo());
            }
            //邮箱
            if (!TextUtils.isEmpty(usrInfo.getEmail())) {
                tvEmail.setText(usrInfo.getEmail());
            }
            //常住地
            if (!TextUtils.isEmpty(usrInfo.getProvince())&&!TextUtils.isEmpty(usrInfo.getCity())&&!TextUtils.isEmpty(usrInfo.getDistrict())) {
//                tvHome.setText(se.getProvince()+se.getCity()+se.getDistrict());
               tvHome.setText(addressUtil.getProName(usrInfo.getProvince())
                        +addressUtil.getCityName(usrInfo.getProvince(),usrInfo.getCity())
                        +addressUtil.getCountryName(usrInfo.getCity(),usrInfo.getDistrict()));
            }
            //所在城市
            if (!TextUtils.isEmpty(usrInfo.getNativePlace())) {
                tvCity.setText(usrInfo.getNativePlace());
            }
            //收款支行
            if (!TextUtils.isEmpty(usrInfo.getBankName())) {
                tvSubbranch.setText(usrInfo.getBankName());
            }
            //收款银行
            if (!TextUtils.isEmpty(usrInfo.getBankNameTitle())) {
                tvBank.setText(usrInfo.getBankNameTitle());
            }
            MyLogUtils.error("收款支行" + usrInfo.getBankName() + "银行账号" + usrInfo.getBankAccNo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(UserEvent event) {
        servantInfo = event.ue;
        switch (event.position) {
            case 0://真实姓名
                tvName.setText(servantInfo.getUserName());
                break;
            case 1://身份证号
                tvCard.setText(servantInfo.getIdentCard());
                break;
            case 4://收支银行
                tvBank.setText(servantInfo.getBankNameTitle());
                break;
            case 5://收支支行
                tvSubbranch.setText(servantInfo.getBankName());
                break;
            case 6://银行账户
                tvBankAcount.setText(servantInfo.getBankAccNo());
                break;
            case 7://电子邮箱
                tvEmail.setText(servantInfo.getEmail());
                break;
            case 8://修改年龄
                tvAge.setText(servantInfo.getUserAge() + "");
                break;
            case 10://修改性别
                if (servantInfo.getUserSex() == 0) {
                    tvSex.setText("女");
                } else {
                    tvSex.setText("男");
                }
                break;
        }
    }

    @OnClick({R.id.rlHead, R.id.rlsexSelector, R.id.rlAge, R.id.rlHome, R.id.rlName, R.id.rlCard, R.id.rlCity, R.id.rlNative,
            R.id.rlHouseHold, R.id.rlBank, R.id.rlSubbranch, R.id.rlBankAccount, R.id.rlEmail})
    public void todo(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rlHead://头像
                MySelfSheetDialog dialog = new MySelfSheetDialog(this);
                dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickCameraImage(null,ServiceDataAct.this);
//                        photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
//                        Uri imageUri = null;
//                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
//                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        startActivityForResult(openCameraIntent, PHOTOTAKE);
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickAlbumsImage(null,ServiceDataAct.this);
//                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                        startActivityForResult(openAlbumIntent, PHOTOZOOM);
                    }
                }).show();
                break;
            case R.id.rlsexSelector://性别
                intent = new Intent(this, SelectSexAct.class);
                intent.putExtra(SelectSexAct.SEX, servantInfo.getUserSex());
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
                startActivity(intent);
                break;
            case R.id.rlAge://年龄
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 8);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
//                intent.putExtra(ServiceMineFrg.USER_INFO,userInfo);
                startActivity(intent);
                break;
            case R.id.rlHome://常住地
                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(this).builder();
                dialogChooseAdress.show();
                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        String proCode=addressUtil.getProCode(adress.get(0));
                        String cityCode=addressUtil.getCityCode(proCode, adress.get(1));
                        String districtCode=addressUtil.getCountryCode(cityCode,adress.get(2));
                        servantInfo.setProvince(proCode);
                        servantInfo.setCity(cityCode);
                        servantInfo.setDistrict(districtCode);
                        updateServantDatas(servantInfo, 0);
                    }
                });
                break;
            case R.id.rlName://姓名
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 0);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
//                intent.putExtra(ServiceMineFrg.USER_INFO,userInfo);
                startActivity(intent);
                break;
            case R.id.rlCard://身份证号
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 1);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
//                intent.putExtra(ServiceMineFrg.USER_INFO,userInfo);
                startActivity(intent);
                break;
            case R.id.rlCity://所在城市
                DialogChooseProvince dialogChooseAdress1 = new DialogChooseProvince(this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseProvince.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        servantInfo.setNativePlace(adress);
                        updateServantDatas(servantInfo, 1);
                    }
                });
                break;
            case R.id.rlHouseHold://户籍
                DialogChooseAdress dialogChooseAdress2 = new DialogChooseAdress(this).builder();
                dialogChooseAdress2.show();
                dialogChooseAdress2.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        servantInfo.setDetailAddr(adress.get(0)+adress.get(1)+adress.get(2));
                        updateServantDatas(servantInfo, 2);
                    }
                });
                break;
            case R.id.rlBank://收支银行
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 4);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
                startActivity(intent);
                break;
            case R.id.rlSubbranch://收支支行
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 5);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
                startActivity(intent);
                break;
            case R.id.rlBankAccount://银行账号
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 6);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
                startActivity(intent);
                break;
            case R.id.rlEmail://电子邮箱
                intent = new Intent(this, ServiceEditAct.class);
                intent.putExtra(ServiceEditAct.USER_TYPE, 7);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, servantInfo);
//                intent.putExtra(ServiceMineFrg.USER_INFO,userInfo);
                startActivity(intent);
                break;

        }
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
        if (imageUri != null) {
            /**
             * 上传头像
             */
            Bitmap bitmap = MyBitmapUtils.decodeUriAsBitmap(ServiceDataAct.this, imageUri);
            File userbanner = MyBitmapUtils.saveBitmap(bitmap, "userhead.png");
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
//                path = photoSavePath + photoSaveName;
//                uri = Uri.fromFile(new File(path));
//                Intent intent2 = new Intent(this, ClipActivity.class);
//                intent2.putExtra("path", path);
//                startActivityForResult(intent2, IMAGE_COMPLETE);
//                break;
//
//            case IMAGE_COMPLETE:// 完成
//                temppath = data.getStringExtra("path");
////                civHead.setImageBitmap(getimage(temppath));
//                uploadFile(appHome + "/tx.png");
//                break;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(final String file) {
        Map<String, FileBody> fileMaps = new HashMap<String, FileBody>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().loadSmalImage(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(servantInfo.getAccountId(), servantInfo.getUserName(),
                        Uri.parse(IFinancialUrl.BASE_IMAGE_URL + result)));
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + result, civHead, options);
                servantInfo.setHeadIcon(result);
                updateServantDatas(servantInfo, 3);
            }

            @Override
            public void onError(int status,String msg) {
                MyLogUtils.info(msg);
            }
        });
    }

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

    private void updateServantDatas(final UserEntity se, final int type) {

        RequestManager.getServicerManager().UpadateServantData(se, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

                switch (type) {

                    case 0://常住地
                        tvHome.setText(addressUtil.getProName(se.getProvince())
                                +addressUtil.getCityName(se.getProvince(),se.getCity())
                                +addressUtil.getCountryName(se.getCity(),se.getDistrict()));
                        break;
                    case 1://所在城市
                        tvCity.setText(se.getNativePlace());
                        break;
                    case 2://户籍
                        tvHouseHold.setText(se.getNativePlaceAddr());
                        break;
                    case 3:

                        break;

                }

                Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, se);
                sendBroadcast(intent);
                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
            }


            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    private void findServantDatas(){
        RequestManager.getServicerManager().findServantDetail(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                servantInfo = rd.getData();
                if (servantInfo!=null) {

                    Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
                    intent.putExtra(ServiceMineFrg.SERVANT_INFO,servantInfo);
                    sendBroadcast(intent);
                    setServantInfo(servantInfo);
                }
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(ServiceDataAct.this, msg);
            }
        });
    }
}
