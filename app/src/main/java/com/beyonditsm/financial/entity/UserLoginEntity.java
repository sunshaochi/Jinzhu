package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/13.
 */
public class UserLoginEntity implements Parcelable{


    /**
     * myReferralCode : HQ84Z5T5DEXS9FCMNF
     * referralCode : OHZOJOQCVCAGPP522U
     * roleName : ROLE_CREDIT_MANAGER
     * username : 15195453333
     *
     *
     */

    private String myReferralCode;//我的邀请码
    private String referralCode;//邀请码
    private String roleName;//角色
    private String description;//用户类型
    private String username;//用户名
    /**
     * vipLevel : 1
     * vipImage : img/1778238623.jpg
     */

    private int vipLevel;//vip等级
    private String vipImage;//vip等级图片

    protected UserLoginEntity(Parcel in) {
        myReferralCode = in.readString();
        referralCode = in.readString();
        roleName = in.readString();
        description=in.readString();
        username = in.readString();
        vipLevel = in.readInt();
        vipImage = in.readString();
    }

    public static final Creator<UserLoginEntity> CREATOR = new Creator<UserLoginEntity>() {
        @Override
        public UserLoginEntity createFromParcel(Parcel in) {
            return new UserLoginEntity(in);
        }

        @Override
        public UserLoginEntity[] newArray(int size) {
            return new UserLoginEntity[size];
        }
    };

    public UserLoginEntity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMyReferralCode(String myReferralCode) {
        this.myReferralCode = myReferralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMyReferralCode() {
        return myReferralCode;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(myReferralCode);
        dest.writeString(referralCode);
        dest.writeString(roleName);
        dest.writeString(description);
        dest.writeString(username);
        dest.writeInt(vipLevel);
        dest.writeString(vipImage);
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipImage() {
        return vipImage;
    }

    public void setVipImage(String vipImage) {
        this.vipImage = vipImage;
    }
}
