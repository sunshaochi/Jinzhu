package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by wangbin on 15/12/20.
 */
@Table(name = "area_table")
public class AreaInfo implements Parcelable {

    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String cityCode;
    private Object abbr;


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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Object getAbbr() {
        return abbr;
    }

    public void setAbbr(Object abbr) {
        this.abbr = abbr;
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
        dest.writeString(this.cityCode);
    }

    public AreaInfo() {
    }

    protected AreaInfo(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.code = in.readString();
        this.cityCode = in.readString();
        this.abbr = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<AreaInfo> CREATOR = new Creator<AreaInfo>() {
        public AreaInfo createFromParcel(Parcel source) {
            return new AreaInfo(source);
        }

        public AreaInfo[] newArray(int size) {
            return new AreaInfo[size];
        }
    };
}
