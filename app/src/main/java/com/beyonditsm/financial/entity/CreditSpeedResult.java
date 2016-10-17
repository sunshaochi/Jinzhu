package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 极速贷返回
 * Created by Administrator on 2016/10/17 0017.
 */

public class CreditSpeedResult implements Parcelable{
    private int total;
    private List<CreditSpeedEntity> rows;

    protected CreditSpeedResult(Parcel in) {
        total = in.readInt();
        rows = in.createTypedArrayList(CreditSpeedEntity.CREATOR);
    }

    public static final Creator<CreditSpeedResult> CREATOR = new Creator<CreditSpeedResult>() {
        @Override
        public CreditSpeedResult createFromParcel(Parcel in) {
            return new CreditSpeedResult(in);
        }

        @Override
        public CreditSpeedResult[] newArray(int size) {
            return new CreditSpeedResult[size];
        }
    };

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CreditSpeedEntity> getRows() {
        return rows;
    }

    public void setRows(List<CreditSpeedEntity> rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total);
        parcel.writeTypedList(rows);
    }
}
