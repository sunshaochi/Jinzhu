package com.beyonditsm.financial.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.beyonditsm.financial.entity.PagerColor;
import com.beyonditsm.financial.fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 打工挣钱，圆环adapter
 * Created by wangbin on 15/12/13.
 */
public class WorkAdapter extends FragmentStatePagerAdapter{

    private Map<Integer, List<PagerColor>> map;

    public WorkAdapter(FragmentManager fm, Map<Integer, List<PagerColor>> map){
        super(fm);
        this.map=map;
    }
    @Override
    public Fragment getItem(int position) {
        WorkFragment fragment=new WorkFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("work_list", (ArrayList<? extends Parcelable>) map.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
