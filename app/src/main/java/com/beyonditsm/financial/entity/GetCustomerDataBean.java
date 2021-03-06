package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuleyuan on 20/12/16.
 */

public class GetCustomerDataBean implements Parcelable {

    /**
     * orderCustomerId : 用户id
     * idNo : 370104199207181319 身份证
     * orderId : null 订单编号
     * uid : null uid
     * phoneNum : null 手机号
     * cusName : 徐乐源 用户名称
     * cusRegion : null 
     * cusOrigin : null
     * sex : 1 性别
     * age : 11 年龄
     * isInBlacklist : null 在黑名单中
     * isMarried : false 已婚
     * nativePlace : null 
     * currentProvince : 110000
     * currentCity : 110100
     * currentRegion : 110113
     * domicileAddr : null
     * careerName : null
     * company : 吐了
     * careerTitle : 啦啦啦
     * hasHouseFunding : true
     * hasSocialInsurance : false
     * haveOwnCar : null
     * haveOwnHouse : null
     * creditState : null
     * createUser : null
     * createTime : null
     * updateUser : null
     * updateTime : null
     * customerInfo : null
     */

    private String orderCustomerId;
    private String idNo;
    private String orderId;
    private String uid;
    private String phoneNum;
    private String cusName;
    private String cusRegion;
    private String cusOrigin;
    private String sex;
    private String age;
    private String isInBlacklist;
    private String isMarried;
    private String nativePlace;
    private String currentProvince;
    private String currentCity;
    private String currentRegion;
    private String domicileAddr;
    private String careerName;
    private String company;
    private String careerTitle;
    private String hasHouseFunding;
    private String hasSocialInsurance;
    private String haveOwnCar;
    private String haveOwnHouse;
    private String creditState;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String customerInfo;

    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusRegion() {
        return cusRegion;
    }

    public void setCusRegion(String cusRegion) {
        this.cusRegion = cusRegion;
    }

    public String getCusOrigin() {
        return cusOrigin;
    }

    public void setCusOrigin(String cusOrigin) {
        this.cusOrigin = cusOrigin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(String isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public String isIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getCurrentProvince() {
        return currentProvince;
    }

    public void setCurrentProvince(String currentProvince) {
        this.currentProvince = currentProvince;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(String currentRegion) {
        this.currentRegion = currentRegion;
    }

    public String getDomicileAddr() {
        return domicileAddr;
    }

    public void setDomicileAddr(String domicileAddr) {
        this.domicileAddr = domicileAddr;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCareerTitle() {
        return careerTitle;
    }

    public void setCareerTitle(String careerTitle) {
        this.careerTitle = careerTitle;
    }

    public String isHasHouseFunding() {
        return hasHouseFunding;
    }

    public void setHasHouseFunding(String hasHouseFunding) {
        this.hasHouseFunding = hasHouseFunding;
    }

    public String isHasSocialInsurance() {
        return hasSocialInsurance;
    }

    public void setHasSocialInsurance(String hasSocialInsurance) {
        this.hasSocialInsurance = hasSocialInsurance;
    }

    public String getHaveOwnCar() {
        return haveOwnCar;
    }

    public void setHaveOwnCar(String haveOwnCar) {
        this.haveOwnCar = haveOwnCar;
    }

    public String getHaveOwnHouse() {
        return haveOwnHouse;
    }

    public void setHaveOwnHouse(String haveOwnHouse) {
        this.haveOwnHouse = haveOwnHouse;
    }

    public String getCreditState() {
        return creditState;
    }

    public void setCreditState(String creditState) {
        this.creditState = creditState;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderCustomerId);
        dest.writeString(this.idNo);
        dest.writeString(this.orderId);
        dest.writeString(this.uid);
        dest.writeString(this.phoneNum);
        dest.writeString(this.cusName);
        dest.writeString(this.cusRegion);
        dest.writeString(this.cusOrigin);
        dest.writeString(this.sex);
        dest.writeString(this.age);
        dest.writeString(this.isInBlacklist);
        dest.writeString(this.isMarried);
        dest.writeString(this.nativePlace);
        dest.writeString(this.currentProvince);
        dest.writeString(this.currentCity);
        dest.writeString(this.currentRegion);
        dest.writeString(this.domicileAddr);
        dest.writeString(this.careerName);
        dest.writeString(this.company);
        dest.writeString(this.careerTitle);
        dest.writeString(this.hasHouseFunding);
        dest.writeString(this.hasSocialInsurance);
        dest.writeString(this.haveOwnCar);
        dest.writeString(this.haveOwnHouse);
        dest.writeString(this.creditState);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.customerInfo);
    }

    public GetCustomerDataBean() {
    }

    protected GetCustomerDataBean(Parcel in) {
        this.orderCustomerId = in.readString();
        this.idNo = in.readString();
        this.orderId = in.readString();
        this.uid = in.readString();
        this.phoneNum = in.readString();
        this.cusName = in.readString();
        this.cusRegion = in.readString();
        this.cusOrigin = in.readString();
        this.sex = in.readString();
        this.age = in.readString();
        this.isInBlacklist = in.readString();
        this.isMarried = in.readString();
        this.nativePlace = in.readString();
        this.currentProvince = in.readString();
        this.currentCity = in.readString();
        this.currentRegion = in.readString();
        this.domicileAddr = in.readString();
        this.careerName = in.readString();
        this.company = in.readString();
        this.careerTitle = in.readString();
        this.hasHouseFunding = in.readString();
        this.hasSocialInsurance = in.readString();
        this.haveOwnCar = in.readString();
        this.haveOwnHouse = in.readString();
        this.creditState = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.customerInfo = in.readString();
    }

    public static final Parcelable.Creator<GetCustomerDataBean> CREATOR = new Parcelable.Creator<GetCustomerDataBean>() {
        @Override
        public GetCustomerDataBean createFromParcel(Parcel source) {
            return new GetCustomerDataBean(source);
        }

        @Override
        public GetCustomerDataBean[] newArray(int size) {
            return new GetCustomerDataBean[size];
        }
    };
}
