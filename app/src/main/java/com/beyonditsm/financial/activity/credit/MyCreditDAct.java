package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.entity.MyCreditBean;
import com.beyonditsm.financial.entity.OrderListBean;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.fragment.MyCreditDetailFragment;
import com.beyonditsm.financial.fragment.MyCreditStatusFragment;
import com.beyonditsm.financial.fragment.SpeedCreditDetailFragment;
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
    @ViewInject(R.id.tl_creditDetail)
    SlidingTabLayout tlCreditDetail;
    private MyCreditBean.RowsEntity rowe;
    private int position;

    private OrderListBean orderListBean;

    private final String[] mTitles = {"贷款状态", "贷款详情"};
//    private String type;

    @Override
    public void setLayout() {
        setContentView(R.layout.mycredit_detail);
//        WindowManager wm = (WindowManager)
//                getSystemService(Context.WINDOW_SERVICE);
//
//        int screenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public void init(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        setLeftTv("返回");
        setTopTitle("贷款详情");
//        type = getIntent().getStringExtra("type");
//        rowe = getIntent().getParcelableExtra(MyCreditAct.CREDIT);
        orderListBean=getIntent().getParcelableExtra("orderListBean");//传递过来的单一实体
        String orderId = SpUtils.getOrderId(MyApplication.getInstance());
        MyLogUtils.info("获取到已保存的orderID+" + orderId);
        initViewpager();
        if (!TextUtils.isEmpty(orderId)) {
            if (orderId.equals(orderListBean.getOrder().getOrderId())) {
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
            if (orderId.equals(orderListBean.getOrder().getOrderId())) {
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
//        if (event._type == 1) {
//            setRightBtn("补件说明", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    SpUtils.clearOrderId(MyApplication.getInstance());
////                    ivDetailRedPoint.setVisibility(View.INVISIBLE);
////                    ivStatusRedPoint.setVisibility(View.INVISIBLE);
////                    sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
////                    sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
////                    sendBroadcast(new Intent(MineFragment.HIDE_POINT));
//                    DialogHint dialogHint = new DialogHint(MyCreditDAct.this, event._remark).builder();
//                    dialogHint.show();
//                }
//            });
//        } else {
//            if ("CANCEL_REQUET".equals(orderListBean.getOrder().getOrderStatus())) {
//                setRightVG(false);
//            } else if ("WAIT_BACKGROUND_APPROVAL".equals(orderListBean.getOrder().getOrderStatus()) || "CREDIT_MANAGER_GRAB".equals(orderListBean.getOrder().getOrderStatus())) {
////                setCancel();//取消订单不需要了
//            }
//        }
    }

    public void setCancel() {
        setRightBtn("取消订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog dialog = new MyAlertDialog(MyCreditDAct.this);
                dialog.builder().setTitle("提示").setMsg("确认取消订单？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestManager.getUserManager().cancelOrder(orderListBean.getOrder().getOrderId(), new RequestManager.CallBack() {
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

    @SuppressWarnings("deprecation")
    private void initViewpager() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderListBean", orderListBean);
        Fragment detailFragment;
        if (orderListBean.getOrder().getOrderType().equals("4")) {//急借通
            detailFragment = new SpeedCreditDetailFragment();
        } else {
            detailFragment = new MyCreditDetailFragment();//贷款详情
            MyCreditStatusFragment statusFragment = new MyCreditStatusFragment();//贷款状态
            statusFragment.setArguments(bundle);
            fragmentList.add(statusFragment);
        }

        detailFragment.setArguments(bundle);

//        if ("comm".equals(type)) {
//            fragmentList.add(detailFragment);
//        } else if ("speed".equals(type)) {
            fragmentList.add(detailFragment);
//        }
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
        tlCreditDetail.hideMsg(1);
        tlCreditDetail.hideMsg(0);
        sendBroadcast(new Intent(MyCreditAct.HIDE_MESSAGE));
        sendBroadcast(new Intent(MineFragment.HIDE_POINT));
        sendBroadcast(new Intent(MainActivity.HIDE_REDPOINT));
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        private List<Fragment> fragmentList = null;

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
