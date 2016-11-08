package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 基本信息entity
 * Created by Administrator on 2016/10/19 0019.
 */

public class UserOrderInfo1 implements Parcelable{
    private String productId;//产品Id
    private String purpose;//借款用途
    private String maxRepaymentWeekly;//最大承受还款额度
    private String totalAmount;//贷款金额
    private String totalPeriods;//还款期限
    private String totalLoanInterest;//利息
    private String realMonthlyRate;//综合费率
    private String name;// 用户姓名
    private String contactNum;//联系电话
    private String idcardno;//身份证号
    private String marriagests;//婚姻状况
    private String qualitications;//学历
    private String childNum;//子女数 （int类型）
    private String domicileProvince;//户籍地省
    private String domicileCity;//户籍地市
    private String domicileArea;//户籍地区
    private String domicileDetail;//户籍地详细信息
    private String permanentProvince;//常住地省
    private String permanentCity;//常住地市
    private String permanentArea;//常住地区
    private String permanentDetail;//常住地详细地址
    private String resSts;//居住状况

    public UserOrderInfo1() {
    }

    protected UserOrderInfo1(Parcel in) {
        productId = in.readString();
        purpose = in.readString();
        maxRepaymentWeekly = in.readString();
        totalAmount = in.readString();
        totalPeriods = in.readString();
        totalLoanInterest = in.readString();
        realMonthlyRate = in.readString();
        name = in.readString();
        contactNum = in.readString();
        idcardno = in.readString();
        marriagests = in.readString();
        qualitications = in.readString();
        childNum = in.readString();
        domicileProvince = in.readString();
        domicileCity = in.readString();
        domicileArea = in.readString();
        domicileDetail = in.readString();
        permanentProvince = in.readString();
        permanentCity = in.readString();
        permanentArea = in.readString();
        permanentDetail = in.readString();
        resSts = in.readString();
    }

    public static final Creator<UserOrderInfo1> CREATOR = new Creator<UserOrderInfo1>() {
        @Override
        public UserOrderInfo1 createFromParcel(Parcel in) {
            return new UserOrderInfo1(in);
        }

        @Override
        public UserOrderInfo1[] newArray(int size) {
            return new UserOrderInfo1[size];
        }
    };


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getMarriagests() {
        return marriagests;
    }

    public void setMarriagests(String marriagests) {
        this.marriagests = marriagests;
    }

    public String getQualitications() {
        return qualitications;
    }

    public void setQualitications(String qualitications) {
        this.qualitications = qualitications;
    }

    public String getChildNum() {
        return childNum;
    }

    public void setChildNum(String childNum) {
        this.childNum = childNum;
    }

    public String getDomicileProvince() {
        return domicileProvince;
    }

    public void setDomicileProvince(String domicileProvince) {
        this.domicileProvince = domicileProvince;
    }

    public String getDomicileCity() {
        return domicileCity;
    }

    public void setDomicileCity(String domicileCity) {
        this.domicileCity = domicileCity;
    }

    public String getDomicileArea() {
        return domicileArea;
    }

    public void setDomicileArea(String domicileArea) {
        this.domicileArea = domicileArea;
    }

    public String getDomicileDetail() {
        return domicileDetail;
    }

    public void setDomicileDetail(String domicileDetail) {
        this.domicileDetail = domicileDetail;
    }

    public String getPermanentProvince() {
        return permanentProvince;
    }

    public void setPermanentProvince(String permanentProvince) {
        this.permanentProvince = permanentProvince;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentArea() {
        return permanentArea;
    }

    public void setPermanentArea(String permanentArea) {
        this.permanentArea = permanentArea;
    }

    public String getPermanentDetail() {
        return permanentDetail;
    }

    public void setPermanentDetail(String permanentDetail) {
        this.permanentDetail = permanentDetail;
    }

    public String getResSts() {
        return resSts;
    }

    public void setResSts(String resSts) {
        this.resSts = resSts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(purpose);
        parcel.writeString(maxRepaymentWeekly);
        parcel.writeString(totalAmount);
        parcel.writeString(totalPeriods);
        parcel.writeString(totalLoanInterest);
        parcel.writeString(realMonthlyRate);
        parcel.writeString(name);
        parcel.writeString(contactNum);
        parcel.writeString(idcardno);
        parcel.writeString(marriagests);
        parcel.writeString(qualitications);
        parcel.writeString(childNum);
        parcel.writeString(domicileProvince);
        parcel.writeString(domicileCity);
        parcel.writeString(domicileArea);
        parcel.writeString(domicileDetail);
        parcel.writeString(permanentProvince);
        parcel.writeString(permanentCity);
        parcel.writeString(permanentArea);
        parcel.writeString(permanentDetail);
        parcel.writeString(resSts);
    }
}
