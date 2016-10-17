package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditSpeedDetailAct;
import com.beyonditsm.financial.adapter.SpeedCreditAdapter;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 急借通fragment
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditFrag extends BaseFragment {
    @ViewInject(R.id.lv_speedCredit)
    private LoadRefreshView lvSpeedCredit;
    private List<CreditSpeedEntity> creditSpeedList;
    private SpeedCreditAdapter speedCreditAdapter;

    //    @ViewInject(R.id.loading_speedCredit)
//    private LoadingView loadingSpeedCredit;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_speedcredit,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        lvSpeedCredit.getRefreshableView().setDivider(null);
        lvSpeedCredit.getRefreshableView().setDividerHeight(20);
        getCreditSpeed();
    }

    private void getCreditSpeed() {
        RequestManager.getCommManager().getCreditSpeed(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                creditSpeedList = gson.fromJson(data.toString(), new TypeToken<List<CreditSpeedEntity>>() {
                }.getType());

                if (speedCreditAdapter==null) {
                    speedCreditAdapter = new SpeedCreditAdapter(getActivity(),creditSpeedList);
                    lvSpeedCredit.getRefreshableView().setAdapter(speedCreditAdapter);
                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
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
