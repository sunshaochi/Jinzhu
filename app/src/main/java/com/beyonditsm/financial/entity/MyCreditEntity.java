package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyCreditEntity implements Parcelable {

    private int page;
    private int rows;


    protected MyCreditEntity(Parcel in) {
        this.page = in.readInt();
        this.rows = in.readInt();
    }

    public static final Creator<MyCreditEntity> CREATOR = new Creator<MyCreditEntity>() {
        @Override
        public MyCreditEntity createFromParcel(Parcel in) {
            return new MyCreditEntity(in);
        }

        @Override
        public MyCreditEntity[] newArray(int size) {
            return new MyCreditEntity[size];
        }
    };

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

    public MyCreditEntity( int page, int rows) {

        this.page = page;
        this.rows = rows;
    }

    public MyCreditEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.page);
        dest.writeInt(this.rows);
    }
}
