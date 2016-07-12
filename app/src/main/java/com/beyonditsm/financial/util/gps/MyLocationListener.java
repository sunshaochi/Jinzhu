package com.beyonditsm.financial.util.gps;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.util.SpUtils;


/**
 * Created by xuleyuan on 2016/6/23.
 */
public class MyLocationListener implements BDLocationListener {
    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
//
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
             changeListener.isGet(true);
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果

            changeListener.isGet(true);
            SpUtils.setCity(MyApplication.getInstance().getApplicationContext(), location.getCity());

            if (location.getCity().equals(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()))) {
                changeListener.onChange(false, location.getCity());
            } else {
                changeListener.onChange(true, location.getCity());
            }
            GPSAddressUtils.mLocationClient.stop();

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            changeListener.isGet(false);
            GPSAddressUtils.mLocationClient.stop();

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            changeListener.isGet(false);
            GPSAddressUtils.mLocationClient.stop();

        }

    }
    public interface LocationChangeListener {
        public void onChange(boolean changed, String cityName);
        public void isGet(boolean isGet);
    }

    static LocationChangeListener changeListener;

    public static void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        changeListener = locationChangeListener;
    }
}

