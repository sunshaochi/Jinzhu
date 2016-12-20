package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/16.
 */

public class UserLoginBean implements Parcelable {
    private AdressBean address;//地址历史列表
    private String userStatus;//用户类别
    private ProfileInfoBean profileInfo;
    private String referCode;
    private String invitationCode;
    private String username;
    private CreditInfoBean creditInfo;


    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AdressBean getAddress() {
        return address;
    }

    public void setAddress(AdressBean address) {
        this.address = address;
    }

    public ProfileInfoBean getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(ProfileInfoBean profileInfo) {
        this.profileInfo = profileInfo;
    }

    public CreditInfoBean getCreditInfo() {
        return creditInfo;
    }

    public void setCreditInfo(CreditInfoBean creditInfo) {
        this.creditInfo = creditInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.address, flags);
        dest.writeString(this.userStatus);
        dest.writeParcelable(this.profileInfo, flags);
        dest.writeString(this.referCode);
        dest.writeString(this.invitationCode);
        dest.writeString(this.username);
        dest.writeParcelable(this.creditInfo, flags);
    }

    public UserLoginBean() {
    }

    protected UserLoginBean(Parcel in) {
        this.address = in.readParcelable(AdressBean.class.getClassLoader());
        this.userStatus = in.readString();
        this.profileInfo = in.readParcelable(ProfileInfoBean.class.getClassLoader());
        this.referCode = in.readString();
        this.invitationCode = in.readString();
        this.username = in.readString();
        this.creditInfo = in.readParcelable(CreditInfoBean.class.getClassLoader());
    }

    public static final Creator<UserLoginBean> CREATOR = new Creator<UserLoginBean>() {
        @Override
        public UserLoginBean createFromParcel(Parcel source) {
            return new UserLoginBean(source);
        }

        @Override
        public UserLoginBean[] newArray(int size) {
            return new UserLoginBean[size];
        }
    };
}
