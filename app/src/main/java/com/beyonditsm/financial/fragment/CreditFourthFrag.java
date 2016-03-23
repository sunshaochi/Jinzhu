package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;

/**
 * 贷款第三步
 * Created by Yang on 2015/11/12 0012.
 */
public class CreditFourthFrag extends BaseFragment implements View.OnClickListener {
    private Button crethrBtn1;
    private Button crethrBtn2;

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
                getActivity().finish();
                AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                break;
            case R.id.crethr_btn2:
                context.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                getActivity().finish();
                AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                break;
        }
    }
}
