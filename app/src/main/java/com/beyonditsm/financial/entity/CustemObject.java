package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangbin on 15/11/17.
 */
public class CustemObject implements Parcelable {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
    }

    public CustemObject() {
    }

    protected CustemObject(Parcel in) {
        this.data = in.readString();
    }

    public static final Parcelable.Creator<CustemObject> CREATOR = new Parcelable.Creator<CustemObject>() {
        public CustemObject createFromParcel(Parcel source) {
            return new CustemObject(source);
        }

        public CustemObject[] newArray(int size) {
            return new CustemObject[size];
        }
    };
}
