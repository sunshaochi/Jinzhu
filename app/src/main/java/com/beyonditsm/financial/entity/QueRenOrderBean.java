package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 确认订单实体
 * Q164454216
 * Created by xiaoke on 2016/12/12.
 */

public class QueRenOrderBean implements Parcelable {

    private String uid;
    private String type;
    private String amount;
    private String name;
    private String bankName;
    private String bankCard;
    private String bankBranchName;
    private String orderNo;
    private String fundPassword;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFundPassword() {
        return fundPassword;
    }

    public void setFundPassword(String fundPassword) {
        this.fundPassword = fundPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.type);
        dest.writeString(this.amount);
        dest.writeString(this.name);
        dest.writeString(this.bankName);
        dest.writeString(this.bankCard);
        dest.writeString(this.bankBranchName);
        dest.writeString(this.orderNo);
        dest.writeString(this.fundPassword);
    }

    public QueRenOrderBean() {
    }

    protected QueRenOrderBean(Parcel in) {
        this.uid = in.readString();
        this.type = in.readString();
        this.amount = in.readString();
        this.name = in.readString();
        this.bankName = in.readString();
        this.bankCard = in.readString();
        this.bankBranchName = in.readString();
        this.orderNo = in.readString();
        this.fundPassword = in.readString();
    }

    public static final Creator<QueRenOrderBean> CREATOR = new Creator<QueRenOrderBean>() {
        @Override
        public QueRenOrderBean createFromParcel(Parcel source) {
            return new QueRenOrderBean(source);
        }

        @Override
        public QueRenOrderBean[] newArray(int size) {
            return new QueRenOrderBean[size];
        }
    };
}
