package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/10.
 */
public class HotProductInfo implements Parcelable{
    private String d7name;//名下是否有车
    private String d6name;//名下房产
    private String monthlyRathMin;//月利率最小值
    private String monthlyRathAvg;//月利率平均值
    private String productName;//产品名称
    private String joinSts;//机构状态  '正常、终止'
    private String orgType;//机构类型  银行、网贷公司、信贷公司',
    private long modifyTime;//修改时间
    private String maxVal;//额度范围最大值
    private String payType;//还款方式
    private String minVal;//额度范围最小值
    private String assuranceType;//担保类型
    private String d9name;//产品特点
    private String monthlyRathMax;//月利率最大值
    private String d5name;//职业身份
    private String orgName;//机构名称
    private String productId;
    private String mortgageType;//抵押类型
    private String _mortgageType;//抵押类型
    private String logoPath;//机构logo保存路径
    private String imageLogoPath;//产品logo路径
    private String applyCondition;//申请条件
    private String accountId;
    private String d8name;//信用状况
    private String productAmount;//金额
    private String detailDescribe;//详细说明
    private String monthlyRate;//月利率
    private int timeMaxVal;//期限范围最大值
    private String publishSts;
    private String modifyPerson;
    private String orgId;//机构id
    private String propertyType;//名下房产类型  无产房、商品房、住宅、商铺、办公楼、 厂房、经济/限价房、房改/危改房'
    private String applyMaterial;//申请资料
    private String productNo;//产品编号
    private String costDescribe;//费用说明
    private String _payType;//还款方式
    private String jobIdentity;//职业身份
    private String carStatus;//名下是否有车   无车 、名下有车 、有车，但车已被抵押 、无车，准备购买'
    private String _assuranceType;//担保类型
    private long joinTime;
    private int isValid;
    private String createPerson;
    private String creditStatus;//信用状况  无信用卡活贷款 、信用良好
    private String succeedApplyCount;
    private int timeMinVal;//期限范围最小值
    private String productChara;//产品特点   速度快 手续便捷 利率较低'
    private long createTime;
    private String taskManageId;
    private String reputationLevel;//好评星级
    private int loanPeriod;//放款周期  单位：工作日

    protected HotProductInfo(Parcel in) {
        d7name = in.readString();
        d6name = in.readString();
        monthlyRathMin = in.readString();
        monthlyRathAvg = in.readString();
        productName = in.readString();
        joinSts = in.readString();
        orgType = in.readString();
        modifyTime = in.readLong();
        maxVal = in.readString();
        payType = in.readString();
        minVal = in.readString();
        assuranceType = in.readString();
        d9name = in.readString();
        monthlyRathMax = in.readString();
        d5name = in.readString();
        orgName = in.readString();
        productId = in.readString();
        mortgageType = in.readString();
        _mortgageType = in.readString();
        logoPath = in.readString();
        imageLogoPath = in.readString();
        applyCondition = in.readString();
        accountId = in.readString();
        d8name = in.readString();
        productAmount = in.readString();
        detailDescribe = in.readString();
        monthlyRate = in.readString();
        timeMaxVal = in.readInt();
        publishSts = in.readString();
        modifyPerson = in.readString();
        orgId = in.readString();
        propertyType = in.readString();
        applyMaterial = in.readString();
        productNo = in.readString();
        costDescribe = in.readString();
        _payType = in.readString();
        jobIdentity = in.readString();
        carStatus = in.readString();
        _assuranceType = in.readString();
        joinTime = in.readLong();
        isValid = in.readInt();
        createPerson = in.readString();
        creditStatus = in.readString();
        succeedApplyCount = in.readString();
        timeMinVal = in.readInt();
        productChara = in.readString();
        createTime = in.readLong();
        taskManageId = in.readString();
        reputationLevel = in.readString();
        loanPeriod = in.readInt();
    }

    public static final Creator<HotProductInfo> CREATOR = new Creator<HotProductInfo>() {
        @Override
        public HotProductInfo createFromParcel(Parcel in) {
            return new HotProductInfo(in);
        }

        @Override
        public HotProductInfo[] newArray(int size) {
            return new HotProductInfo[size];
        }
    };

    public void setD7name(String d7name) {
        this.d7name = d7name;
    }

    public void setD6name(String d6name) {
        this.d6name = d6name;
    }

    public void setMonthlyRathMin(String monthlyRathMin) {
        this.monthlyRathMin = monthlyRathMin;
    }

