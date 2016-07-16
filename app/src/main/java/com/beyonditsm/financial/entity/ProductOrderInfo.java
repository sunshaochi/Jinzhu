package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuleyuan on 2016/7/14.
 */
public class ProductOrderInfo implements Parcelable{
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

    public ProductOrderInfo(Parcel in) {
        this.key = in.readString();
        this.val = in.readString();
    }

    public ProductOrderInfo() {
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
    public static final Parcelable.Creator<ProductOrderInfo> CREATOR = new Parcelable.Creator<ProductOrderInfo>() {
        public ProductOrderInfo createFromParcel(Parcel source) {
            return new ProductOrderInfo(source);
        }

        public ProductOrderInfo[] newArray(int size) {
            return new ProductOrderInfo[size];
        }
    };
}
