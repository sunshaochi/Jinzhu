package com.beyonditsm.financial.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AdressBean;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.entity.ProfileInfoBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.entity.UserLoginBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.view.crop.square.CameraUtils;
import com.beyonditsm.financial.view.crop.square.Crop;
import com.beyonditsm.financial.widget.ScaleAllImageView;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTInterface;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的资料
 * Created by wangbin on 15/11/12.
 */
public class UpdateAct extends BaseActivity {
    private ProfileInfoBean userInfo;//传过来的user信息
    private UserLoginBean userLoginBean;
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
    private AdressBean adressBean=new AdressBean();
    private List<JJTProvinceEntity> provinceList;
    private DialogJJTAddress dialogJJTAddress;
    private List<JJTCityEntity> cityList;
    private List<JJTCounyEntity> counyList;
    private String defaultProvince;
    private String defaultCity;
    private String defaultArea;
    private String nativePlaceProvince;
    private String nativePlaceCity;
    private String nativePlaceDistrict;
    private String nativePlaceAddrProvince;
    private String nativePlaceAddrCity;
    private String nativePlaceAddrDistrict;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null!=userInfo){
            outState.putParcelable("UserEntity",userInfo);
        }
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if (null!=savedInstanceState){
//            userInfo = savedInstanceState.getParcelable("UserEntity");
//        }
//    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_update);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的资料");
        setLeftTv("返回");
        queryProvince();
        //强制关闭键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        addressUtil = new AddressUtil(this);
        adressBean = new AdressBean();
        userLoginBean = getIntent().getParcelableExtra(MineFragment.USER_KEY);
        //把用户信息放进去
        if (userLoginBean != null) {
            if (userLoginBean.getProfileInfo()!=null){
                userInfo=userLoginBean.getProfileInfo();
                adressBean=userLoginBean.getAddress();
                setUserMes(userInfo,adressBean);
            }else {
                userInfo=new ProfileInfoBean();
                adressBean=userLoginBean.getAddress();
                setUserMes(userInfo,adressBean);
            }
        } else{
            getUserInfo();
        }
        //注册EventBus
        EventBus.getDefault().register(this);
        cbSelectSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userInfo.setSex(0+"");
                    updateData(userInfo,adressBean, 5);
                } else {
                    userInfo.setSex(1+"");
                    updateData(userInfo,adressBean, 5);
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
                tvName.setText(event.ue.getName());
                break;
            case 1://身份证号
                tvCard.setText(userInfo.getIdNo());
                if (TextUtils.equals(userInfo.getSex(),0+"")) {
                    cbSelectSex.setChecked(true);
                } else {
                    cbSelectSex.setChecked(false);
                }
                if (userInfo.getAge() != null)
                    tvAge.setText(userInfo.getAge() + "");
                tvnative.setText(userInfo.getNativePlace());
                break;
            case 8://修改年龄
                tvAge.setText(userInfo.getAge() + "");
                break;
            case 10://修改性别
                if (TextUtils.equals(userInfo.getSex(),0+"")) {
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
                intent.putExtra(MineFragment.USER_KEY, userLoginBean);
                startActivity(intent);
                break;
            case R.id.rlCard://身份证号
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 1);
                intent.putExtra(MineFragment.USER_KEY, userLoginBean);
                startActivity(intent);
                break;
            case R.id.rlLocal://常住地
//                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(this).builder();
//                dialogChooseAdress.show();
//                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress) {
//                        String proCode = addressUtil.getProCode(adress.get(0));
//                        String cityCode = addressUtil.getCityCode(proCode, adress.get(1));
//                        String districtCode = addressUtil.getCountryCode(cityCode, adress.get(2));
//                        userInfo.setProvince(proCode);
//                        userInfo.setCity(cityCode);
//                        userInfo.setDistrict(districtCode);
//                        updateData(userInfo, 3);
//                    }
//                });
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        defaultProvince = adress.get(0);
                        String provinceCode = queryProvinceCodeByName(defaultProvince);
                        defaultCity = adress.get(1);
                        if (!TextUtils.isEmpty(defaultCity)){
                            String cityCode = queryCityCodeByName(defaultCity);
                            adressBean.setCity(defaultCity);
                        }else {
                            adressBean.setCity("");
                        }
                        defaultArea = adress.get(2);

                        if (!TextUtils.isEmpty(defaultArea)){
                            String districtCode = queryAreaCodeByName(defaultArea);
                            adressBean.setDistrict(defaultArea);
                        }

                        adressBean.setProvince(defaultProvince);


                        updateData(userInfo,adressBean, 3);
                    }
                });
                break;
            case R.id.rlNative://籍贯
