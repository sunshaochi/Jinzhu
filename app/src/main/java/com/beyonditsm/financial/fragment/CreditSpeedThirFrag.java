package com.beyonditsm.financial.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 极速贷第三步
 * Created by Administrator on 2016/10/11 0011.
 */

public class CreditSpeedThirFrag extends BaseFragment {
    @ViewInject(R.id.tv_rereq)
    private TextView tvRereq;//申请材料
    @ViewInject(R.id.tv_positiveCard)
    private TextView tvPositiveCard;//身份证正面
    @ViewInject(R.id.tv_negativeCard)
    private TextView tvNegativeCard;//身份证背面
    @ViewInject(R.id.tv_selectStore)
    private TextView tvSelectStore;//选择门店
    @ViewInject(R.id.tv_selectStoreCity)
    private TextView tvSelectStoreCity;//所在城市
    @ViewInject(R.id.tv_selectStoreAddr)
    private TextView tvSelectStoreAddr;//门店地址
    @ViewInject(R.id.tv_seeMore)
    private TextView tvSeeMore;//点击查看
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_creditspeedthir,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initText();
    }

    private void initText() {
        String rereq = "申请材料<font color='#FF0000'>*</font>";
        tvRereq.setText(Html.fromHtml(rereq));
        String positiveCard = "身份证正面<font color='#FF0000'>*</font>:";
        tvPositiveCard.setText(Html.fromHtml(positiveCard));
        String negativeCard = "身份证背面<font color='#FF0000'>*</font>:";
        tvNegativeCard.setText(Html.fromHtml(negativeCard));
        String selectStore = "选择门店<font color='#FF0000'>*</font>";
        tvSelectStore.setText(Html.fromHtml(selectStore));
        String storeCity = "所在城市<font color='#FF0000'>*</font>:";
        tvSelectStoreCity.setText(Html.fromHtml(storeCity));
        String storeAddr = "门店地址<font color='#FF0000'>*</font>:";
        tvSelectStoreAddr.setText(Html.fromHtml(storeAddr));
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.tv_seeMore})
    public void todo(View view){
        switch (view.getId()){
            case R.id.tv_seeMore:

                break;
        }
    }
}
