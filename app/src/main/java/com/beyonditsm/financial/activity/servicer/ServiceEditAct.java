package com.beyonditsm.financial.activity.servicer;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;

/**
 * Created by Administrator on 2015/11/29.
 */
public class ServiceEditAct extends BaseActivity {
    @ViewInject(R.id.etM)
    private ClearEditText etM;

    private int TYPE;//0真实姓名 1身份证号，2/籍贯 3户籍地址 4、收支银行 5、收支支行 6、银行账号 7、邮箱
    public static String USER_TYPE = "type";
//    private ServantEntity servantInfo;
    private UserEntity userInfo;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        userInfo = getIntent().getParcelableExtra(ServiceMineFrg.SERVANT_INFO);
//        userInfo = getIntent().getParcelableExtra(ServiceMineFrg.USER_INFO);
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
                    MyToastUtils.showShortToast(getApplicationContext(), "请填写后点击提交");
                    return;
                }

                switch (TYPE) {
                    case 0://真实姓名
                        userInfo.setUserName(content);
                        updateUserData(userInfo);
                        break;
                    case 1://身份证号
                        if (!IdcardUtils.validateCard(content)) {
                            MyToastUtils.showShortToast(getApplicationContext(), "请输入合法的身份证号");
                            return;
                        }
                        userInfo.setIdentCard(content);
                        if (userInfo.getUserAge() == null)
                            userInfo.setUserAge(IdcardUtils.getAgeByIdCard(content));

                        if (userInfo.getUserSex() == null) {
                            if ("M".equals(IdcardUtils.getGenderByIdCard(content))) {
                                userInfo.setUserSex(1);
                            } else if ("F".equals(IdcardUtils.getGenderByIdCard(content))) {
                                userInfo.setUserSex(0);
                            }
                        }
                        if (TextUtils.isEmpty(userInfo.getNativePlace()))
                            userInfo.setNativePlace(IdcardUtils.getProvinceByIdCard(content));
                        updateUserData(userInfo);
                        break;

                    case 7://邮箱
                        userInfo.setEmail(content);
                        updateUserData(userInfo);
                        break;
                    case 8://年龄
                        userInfo.setUserAge(Integer.valueOf(content));
                        updateUserData(userInfo);
                        break;
                    case 4://收支银行
                        userInfo.setBankNameTitle(content);
                        updateData(userInfo);
                        break;
                    case 5://收支支行
                        userInfo.setBankName(content);
                        updateData(userInfo);
                        break;
                    case 6://银行账号
                        userInfo.setBankAccNo(content);
                        updateData(userInfo);
                        break;
                }
            }
        });
    }

    /**
     * 设置顶部标题
     *
     * @param position
     */
    private void setTopT(int position) {

        switch (position) {
            case 0:
                setTopTitle("真实姓名");
                etM.setHint("请输入真实姓名");
                if (!TextUtils.isEmpty(userInfo.getUserName())) {
                    etM.setText(userInfo.getUserName());
                }
                break;
            case 1:
                setTopTitle("身份证号");
                etM.setHint("请输入身份证号");
                etM.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                if (!TextUtils.isEmpty(userInfo.getIdentCard())) {//NullPoint
                    etM.setText(userInfo.getIdentCard());
                }
                break;

            case 4:////
                setTopTitle("收款银行");
                etM.setText("请输入收款银行");
                if (!TextUtils.isEmpty(userInfo.getBankNameTitle())) {
                    etM.setText(userInfo.getBankNameTitle());
                }
                break;
            case 5://
                setTopTitle("收款支行");
                etM.setHint("请输入收款支行");
                if (!TextUtils.isEmpty(userInfo.getBankName())) {
                    etM.setText(userInfo.getBankName());
                }
                break;
            case 6:
                setTopTitle("银行账号");
                etM.setHint("请输入银行账号");
                etM.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                etM.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (!TextUtils.isEmpty(userInfo.getBankAccNo())) {
                    etM.setText(userInfo.getBankAccNo());
                }
                break;
            case 7:
                setTopTitle("电子邮箱");
                etM.setHint("请输入电子邮箱");
                etM.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                if (!TextUtils.isEmpty(userInfo.getEmail())) {
                    etM.setText(userInfo.getEmail());
                }
                break;
            case 8:
                setTopTitle("年龄");
                etM.setHint("请输入年龄");
                if (userInfo.getUserAge() != null) {
                    etM.setText(userInfo.getUserAge() + "");
                }
                break;
            case 9://
                setTopTitle("所在支行");
                etM.setHint("请输入所在支行");
                if (!TextUtils.isEmpty(userInfo.getBankName())) {
                    etM.setText(userInfo.getBankName());
                }
                break;

        }
    }

    /**
     * 更新资料
     */
    private void updateData(final UserEntity se) {
        RequestManager.getServicerManager().UpadateServantData(se, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                EventBus.getDefault().post(new UserEvent(se, TYPE));

//                Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
//                intent.putExtra(ServiceMineFrg.SERVANT_INFO, se);
//                sendBroadcast(intent);

                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    private void updateUserData(final UserEntity userEntity){
        RequestManager.getCommManager().updateData(userEntity, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                EventBus.getDefault().post(new UserEvent(userEntity, TYPE));
                Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, userEntity);
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

