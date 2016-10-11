package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditGuideAct;
import com.beyonditsm.financial.activity.newscenter.NewsCenterActivity;
import com.beyonditsm.financial.activity.newscenter.NewsDetailActivity;
import com.beyonditsm.financial.activity.user.creditcard.CreditCardAct;
import com.beyonditsm.financial.activity.user.GameActivity;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.activity.user.LoginAct;
import com.beyonditsm.financial.activity.user.MyRecommAct;
import com.beyonditsm.financial.adapter.HomeCreditAdapter;
import com.beyonditsm.financial.adapter.HotNewsAdapter;
import com.beyonditsm.financial.entity.HomeHotProductEntity;
import com.beyonditsm.financial.entity.HotNewsEntity;
import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserLoginEntity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;
import com.beyonditsm.financial.util.Uitls;
import com.beyonditsm.financial.util.gps.GPSAddressUtils;
import com.beyonditsm.financial.util.gps.LocationListener;
import com.beyonditsm.financial.view.ListViewForScrollView;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.LoadRefreshView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.beyonditsm.financial.widget.GPSAlertDialog;
import com.beyonditsm.financial.widget.gpscity.DialogChooseCity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//import com.baidu.location.Poi;

/**
 * Created by liwk on 2015/12/8
 */
public class HomeFragment extends BaseFragment implements LocationListener {
    @ViewInject(R.id.plv_hotCredit)
    private ListView plvHotCredit;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    @ViewInject(R.id.tv_city)
    private TextView tvCity;
    @ViewInject(R.id.tv_checkMore)
    private TextView tvCheckMore;
    @ViewInject(R.id.lv_newsCenter)
    private ListView lvNewsCenter;
    @ViewInject(R.id.iv_firstNews)
    private ImageView ivFirstNews;
    @ViewInject(R.id.iv_secNews)
    private ImageView ivSecNews;
    private HotNewsEntity firstNews;
    private HotNewsEntity secNews;
    private int currentPage = 1;
    private HomeCreditAdapter adapter;
    private List<HomeHotProductEntity> hotList;
    private UserLoginEntity ule;
    private boolean productLoaded = false;
    private boolean newsLoaded = false;
    private Activity mParentActivity;
    private List<HotNewsEntity> hotNewsList;
    private HotNewsAdapter hotNewsAdapter;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

//    public LocationClient mLocationClient = null;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_home, null);

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden){
//
//        }
//    }

    @Override
    public void onStart() {

        super.onStart();
        if (ParamsUtil.getInstance().isFirstLocated()) {
            ParamsUtil.getInstance().setFirstLocated(false);
            getLocation();
        }
        if (ParamsUtil.getInstance().isReLogin()) {
            ParamsUtil.getInstance().setReLogin(false);
            getLocation();
        }
        getUserLoginInfo();

    }

    private void getLocation() {
        GPSAddressUtils.getInstance().setLocationListener(this);
        GPSAddressUtils.getInstance().getLocation(mParentActivity);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != ule) {
            outState.putParcelable("userLogin", ule);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState) {
            ule = savedInstanceState.getParcelable("userLogin");
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        getHotProductList(currentPage);

        String roleName = SpUtils.getRoleName(context);
        MyLogUtils.info("ROLENAME=" + roleName);
        plvHotCredit.setDivider(null);
        plvHotCredit.setVerticalScrollBarEnabled(false);
        plvHotCredit.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        plvHotCredit.setPullRefreshEnabled(true);
//        plvHotCredit.setScrollLoadEnabled(false);
//        plvHotCredit.setPullLoadEnabled(true);
//        plvHotCredit.setHasMoreData(true);
//        plvHotCredit.getRefreshableView().setDivider(null);
        plvHotCredit.setVerticalScrollBarEnabled(false);
//        plvHotCredit.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
//        plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
//        plvHotCredit.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                plvHotCredit.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
//                currentPage = 1;
//                getHotProductList(currentPage);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                currentPage++;
//                getHotProductList(currentPage);
//            }
//        });

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
        getNewsIndex();
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getNewsIndex();
                getHotProductList(currentPage);
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity)
            mParentActivity = (MainActivity) activity;
        if (mParentActivity == null)
            mParentActivity = MainActivity.getInstance();
    }

    @Override
    public void setListener() {
        plvHotCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mParentActivity, HomeCreditDetailAct.class);
                if (datas.size() > position) {
                    intent.putExtra(HomeCreditDetailAct.PRODUCTINFO, datas.get(position).getProductId());
                    intent.putExtra(HomeCreditDetailAct.CREDIT_NAME, datas.get(position).getProductName());
                }

