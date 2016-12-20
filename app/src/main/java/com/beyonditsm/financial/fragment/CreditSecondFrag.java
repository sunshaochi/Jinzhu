package com.beyonditsm.financial.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.entity.GetCustomerDataBean;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.entity.Orederinfo;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.RelationEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;
import com.tencent.connect.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款第二步
 * Created by Yang on 2015/11/12 0012.
 */
public class CreditSecondFrag extends BaseFragment {
    private ScrollView criSv;

    private EditText name;//姓名
    private EditText IdCard;//身份证
    private EditText phoneNumber;//身份证
    private TextView position;//常住地
    private EditText address;//详细地址
    private TextView tvMarrayed;//是否结婚文本
    private EditText tvJiguan;//籍贯
    private EditText tvAddress;//户籍地址
    private TextView tvWork;//职业身份文本
    private EditText companyName;//公司名
    private EditText zhiwu;//职务
    private EditText age;//年龄
    private TextView tvSb;//社保
    private TextView tvGjj;//公积金
    private TextView tvHome;//房产类型
    private TextView tvCar;//车产类型
    private TextView tvXy;//信用状况
    private Button secondBtnNext;//下一步
    private CheckBox cbSelectSex;
    private AddressUtil addressUtil;

    //订单成功
    @ViewInject(R.id.llSucess)
    private LinearLayout llSucess;
    @ViewInject(R.id.tvSecond)
    private TextView tvSecond;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.zz_ll)
    private LinearLayout zz_ll;//资质扩展区域


    private List<RelationEntity> carList, jobList, hourseList, creditList;//车产，职业，房产，信用
    private int jobPos, carPos, hoursePos, creditPos;
    private ProductBean productInfo;//产品信息
    private boolean jobSelect = false;
    private boolean carSelect = false;
    private boolean hourseSelect = false;
    private boolean creditSelect = false;
    public final String USER_KEY = "user_info";
    public final String PRODUCT_KEY = "product_info";
    public Map<Integer, Boolean> map = new HashMap<>();
    private   GetCustomerDataBean user;
    private String haveHoursId;
    private String haveCarId;
    private String jobId;
    private String creditId;
    private String haveHoursName;
    private String haveCarName;
    private String haveJobName;
    private String creditName;

    private Activity mParentActivity;
    private List<JJTProvinceEntity> provinceList;
    private DialogJJTAddress dialogJJTAddress;
    private List<JJTCityEntity> cityList;
    private List<JJTCounyEntity> counyList;
    private List<String> keyLists;

    private Orederinfo orederinfo;

    private Thread thread;

    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.creditsecondfrag, null);
        assignViews();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {//保存当前状态
        super.onSaveInstanceState(outState);
        outState.putParcelable(USER_KEY, user);
        outState.putParcelable(PRODUCT_KEY, productInfo);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {//赋值当前状态
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState) {
            user = savedInstanceState.getParcelable(USER_KEY);
            productInfo = savedInstanceState.getParcelable(PRODUCT_KEY);
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getData();//获取个人资料并给界面赋值
        addressUtil = new AddressUtil(mParentActivity);
        keyLists = new ArrayList<>();
        keyLists.add("jobType");
        keyLists.add("cardProperty");
        keyLists.add("houseProperty");
        keyLists.add("creidtType");
        getDictionaryContent(keyLists);//职业身份获取身份列表供选择
        user = new GetCustomerDataBean();
        user.setSex("1");

        productInfo = getArguments().getParcelable(HomeCreditDetailAct.PRODUCTINFO);//取到传递过来的产品
        queryProvince();//获取省份
        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getData();
            }
        });
    }

    @Override
    public void setListener() {
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!FinancialUtil.isInputChinese(name.getText().toString())) {
                    name.setError("真实姓名必须为中文！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cbSelectSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setSex("0");
                    MyLogUtils.info("选择了女");
                } else {
                    user.setSex("1");
                    MyLogUtils.info("选择了男");
                }
            }
        });
    }

    private void assignViews() {
        criSv = (ScrollView) view.findViewById(R.id.criSv);
        name = (EditText) view.findViewById(R.id.name);
        IdCard = (EditText) view.findViewById(R.id.IdCard);
        position = (TextView) view.findViewById(R.id.position);
        address = (EditText) view.findViewById(R.id.address);
        tvMarrayed = (TextView) view.findViewById(R.id.tv_marrayed);
        tvJiguan = (EditText) view.findViewById(R.id.tv_jiguan);
        tvAddress = (EditText) view.findViewById(R.id.tv_address);
        tvWork = (TextView) view.findViewById(R.id.tv_work);
        companyName = (EditText) view.findViewById(R.id.company_name);
        zhiwu = (EditText) view.findViewById(R.id.zhiwu);
        age = (EditText) view.findViewById(R.id.age);
        tvSb = (TextView) view.findViewById(R.id.tv_sb);
        tvGjj = (TextView) view.findViewById(R.id.tv_gjj);
        tvHome = (TextView) view.findViewById(R.id.tv_home);
        tvCar = (TextView) view.findViewById(R.id.tv_car);
        tvXy = (TextView) view.findViewById(R.id.tv_xy);
        secondBtnNext = (Button) view.findViewById(R.id.second_btn_next);
        cbSelectSex = (CheckBox) view.findViewById(R.id.cb_select_sex);
        phoneNumber = (EditText) view.findViewById(R.id.et_phoneNumber);
    }

    @OnClick({R.id.cb_select_sex, R.id.second_btn_next, R.id.zz_tv, R.id.rlNative, R.id.rl_marrayed, R.id.rl_sb, R.id.rl_gjj, R.id.rl_work
            , R.id.rl_home, R.id.rl_car, R.id.rl_xy, R.id.rlAddress, R.id.rlPosition, R.id.commit_file, R.id.tvSure})
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.cb_select_sex:

                break;
            case R.id.second_btn_next://下一步
                secondBtnNext.setClickable(false);
                upData();
