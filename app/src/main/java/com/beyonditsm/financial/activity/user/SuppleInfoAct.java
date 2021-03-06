package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AdressBean;
import com.beyonditsm.financial.entity.ProfileInfoBean;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserLoginBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogChooseAdress;
import com.beyonditsm.financial.widget.DialogChooseProvince;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.List;

/**
 * Created by gxy on 2015/11/26
 */
public class SuppleInfoAct extends BaseActivity {
    @ViewInject(R.id.name)
    private EditText name;//姓名
    @ViewInject(R.id.tv_name)
    private TextView tvname;

    @ViewInject(R.id.cb_select_sex)
    private CheckBox cbSelectSex;//选择性别

    @ViewInject(R.id.age)
    private EditText age;//年龄
    @ViewInject(R.id.tv_age)
    private TextView tvage;
    @ViewInject(R.id.IdCard)
    private EditText IdCard;//身份证
    @ViewInject(R.id.tv_IdCard)
    private TextView tvIdCard;
    @ViewInject(R.id.rlcaddress)
    private RelativeLayout rlcaddress;//选择常住地
    @ViewInject(R.id.tv_position)
    private TextView position;//常住地
    @ViewInject(R.id.tvposition)
    private TextView tvposition;//显示常住地
    @ViewInject(R.id.address)
    private EditText address;//详细地址
    @ViewInject(R.id.tvaddress)
    private TextView tvaddress;
    @ViewInject(R.id.rl_marrayed)
    private RelativeLayout rlMarrayed;//是否结婚点击框
    @ViewInject(R.id.tv_marrayed)
    private TextView tvMarrayed;//是否结婚文本
    @ViewInject(R.id.tvmarryed)
    private TextView tvmarryed;
    @ViewInject(R.id.rljiguan)
    private RelativeLayout rljiguan;//选择籍贯
    @ViewInject(R.id.tv_jiguan)
    private TextView tvJiguan;//籍贯
    @ViewInject(R.id.tvjiguan)
    private TextView tvjiguan;
    @ViewInject(R.id.rladdress)
    private RelativeLayout rladdress;//选择户籍地址
    @ViewInject(R.id.tv_address)
    private TextView tvAddress;//户籍地址
    @ViewInject(R.id.tvhuji)
    private TextView tvhuji;
    @ViewInject(R.id.btn_submit)
    private Button btn_submit;//提交
    private UserLoginBean userLoginBean;
    private ProfileInfoBean userinfo;
    private AddressUtil addressUtil;
    private AdressBean adressBean;
    @Override
    public void setLayout() {
        setContentView(R.layout.supple_info);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("补充资料");
        setLeftTv("返回");
    //    tbSex.setIsSwitch(true);
        addressUtil = new AddressUtil(this);
        userLoginBean=getIntent().getParcelableExtra(MineFragment.USER_KEY);
        if (userLoginBean!=null){
           userinfo= userLoginBean.getProfileInfo();
           adressBean= userLoginBean.getAddress();
        }
        if(userinfo!=null){
            setUserMe(userinfo,adressBean);
            if((!TextUtils.isEmpty(userinfo.getName()))&&(userinfo.getSex()!=null)&&
                    (userinfo.getAge()!=null)&&(!TextUtils.isEmpty(userinfo.getIdNo()))&&
                    (!TextUtils.isEmpty(adressBean.getAddress()))&&
                    (!TextUtils.isEmpty(userinfo.getNativePlace()))&&(!TextUtils.isEmpty(userinfo.getHomeAddress()))
                    &&(!TextUtils.isEmpty(adressBean.getProvince()))&&(!TextUtils.isEmpty(adressBean.getCity()))&&(!TextUtils.isEmpty(adressBean.getDistrict()))){
                btn_submit.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"暂无可补充的资料",Toast.LENGTH_SHORT).show();
            }
        }else {
            getUserInfo();
        }



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProfileInfoBean userEntity = setUser();

