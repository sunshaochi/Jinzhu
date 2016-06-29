package com.beyonditsm.financial.util.gps;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.beyonditsm.financial.util.SpUtils;


/**
 * Created by xuleyuan on 2016/6/23.
 */
public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
//
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果



        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }

    }

}