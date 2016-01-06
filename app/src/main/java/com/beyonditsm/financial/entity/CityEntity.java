package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2015/11/26.
 */
public class CityEntity implements Parcelable {
    private String code;//省份编码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public CityEntity() {
    }

    protected CityEntity(Parcel in) {
        code = in.readString();

    }

    public static final Creator<CityEntity> CREATOR = new Creator<CityEntity>() {
        @Override
        public CityEntity createFromParcel(Parcel in) {
            return new CityEntity(in);
        }

        @Override
        public CityEntity[] newArray(int size) {
            return new CityEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);

    }
}
