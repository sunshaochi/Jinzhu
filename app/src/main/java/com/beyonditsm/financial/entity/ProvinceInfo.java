package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 *
 * 省份
 * Created by wangbin on 15/12/20.
 */
@Table(name = "province_table")
public class ProvinceInfo implements Parcelable {
    @Column
    private String id;
    @Column
    private String code;
    @Column
    private String name;

    private Object abbr;

    public Object getAbbr() {
        return abbr;
    }

    public void setAbbr(Object abbr) {
        this.abbr = abbr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
    }

    public ProvinceInfo() {
    }

    protected ProvinceInfo(Parcel in) {
        this.id = in.readString();
        this.code = in.readString();
        this.name = in.readString();
    }

    public static final Creator<ProvinceInfo> CREATOR = new Creator<ProvinceInfo>() {
        public ProvinceInfo createFromParcel(Parcel source) {
            return new ProvinceInfo(source);
        }

        public ProvinceInfo[] newArray(int size) {
            return new ProvinceInfo[size];
        }
    };
}
