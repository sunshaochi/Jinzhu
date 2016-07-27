package com.beyonditsm.financial.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.io.File;
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

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @ViewInject(R.id.civHead)
    private ScaleAllImageView civHead;//头像
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

    private AddressUtil addressUtil;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null!=userInfo){
            outState.putParcelable("UserEntity",userInfo);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null!=savedInstanceState){
            userInfo = savedInstanceState.getParcelable("UserEntity");
        }
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_update);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的资料");
        setLeftTv("返回");
        //强制关闭键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        addressUtil = new AddressUtil(this);
        userInfo = getIntent().getParcelableExtra(MineFragment.USER_KEY);
        //把用户信息放进去
        if (userInfo != null)
            setUserMes(userInfo);
        else
            getUserInfo();

        //注册EventBus
        EventBus.getDefault().register(this);
        cbSelectSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userInfo.setUserSex(0);
                    updateData(userInfo, 5);
                } else {
                    userInfo.setUserSex(1);
                    updateData(userInfo, 5);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    public void onEvent(UserEvent event) {
        userInfo = event.ue;
        switch (event.position) {
            case 0://真实姓名
                tvName.setText(event.ue.getUserName());
                break;
            case 1://身份证号
                tvCard.setText(userInfo.getIdentCard());
                if (userInfo.getUserSex() == 0) {
                    cbSelectSex.setChecked(true);
                } else {
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
                    cbSelectSex.setChecked(true);
                } else {
                    cbSelectSex.setChecked(false);
                }
                break;
        }
    }

    @OnClick({R.id.rlHead, R.id.rlName, R.id.rlCard, R.id.rlNative, R.id.rlHouseHold, R.id.rlAge, R.id.rlMarry, R.id.rlLocal})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //头像
            case R.id.rlHead:
                MySelfSheetDialog dialog = new MySelfSheetDialog(this);
                dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                            Crop.pickCameraImage(null, UpdateAct.this);
                        } else {
                            MyToastUtils.showShortToast(getApplicationContext(), "请在设置中勾选应用的相机权限");
                        }
                    }
                }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        Crop.pickAlbumsImage(null, UpdateAct.this);

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
                        String proCode = addressUtil.getProCode(adress.get(0));
                        String cityCode = addressUtil.getCityCode(proCode, adress.get(1));
                        String districtCode = addressUtil.getCountryCode(cityCode, adress.get(2));
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
                    }
                });
                break;
            case R.id.rlHouseHold://户籍地址
                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        userInfo.setNativePlaceAddr(adress.get(0) + adress.get(1) + adress.get(2));
                        updateData(userInfo, 2);
                    }
                });
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
        }
    }

    /**
     * 填入信息
     *
     * @param userInfo 用户实体类
     */
    @SuppressLint("SetTextI18n")
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
            if (!TextUtils.isEmpty(userInfo.getProvince()) && !TextUtils.isEmpty(userInfo.getCity()) && !TextUtils.isEmpty(userInfo.getDistrict())) {
                tvLocal.setText(addressUtil.getProName(userInfo.getProvince())
                        + addressUtil.getCityName(userInfo.getProvince(), userInfo.getCity())
                        + addressUtil.getCountryName(userInfo.getCity(), userInfo.getDistrict()));
            }

        }
    }

    /**
     * 更新资料
     *
     * @param ue 用户实体类
     */
    private void updateData(final UserEntity ue, final int iType) {
        RequestManager.getCommManager().updateData(ue, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
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
                                + addressUtil.getCityName(ue.getProvince(), ue.getCity())
                                + addressUtil.getCountryName(ue.getCity(), ue.getDistrict()));
                        break;
                    case 4://籍贯
                        tvnative.setText(ue.getNativePlace());
                        break;
                    case 5://性别
                        if (ue.getUserSex() == 1) {
                            cbSelectSex.setChecked(false);
                        } else {
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
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
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
            public void onError(int status, String msg) {
//                loadingView.loadError();
                MyToastUtils.showShortToast(UpdateAct.this, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri imageUri = CameraUtils.getBitmapUri(null, this, requestCode, resultCode, data);
        MyLogUtils.info("imageUri+" + imageUri);
        if (imageUri != null) {
            /**
             * 上传头像
             */
            Bitmap bitmap = MyBitmapUtils.decodeUriAsBitmap(UpdateAct.this, imageUri);
            File userbanner = MyBitmapUtils.saveBitmap(bitmap, "userhead.png");
            MyLogUtils.info("userbanner.getPath+" + userbanner.getPath());
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
     * @param file 文件
     */
    private void uploadFile(final String file) {
        Map<String, FileBody> fileMaps = new HashMap<>();
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

}
