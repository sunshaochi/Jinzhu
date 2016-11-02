package com.beyonditsm.financial.activity.speedcredit;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.entity.RelationEntity;
import com.beyonditsm.financial.entity.UserOrderInfo1;
import com.beyonditsm.financial.fragment.SpeedCreditFrag;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.CoustomDialog;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 极速贷第二步，第一小步
 * Created by Administrator on 2016/10/14 0014.
 */

public class CreditSpeedSecond_1Act extends BaseActivity implements JJTInterface{

    @ViewInject(R.id.tv_speed_top_1)
    private TextView tvSpeedTop_1;//顶部文字
    @ViewInject(R.id.tv_speedName)
    private TextView tvSpeedName;//姓名显示
    @ViewInject(R.id.et_speedName)
    private EditText etSpeedName;//姓名输入
    @ViewInject(R.id.tv_speedIdCard)
    private TextView tvSpeedIdCard;//身份证显示
    @ViewInject(R.id.et_speedIdCard)
    private EditText etSpeedIdCard;//身份证输入
    @ViewInject(R.id.tv_speedPhone)
    private TextView tvSpeedPhone;//电话号码显示
    @ViewInject(R.id.et_speedPhone)
    private EditText etSpeedPhone;//电话号码输入
    @ViewInject(R.id.tv_speedMarriage)
    private TextView tvSpeedMarriage;//婚姻状况显示
    @ViewInject(R.id.tv_speedEducation)
    private TextView tvSpeedEducation;//学历显示
    @ViewInject(R.id.tv_speedPermanent)
    private TextView tvSpeedPermanent;//户籍地址显示
    @ViewInject(R.id.et_speedPermanentDetail)
    private EditText etSpeedPermanentDetail;//户籍地详细地址
    @ViewInject(R.id.tv_speedResident)
    private TextView tvSpeedResident;//常住地显示
    @ViewInject(R.id.et_speedResidentDetail)
    private EditText etSpeedResidentDetail;//常住地详细地址
    @ViewInject(R.id.tv_speedLivingConditions)
    private TextView tvSpeedLivingConditions;//居住状况显示
    @ViewInject(R.id.tv_speedBank)
    private TextView tvSpeedBank;//开户银行显示
    @ViewInject(R.id.tv_speedBankCard)
    private TextView tvSpeedBankCard;//银行卡号显示
    @ViewInject(R.id.tv_speedBankPhone)
    private TextView tvSpeedBankPhone;//银行预留手机号显示
    @ViewInject(R.id.tv_speedChildrenNum)
    private TextView tvSpeedChildrenNum;//子女数目显示
    @ViewInject(R.id.tv_speedPermanentDetail)
    private TextView tvSpeedPermanentDetail;//户籍地详细地址显示
    @ViewInject(R.id.tv_speedResidentDetail)
    private TextView tvSpeedResidentDetail;//常住地详细地址显示
    @ViewInject(R.id.tv_speedSelectLivingConditions)
    private TextView tvSpeedSelectLivingConditions;
    @ViewInject(R.id.tv_speedSelectMarriage)
    private TextView tvSpeedSelectMarriage;
    @ViewInject(R.id.tv_speedSelectChildrenNum)
    private TextView tvSpeedSelectChildrenNum;
    @ViewInject(R.id.tv_speedSelectEdu)
    private TextView tvSpeedSelectEdu;
    @ViewInject(R.id.tv_speedSelectPermanent)
    private TextView tvSpeedSelectPermanent;
    @ViewInject(R.id.tv_speedSelectResident)
    private TextView tvSpeedSelectResident;
    @ViewInject(R.id.tv_speed_toTwo)
    private TextView tvSpeedToTwo;
    private String orderId;
    private List<CreditSpeedEntity.PropertyTypesBean> propetyTypesList;

    private List<RelationEntity> marriageList;
    private List<RelationEntity> eduList;
    private List<String> childNumList;
    private String permanentP;
    private String permanentC;
    private String permanentA;
    private String residentP;
    private String residentC;
    private String residentA;

    private List<JJTProvinceEntity> provinceList;
    private List<JJTCityEntity> cityEntityList;
    private List<JJTCounyEntity> counyEntityList;
    private DialogJJTAddress dialogChooseAdress1;
    private List<String> uuntanal;

    public static final String ORDER_ID ="order_id";

