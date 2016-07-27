package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditDetailAct;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.entity.DictionaryType;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseAdress;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TextView position;//常住地
    private EditText address;//详细地址
    private TextView tvMarrayed;//是否结婚文本
    private TextView tvJiguan;//籍贯
    private TextView tvAddress;//户籍地址
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


    private List<DictionaryType> carList, jobList, hourseList, creditList;//车产，职业，房产，信用
    private int jobPos, carPos, hoursePos, creditPos;
    private ProductInfo productInfo;//产品信息
    private boolean jobSelect = false;
    private boolean carSelect = false;
    private boolean hourseSelect = false;
    private boolean creditSelect = false;

    private Map<Integer, Boolean> map = new HashMap<>();
    private UserEntity user;
    private String haveHoursId;
    private String haveCarId;
    private String jobId;
    private String creditId;
    private String haveHoursName;
    private String haveCarName;
    private String haveJobName;
    private String creditName;

    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.creditsecondfrag, null);
        assignViews();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        addressUtil = new AddressUtil(getActivity());
        getDictionaryContent(0, "job_identity");//职业身份
        getDictionaryContent(1, "under_own_car");//名下车产
        getDictionaryContent(2, "under_own_hour");//名下房产
        getDictionaryContent(3, "two_year_credit");//信用状况


        map.put(1, false);
        map.put(2, false);
        productInfo = getArguments().getParcelable(CreditDetailAct.PRODUCTINFO);
        getData();
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
                    user.setUserSex(0);
                    MyLogUtils.info("选择了女");
                } else {
                    user.setUserSex(1);
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
        tvJiguan = (TextView) view.findViewById(R.id.tv_jiguan);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
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
    }
    @OnClick({R.id.cb_select_sex, R.id.second_btn_next, R.id.zz_tv, R.id.rlNative, R.id.rl_marrayed, R.id.rl_sb, R.id.rl_gjj, R.id.rl_work
            , R.id.rl_home, R.id.rl_car, R.id.rl_xy, R.id.rlAddress, R.id.rlPosition, R.id.commit_file,R.id.tvSure})
    public void todo(View v){
        switch (v.getId()){
            case R.id.cb_select_sex:

                break;
            case R.id.second_btn_next://下一步
                secondBtnNext.setClickable(false);
                upData();
                break;
            case R.id.rlPosition://常住地
                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(getActivity()).builder();
                dialogChooseAdress.show();
                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        String proCode = addressUtil.getProCode(adress.get(0));
                        String cityCode = addressUtil.getCityCode(proCode, adress.get(1));
                        String districtCode = addressUtil.getCountryCode(cityCode, adress.get(2));
                        user.setProvince(proCode);
                        user.setCity(cityCode);
                        user.setDistrict(districtCode);
                        position.setText(addressUtil.getProName(user.getProvince())
                                + addressUtil.getCityName(user.getProvince(), user.getCity())
                                + addressUtil.getCountryName(user.getCity(), user.getDistrict()));
                    }
                });
                break;
            case R.id.rlNative://籍贯
                DialogChooseProvince dialogChooseProvince = new DialogChooseProvince(getActivity()).builder();
                dialogChooseProvince.show();
                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseProvince.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvJiguan.setText(adress);
                    }
                });
                break;
            case R.id.rlAddress://户籍地址
                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(getActivity()).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        tvAddress.setText(adress.get(0) + adress.get(1) + adress.get(2));
                    }
                });
                break;
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
                MySelfSheetDialog dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("未婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvMarrayed.setText("未婚");
                        user.setMarrySts(0);
                    }
                });
                dialog.addSheetItem("已婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvMarrayed.setText("已婚");
                        user.setMarrySts(1);
                    }
                });
                dialog.show();
                break;
            case R.id.rl_sb:
                dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("否，没有", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvSb.setText("否");
                        user.setSecailSecurity(0);
                    }
                });
                dialog.addSheetItem("是，有本地社保", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvSb.setText("是");
                        user.setSecailSecurity(1);
                    }
                });
                dialog.show();
                break;
            case R.id.rl_gjj:
                dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("否，没有", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvGjj.setText("否");
                        user.setProFund(0);
                    }
                });
                dialog.addSheetItem("是，有本地公积金", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvGjj.setText("是");
                        user.setProFund(1);
                    }
                });
                dialog.show();
                break;
            case R.id.rl_work:
                //企业主、个体户、上班族、无固定职业
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (jobList.size() != 0) {
                    for (int i = 0; i < jobList.size(); i++) {
                        dialog.addSheetItem(jobList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                jobSelect = true;
                                tvWork.setText(jobList.get(which - 1).getName());
                                jobPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_home:
                //无房产、商品房、 住宅、 商铺、 办公楼、 厂房、经济/限价房、房改/危改房

                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (hourseList.size() != 0) {
                    for (int i = 0; i < hourseList.size(); i++) {
                        dialog.addSheetItem(hourseList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                hourseSelect = true;
                                tvHome.setText(hourseList.get(which - 1).getName());
                                hoursePos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_car:
                //无车 、名下有车 、有车，但车已被抵押 、无车，准备购买
                dialog = new MySelfSheetDialog(getActivity()).builder();

                if (carList.size() != 0) {
                    for (int i = 0; i < carList.size(); i++) {
                        dialog.addSheetItem(carList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                carSelect = true;
                                tvCar.setText(carList.get(which - 1).getName());
                                carPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_xy:
                //无信用卡活贷款 、信用良好
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (creditList.size() != 0) {
                    for (int i = 0; i < creditList.size(); i++) {
                        dialog.addSheetItem(creditList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                creditSelect = true;
                                tvXy.setText(creditList.get(which - 1).getName());
                                creditPos = which - 1;
                            }
                        });
                    }
                }
                dialog.show();
                break;

            case R.id.tvSure://确定
                i=-1;
                EventBus.getDefault().post(new CreditStepAct.FirstEvent(2,reOrderId));
                break;
        }
    }
    private void getDictionaryContent(final int pos, String key) {

        RequestManager.getCommManager().getDicMap(key, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                switch (pos) {
                    case 0:
                        jobList = gson.fromJson(dataArr.toString(), new TypeToken<List<DictionaryType>>() {
                        }.getType());
                        break;
                    case 1:
                        carList = gson.fromJson(dataArr.toString(), new TypeToken<List<DictionaryType>>() {
                        }.getType());
                        break;
                    case 2:
                        hourseList = gson.fromJson(dataArr.toString(), new TypeToken<List<DictionaryType>>() {
                        }.getType());
                        break;
                    case 3:
                        creditList = gson.fromJson(dataArr.toString(), new TypeToken<List<DictionaryType>>() {
                        }.getType());
                        break;
                }

            }

            @Override
            public void onError(int status, String msg) {
                secondBtnNext.setClickable(true);
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 获取个人资料
     */
    private void getData(){
        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                ResultData<UserEntity> rd = (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user = rd.getData();
                if (user != null) {
                    haveHoursId = user.getHaveHours();
                    haveHoursName = user.getHaveHoursName();
                    haveCarId = user.getHaveCar();
                    haveCarName = user.getHaveCarName();
                    jobId = user.getJobId();
                    haveJobName = user.getHavaJobName();
                    creditId = user.getTowYearCred();
                    creditName = user.getTowYearCredName();
                    if (!TextUtils.isEmpty(user.getUserName())) {
                        name.setText(user.getUserName());
                        name.setSelection(user.getUserName().length());
                    }

                    if (!TextUtils.isEmpty(user.getIdentCard())) {
                        IdCard.setText(user.getIdentCard());//身份证
                        IdCard.setSelection(user.getIdentCard().length());
                    }

                    //判断男女
                    if (user.getUserSex() != null) {
                        if (user.getUserSex() == 1) {
                            cbSelectSex.setChecked(false);
                        } else if (user.getUserSex() == 0) {
                            cbSelectSex.setChecked(true);
                        }
                    }

                    if (!TextUtils.isEmpty(user.getHavaJobName())) {//职业身份
                        tvWork.setText(user.getHavaJobName());
                    }

                    if (user.getUserAge() != null) {
                        age.setText(user.getUserAge() + "");//年龄
                    }

                    if (user.getProFund() != null) {
                        if (user.getProFund() == 0) {
                            tvGjj.setText("是");
                        } else {
                            tvGjj.setText("否");
                        }
                    }

                    if (!TextUtils.isEmpty(user.getProvince()) && !TextUtils.isEmpty(user.getCity()) && !TextUtils.isEmpty(user.getDistrict())) {
                        position.setText(addressUtil.getProName(user.getProvince())
                                + addressUtil.getCityName(user.getProvince(), user.getCity())
                                + addressUtil.getCountryName(user.getCity(), user.getDistrict()));//常住地
                    }

                    if (!TextUtils.isEmpty(user.getDetailAddr())) {
                        address.setText(user.getDetailAddr());//详细地址
                    }

                    if (!TextUtils.isEmpty(user.getNativePlace())) {
                        tvJiguan.setText(user.getNativePlace());//籍贯
                    }

                    if (!TextUtils.isEmpty(user.getNativePlaceAddr())) {
                        tvAddress.setText(user.getNativePlaceAddr());//户籍地址
                    }

                    if (user.getMarrySts() != null) {
                        if (user.getMarrySts() == 0)
                            tvMarrayed.setText("未婚");
                        else
                            tvMarrayed.setText("已婚");
                    }

                    if (user.getSecailSecurity() != null) {
                        if (user.getSecailSecurity() == 0)
                            tvSb.setText("否");
                        else
                            tvSb.setText("是");
                    }

                    if (!TextUtils.isEmpty(user.getHaveHoursName())&&!TextUtils.isEmpty(user.getHaveHours())) {//房产类型
                        tvHome.setText(user.getHaveHoursName());
                    }

                    if (!TextUtils.isEmpty(user.getHaveCarName())) {//车产
                        tvCar.setText(user.getHaveCarName());
                    }

                    if (!TextUtils.isEmpty(user.getCompanyName())) {
                        companyName.setText(user.getCompanyName());
                    }

                    if (!TextUtils.isEmpty(user.getBusiness())) {
                        zhiwu.setText(user.getBusiness());
                    }

                    if (!TextUtils.isEmpty(user.getTowYearCredName())) {//信用
                        tvXy.setText(user.getTowYearCredName());
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
                MyToastUtils.showShortToast(getActivity(), msg);
            }
        });
    }
    /**
     * 更新个人资料
     */
    private void upData(){
        if (isHaveData()){
            user.setIdentCard(IdCard.getText().toString());
            user.setDetailAddr(address.getText().toString());
            user.setNativePlace(tvJiguan.getText().toString());
            user.setNativePlaceAddr(tvAddress.getText().toString());
            user.setUserAge(Integer.parseInt(age.getText().toString()));
            if (carSelect){
                user.setHaveCar(carList.get(carPos).getId());//车产
                user.setHaveCarName(carList.get(carPos).getName());
            }else{
                if (!TextUtils.isEmpty(haveCarId)&&!TextUtils.isEmpty(haveCarName)) {
                    user.setHaveCar(haveCarId);
                    user.setHaveCarName(haveCarName);
                }
            }
            if (jobSelect){
                user.setJobId(jobList.get(jobPos).getId());//职业身份
                user.setHavaJobName(jobList.get(jobPos).getName());
            }else{
                if (!TextUtils.isEmpty(jobId)&&!TextUtils.isEmpty(haveJobName)) {
                    user.setJobId(jobId);
                    user.setHavaJobName(haveJobName);
                }
            }
            if (hourseSelect){
                user.setHaveHours(hourseList.get(hoursePos).getId());//房产
                user.setHaveHoursName(hourseList.get(hoursePos).getName());
            }else{
                if (!TextUtils.isEmpty(haveHoursId)&&!TextUtils.isEmpty(haveHoursName)) {
                    user.setHaveHours(haveHoursId);
                    user.setHaveHoursName(haveHoursName);
                }
            }
            if (creditSelect){
                user.setTowYearCred(creditList.get(creditPos).getId());//信用状况
                user.setTowYearCredName(creditList.get(creditPos).getName());
            }else{
                if (!TextUtils.isEmpty(creditId)&&!TextUtils.isEmpty(creditName)) {
                    user.setTowYearCred(creditId);
                    user.setTowYearCredName(creditName);
                }
            }
            user.setUserName(name.getText().toString());
            user.setCompanyName(companyName.getText().toString());
            user.setBusiness(zhiwu.getText().toString());
            RequestManager.getCommManager().updateData(user, new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) throws JSONException {
                    String roleName = SpUtils.getRoleName(context);
                    if (roleName.equals("ROLE_COMMON_CLIENT")) {
                        Intent intent = new Intent(MineFragment.UPDATE_USER);
                        intent.putExtra(MineFragment.USER_KEY, user);
                        getActivity().sendBroadcast(intent);
                        getActivity().sendBroadcast(new Intent(MineFragment.UPDATE_SCORE));
                    } else {
                        Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
                        intent.putExtra(ServiceMineFrg.SERVANT_INFO, user);
                        getActivity().sendBroadcast(intent);
                        getActivity().sendBroadcast(new Intent(ServiceMineFrg.UPDATE_SCORE));
                    }

                    toSubmitOrder();
                }

                @Override
                public void onError(int status, String msg) {
                    secondBtnNext.setClickable(true);
                }
            });
        }
    }
    private boolean isHaveData() {
        if (!FinancialUtil.isInputChinese(name.getText().toString())) {
            MyToastUtils.showShortToast(getActivity(), "真实姓名必须为中文！");
            secondBtnNext.setClickable(true);
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            MyToastUtils.showShortToast(context, "请输入真实姓名");
            secondBtnNext.setClickable(true);
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(IdCard.getText().toString())) {
            MyToastUtils.showShortToast(context, "请输入身份证号");
            secondBtnNext.setClickable(true);
            IdCard.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(position.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择常住地址");
            secondBtnNext.setClickable(true);
            position.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvMarrayed.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择婚姻状况");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvJiguan.getText().toString())) {
            MyToastUtils.showShortToast(context, "请填写籍贯");
            secondBtnNext.setClickable(true);
            tvJiguan.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvAddress.getText().toString())) {
            MyToastUtils.showShortToast(context, "请填写户籍地址");
            secondBtnNext.setClickable(true);
            tvAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvWork.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择职业身份");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(companyName.getText().toString())) {
            MyToastUtils.showShortToast(context, "请填写公司名称");
            secondBtnNext.setClickable(true);
            companyName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(zhiwu.getText().toString())) {
            MyToastUtils.showShortToast(context, "请填写您的职务");
            secondBtnNext.setClickable(true);
            zhiwu.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(age.getText().toString())) {
            MyToastUtils.showShortToast(context, "请填写您的年龄");
            secondBtnNext.setClickable(true);
            age.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSb.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择是否有社保");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvGjj.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择是否有公积金");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvHome.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择房产类型");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvCar.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择车产类型");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (TextUtils.isEmpty(tvXy.getText().toString())) {
            MyToastUtils.showShortToast(context, "请选择您的信用状况");
            secondBtnNext.setClickable(true);
            return false;
        }
        if (!IdcardUtils.validateCard(IdCard.getText().toString())) {
            MyToastUtils.showShortToast(context, "请输入合法的身份证号码");
            secondBtnNext.setClickable(true);
            return false;

        }
        return true;
    }
    private int i = 5;
    private String reOrderId;
    /**
     * 提交订单
     */
    private void toSubmitOrder(){
        OrderBean orderBean = new OrderBean();
        orderBean.setProductId(productInfo.getProductId());
        if (null == HomeCreditDetailAct.creditMoney){
            MyToastUtils.showShortToast(getContext(),"网络不给力，请返回重新提交");
            secondBtnNext.setClickable(true);
        }else {
            orderBean.setTotalAmount(Double.parseDouble(HomeCreditDetailAct.creditMoney) * 10000 + "");//总金额
            orderBean.setTotalPeriods(HomeCreditDetailAct.creditMonth);//总期数
            orderBean.setPeriodsAmount(HomeCreditDetailAct.monthlyPayments);//单期还款金额
            RequestManager.getCommManager().submitOrder(orderBean, new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) {
                    secondBtnNext.setClickable(true);
                    llSucess.setVisibility(View.VISIBLE);
                    criSv.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        reOrderId=jsonObject.getString("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (i>0){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                i--;
                                handler.sendEmptyMessage(i);

                            }
                        }
                    }).start();

                }

                @Override
                public void onError(int status, String msg) {
                    secondBtnNext.setClickable(true);
                    MyToastUtils.showShortToast(context, msg);
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
            if(msg.what>0) {
                tvSecond.setText(msg.what + "");
            }else {
                EventBus.getDefault().post(new CreditStepAct.FirstEvent(2,reOrderId));
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
}