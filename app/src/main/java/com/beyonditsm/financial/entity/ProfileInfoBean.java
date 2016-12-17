package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 个人信息资料
 * Q164454216
 * Created by xiaoke on 2016/12/16.
 */

public class ProfileInfoBean implements Parcelable {
    private String profileId;//": "fda1f62736d045c98439a1743b7471d4",
    private String customerId;//": "d555e27706b345748a32b82fb913c32d",
    private String name;//": null,
    private String idNo;//": null,身份证号
    private String sex;//": null,
    private String age;//": null,
    private String mobilePhone;//": "18939265921",
    private boolean isMarried;//": null,0未婚，1已婚
    private String email;//": null,
    private String nativePlace;//": null,
    private String homeAddress;//": null,
    private String avatarPic;//": null,
    private String company;//": null,
    private String careerTitle;//": null,
    private String createUser;//": "admin",
    private String createTime;//": 1481699913000,
    private String updateUser;//": null,
    private String updateTime;//": null


    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(String avatarPic) {
        this.avatarPic = avatarPic;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCareerTitle() {
        return careerTitle;
    }

    public void setCareerTitle(String careerTitle) {
        this.careerTitle = careerTitle;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profileId);
        dest.writeString(this.customerId);
        dest.writeString(this.name);
        dest.writeString(this.idNo);
        dest.writeString(this.sex);
        dest.writeString(this.age);
        dest.writeString(this.mobilePhone);
        dest.writeByte(this.isMarried ? (byte) 1 : (byte) 0);
        dest.writeString(this.email);
        dest.writeString(this.nativePlace);
        dest.writeString(this.homeAddress);
        dest.writeString(this.avatarPic);
        dest.writeString(this.company);
        dest.writeString(this.careerTitle);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public ProfileInfoBean() {
    }

    protected ProfileInfoBean(Parcel in) {
        this.profileId = in.readString();
        this.customerId = in.readString();
        this.name = in.readString();
        this.idNo = in.readString();
        this.sex = in.readString();
        this.age = in.readString();
        this.mobilePhone = in.readString();
        this.isMarried = in.readByte() != 0;
        this.email = in.readString();
        this.nativePlace = in.readString();
        this.homeAddress = in.readString();
        this.avatarPic = in.readString();
        this.company = in.readString();
        this.careerTitle = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<ProfileInfoBean> CREATOR = new Creator<ProfileInfoBean>() {
        @Override
        public ProfileInfoBean createFromParcel(Parcel source) {
            return new ProfileInfoBean(source);
        }

        @Override
        public ProfileInfoBean[] newArray(int size) {
            return new ProfileInfoBean[size];
        }
    };
}
