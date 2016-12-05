package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 基本信息entity
 * Created by Administrator on 2016/10/19 0019.
 */

public class UserOrderInfo1 implements Parcelable{
    private String productId;//产品Id
    private String purpose;//借款用途
    private String maxRepaymentWeekly;//最大承受还款额度
    private String applyAmount;//贷款金额
    private String applyPeriods;//还款期限
    private String periodsAmount;//利息
    private String monthlyRate;//综合费率
    private String name;// 用户姓名
    private String mobilePhone;//联系电话
    private String idNo;//身份证号码
    private String marryStatus;//婚姻状况
    private String qualitications;//学历状况
    private String childrenCount;//子女数
    private String currentProvince;//常驻地省份
    private String currentCity;//常驻地地市
    private String currentRegion;//常驻地县
    private String currentAddress;//常驻地详细信息
    private String domicileProvince;//户籍省份
    private String domicileCity;//户籍地市
    private String domicileRegion;//户籍县
    private String domicileAddress;//户籍地详细信息
    private String resideStatus;//居住状况

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMaxRepaymentWeekly() {
        return maxRepaymentWeekly;
    }

    public void setMaxRepaymentWeekly(String maxRepaymentWeekly) {
        this.maxRepaymentWeekly = maxRepaymentWeekly;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getApplyPeriods() {
        return applyPeriods;
    }

    public void setApplyPeriods(String applyPeriods) {
        this.applyPeriods = applyPeriods;
    }

    public String getPeriodsAmount() {
        return periodsAmount;
    }

    public void setPeriodsAmount(String periodsAmount) {
        this.periodsAmount = periodsAmount;
    }

    public String getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(String monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getQualitications() {
        return qualitications;
    }

    public void setQualitications(String qualitications) {
        this.qualitications = qualitications;
    }

    public String getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(String childrenCount) {
        this.childrenCount = childrenCount;
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

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getDomicileProvince() {
        return domicileProvince;
    }

    public void setDomicileProvince(String domicileProvince) {
        this.domicileProvince = domicileProvince;
    }

    public String getDomicileCity() {
        return domicileCity;
    }

    public void setDomicileCity(String domicileCity) {
        this.domicileCity = domicileCity;
    }

    public String getDomicileRegion() {
        return domicileRegion;
    }

    public void setDomicileRegion(String domicileRegion) {
        this.domicileRegion = domicileRegion;
    }

    public String getDomicileAddress() {
        return domicileAddress;
    }

    public void setDomicileAddress(String domicileAddress) {
        this.domicileAddress = domicileAddress;
    }

    public String getResideStatus() {
        return resideStatus;
    }

    public void setResideStatus(String resideStatus) {
        this.resideStatus = resideStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.purpose);
        dest.writeString(this.maxRepaymentWeekly);
        dest.writeString(this.applyAmount);
        dest.writeString(this.applyPeriods);
        dest.writeString(this.periodsAmount);
        dest.writeString(this.monthlyRate);
        dest.writeString(this.name);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.idNo);
        dest.writeString(this.marryStatus);
        dest.writeString(this.qualitications);
        dest.writeString(this.childrenCount);
        dest.writeString(this.currentProvince);
        dest.writeString(this.currentCity);
        dest.writeString(this.currentRegion);
        dest.writeString(this.currentAddress);
        dest.writeString(this.domicileProvince);
        dest.writeString(this.domicileCity);
        dest.writeString(this.domicileRegion);
        dest.writeString(this.domicileAddress);
        dest.writeString(this.resideStatus);
    }

    public UserOrderInfo1() {
    }

    protected UserOrderInfo1(Parcel in) {
        this.productId = in.readString();
        this.purpose = in.readString();
        this.maxRepaymentWeekly = in.readString();
        this.applyAmount = in.readString();
        this.applyPeriods = in.readString();
        this.periodsAmount = in.readString();
        this.monthlyRate = in.readString();
        this.name = in.readString();
        this.mobilePhone = in.readString();
        this.idNo = in.readString();
        this.marryStatus = in.readString();
        this.qualitications = in.readString();
        this.childrenCount = in.readString();
        this.currentProvince = in.readString();
        this.currentCity = in.readString();
        this.currentRegion = in.readString();
        this.currentAddress = in.readString();
        this.domicileProvince = in.readString();
        this.domicileCity = in.readString();
        this.domicileRegion = in.readString();
        this.domicileAddress = in.readString();
        this.resideStatus = in.readString();
    }

    public static final Creator<UserOrderInfo1> CREATOR = new Creator<UserOrderInfo1>() {
        @Override
        public UserOrderInfo1 createFromParcel(Parcel source) {
            return new UserOrderInfo1(source);
        }

        @Override
        public UserOrderInfo1[] newArray(int size) {
            return new UserOrderInfo1[size];
        }
    };
}
