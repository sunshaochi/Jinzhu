package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单实体类
 * Created by Yang on 2015/11/28 0028.
 */
public class OrderBean implements Parcelable{
    private String id;
    private String clientId;
    private String createPersonId;//创建人
    private String createTime;//创建时间
    private String creditManagerId;
    private String customerEvaluate;//管理平台对客户的评价
    private String expectedFrequency;//预期违约率 单位百分比 1 代表 1%
    private String isValid;//是否有效，默认值1；1为有效，0为无效
    private String modifyPersonId;//修改人
    private String modifyTime;//修改时间
    private String orderNo;//订单编号
    private String orderSts;//订单状态 在枚举类OrderStatus里面定义
    private String periodsAmount;//单期还款金额 单位元
    private String practicalLoan;//实际放款额 单位元
    private String practicalLoanDate;//实际放款日期
    private String productId;
    private String totalAmount;//总金额 单位 元
    private String totalPeriods;//总期数 单位 月

    private String userName;//用户姓名
    private String bankName;//银行名称
    private String bankCardNo;//银行卡号
    private Double cashOutAmount;//现金兑换数量
    private String deductibleInterest;//可抵扣利息
    private String totalLoanInterest;//总贷款利息
    private String orderId;//订单Id

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

    public String getTotalLoanInterest() {
        return totalLoanInterest;
    }

    public void setTotalLoanInterest(String totalLoanInterest) {
        this.totalLoanInterest = totalLoanInterest;
    }

    public Double getCashOutAmount() {
        return cashOutAmount;
    }

    public void setCashOutAmount(Double cashOutAmount) {
        this.cashOutAmount = cashOutAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreditManagerId() {
        return creditManagerId;
    }

    public void setCreditManagerId(String creditManagerId) {
        this.creditManagerId = creditManagerId;
    }

    public String getCustomerEvaluate() {
        return customerEvaluate;
    }

    public void setCustomerEvaluate(String customerEvaluate) {
        this.customerEvaluate = customerEvaluate;
    }

    public String getExpectedFrequency() {
        return expectedFrequency;
    }

    public void setExpectedFrequency(String expectedFrequency) {
        this.expectedFrequency = expectedFrequency;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(String orderSts) {
        this.orderSts = orderSts;
    }

    public String getPeriodsAmount() {
        return periodsAmount;
    }

    public void setPeriodsAmount(String periodsAmount) {
        this.periodsAmount = periodsAmount;
    }

    public String getPracticalLoan() {
        return practicalLoan;
    }

    public void setPracticalLoan(String practicalLoan) {
        this.practicalLoan = practicalLoan;
    }

    public String getPracticalLoanDate() {
        return practicalLoanDate;
    }

    public void setPracticalLoanDate(String practicalLoanDate) {
        this.practicalLoanDate = practicalLoanDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.clientId);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTime);
        dest.writeString(this.creditManagerId);
        dest.writeString(this.customerEvaluate);
        dest.writeString(this.expectedFrequency);
        dest.writeString(this.isValid);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.orderNo);
        dest.writeString(this.orderSts);
        dest.writeString(this.periodsAmount);
        dest.writeString(this.practicalLoan);
        dest.writeString(this.practicalLoanDate);
        dest.writeString(this.productId);
        dest.writeString(this.totalAmount);
        dest.writeString(this.totalPeriods);
        dest.writeString(this.userName);
        dest.writeString(this.bankName);
        dest.writeString(this.bankCardNo);
        dest.writeValue(this.cashOutAmount);
        dest.writeString(this.deductibleInterest);
        dest.writeString(this.totalLoanInterest);
        dest.writeString(this.orderId);
    }

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        this.id = in.readString();
        this.clientId = in.readString();
        this.createPersonId = in.readString();
        this.createTime = in.readString();
        this.creditManagerId = in.readString();
        this.customerEvaluate = in.readString();
        this.expectedFrequency = in.readString();
        this.isValid = in.readString();
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.orderNo = in.readString();
        this.orderSts = in.readString();
        this.periodsAmount = in.readString();
        this.practicalLoan = in.readString();
        this.practicalLoanDate = in.readString();
        this.productId = in.readString();
        this.totalAmount = in.readString();
        this.totalPeriods = in.readString();
        this.userName=in.readString();
        this.bankName=in.readString();
        this.bankCardNo=in.readString();
        this.cashOutAmount= (Double) in.readValue(Double.class.getClassLoader());
        this.deductibleInterest=in.readString();
        this.totalLoanInterest=in.readString();
        this.orderId=in.readString();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
