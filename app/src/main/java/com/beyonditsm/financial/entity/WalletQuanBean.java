package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/9.
 */

public class WalletQuanBean implements Parcelable {
    private String couponSummaryId;
    private String uid;
    private String type;
    private String balance;
    private Boolean isDisabled;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "WalletQuanBean{" +
                "couponSummaryId='" + couponSummaryId + '\'' +
                ", uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", balance='" + balance + '\'' +
                ", isDisabled=" + isDisabled +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getCouponSummaryId() {
        return couponSummaryId;
    }

    public void setCouponSummaryId(String couponSummaryId) {
        this.couponSummaryId = couponSummaryId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.couponSummaryId);
        dest.writeString(this.uid);
        dest.writeString(this.type);
        dest.writeString(this.balance);
        dest.writeValue(this.isDisabled);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    public WalletQuanBean() {
    }

    protected WalletQuanBean(Parcel in) {
        this.couponSummaryId = in.readString();
        this.uid = in.readString();
        this.type = in.readString();
        this.balance = in.readString();
        this.isDisabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<WalletQuanBean> CREATOR = new Creator<WalletQuanBean>() {
        @Override
        public WalletQuanBean createFromParcel(Parcel source) {
            return new WalletQuanBean(source);
        }

        @Override
        public WalletQuanBean[] newArray(int size) {
            return new WalletQuanBean[size];
        }
    };
}
