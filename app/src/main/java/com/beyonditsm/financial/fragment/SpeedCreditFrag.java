package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.adapter.SpeedCreditAdapter;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditFrag extends BaseFragment {
    @ViewInject(R.id.lv_speedCredit)
    private LoadRefreshView lvSpeedCredit;
//    @ViewInject(R.id.loading_speedCredit)
//    private LoadingView loadingSpeedCredit;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_speedcredit,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        SpeedCreditAdapter speedCreditAdapter = new SpeedCreditAdapter(getActivity());
        lvSpeedCredit.getRefreshableView().setAdapter(speedCreditAdapter);
        lvSpeedCredit.getRefreshableView().setDivider(null);
        lvSpeedCredit.getRefreshableView().setDividerHeight(20);

    }

    @Override
    public void setListener() {
        lvSpeedCredit.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CreditSpeedDetailAct.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
