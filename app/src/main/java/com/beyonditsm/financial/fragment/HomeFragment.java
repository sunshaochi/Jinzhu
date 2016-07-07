package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.credit.CreditGuideAct;
import com.beyonditsm.financial.activity.user.CreditCardAct;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.adapter.HomeCreditAdapter;
import com.beyonditsm.financial.entity.HomeHotProductEntity;
import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.widget.gpscity.DialogChooseCity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//import com.baidu.location.Poi;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HomeFragment extends BaseFragment implements MyApplication.LocationChangeListener{
    @ViewInject(R.id.plv_hotCredit)
    private LoadRefreshView plvHotCredit;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    @ViewInject(R.id.ll_credit)
    private LinearLayout llCredit;
    @ViewInject(R.id.ll_tillage)
    private LinearLayout llTillage;
    @ViewInject(R.id.ll_work)
    private LinearLayout llWork;
    @ViewInject(R.id.ivSuspen)
    private ImageView ivSuspen;
    @ViewInject(R.id.tv_city)
    private TextView tvCity;
    private CreditFragment creditFragment;
    private int currentPage = 1;
    private HomeCreditAdapter adapter;
    private List<HomeHotProductEntity> hotList;
    private UserLoginEntity ule;
    private UserEntity user;
//    public LocationClient mLocationClient = null;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_home, null);

    }


    @Override
    public void onStart() {
//        loadingView.loading();
//        llWork.setClickable(false);
        getUserLoginInfo();
//        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
//        initLocation();
//        mLocationClient.registerLocationListener( new MyLocationListener());    //注册监听函数
//        mLocationClient.start();
        super.onStart();

    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        getHotProductList(currentPage);
        tvCity.setText(SpUtils.getCity(getContext()));
        String roleName = SpUtils.getRoleName(context);
        MyLogUtils.info("ROLENAME="+roleName);
//        if (!"ROLE_COMMON_CLIENT".equals(roleName)&&!TextUtils.isEmpty(roleName)){//普通用户显示贷款指南
//            ivSuspen.setBackgroundResource(R.mipmap.servant_guide);

//        }
        plvHotCredit.setPullRefreshEnabled(true);
        plvHotCredit.setScrollLoadEnabled(false);
        plvHotCredit.setPullLoadEnabled(true);
        plvHotCredit.setHasMoreData(true);
        plvHotCredit.getRefreshableView().setDivider(null);
        plvHotCredit.getRefreshableView().setVerticalScrollBarEnabled(false);
        plvHotCredit.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
        plvHotCredit.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
                currentPage = 1;
                getHotProductList(currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getHotProductList(currentPage);
            }
        });

//        MaterialRippleLayout.on(llCredit)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
//        MaterialRippleLayout.on(llTillage)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
//        MaterialRippleLayout.on(llWork)
//                .rippleColor(Color.parseColor("#919191"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .create();
        getHotProductList(currentPage);
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getHotProductList(currentPage);
            }
        });
    }

    @Override
    public void setListener() {
        plvHotCredit.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HomeCreditDetailAct.class);
                if(datas.size()>position){
                    intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME,datas.get(position).getProductName());
                }

//                intent.putExtra(HomeCreditDetailAct.CREDIT_MIN,ConstantValue.CREDIT_MIN_MONEY+"");
//                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME_MIN,ConstantValue.CREDIT_MIN_MONTH+"");
                intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY+"");
                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME,ConstantValue.CREDIT_MONTH+"");

                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.ll_credit, R.id.ll_tillage, R.id.ll_work,R.id.ivSuspen,R.id.ll_creditCard,R.id.ll_gps})
    public void toClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_credit://我要贷款
                EventBus.getDefault().post(new ToSwitchEvent());
                break;
            case R.id.ll_tillage://信用耕耘
                if(TextUtils.isEmpty(SpUtils.getRoleName(context).toString())){
                    Intent goLog = new Intent(context,LoginAct.class);
                    context.startActivity(goLog);
                }else{
                    intent = new Intent(getActivity(), GameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_work://打工挣钱
                if(!"".equals(SpUtils.getRoleName(getActivity()))) {
//                    intent = new Intent(getActivity(), NewWorkAct.class);//跳转打工挣钱页面
                    intent = new Intent(getActivity(), MyRecommAct.class);//跳转代言人页面
                    if(null!=ule && !"".equals(ule.getReferralCode())){
                        intent.putExtra("userLogin", ule);
                    }else {
                        intent.putExtra("userLogin", ParamsUtil.getInstance().getUle());
                    }

                    startActivity(intent);
                }else{
                    Intent goLog = new Intent(context,LoginAct.class);
                    context.startActivity(goLog);
                }
                break;
            case R.id.ivSuspen://浮窗
                intent=new Intent(getContext(), CreditGuideAct.class);
                startActivity(intent);
                break;
            case R.id.ll_creditCard://信用卡
                if(TextUtils.isEmpty(SpUtils.getRoleName(context).toString())){
                    MyToastUtils.showShortToast(getContext(),"请先登录金蛛账号");
                    Intent goLog = new Intent(context,LoginAct.class);
                    context.startActivity(goLog);
                }else{
                    intent = new Intent(getActivity(), CreditCardAct.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_gps://GPS
                DialogChooseCity dialogChooseAdress1 = new DialogChooseCity(context).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseCity.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress) {
                        if (adress.get(1).length()>4){
                            tvCity.setText(adress.get(1).substring(0,4)+"...");
                        }else {
                            tvCity.setText(adress.get(1));
                        }

//                        userInfo.setNativePlaceAddr(adress.get(0)+adress.get(1)+adress.get(2));
//                        updateData(userInfo, 2);
                    }
                });
                break;
        }
    }

    @Override
    public void onChange(boolean changed, String cityName) {
        if (changed){
            tvCity.setText(cityName);
        }
    }


    public class ToSwitchEvent{

    }

    private List<HomeHotProductEntity> datas = new ArrayList<>();
    private void getHotProductList(final int Page) {
        HotProduct hp = new HotProduct();
        hp.setPage(Page);
        hp.setRows(30);
        RequestManager.getUserManager().findHotProductList(hp, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadingView.loadComplete();
                plvHotCredit.onPullUpRefreshComplete();
                plvHotCredit.onPullDownRefreshComplete();

                JSONObject object = new JSONObject(result);
                JSONArray data = object.getJSONArray("data");
                Gson gson = new Gson();
                hotList = gson.fromJson(data.toString(), new TypeToken<List<HomeHotProductEntity>>() {
                }.getType());
                if (data == null) {
                    loadingView.noContent();
                    return;
                }
                if (hotList == null || hotList.size() == 0) {
                    if (Page == 1) {
                        loadingView.noContent();
                    } else {
                        plvHotCredit.setHasMoreData(false);
                    }
                    return;
                }
                if (Page == 1) {
                    datas.clear();
                }
                datas.addAll(hotList);
                if (adapter == null) {
                    if (null != getContext()){
                        adapter = new HomeCreditAdapter(getContext(), hotList);
                        plvHotCredit.getRefreshableView().setAdapter(adapter);
                    }
                } else {
                    adapter.setDatas(hotList);
                }
            }

            @Override
            public void onError(int status, String msg) {
                plvHotCredit.onPullUpRefreshComplete();
                plvHotCredit.onPullDownRefreshComplete();
                loadingView.loadError();
            }
        });
    }

    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {
        MyApplication.getInstance().getLocation();
        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                String a = result;
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200){
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                }

//                loadingView.loadComplete();
//                llWork.setClickable(true);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

}
