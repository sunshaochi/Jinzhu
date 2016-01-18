package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class OrderListEntity {
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
        private String ORDER_ID;//订单号
        private String USER_ID;//用户ID
        private String CASH_OUT_AMOUNT;//提现金额
        private String ORDER_STS;//订单状态
        private String CREATE_TIME;//创建时间
        private String O_TYPE;//订单类型  现金兑换，抵扣兑换

        protected RowsEntity(Parcel in) {
            ORDER_ID = in.readString();
            USER_ID = in.readString();
            CASH_OUT_AMOUNT = in.readString();
            ORDER_STS = in.readString();
            CREATE_TIME = in.readString();
            O_TYPE = in.readString();
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

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getCASH_OUT_AMOUNT() {
            return CASH_OUT_AMOUNT;
        }

        public void setCASH_OUT_AMOUNT(String CASH_OUT_AMOUNT) {
            this.CASH_OUT_AMOUNT = CASH_OUT_AMOUNT;
        }

        public String getORDER_STS() {
            return ORDER_STS;
        }

        public void setORDER_STS(String ORDER_STS) {
            this.ORDER_STS = ORDER_STS;
        }

        public String getCREATE_TIME() {
            return CREATE_TIME;
        }

        public void setCREATE_TIME(String CREATE_TIME) {
            this.CREATE_TIME = CREATE_TIME;
        }

        public String getO_TYPE() {
            return O_TYPE;
        }

        public void setO_TYPE(String o_TYPE) {
            O_TYPE = o_TYPE;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ORDER_ID);
            dest.writeString(USER_ID);
            dest.writeString(CASH_OUT_AMOUNT);
            dest.writeString(ORDER_STS);
            dest.writeString(CREATE_TIME);
            dest.writeString(O_TYPE);
        }
    }


}
