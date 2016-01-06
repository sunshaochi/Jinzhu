package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CreditManagerEntity implements Parcelable {
    private CreditManager creditManager;
    private String username;
    private String newPassword;

    protected CreditManagerEntity(Parcel in) {
        username = in.readString();
        newPassword = in.readString();
    }

    public static final Creator<CreditManagerEntity> CREATOR = new Creator<CreditManagerEntity>() {
        @Override
        public CreditManagerEntity createFromParcel(Parcel in) {
            return new CreditManagerEntity(in);
        }

        @Override
        public CreditManagerEntity[] newArray(int size) {
            return new CreditManagerEntity[size];
        }
    };

    public CreditManager getCreditManager() {
        return creditManager;
    }

    public void setCreditManager(CreditManager creditManager) {
        this.creditManager = creditManager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreditManagerEntity() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(newPassword);
    }
}
