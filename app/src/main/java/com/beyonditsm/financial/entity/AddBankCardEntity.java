package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/15
 */
public class AddBankCardEntity implements Parcelable{
    private String accountName;//开户姓名
    private String cardNo;//银行卡号
    private String bankName;//银行名称
    private String branchBankName;//支行名称
    private String fundPassword;//资金密码

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AddBankCardEntity() {

    }

    protected AddBankCardEntity(Parcel in) {
        cardNo = in.readString();
        bankName = in.readString();
        branchBankName = in.readString();
        fundPassword = in.readString();
        accountName = in.readString();
    }

    public static final Creator<AddBankCardEntity> CREATOR = new Creator<AddBankCardEntity>() {
        @Override
        public AddBankCardEntity createFromParcel(Parcel in) {
            return new AddBankCardEntity(in);
        }

        @Override
        public AddBankCardEntity[] newArray(int size) {
            return new AddBankCardEntity[size];
        }
    };

    public String getFundPassword() {
        return fundPassword;
    }

    public void setFundPassword(String fundPassword) {
        this.fundPassword = fundPassword;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardNo);
        parcel.writeString(bankName);
        parcel.writeString(branchBankName);
        parcel.writeString(fundPassword);
        parcel.writeString(accountName);
    }
}
