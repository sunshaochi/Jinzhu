package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class ProductBean2 implements Parcelable {
    /**
     * 订单的产品信息ID
     */
    private String orderProductId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 利率类型(1.月利率2.日利率)
     */
    private int loanRateType;

    /**
     * 最大月利率
     */
    private String  maxRate;

    /**
     * 最小月利率
     */
    private String minRate;

    /**
     * 平台收费，单位%
     */
    private String platRate;

    /**
     * 手续费，单位%
     */
    private String feeRate;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品LOGO
     */
    private String productLogo;

    /**
     * 最低贷款金额
     */
    private String minLoanAmt;

    /**
     * 最大贷款金额
     */
    private String maxLoanAmt;

    /**
     * 产品机构
     */
    private String orgName;

    /**
     * 担保方式
     */
    private String guaranteeWay;

    /**
     * 放款时长，单位天
     */
    private int grantDays;

    /**
     * 借款用途
     */
    private String loanUser;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 还款方式
     */
    private String paymentTerm;

    /**
     * 订单的完整产品信息，json格式存储
     */
    private String productInfo;

    /**
     * 渠道返俑比例
     */
    private String commissionRate;

    /**
     * 机构返佣比例
     */
    private String orgCommissionRate;

    /**
     *产品编号
     */
    private String productNo;


    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getLoanRateType() {
        return loanRateType;
    }

    public void setLoanRateType(int loanRateType) {
        this.loanRateType = loanRateType;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getPlatRate() {
        return platRate;
    }

    public void setPlatRate(String platRate) {
        this.platRate = platRate;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getMinLoanAmt() {
        return minLoanAmt;
    }

    public void setMinLoanAmt(String minLoanAmt) {
        this.minLoanAmt = minLoanAmt;
    }

    public String getMaxLoanAmt() {
        return maxLoanAmt;
    }

    public void setMaxLoanAmt(String maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGuaranteeWay() {
        return guaranteeWay;
    }

    public void setGuaranteeWay(String guaranteeWay) {
        this.guaranteeWay = guaranteeWay;
    }

    public int getGrantDays() {
        return grantDays;
    }

    public void setGrantDays(int grantDays) {
        this.grantDays = grantDays;
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getOrgCommissionRate() {
        return orgCommissionRate;
    }

    public void setOrgCommissionRate(String orgCommissionRate) {
        this.orgCommissionRate = orgCommissionRate;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderProductId);
        dest.writeString(this.orderId);
        dest.writeInt(this.loanRateType);
        dest.writeString(this.maxRate);
        dest.writeString(this.minRate);
        dest.writeString(this.platRate);
        dest.writeString(this.feeRate);
        dest.writeString(this.productName);
        dest.writeString(this.productLogo);
        dest.writeString(this.minLoanAmt);
        dest.writeString(this.maxLoanAmt);
        dest.writeString(this.orgName);
        dest.writeString(this.guaranteeWay);
        dest.writeInt(this.grantDays);
        dest.writeString(this.loanUser);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.paymentTerm);
        dest.writeString(this.productInfo);
        dest.writeString(this.commissionRate);
        dest.writeString(this.orgCommissionRate);
        dest.writeString(this.productNo);
    }

    public ProductBean2() {
    }

    protected ProductBean2(Parcel in) {
        this.orderProductId = in.readString();
        this.orderId = in.readString();
        this.loanRateType = in.readInt();
        this.maxRate = in.readString();
        this.minRate = in.readString();
        this.platRate = in.readString();
        this.feeRate = in.readString();
        this.productName = in.readString();
        this.productLogo = in.readString();
        this.minLoanAmt = in.readString();
        this.maxLoanAmt = in.readString();
        this.orgName = in.readString();
        this.guaranteeWay = in.readString();
        this.grantDays = in.readInt();
        this.loanUser = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.paymentTerm = in.readString();
        this.productInfo = in.readString();
        this.commissionRate = in.readString();
        this.orgCommissionRate = in.readString();
        this.productNo = in.readString();
    }

    public static final Creator<ProductBean2> CREATOR = new Creator<ProductBean2>() {
        @Override
        public ProductBean2 createFromParcel(Parcel source) {
            return new ProductBean2(source);
        }

        @Override
        public ProductBean2[] newArray(int size) {
            return new ProductBean2[size];
        }
    };
}
