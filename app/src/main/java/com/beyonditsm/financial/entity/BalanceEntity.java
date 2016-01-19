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
        private Double deductionAmount;
        private String orderId;
        private String remark;
        private String userId;

        protected RowsEntity(Parcel in) {
            deductionTHistoryId = in.readString();
            businessSubject = in.readString();
            createPersonId = in.readString();
            createTime = in.readLong();
            deductionAmount = in.readDouble();
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

        public void setDeductionAmount(double deductionAmount) {
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

        public double getDeductionAmount() {
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
            dest.writeDouble(deductionAmount);
            dest.writeString(orderId);
            dest.writeString(remark);
            dest.writeString(userId);
        }
    }
}
