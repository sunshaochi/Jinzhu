package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Administrator on 2015/12/5.
 */
public class ServantWithdrawEntity implements Parcelable{

    private Date startTime;
    private Date endTime;
    private int page;
    private int rows;

    public ServantWithdrawEntity() {
    }

    protected ServantWithdrawEntity(Parcel in) {
        page = in.readInt();
        rows = in.readInt();

    }

    public static final Creator<ServantWithdrawEntity> CREATOR = new Creator<ServantWithdrawEntity>() {
        @Override
        public ServantWithdrawEntity createFromParcel(Parcel in) {
            return new ServantWithdrawEntity(in);
        }

        @Override
        public ServantWithdrawEntity[] newArray(int size) {
            return new ServantWithdrawEntity[size];
        }
    };



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
