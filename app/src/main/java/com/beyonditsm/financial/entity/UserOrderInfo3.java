package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/18.
 */

public class UserOrderInfo3 {

    /**
     * orderId :  订单id
     * relatives1Name : 亲属1姓名
     * relatives1Rs :  亲属1关系
     * relatives1ContactNum :  亲属1联系电话
     * relatives2Name :  亲属2姓名
     * relatives2Rs :  亲属2关系
     * relatives2ContactNum :  亲属2联系电话
     * colleagueName :  同事姓名
     * colleagueRs :  同事关系
     * colleagueContactNum :  同事联系电话
     * ecName :  紧急联系人姓名
     * ecRs :  紧急联系人关系
     * ecContactNum :  紧急联系人联系电话
     */

    private String orderId;
    private String relatives1Name;
    private String relatives1Rs;
    private String relatives1ContactNum;
    private String relatives2Name;
    private String relatives2Rs;
    private String relatives2ContactNum;
    private String colleagueName;
    private String colleagueRs;
    private String colleagueContactNum;
    private String ecName;
    private String ecRs;
    private String ecContactNum;

    public UserOrderInfo3() {

    }

    public UserOrderInfo3(String orderId, String relatives1Name, String relatives1Rs, String relatives1ContactNum, String relatives2Name, String relatives2Rs, String relatives2ContactNum, String colleagueName, String colleagueRs, String colleagueContactNum, String ecName, String ecRs, String ecContactNum) {
        this.orderId = orderId;
        this.relatives1Name = relatives1Name;
        this.relatives1Rs = relatives1Rs;
        this.relatives1ContactNum = relatives1ContactNum;
        this.relatives2Name = relatives2Name;
        this.relatives2Rs = relatives2Rs;
        this.relatives2ContactNum = relatives2ContactNum;
        this.colleagueName = colleagueName;
        this.colleagueRs = colleagueRs;
        this.colleagueContactNum = colleagueContactNum;
        this.ecName = ecName;
        this.ecRs = ecRs;
        this.ecContactNum = ecContactNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRelatives1Name() {
        return relatives1Name;
    }

    public void setRelatives1Name(String relatives1Name) {
        this.relatives1Name = relatives1Name;
    }

    public String getRelatives1Rs() {
        return relatives1Rs;
    }

    public void setRelatives1Rs(String relatives1Rs) {
        this.relatives1Rs = relatives1Rs;
    }

    public String getRelatives1ContactNum() {
        return relatives1ContactNum;
    }

    public void setRelatives1ContactNum(String relatives1ContactNum) {
        this.relatives1ContactNum = relatives1ContactNum;
    }

    public String getRelatives2Name() {
        return relatives2Name;
    }

    public void setRelatives2Name(String relatives2Name) {
        this.relatives2Name = relatives2Name;
    }

    public String getRelatives2Rs() {
        return relatives2Rs;
    }

    public void setRelatives2Rs(String relatives2Rs) {
        this.relatives2Rs = relatives2Rs;
    }

    public String getRelatives2ContactNum() {
        return relatives2ContactNum;
    }

    public void setRelatives2ContactNum(String relatives2ContactNum) {
        this.relatives2ContactNum = relatives2ContactNum;
    }

    public String getColleagueName() {
        return colleagueName;
    }

    public void setColleagueName(String colleagueName) {
        this.colleagueName = colleagueName;
    }

    public String getColleagueRs() {
        return colleagueRs;
    }

    public void setColleagueRs(String colleagueRs) {
        this.colleagueRs = colleagueRs;
    }

    public String getColleagueContactNum() {
        return colleagueContactNum;
    }

    public void setColleagueContactNum(String colleagueContactNum) {
        this.colleagueContactNum = colleagueContactNum;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public String getEcRs() {
        return ecRs;
    }

    public void setEcRs(String ecRs) {
        this.ecRs = ecRs;
    }

    public String getEcContactNum() {
        return ecContactNum;
    }

    public void setEcContactNum(String ecContactNum) {
        this.ecContactNum = ecContactNum;
    }
}
