package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 市
 * Created by wangbin on 15/12/20.
 */
@Table(name = "city_table")
public class CityInfo implements Parcelable {
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String provinceCode;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.provinceCode);
    }

    public CityInfo() {
    }

    protected CityInfo(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.code = in.readString();
        this.provinceCode = in.readString();
    }

    public static final Creator<CityInfo> CREATOR = new Creator<CityInfo>() {
        public CityInfo createFromParcel(Parcel source) {
            return new CityInfo(source);
        }

        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };
}
