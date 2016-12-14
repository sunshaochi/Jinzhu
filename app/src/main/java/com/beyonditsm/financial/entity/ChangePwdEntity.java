package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 修改密码
 * Created by Yang on 2015/11/23 0023.
 */
public class ChangePwdEntity implements Parcelable{
    private String password;
    private String newPassword;
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static Creator<ChangePwdEntity> getCREATOR() {
        return CREATOR;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePwdEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.password);
        dest.writeString(this.newPassword);
        dest.writeString(this.userName);
    }

    protected ChangePwdEntity(Parcel in) {
        this.password = in.readString();
        this.newPassword = in.readString();
        this.userName = in.readString();
    }

    public static final Creator<ChangePwdEntity> CREATOR = new Creator<ChangePwdEntity>() {
        @Override
        public ChangePwdEntity createFromParcel(Parcel source) {
            return new ChangePwdEntity(source);
        }

        @Override
        public ChangePwdEntity[] newArray(int size) {
            return new ChangePwdEntity[size];
        }
    };
}
