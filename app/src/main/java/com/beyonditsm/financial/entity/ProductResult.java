package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 产品返回
 * Created by wangbin on 15/11/26.
 */
public class ProductResult implements Parcelable {

    private int total;
    private List<ProductInfo> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProductInfo> getRows() {
        return rows;
    }

    public void setRows(List<ProductInfo> rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeTypedList(rows);
    }

    public ProductResult() {
    }

    protected ProductResult(Parcel in) {
        this.total = in.readInt();
        this.rows = in.createTypedArrayList(ProductInfo.CREATOR);
    }

    public static final Parcelable.Creator<ProductResult> CREATOR = new Parcelable.Creator<ProductResult>() {
        public ProductResult createFromParcel(Parcel source) {
            return new ProductResult(source);
        }

        public ProductResult[] newArray(int size) {
            return new ProductResult[size];
        }
    };
}
