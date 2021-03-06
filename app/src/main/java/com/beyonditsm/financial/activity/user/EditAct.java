package com.beyonditsm.financial.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.View;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.AdressBean;
import com.beyonditsm.financial.entity.ProfileInfoBean;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.entity.UserLoginBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

/**
 * 修改各种信息
 * Created by wangbin on 15/11/13.
 */
public class EditAct extends BaseActivity {
    @ViewInject(R.id.etM)
    private ClearEditText etM;

    private UserLoginBean userInfo2 = new UserLoginBean();
    private ProfileInfoBean userInfo;
    private AdressBean adressBean;
    private int TYPE;//0真实姓名 1身份证号，2/籍贯 3户籍地址 4、收支银行 5、收支支行 6、银行账号 7、邮箱
    public static String USER_TYPE = "type";

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        userInfo2 = getIntent().getParcelableExtra(MineFragment.USER_KEY);
//        userInfo=new ProfileInfoBean();
        if (userInfo2 != null) {
            userInfo = userInfo2.getProfileInfo();
            adressBean = userInfo2.getAddress();
        } else {
            userInfo = new ProfileInfoBean();
        }
        TYPE = getIntent().getIntExtra(USER_TYPE, 0);
        setTopT(TYPE);
        if (!TextUtils.isEmpty(etM.getText().toString().trim())) {
            etM.setSelection(etM.getText().toString().length());
        }
        setRightBtn("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etM.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请先完成输入");
                    return;
                }


                switch (TYPE) {
                    case 0://真实姓名
                        if (!FinancialUtil.isInputChinese(content)) {
                            MyToastUtils.showShortToast(getApplicationContext(), "真实姓名必须为中文！");
                            return;
                        } else {
                            userInfo.setName(content);
                        }

                        break;
                    case 1://身份证号
                        if (!IdcardUtils.validateCard(content)) {
                            MyToastUtils.showShortToast(getApplicationContext(), "请输入合法的身份证号");
                            return;
                        }
                        if (!TextUtils.isEmpty(content)) {
                            userInfo.setIdNo(content);
                        }
                        if (userInfo.getAge() == null)
                            userInfo.setAge(IdcardUtils.getAgeByIdCard(content) + "");

                        if (userInfo.getSex() == null) {
                            if ("M".equals(IdcardUtils.getGenderByIdCard(content))) {
                                userInfo.setSex(1 + "");
                            } else if ("F".equals(IdcardUtils.getGenderByIdCard(content))) {
                                userInfo.setSex(0 + "");
                            }
                        }
                        if (TextUtils.isEmpty(userInfo.getNativePlace())) {
                            userInfo.setNativePlace(IdcardUtils.getProvinceByIdCard(content));
                        }

                        break;
                    case 8:
                        if (!TextUtils.isEmpty(content)) {
//                            if (userInfo==null){
//                                userInfo=new ProfileInfoBean();
//                                userInfo.setAge(content + "");
//                            }else {
                            userInfo.setAge(content);
//                            }
                        }
                        break;
                }
                updateData(userInfo, adressBean);

            }
        });
    }


    /**
     * 设置顶部标题
     *
     * @param position position
     */
    @SuppressLint("SetTextI18n")
    private void setTopT(int position) {

        switch (position) {
            case 0:
                setTopTitle("真实姓名");
                etM.setHint("请输入真实姓名");
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getName())) {
                    etM.setText(userInfo.getName());
                }
                etM.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!FinancialUtil.isInputChinese(etM.getText().toString())) {
                            etM.setError("真实姓名必须为中文！");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                break;
            case 1:
                setTopTitle("身份证号");
                etM.setHint("请输入身份证号");
                etM.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
//                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                etM.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'X'};
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_CLASS_NUMBER;
                    }
                });
//                etM.setKeyListener(new DigitsKeyListener(false,true));
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getIdNo())) {
                    etM.setText(userInfo.getIdNo());
                }
                break;
            case 2:
                setTopTitle("籍贯");
                break;
            case 3:
                setTopTitle("户籍地址");
                etM.setHint("请输入户籍地址");
                break;
            case 4:
                setTopTitle("收支银行");
                etM.setText("请输入收支银行");
                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 5:
                setTopTitle("收支支行");
                etM.setHint("请输入收支支行");
                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 6:
                setTopTitle("银行账号");
                etM.setHint("请输入银行账号");
                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 7:
                setTopTitle("邮箱");
                etM.setHint("请输入邮箱");
                etM.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 8:
                setTopTitle("年龄");
                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                etM.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etM.setHint("请输入年龄");
                if (userInfo != null && userInfo.getAge() != null) {
                    etM.setText(userInfo.getAge() + "");
                }
                break;
        }
    }

    /**
     * 更新资料
     *
     * @param ue 用户实体类
     */
    private void updateData(final ProfileInfoBean ue, final AdressBean adressBean) {
//        ue.setProfileId(SpUtils.getProfileid(EditAct.this));
//        adressBean.setAddressId(SpUtils.getAddressid(EditAct.this));
        RequestManager.getCommManager().updateData(ue, adressBean, SpUtils.getPhonenumber(getApplicationContext()), new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                userInfo2.setProfileInfo(ue);
                userInfo2.setAddress(adressBean);
                if (!TextUtils.isEmpty(ue.getName())){
                    SpUtils.setUsername(getApplicationContext(),ue.getName());
                }
                EventBus.getDefault().post(new UserEvent(ue, TYPE));
                Intent intent = new Intent(MineFragment.USER_KEY);
                intent.putExtra(MineFragment.USER_KEY, userInfo2);
                sendBroadcast(intent);
                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }
}
