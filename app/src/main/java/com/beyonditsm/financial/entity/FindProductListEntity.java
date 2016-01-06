package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 信贷经理通过产品表相关参数查询产品信息
 * Created by Yang on 2015/11/24 0024.
 */
public class FindProductListEntity implements Parcelable{


    private ProductInfo product;
    private int page;
    private int rows;

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
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
        dest.writeParcelable(this.product, 0);
        dest.writeInt(this.page);
        dest.writeInt(this.rows);
    }

    public FindProductListEntity() {
    }

    protected FindProductListEntity(Parcel in) {
        this.product = in.readParcelable(ProductInfo.class.getClassLoader());
        this.page = in.readInt();
        this.rows = in.readInt();
    }

    public static final Creator<FindProductListEntity> CREATOR = new Creator<FindProductListEntity>() {
        public FindProductListEntity createFromParcel(Parcel source) {
            return new FindProductListEntity(source);
        }

        public FindProductListEntity[] newArray(int size) {
            return new FindProductListEntity[size];
        }
    };
}
