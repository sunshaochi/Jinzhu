package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuleyuan on 2016/7/14.
 */
public class OrgTypeInfo implements Parcelable {
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

    public OrgTypeInfo(Parcel in) {
        this.key = in.readString();
        this.val = in.readString();
    }

    public OrgTypeInfo() {
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

    public static final Creator<OrgTypeInfo> CREATOR = new Creator<OrgTypeInfo>() {
        public OrgTypeInfo createFromParcel(Parcel source) {
            return new OrgTypeInfo(source);
        }

        public OrgTypeInfo[] newArray(int size) {
            return new OrgTypeInfo[size];
        }
    };
}
