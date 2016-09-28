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
 * 急速贷款第三步，联系人信息
 * Created by Administrator on 2016/9/27 0027.
 */

public class CreditSpeedSecondFrag3 extends BaseFragment {
    @ViewInject(R.id.tv_speedRelativesName_1)
    private TextView tvSpeedRelativesName;
    @ViewInject(R.id.tv_speedRelativesPhone_1)
    private TextView tvSpeedRelativePhone;
    @ViewInject(R.id.tv_speedRelationship_1)
    private TextView tvSpeedRelationship;
    @ViewInject(R.id.tv_speedRelativesWoH_1)
    private TextView tvSpeedRelationsWoH;
    @ViewInject(R.id.et_speedRelativesWoHDetail_1)
    private EditText etSpeedRelativeWoHDetail;
    @ViewInject(R.id.tv_speedColleagueName_1)
    private TextView tvSpeedColleagueName;
    @ViewInject(R.id.tv_speedColleaguePhone_1)
    private TextView tvSpeedColleaguePhone;
    @ViewInject(R.id.tv_speedColleagueWoH_1)
    private TextView tvSpeedColleagueWoH;
    @ViewInject(R.id.et_speedColleagueWoHDetail_1)
    private EditText etSpeedColleagueWoHDetail;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_creditspeedsecond_3,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initText();
    }

    private void initText() {
        String speedName = "姓&#160;&#160;&#160;&#160;&#160;&#160;名<font color='#FF0000'>*</font>:";
        tvSpeedRelativesName.setText(Html.fromHtml(speedName));
        tvSpeedColleagueName.setText(Html.fromHtml(speedName));
        String speedPhone = "联系电话<font color='#FF0000'>*</font>:";
        tvSpeedRelativePhone.setText(Html.fromHtml(speedPhone));
        tvSpeedColleaguePhone.setText(Html.fromHtml(speedPhone));
        String relationship = "关&#160;&#160;&#160;&#160;&#160;&#160;系<font color='#FF0000'>*</font>:";
        tvSpeedRelationship.setText(Html.fromHtml(relationship));
        String homeOrCompany = "家庭/单位地址<font color='#FF0000'>*</font>:";
        tvSpeedRelationsWoH.setText(Html.fromHtml(homeOrCompany));
        tvSpeedColleagueWoH.setText(Html.fromHtml(homeOrCompany));
        String speedPermanentDetail = "详细地址<font color='#FF0000'>*</font>:";
        etSpeedRelativeWoHDetail.setHint(Html.fromHtml(speedPermanentDetail));
        etSpeedColleagueWoHDetail.setHint(Html.fromHtml(speedPermanentDetail));
    }

    @Override
    public void setListener() {

    }
}
