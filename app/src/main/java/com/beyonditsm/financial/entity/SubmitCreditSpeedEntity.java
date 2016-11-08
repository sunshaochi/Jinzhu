package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 提交极速贷订单
 * Created by Administrator on 2016/10/18 0018.
 */

public class SubmitCreditSpeedEntity  implements Parcelable{
    private String purpose;//借款用途
    private String maxRepaymentWeekly;//最大承受还款额度
    private String totalAmount;//贷款金额
    private String totalPeriods;//还款期限
    private String totalLoanInterest;//利息
    private String realMonthlyRate;//综合费率

    public SubmitCreditSpeedEntity() {

    }

    private SubmitCreditSpeedEntity(Parcel in) {
        purpose = in.readString();
        maxRepaymentWeekly = in.readString();
        totalAmount = in.readString();
        totalPeriods = in.readString();
        totalLoanInterest = in.readString();
        realMonthlyRate = in.readString();
    }

    public static final Creator<SubmitCreditSpeedEntity> CREATOR = new Creator<SubmitCreditSpeedEntity>() {
        @Override
        public SubmitCreditSpeedEntity createFromParcel(Parcel in) {
            return new SubmitCreditSpeedEntity(in);
        }

        @Override
        public SubmitCreditSpeedEntity[] newArray(int size) {
            return new SubmitCreditSpeedEntity[size];
        }
    };

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMaxRepaymentWeekly() {
        return maxRepaymentWeekly;
    }

    public void setMaxRepaymentWeekly(String maxRepaymentWeekly) {
        this.maxRepaymentWeekly = maxRepaymentWeekly;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(String totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public String getTotalLoanInterest() {
        return totalLoanInterest;
    }

    public void setTotalLoanInterest(String totalLoanInterest) {
        this.totalLoanInterest = totalLoanInterest;
    }

    public String getRealMonthlyRate() {
        return realMonthlyRate;
    }

    public void setRealMonthlyRate(String realMonthlyRate) {
        this.realMonthlyRate = realMonthlyRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(purpose);
        parcel.writeString(maxRepaymentWeekly);
        parcel.writeString(totalAmount);
        parcel.writeString(totalPeriods);
        parcel.writeString(totalLoanInterest);
        parcel.writeString(realMonthlyRate);
    }
}
