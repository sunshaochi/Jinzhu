package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/15.
 */

public class HomeHotProductBean implements Parcelable {
    private String minLoanAmt;//: 10000,
    private String orgName;//": "测试机构勿动",
    private String pcProductLogo;//": "img/3038173465019392.png",
    private String productId;//: "142e7774d7c14ced91bba5cf10638c10",
    private String loanPeriodType;//": 0,
    private String productName;//": "测试产品勿动",
    private String minLoanPeriod;//": 1,//最小利率
    private String applyMaterialDesc;//": "申贷所需材料",
    private String avgSucRate;//": 99,
    private String androidProductLogo;//": "img/3038173526000640.png",
    private String guaranteeType;//": "1",
    private String applyCondDesc;//": "这是申贷条件",
    private String maxLoanAmt;//": 100000,
    private String maxLoanRate;//": 12,最大利率
    private String productNO;//": "Test001",
    private String preLoanPeriod;//": 0,放款周期
    private String applyDetailDesc;//": "这是详细说明"


    public String getMinLoanAmt() {
        return minLoanAmt;
    }

    public void setMinLoanAmt(String minLoanAmt) {
        this.minLoanAmt = minLoanAmt;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPcProductLogo() {
        return pcProductLogo;
    }

    public void setPcProductLogo(String pcProductLogo) {
        this.pcProductLogo = pcProductLogo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(String loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(String minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public String getApplyMaterialDesc() {
        return applyMaterialDesc;
    }

    public void setApplyMaterialDesc(String applyMaterialDesc) {
        this.applyMaterialDesc = applyMaterialDesc;
    }

    public String getAvgSucRate() {
        return avgSucRate;
    }

    public void setAvgSucRate(String avgSucRate) {
        this.avgSucRate = avgSucRate;
    }

    public String getAndroidProductLogo() {
        return androidProductLogo;
    }

    public void setAndroidProductLogo(String androidProductLogo) {
        this.androidProductLogo = androidProductLogo;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getApplyCondDesc() {
        return applyCondDesc;
    }

    public void setApplyCondDesc(String applyCondDesc) {
        this.applyCondDesc = applyCondDesc;
    }

    public String getMaxLoanAmt() {
        return maxLoanAmt;
    }

    public void setMaxLoanAmt(String maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public String getMaxLoanRate() {
        return maxLoanRate;
    }

    public void setMaxLoanRate(String maxLoanRate) {
        this.maxLoanRate = maxLoanRate;
    }

    public String getProductNO() {
        return productNO;
    }

    public void setProductNO(String productNO) {
        this.productNO = productNO;
    }

    public String getPreLoanPeriod() {
        return preLoanPeriod;
    }

    public void setPreLoanPeriod(String preLoanPeriod) {
        this.preLoanPeriod = preLoanPeriod;
    }

    public String getApplyDetailDesc() {
        return applyDetailDesc;
    }

    public void setApplyDetailDesc(String applyDetailDesc) {
        this.applyDetailDesc = applyDetailDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.minLoanAmt);
        dest.writeString(this.orgName);
        dest.writeString(this.pcProductLogo);
        dest.writeString(this.productId);
        dest.writeString(this.loanPeriodType);
        dest.writeString(this.productName);
        dest.writeString(this.minLoanPeriod);
        dest.writeString(this.applyMaterialDesc);
        dest.writeString(this.avgSucRate);
        dest.writeString(this.androidProductLogo);
        dest.writeString(this.guaranteeType);
        dest.writeString(this.applyCondDesc);
        dest.writeString(this.maxLoanAmt);
        dest.writeString(this.maxLoanRate);
        dest.writeString(this.productNO);
        dest.writeString(this.preLoanPeriod);
        dest.writeString(this.applyDetailDesc);
    }

    public HomeHotProductBean() {
    }

    protected HomeHotProductBean(Parcel in) {
        this.minLoanAmt = in.readString();
        this.orgName = in.readString();
        this.pcProductLogo = in.readString();
        this.productId = in.readString();
        this.loanPeriodType = in.readString();
        this.productName = in.readString();
        this.minLoanPeriod = in.readString();
        this.applyMaterialDesc = in.readString();
        this.avgSucRate = in.readString();
        this.androidProductLogo = in.readString();
        this.guaranteeType = in.readString();
        this.applyCondDesc = in.readString();
        this.maxLoanAmt = in.readString();
        this.maxLoanRate = in.readString();
        this.productNO = in.readString();
        this.preLoanPeriod = in.readString();
        this.applyDetailDesc = in.readString();
    }

    public static final Creator<HomeHotProductBean> CREATOR = new Creator<HomeHotProductBean>() {
        @Override
        public HomeHotProductBean createFromParcel(Parcel source) {
            return new HomeHotProductBean(source);
        }

        @Override
        public HomeHotProductBean[] newArray(int size) {
            return new HomeHotProductBean[size];
        }
    };
}