//                intent.putExtra(HomeCreditDetailAct.CREDIT_MIN,ConstantValue.CREDIT_MIN_MONEY+"");
//                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME_MIN,ConstantValue.CREDIT_MIN_MONTH+"");
                intent.putExtra(HomeCreditDetailAct.CREDIT_AMOUNT, ConstantValue.CREDIT_MONEY + "");
                intent.putExtra(HomeCreditDetailAct.CREDIT_TIME, ConstantValue.CREDIT_MONTH + "");

                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.ll_credit, R.id.ll_tillage, R.id.ll_work, R.id.ivSuspen, R.id.ll_creditCard, R.id.ll_gps, R.id.tv_checkMore,R.id.iv_firstNews,R.id.iv_secNews})
    public void toClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_credit://我要贷款
                EventBus.getDefault().post(new ToSwitchEvent());
                break;
            case R.id.ll_tillage://信用耕耘
                if (TextUtils.isEmpty(SpUtils.getRoleName(context) + "")) {
                    Intent goLog = new Intent(context, LoginAct.class);
                    context.startActivity(goLog);
                } else {
                    intent = new Intent(mParentActivity, GameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_work://打工挣钱
                if (!"".equals(SpUtils.getRoleName(context))) {
//                    intent = new Intent(mParentActivity, NewWorkAct.class);//跳转打工挣钱页面
                    intent = new Intent(mParentActivity, MyRecommAct.class);//跳转代言人页面
                    if (null != ule && !"".equals(ule.getReferralCode())) {
                        intent.putExtra("userLogin", ule);
                    } else {
                        intent.putExtra("userLogin", ParamsUtil.getInstance().getUle());
                    }

                    startActivity(intent);
                } else {
                    Intent goLog = new Intent(context, LoginAct.class);
                    context.startActivity(goLog);
                }
                break;
            case R.id.ivSuspen://浮窗
                intent = new Intent(getContext(), CreditGuideAct.class);
                startActivity(intent);
                break;
            case R.id.ll_creditCard://信用卡
//                MyToastUtils.showShortToast(getContext(), "敬请期待");
                if (TextUtils.isEmpty(SpUtils.getRoleName(context).toString())) {
                    MyToastUtils.showShortToast(getContext(), "请先登录金蛛账号");
                    Intent goLog = new Intent(context, LoginAct.class);
                    context.startActivity(goLog);
                } else {
                    intent = new Intent(mParentActivity, CreditCardAct.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_checkMore://咨询中心查看更多
                intent = new Intent(mParentActivity, NewsCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_gps://GPS
                DialogChooseCity dialogChooseAdress1 = new DialogChooseCity(context).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseCity.SexClickListener() {
                    @Override
                    public void getAdress(final List<String> adress) {
                        MyLogUtils.info("选择的地址:" + adress.get(1));

                        String city = SpUtils.getCity(getContext());
                        if (addressChange(city, adress.get(1))) {
                            GPSAlertDialog gpsAlertDialog = new GPSAlertDialog(context);
                            gpsAlertDialog.builder().setCancelable(false).setMsg("您目前所在的区域更改", "是否将所在的城市切换为：", adress.get(1)).setPositiveButton("确认切换", new View.OnClickListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onClick(View v) {
                                    if (!"".equals(SpUtils.getRoleName(mParentActivity))) {
                                        updateLocation(adress.get(1));
                                    }
                                    if (adress.get(1).length() > 4) {
                                        tvCity.setText(adress.get(1).substring(0, 4) + "...");
                                    } else {
                                        tvCity.setText(adress.get(1));
                                    }
                                    SpUtils.setCity(MyApplication.getInstance().getApplicationContext(), adress.get(1));
                                    currentPage = 1;
                                    getHotProductList(currentPage);
                                }
                            }).setNegativeButton("取消", null).show();

                        }
//                        userInfo.setNativePlaceAddr(adress.get(0)+adress.get(1)+adress.get(2));
//                        updateData(us
                    }
                });
                break;
            case R.id.iv_firstNews:
                Intent intent2 = new Intent(mParentActivity, NewsDetailActivity.class);
                intent2.putExtra("hotnews",firstNews);
                startActivity(intent2);
                break;
            case R.id.iv_secNews:
                Intent intent3 = new Intent(mParentActivity, NewsDetailActivity.class);
                intent3.putExtra("hotnews",secNews);
                startActivity(intent3);
                break;
        }
    }


    @Override
    public void onChanged(boolean isGet, String city) {
        ParamsUtil.getInstance().setChangedCity(city);
        ParamsUtil.getInstance().setCityGet(isGet);
        initLocation();
    }

    public class ToSwitchEvent {

    }

    private List<HomeHotProductEntity> datas = new ArrayList<>();

    private void getHotProductList(final int Page) {
        HotProduct hp = new HotProduct();
        hp.setPage(Page);
        hp.setRows(30);
        RequestManager.getUserManager().findHotProductList(hp, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                productLoaded = true;
                if (newsLoaded){
                    loadingView.loadComplete();
                }
//                plvHotCredit.onPullUpRefreshComplete();
//                plvHotCredit.onPullDownRefreshComplete();

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
                    }
//                    else {
////                        plvHotCredit.setHasMoreData(false);
//                    }
                    return;
                }
                if (Page == 1) {
                    datas.clear();
                }
                datas.addAll(hotList);
                if (adapter == null) {
                    if (null != getContext()) {
                        adapter = new HomeCreditAdapter(getContext(), datas);
                        plvHotCredit.setAdapter(adapter);
                        Uitls.setListViewHeightBasedOnChildren(plvHotCredit);
                    }
                } else {
                    adapter.setDatas(datas);
                    Uitls.setListViewHeightBasedOnChildren(plvHotCredit);
                }
            }

            @Override
            public void onError(int status, String msg) {
//                plvHotCredit.onPullUpRefreshComplete();
//                plvHotCredit.onPullDownRefreshComplete();
                productLoaded = false;
                loadingView.loadError();
            }
        });
    }


    /**
     * 获取用户的角色信息
     */
    private void getUserLoginInfo() {

        RequestManager.getUserManager().findUserLoginInfo(new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("status");
                if (status == 200) {
                    ResultData<UserLoginEntity> rd = (ResultData<UserLoginEntity>) GsonUtils.json(result, UserLoginEntity.class);
                    ule = rd.getData();
                    ParamsUtil.getInstance().setUle(ule);
                }

                loadingView.loadComplete();
//                llWork.setClickable(true);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    private boolean addressChange(String locationCity, String selectCity) {
        return !locationCity.equals(selectCity);
    }

    private void initLocation() {
//        if (!TextUtils.isEmpty(ParamsUtil.getInstance().getChangedCity())) {
//            tvCity.setText(ParamsUtil.getInstance().getChangedCity());
//            SpUtils.setCity(MyApplication.getInstance().getApplicationContext(), ParamsUtil.getInstance().getChangedCity());
//        }

//        tvCity.setText("——");
//        MyLogUtils.info("是否是定位获取城市："+ParamsUtil.getInstance().getChangedCity());
        if (ParamsUtil.getInstance().isCityGet()) {
//            MyLogUtils.info("getCity:" + SpUtils.getCity(MyApplication.getInstance().getApplicationContext()) + "," + ParamsUtil.getInstance().getChangedCity());
            if (!TextUtils.isEmpty(SpUtils.getCity(MyApplication.getInstance().getApplicationContext())) && !TextUtils.isEmpty(ParamsUtil.getInstance().getChangedCity())) {
                if (SpUtils.getCity(MyApplication.getInstance().getApplicationContext()).equals(ParamsUtil.getInstance().getChangedCity())) {
                    tvCity.setText(ParamsUtil.getInstance().getChangedCity());
                    currentPage = 1;
                    getHotProductList(currentPage);
                } else {
                    GPSAlertDialog gpsAlertDialog = new GPSAlertDialog(context);
                    gpsAlertDialog.builder().setCancelable(false).setMsg("您目前所处区域发生变更", "是否将所在城市切换为", ParamsUtil.getInstance().getChangedCity()).setPositiveButton("确认切换", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!"".equals(SpUtils.getRoleName(mParentActivity))) {
                                updateLocation(ParamsUtil.getInstance().getChangedCity());
                            }
                            tvCity.setText(ParamsUtil.getInstance().getChangedCity());
                            SpUtils.setCity(MyApplication.getInstance().getApplicationContext(), ParamsUtil.getInstance().getChangedCity());
                            currentPage = 1;
                            getHotProductList(currentPage);
                        }
                    }).setNegativeButton("取消", null).show();
                    tvCity.setText(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));

                }
            }

        } else {
            tvCity.setText("——");
            GPSAlertDialog gpsAlertDialog = new GPSAlertDialog(context);
            gpsAlertDialog.builder().setCancelable(false).setMsg("无法获取当前位置，请检查设置", "或直接切换城市", null).setPositiveButton("去设置", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParamsUtil.getInstance().setFirstLocated(true);
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }).setNegativeButton("知道了", null).show();
        }
    }

    private void updateLocation(String area) {
        RequestManager.getCommManager().updateLocation(area, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    public void getNewsIndex() {
        CommManager.getCommManager().findNewsMobileIndex(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                newsLoaded = true;
                if (productLoaded){
                    loadingView.loadComplete();
                }
                JSONObject object = new JSONObject(result);
                JSONArray data = object.getJSONArray("data");
                Gson gson = new Gson();

                hotNewsList = gson.fromJson(data.toString(), new TypeToken<List<HotNewsEntity>>() {
                }.getType());

                if (hotNewsList == null || hotNewsList.size() == 0) {
//                    adapter.setDatas(datas ,isLast(cardList));
//                    adapter.setOnCreditCardListner(CreditCardAct.this);
//                    adapter.notifyDataSetChanged();
                    loadingView.noContent();
                    return;
                }
                for (int i = 0; i < hotNewsList.size(); i++) {
                    MyLogUtils.info("Weights:" + hotNewsList.get(i).getWeights()+"");
                    if (hotNewsList.get(i).getWeights() == 6) {
                        MyLogUtils.info("BASE_IMAGE_URL:" + hotNewsList.get(i).getPictrue()+"");
                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + hotNewsList.get(i).getPictrue(), ivFirstNews, options);
                        firstNews = hotNewsList.get(i);
                        hotNewsList.remove(i);
                        i--;
                    } else if (hotNewsList.get(i).getWeights() == 7) {
                        MyLogUtils.info("BASE_IMAGE_URL:" + hotNewsList.get(i).getPictrue()+"");
                        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + hotNewsList.get(i).getPictrue(), ivSecNews, options);
                        secNews = hotNewsList.get(i);
                        hotNewsList.remove(i);
                    }
                }
                if (hotNewsAdapter == null) {
                    hotNewsAdapter = new HotNewsAdapter(mParentActivity, hotNewsList);
                    lvNewsCenter.setAdapter(hotNewsAdapter);
                    Uitls.setListViewHeightBasedOnChildren(lvNewsCenter);
//                    hotNewsAdapter.setOnCreditCardListner(mParentActivity);

                } else {
                    hotNewsAdapter.setDatas(hotNewsList);
//                    hotNewsAdapter.setOnCreditCardListner(mParentActivity);
                    hotNewsAdapter.notifyDataSetChanged();
                    Uitls.setListViewHeightBasedOnChildren(lvNewsCenter);
                }

                lvNewsCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(mParentActivity, NewsDetailActivity.class);
                        intent.putExtra("hotnews",hotNewsList.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int status, String msg) {
                newsLoaded = false;
                loadingView.loadError();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ParamsUtil.getInstance().setFirstLocated(true);
    }
}
