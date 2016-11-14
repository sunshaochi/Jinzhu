package com.beyonditsm.financial.activity.speedcredit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.speedcredit.creditspeedthied.CreditSpeedThird_2Act;
import com.beyonditsm.financial.activity.speedcredit.listener.DialogListener;
import com.beyonditsm.financial.entity.RelationEntity;
import com.beyonditsm.financial.entity.UserOrderInfo3;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.CheckUtil;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.DialogCloseBtn;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极速贷第二步，第三小步
 * Created by Administrator on 2016/10/14 0014.
 */

public class CreditSpeedSecond_3Act extends BaseActivity {

    @ViewInject(R.id.tv_speedRelativesName_1)
    private TextView tvSpeedRelativesName1; //亲属姓名1
    @ViewInject(R.id.tv_speedRelativesPhone_1)
    private TextView tvSpeedRelativePhone1; //亲属手机1
    @ViewInject(R.id.tv_speedRelationship_1)
    private TextView tvSpeedRelationship1;//亲属关系1

    @ViewInject(R.id.tv_speedRelativesName_2)
    private TextView tvSpeedRelativesName2; //亲属姓名2
    @ViewInject(R.id.tv_speedRelativesPhone_2)
    private TextView tvSpeedRelativePhone2; //亲属手机2
    @ViewInject(R.id.tv_speedRelationship_2)
    private TextView tvSpeedRelationship2;  //亲属关系2


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
    @ViewInject(R.id.tv_speedSelectRelationship_1)
    private TextView tvSpeedSelectRelationship_1;
    @ViewInject(R.id.tv_speedSelectRelationship_2)
    private TextView tvSpeedSelectRelationship_2;
    @ViewInject(R.id.tv_ColleagueRelationship)
    private TextView tvColleagueRelationship;
    @ViewInject(R.id.tv_ColleagueSelectRelationship)
    private TextView tvColleagueSelectRelationship;
    @ViewInject(R.id.tv_speedSelectEmergent_1)
    private TextView tvSpeedSelectEmergent_1;
    @ViewInject(R.id.et_speedRelativesName_1)
    private EditText etSpeedRelativesName_1; //亲属姓名1
    @ViewInject(R.id.et_speedRelativesPhone_1)
    private EditText etSpeedRelativesPhone_1; //亲属电话1
    @ViewInject(R.id.et_speedRelativesName_2)
    private EditText etSpeedRelativesName_2; //亲属姓名2
    @ViewInject(R.id.et_speedRelativesPhone_2)
    private EditText etSpeedRelativesPhone_2; //亲属电话2
    @ViewInject(R.id.et_speedColleagueName_1)
    private EditText etSpeedColleagueName_1; //同事姓名1
    @ViewInject(R.id.et_speedColleaguePhone_1)
    private EditText etSpeedColleaguePhone_1; //同事电话
    @ViewInject(R.id.et_speedEmergentName_1)
    private EditText etSpeedEmergentName_1; //紧急联系人姓名1
    @ViewInject(R.id.et_speedEmergentPhone_1)
    private EditText etSpeedEmergentPhone_1; //紧急联系人电话1
    @ViewInject(R.id.ll_fatherLayout)
    private LinearLayout llFatherLayout;
    @ViewInject(R.id.tv_speedEmergentName_1)
    private TextView tv_speedEmergentName_1;
    @ViewInject(R.id.tv_speedEmergentPhone_1)
    private TextView tv_speedEmergentPhone_1;
    @ViewInject(R.id.tv_speedEmergent_1)
    private TextView tv_speedEmergent_1;
    private List<RelationEntity> relationList;
    private String orderId;
    private int CURRENT_SELECT;
    private final int RL_SPEEDMARRIAGE1 = 1;
    private final int RL_SPEEDMARRIAGE2 = 2;
    private final int RL_SPEEDCOLLEAGUE = 3;
    private final int RL_SPEEDEMERGENT = 4;
    private String marriage1Relation;
    private String marriage2Relation;
    private String colleagueRelation;
    private String mergentRelation;

    private final static long WAITTIME = 2000;
    private long touchTime = 0;

