package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gxy on 2016/1/7.
 */
public class DictionaryType implements Parcelable{
    /**
     * "id":"2c908c6e50d077f80150d082348a000f",
     * "code":"",
     * "name":"无房产",
     * "type":"under_own_hour",
     * "describ":"名下房产",
     * "isValid":1
     */

    private String id;
    private String code;
    private String name;
    private String type;
    private String describ;
    private String isValid;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    protected DictionaryType(Parcel in) {
        id = in.readString();
        code = in.readString();
        name = in.readString();
        type = in.readString();
        describ = in.readString();
        isValid = in.readString();
    }

    public static final Creator<DictionaryType> CREATOR = new Creator<DictionaryType>() {
        @Override
        public DictionaryType createFromParcel(Parcel in) {
            return new DictionaryType(in);
        }

        @Override
        public DictionaryType[] newArray(int size) {
            return new DictionaryType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(describ);
        dest.writeString(isValid);
    }
}
