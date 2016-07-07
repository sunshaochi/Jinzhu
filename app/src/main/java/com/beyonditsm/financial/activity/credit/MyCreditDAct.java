package com.beyonditsm.financial.activity.credit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.servicer.ServiceMainAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.fragment.MyCreditDetailFragment;
import com.beyonditsm.financial.fragment.MyCreditStatusFragment;
import com.beyonditsm.financial.fragment.ServiceMineFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.tablayout.SlidingTabLayout;
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
    @ViewInject(R.id.tl_creditDetail)
    SlidingTabLayout tlCreditDetail;
    private ImageView[] imageArras;
    private TextView[] textArras;
    private LinearLayout[] linearArras;
    private MyCreditBean.RowsEntity rowe;
    private int position;

    private int screenWidth;
    private final String[] mTitles = {"贷款状态", "贷款详情"};

    @Override
    public void setLayout() {
        setContentView(R.layout.mycredit_detail);
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public void init(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        setLeftTv("返回");
        setTopTitle("贷款详情");
        rowe = getIntent().getParcelableExtra(MyCreditAct.CREDIT);
        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
        MyLogUtils.info("获取到已保存的orderID+" + orderId);
        initViewpager();
        if (!TextUtils.isEmpty(orderId)) {
            if (orderId.equals(rowe.getId())) {
//                tlCreditDetail.setMsgMargin(1,60,10);
                tlCreditDetail.showDot(1);
            }
        } else {
            tlCreditDetail.hideMsg(1);
        }
        position = getIntent().getIntExtra("position", 0);

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
        MyLogUtils.info("重新获取到已保存的orderID+" + orderId);
        if (!TextUtils.isEmpty(orderId)) {
            if (orderId.equals(rowe.getId())) {
//                tlCreditDetail.setMsgMargin(1,30,10);
                tlCreditDetail.showDot(0);
            }
        } else {
            tlCreditDetail.hideMsg(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final MyCreditDetailFragment.PatchEvent event) {
        if (event._type == 1) {
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
        } else {
            if ("CANCEL_REQUET".equals(rowe.getOrderSts())) {
                setRightVG(false);
            } else if ("WAIT_BACKGROUND_APPROVAL".equals(rowe.getOrderSts()) || "CREDIT_MANAGER_GRAB".equals(rowe.getOrderSts())) {
                setRightBtn("取消订单", new View.OnClickListener() {
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
                        MyAlertDialog dialog = new MyAlertDialog(MyCreditDAct.this);
                        dialog.builder().setTitle("提示").setMsg("确认取消订单？").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
        MyCreditDetailFragment detailFragment = new MyCreditDetailFragment();
        statusFragment.setArguments(bundle);
        detailFragment.setArguments(bundle);
        fragmentList.add(statusFragment);
        myCreditViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        myCreditViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearRedPoint();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCreditViewpager.setCurrentItem(0);
        tlCreditDetail.setViewPager(myCreditViewpager);
    }

    private void clearRedPoint() {
        SpUtils.clearOrderId(MyApplication.getInstance());
        ivDetailRedPoint.setVisibility(View.INVISIBLE);
        ivStatusRedPoint.setVisibility(View.INVISIBLE);
        tlCreditDetail.hideMsg(1);
        sendBroadcast(new Intent(MyCreditAct.HIDE_MESSAGE));
        sendBroadcast(new Intent(MineFragment.HIDE_POINT));
        sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
        sendBroadcast(new Intent(ServiceMineFrg.HIDE_RED_POINT));
        sendBroadcast(new Intent(ServiceMainAct.HIDE));
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        private List<Fragment> fragmentList = null;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }

}
