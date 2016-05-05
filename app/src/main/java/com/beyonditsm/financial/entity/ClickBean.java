package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by Administrator on 2016/4/6.
 */
@Table(name= "isclick_table")
public class ClickBean implements Parcelable{
    public static final String ORDERID ="orderid";
    @Column
    private String isclick;
    @Column
    private String orderid;

    protected ClickBean(Parcel in) {
        isclick = in.readString();
        orderid = in.readString();
    }

    public ClickBean() {
    }

    public static final Creator<ClickBean> CREATOR = new Creator<ClickBean>() {
        @Override
        public ClickBean createFromParcel(Parcel in) {
            return new ClickBean(in);
        }

        @Override
        public ClickBean[] newArray(int size) {
            return new ClickBean[size];
        }
    };

    public String getIsclick() {
        return isclick;
    }

    public void setIsclick(String isclick) {
        this.isclick = isclick;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(isclick);
        parcel.writeString(orderid);
    }
}
