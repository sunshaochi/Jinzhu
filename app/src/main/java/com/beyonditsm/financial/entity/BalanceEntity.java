package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 收支明细实体类
 * Created by Administrator on 2016/1/18.
 */
public class BalanceEntity {
    private int total;
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
    public static class RowsEntity implements Parcelable{

        /**
         * deductionTHistoryId : 2c908c5c52594e040152594ee8970003
         * businessSubject : 推荐用户
         * createPersonId : -1
         * createTime : 1453197289000
         * deductionAmount : 1000.0
         * orderId :
         * remark :
         * userId : 8aae982851f7dce401520aaf07de0056
         */

        private String deductionTHistoryId;
        private String businessSubject;
        private String createPersonId;
        private long createTime;
        private String deductionAmount;
        private String orderId;
        private String remark;
        private String userId;
        /**
         * unCashAmount : 2.4839999999999997E-4
         * cashAmount : 5.795999999999999E-4
         * cashTHistoryId : 2c908c6d5259c5be015259c86e7b0001
         */

        private Double unCashAmount;
        private String cashAmount;
        private String cashTHistoryId;
        /**
         * remainingSum : 0
         */

        private Double remainingSum;


        protected RowsEntity(Parcel in) {
            deductionTHistoryId = in.readString();
            businessSubject = in.readString();
            createPersonId = in.readString();
            createTime = in.readLong();
            deductionAmount = in.readString();
            orderId = in.readString();
            remark = in.readString();
            userId = in.readString();
        }

        public static final Creator<RowsEntity> CREATOR = new Creator<RowsEntity>() {
            @Override
            public RowsEntity createFromParcel(Parcel in) {
                return new RowsEntity(in);
            }

            @Override
            public RowsEntity[] newArray(int size) {
                return new RowsEntity[size];
            }
        };

        public void setDeductionTHistoryId(String deductionTHistoryId) {
            this.deductionTHistoryId = deductionTHistoryId;
        }

        public void setBusinessSubject(String businessSubject) {
            this.businessSubject = businessSubject;
        }

        public void setCreatePersonId(String createPersonId) {
            this.createPersonId = createPersonId;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setDeductionAmount(String deductionAmount) {
            this.deductionAmount = deductionAmount;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDeductionTHistoryId() {
            return deductionTHistoryId;
        }

        public String getBusinessSubject() {
            return businessSubject;
        }

        public String getCreatePersonId() {
            return createPersonId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public String getDeductionAmount() {
            return deductionAmount;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getRemark() {
            return remark;
        }

        public String getUserId() {
            return userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(deductionTHistoryId);
            dest.writeString(businessSubject);
            dest.writeString(createPersonId);
            dest.writeLong(createTime);
            dest.writeString(deductionAmount);
            dest.writeString(orderId);
            dest.writeString(remark);
            dest.writeString(userId);
        }

        public void setUnCashAmount(Double unCashAmount) {
            this.unCashAmount = unCashAmount;
        }

        public void setCashAmount(String cashAmount) {
            this.cashAmount = cashAmount;
        }

        public void setCashTHistoryId(String cashTHistoryId) {
            this.cashTHistoryId = cashTHistoryId;
        }

        public Double getUnCashAmount() {
            return unCashAmount;
        }

        public String getCashAmount() {
            return cashAmount;
        }

        public String getCashTHistoryId() {
            return cashTHistoryId;
        }

        public void setRemainingSum(Double remainingSum) {
            this.remainingSum = remainingSum;
        }

        public Double getRemainingSum() {
            return remainingSum;
        }
    }
}
