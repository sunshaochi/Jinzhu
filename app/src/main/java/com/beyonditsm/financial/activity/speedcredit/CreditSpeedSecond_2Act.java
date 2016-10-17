package com.beyonditsm.financial.activity.speedcredit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 极速贷第二步 第二小步
 * Created by Administrator on 2016/10/14 0014.
 */

public class CreditSpeedSecond_2Act extends BaseActivity {

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
    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeedsecond_2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initText();
    }

    /*初始化页面字体显示*/
    private void initText() {
        String topTitle = "您的资质（<font color='#FF0000'>*</font>为必填项）";
        tvSpeedTop_2.setText(Html.fromHtml(topTitle));
        String speedName = "单位名称<font color='#FF0000'>*</font>:";
        tvSpeedCompanyName.setText(Html.fromHtml(speedName));
        String speedIdCard = "单位电话<font color='#FF0000'>*</font>:";
        tvSpeedCompanyPhone.setText(Html.fromHtml(speedIdCard));
        String speedPhone = "单位地址<font color='#FF0000'>*</font>:";
        tvSpeedCompantAddress.setText(Html.fromHtml(speedPhone));
        String speedMarriage = "部&#160;&#160;&#160;&#160;&#160;&#160;门<font color='#FF0000'>*</font>:";
        tvSpeedDepartment.setText(Html.fromHtml(speedMarriage));
        String speedEdu = "单位性质<font color='#FF0000'>*</font>:";
        tvSpeedCompanyType.setText(Html.fromHtml(speedEdu));
        String speedPermanent = "工作性质<font color='#FF0000'>*</font>:";
        tvSpeedWorkType.setText(Html.fromHtml(speedPermanent));
        String speedResident = "月基本薪水<font color='#FF0000'>*</font>:";
        tvSpeedSalary.setText(Html.fromHtml(speedResident));
        String speedLiving = "月薪发放形式<font color='#FF0000'>*</font>:";
        tvSpeedSalaryType.setText(Html.fromHtml(speedLiving));
        String speedBank = "月发薪日<font color='#FF0000'>*</font>:";
        tvSpeedSalaryDay.setText(Html.fromHtml(speedBank));
        String speedPermanentDetail = "详细地址<font color='#FF0000'>*</font>";
        etSpeedCompanyAddressDetail.setHint(Html.fromHtml(speedPermanentDetail));

    }

    @OnClick({R.id.tv_speed_toThere})
    public void todo(View view){
        switch (view.getId()){
            case R.id.tv_speed_toThere:
                Intent intent = new Intent(this, CreditSpeedSecond_3Act.class);
                startActivity(intent);
//                Intent intent = new Intent(CreditSpeedSecondFrag.NEXT);
//                intent.putExtra("item",2);
//                getActivity().sendBroadcast(intent);
                break;
        }
    }
}