                RequestManager.getCommManager().updateData(userEntity, adressBean,SpUtils.getPhonenumber(getApplicationContext()),new RequestManager.CallBack() {
                    @Override
                    public void onSucess(String result) throws JSONException {
                        finish();
                        Intent intent = new Intent(MineFragment.UPDATE_USER);
                        intent.putExtra(MineFragment.USER_KEY, userEntity);
                        sendBroadcast(intent);
                    }

                    @Override
                    public void onError(int status,String msg) {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        cbSelectSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userinfo.setSex(0+"");
                    MyLogUtils.info("选择了女");
                } else {
                    userinfo.setSex(1+"");
                    MyLogUtils.info("选择了男");
                }
            }
        });
    }


    @OnClick({R.id.rlcaddress,R.id.rl_marrayed,R.id.rljiguan,R.id.rladdress})
    public void todo(View v){
        switch (v.getId()){
            case R.id.rlcaddress:
                DialogChooseAdress dialogChooseAdress = new DialogChooseAdress(this).builder();
                dialogChooseAdress.show();
                dialogChooseAdress.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void getAdress(List<String> adress) {
                        String proCode=addressUtil.getProCode(adress.get(0));
                        String cityCode=addressUtil.getCityCode(proCode, adress.get(1));
                        String districtCode=addressUtil.getCountryCode(cityCode,adress.get(2));
                        adressBean.setProvince(adress.get(0));
                        adressBean.setCity(adress.get(1));
                        adressBean.setDistrict(adress.get(2));
//                        userinfo.setCity(cityCode);
//                        userinfo.setDistrict(districtCode);
//                        tvposition.setText(addressUtil.getProName(userinfo.getProvince())+addressUtil.getCityName(userinfo.getProvince(), userinfo.getCity())+addressUtil.getCountryName(userinfo.getCity(), userinfo.getDistrict()));
                        tvposition.setText(adress.get(0)+adress.get(1)+adress.get(2));

                    }
                });
                break;
            case R.id.rl_marrayed://是否结婚
                MySelfSheetDialog dialog = new MySelfSheetDialog(SuppleInfoAct.this).builder();
                dialog.addSheetItem("已婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvmarryed.setText("已婚");
                    }
                });
                dialog.addSheetItem("未婚", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvmarryed.setText("未婚");
                    }
                });
                dialog.show();
                break;
            case R.id.rljiguan:
                DialogChooseProvince dialogChooseProvince = new DialogChooseProvince(this).builder();
                dialogChooseProvince.show();
                dialogChooseProvince.setOnSheetItemClickListener(new DialogChooseProvince.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvjiguan.setText(adress);
                    }
                });
                break;
            case R.id.rladdress:
                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void getAdress(List<String> adress) {
                        tvhuji.setText(adress.get(0)+adress.get(1)+adress.get(2));
                    }
                });

                break;
        }
    }
    @SuppressLint("SetTextI18n")
    private void setUserMe(ProfileInfoBean userinfo,AdressBean adressBean){
        if(!TextUtils.isEmpty(userinfo.getName())){
            name.setVisibility(View.INVISIBLE);
            tvname.setVisibility(View.VISIBLE);
            tvname.setText(userinfo.getName());
        }else {
            name.setVisibility(View.VISIBLE);
            tvname.setVisibility(View.INVISIBLE);
        }
        if(userinfo.getSex()!=null){
            if (TextUtils.equals(userinfo.getSex(),0+"")) {//女
//                tbSex.setIsSwitch(false);
                cbSelectSex.setChecked(true);
//                sexWoman.setChecked(true);
//                sexMan.setClickable(false);
            } else if(TextUtils.equals(userinfo.getSex(),1+"")){
//                tbSex.setIsSwitch(true);
                cbSelectSex.setChecked(false);
//                sexMan.setChecked(true);
//                sexWoman.setClickable(false);
            }
//            tbSex.setClickable(false);

        }

        if(userinfo.getAge()!=null){
            age.setVisibility(View.INVISIBLE);
            tvage.setVisibility(View.VISIBLE);
            tvage.setText(userinfo.getAge()+"");
        }else {
            age.setVisibility(View.VISIBLE);
            tvage.setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(userinfo.getIdNo())){
            IdCard.setVisibility(View.INVISIBLE);
            tvIdCard.setVisibility(View.VISIBLE);
            tvIdCard.setText(userinfo.getIdNo());
        }else {
            IdCard.setVisibility(View.VISIBLE);
            tvIdCard.setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(adressBean.getProvince())&&!TextUtils.isEmpty(adressBean.getCity())&&!TextUtils.isEmpty(adressBean.getDistrict())){
            tvposition.setVisibility(View.VISIBLE);
            tvposition.setText(adressBean.getProvince()+adressBean.getCity()+adressBean.getCity()+adressBean.getDistrict());
//            tvposition.setText(userinfo.getProvince()+userinfo.getCity()+userinfo.getDistrict());
            position.setVisibility(View.INVISIBLE);
            position.setClickable(false);
            rlcaddress.setClickable(false);
        }else {
            tvposition.setVisibility(View.VISIBLE);
            position.setVisibility(View.VISIBLE);
            position.setClickable(true);
            tvposition.setClickable(true);
            rlcaddress.setClickable(true);
        }
        if(!TextUtils.isEmpty(adressBean.getAddress())){
            tvaddress.setVisibility(View.VISIBLE);
            tvaddress.setText(adressBean.getAddress());
            address.setVisibility(View.INVISIBLE);
        }else {
            address.setVisibility(View.VISIBLE);
            tvaddress.setVisibility(View.INVISIBLE);
        }
        if(userinfo.isMarried()){
            tvmarryed.setVisibility(View.VISIBLE);
            if(userinfo.isMarried()){
                tvmarryed.setText("未婚");
            }else {
                tvmarryed.setText("已婚");
            }
            tvMarrayed.setVisibility(View.INVISIBLE);
            tvMarrayed.setClickable(false);
            rlMarrayed.setClickable(false);
        }else {
            tvMarrayed.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(userinfo.getNativePlace())){
            tvjiguan.setVisibility(View.VISIBLE);
            tvjiguan.setText(userinfo.getNativePlace());
            tvJiguan.setVisibility(View.INVISIBLE);
            tvJiguan.setClickable(false);
            rljiguan.setClickable(false);
        }else {
            tvJiguan.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(userinfo.getNativePlace())){
            tvhuji.setVisibility(View.VISIBLE);
            tvhuji.setText(userinfo.getNativePlace());
            tvAddress.setVisibility(View.INVISIBLE);
            tvAddress.setClickable(false);
            rladdress.setClickable(false);
        }else {
            tvAddress.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestManager.getCommManager().findUserInfo( SpUtils.getPhonenumber(getApplicationContext()),new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) {
                ResultData<UserLoginBean> rd = (ResultData<UserLoginBean>) GsonUtils.json(result, UserLoginBean.class);
                userLoginBean = rd.getData();
                if (userLoginBean != null) {
                    Intent intent = new Intent(MineFragment.UPDATE_USER);
                    intent.putExtra(MineFragment.USER_KEY, userLoginBean);
                    sendBroadcast(intent);
                    if (userLoginBean.getProfileInfo()!=null){
                        userinfo=userLoginBean.getProfileInfo();
                        adressBean=userLoginBean.getAddress();
                        setUserMe(userinfo,adressBean);
                    }
                }
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(SuppleInfoAct.this, msg);
            }
        });
    }
    private ProfileInfoBean setUser(){
        if(!TextUtils.isEmpty(userinfo.getName())){
            userinfo.setName(userinfo.getName());
        }else {
            userinfo.setName(name.getText().toString());
        }
        if(userinfo.getSex()!=null){
            userinfo.setSex(userinfo.getSex());
        }

        if(userinfo.getAge()!=null){
            userinfo.setAge(userinfo.getAge());
        }else {
            if(!TextUtils.isEmpty(age.getText().toString())){
                userinfo.setAge(age.getText().toString());
            }
        }
        if(!TextUtils.isEmpty(userinfo.getIdNo())){
            userinfo.setIdNo(userinfo.getIdNo());
        }else {
            if(!TextUtils.isEmpty(IdCard.getText().toString())){
                if (!IdcardUtils.validateCard(IdCard.getText().toString())) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入合法的身份证号码");
                }else {
                    userinfo.setIdNo(IdCard.getText().toString());
                }
            }
        }
