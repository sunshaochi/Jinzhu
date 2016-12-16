package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/16.
 */

public class CreditInfoBean implements Parcelable {


    private String creditId;//": "4247a871b8244109b75c32a3afb469b4",
    private String customerId;//": "d555e27706b345748a32b82fb913c32d",
    private String creditScore;//": null,
    private String careerName;//": null,
    private String hasHouseFunding;//": null,
    private String hasSocialInsurance;//": null,
    private String haveOwnCar;//": null,
    private String haveOwnHouse;//": null,
    private String creditStateIn2Year;//": null,
    private String grantedCreditScore;//": null,
    private String checkInScore;//": null,
    private String creditLimit;//": null,
    private String creditCardState;//": null,
    private String creditCardApplyTime;//": null,
    private String createUser;//": "admin",
    private String createTime;//": 1481699913000,
    private String updateUser;//": null,
    private String updateTime;//": null

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getHasHouseFunding() {
        return hasHouseFunding;
    }

    public void setHasHouseFunding(String hasHouseFunding) {
        this.hasHouseFunding = hasHouseFunding;
    }

    public String getHasSocialInsurance() {
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

    public String getCreditStateIn2Year() {
        return creditStateIn2Year;
    }

    public void setCreditStateIn2Year(String creditStateIn2Year) {
        this.creditStateIn2Year = creditStateIn2Year;
    }

    public String getGrantedCreditScore() {
        return grantedCreditScore;
    }

    public void setGrantedCreditScore(String grantedCreditScore) {
        this.grantedCreditScore = grantedCreditScore;
    }

    public String getCheckInScore() {
        return checkInScore;
    }

    public void setCheckInScore(String checkInScore) {
        this.checkInScore = checkInScore;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditCardState() {
        return creditCardState;
    }

    public void setCreditCardState(String creditCardState) {
        this.creditCardState = creditCardState;
    }

    public String getCreditCardApplyTime() {
        return creditCardApplyTime;
    }

    public void setCreditCardApplyTime(String creditCardApplyTime) {
        this.creditCardApplyTime = creditCardApplyTime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.creditId);
        dest.writeString(this.customerId);
        dest.writeString(this.creditScore);
        dest.writeString(this.careerName);
        dest.writeString(this.hasHouseFunding);
        dest.writeString(this.hasSocialInsurance);
        dest.writeString(this.haveOwnCar);
        dest.writeString(this.haveOwnHouse);
        dest.writeString(this.creditStateIn2Year);
        dest.writeString(this.grantedCreditScore);
        dest.writeString(this.checkInScore);
        dest.writeString(this.creditLimit);
        dest.writeString(this.creditCardState);
        dest.writeString(this.creditCardApplyTime);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public CreditInfoBean() {
    }

    protected CreditInfoBean(Parcel in) {
        this.creditId = in.readString();
        this.customerId = in.readString();
        this.creditScore = in.readString();
        this.careerName = in.readString();
        this.hasHouseFunding = in.readString();
        this.hasSocialInsurance = in.readString();
        this.haveOwnCar = in.readString();
        this.haveOwnHouse = in.readString();
        this.creditStateIn2Year = in.readString();
        this.grantedCreditScore = in.readString();
        this.checkInScore = in.readString();
        this.creditLimit = in.readString();
        this.creditCardState = in.readString();
        this.creditCardApplyTime = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<CreditInfoBean> CREATOR = new Creator<CreditInfoBean>() {
        @Override
        public CreditInfoBean createFromParcel(Parcel source) {
            return new CreditInfoBean(source);
        }

        @Override
        public CreditInfoBean[] newArray(int size) {
            return new CreditInfoBean[size];
        }
    };
}