//                toSubmitOrder();
                break;
            case R.id.rlPosition://常住地
//                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(mParentActivity).builder();
//                dialogChooseAdress.show();
//                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress) {
//
//                    }
//                });
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        String defaultProvince = adress.get(0);
                        String provinceCode = queryProvinceCodeByName(defaultProvince);//通过名字查询id
                        String defaultCity = adress.get(1);
                        String cityCode = queryCityCodeByName(defaultCity);
                        String defaultArea = adress.get(2);
                        String districtCode = queryAreaCodeByName(defaultArea);
                        user.setCurrentProvince(provinceCode);
                        user.setCurrentCity(cityCode);
                        user.setCurrentRegion(districtCode);
                        position.setText(defaultProvince + defaultCity + defaultArea);
                    }
                });
                break;
//            case R.id.rlNative://籍贯
////                DialogChooseAdress dialogChooseProvince = new DialogChooseAdress(mParentActivity).builder();
////                dialogChooseProvince.show();
////                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
////                    @Override
////                    public void getAdress(List<String> adress) {
////                        tvJiguan.setText(adress.get(0)+adress.get(1)+adress.get(2));
////                    }
////                });
//                dialogJJTAddress.show();
//                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress,List<Integer> id) {
//                        String nativePlaceProvince = adress.get(0);
//                        String nativePlaceProvinceCode = queryProvinceCodeByName(nativePlaceProvince);
//                        String nativePlaceCity = adress.get(1);
//                        String nativePlaceCityCode = queryCityCodeByName(nativePlaceCity);
//                        String nativePlaceDistrict = adress.get(2);
//                        String nativePlaceCounyCode = queryAreaCodeByName(nativePlaceDistrict);
//                        user.setNativePlaceProvince(nativePlaceProvinceCode);
//                        user.setNativePlaceCity(nativePlaceCityCode);
//                        user.setNativePlaceDistrict(nativePlaceCounyCode);
//                        tvJiguan.setText(nativePlaceProvince + nativePlaceCity + nativePlaceDistrict);
//                    }
//                });
//
//                break;
//            case R.id.rlAddress://户籍地址
////                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(mParentActivity).builder();
////                dialogChooseAdress1.show();
////                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
////                    @Override
////                    public void getAdress(List<String> adress) {
////                        nativePlaceAddrProvince = adress.get(0);
////                        nativePlaceAddrCity = adress.get(1);
////                        nativePlaceAddrDistrict = adress.get(2);
////                        tvAddress.setText(nativePlaceAddrProvince + nativePlaceAddrCity + nativePlaceAddrDistrict);
////                    }
////                });
//                dialogJJTAddress.show();
//                dialogJJTAddress.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
//                    @Override
//                    public void getAdress(List<String> adress,List<Integer> id) {
//                        String nativePlaceAddrProvince = adress.get(0);
//                        String nativePlaceAddrProvinceCode = queryProvinceCodeByName(nativePlaceAddrProvince);
//                        String nativePlaceAddrCity = adress.get(1);
//                        String nativePlaceAddrCityCode = queryCityCodeByName(nativePlaceAddrCity);
//                        String nativePlaceAddrDistrict = adress.get(2);
//                        String nativePlaceAddrCounyCode = queryAreaCodeByName(nativePlaceAddrDistrict);
//                        user.setNativePlaceAddrProvince(nativePlaceAddrProvinceCode);
//                        user.setNativePlaceAddrCity(nativePlaceAddrCityCode);
//                        user.setNativePlaceAddrDistrict(nativePlaceAddrCounyCode);
//                        tvAddress.setText(nativePlaceAddrProvince + nativePlaceAddrCity + nativePlaceAddrDistrict);
//                    }
//                });
//                break;
            case R.id.zz_tv://我的资质显示与否按钮
                if (!map.get(1)) {
//                    obaDown.start();
                    zz_ll.setVisibility(View.VISIBLE);
                    map.put(1, true);
                    scrollDown();
                } else {
//                    obaOn.start();
                    zz_ll.setVisibility(View.GONE);
                    map.put(1, false);
                }
                break;
            case R.id.rl_marrayed://是否结婚
                MySelfSheetDialog dialog = new MySelfSheetDialog(mParentActivity).builder();
                dialog.addSheetItem("未婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvMarrayed.setText("未婚");
                        user.setIsMarried("0");
                    }
                });
                dialog.addSheetItem("已婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvMarrayed.setText("已婚");
                        user.setIsMarried("1");
                    }
                });
                dialog.show();
                break;
            case R.id.rl_sb: //社保
                dialog = new MySelfSheetDialog(mParentActivity).builder();
                dialog.addSheetItem("否，没有", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvSb.setText("否");
                        user.setHasSocialInsurance("0");
                    }
                });
                dialog.addSheetItem("是，有本地社保", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvSb.setText("是");
                        user.setHasSocialInsurance("1");
                    }
                });
                dialog.show();
                break;
            case R.id.rl_gjj: //公积金
                dialog = new MySelfSheetDialog(mParentActivity).builder();
                dialog.addSheetItem("否，没有", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvGjj.setText("否");
                        user.setHasHouseFunding("0");
                    }
                });
                dialog.addSheetItem("是，有本地公积金", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvGjj.setText("是");
                        user.setHasHouseFunding("1");
                    }
                });
                dialog.show();
                break;
            case R.id.rl_work:
                //企业主、个体户、上班族、无固定职业
                dialog = new MySelfSheetDialog(mParentActivity).builder();
                if (jobList.size() != 0) {
                    for (int i = 0; i < jobList.size(); i++) {
                        dialog.addSheetItem(jobList.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                jobSelect = true;
                                tvWork.setText(jobList.get(which - 1).getOptionName());
                                jobPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_home:
                //无房产、商品房、 住宅、 商铺、 办公楼、 厂房、经济/限价房、房改/危改房

                try{

                    dialog = new MySelfSheetDialog(mParentActivity).builder();

                    if (hourseList.size() != 0) {
                        for (int i = 0; i < hourseList.size(); i++) {
                            dialog.addSheetItem(hourseList.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    hourseSelect = true;
                                    tvHome.setText(hourseList.get(which - 1).getOptionName());
                                    hoursePos = which - 1;
                                }
                            });
                        }
                    }
                    dialog.show();
                }
                catch (NullPointerException e){
                    getDictionaryContent(keyLists);
                }

                break;
            case R.id.rl_car:
                //无车 、名下有车 、有车，但车已被抵押 、无车，准备购买
                dialog = new MySelfSheetDialog(mParentActivity).builder();

                if (carList.size() != 0) {
                    for (int i = 0; i < carList.size(); i++) {
                        dialog.addSheetItem(carList.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                carSelect = true;
                                tvCar.setText(carList.get(which - 1).getOptionName());
                                carPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_xy:
                //无信用卡活贷款 、信用良好
                dialog = new MySelfSheetDialog(mParentActivity).builder();
                if (creditList.size() != 0) {
                    for (int i = 0; i < creditList.size(); i++) {
                        dialog.addSheetItem(creditList.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                creditSelect = true;
                                tvXy.setText(creditList.get(which - 1).getOptionName());
                                creditPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;

            case R.id.tvSure://确定
                i = -1;
                EventBus.getDefault().post(new CreditStepAct.FirstEvent(2, reOrderId));
                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CreditStepAct) {
            mParentActivity = activity;
        }
        if (mParentActivity == null) {
            mParentActivity = CreditStepAct.getInstance();
        }
    }

    //职业身份弹出框
    private void getDictionaryContent( List<String> key) {

        RequestManager.getCommManager().findDicMap(key, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                Gson gson = new Gson();
                jobList = gson.fromJson(data.getJSONArray("jobType").toString(),new TypeToken<List<RelationEntity>>() {
                       }.getType());
                carList = gson.fromJson(data.getJSONArray("cardProperty").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                hourseList = gson.fromJson(data.getJSONArray("houseProperty").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                creditList = gson.fromJson(data.getJSONArray("creidtType").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
//                switch (pos) {
//                    case 0:
//                        jobList = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());//职业身份
//                        break;
//                    case 1:
//                        carList = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                    case 2:
//                        hourseList = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                    case 3:
//                        creditList = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                }

            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
                secondBtnNext.setClickable(true);
                Toast.makeText(mParentActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取个人资料
     */
    private void getData() {
        RequestManager.getCommManager().getCustomerInfo4ApplyOrder(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<GetCustomerDataBean> rd = (ResultData<GetCustomerDataBean>) GsonUtils.json(result, GetCustomerDataBean.class);
                user = rd.getData();
                if (user != null) {
                    haveHoursId = user.getHaveOwnHouse();
                    haveCarId = user.getHaveOwnCar();
                    jobId = user.getCareerName();
                    creditId = user.getCreditState();
                    if (!TextUtils.isEmpty(haveHoursId))
                        for (int i = 0; i < hourseList.size(); i++) {
                            if ( hourseList.get(i).getDictSubId().equals(haveHoursId)){
                                tvHome.setText(hourseList.get(i).getOptionName());
                            }
                        }
                    if (!TextUtils.isEmpty(haveCarId))
                    for (int i = 0; i < carList.size(); i++) {
                        if ( carList.get(i).getDictSubId().equals(haveCarId)){
                            tvCar.setText(carList.get(i).getOptionName());
                        }
                    }
                    if (!TextUtils.isEmpty(jobId))
                    for (int i = 0; i < jobList.size(); i++) {
                        if ( jobList.get(i).getDictSubId().equals(jobId)){
                            tvWork.setText(jobList.get(i).getOptionName());
                        }
                    }
                    if (!TextUtils.isEmpty(creditId))
                    for (int i = 0; i < creditList.size(); i++) {
                        if ( creditList.get(i).getDictSubId().equals(haveHoursId)){
                            tvXy.setText(creditList.get(i).getOptionName());
                        }
                    }
                    if (!TextUtils.isEmpty(user.getCusName())) {
                        name.setText(user.getCusName());
                        name.setSelection(user.getCusName().length());
                    }

                    if (!TextUtils.isEmpty(user.getIdNo())) {
                        IdCard.setText(user.getIdNo());//身份证
                        IdCard.setSelection(user.getIdNo().length());
                    }
                    if (!TextUtils.isEmpty(user.getPhoneNum())) {
                        phoneNumber.setText(user.getPhoneNum());//手机号
                    }

                    //判断男女
                    if (!TextUtils.isEmpty(user.getSex()) ) {
                        if (user.getSex().equals("1")) {
                            cbSelectSex.setChecked(false);
                        } else if (user.equals("0") ) {
                            cbSelectSex.setChecked(true);
                        }
                    }

                    if (!TextUtils.isEmpty(user.getCareerName())) {//职业身份
                        tvWork.setText(user.getCareerName());
                    }

                    if (!TextUtils.isEmpty(user.getAge())) {
                        age.setText(user.getAge() + "");//年龄
                    }

                    if (!TextUtils.isEmpty(user.isHasHouseFunding()) ) {
                        if (user.isHasHouseFunding().equals("true") ) {
                            tvGjj.setText("是");
                            user.setHasHouseFunding("1");
                        } else {
                            tvGjj.setText("否");
                            user.setHasHouseFunding("0");
                        }
                    }

                    if (!TextUtils.isEmpty(user.getCurrentProvince()) && !TextUtils.isEmpty(user.getCurrentCity())
                            && !TextUtils.isEmpty(user.getCurrentRegion())) {
                        position.setText(addressUtil.getProName(user.getCurrentProvince())
                                +addressUtil.getCityName(user.getCurrentProvince(),user.getCurrentCity())
                                +addressUtil.getCityName(user.getCurrentCity(),user.getCurrentRegion()));//常住地
                    }

//                    if (!TextUtils.isEmpty(user.getDetailAddr())) {
//                        address.setText(user.getDetailAddr());//详细地址
//                    }

                    if (!TextUtils.isEmpty(user.getNativePlace())) {
                        tvJiguan.setText(user.getNativePlace());//籍贯
                    }
//                    if (TextUtils.isEmpty(user.getNativePlaceProvince())&&TextUtils.isEmpty(user.getNativePlaceCity())&&TextUtils.isEmpty(user.getNativePlaceDistrict())){
//                        tvJiguan.setText(user.getNativePlace());//籍贯
//                    }else if (!TextUtils.isEmpty(user.getNativePlaceProvince())&&!TextUtils.isEmpty(user.getNativePlaceCity())&&!TextUtils.isEmpty(user.getNativePlaceDistrict())){
//                        tvJiguan.setText(user.getNativePlaceProvince()+user.getNativePlaceCity()+user.getNativePlaceDistrict());//籍贯
//                    }

                    if (!TextUtils.isEmpty(user.getDomicileAddr())) {
                        tvAddress.setText(user.getDomicileAddr());//户籍地址
                    }
//                    if (TextUtils.isEmpty(user.getNativePlaceAddrProvince())&&TextUtils.isEmpty(user.getNativePlaceAddrCity())&&TextUtils.isEmpty(user.getNativePlaceAddrDistrict())){
//                        tvAddress.setText(user.getNativePlaceAddr());//户籍地址
//                    }else if (!TextUtils.isEmpty(user.getNativePlaceProvince())&&!TextUtils.isEmpty(user.getNativePlaceCity())&&!TextUtils.isEmpty(user.getNativePlaceDistrict())){
//                        tvAddress.setText(user.getNativePlaceAddrProvince()+user.getNativePlaceAddrCity()+user.getNativePlaceAddrDistrict());//户籍地址
//                    }

                    if (!TextUtils.isEmpty(user.isIsMarried())) {
                        if (user.isIsMarried().equals("0"))
                            tvMarrayed.setText("未婚");
                        else
                            tvMarrayed.setText("已婚");
                    }

                    if (!TextUtils.isEmpty(user.isHasSocialInsurance())) {
                        if (user.isHasSocialInsurance().equals("false")){
                            tvSb.setText("否");
                            user.setHasSocialInsurance("0");
                        }
                        else{
                            tvSb.setText("是");
                            user.setHasSocialInsurance("1");
                        }

                    }

                    if (!TextUtils.isEmpty(user.getHaveOwnHouse()) && !TextUtils.isEmpty(user.getHaveOwnHouse())) {//房产类型
                        tvHome.setText(user.getHaveOwnHouse());
                    }

                    if (!TextUtils.isEmpty(user.getHaveOwnCar())) {//车产
                        tvCar.setText(user.getHaveOwnCar());
                    }

                    if (!TextUtils.isEmpty(user.getCompany())) {
                        companyName.setText(user.getCompany());
                    }

                    if (!TextUtils.isEmpty(user.getCareerTitle())) {
                        zhiwu.setText(user.getCareerTitle());
                    }

                    if (!TextUtils.isEmpty(user.getCreditState())) {//信用
                        tvXy.setText(user.getCreditState());
                    }
                }

                queryProvince();//获取省份
            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
                MyToastUtils.showShortToast(mParentActivity, msg);
            }
        });
    }

    /**
     * 更新个人资料
     */
    private void upData() {
        if (isHaveData()) {
            user.setIdNo(IdCard.getText().toString());
//            user.set(address.getText().toString());
            user.setNativePlace(tvJiguan.getText().toString()); //设置籍贯

            user.setDomicileAddr(tvAddress.getText().toString());//设置户籍

            user.setPhoneNum(phoneNumber.getText().toString());
            user.setAge(age.getText().toString());
            if (carSelect) {
                user.setHaveOwnCar(carList.get(carPos).getDictSubId());//车产
//                user.setHaveCarName(carList.get(carPos).getName());
            } else {
                if (!TextUtils.isEmpty(haveCarId) ) {
                    user.setHaveOwnCar(haveCarId);
                }
            }
            if (jobSelect) {
                user.setCareerName(jobList.get(jobPos).getDictSubId());//职业身份
            } else {
                if (!TextUtils.isEmpty(jobId)) {
                    user.setCareerName(jobId);
                }
            }
            if (hourseSelect) {
                user.setHaveOwnHouse(hourseList.get(hoursePos).getDictSubId());//房产
            } else {
                if (!TextUtils.isEmpty(haveHoursId)) {
                    user.setHaveOwnHouse(haveHoursId);
                }
            }
            if (creditSelect) {
                user.setCreditState(creditList.get(creditPos).getDictSubId());//信用状况
            } else {
                if (!TextUtils.isEmpty(creditId)) {
                    user.setCreditState(creditId);
                }
            }
            user.setCusName(name.getText().toString());
            user.setCompany(companyName.getText().toString());
            user.setCareerTitle(zhiwu.getText().toString());
            toSubmitOrder();
//            RequestManager.getCommManager().updateData(user, new RequestManager.CallBack() {
//                @Override
//                public void onSucess(String result) throws JSONException {
//                    String roleName = SpUtils.getRoleName(context);
//                        Intent intent = new Intent(MineFragment.UPDATE_USER);
//                        intent.putExtra(MineFragment.USER_KEY, user);
//                        mParentActivity.sendBroadcast(intent);
//                        mParentActivity.sendBroadcast(new Intent(MineFragment.UPDATE_SCORE));
//
//
//                    toSubmitOrder();//立即申请提交订单
//                }
//
//                @Override
//                public void onError(int status, String msg) {
//                    secondBtnNext.setClickable(true);
//                }
//            });
        }
    }

    private boolean isHaveData() {
        if (!FinancialUtil.isInputChinese(name.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "真实姓名必须为中文！");
            secondBtnNext.setClickable(true);
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请输入真实姓名");
            secondBtnNext.setClickable(true);
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(IdCard.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请输入身份证号");
            secondBtnNext.setClickable(true);
            IdCard.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请输入手机号");
            secondBtnNext.setClickable(true);
            phoneNumber.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(position.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择常住地址");
            secondBtnNext.setClickable(true);
            position.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvMarrayed.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择婚姻状况");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvJiguan.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请填写籍贯");
            secondBtnNext.setClickable(true);
            tvJiguan.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvAddress.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请填写户籍地址");
            secondBtnNext.setClickable(true);
            tvAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvWork.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择职业身份");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(companyName.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请填写公司名称");
            secondBtnNext.setClickable(true);
            companyName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(zhiwu.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请填写您的职务");
            secondBtnNext.setClickable(true);
            zhiwu.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(age.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请填写您的年龄");
            secondBtnNext.setClickable(true);
            age.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSb.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择是否有社保");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvGjj.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择是否有公积金");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvHome.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择房产类型");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvCar.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择车产类型");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvXy.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请选择您的信用状况");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (!IdcardUtils.validateCard(IdCard.getText().toString())) {
            MyToastUtils.showShortToast(mParentActivity, "请输入合法的身份证号码");
            secondBtnNext.setClickable(true);
            return false;

        }
        return true;
    }

    private int i = 5;
    private String reOrderId;//快速申贷返回来的订单id传给第三不

    /**
     * 提交订单
     */
    private void toSubmitOrder() {
//        OrderBean orderBean = new OrderBean();
        orederinfo = new Orederinfo();
        orederinfo.getProductInfo().setProductId(productInfo.getProductId());
//        orderBean.setProductId(productInfo.getProductId());
        if (null == HomeCreditDetailAct.creditMoney) {
            MyToastUtils.showShortToast(mParentActivity, "网络不给力，请返回重新提交");
            secondBtnNext.setClickable(true);
        } else {
            orederinfo.getOrderInfo().setApplyAmount((Double.parseDouble(HomeCreditDetailAct.creditMoney) * 10000 + ""));//设置金额
            orederinfo.getOrderInfo().setApplyPeriods(HomeCreditDetailAct.creditMonth);//总期数
//            orderBean.setPeriodsAmount(HomeCreditDetailAct.monthlyPayments);//单期还款金额
            orederinfo.getCustomerInfo().setCusName(user.getCusName());//姓名
            orederinfo.getCustomerInfo().setSex(user.getSex());//性别
            orederinfo.getCustomerInfo().setIdNo(user.getIdNo());//身份证
            orederinfo.getCustomerInfo().setCurrentProvince(user.getCurrentProvince());//省
            orederinfo.getCustomerInfo().setCurrentCity(user.getCurrentCity());//市
            orederinfo.getCustomerInfo().setCurrentRegion(user.getCurrentRegion());//区
            orederinfo.getCustomerInfo().setIsMarried(user.isIsMarried());//婚姻状态
            orederinfo.getCustomerInfo().setNativePlace(user.getNativePlace());//籍贯
            orederinfo.getCustomerInfo().setDomicileAddr(user.getDomicileAddr());//户籍地
            orederinfo.getCustomerInfo().setPhoneNum(user.getPhoneNum());//手机号
            orederinfo.getCustomerInfo().setCareerName(user.getCareerName());//职业身份
            orederinfo.getCustomerInfo().setCompany(user.getCompany());//公司名称
            orederinfo.getCustomerInfo().setCareerTitle(user.getCareerTitle());//职务
            orederinfo.getCustomerInfo().setAge(user.getAge());//年纪
            orederinfo.getCustomerInfo().setHasHouseFunding(user.isHasHouseFunding());//是否有公积金
            orederinfo.getCustomerInfo().setHasSocialInsurance(user.isHasSocialInsurance());//是否有社保
            orederinfo.getCustomerInfo().setHaveOwnCar(user.getHaveOwnCar());//名下车产类型
            orederinfo.getCustomerInfo().setHaveOwnHouse(user.getHaveOwnHouse());//房产类型
            orederinfo.getCustomerInfo().setCreditState(user.getCreditState());//信用状况


            RequestManager.getCommManager().submitOrder(orederinfo, new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) {
                    secondBtnNext.setClickable(true);
                    llSucess.setVisibility(View.VISIBLE);
                    criSv.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
                        reOrderId = data.getString("orderId");//获取订单id
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (i > 0) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    i--;
                                    handler.sendEmptyMessage(i);

                                }
                            }
                        });
                        thread.start();
                }

                @Override
                public void onError(int status, String msg) {
                    secondBtnNext.setClickable(true);
//                        MyToastUtils.showShortToast(mParentActivity, msg);
                }
            });
        }


    }

    /**
     * 倒计时
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                tvSecond.setText(msg.what + "");
            } else {
                EventBus.getDefault().post(new CreditStepAct.FirstEvent(2, reOrderId));
            }
        }
    };

    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                criSv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    //获得省
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
                    dialogJJTAddress = new DialogJJTAddress(getActivity(), provinceList).builder();
                    dialogJJTAddress.getJJTPicker().setOnSrollListener(new JJTInterface() {
                        @Override
                        public void onProvinceSelected(JJTProvinceEntity jjtProvinceEntity) {
                            queryCity(jjtProvinceEntity.getCode());//获取城市
                        }

                        @Override
                        public void onCitySelected(JJTCityEntity jjtCityEntity) {
                            queryDistrict(jjtCityEntity.getCode());//获取地区
                        }

                        @Override
                        public void onCounySelected(JJTCounyEntity jjtCounyEntity) {
                        }
                    });
                    queryCity(provinceList.get(0).getCode());//通过省份id查询城市
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
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                cityList = gson.fromJson(data.toString(), new TypeToken<List<JJTCityEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCityEntityList(cityList);
                dialogJJTAddress.getJJTPicker().setCityList();
                if (cityList != null && cityList.size() > 0) {
                    queryDistrict(cityList.get(0).getCode());//查询地区
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
//查询地区
    private void queryDistrict(String cityCode) {
        RequestManager.getCommManager().getDistrict(cityCode, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                counyList = gson.fromJson(data.toString(), new TypeToken<List<JJTCounyEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCounyEntityList(counyList);
                dialogJJTAddress.getJJTPicker().setCouny();

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}