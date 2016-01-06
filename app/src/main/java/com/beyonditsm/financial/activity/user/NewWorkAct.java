package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.WorkAdapter;
import com.beyonditsm.financial.entity.PagerColor;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UpServantEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.RoundProgressBar;
import com.beyonditsm.financial.view.viewpagerindicator.CirclePageIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新打工挣钱界面
 * Created by Yang on 2015/12/10 0010.
 */
public class NewWorkAct extends BaseActivity {
    @ViewInject(R.id.budgetpic_indicator)
    private CirclePageIndicator indicator;// 轮播图点
    @ViewInject(R.id.myViewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.rl_viewpager)
    private RelativeLayout rlViewpager;
    @ViewInject(R.id.btn_tobe_service)
    private Button service;
    @ViewInject(R.id.content)
    private TextView content;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.single_two)
    private LinearLayout single_two;//服务者往上升级单独的两个圆形进度外围布局
    @ViewInject(R.id.up_name)
    private TextView up_name;//顶部圆形进度条中间内容
    @ViewInject(R.id.up_content)
    private TextView up_content;//顶部圆形进度条底部描述
    @ViewInject(R.id.up_progress)
    private RoundProgressBar up_progress;//顶部圆形进度条
    @ViewInject(R.id.down_name)
    private TextView down_name;//底部圆形进度条中间内容
    @ViewInject(R.id.down_content)
    private TextView down_content;//底部圆形进度条底部描述
    @ViewInject(R.id.down_progress)
    private RoundProgressBar down_progress;//底部圆形进度条
    private int primary, middle, senior, recommend, credit, customer;
    private Map<Integer, List<PagerColor>> datas = new HashMap<>();
    private List<PagerColor> list1 = new ArrayList<>();
    private List<PagerColor> list2 = new ArrayList<>();
    private List<PagerColor> list3 = new ArrayList<>();
    private WorkAdapter adapter;

    public static final String ROLE="role";//角色
    private String role;
    private String isApproving;
    private int sumLoan;

    private int i=0;
    private int j=0;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_newwork);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setTopTitle("打工挣钱");
        setLeftTv("返回");
