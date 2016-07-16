package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuleyuan on 2016/7/14.
 */
public class MoneyScopeInfo implements Parcelable{
    private String key;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public MoneyScopeInfo(Parcel in) {
        this.key = in.readString();
        this.val = in.readString();
    }

    public MoneyScopeInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.val);

    }
    public static final Parcelable.Creator<MoneyScopeInfo> CREATOR = new Parcelable.Creator<MoneyScopeInfo>() {
        public MoneyScopeInfo createFromParcel(Parcel source) {
            return new MoneyScopeInfo(source);
        }

        public MoneyScopeInfo[] newArray(int size) {
            return new MoneyScopeInfo[size];
        }
    };
}