//                DialogChooseAdress dialogChooseProvince = new DialogChooseAdress(this).builder();
//                dialogChooseProvince.show();
//                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress) {
//                        userInfo.setNativePlace(adress.get(0)+adress.get(1)+adress.get(2));
//                        updateData(userInfo, 4);
//                    }
//                });
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        nativePlaceProvince = adress.get(0);
                        String nativePlaceProvinceCode = queryProvinceCodeByName(nativePlaceProvince);
                        nativePlaceCity = adress.get(1);
                        String nativePlaceCityCode = queryCityCodeByName(nativePlaceCity);
                        nativePlaceDistrict = adress.get(2);
                        String nativePlaceCounyCode = queryAreaCodeByName(nativePlaceDistrict);
                        userInfo.setNativePlace(nativePlaceProvince+nativePlaceCity+nativePlaceDistrict);
//                        adressBean.setAddress(nativePlaceAddrDistrict);
//                        adressBean.setProvince(nativePlaceProvince);
//                        adressBean.setCity(nativePlaceCity);

//                        adressBean.setCity(nativePlaceCityCode);
//                        adressBean.set(nativePlaceCounyCode);
                        updateData(userInfo,adressBean, 4);
                    }
                });
                break;
            case R.id.rlHouseHold://户籍地址
//                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(this).builder();
//                dialogChooseAdress1.show();
//                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress) {
//                        userInfo.setNativePlaceAddr(adress.get(0) + adress.get(1) + adress.get(2));
//                        updateData(userInfo, 2);
//                    }
//                });
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        nativePlaceAddrProvince = adress.get(0);
                        String nativePlaceAddrProvinceCode = queryProvinceCodeByName(nativePlaceAddrProvince);
                        nativePlaceAddrCity = adress.get(1);
                        String nativePlaceAddrCityCode = queryCityCodeByName(nativePlaceAddrCity);
                        nativePlaceAddrDistrict = adress.get(2);
                        String nativePlaceAddrCounyCode = queryAreaCodeByName(nativePlaceAddrDistrict);
                        userInfo.setHomeAddress(nativePlaceAddrProvince+nativePlaceAddrCity+nativePlaceDistrict);

