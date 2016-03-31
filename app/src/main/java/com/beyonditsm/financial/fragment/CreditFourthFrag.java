package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.credit.SubFlowAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 贷款第三步
 * Created by Yang on 2015/11/12 0012.
 */
public class CreditFourthFrag extends BaseFragment implements View.OnClickListener {
    private Button crethrBtn1;
    private Button crethrBtn2;

    @ViewInject(R.id.tvNo)
    private TextView tvNo;//不需要增信材料
    @ViewInject(R.id.llHas)
    private LinearLayout llHas;//需要增信材料

    String orderId;

    private void assignViews() {
        crethrBtn1 = (Button) view.findViewById(R.id.crethr_btn1);
        crethrBtn2 = (Button) view.findViewById(R.id.crethr_btn2);
    }


    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.creditthrfrag, null);
        assignViews();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        orderId=CreditStepAct.orderId;
        if(CreditStepAct.upList!=null&&CreditStepAct.upList.size()>0){
            crethrBtn1.setText("提交增信材料");
            llHas.setVisibility(View.VISIBLE);
            tvNo.setVisibility(View.GONE);
        }else{
            crethrBtn1.setText("继续申请");
            llHas.setVisibility(View.GONE);
            tvNo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setListener() {
        crethrBtn1.setOnClickListener(this);
        crethrBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crethr_btn1:
                if(CreditStepAct.upList!=null&&CreditStepAct.upList.size()>0){
                    Intent intent=new Intent(getActivity(), SubFlowAct.class);
                    intent.putParcelableArrayListExtra("sub_list", (ArrayList<? extends Parcelable>) CreditStepAct.upList);
                    intent.putExtra("order_id",orderId);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }else {
                    getActivity().finish();
                    AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                }
                break;
            case R.id.crethr_btn2:
                context.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                getActivity().finish();
                AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                break;
        }
    }
}
