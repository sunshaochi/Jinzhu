package com.beyonditsm.financial.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

/**
 * 极速贷我的贷款详情
 * Created by Administrator on 2016/10/24 0024.
 */

public class MyCreditSpeedDetailFrag extends BaseFragment {
    @ViewInject(R.id.iv_basicInfo)
    private ImageView ivBasicInfo;
    @ViewInject(R.id.iv_credentials)
    private ImageView ivCredentials;
    @ViewInject(R.id.iv_contacts)
    private ImageView ivContacts;
    @ViewInject(R.id.iv_material)
    private ImageView ivMaterial;
    @ViewInject(R.id.iv_storeInfo)
    private ImageView ivStroeInfo;
    @ViewInject(R.id.ll_basicInfo)
    private LinearLayout llBasicInfo;
    @ViewInject(R.id.ll_credentials)
    private LinearLayout llCredentials;
    @ViewInject(R.id.ll_contacts)
    private LinearLayout llContacts;
    @ViewInject(R.id.ll_material)
    private LinearLayout llMaterial;
    @ViewInject(R.id.ll_storeInfo)
    private LinearLayout llStoreInfo;
    @ViewInject(R.id.sv)
    private ScrollView sv;

    private MyCreditBean.RowsEntity rowe;
    private Map<Integer, Boolean> map = new HashMap<>();
    private ObjectAnimator obaDownBasicInfo,obaDownCredentials,obaDownContacts,obaDownMaterial,obaDownStoreInfo;
    private ObjectAnimator obaUpBasicInfo,obaUpCredentials,obaUpContacts,obaUpMaterial,obaUpStoreInfo;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_speedcreditdetail,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rowe = getArguments().getParcelable("rowe");

        initObjectAnimator();
        llBasicInfo.setVisibility(View.GONE);
        llCredentials.setVisibility(View.GONE);
        llMaterial.setVisibility(View.GONE);
        llContacts.setVisibility(View.GONE);
        llStoreInfo.setVisibility(View.GONE);
        map.put(0, false);
        map.put(1, false);
        map.put(2, false);
        map.put(3, false);
        map.put(4, false);
    }

    private void initObjectAnimator() {
        obaDownBasicInfo = ObjectAnimator.ofFloat(ivBasicInfo, "rotation", 0, 180);
        obaDownBasicInfo.setDuration(300);
        obaUpBasicInfo = ObjectAnimator.ofFloat(ivBasicInfo, "rotation", -180,
                0);
        obaUpBasicInfo.setDuration(300);

        obaDownCredentials = ObjectAnimator.ofFloat(ivCredentials, "rotation", 0, 180);
        obaDownCredentials.setDuration(300);
        obaUpCredentials = ObjectAnimator.ofFloat(ivCredentials, "rotation", -180,
                0);
        obaUpCredentials.setDuration(300);

        obaDownMaterial = ObjectAnimator.ofFloat(ivMaterial, "rotation", 0, 180);
        obaDownMaterial.setDuration(300);
        obaUpMaterial = ObjectAnimator.ofFloat(ivMaterial, "rotation", -180,
                0);
        obaUpMaterial.setDuration(300);

        obaDownContacts = ObjectAnimator.ofFloat(ivContacts, "rotation", 0, 180);
        obaDownContacts.setDuration(300);
        obaUpContacts = ObjectAnimator.ofFloat(ivContacts, "rotation", -180,
                0);
        obaUpContacts.setDuration(300);

        obaDownStoreInfo = ObjectAnimator.ofFloat(ivStroeInfo, "rotation", 0, 180);
        obaDownStoreInfo.setDuration(300);
        obaUpStoreInfo = ObjectAnimator.ofFloat(ivStroeInfo, "rotation", -180,
                0);
        obaUpStoreInfo.setDuration(300);

    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.rl_storeInfo,R.id.rl_material,R.id.rl_contacts,R.id.rl_credentials,R.id.rl_basicInfo})
    public void todo (View view){
        switch (view.getId()){
            //基本信息点击
            case R.id.rl_basicInfo:
                if (!map.get(0)) {
                    obaDownBasicInfo.start();
                    llBasicInfo.setVisibility(View.VISIBLE);
                    map.put(0, true);
                    scrollDown();
                } else {
                    obaUpBasicInfo.start();
                    llBasicInfo.setVisibility(View.GONE);
                    map.put(0, false);
                }
                break;
            //资质点击
            case R.id.rl_credentials:
                if (!map.get(1)) {
                    obaDownCredentials.start();
                    llCredentials.setVisibility(View.VISIBLE);
                    map.put(1, true);
                    scrollDown();
                } else {
                    obaUpCredentials.start();
                    llCredentials.setVisibility(View.GONE);
                    map.put(1, false);
                }
                break;
            //联系人点击
            case R.id.rl_contacts:
                if (!map.get(2)) {
                    obaDownContacts.start();
                    llContacts.setVisibility(View.VISIBLE);
                    map.put(2, true);
                    scrollDown();
                } else {
                    obaUpContacts.start();
                    llContacts.setVisibility(View.GONE);
                    map.put(2, false);
                }
                break;
            //资料点击
            case R.id.rl_material:
                if (!map.get(3)) {
                    obaDownMaterial.start();
                    llMaterial.setVisibility(View.VISIBLE);
                    map.put(3, true);
                    scrollDown();
                } else {
                    obaDownMaterial.start();
                    llMaterial.setVisibility(View.GONE);
                    map.put(3, false);
                }
                break;
            //门店信息点击
            case R.id.rl_storeInfo:
                if (!map.get(4)) {
                    obaDownStoreInfo.start();
                    llStoreInfo.setVisibility(View.VISIBLE);
                    map.put(4, true);
                    scrollDown();
                } else {
                    obaUpStoreInfo.start();
                    llStoreInfo.setVisibility(View.GONE);
                    map.put(4, false);
                }
                break;
        }
    }

    private void scrollDown() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
