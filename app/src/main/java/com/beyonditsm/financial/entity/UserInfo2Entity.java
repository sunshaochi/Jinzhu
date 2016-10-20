package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/20.
 */

public class UserInfo2Entity {

    /**
     * orderId :  订单id(String)
     * companyName :  公司名称(String)
     * companyArea : 公司所在区(String)
     * companyCity : ss
     * companyProvince :    公司所在省(String)
     * companyDetail :  公司所在详细地址(String)
     * areaCode : 区号()String
     * companyPhoneNum : 公司联系电话及分机号(String)
     * companyNature : 公司性质(String)
     * workingProp :  工作性质(String)
     * salary : 薪水（Double）
     * payout : 发放形式(String)
     */

    private String orderId;
    private String companyName;
    private String companyArea;
    private String companyCity;
    private String companyProvince;
    private String companyDetail;
    private String areaCode;
    private String companyPhoneNum;
    private String companyNature;
    private String workingProp;
    private String salary;
    private String payout;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(String companyDetail) {
        this.companyDetail = companyDetail;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCompanyPhoneNum() {
        return companyPhoneNum;
    }

    public void setCompanyPhoneNum(String companyPhoneNum) {
        this.companyPhoneNum = companyPhoneNum;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getWorkingProp() {
        return workingProp;
    }

    public void setWorkingProp(String workingProp) {
        this.workingProp = workingProp;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }
}
