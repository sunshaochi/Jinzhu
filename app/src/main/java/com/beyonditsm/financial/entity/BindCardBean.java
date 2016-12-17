package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/12.
 */

public class BindCardBean implements Parcelable {
   private String accountName;
    private String     cardType;
    private String     updateUser;
    private String       bankName;
    private String       updateTime;//": null,
    private String       cardNo;//": "6212262011022313311",
    private String       bankCardId;//": null,
    private String       accountId;//": "8",
    private String       createTime;//": null,
    private String      bankNo;//": "",
    private String       branchBankName;//": "中山市黄圃支行",
    private String      createUser;//": null,
    private String     status;//": 1


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.cardType);
        dest.writeString(this.updateUser);
        dest.writeString(this.bankName);
        dest.writeString(this.updateTime);
        dest.writeString(this.cardNo);
        dest.writeString(this.bankCardId);
        dest.writeString(this.accountId);
        dest.writeString(this.createTime);
        dest.writeString(this.bankNo);
        dest.writeString(this.branchBankName);
        dest.writeString(this.createUser);
        dest.writeString(this.status);
    }

    public BindCardBean() {
    }

    protected BindCardBean(Parcel in) {
        this.accountName = in.readString();
        this.cardType = in.readString();
        this.updateUser = in.readString();
        this.bankName = in.readString();
        this.updateTime = in.readString();
        this.cardNo = in.readString();
        this.bankCardId = in.readString();
        this.accountId = in.readString();
        this.createTime = in.readString();
        this.bankNo = in.readString();
        this.branchBankName = in.readString();
        this.createUser = in.readString();
        this.status = in.readString();
    }

    public static final Creator<BindCardBean> CREATOR = new Creator<BindCardBean>() {
        @Override
        public BindCardBean createFromParcel(Parcel source) {
            return new BindCardBean(source);
        }

        @Override
        public BindCardBean[] newArray(int size) {
            return new BindCardBean[size];
        }
    };
}
