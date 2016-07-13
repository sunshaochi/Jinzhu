package com.beyonditsm.financial.activity.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.CreditManagerEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.ManagerMineFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.AddressUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.DialogChooseAdress;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;

import java.util.List;

/**
 * 信贷经理查看我的资料和更改资料合并
 * Created by Yang on 2015/11/17 0017.
 */
public class ManagerUpdateAct extends BaseActivity {

    private CreditManager creditManager;

    @ViewInject(R.id.tv_JobNum)
    private TextView tvJobNuM;
    @ViewInject(R.id.tv_ManaName)
    private TextView tvManaName;
    @ViewInject(R.id.tv_ManaTel)
    private TextView tvManaTel;
    @ViewInject(R.id.tv_Address)
    private TextView tvAddress;
    @ViewInject(R.id.tv_Work)
    private TextView tvWork;
    private AddressUtil addressUtil;

    @Override
    public void setLayout() {
        setContentView(R.layout.managerupdateact);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("我的资料");
        addressUtil = new AddressUtil(this);
        creditManager = getIntent().getParcelableExtra(ManagerMineFrg.CREDIT_DATAS);
        if (creditManager != null) {
            setCreditManagerDetails(creditManager);
        } else {
            findCreditManagerDetails();
        }
        EventBus.getDefault().register(this);
    }

    @SuppressLint("SetTextI18n")
    private void setCreditManagerDetails(CreditManager cm) {

        //姓名
        if (!TextUtils.isEmpty(cm.getManaName())) {
            tvManaName.setText(cm.getManaName());
        }
        //工号
        if (!TextUtils.isEmpty(cm.getJobNum())) {
            tvJobNuM.setText(cm.getJobNum());
        }

        //联系方式
        if (!TextUtils.isEmpty(cm.getManaTel())) {
            tvManaTel.setText(cm.getManaTel());
        }
        //职位名称
//        if (!TextUtils.isEmpty(cm.get())) {
//            tvJobName.setText(cm.getEmail());
//        }
        //所属机构
        if (!TextUtils.isEmpty(cm.getBranchName())) {
            tvWork.setText(cm.getBranchName());
        }
        //地址
        if (cm.getCity()!=null) {
            tvAddress.setText(addressUtil.getProName(cm.getProvince())
                    +addressUtil.getCityName(cm.getProvince(),cm.getCity())
                    +addressUtil.getCountryName(cm.getCity(),cm.getDistrict()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(UserEvent event) {
        creditManager = event.cm;
        switch (event.position) {
            case 3:
                tvManaName.setText(event.cm.getManaName());
                break;
            case 4:
                tvJobNuM.setText(creditManager.getJobNum());
                break;
            case 11:
                tvManaTel.setText(creditManager.getManaTel());
                break;
            case 9:
                tvWork.setText(creditManager.getBranchName());
                break;
        }
    }

    @OnClick({R.id.rl_JobNum,R.id.rl_ManaName,R.id.rl_ManaTel,R.id.rl_Address,R.id.rl_jobName,R.id.rl_Work})
    public void todo(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_ManaName://姓名
                intent = new Intent(this, ManagerEditAct.class);
                intent.putExtra(ManagerEditAct.TYPE, 3);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, creditManager);
                startActivity(intent);
                break;
            case R.id.rl_JobNum://工号
                intent = new Intent(this, ManagerEditAct.class);
                intent.putExtra(ManagerEditAct.TYPE, 4);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, creditManager);
                startActivity(intent);
                break;
            case R.id.rl_Address://地址
                DialogChooseAdress dialogChooseAdress1 = new DialogChooseAdress(this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseAdress.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        creditManager.setCity(adress.get(0)+adress.get(1)+adress.get(2));
                        updateCreditDatas(creditManager, 0);
                    }
                });
                break;

            case R.id.rl_work://所在机构
                intent = new Intent(this, ManagerEditAct.class);
                intent.putExtra(ManagerEditAct.TYPE, 9);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, creditManager);
                startActivity(intent);
                break;
            case R.id.rl_jobName://职位名称
                intent = new Intent(this, ManagerEditAct.class);
                intent.putExtra(ManagerEditAct.TYPE, 10);
//                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, cme);
                startActivity(intent);
                break;
            case R.id.rl_ManaTel://联系方式
                intent = new Intent(this, ManagerEditAct.class);
                intent.putExtra(ManagerEditAct.TYPE, 11);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, creditManager);
                startActivity(intent);
                break;
        }
    }

    public void findCreditManagerDetails() {
        RequestManager.getMangManger().currentCreditManagerDetail(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<CreditManagerEntity> rd = (ResultData<CreditManagerEntity>) GsonUtils.json(result, CreditManagerEntity.class);
                CreditManagerEntity cme = rd.getData();
                if (cme != null) {
                    creditManager = cme.getCreditManager();
                    if (creditManager != null) {
                        Intent intent = new Intent(ManagerMineFrg.UPDATE_CREDIT);
                        intent.putExtra(ManagerMineFrg.CREDIT_DATAS, creditManager);
                        sendBroadcast(intent);
                        setCreditManagerDetails(creditManager);
                    }
                }
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    public void updateCreditDatas(final CreditManager cm, final int type) {
        RequestManager.getMangManger().modifyCreditManager(cm, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                switch (type) {
                    case 0://地址
                        tvAddress.setText(cm.getCity());
                        break;
                }
                Intent intent = new Intent(ManagerMineFrg.UPDATE_CREDIT);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS, cm);
                sendBroadcast(intent);

                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }
}
