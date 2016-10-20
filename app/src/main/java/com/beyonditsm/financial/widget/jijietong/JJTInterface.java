package com.beyonditsm.financial.widget.jijietong;

import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;

/**
 * Created by xuleyuan on 2016/10/20.
 */

public interface JJTInterface {
    void onProvinceSelected(JJTProvinceEntity jjtProvinceEntity);
    void onCitySelected(JJTCityEntity jjtCityEntity);
}
