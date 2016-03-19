package com.beyonditsm.financial.activity.manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.fragment.ManagerOrderFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 补件说明界面
 * Created by Yang on 2015/12/13 0013.
 */
public class DribblewareAct extends BaseActivity {
    @ViewInject(R.id.bj_commit)
    private Button commit;//提交
    @ViewInject(R.id.bj_et)
    private EditText et;//内容
    private GrabOrderBean.RowsEntity data;
    private String orderId;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_dribbleware);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("补件说明");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            data = bundle.getParcelable("data");
            orderId = data.getId();
        }
        if (TextUtils.isEmpty(orderId)) {
            commit.setEnabled(false);
        } else {
            commit.setEnabled(true);
        }
    }

    @OnClick(R.id.bj_commit)
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.bj_commit:
                String content = et.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    MyToastUtils.showShortToast(this, "请输入补件说明");
                } else {
                    RequestManager.getMangManger().orderbj(orderId, content, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {

                            Intent intent = new Intent(ManagerOrderFragment.UPDATA);
                            intent.putExtra("orderSts", "SUPPLEMENT_DATA");
                            sendBroadcast(intent);
                            Intent intent1 = new Intent(DribblewareAct.this, ManagerMainAct.class);
                            intent1.putExtra("position",1);
                            startActivity(intent1);
                            MyToastUtils.showShortToast(DribblewareAct.this, "提交成功");
//                            finish();
                        }

                        @Override
                        public void onError(int status, String msg) {
                            MyToastUtils.showShortToast(DribblewareAct.this, msg);
                        }
                    });
                }
                break;
        }
    }
}
