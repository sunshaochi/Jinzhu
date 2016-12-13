package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/9.
 */

public class TiXianBean implements Parcelable {
    private String enchashmentOrderId;
    private String enchashmentOrderNo;
    private String uid;
    private String name;
    private String type;
    private String amount;
    private String status;
    private String accountType;
    private String bankNo;
    private String bankName;
    private String bankBranchName;
    private String bankCard;
    private String orderNo;
    private String orderRemark;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String startTime;
    private String endTime;

    public String getEnchashmentOrderId() {
        return enchashmentOrderId;
    }

    public void setEnchashmentOrderId(String enchashmentOrderId) {
        this.enchashmentOrderId = enchashmentOrderId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
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
        dest.writeString(this.enchashmentOrderId);
        dest.writeString(this.enchashmentOrderNo);
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.amount);
        dest.writeString(this.status);
        dest.writeString(this.accountType);
        dest.writeString(this.bankNo);
        dest.writeString(this.bankName);
        dest.writeString(this.bankBranchName);
        dest.writeString(this.bankCard);
        dest.writeString(this.orderNo);
        dest.writeString(this.orderRemark);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    public TiXianBean() {
    }

    protected TiXianBean(Parcel in) {
        this.enchashmentOrderId = in.readString();
        this.enchashmentOrderNo = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.amount = in.readString();
        this.status = in.readString();
        this.accountType = in.readString();
        this.bankNo = in.readString();
        this.bankName = in.readString();
        this.bankBranchName = in.readString();
        this.bankCard = in.readString();
        this.orderNo = in.readString();
        this.orderRemark = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<TiXianBean> CREATOR = new Creator<TiXianBean>() {
        @Override
        public TiXianBean createFromParcel(Parcel source) {
            return new TiXianBean(source);
        }

        @Override
        public TiXianBean[] newArray(int size) {
            return new TiXianBean[size];
        }
    };
}
