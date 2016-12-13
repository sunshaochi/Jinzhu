package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 贷款产品实体
 * Q164454216
 * Created by xiaoke on 2016/12/11.
 */

public class OrderBean2 implements Parcelable {
  private String  orderNo;
    private String        orderId;
    private String deductibleInterest;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeductibleInterest() {
        return deductibleInterest;
    }

    public void setDeductibleInterest(String deductibleInterest) {
        this.deductibleInterest = deductibleInterest;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNo);
        dest.writeString(this.orderId);
        dest.writeString(this.deductibleInterest);
    }

    public OrderBean2() {
    }

    protected OrderBean2(Parcel in) {
        this.orderNo = in.readString();
        this.orderId = in.readString();
        this.deductibleInterest = in.readString();
    }

    public static final Creator<OrderBean2> CREATOR = new Creator<OrderBean2>() {
        @Override
        public OrderBean2 createFromParcel(Parcel source) {
            return new OrderBean2(source);
        }

        @Override
        public OrderBean2[] newArray(int size) {
            return new OrderBean2[size];
        }
    };
}
