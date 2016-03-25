package com.beyonditsm.financial.activity.credit;

import android.os.Bundle;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.http.RequestManager;

import org.json.JSONException;

/**
 * 贷款上传资料
 * Created by wangbin on 16/3/22.
 */
public class CreditUploadAct extends BaseActivity {
    private String orderId;
    private String flowId;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_upload);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("上传资料图片");
        orderId=getIntent().getStringExtra("orderId");
        flowId=getIntent().getStringExtra("flowId");
        findFlowDetail(orderId,"c897fa09f2224325af532dab81a0d7da");
    }


    /**
     * 获取上传列表
     * @param orderId
     * @param flowId
     */
    private void findFlowDetail(String orderId,String flowId){
        RequestManager.getCommManager().findFlowDetail(orderId, flowId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