    public void setMonthlyRathAvg(String monthlyRathAvg) {
        this.monthlyRathAvg = monthlyRathAvg;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setJoinSts(String joinSts) {
        this.joinSts = joinSts;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public void setAssuranceType(String assuranceType) {
        this.assuranceType = assuranceType;
    }

    public void setD9name(String d9name) {
        this.d9name = d9name;
    }

    public void setMonthlyRathMax(String monthlyRathMax) {
        this.monthlyRathMax = monthlyRathMax;
    }

    public void setD5name(String d5name) {
        this.d5name = d5name;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public void set_mortgageType(String _mortgageType) {
        this._mortgageType = _mortgageType;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setImageLogoPath(String imageLogoPath) {
        this.imageLogoPath = imageLogoPath;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setD8name(String d8name) {
        this.d8name = d8name;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public void setMonthlyRate(String monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public void setTimeMaxVal(int timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
    }

    public void setPublishSts(String publishSts) {
        this.publishSts = publishSts;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setApplyMaterial(String applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }

    public void set_payType(String _payType) {
        this._payType = _payType;
    }

    public void setJobIdentity(String jobIdentity) {
        this.jobIdentity = jobIdentity;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public void set_assuranceType(String _assuranceType) {
        this._assuranceType = _assuranceType;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public void setSucceedApplyCount(String succeedApplyCount) {
        this.succeedApplyCount = succeedApplyCount;
    }

    public void setTimeMinVal(int timeMinVal) {
        this.timeMinVal = timeMinVal;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId;
    }

    public void setReputationLevel(String reputationLevel) {
        this.reputationLevel = reputationLevel;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getD7name() {
        return d7name;
    }

    public String getD6name() {
        return d6name;
    }

    public String getMonthlyRathMin() {
        return monthlyRathMin;
    }

    public String getMonthlyRathAvg() {
        return monthlyRathAvg;
    }

    public String getProductName() {
        return productName;
    }

    public String getJoinSts() {
        return joinSts;
    }

    public String getOrgType() {
        return orgType;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public String getPayType() {
        return payType;
    }

    public String getMinVal() {
        return minVal;
    }

    public String getAssuranceType() {
        return assuranceType;
    }

    public String getD9name() {
        return d9name;
    }

    public String getMonthlyRathMax() {
        return monthlyRathMax;
    }

    public String getD5name() {
        return d5name;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getProductId() {
        return productId;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public String get_mortgageType() {
        return _mortgageType;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getImageLogoPath() {
        return imageLogoPath;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getD8name() {
        return d8name;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public String getMonthlyRate() {
        return monthlyRate;
    }

    public int getTimeMaxVal() {
        return timeMaxVal;
    }

    public String getPublishSts() {
        return publishSts;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getApplyMaterial() {
        return applyMaterial;
    }

    public String getProductNo() {
        return productNo;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public String get_payType() {
        return _payType;
    }

    public String getJobIdentity() {
        return jobIdentity;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public String get_assuranceType() {
        return _assuranceType;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public String getSucceedApplyCount() {
        return succeedApplyCount;
    }

    public int getTimeMinVal() {
        return timeMinVal;
    }

    public String getProductChara() {
        return productChara;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getTaskManageId() {
        return taskManageId;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(d7name);
        dest.writeString(d6name);
        dest.writeString(monthlyRathMin);
        dest.writeString(monthlyRathAvg);
        dest.writeString(productName);
        dest.writeString(joinSts);
        dest.writeString(orgType);
        dest.writeLong(modifyTime);
        dest.writeString(maxVal);
        dest.writeString(payType);
        dest.writeString(minVal);
        dest.writeString(assuranceType);
        dest.writeString(d9name);
        dest.writeString(monthlyRathMax);
        dest.writeString(d5name);
        dest.writeString(orgName);
        dest.writeString(productId);
        dest.writeString(mortgageType);
        dest.writeString(_mortgageType);
        dest.writeString(logoPath);
        dest.writeString(imageLogoPath);
        dest.writeString(applyCondition);
        dest.writeString(accountId);
        dest.writeString(d8name);
        dest.writeString(productAmount);
        dest.writeString(detailDescribe);
        dest.writeString(monthlyRate);
        dest.writeInt(timeMaxVal);
        dest.writeString(publishSts);
        dest.writeString(modifyPerson);
        dest.writeString(orgId);
        dest.writeString(propertyType);
        dest.writeString(applyMaterial);
        dest.writeString(productNo);
        dest.writeString(costDescribe);
        dest.writeString(_payType);
        dest.writeString(jobIdentity);
        dest.writeString(carStatus);
        dest.writeString(_assuranceType);
        dest.writeLong(joinTime);
        dest.writeInt(isValid);
        dest.writeString(createPerson);
        dest.writeString(creditStatus);
        dest.writeString(succeedApplyCount);
        dest.writeInt(timeMinVal);
        dest.writeString(productChara);
        dest.writeLong(createTime);
        dest.writeString(taskManageId);
        dest.writeString(reputationLevel);
        dest.writeInt(loanPeriod);
    }
}
