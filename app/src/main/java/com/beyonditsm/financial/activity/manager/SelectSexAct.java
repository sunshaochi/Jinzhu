package com.beyonditsm.financial.activity.manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.ServantEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;

/**
 * 性别选择界面
 * Created by Yang on 2015/11/27 0027.
 */
public class SelectSexAct extends BaseActivity {
    @ViewInject(R.id.rgBoy)
    private RadioButton rgBoy;
    @ViewInject(R.id.rgGirl)
    private RadioButton rgGirl;
    @ViewInject(R.id.rgSex)
    private RadioGroup rgSex;

    public final static String SEX = "sex";
    private int sex;//0女 1男

    private UserEntity userInfo;
    private UserEntity servantInfo ;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_selectsex);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("性别");
        userInfo = getIntent().getParcelableExtra(MineFragment.USER_KEY);
        servantInfo = getIntent().getParcelableExtra(ServiceMineFrg.SERVANT_INFO);
        sex = getIntent().getIntExtra(SEX, 0);
        if (sex == 0) {
            rgGirl.setChecked(true);
        } else {
            rgBoy.setChecked(true);
        }

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rgBoy:
                        if (userInfo != null) {
                            userInfo.setUserSex(1);
                            updateData(userInfo);
                        }
                        if (servantInfo != null) {
                            servantInfo.setUserSex(1);
                            updateServantDatas(servantInfo);
                        }
                        break;
                    case R.id.rgGirl:
                        if (userInfo != null) {
                            userInfo.setUserSex(0);
                            updateData(userInfo);
                        }
                        if (servantInfo != null) {
                            servantInfo.setUserSex(0);
                            updateServantDatas(servantInfo);
                        }
                        break;
                }

            }
        });

    }


    private void updateData(final UserEntity ue) {
        RequestManager.getCommManager().updateData(ue, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                EventBus.getDefault().post(new UserEvent(ue, 10));
                Intent intent = new Intent(MineFragment.UPDATE_USER);
                intent.putExtra(MineFragment.USER_KEY, ue);
                sendBroadcast(intent);


                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
                finish();
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });

    }

    private void updateServantDatas(final UserEntity se) {
        RequestManager.getServicerManager().UpadateServantData(se, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                EventBus.getDefault().post(new UserEvent(se, 10));

                Intent intent = new Intent(ServiceMineFrg.UPDATE_SERVANT);
                intent.putExtra(ServiceMineFrg.SERVANT_INFO, se);
                sendBroadcast(intent);
                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
                finish();
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }
}
