package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yang on 2015/12/10 0010.
 */
public class PagerColor implements Parcelable{
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTvname() {
        return tvname;
    }

    public void setTvname(String tvname) {
        this.tvname = tvname;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    private String color;
    private int progress;
    private String tvname;
    private String tv;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeInt(this.progress);
        dest.writeString(this.tvname);
        dest.writeString(this.tv);
    }

    public PagerColor() {
    }

    protected PagerColor(Parcel in) {
        this.color = in.readString();
        this.progress = in.readInt();
        this.tvname = in.readString();
        this.tv = in.readString();
    }

    public static final Creator<PagerColor> CREATOR = new Creator<PagerColor>() {
        public PagerColor createFromParcel(Parcel source) {
            return new PagerColor(source);
        }

        public PagerColor[] newArray(int size) {
            return new PagerColor[size];
        }
    };
}