    private final static long WAITTIME = 2000;
    private long touchTime = 0;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= WAITTIME) {
            Toast.makeText(this, "再按一次取消贷款申请", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {

            Intent intent = new Intent(CreditSpeedSecond_1Act.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeedsecond_1);
    }

    @Override
    public void goback(View view) {
//        super.goback(view);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= WAITTIME) {
            Toast.makeText(this, "再按一次取消贷款申请", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {

            Intent intent = new Intent(CreditSpeedSecond_1Act.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("快速判断资质");
        setLeftTv("返回");
        orderId = getIntent().getStringExtra(CreditSpeedDetailAct.SPEED_CREDIT_ORDER_ID);
        propetyTypesList = (List<CreditSpeedEntity.PropertyTypesBean>) getIntent().getSerializableExtra(SpeedCreditFrag.PROPERTY_TYPES);
        initText();
        queryAllProvince();
        getMarriage();
        getEdu();
        childNumList = new ArrayList<>();
        for (int i = 0;i<9;i++){
            childNumList.add(String.valueOf(i));
        }

        etSpeedName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!FinancialUtil.isInputChinese(etSpeedName.getText().toString())) {
                    etSpeedName.setError("真实姓名必须为中文！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*初始化页面字体显示*/
    private void initText() {
        String topTitle = "您的基本信息（<font color='#FF0000'>*</font>为必填项）";
        tvSpeedTop_1.setText(Html.fromHtml(topTitle));
        String speedName = "姓名<font color='#FF0000'>*</font>:";
        tvSpeedName.setText(Html.fromHtml(speedName));
        String speedIdCard = "身份证号<font color='#FF0000'>*</font>:";
        tvSpeedIdCard.setText(Html.fromHtml(speedIdCard));
        String speedPhone = "电话号码<font color='#FF0000'>*</font>:";
        tvSpeedPhone.setText(Html.fromHtml(speedPhone));
        String speedMarriage = "婚姻状况<font color='#FF0000'>*</font>:";
        tvSpeedMarriage.setText(Html.fromHtml(speedMarriage));
        String speedChildrenNum = "子女数目<font color='#FF0000'>*</font>:";
        tvSpeedChildrenNum.setText(Html.fromHtml(speedChildrenNum));
        String speedEdu = "学历<font color='#FF0000'>*</font>:";
        tvSpeedEducation.setText(Html.fromHtml(speedEdu));
        String speedPermanent = "户籍地<font color='#FF0000'>*</font>:";
        tvSpeedPermanent.setText(Html.fromHtml(speedPermanent));
        String speedResident = "常住地<font color='#FF0000'>*</font>:";
        tvSpeedResident.setText(Html.fromHtml(speedResident));
        String speedLiving = "居住状况<font color='#FF0000'>*</font>:";
        tvSpeedLivingConditions.setText(Html.fromHtml(speedLiving));
        String speedBank = "开户银行<font color='#FF0000'>*</font>:";
        tvSpeedBank.setText(Html.fromHtml(speedBank));
        String speedBankCard = "银行卡号<font color='#FF0000'>*</font>:";
        tvSpeedBankCard.setText(Html.fromHtml(speedBankCard));
        String speedBankPhone = "银行预留手机<font color='#FF0000'>*</font>:";
        tvSpeedBankPhone.setText(Html.fromHtml(speedBankPhone));
        String speedPermanentDetail = "详细地址<font color='#FF0000'>*</font>:";
        tvSpeedPermanentDetail.setText(Html.fromHtml(speedPermanentDetail));
        tvSpeedResidentDetail.setText(Html.fromHtml(speedPermanentDetail));

    }

    @OnClick({R.id.tv_speed_toTwo,R.id.rl_speedSelectLiving,R.id.rl_speedSelectMarriage,R.id.rl_speedSelectEdu,R.id.rl_speedSelectPermanent,R.id.rl_speedSelectResident,R.id.rl_speedSelectChildrenNum})
    public void todo(View view){

        switch (view.getId()){
            case R.id.tv_speed_toTwo:
                if (isHaveData()) {
                    UserOrderInfo1 userOrderInfo1 = new UserOrderInfo1();
                    if (!TextUtils.isEmpty(orderId)) {
                        userOrderInfo1.setOrderId(orderId);
                    }
                    userOrderInfo1.setChildNum(tvSpeedSelectChildrenNum.getText().toString().trim());
                    userOrderInfo1.setContactNum(etSpeedPhone.getText().toString().trim());
                    userOrderInfo1.setIdcardno(etSpeedIdCard.getText().toString().trim());
                    userOrderInfo1.setName(etSpeedName.getText().toString().trim());
                    userOrderInfo1.setMarriagests(tvSpeedSelectMarriage.getText().toString().trim());
                    userOrderInfo1.setQualitications(tvSpeedSelectEdu.getText().toString().trim());
                    if (!TextUtils.isEmpty(permanentP)) {
                        userOrderInfo1.setDomicileProvince(permanentP);
                    }
                    if (!TextUtils.isEmpty(permanentC)) {
                        userOrderInfo1.setDomicileCity(permanentC);
                    }
                    if (!TextUtils.isEmpty(permanentA)) {
                        userOrderInfo1.setDomicileArea(permanentA);
                    }
                    userOrderInfo1.setDomicileDetail(etSpeedPermanentDetail.getText().toString().trim());
                    if (!TextUtils.isEmpty(residentP)) {
                        userOrderInfo1.setPermanentProvince(residentP);
                    }
                    if (!TextUtils.isEmpty(residentC)) {
                        userOrderInfo1.setPermanentCity(residentC);
                    }
                    if (!TextUtils.isEmpty(residentA)) {
                        userOrderInfo1.setPermanentArea(residentA);
                    }
                    userOrderInfo1.setPermanentDetail(etSpeedResidentDetail.getText().toString().trim());
                    userOrderInfo1.setResSts(tvSpeedSelectLivingConditions.getText().toString().trim());
                    saveEsseatialInfo(userOrderInfo1);

                }
                break;
            //选择居住状况
            case R.id.rl_speedSelectLiving:
                MySelfSheetDialog mySelfSheetDialog = new MySelfSheetDialog(CreditSpeedSecond_1Act.this).builder();
                if (propetyTypesList.size() != 0) {
                    for (int i = 0; i < propetyTypesList.size(); i++) {
                        mySelfSheetDialog.addSheetItem(propetyTypesList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSpeedSelectLivingConditions.setText(propetyTypesList.get(which - 1).getName());
                            }
                        });
                    }
                }
                mySelfSheetDialog.show();
                break;
            //选择婚姻状况
            case R.id.rl_speedSelectMarriage:
                MySelfSheetDialog mySelfSheetDialogm = new MySelfSheetDialog(CreditSpeedSecond_1Act.this).builder();
                if (marriageList.size() != 0) {
                    for (int i = 0; i < marriageList.size(); i++) {
                        mySelfSheetDialogm.addSheetItem(marriageList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSpeedSelectMarriage.setText(marriageList.get(which - 1).getName());
                            }
                        });
                    }
                }
                mySelfSheetDialogm.show();
                break;
            //选择学历
            case R.id.rl_speedSelectEdu:
                MySelfSheetDialog mySelfSheetDialoge = new MySelfSheetDialog(CreditSpeedSecond_1Act.this).builder();
                if (eduList.size() != 0) {
                    for (int i = 0; i < eduList.size(); i++) {
                        mySelfSheetDialoge.addSheetItem(eduList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSpeedSelectEdu.setText(eduList.get(which - 1).getName());
                            }
                        });
                    }
                }
                mySelfSheetDialoge.show();
                break;

            //选择户籍地城市
            case R.id.rl_speedSelectPermanent:
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(final List<String> adress) {
//                        MyLogUtils.info("选择的地址:" + adress.get(1));

                        permanentP = adress.get(0);
                        permanentC = adress.get(1);
                       permanentA =  adress.get(2);
                        tvSpeedSelectPermanent.setText(permanentP + permanentC + permanentA);
//                        for (int i=0;i<adress.size();i++){
//                            MyLogUtils.info("address"+adress.get(i));
//                        }
                    }
                });
                break;
            //选择常住地城市
            case R.id.rl_speedSelectResident:

                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogJJTAddress.SexClickListener() {
                    @Override
                    public void getAdress(final List<String> adress) {

                        residentP = adress.get(0);
                        residentC = adress.get(1);
                        residentA = adress.get(2);
                        tvSpeedSelectResident.setText(residentP + residentC + residentA);
                    }
                });
                break;
            //选择子女数目
            case R.id.rl_speedSelectChildrenNum:
                MySelfSheetDialog mySelfSheetDialogCN = new MySelfSheetDialog(CreditSpeedSecond_1Act.this).builder();
                if (childNumList.size() != 0) {
                    for (int i = 0; i < childNumList.size(); i++) {
                        mySelfSheetDialogCN.addSheetItem(childNumList.get(i), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSpeedSelectChildrenNum.setText(childNumList.get(which - 1));
                            }
                        });
                    }
                }
                mySelfSheetDialogCN.show();
                break;
        }
    }

    /**
     * 获取婚姻状况
     */
    private void getMarriage(){
        RequestManager.getCommManager().getMarrias(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                marriageList = gson.fromJson(data.toString(), new TypeToken<List<RelationEntity>>() {
                }.getType());
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 获取学历
     */
    private void getEdu(){
        RequestManager.getCommManager().getEducation(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                eduList = gson.fromJson(data.toString(), new TypeToken<List<RelationEntity>>() {
                }.getType());
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 保存基本信息
     */
    private void saveEsseatialInfo(final UserOrderInfo1 userOrderInfo1){
        RequestManager.getCommManager().saveEssentialInfo(userOrderInfo1,new RequestManager.CallBack() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSucess(String result) throws JSONException {

                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.get("data") instanceof JSONArray){
                    JSONArray data = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    uuntanal = gson.fromJson(data.toString(), new TypeToken<List<String>>() {
                    }.getType());
                    CoustomDialog coustomDialog = new CoustomDialog(CreditSpeedSecond_1Act.this, uuntanal);
                    coustomDialog.builder();
                    coustomDialog.setCanceledOnTouchOutside(false);
                    coustomDialog.show();
                    tvSpeedToTwo.setClickable(false);
                    tvSpeedToTwo.setBackground(getResources().getDrawable(R.drawable.button_grey));
                }else{
                    Intent intent = new Intent(CreditSpeedSecond_1Act.this, CreditSpeedSecond_2Act.class);
                    intent.putExtra(ORDER_ID,userOrderInfo1.getOrderId());
                    startActivity(intent);
                }

            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this,msg);
            }
        });
    }

    private boolean isHaveData(){
        if (!FinancialUtil.isInputChinese(etSpeedName.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "真实姓名必须为中文！");
//            secondBtnNext.setClickable(true);
            etSpeedName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etSpeedName.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "请输入真实姓名");
//            secondBtnNext.setClickable(true);
            etSpeedName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etSpeedIdCard.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "请输入身份证号");
//            secondBtnNext.setClickable(true);
            etSpeedIdCard.requestFocus();
            return false;
        }
        if (!IdcardUtils.validateCard(etSpeedIdCard.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "输入的身份证号不合法");
//            secondBtnNext.setClickable(true);
            etSpeedIdCard.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etSpeedPhone.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "请输入手机号码");
