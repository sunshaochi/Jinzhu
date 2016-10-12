package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 急速贷款第一步，基本信息
 * Created by Administrator on 2016/9/27 0027.
 */

public class CreditSpeedSecondFrag1 extends BaseFragment {
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
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_creditspeedsecond_1,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initText();
    }

    /*初始化页面字体显示*/
    private void initText() {
        String topTitle = "您的基本信息（<font color='#FF0000'>*</font>为必填项）";
        tvSpeedTop_1.setText(Html.fromHtml(topTitle));
        String speedName = "姓&#160;&#160;&#160;&#160;&#160;&#160;名<font color='#FF0000'>*</font>:";
        tvSpeedName.setText(Html.fromHtml(speedName));
        String speedIdCard = "身份证号<font color='#FF0000'>*</font>:";
        tvSpeedIdCard.setText(Html.fromHtml(speedIdCard));
        String speedPhone = "电话号码<font color='#FF0000'>*</font>:";
        tvSpeedPhone.setText(Html.fromHtml(speedPhone));
        String speedMarriage = "婚姻状况<font color='#FF0000'>*</font>:";
        tvSpeedMarriage.setText(Html.fromHtml(speedMarriage));
        String speedEdu = "学&#160;&#160;&#160;&#160;&#160;&#160;历<font color='#FF0000'>*</font>:";
        tvSpeedEducation.setText(Html.fromHtml(speedEdu));
        String speedPermanent = "户&#160;&#160;籍&#160;&#160;地<font color='#FF0000'>*</font>:";
        tvSpeedPermanent.setText(Html.fromHtml(speedPermanent));
        String speedResident = "常&#160;&#160;住&#160;&#160;地<font color='#FF0000'>*</font>:";
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
        etSpeedPermanentDetail.setHint(Html.fromHtml(speedPermanentDetail));
        etSpeedResidentDetail.setHint(Html.fromHtml(speedPermanentDetail));

    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.tv_speed_toTwo})
    public void todo(View view){
        switch (view.getId()){
            case R.id.tv_speed_toTwo:
                Intent intent = new Intent(CreditSpeedSecondFrag.NEXT);
                intent.putExtra("item",1);
                getActivity().sendBroadcast(intent);
                break;
        }
    }
}
