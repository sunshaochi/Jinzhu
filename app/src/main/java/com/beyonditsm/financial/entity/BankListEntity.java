package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/17
 */
public class BankListEntity implements Parcelable {
    private String bankName;
    private String id;

    protected BankListEntity(Parcel in) {
        bankName = in.readString();
        id = in.readString();
    }

    public static final Creator<BankListEntity> CREATOR = new Creator<BankListEntity>() {
        @Override
        public BankListEntity createFromParcel(Parcel in) {
            return new BankListEntity(in);
        }

        @Override
        public BankListEntity[] newArray(int size) {
            return new BankListEntity[size];
        }
    };

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bankName);
        parcel.writeString(id);
    }
}
