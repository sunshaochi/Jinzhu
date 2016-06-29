package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.DictionaryType;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseAdress;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 领取奖励
 * Created by Administrator on 2016/5/26.
 */
public class ReceiveRewardAct extends BaseActivity {

    @ViewInject(R.id.name)
    private EditText etName;
    @ViewInject(R.id.cb_select_sex)
    private CheckBox cbSelectSex;
    @ViewInject(R.id.et_idCard)
    private EditText etIdCard;
    @ViewInject(R.id.et_age)
    private EditText etAge;
    @ViewInject(R.id.tv_position)
    private TextView tvPosition;
    @ViewInject(R.id.et_address)
    private EditText etAddress;
    @ViewInject(R.id.tv_marrayed)
    private TextView tvMarrayed;//婚姻状况
    @ViewInject(R.id.tv_work)
    private TextView tvWork;//职业身份
    @ViewInject(R.id.tv_sb)
    private TextView tvSb;
    @ViewInject(R.id.tv_gjj)
    private TextView tvGjj;
    @ViewInject(R.id.tv_home)
    private TextView tvHome;
    @ViewInject(R.id.tv_car)
    private TextView tvCar;
    @ViewInject(R.id.tv_xy)
    private TextView tvXy;

    private AddressUtil addressUtil;
    private MySelfSheetDialog dialog;
    private List<DictionaryType> carList, jobList, hourseList, creditList;//车产，职业，房产，信用
    private int jobPos, carPos, hoursePos, creditPos;
    private boolean jobSelect = false;
    private boolean carSelect = false;
    private boolean hourseSelect = false;
    private boolean creditSelect = false;
    private String haveHoursId;
    private String haveCarId;
    private String jobId;
    private String creditId;
    private String haveHoursName;
    private String haveCarName;
    private String haveJobName;
    private String creditName;
    private UserEntity user;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_receivereward);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("领取奖励");
        addressUtil = new AddressUtil(this);
        getUserInfo();
        setListener();
        getDictionaryContent(0, "job_identity");//职业身份
        getDictionaryContent(1, "under_own_car");//名下车产
        getDictionaryContent(2, "under_own_hour");//名下房产
        getDictionaryContent(3, "two_year_credit");//信用状况
    }

    private void setListener() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!FinancialUtil.isInputChinese(etName.getText().toString())) {
                    etName.setError("真实姓名必须为中文！");
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

    @OnClick({R.id.rlPosition,R.id.rl_marrayed,R.id.rl_work,R.id.rl_sb,R.id.rl_gjj,R.id.rl_home,R.id.rl_car,R.id.rl_xy,R.id.btn_receiveReward})
    public void todo(View view){
        switch (view.getId()){
            case R.id.rlPosition://常住地
                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(this).builder();
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
                        tvPosition.setText(addressUtil.getProName(user.getProvince())
                                + addressUtil.getCityName(user.getProvince(), user.getCity())
                                + addressUtil.getCountryName(user.getCity(), user.getDistrict()));
                    }
                });
                break;
            case R.id.rl_marrayed://是否结婚
                dialog = new MySelfSheetDialog(this).builder();
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

            case R.id.rl_sb://是否有本地社保
                dialog = new MySelfSheetDialog(this).builder();
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
            case R.id.rl_gjj://是否有本地公积金
                dialog = new MySelfSheetDialog(this).builder();
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
                dialog = new MySelfSheetDialog(this).builder();
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
                dialog = new MySelfSheetDialog(this).builder();
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
                MyLogUtils.info("点击了车产类型");
                //无车 、名下有车 、有车，但车已被抵押 、无车，准备购买
                dialog = new MySelfSheetDialog(this).builder();

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
                dialog = new MySelfSheetDialog(this).builder();
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
            case R.id.btn_receiveReward://领取奖励
                upData();
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
                MyToastUtils.showShortToast(getApplicationContext(),msg);
            }
        });
    }
    private void getUserInfo(){
        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

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
                        etName.setText(user.getUserName());
                        etName.setSelection(user.getUserName().length());
                    }

                    if (!TextUtils.isEmpty(user.getIdentCard())) {
                        etIdCard.setText(user.getIdentCard());//身份证
                        etIdCard.setSelection(user.getIdentCard().length());
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
                        etAge.setText(user.getUserAge() + "");//年龄
                    }

                    if (user.getProFund() != null) {
                        if (user.getProFund() == 0) {
                            tvGjj.setText("是");
                        } else {
                            tvGjj.setText("否");
                        }
                    }

                    if (!TextUtils.isEmpty(user.getProvince()) && !TextUtils.isEmpty(user.getCity()) && !TextUtils.isEmpty(user.getDistrict())) {
                        tvPosition.setText(addressUtil.getProName(user.getProvince())
                                + addressUtil.getCityName(user.getProvince(), user.getCity())
                                + addressUtil.getCountryName(user.getCity(), user.getDistrict()));//常住地
                    }

                    if (!TextUtils.isEmpty(user.getDetailAddr())) {
                        etAddress.setText(user.getDetailAddr());//详细地址
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

                    if (!TextUtils.isEmpty(user.getHaveHoursName()) && !TextUtils.isEmpty(user.getHaveHours())) {//房产类型
                        tvHome.setText(user.getHaveHoursName());
                    }

                    if (!TextUtils.isEmpty(user.getHaveCarName())) {//车产
                        tvCar.setText(user.getHaveCarName());
                    }

                    if (!TextUtils.isEmpty(user.getNativePlaceAddr())) {
                    }

                    if (!TextUtils.isEmpty(user.getTowYearCredName())) {//信用
                        tvXy.setText(user.getTowYearCredName());
                    }
                }
