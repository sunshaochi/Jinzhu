package com.beyonditsm.financial.util.gps;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.fragment.HomeFragment;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.util.SpUtils;

/**
 * Created by xuleyuan on 2016/6/21.
 */
public class GPSAddressUtils {

    private static GPSAddressUtils gpsAddressUtils;

    public LocationClient mLocationClient = new LocationClient(MyApplication.getInstance().getApplicationContext());

    LocationListener locationListener;

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public static GPSAddressUtils getInstance() {
        if (null == gpsAddressUtils) {
            gpsAddressUtils = new GPSAddressUtils();
            return gpsAddressUtils;
        }
        return gpsAddressUtils;
    }

    private Activity context;

    public void getLocation(Activity appContext) {
        //声明LocationClient类
        context = appContext;
        BDLocationListener myListener = new MyLocationListener();
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setTimeOut(6000);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
//
            switch (location.getLocType()) {
                case BDLocation.TypeGpsLocation: // GPS定位结果
                    locationListener.onChanged(true, location.getCity());
                    GPSAddressUtils.getInstance().mLocationClient.stop();
                    break;
                case BDLocation.TypeNetWorkLocation: // 网络定位结果
                    locationListener.onChanged(true, location.getCity());
                    GPSAddressUtils.getInstance().mLocationClient.stop();
                    break;
                case BDLocation.TypeNetWorkException:
                    locationListener.onChanged(false, "");
                    GPSAddressUtils.getInstance().mLocationClient.stop();
                    break;
                case BDLocation.TypeCriteriaException:
                    locationListener.onChanged(false, "");
                    GPSAddressUtils.getInstance().mLocationClient.stop();
                    break;
                default:
                    locationListener.onChanged(false, "");
                    break;
            }

        }

    }

}
