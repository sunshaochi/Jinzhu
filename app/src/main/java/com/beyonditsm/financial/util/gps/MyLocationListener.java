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
            if (location.getCity().equals(SpUtils.getCity(MyApplication.getInstance().getApplicationContext())+"")) {
                changeListener.onChange(false, location.getCity());
            } else {
                changeListener.onChange(true, location.getCity());
            }
            GPSAddressUtils.getInstance().mLocationClient.stop();

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            changeListener.isGet(false);
            GPSAddressUtils.getInstance().mLocationClient.stop();

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            changeListener.isGet(false);
            GPSAddressUtils.getInstance().mLocationClient.stop();

        }

    }
    public interface LocationChangeListener {
        public void onChange(boolean changed, String cityName);
        public void isGet(boolean isGet);
    }

    LocationChangeListener changeListener;

    public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        changeListener = locationChangeListener;
    }
}

