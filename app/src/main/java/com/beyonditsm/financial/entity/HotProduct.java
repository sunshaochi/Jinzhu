package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HotProduct implements Parcelable {
    private int page;
    private int rows;

    public HotProduct() {
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

    protected HotProduct(Parcel in) {
        page = in.readInt();
        rows = in.readInt();
    }

    public static final Creator<HotProduct> CREATOR = new Creator<HotProduct>() {
        @Override
        public HotProduct createFromParcel(Parcel in) {
            return new HotProduct(in);
        }

        @Override
        public HotProduct[] newArray(int size) {
            return new HotProduct[size];
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
