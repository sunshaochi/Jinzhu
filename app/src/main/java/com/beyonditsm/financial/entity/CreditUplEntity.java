package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wangbin on 16/3/28.
 */
public class CreditUplEntity implements Parcelable {

    private List<CreditImageBean> image;
    private String uploadDisplayName;
    private String uploadItemId;
    private Integer limit;

    public List<CreditImageBean> getImage() {
        return image;
    }

    public void setImage(List<CreditImageBean> image) {
        this.image = image;
    }

    public String getUploadDisplayName() {
        return uploadDisplayName;
    }

    public void setUploadDisplayName(String uploadDisplayName) {
        this.uploadDisplayName = uploadDisplayName;
    }

    public String getUploadItemId() {
        return uploadItemId;
    }

    public void setUploadItemId(String uploadItemId) {
        this.uploadItemId = uploadItemId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(image);
        dest.writeString(this.uploadDisplayName);
        dest.writeString(this.uploadItemId);
        dest.writeValue(this.limit);
    }

    public CreditUplEntity() {
    }

    protected CreditUplEntity(Parcel in) {
        this.image = in.createTypedArrayList(CreditImageBean.CREATOR);
        this.uploadDisplayName = in.readString();
        this.uploadItemId = in.readString();
        this.limit = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<CreditUplEntity> CREATOR = new Parcelable.Creator<CreditUplEntity>() {
        public CreditUplEntity createFromParcel(Parcel source) {
            return new CreditUplEntity(source);
        }

        public CreditUplEntity[] newArray(int size) {
            return new CreditUplEntity[size];
        }
    };
}
