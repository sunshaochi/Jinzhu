package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/28.
 */
public class AreaEntity implements Parcelable{

    private String cityCode;//城市编码

    protected AreaEntity(Parcel in) {
        cityCode = in.readString();
    }

    public static final Creator<AreaEntity> CREATOR = new Creator<AreaEntity>() {
        @Override
        public AreaEntity createFromParcel(Parcel in) {
            return new AreaEntity(in);
        }

        @Override
        public AreaEntity[] newArray(int size) {
            return new AreaEntity[size];
        }
    };

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public AreaEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityCode);
    }
}
