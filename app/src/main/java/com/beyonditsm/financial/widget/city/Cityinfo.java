package com.beyonditsm.financial.widget.city;

import android.os.Parcel;
import android.os.Parcelable;

public class Cityinfo implements Parcelable {
    private String id;
    private String city_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.city_name);
    }

    public Cityinfo() {
    }

    protected Cityinfo(Parcel in) {
        this.id = in.readString();
        this.city_name = in.readString();
    }

    public static final Creator<Cityinfo> CREATOR = new Creator<Cityinfo>() {
        public Cityinfo createFromParcel(Parcel source) {
            return new Cityinfo(source);
        }

        public Cityinfo[] newArray(int size) {
            return new Cityinfo[size];
        }
    };
}
