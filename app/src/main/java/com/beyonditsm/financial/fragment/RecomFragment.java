package com.beyonditsm.financial.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.CreditAdapter;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.ProductResult;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.CommonView;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;

import java.util.List;

/**推荐fragment
 * Created by bitch-1 on 2016/11/28.
 */

public class RecomFragment extends BaseFragment {

    @ViewInject(R.id.frem_back)
    private RelativeLayout frem_back;

    private TjfirstFragment tjfirstfrg;//推荐第一步fragment
    private TjlistFragment tjlistFragment;//推荐第二不list

    private FragmentManager fragmentManager;
    private myBroadcastReceiver receiver;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_recomfrg, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        fragmentManager =getActivity().getSupportFragmentManager();
//        EventBus.getDefault().register(getActivity());
        setTabSelection(0);


    }
    //选择不同的fra显示
    private void setTabSelection(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//拿到对象
        hideFragments(fragmentTransaction);//隐藏所有对象
        switch (i){
            case 0:
                frem_back.setVisibility(View.GONE);
                if(tjfirstfrg==null){
                    tjfirstfrg=new TjfirstFragment();
                    fragmentTransaction.add(R.id.fl_main,tjfirstfrg);
                }else {
                    fragmentTransaction.show(tjfirstfrg);
                }
                break;
            case 1:
                frem_back.setVisibility(View.VISIBLE);
                frem_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTabSelection(0);
                    }
                });
                if(tjlistFragment==null){
                    tjlistFragment=new TjlistFragment();
                    fragmentTransaction.add(R.id.fl_main,tjlistFragment);
                }else {
                    fragmentTransaction.show(tjlistFragment);
                }
                break;
        }

        fragmentTransaction.commit();

    }
    //隐藏所有对象
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if(tjfirstfrg!=null){
            fragmentTransaction.hide(tjfirstfrg);
        }
        if(tjlistFragment!=null){
            fragmentTransaction.hide(tjlistFragment);
        }

    }


    @Override
    public void setListener() {

    }

    public static final String CHANGE ="tjfirstfrgment";

    public class myBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            setTabSelection(1);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(receiver==null){
            receiver=new myBroadcastReceiver();
        }
        getActivity().registerReceiver(receiver,new IntentFilter(CHANGE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }
    }

    //    //从推荐第一步传递post的参数
//    public static class FirstEvent {
//
//    }
//    //重写event方法处理
//    public void onEvent(){
//        setTabSelection(1);//显示推荐第二部frament
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(!EventBus.getDefault().isRegistered(this)){//加上判断
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (EventBus.getDefault().isRegistered(this))//加上判断
//            EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
}
