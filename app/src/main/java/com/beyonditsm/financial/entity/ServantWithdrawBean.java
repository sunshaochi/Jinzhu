package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/12/5.
 */
public class ServantWithdrawBean implements Parcelable{


    /**
     * total : 2
     * rows : [{"commissionType":"名称","amount":"2000","operatTime":"2015-12-04","auditStatus":"审核中","remark":"备注"},"..."]
     */

    private int total;
    /**
     * commissionType : 名称
     * amount : 2000
     * operatTime : 2015-12-04
     * auditStatus : 审核中
     * remark : 备注
     */

    private List<RowsEntity> rows;

    protected ServantWithdrawBean(Parcel in) {
        total = in.readInt();
    }

    public static final Creator<ServantWithdrawBean> CREATOR = new Creator<ServantWithdrawBean>() {
        @Override
        public ServantWithdrawBean createFromParcel(Parcel in) {
            return new ServantWithdrawBean(in);
        }

        @Override
        public ServantWithdrawBean[] newArray(int size) {
            return new ServantWithdrawBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
    }

    public static class RowsEntity {
        private String commissionType;
        private String amount;
        private long operatTime;
        private String auditStatus;
        private String remark;

        public void setCommissionType(String commissionType) {
            this.commissionType = commissionType;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setOperatTime(long operatTime) {
            this.operatTime = operatTime;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCommissionType() {
            return commissionType;
        }

        public String getAmount() {
            return amount;
        }

        public long getOperatTime() {
            return operatTime;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public String getRemark() {
            return remark;
        }
        public RowsEntity() {
        }
    }

    public ServantWithdrawBean() {
    }
}
