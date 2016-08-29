package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyCreditBean implements Parcelable {

    /**
     * total : 10
     * rows : [{"totalAmount":100000,"orderNo":"dk2606466316731392","periodsAmount":8000,"name":"已提交","manaTel":"15637843298","totalPeriods":12,"productName":"新时代","manaName":"张三丰"}]
     */

    private int total;
    /**
     * totalAmount : 100000
     * orderNo : dk2606466316731392
     * periodsAmount : 8000
     * name : 已提交
     * manaTel : 15637843298
     * totalPeriods : 12
     * productName : 新时代
     * manaName : 张三丰
     */

    private List<RowsEntity> rows;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public static class RowsEntity implements Parcelable {
        private String totalAmount;
        private String orderNo;
        private String orderSts;
        private String periodsAmount;
        private String name;
        private String manaTel;
        private String totalPeriods;
        private String productName;
        private String manaName;
        private String id;
//        private String orderType;
        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public void setPeriodsAmount(String periodsAmount) {
            this.periodsAmount = periodsAmount;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setManaTel(String manaTel) {
            this.manaTel = manaTel;
        }

        public void setTotalPeriods(String totalPeriods) {
            this.totalPeriods = totalPeriods;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setManaName(String manaName) {
            this.manaName = manaName;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getPeriodsAmount() {
            return periodsAmount;
        }

        public String getName() {
            return name;
        }

        public String getManaTel() {
            return manaTel;
        }

        public String getTotalPeriods() {
            return totalPeriods;
        }

        public String getProductName() {
            return productName;
        }

        public String getManaName() {
            return manaName;
        }

        public String getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(String orderSts) {
            this.orderSts = orderSts;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
//
//        public String getOrderType() {
//            return orderType;
//        }
//
//        public void setOrderType(String orderType) {
//            this.orderType = orderType;
//        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.totalAmount);
            dest.writeString(this.orderNo);
            dest.writeString(this.orderSts);
            dest.writeString(this.periodsAmount);
            dest.writeString(this.name);
            dest.writeString(this.manaTel);
            dest.writeString(this.totalPeriods);
            dest.writeString(this.productName);
            dest.writeString(this.manaName);
            dest.writeString(this.id);
//            dest.writeString(this.orderType);
        }

        public RowsEntity() {
        }

        protected RowsEntity(Parcel in) {
            this.totalAmount = in.readString();
            this.orderNo = in.readString();
            this.orderSts = in.readString();
            this.periodsAmount = in.readString();
            this.name = in.readString();
            this.manaTel = in.readString();
            this.totalPeriods = in.readString();
            this.productName = in.readString();
            this.manaName = in.readString();
            this.id = in.readString();
//            this.orderType = in.readString();
        }

        public static final Parcelable.Creator<RowsEntity> CREATOR = new Parcelable.Creator<RowsEntity>() {
            public RowsEntity createFromParcel(Parcel source) {
                return new RowsEntity(source);
            }

            public RowsEntity[] newArray(int size) {
                return new RowsEntity[size];
            }
        };
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

    public MyCreditBean() {
    }

    protected MyCreditBean(Parcel in) {
        this.total = in.readInt();
        this.rows = in.createTypedArrayList(RowsEntity.CREATOR);
    }

    public static final Parcelable.Creator<MyCreditBean> CREATOR = new Parcelable.Creator<MyCreditBean>() {
        public MyCreditBean createFromParcel(Parcel source) {
            return new MyCreditBean(source);
        }

        public MyCreditBean[] newArray(int size) {
            return new MyCreditBean[size];
        }
    };
}
