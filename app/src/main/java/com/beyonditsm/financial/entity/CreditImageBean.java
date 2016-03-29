package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片bean
 * Created by wangbin on 16/3/28.
 */
public class CreditImageBean implements Parcelable {
    private String id;
    private String isPass;//是否通过审核：1：通过，0驳回，2待审核
    private String remark;
    private String imageUrl;

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CreditImageBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.isPass);
        dest.writeString(this.remark);
        dest.writeString(this.imageUrl);
    }

    protected CreditImageBean(Parcel in) {
        this.id = in.readString();
        this.isPass = in.readString();
        this.remark = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<CreditImageBean> CREATOR = new Creator<CreditImageBean>() {
        public CreditImageBean createFromParcel(Parcel source) {
            return new CreditImageBean(source);
        }

        public CreditImageBean[] newArray(int size) {
            return new CreditImageBean[size];
        }
    };
}