//        FinalLoadDialog dialog=new FinalLoadDialog(this);
//        dialog.show();
//        role=getIntent().getStringExtra(ROLE);
        role= SpUtils.getRoleName(this);
        if(role==null){
            getUserLoginInfo();
        }else{
            if("ROLE_COMMON_CLIENT".equals(role)){
                uptoServant("primary_servant");
            }
            else if("ROLE_SERVANT_PRIMARY".equals(role)){
                rlViewpager.setVisibility(View.GONE);
                single_two.setVisibility(View.VISIBLE);
                uptoMiddleServant("middle_servant");
            }else if("ROLE_SERVANT_MIDDLE".equals(role)){
                rlViewpager.setVisibility(View.GONE);
                single_two.setVisibility(View.VISIBLE);
                uptoMiddleServant("senior_servant");
            }
        }
        loadView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getUserLoginInfo();
            }
        });
    }



    private void uptoMiddleServant(final String servantRoleType){
        RequestManager.getUserManager().uptoServant(servantRoleType, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                UpServantEntity ue = GsonUtils.json2Bean(result, UpServantEntity.class);
                UpServantEntity.DataEntity data = ue.getData();

                sumLoan = data.get_sumLoan();
                recommend = data.get_recommendCount();//推荐
                isApproving = data.getIsApproving();//是否在审核中

//                up_progress.setCricleProgressColor(R.color.up_progress);
                up_progress.setProgress(recommend);
                up_name.setText("推荐好友");
                if ("senior_servant".equals(servantRoleType)){
                    up_content.setText("推荐好友注册成功30个及以上");
                }else if ("middle_servant".equals(servantRoleType)) {
                    up_content.setText("推荐好友注册成功10个及以上");
                }

//                down_progress.setCricleProgressColor(R.color.down_progress);
                down_progress.setProgress(sumLoan);
                down_name.setText("授信额");
                if ("senior_servant".equals(servantRoleType)){
                    down_content.setText("授信额达到100万及以上");
                }else if ("middle_servant".equals(servantRoleType)) {
                    down_content.setText("授信额达到30万及以上");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (i<recommend) {
                            i++;
                            Message message = handler.obtainMessage();
                            message.what=0;
                            message.arg1 = i;
                            handler.sendMessage(message);
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (j<sumLoan) {
                            j++;
                            Message message = handler.obtainMessage();
                            message.what=1;
                            message.arg2 = j;
                            handler.sendMessage(message);

                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                service.setText("提升服务者等级");
                content.setText("初级升中级，推荐好友数达到10人以及授信额总数达到30万\n"+"中级升高级，推荐好友数达到30人以及授信额总数达到100万");
                String totalStatus=data.getTotalStatus();
                MyLogUtils.info("是否可升级：" + totalStatus + ";是否在审核：" + isApproving);
                if ("true".equals(totalStatus)) {
                    if("false".equals(isApproving)){
                        service.setBackgroundResource(R.drawable.btn_submit);
                        service.setEnabled(true);
                    }else {
                        service.setBackgroundResource(R.drawable.graborder_btn_bg);
                        service.setEnabled(false);
                        service.setText("正在审核中");
                    }
                } else {
                    service.setBackgroundResource(R.drawable.graborder_btn_bg);
                    service.setEnabled(false);
                }
            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
            }
        });
    }
    /**
     * 获取升级服务者信息
     *
     * @param servantRoleType
     */
    private void uptoServant(String servantRoleType) {
        RequestManager.getUserManager().uptoServant(servantRoleType, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                UpServantEntity ue = GsonUtils.json2Bean(result, UpServantEntity.class);
                UpServantEntity.DataEntity data = ue.getData();

                primary = data.get_primaryTaskCt();//初级
                middle = data.get_middleTaskCt();//中级
                senior = data.get_seniorTaskCt();//高级
                recommend = data.get_recommendCount();//推荐
                credit = data.get_creditScoreInt();//信用
                customer = data.get_customerMustAddInt();//基础
                isApproving = data.getIsApproving();//是否在审核中

                PagerColor pagerColor = new PagerColor();
                pagerColor.setColor("#ff8383");
                pagerColor.setProgress(recommend);
                pagerColor.setTv("推荐好友注册成功10个及以上");
                pagerColor.setTvname("推荐好友数");
                list1.add(pagerColor);
                PagerColor pagerColor1 = new PagerColor();
                pagerColor1.setColor("#83ccff");
                pagerColor1.setProgress(credit);
                pagerColor1.setTv("平台信用分大于等于500分");
                pagerColor1.setTvname("信用分达标");
                list1.add(pagerColor1);
                datas.put(0, list1);
                PagerColor pagerColor2 = new PagerColor();
                pagerColor2.setColor("#3ad779");
                pagerColor2.setProgress(customer);
                pagerColor2.setTv("基础任务（必填项）全部完成");
                pagerColor2.setTvname("必填资料");
                list2.add(pagerColor2);
                PagerColor pagerColor3 = new PagerColor();
                pagerColor3.setColor("#f8955a");
                pagerColor3.setProgress(primary);
                pagerColor3.setTv("初级任务完成3个及以上");
                pagerColor3.setTvname("初级任务");
                list2.add(pagerColor3);
                datas.put(1, list2);
                PagerColor pagerColor4 = new PagerColor();
                pagerColor4.setColor("#7ab3ff");
                pagerColor4.setProgress(middle);
                pagerColor4.setTv("中级任务完成3个及以上");
                pagerColor4.setTvname("中级任务");
                list3.add(pagerColor4);
                PagerColor pagerColor5 = new PagerColor();
                pagerColor5.setColor("#ff88a4");
                pagerColor5.setProgress(senior);
                pagerColor5.setTv("高级任务完成3个及以上");
                pagerColor5.setTvname("高级任务");
                list3.add(pagerColor5);
                datas.put(2, list3);
                adapter = new WorkAdapter(getSupportFragmentManager(), datas);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(0);
                indicator.setViewPager(viewPager);
//                adapter = new MyViewPager();
//                viewPager.setAdapter(adapter);
//                indicator.setViewPager(viewPager);
                String totalStatus=data.getTotalStatus();
                MyLogUtils.info("是否可升级：" + totalStatus+";是否在审核："+isApproving);
                if ("true".equals(totalStatus)) {
                    if("false".equals(isApproving)){
                        service.setBackgroundResource(R.drawable.btn_submit);
                        service.setEnabled(true);
                    }else {
                        service.setBackgroundResource(R.drawable.graborder_btn_bg);
                        service.setEnabled(false);
                        service.setText("正在审核中");
                    }
                } else {
                    service.setBackgroundResource(R.drawable.graborder_btn_bg);
                    service.setEnabled(false);
                }

            }

            @Override
            public void onError(int status, String msg) {
                loadView.loadError();
            }
        });
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo(){
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                UserLoginEntity ule = rd.getData();
                role=ule.getRoleName();
                if("ROLE_COMMON_CLIENT".equals(role)){
                    uptoServant("primary_servant");
                }else if("ROLE_SERVANT_PRIMARY".equals(role)){
                    uptoMiddleServant("middle_servant");
                    rlViewpager.setVisibility(View.GONE);
                    single_two.setVisibility(View.VISIBLE);
                }else if("ROLE_SERVANT_MIDDLE".equals(role)){
                    uptoMiddleServant("senior_servant");
                    rlViewpager.setVisibility(View.GONE);
                    single_two.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    up_progress.setProgress(msg.arg1);
                    break;
                case 1:
                    down_progress.setProgress(msg.arg2);
                    break;
            }

        }
    };
}
