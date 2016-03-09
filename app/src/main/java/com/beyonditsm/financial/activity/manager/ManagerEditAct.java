package com.beyonditsm.financial.activity.manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.CreditManagerEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.ManagerMineFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.IdcardUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;

/**
 * 信贷经理编辑框
 * Created by Yang on 2015/11/17 0017.
 */
public class ManagerEditAct extends BaseActivity {
    @ViewInject(R.id.etM)
    private ClearEditText et;

    private int flag;

    public static final String TYPE = "type";
    private int type;
    private CreditManager cm;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");

        cm = getIntent().getParcelableExtra(ManagerMineFrg.CREDIT_DATAS);
        type = getIntent().getIntExtra(TYPE, 0);
        initdata(type);
        if(!TextUtils.isEmpty(et.getText().toString().trim())){
            et.setSelection(et.getText().toString().length());
        }
        setRightBtn("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = et.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请填写后点击保存");
                    return;
                }
                switch (type){
                    case 3://修改姓名
                        cm.setManaName(content);
                        break;
                    case 4://修改工号
                        cm.setJobNum(content);
                        break;
                    case 11://修改联系方式
                        cm.setManaTel(content);
                        break;
                    case 9://修改所在机构
                        cm.setBranchName(content);
                        break;
                    case 10://修改职位名称

                        break;
                }
                updateCreditDatas(cm);
            }
        });

    }

    /*
    * 初始化数据
    * */
    private void initdata(int position) {

        switch (position) {
            case 3://姓名
                setTopTitle("真实姓名");
                et.setHint("请输入真实姓名");
                if (!TextUtils.isEmpty(cm.getManaName())){
                    et.setText(cm.getManaName());
                }
                break;
            case 4://工号
                setTopTitle("工号");
                et.setHint("请输入工号");
                if (!TextUtils.isEmpty(cm.getJobNum())){
                    et.setText(cm.getJobNum());
                }
                break;

            case 9:
                setTopTitle("所在机构");
                et.setHint("请输入所在机构");
                if (!TextUtils.isEmpty(cm.getBranchName())){
                    et.setText(cm.getBranchName());
                }
                break;
            case 10://职级
                setTopTitle("职位名称");
                et.setHint("请输入职位名称");
                break;
            case 11://联系方式
                setTopTitle("联系方式");
                et.setHint("请输入联系方式");
                et.setInputType(InputType.TYPE_CLASS_PHONE);
                et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                if (!TextUtils.isEmpty(cm.getManaTel())){
                    et.setText(cm.getManaTel());
                }
                break;
        }

    }
    public void updateCreditDatas(final CreditManager cm){
        RequestManager.getMangManger().modifyCreditManager(cm, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                EventBus.getDefault().post(new UserEvent(cm,type));

                Intent intent = new Intent(ManagerMineFrg.UPDATE_CREDIT);
                intent.putExtra(ManagerMineFrg.CREDIT_DATAS,cm);
                sendBroadcast(intent);

                MyToastUtils.showShortToast(getApplicationContext(), "更新成功");
                finish();
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(getApplicationContext(),msg);
            }
        });
    }
}
