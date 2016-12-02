package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 产品返回
 * Created by wangbin on 15/11/26.
 */
public class ProductResult implements Parcelable {

    public ProductResult() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProductBean> getRows() {
        return rows;
    }

    public void setRows(List<ProductBean> rows) {
        this.rows = rows;
    }

    private int total;
    private List<ProductBean> rows;


    protected ProductResult(Parcel in) {
        total = in.readInt();
        rows = in.createTypedArrayList(ProductBean.CREATOR);
    }

    public static final Creator<ProductResult> CREATOR = new Creator<ProductResult>() {
        @Override
        public ProductResult createFromParcel(Parcel in) {
            return new ProductResult(in);
        }

        @Override
        public ProductResult[] newArray(int size) {
            return new ProductResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeTypedList(rows);
    }
}
