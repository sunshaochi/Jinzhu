package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by bitch-1 on 2016/12/8.
 */

public class EditorAct extends BaseActivity {
    @ViewInject(R.id.et_tv)
    private EditText et_tv;
    @ViewInject(R.id.tv_btn_bc)
    private TextView tv_btn_bc;//保存按钮

    private String text;
    private int type;
    private String ettext;

    private String title;
    @Override
    public void setLayout() {
       setContentView(R.layout.act_edito);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        type=getIntent().getExtras().getInt("type");
        ettext=getIntent().getExtras().getString("text");
        if(type==1){
            title="贷款金额";
        }else if(type==2){
            title="请输入年纪";
        }
        else if(type==3){
            title="工作/执照注册时长";
        }
        else if(type==4){
            title="公积金/社保时长";
        }
        setTopTitle(title);
        if(!TextUtils.isEmpty(ettext)){
           et_tv.setText(ettext);
        }else {
        et_tv.setHint(title);}
    }

    @OnClick({R.id.tv_btn_bc})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_btn_bc:
                text=et_tv.getText().toString();
                if(TextUtils.isEmpty(text)){
                    MyToastUtils.showLongToast(EditorAct.this,title);
                    et_tv.requestFocus();
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("text",text);
                    setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                    finish();
                }
                break;
        }

    }

}
