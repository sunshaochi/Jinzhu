package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 提交图片资料
 * Created by wangbin on 16/3/29.
 */
public class SubUplEntity implements Parcelable {

    private String uploadItemId;
    private List<CreditImageBean> imageUrl;


    public String getUploadItemId() {
        return uploadItemId;
    }

    public void setUploadItemId(String uploadItemId) {
        this.uploadItemId = uploadItemId;
    }

    public List<CreditImageBean> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<CreditImageBean> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uploadItemId);
        dest.writeTypedList(imageUrl);
    }

    public SubUplEntity() {
    }

    protected SubUplEntity(Parcel in) {
        this.uploadItemId = in.readString();
        this.imageUrl = in.createTypedArrayList(CreditImageBean.CREATOR);
    }

    public static final Parcelable.Creator<SubUplEntity> CREATOR = new Parcelable.Creator<SubUplEntity>() {
        public SubUplEntity createFromParcel(Parcel source) {
            return new SubUplEntity(source);
        }

        public SubUplEntity[] newArray(int size) {
            return new SubUplEntity[size];
        }
    };
}