    @Override
    public void goback(View view) {
//        super.goback(view);
        new DialogCloseBtn(CreditSpeedSecond_3Act.this).builder().setCreditOfflineDialogListener(new DialogListener() {
            @Override
            public void onYes() {
                Intent intent = new Intent(CreditSpeedSecond_3Act.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }


    @Override
    public void onBackPressed() {
        new DialogCloseBtn(CreditSpeedSecond_3Act.this).builder().setCreditOfflineDialogListener(new DialogListener() {
            @Override
            public void onYes() {
                Intent intent = new Intent(CreditSpeedSecond_3Act.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeedsecond_3);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("快速判断资质");
        setLeftTv("返回");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        queryRelation();
        initText();


        etSpeedRelativesName_1.addTextChangedListener(new TextWatch());
        etSpeedRelativesName_2.addTextChangedListener(new TextWatch());
        etSpeedColleagueName_1.addTextChangedListener(new TextWatch());
        etSpeedEmergentName_1.addTextChangedListener(new TextWatch());

    }


    private class  TextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!FinancialUtil.isInputChinese(etSpeedRelativesName_1.getText().toString())) {
                etSpeedRelativesName_1.setError("真实姓名必须为中文！");
            }
            if (!FinancialUtil.isInputChinese(etSpeedRelativesName_2.getText().toString())) {
                etSpeedRelativesName_2.setError("真实姓名必须为中文！");
            }
            if (!FinancialUtil.isInputChinese(etSpeedColleagueName_1.getText().toString())) {
                etSpeedColleagueName_1.setError("真实姓名必须为中文！");
            }
            if (!FinancialUtil.isInputChinese(etSpeedEmergentName_1.getText().toString())) {
                etSpeedEmergentName_1.setError("真实姓名必须为中文！");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private MySelfSheetDialog initDialog() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(CreditSpeedSecond_3Act.this).builder();
        for (int i = 0; i < relationList.size(); i++) {
            dialog.addSheetItem(relationList.get(i).getName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    switch (CURRENT_SELECT){
                        case RL_SPEEDMARRIAGE1:
                            marriage1Relation = relationList.get(which-1).getId()+"";
                            break;
                        case RL_SPEEDMARRIAGE2:
                            marriage2Relation = relationList.get(which-1).getId()+"";
                            break;
                        case RL_SPEEDCOLLEAGUE:
                            colleagueRelation = relationList.get(which-1).getId()+"";
                            break;
                        case RL_SPEEDEMERGENT:
                            mergentRelation = relationList.get(which-1).getId()+"";
                            break;
                    }
                }
            });
        }
        return dialog;
    }


    private void initText() {
        String speedName = "姓名<font color='#FF0000'>*</font>:";
        tvSpeedRelativesName1.setText(Html.fromHtml(speedName));
        tvSpeedRelativesName2.setText(Html.fromHtml(speedName));
        tvSpeedColleagueName.setText(Html.fromHtml(speedName));
        String speedPhone = "联系电话<font color='#FF0000'>*</font>:";
        tvSpeedRelativePhone1.setText(Html.fromHtml(speedPhone));
        tvSpeedRelativePhone2.setText(Html.fromHtml(speedPhone));
        tvSpeedColleaguePhone.setText(Html.fromHtml(speedPhone));
        String relationship = "关系<font color='#FF0000'>*</font>:";
        tvSpeedRelationship1.setText(Html.fromHtml(relationship));
        tvSpeedRelationship2.setText(Html.fromHtml(relationship));
        tvColleagueRelationship.setText(Html.fromHtml(relationship));
        String homeOrCompany = "家庭/单位地址<font color='#FF0000'>*</font>:";
        tvSpeedRelationsWoH.setText(Html.fromHtml(homeOrCompany));
        tvSpeedColleagueWoH.setText(Html.fromHtml(homeOrCompany));
        String speedPermanentDetail = "详细地址<font color='#FF0000'>*</font>:";
        etSpeedRelativeWoHDetail.setHint(Html.fromHtml(speedPermanentDetail));
        etSpeedColleagueWoHDetail.setHint(Html.fromHtml(speedPermanentDetail));

        tv_speedEmergentName_1.setText(Html.fromHtml(speedName));
        tv_speedEmergentPhone_1.setText(Html.fromHtml(speedPhone));
        tv_speedEmergent_1.setText(Html.fromHtml(relationship));
    }

    @OnClick({R.id.tv_speed_toEnd, R.id.rl_speedMarriage1, R.id.rl_speedMarriage2, R.id.rl_speedColleague, R.id.rl_speedEmergent})
    public void todo(View view) {
        switch (view.getId()) {
            case R.id.tv_speed_toEnd:
                String result = CheckUtil.CheckOutNull(llFatherLayout);
                if (result!=null){
                    MyToastUtils.showShortDebugToast(CreditSpeedSecond_3Act.this,result);
                }  else {
                    if (isChinese() && isPhoneNum()) {

                        saveUserInfo3();
                    }
                }



                break;
            case R.id.rl_speedMarriage1:
                CURRENT_SELECT = RL_SPEEDMARRIAGE1;
                initDialog().show(tvSpeedSelectRelationship_1);

                break;
            case R.id.rl_speedMarriage2:
                CURRENT_SELECT = RL_SPEEDMARRIAGE2;
                initDialog().show(tvSpeedSelectRelationship_2);
                break;
            case R.id.rl_speedColleague:
                CURRENT_SELECT = RL_SPEEDCOLLEAGUE;
                initDialog().show(tvColleagueSelectRelationship);
                break;
            case R.id.rl_speedEmergent:
                CURRENT_SELECT = RL_SPEEDEMERGENT;
                initDialog().show(tvSpeedSelectEmergent_1);

                break;
        }
    }

    private boolean isPhoneNum() {
        if(etSpeedRelativesPhone_1.getText().toString().length() == 11 &&
                etSpeedRelativesPhone_2.getText().toString().length() == 11 &&
                etSpeedColleaguePhone_1.getText().toString().length() == 11 &&
                etSpeedEmergentPhone_1.getText().toString().length() == 11){
            return true;
        }else {
            MyToastUtils.showShortToast(CreditSpeedSecond_3Act.this,"请输入正确手机号");
            return false;
        }


    }

    /*获取亲属关系*/
    public void queryRelation() {
        CommManager.getCommManager().queryRelation(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                relationList = gson.fromJson(data.toString(), new TypeToken<List<RelationEntity>>() {
                }.getType());
                initDialog();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    public void saveUserInfo3() {

        UserOrderInfo3 userOrderInfo3 = new UserOrderInfo3(orderId, etSpeedRelativesName_1.getText().toString() + "",
                marriage1Relation + "",
                etSpeedRelativesPhone_1.getText().toString() + "", etSpeedRelativesName_2.getText().toString() + "",
                marriage2Relation + "",
                etSpeedRelativesPhone_2.getText().toString() + "", etSpeedColleagueName_1.getText().toString() + "",
                colleagueRelation + "",
                etSpeedColleaguePhone_1.getText().toString() + "",
                etSpeedEmergentName_1.getText().toString() + "",
                mergentRelation + "",
                etSpeedEmergentPhone_1.getText().toString() + ""
        );
        CommManager.getCommManager().saveUserOrderInfo3(userOrderInfo3, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                Intent intent = new Intent(CreditSpeedSecond_3Act.this, CreditSpeedThird_2Act.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private boolean isChinese(){
        if (!FinancialUtil.isInputChinese(etSpeedRelativesName_1.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_3Act.this, "真实姓名必须为中文！");
            return false;
        }
        if (!FinancialUtil.isInputChinese(etSpeedRelativesName_2.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_3Act.this, "真实姓名必须为中文！");
            return false;
        }
        if (!FinancialUtil.isInputChinese(etSpeedColleagueName_1.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_3Act.this, "真实姓名必须为中文！");
            return false;
        }
        if (!FinancialUtil.isInputChinese(etSpeedEmergentName_1.getText().toString())) {
            MyToastUtils.showShortToast(CreditSpeedSecond_3Act.this, "真实姓名必须为中文！");
            return false;
        }

        return true;
    }
}
