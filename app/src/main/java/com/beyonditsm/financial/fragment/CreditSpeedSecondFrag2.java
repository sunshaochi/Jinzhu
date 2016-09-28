package com.beyonditsm.financial.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 急速贷款第二步，资质
 * Created by Administrator on 2016/9/27 0027.
 */

public class CreditSpeedSecondFrag2 extends BaseFragment {
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
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_creditspeedsecond_2,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
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

    @Override
    public void setListener() {

    }
}
