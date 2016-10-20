package com.beyonditsm.financial.activity.speedcredit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 极速贷第二步 第二小步
 * Created by Administrator on 2016/10/14 0014.
 */

public class CreditSpeedSecond_2Act extends BaseActivity implements JJTInterface {

    @ViewInject(R.id.tv_speed_top_2)
    private TextView tvSpeedTop_2;
    @ViewInject(R.id.tv_speedCompanyName)
    private TextView tvSpeedCompanyName;
    @ViewInject(R.id.tv_speedCompanyPhone)
    private TextView tvSpeedCompanyPhone;
    @ViewInject(R.id.tv_speedCompanyAddress)
    private TextView tvSpeedCompantAddress;
    @ViewInject(R.id.tv_speedDepartment)
    private TextView tvSpeedDepartment;
    @ViewInject(R.id.tv_speedCompanyType)
    private TextView tvSpeedCompanyType;
    @ViewInject(R.id.tv_speedWorkType)
    private TextView tvSpeedWorkType;
    @ViewInject(R.id.tv_speedSalary)
    private TextView tvSpeedSalary;
    @ViewInject(R.id.tv_speedSalaryType)
    private TextView tvSpeedSalaryType;
    @ViewInject(R.id.tv_speedSalaryDay)
    private TextView tvSpeedSalaryDay;
    @ViewInject(R.id.et_speedCompanyAddressDetail)
    private EditText etSpeedCompanyAddressDetail;
    @ViewInject(R.id.tv_speedCompanyAddressDetail)
    private TextView tvSpeedCompanyAddressDetail;
    private List<JJTProvinceEntity> provinceList;
    private List<JJTCityEntity> cityEntityList;
    private List<JJTCounyEntity> counyEntityList;
    private DialogJJTAddress dialogChooseAdress1;
    private String orderId;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeedsecond_2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(CreditSpeedSecond_1Act.ORDER_ID);
        queryUnitProperty();
        queryWorkingProperty();
        querySalary();
        queryAllProvince();
        initText();
    }

    /*初始化页面字体显示*/
    private void initText() {
        String topTitle = "您的资质（<font color='#FF0000'> *</font>为必填项）";
        tvSpeedTop_2.setText(Html.fromHtml(topTitle));
        String speedName = "单位名称<font color='#FF0000'> *</font>:";
        tvSpeedCompanyName.setText(Html.fromHtml(speedName));
        String speedIdCard = "单位电话<font color='#FF0000'> *</font>:";
        tvSpeedCompanyPhone.setText(Html.fromHtml(speedIdCard));
        String speedPhone = "单位地址<font color='#FF0000'> *</font>:";
        tvSpeedCompantAddress.setText(Html.fromHtml(speedPhone));
        String speedMarriage = "部门<font color='#FF0000'> *</font>:";
        tvSpeedDepartment.setText(Html.fromHtml(speedMarriage));
        String speedEdu = "单位性质<font color='#FF0000'> *</font>:";
        tvSpeedCompanyType.setText(Html.fromHtml(speedEdu));
        String speedPermanent = "工作性质<font color='#FF0000'> *</font>:";
        tvSpeedWorkType.setText(Html.fromHtml(speedPermanent));
        String speedResident = "基本月薪<font color='#FF0000'> *</font>:";
        tvSpeedSalary.setText(Html.fromHtml(speedResident));
        String speedLiving = "发放形式<font color='#FF0000'>*</font>:";
        tvSpeedSalaryType.setText(Html.fromHtml(speedLiving));
        String speedBank = "月发薪日<font color='#FF0000'> *</font>:";
        tvSpeedSalaryDay.setText(Html.fromHtml(speedBank));
        String speedPermanentDetail = "详细地址<font color='#FF0000'> *</font>";
        tvSpeedCompanyAddressDetail.setHint(Html.fromHtml(speedPermanentDetail));

    }

    @OnClick({R.id.tv_speed_toThere, R.id.rl_speedCompanyAddress})
    public void todo(View view) {
        switch (view.getId()) {
            case R.id.tv_speed_toThere:
                Intent intent = new Intent(this, CreditSpeedSecond_3Act.class);
                startActivity(intent);
//                Intent intent = new Intent(CreditSpeedSecondFrag.NEXT);
//                intent.putExtra("item",2);
//                getActivity().sendBroadcast(intent);
                break;
            case R.id.rl_speedCompanyAddress:

                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(final List<String> adress) {
//                        MyLogUtils.info("选择的地址:" + adress.get(1));

                        for (int i=0;i<adress.size();i++){
                            MyLogUtils.info("address"+adress.get(i));
                        }
                    }
                });
                break;
        }
    }

    /*查询工作性质*/
    private void queryWorkingProperty() {
        CommManager.getCommManager().queryWorkingProperty(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*查询单位性质*/
    private void queryUnitProperty() {
        CommManager.getCommManager().queryUnitProperty(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
//                Gson gson
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*查询月薪发放形式*/
    private void querySalary() {
        CommManager.getCommManager().querySalary(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*省查询*/
    private void queryAllProvince() {
        CommManager.getCommManager().queryAllProvince(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray res = data.getJSONArray("result");
                Gson gson = new Gson();
                provinceList = gson.fromJson(res.toString(), new TypeToken<List<JJTProvinceEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setProvinceEntityList(provinceList);

                if (provinceList != null && provinceList.size() > 0) {
                    dialogChooseAdress1 = new DialogJJTAddress(CreditSpeedSecond_2Act.this, provinceList).builder();
                    dialogChooseAdress1.getJJTPicker().setOnSrollListener(CreditSpeedSecond_2Act.this);
                    queryAllCity(provinceList.get(0).getId() + "");
                }

            }


            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*市查询*/
    private void queryAllCity(String parentId) {
        CommManager.getCommManager().queryAllCity(parentId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray res = data.getJSONArray("result");
                Gson gson = new Gson();
                cityEntityList = gson.fromJson(res.toString(), new TypeToken<List<JJTCityEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCityEntityList(cityEntityList);
                dialogChooseAdress1.getJJTPicker().setCityList();
                if (cityEntityList != null && cityEntityList.size() > 0) {
                    queryAllArea(cityEntityList.get(0).getId() + "");
                }


            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /*区查询*/
    private void queryAllArea(String parentId) {
        CommManager.getCommManager().queryAllArea(parentId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray res = data.getJSONArray("result");
                Gson gson = new Gson();
                counyEntityList = gson.fromJson(res.toString(), new TypeToken<List<JJTCounyEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCounyEntityList(counyEntityList);
                dialogChooseAdress1.getJJTPicker().setCouny();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @Override
    public void onProvinceSelected(JJTProvinceEntity jjtProvinceEntity) {
        queryAllCity(jjtProvinceEntity.getId() + "");

    }

    @Override
    public void onCitySelected(JJTCityEntity jjtCityEntity) {
        queryAllArea(jjtCityEntity.getId() + "");
    }
}
