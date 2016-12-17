package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/16.
 */

public class UserLoginBean implements Parcelable {
    private AdressBean adress;//地址历史列表
    private String userStatus;//用户类别
    private ProfileInfoBean profileInfoBean;
    private String referCode;
    private String invitationCode;
    private String username;
    private CreditInfoBean creditInfoBean;


    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public ProfileInfoBean getProfileInfoBean() {
        return profileInfoBean;
    }

    public void setProfileInfoBean(ProfileInfoBean profileInfoBean) {
        this.profileInfoBean = profileInfoBean;
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

    public CreditInfoBean getCreditInfoBean() {
        return creditInfoBean;
    }

    public void setCreditInfoBean(CreditInfoBean creditInfoBean) {
        this.creditInfoBean = creditInfoBean;
    }


    public AdressBean getAdress() {
        return adress;
    }

    public void setAdress(AdressBean adress) {
        this.adress = adress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.adress, flags);
        dest.writeString(this.userStatus);
        dest.writeParcelable(this.profileInfoBean, flags);
        dest.writeString(this.referCode);
        dest.writeString(this.invitationCode);
        dest.writeString(this.username);
        dest.writeParcelable(this.creditInfoBean, flags);
    }

    public UserLoginBean() {
    }

    protected UserLoginBean(Parcel in) {
        this.adress = in.readParcelable(AdressBean.class.getClassLoader());
        this.userStatus = in.readString();
        this.profileInfoBean = in.readParcelable(ProfileInfoBean.class.getClassLoader());
        this.referCode = in.readString();
        this.invitationCode = in.readString();
        this.username = in.readString();
        this.creditInfoBean = in.readParcelable(CreditInfoBean.class.getClassLoader());
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
