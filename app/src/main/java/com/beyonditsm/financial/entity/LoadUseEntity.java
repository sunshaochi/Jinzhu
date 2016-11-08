package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 借款用途entity
 * Created by Administrator on 2016/10/17 0017.
 */

public class LoadUseEntity implements Parcelable{

    /**
     * name : 医疗
     * type : loan_use
     * id : 524
     */

    private String name;
    private String type;
    private String id;

    protected LoadUseEntity(Parcel in) {
        name = in.readString();
        type = in.readString();
        id = in.readString();
    }

    public static final Creator<LoadUseEntity> CREATOR = new Creator<LoadUseEntity>() {
        @Override
        public LoadUseEntity createFromParcel(Parcel in) {
            return new LoadUseEntity(in);
        }

        @Override
        public LoadUseEntity[] newArray(int size) {
            return new LoadUseEntity[size];
        }
    };

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(id);
    }
}
