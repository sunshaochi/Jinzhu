package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/15.
 */
public class QueryBankCardEntity implements Parcelable {

    /**
     * cardType : 个人
     * bankNo :
     * accountId : 8aae982851f7dce401520aaf07de0056
     * bankName : sfjkllhfds
     * cardNo : qryjllsshjk
     * cardId : 8aae98415379572f0153795c682f0009
     */

    private String cardType;//银行卡类型
    private String bankNo;//银行号
    private String accountId;//账户ID
    private String bankName;//银行名称
    private String cardNo;//卡号
    private String cardId;//银行卡ID
    /**
     * status : 1
     */

    private int status;
    /**
     * accountName : 吕东东
     */

    private String accountName;

    protected QueryBankCardEntity(Parcel in) {
        cardType = in.readString();
        bankNo = in.readString();
        accountId = in.readString();
        bankName = in.readString();
        cardNo = in.readString();
        cardId = in.readString();
        status = in.readInt();
    }

    public static final Creator<QueryBankCardEntity> CREATOR = new Creator<QueryBankCardEntity>() {
        @Override
        public QueryBankCardEntity createFromParcel(Parcel in) {
            return new QueryBankCardEntity(in);
        }

        @Override
        public QueryBankCardEntity[] newArray(int size) {
            return new QueryBankCardEntity[size];
        }
    };

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public String getBankNo() {
        return bankNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getCardId() {
        return cardId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardType);
        parcel.writeString(bankNo);
        parcel.writeString(accountId);
        parcel.writeString(bankName);
        parcel.writeString(cardNo);
        parcel.writeString(cardId);
        parcel.writeInt(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