//                getReceiveReward(user);
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    private boolean isHaveData() {
        if (!FinancialUtil.isInputChinese(etName.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "真实姓名必须为中文！");
            etName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etName.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入真实姓名");
            etName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etIdCard.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入身份证号");
            etIdCard.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvPosition.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择常住地址");
            tvPosition.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvMarrayed.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择婚姻状况");
            return false;
        }
//        if (TextUtils.isEmpty(tvJiguan.getText().toString())) {
//            MyToastUtils.showShortToast(context, "请填写籍贯");
//            tvJiguan.requestFocus();
//            return false;
//        }
        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请填写户籍地址");
            etAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvWork.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择职业身份");
            return false;
        }
//        if (TextUtils.isEmpty(companyName.getText().toString())) {
//            MyToastUtils.showShortToast(context, "请填写公司名称");
//            companyName.requestFocus();
//            return false;
//        }
//        if (TextUtils.isEmpty(zhiwu.getText().toString())) {
//            MyToastUtils.showShortToast(context, "请填写您的职务");
//            zhiwu.requestFocus();
//            return false;
//        }
        if (TextUtils.isEmpty(etAge.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请填写您的年龄");
            etAge.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSb.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择是否有社保");
            return false;
        }
        if (TextUtils.isEmpty(tvGjj.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择是否有公积金");
            return false;
        }
        if (TextUtils.isEmpty(tvHome.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择房产类型");
            return false;
        }
        if (TextUtils.isEmpty(tvCar.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择车产类型");
            return false;
        }
        if (TextUtils.isEmpty(tvXy.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请选择您的信用状况");
            return false;
        }
        if (!IdcardUtils.validateCard(etIdCard.getText().toString())) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入合法的身份证号码");
            return false;
        }
        return true;
    }
    //获取奖励（修改资料）
    private void getReceiveReward(UserEntity userEntity){
        RequestManager.getCommManager().getReceiveReward(userEntity,new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(),"恭喜您已成功领取奖励，请至我的钱包查收");
                ParamsUtil.getInstance().setWalletEnter(true);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(),"已领取过奖励，用户信息修改成功");
                finish();
            }
        });
    }

    /**
     * 更新个人资料
     */
    private void upData(){
        if (isHaveData()){
            user.setIdentCard(etIdCard.getText().toString());
            user.setDetailAddr(etAddress.getText().toString());
//            user.setNativePlace(tvJiguan.getText().toString());
//            user.setNativePlaceAddr(e.getText().toString());
            user.setUserAge(Integer.parseInt(etAge.getText().toString()));
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
//                user.setJobName(jobList.get(jobPos).getId());
                user.setHavaJobName(jobList.get(jobPos).getName());
            }else{
                if (!TextUtils.isEmpty(jobId)&&!TextUtils.isEmpty(haveJobName)) {
                    user.setJobId(jobId);
//                    user.setJobName(jobId);
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
            user.setUserName(etName.getText().toString());
//            user.setCompanyName(companyName.getText().toString());
//            user.setBusiness(zhiwu.getText().toString());
            getReceiveReward(user);
//            RequestManager.getCommManager().updateData(user, new RequestManager.CallBack() {
//                @Override
//                public void onSucess(String result) throws JSONException {
//                    String roleName = SpUtils.getRoleName(getApplicationContext());
//                    if (roleName.equals("ROLE_COMMON_CLIENT")) {
//                        Intent intent = new Intent(MineFragment.UPDATE_USER);
//                        intent.putExtra(MineFragment.USER_KEY, user);
//                        sendBroadcast(intent);
//                        sendBroadcast(new Intent(MineFragment.UPDATE_SCORE));
//                    } else {
//                        Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
//                        intent.putExtra(ServiceMineFrg.SERVANT_INFO, user);
//                        sendBroadcast(intent);
//                        sendBroadcast(new Intent(ServiceMineFrg.UPDATE_SCORE));
//                    }
//
//
//                }
//
//                @Override
//                public void onError(int status, String msg) {
//
//                }
//            });
        }
    }
}
