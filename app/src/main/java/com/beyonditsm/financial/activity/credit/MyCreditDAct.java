package com.beyonditsm.financial.activity.credit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.fragment.MyCreditDetailFragment;
import com.beyonditsm.financial.fragment.MyCreditStatusFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.widget.DialogHint;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的贷款详情
 * Created by wangbin on 15/11/13.
 */
public class MyCreditDAct extends BaseActivity {
//    @ViewInject(R.id.start_msg)
//    private TextView start;//开启聊天

    @ViewInject(R.id.mycredit_viewpager)
    private ViewPager myCreditViewpager;
    @ViewInject(R.id.iv_statusRedPoint)
    private ImageView ivStatusRedPoint;
    @ViewInject(R.id.iv_detailRedPoint)
    private ImageView ivDetailRedPoint;

    private ImageView[] imageArras;
    private TextView[] textArras;
    private LinearLayout[] linearArras;
    private MyCreditBean.RowsEntity rowe;
    private int position;
    private HideBoradcastReceiver hideReceiver;


    @Override
    public void setLayout() {
        setContentView(R.layout.mycredit_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setLeftTv("返回");
        setTopTitle("贷款详情");
        rowe = getIntent().getParcelableExtra(MyCreditAct.CREDIT);
        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
        MyLogUtils.info("获取到已保存的orderID+"+orderId);
        if (!TextUtils.isEmpty(orderId)){
            if (orderId.equals(rowe.getId())){
                ivDetailRedPoint.setVisibility(View.VISIBLE);
                ivStatusRedPoint.setVisibility(View.VISIBLE);
            }
        }else{
            ivDetailRedPoint.setVisibility(View.INVISIBLE);
            ivStatusRedPoint.setVisibility(View.INVISIBLE);
        }
        position = getIntent().getIntExtra("position", 0);

        initTextView();
        initImageView();
        initViewpager();



//        textArras[0].setTextColor(getResources().getColor(R.color.main_color));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hideReceiver==null) {
            hideReceiver = new HideBoradcastReceiver();
        }
        registerReceiver(hideReceiver,new IntentFilter(HIDE_RED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (hideReceiver!=null){
            unregisterReceiver(hideReceiver);
        }
    }

    public void onEvent(final MyCreditDetailFragment.PatchEvent event){
        if(event._type==1) {
            setRightBtn("补件说明", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SpUtils.clearOrderId(MyApplication.getInstance());
//                    ivDetailRedPoint.setVisibility(View.INVISIBLE);
//                    ivStatusRedPoint.setVisibility(View.INVISIBLE);
//                    sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
//                    sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
//                    sendBroadcast(new Intent(MineFragment.HIDE_POINT));
                    DialogHint dialogHint = new DialogHint(MyCreditDAct.this, event._remark).builder();
                    dialogHint.show();
                }
            });
        }else {
            if ("CANCEL_REQUET".equals(rowe.getOrderSts())){
                setRightVG(false);
            }else if ("WAIT_BACKGROUND_APPROVAL".equals(rowe.getOrderSts())||"CREDIT_MANAGER_GRAB".equals(rowe.getOrderSts())){
                setRightBtn("取消订单", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAlertDialog dialog = new MyAlertDialog(MyCreditDAct.this);
                        dialog.builder().setTitle("提示").setMsg("确认取消订单？").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RequestManager.getUserManager().cancelOrder(rowe.getId(), new RequestManager.CallBack() {
                                    @Override
                                    public void onSucess(String result) throws JSONException {

                                        JSONObject object = new JSONObject(result);
                                        int status = object.getInt("status");
                                        String message = object.getString("message");
                                        if (status == 200) {
                                            MyToastUtils.showShortToast(MyCreditDAct.this, message);
                                            Intent intent = new Intent(MyCreditAct.CREDIT_RECEIVER);
                                            intent.putExtra("position", position);
                                            sendBroadcast(intent);
//                                            SpUtils.clearOrderId(MyApplication.getInstance());
//                                            sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
//                                            sendBroadcast(new Intent(MineFragment.HIDE_POINT));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onError(int status, String msg) {
                                        MyToastUtils.showShortToast(MyCreditDAct.this, msg);
                                    }
                                });
                            }
                        }).setNegativeButton("取消", null).show();

                    }
                });
            }
        }
    }

    private void initViewpager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("rowe", rowe);
        MyCreditStatusFragment statusFragment = new MyCreditStatusFragment();
        MyCreditDetailFragment detailFragment =new MyCreditDetailFragment();
        statusFragment.setArguments(bundle);
        detailFragment.setArguments(bundle);
        fragmentList.add(statusFragment);
        fragmentList.add(detailFragment);
        myCreditViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        myCreditViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<linearArras.length;i++){
//                    textArras[i].setTextColor(getResources().getColor(R.color.tv_second_color));
                    imageArras[i].setBackgroundColor(Color.TRANSPARENT);
                }
//                textArras[position].setTextColor(getResources().getColor(R.color.main_color));
                imageArras[position].setBackgroundColor(getResources().getColor(R.color.main_color));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCreditViewpager.setCurrentItem(0);
    }

    private void initImageView() {
        LinearLayout tabTextLayout = (LinearLayout) findViewById(R.id.tabImageLayout);
        imageArras = new ImageView[2];
        for (int i =0;i< imageArras.length;i++){
            ImageView imageView = (ImageView) tabTextLayout.getChildAt(i);
            imageArras[i] = imageView;
            imageArras[i].setTag(i);
            imageArras[i].setBackgroundColor(Color.TRANSPARENT);
        }
        imageArras[0].setBackgroundColor(getResources().getColor(R.color.main_color));
    }

    private void initTextView() {
        LinearLayout tabTextLayout = (LinearLayout) findViewById(R.id.tabTextLayout);
//        textArras = new TextView[2];
        linearArras = new LinearLayout[2];
        for (int i =0;i< linearArras.length;i++){
//            TextView textView = (TextView) tabTextLayout.getChildAt(i);
            LinearLayout linearLayout = (LinearLayout) tabTextLayout.getChildAt(i);
//            textArras[i] = textView;
            linearArras[i] = linearLayout;
//            textArras[i].setTag(i);
//            textArras[i].setEnabled(true);
//            textArras[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    myCreditViewpager.setCurrentItem((Integer) v.getTag());
//                }
//            });
            linearArras[i].setEnabled(true);
            linearArras[i].setTag(i);
            linearArras[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCreditViewpager.setCurrentItem((Integer) v.getTag());
                }
            });
        }
    }
    class  MyAdapter extends FragmentStatePagerAdapter{

        private FragmentManager fm;
        private List<Fragment> fragmentList =null;
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm,List<Fragment> fragmentList){
            super(fm);
            this.fm = fm;
            this.fragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }
    public static final String HIDE_RED = "hide_redpoint";
    private class HideBoradcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ivStatusRedPoint.setVisibility(View.INVISIBLE);
            ivDetailRedPoint.setVisibility(View.INVISIBLE);
        }
    }
}
