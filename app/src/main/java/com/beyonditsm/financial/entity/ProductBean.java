package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2016/12/2.
 */

public class ProductBean implements Parcelable{
    private String productId;//产品id
    private  String productNo;//产品编号
    private  String productName;//产品名称
    private String minLoanRate;//最小利率
    private  String maxLoanRate;//最大利率
    private  String loanPeriodType;//贷款期限类型
    private String minLoanPeriod;//最小贷款时间
    private String maxLoanPeriod;//最大贷款时间
    private String guaranteeType;//担保方式
    private String minLoanAmt;//最小贷款金额
    private String maxLoanAmt;//最大贷款金额
    private  String applyCondDesc;//这是申贷条件
    private  String applyDetailDesc;//:这是详细说明
    private String applyMaterialDesc;// 申贷所需材料
    private String preLoanPeriod;//放款周期（工作日）
    private List<String> paymentTerm;//还款方式
    private  String productLogo;//贷款log
    private String disposableRateMin;//一次性费率最小
    private String disposableRateMax;//一次性费率最大值
    private String productType;//( 0代表大额带1代表急借通)


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ProductBean() {
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getDisposableRateMin() {
        return disposableRateMin;
    }

    public void setDisposableRateMin(String disposableRateMin) {
        this.disposableRateMin = disposableRateMin;
    }

    public String getDisposableRateMax() {
        return disposableRateMax;
    }

    public void setDisposableRateMax(String disposableRateMax) {
        this.disposableRateMax = disposableRateMax;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMinLoanRate() {
        return minLoanRate;
    }

    public void setMinLoanRate(String minLoanRate) {
        this.minLoanRate = minLoanRate;
    }

    public String getMaxLoanRate() {
        return maxLoanRate;
    }

    public void setMaxLoanRate(String maxLoanRate) {
        this.maxLoanRate = maxLoanRate;
    }

    public String getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(String loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public String getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(String minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public String getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    public void setMaxLoanPeriod(String maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
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

    public String getApplyCondDesc() {
        return applyCondDesc;
    }

    public void setApplyCondDesc(String applyCondDesc) {
        this.applyCondDesc = applyCondDesc;
    }

    public String getApplyDetailDesc() {
        return applyDetailDesc;
    }

    public void setApplyDetailDesc(String applyDetailDesc) {
        this.applyDetailDesc = applyDetailDesc;
    }

    public String getApplyMaterialDesc() {
        return applyMaterialDesc;
    }

    public void setApplyMaterialDesc(String applyMaterialDesc) {
        this.applyMaterialDesc = applyMaterialDesc;
    }

    public String getPreLoanPeriod() {
        return preLoanPeriod;
    }

    public void setPreLoanPeriod(String preLoanPeriod) {
        this.preLoanPeriod = preLoanPeriod;
    }

    public List<String> getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(List<String> paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productNo);
        dest.writeString(this.productName);
        dest.writeString(this.minLoanRate);
        dest.writeString(this.maxLoanRate);
        dest.writeString(this.loanPeriodType);
        dest.writeString(this.minLoanPeriod);
        dest.writeString(this.maxLoanPeriod);
        dest.writeString(this.guaranteeType);
        dest.writeString(this.minLoanAmt);
        dest.writeString(this.maxLoanAmt);
        dest.writeString(this.applyCondDesc);
        dest.writeString(this.applyDetailDesc);
        dest.writeString(this.applyMaterialDesc);
        dest.writeString(this.preLoanPeriod);
        dest.writeStringList(this.paymentTerm);
        dest.writeString(this.productLogo);
        dest.writeString(this.disposableRateMin);
        dest.writeString(this.disposableRateMax);
        dest.writeString(this.productType);
    }

    protected ProductBean(Parcel in) {
        this.productId = in.readString();
        this.productNo = in.readString();
        this.productName = in.readString();
        this.minLoanRate = in.readString();
        this.maxLoanRate = in.readString();
        this.loanPeriodType = in.readString();
        this.minLoanPeriod = in.readString();
        this.maxLoanPeriod = in.readString();
        this.guaranteeType = in.readString();
        this.minLoanAmt = in.readString();
        this.maxLoanAmt = in.readString();
        this.applyCondDesc = in.readString();
        this.applyDetailDesc = in.readString();
        this.applyMaterialDesc = in.readString();
        this.preLoanPeriod = in.readString();
        this.productLogo = in.readString();
        this.disposableRateMin = in.readString();
        this.disposableRateMax = in.readString();
        this.productType = in.readString();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
}