//            secondBtnNext.setClickable(true);
            etSpeedPhone.requestFocus();
           return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectMarriage.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择婚姻状况");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectChildrenNum.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择子女数目");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectEdu.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择学历");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectPermanent.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择户籍地");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etSpeedPermanentDetail.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "请输入户籍地详细地址");
//            secondBtnNext.setClickable(true);
            etSpeedPermanentDetail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectResident.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择常住地");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etSpeedResidentDetail.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "请输入常住地详细地址");
//            secondBtnNext.setClickable(true);
            etSpeedResidentDetail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvSpeedSelectLivingConditions.getText().toString())){
            MyToastUtils.showShortToast(CreditSpeedSecond_1Act.this, "尚未选择居住状况");
//            secondBtnNext.setClickable(true);
//            etSpeedPhone.requestFocus();
            return false;
        }
        return true;
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
                    dialogChooseAdress1 = new DialogJJTAddress(CreditSpeedSecond_1Act.this, provinceList).builder();
                    dialogChooseAdress1.getJJTPicker().setOnSrollListener(CreditSpeedSecond_1Act.this);
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
        queryAllCity(jjtProvinceEntity.getId()+"");
    }

    @Override
    public void onCitySelected(JJTCityEntity jjtCityEntity) {
        queryAllArea(jjtCityEntity.getId()+"");
    }

    @Override
    public void onCounySelected(JJTCounyEntity jjtCounyEntity) {

    }
}