//                        userInfo.setNativePlaceAddrProvince(nativePlaceAddrProvinceCode);
//                        userInfo.setNativePlaceAddrCity(nativePlaceAddrCityCode);
//                        userInfo.setNativePlaceAddrDistrict(nativePlaceAddrCounyCode);
                        updateData(userInfo,adressBean, 2);
                    }
                });
                break;
            case R.id.rlAge://年龄
                intent = new Intent(this, EditAct.class);
                intent.putExtra(EditAct.USER_TYPE, 8);
                intent.putExtra(MineFragment.USER_KEY, userLoginBean);
                startActivity(intent);
                break;
            case R.id.rlMarry://婚姻状况
                MySelfSheetDialog mDialog = new MySelfSheetDialog(UpdateAct.this);
                mDialog.builder().addSheetItem("未婚", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        userInfo.setMarried(false);
                        updateData(userInfo,adressBean, 0);
                    }
                }).addSheetItem("已婚", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        userInfo.setMarried(true);
                        updateData(userInfo,adressBean, 0);
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
    private void setUserMes(ProfileInfoBean userInfo ,AdressBean adressBean) {
        if (userInfo != null) {
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + userInfo.getAvatarPic(), civHead, options);
            if (userInfo.getSex() != null) {
                if (TextUtils.equals(userInfo.getSex(),0+"")) {
                    cbSelectSex.setChecked(true);
                } else {
                    cbSelectSex.setChecked(false);
                }
            }

            if (userInfo.getAge() != null) {
                tvAge.setText(userInfo.getAge() + "");
            }
            if (!TextUtils.isEmpty(userInfo.getName())) {
                tvName.setText(userInfo.getName());
            }
            if (!TextUtils.isEmpty(userInfo.getIdNo())) {
                tvCard.setText(userInfo.getIdNo());
            }
//            if (userInfo.isMarried()) {
                if (userInfo.isMarried()) {
                    tvMarry.setText("已婚");
                } else {
                    tvMarry.setText("未婚");
                }
//            }
            if (!TextUtils.isEmpty(userInfo.getNativePlace())) {//籍贯地址
                tvnative.setText(userInfo.getNativePlace());
            }
            if (!TextUtils.isEmpty(userInfo.getHomeAddress())) {//户籍地址
                tvHouseHold.setText(userInfo.getHomeAddress());
            }

            if (adressBean!=null) {//常住地
                if (!TextUtils.isEmpty(adressBean.getProvince())){
                    tvLocal.setText(adressBean.getProvince()+adressBean.getCity()+adressBean.getDistrict());
                }else {
                    tvLocal.setText(adressBean.getCity()+adressBean.getDistrict());
                }
            }

        }
    }

    /**
     * 更新资料
     *
     * @param ue 用户实体类
     */
    private void updateData(final ProfileInfoBean ue, final AdressBean adressBean, final int iType) {
        RequestManager.getCommManager().updateData(ue,adressBean,SpUtils.getPhonenumber(getApplicationContext()), new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) {
                switch (iType) {
                    case 0://婚姻状况
                        if (ue.isMarried()) {
                            tvMarry.setText("已婚");
                        } else {
                            tvMarry.setText("未婚");
                        }
                        break;
                    case 1://上传头像

                        break;
                    case 2://更新户籍地址
                        tvHouseHold.setText(nativePlaceAddrProvince + nativePlaceAddrCity + nativePlaceAddrDistrict);
                        break;
                    case 3://常住地
                        tvLocal.setText(defaultProvince + defaultCity + defaultArea);
                        break;
                    case 4://籍贯
                        tvnative.setText(nativePlaceProvince + nativePlaceCity + nativePlaceDistrict);
                        break;
                    case 5://性别
                        if (TextUtils.equals(ue.getSex(),1+"")) {
                            cbSelectSex.setChecked(false);
                        } else {
                            cbSelectSex.setChecked(true);
                        }
                        break;
                }
                Intent intent = new Intent(MineFragment.UPDATE_USER);
                userLoginBean.setProfileInfo(ue);
                userLoginBean.setAddress(adressBean);
                intent.putExtra(MineFragment.USER_KEY, userLoginBean);
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
        RequestManager.getCommManager().findUserInfo( SpUtils.getPhonenumber(getApplicationContext()),new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
//                loadingView.loadComplete();
                ResultData<UserLoginBean> rd = (ResultData<UserLoginBean>) GsonUtils.json(result, UserLoginBean.class);
                userLoginBean = rd.getData();
                if (userLoginBean != null) {
                    Intent intent = new Intent(MineFragment.UPDATE_USER);
                    intent.putExtra(MineFragment.USER_KEY, userLoginBean);
                    sendBroadcast(intent);
                    if (userLoginBean.getProfileInfo()!=null){
                        userInfo=userLoginBean.getProfileInfo();
                        adressBean=userLoginBean.getAddress();
                        setUserMes(userInfo,adressBean);
                    }
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
     * @param
     */
    private void uploadFile(final String file) {
        Map<String, FileBody> fileMaps = new HashMap<>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().loadSmalImage(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
//                RongIM.getInstance().refreshUserInfoCache(new UserInfo(userInfo.getAccountId(), userInfo.getUserName(),
//                        Uri.parse(IFinancialUrl.BASE_IMAGE_URL + result)));
//                FriendBean friendBean = new FriendBean();
//                friendBean.setUserHead(IFinancialUrl.BASE_IMAGE_URL + result);
//                friendBean.setUserId(userInfo.getAccountId());
//                friendBean.setUserName(userInfo.getUserName());
//                FriendDao.updateUser(friendBean);
//
//                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + result, civHead, options);
//                userInfo.setHeadIcon(result);
//                updateData(userInfo, 1);
            }

            @Override
            public void onError(int status, String msg) {
                MyLogUtils.info(msg);
            }
        });
    }

    private void queryProvince() {
        RequestManager.getCommManager().getProvince(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                provinceList = gson.fromJson(data.toString(), new TypeToken<List<JJTProvinceEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setProvinceEntityList(provinceList);
                if (provinceList != null && provinceList.size() > 0) {
                    dialogJJTAddress = new DialogJJTAddress(UpdateAct.this, provinceList).builder();
                    dialogJJTAddress.getJJTPicker().setOnSrollListener(new JJTInterface() {
                        @Override
                        public void onProvinceSelected(JJTProvinceEntity jjtProvinceEntity) {
                            queryCity(jjtProvinceEntity.getCode());
                        }
                        @Override
                        public void onCitySelected(JJTCityEntity jjtCityEntity) {
                            queryDistrict(jjtCityEntity.getCode());
                        }

                        @Override
                        public void onCounySelected(JJTCounyEntity jjtCounyEntity) {
                        }
                    });
                    queryCity(provinceList.get(0).getCode());
                }
//                if ()
//                queryProvinceCodeByName()
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void queryCity(String provinceCode) {
        RequestManager.getCommManager().getCity(provinceCode, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.get("data") instanceof JSONArray){
                    JSONArray data = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    cityList = gson.fromJson(data.toString(), new TypeToken<List<JJTCityEntity>>() {
                    }.getType());

                    ParamsUtil.getInstance().setCityEntityList(cityList);
                    dialogJJTAddress.getJJTPicker().setCityList();
                    if (cityList != null && cityList.size() > 0) {
                        queryDistrict(cityList.get(0).getCode());
                    }
                }else {
                    cityList = new ArrayList<JJTCityEntity>();
                    JJTCityEntity jjtCityEntity = new JJTCityEntity();
                    jjtCityEntity.setName("");
                    jjtCityEntity.setId("");
                    jjtCityEntity.setCode("");
                    cityList.add(jjtCityEntity);
                    ParamsUtil.getInstance().setCityEntityList(cityList);
                    dialogJJTAddress.getJJTPicker().setCityList();

                    counyList = new ArrayList<JJTCounyEntity>();
                    JJTCounyEntity jjtCounyEntity = new JJTCounyEntity();
                    jjtCounyEntity.setName("");
                    jjtCounyEntity.setId("");
                    counyList.add(jjtCounyEntity);
                    ParamsUtil.getInstance().setCounyEntityList(counyList);
                    dialogJJTAddress.getJJTPicker().setCouny();


                }


            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void queryDistrict(String cityCode) {
        RequestManager.getCommManager().getDistrict(cityCode, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.get("data") instanceof JSONArray){
                    JSONArray data = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    counyList = gson.fromJson(data.toString(), new TypeToken<List<JJTCounyEntity>>() {
                    }.getType());
                    ParamsUtil.getInstance().setCounyEntityList(counyList);
                    dialogJJTAddress.getJJTPicker().setCouny();
                }else {
                    counyList = new ArrayList<JJTCounyEntity>();
                    JJTCounyEntity jjtCityEntity = new JJTCounyEntity();
                    jjtCityEntity.setName("");
                    jjtCityEntity.setId("");
                    counyList.add(jjtCityEntity);
                    ParamsUtil.getInstance().setCounyEntityList(counyList);
                    dialogJJTAddress.getJJTPicker().setCouny();
                }


            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    public String queryProvinceCodeByName(String name) {
        for (int i = 0; i < provinceList.size(); i++) {
            JJTProvinceEntity jjtProvinceEntity = provinceList.get(i);
            if (jjtProvinceEntity.getName().equals(name)) {
                return jjtProvinceEntity.getCode();
            }
        }
        return "";
    }

    public String queryCityCodeByName(String name) {
        for (int i = 0; i < cityList.size(); i++) {
            JJTCityEntity jjtProvinceEntity = cityList.get(i);
            if (jjtProvinceEntity.getName().equals(name)) {
                return jjtProvinceEntity.getCode();
            }
        }
        return "";
    }

    public String queryAreaCodeByName(String name) {
        for (int i = 0; i < counyList.size(); i++) {
            JJTCounyEntity jjtProvinceEntity = counyList.get(i);
            if (jjtProvinceEntity.getName().equals(name)) {
                return jjtProvinceEntity.getCode();
            }
        }
        return "";
    }

}