//        if(!TextUtils.isEmpty(adressBean.getProvince())&&!TextUtils.isEmpty(adressBean.getCity())&&!TextUtils.isEmpty(userinfo.getDistrict())){
//            userinfo.set(addressUtil.getProName(userinfo.getProvince())+addressUtil.getCityName(userinfo.getProvince(), userinfo.getCity())+addressUtil.getCountryName(userinfo.getCity(), userinfo.getDistrict()));
//        }
//        else {
//            if(!TextUtils.isEmpty(tvposition.getText().toString())){
//                userinfo.setCurrAddress(tvposition.getText().toString());
//            }
//        }
//        if(!TextUtils.isEmpty(userinfo.getDetailAddr())){
//            userinfo.setDetailAddr(userinfo.getDetailAddr());
//        }else {
//            if (!TextUtils.isEmpty(address.getText().toString())){
//                userinfo.setDetailAddr(address.getText().toString());
//            }
//        }
//        if (userinfo.getMarrySts() != null) {
//            userinfo.setMarried(userinfo.isMarried());
//        }else {
            if("未婚".equals(tvmarryed.getText().toString())){
                userinfo.setMarried(false);
            }else if("已婚".equals(tvmarryed.getText().toString())){
                userinfo.setMarried(true);
            }
//        }
        if(!TextUtils.isEmpty(userinfo.getNativePlace())) {
            userinfo.setNativePlace(userinfo.getNativePlace());
        }else {
            if (!TextUtils.isEmpty(tvjiguan.getText().toString())){
                userinfo.setNativePlace(tvjiguan.getText().toString());
            }
        }
        if(!TextUtils.isEmpty(userinfo.getNativePlace())) {
            userinfo.setNativePlace(userinfo.getNativePlace());
        }else {
            if (!TextUtils.isEmpty(tvhuji.getText().toString())){
                userinfo.setNativePlace(tvhuji.getText().toString());
            }
        }
        return userinfo;
    }
}
