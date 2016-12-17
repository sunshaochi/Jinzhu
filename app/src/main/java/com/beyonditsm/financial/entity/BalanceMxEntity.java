package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 现金收支明细，实体类
 * Q164454216
 * Created by xiaoke on 2016/12/11.
 */

public class BalanceMxEntity implements Parcelable {
   private  String  couponReferenceId;
    private  String couponNo;
    private  String enchashmentOrderNo;
    private  String uid;
    private  String type;
    private  String amount;
    private  String balance;
    private  String remark;
    private  String createUser;
    private  String createTime;
    private  String updateUser;
    private  String updateTime;
    private  String startTime;
    private  String endTime;

    public String getCouponReferenceId() {
        return couponReferenceId;
    }

    public void setCouponReferenceId(String couponReferenceId) {
        this.couponReferenceId = couponReferenceId;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getEnchashmentOrderNo() {
        return enchashmentOrderNo;
    }

    public void setEnchashmentOrderNo(String enchashmentOrderNo) {
        this.enchashmentOrderNo = enchashmentOrderNo;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        dest.writeString(this.couponReferenceId);
        dest.writeString(this.couponNo);
        dest.writeString(this.enchashmentOrderNo);
        dest.writeString(this.uid);
        dest.writeString(this.type);
        dest.writeString(this.amount);
        dest.writeString(this.balance);
        dest.writeString(this.remark);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    public BalanceMxEntity() {
    }

    protected BalanceMxEntity(Parcel in) {
        this.couponReferenceId = in.readString();
        this.couponNo = in.readString();
        this.enchashmentOrderNo = in.readString();
        this.uid = in.readString();
        this.type = in.readString();
        this.amount = in.readString();
        this.balance = in.readString();
        this.remark = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<BalanceMxEntity> CREATOR = new Creator<BalanceMxEntity>() {
        @Override
        public BalanceMxEntity createFromParcel(Parcel source) {
            return new BalanceMxEntity(source);
        }

        @Override
        public BalanceMxEntity[] newArray(int size) {
            return new BalanceMxEntity[size];
        }
    };
}
