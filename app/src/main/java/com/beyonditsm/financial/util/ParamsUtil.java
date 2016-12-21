package com.beyonditsm.financial.util;

import android.app.Activity;

import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.entity.UserLoginEntity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 实体类转表单
 * Created by Yang on 2015/11/28 0028.
 */
public class ParamsUtil {

    public static ParamsUtil util;
    private boolean walletEnter = false;
    private UserLoginEntity ule;
    private String UserID;
    private Activity mainAct;
    private Activity serviceMainAct;
    private double firstWard;
    private double secWard;
    private double thirdWard;
    private double firstCardWard;
    private double secCardWard;
    private double thirdCardWard;
    private Activity currentAct;
    private String changedCity;
    private boolean cityGet;
    private boolean firstLocated = true;
    private List<ProductSortEntity.OrgTypeBean> orgTypeInfos;
    private boolean isReLogin = false;
    private boolean isClosing = false;
    private List<JJTProvinceEntity> provinceEntityList;
    private List<JJTCityEntity> cityEntityList;
    private List<JJTCounyEntity> counyEntityList;
    private ArrayList<CreditSpeedEntity.ResideStatusmapBean> propertyTypeList;
    public boolean isLogin = false;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public ArrayList<CreditSpeedEntity.ResideStatusmapBean> getPropertyTypeList() {
        return propertyTypeList;
    }

    public void setPropertyTypeList(ArrayList<CreditSpeedEntity.ResideStatusmapBean> propertyTypeList) {
        this.propertyTypeList = propertyTypeList;
    }

    public List<JJTCounyEntity> getCounyEntityList() {
        return counyEntityList;
    }

    public void setCounyEntityList(List<JJTCounyEntity> counyEntityList) {
        this.counyEntityList = counyEntityList;
    }

    public List<JJTProvinceEntity> getProvinceEntityList() {
        return provinceEntityList;
    }

    public void setProvinceEntityList(List<JJTProvinceEntity> provinceEntityList) {
        this.provinceEntityList = provinceEntityList;
    }

    public List<JJTCityEntity> getCityEntityList() {
        return cityEntityList;
    }

    public void setCityEntityList(List<JJTCityEntity> cityEntityList) {
        this.cityEntityList = cityEntityList;
    }

    public String transMapToString(Map map){
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (java.util.Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "\":\"" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "\",\"" : "");
        }
        return sb.toString();
    }

    public boolean isClosing() {
        return isClosing;
    }

    public void setClosing(boolean closing) {
        isClosing = closing;
    }

    public boolean isReLogin() {
        return isReLogin;
    }

    public void setReLogin(boolean reLogin) {
        isReLogin = reLogin;
    }

    public List<ProductSortEntity.OrgTypeBean> getOrgTypeInfos() {
        return orgTypeInfos;
    }

    public void setOrgTypeInfos(List<ProductSortEntity.OrgTypeBean> orgTypeInfos) {
        this.orgTypeInfos = orgTypeInfos;
    }

    public boolean isFirstLocated() {
        return firstLocated;
    }

    public void setFirstLocated(boolean firstLocated) {
        this.firstLocated = firstLocated;
    }

    public boolean isCityGet() {
        return cityGet;
    }

    public void setCityGet(boolean cityGet) {
        this.cityGet = cityGet;
    }

    public String getChangedCity() {
        return changedCity;
    }

    public void setChangedCity(String changedCity) {
        this.changedCity = changedCity;
    }

    public Activity getCurrentAct() {
        return currentAct;
    }

    public void setCurrentAct(Activity currentAct) {
        this.currentAct = currentAct;
    }

    public Activity getServiceMainAct() {
        return serviceMainAct;
    }

    public void setServiceMainAct(Activity serviceMainAct) {
        this.serviceMainAct = serviceMainAct;
    }

    public double getFirstWard() {
        return firstWard;
    }

    public void setFirstWard(double firstWard) {
        this.firstWard = firstWard;
    }

    public double getSecWard() {
        return secWard;
    }

    public void setSecWard(double secWard) {
        this.secWard = secWard;
    }

    public double getThirdWard() {
        return thirdWard;
    }

    public void setThirdWard(double thirdWard) {
        this.thirdWard = thirdWard;
    }

    public double getFirstCardWard() {
        return firstCardWard;
    }

    public void setFirstCardWard(double firstCardWard) {
        this.firstCardWard = firstCardWard;
    }

    public double getSecCardWard() {
        return secCardWard;
    }

    public void setSecCardWard(double secCardWard) {
        this.secCardWard = secCardWard;
    }

    public double getThirdCardWard() {
        return thirdCardWard;
    }

    public void setThirdCardWard(double thirdCardWard) {
        this.thirdCardWard = thirdCardWard;
    }

    public Activity getMainAct() {
        return mainAct;
    }

    public void setMainAct(Activity mainAct) {
        this.mainAct = mainAct;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public UserLoginEntity getUle() {
        if (null == ule) {
            ule = new UserLoginEntity();
            return ule;
        }
        return ule;
    }

    public void setUle(UserLoginEntity ule) {
        this.ule = ule;
    }

    public boolean isWalletEnter() {
        return walletEnter;
    }

    public void setWalletEnter(boolean walletEnter) {
        this.walletEnter = walletEnter;
    }

    public static ParamsUtil getInstance() {
        if (util == null)
            util = new ParamsUtil();
        return util;
    }

    private ParamsUtil() {

    }

    public List<NameValuePair> BeanToForm(String bean) {
        List<NameValuePair> queryParams = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(bean);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(jsonObject.get(key));
                queryParams.add(new BasicNameValuePair(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return queryParams;
    }
}
