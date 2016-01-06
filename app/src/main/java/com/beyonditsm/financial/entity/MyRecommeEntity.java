package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sunshaochi-1 on 2015/11/26.
 */
public class MyRecommeEntity implements Parcelable{
    private int page;
    private int rows;

    public MyRecommeEntity() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    protected MyRecommeEntity(Parcel in) {
        page = in.readInt();
        rows = in.readInt();
    }

    public static final Creator<MyRecommeEntity> CREATOR = new Creator<MyRecommeEntity>() {
        @Override
        public MyRecommeEntity createFromParcel(Parcel in) {
            return new MyRecommeEntity(in);
        }

        @Override
        public MyRecommeEntity[] newArray(int size) {
            return new MyRecommeEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(rows);
    }
}
