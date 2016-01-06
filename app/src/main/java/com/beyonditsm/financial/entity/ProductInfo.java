package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wangbin on 15/11/26.
 */
public class ProductInfo implements Parcelable {

    private String productId;//产品id
    private String applyCondition;//申请条件
    private String productNo;//产品编号 由机构自行定义
//    private String orgId;//机构id
    private String applyMaterial;//申请资料
    private String assuranceType;//担保类型
    private String costDescribe;//费用说明
//    private String createPersonId;//创建人
//    private String createTime;//创建时间
    private String detailDescribe;//详细说明
    private String imageLogoPath;//'产品logo图片路径
//    private int isValid;//是否有效，默认值1；1为有效，0为无效
    private String loanPeriod;//放款周期 单位 工作日
    private String maxVal;//额度范围最大值
    private String minVal;//额度范围最小值
//    private String modifyPersonId;//修改人
//    private String modifyTime;//修改时间
    private String monthlyRate;//月利率 1.3代表1.3%
    private String mortgageType;//抵押类型
    private String payType;//还款方式
    private String productAmount;//金额 单位是万 5.2代表5.2万
    private String productName;//产品名称
    private String publishSts;//发布状态 未发布、已发布、冻结
    private String reputationLevel;//好评星级 1.5代表 1星半
    private String succeedApplyCount;//'成功申请人数 默认0'
    private String timeMaxVal;//期限范围 最大值 单位为月 1代表1个月
    private String timeMinVal;//期限范围 最小值 单位为月 1代表1个月
    private String jobIdentity;//职业身份:下拉框的形式 字典表定义 企业主、个体户、上班族、无固定职业
    private String propertyType;//名下房产类型  无房产、商品房、 住宅、 商铺、 办公楼、 厂房、经济/限价房、房改/危改房
    private String carStatus;//名下是否有车  无车 、名下有车 、有车，但车已被抵押 、无车，准备购买
    private String creditStatus;//信用状况 无信用卡活贷款 、信用良好
    private String productChara;//'产品特点 下拉框的形式 字典表定义 速度快 手续便捷 利率较低'


    private String orgId;//机构id
    private String accountId;//关联账号id
    private String createPersonId;//创建人
    private String createTime;//创建时间
    private int isValid;//是否有效，默认值1；1为有效，0为无效
    private String joinSts;//正常、终止
    private String joinTime;//机构入驻时间
    private String modifyPersonId;//修改人
    private String modifyTime;//修改时间
    private String orgName;//机构名称
    private String orgType;//类型 银行、网贷公司、信贷公司
    private String logoPath;//机构logo图片的保存路径
    private String monthlyRathAvg;
    private String monthlyRathMin;
    private String monthlyRathMax;
    private String _payType;//还款方式
    private String _assuranceType;//保证
    private String _mortgageType;//房屋抵押

    private String _monthlyPayment;//月供
    private String _totalRath;//总利息
    private String disposableRateMin;
    private String disposableRateMax;
    private String payTypeName;

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
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

    private String taskManageId;//任务id

    private List<ProductListTasks> tasks;//产品列表任务

    public ProductInfo(){

    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getApplyMaterial() {
        return applyMaterial;
    }

    public void setApplyMaterial(String applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public String getAssuranceType() {
        return assuranceType;
    }

    public void setAssuranceType(String assuranceType) {
        this.assuranceType = assuranceType;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public String getImageLogoPath() {
        return imageLogoPath;
    }

    public void setImageLogoPath(String imageLogoPath) {
        this.imageLogoPath = imageLogoPath;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(String monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPublishSts() {
        return publishSts;
    }

    public void setPublishSts(String publishSts) {
        this.publishSts = publishSts;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }

    public void setReputationLevel(String reputationLevel) {
        this.reputationLevel = reputationLevel;
    }

    public String getSucceedApplyCount() {
        return succeedApplyCount;
    }

    public void setSucceedApplyCount(String succeedApplyCount) {
        this.succeedApplyCount = succeedApplyCount;
    }

    public String getTimeMaxVal() {
        return timeMaxVal;
    }

    public void setTimeMaxVal(String timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
    }

    public String getTimeMinVal() {
        return timeMinVal;
    }

    public void setTimeMinVal(String timeMinVal) {
        this.timeMinVal = timeMinVal;
    }

    public String getJobIdentity() {
        return jobIdentity;
    }

    public void setJobIdentity(String jobIdentity) {
        this.jobIdentity = jobIdentity;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getProductChara() {
        return productChara;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getJoinSts() {
        return joinSts;
    }

    public void setJoinSts(String joinSts) {
        this.joinSts = joinSts;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getMonthlyRathAvg() {
        return monthlyRathAvg;
    }

    public void setMonthlyRathAvg(String monthlyRathAvg) {
        this.monthlyRathAvg = monthlyRathAvg;
    }

    public String getMonthlyRathMin() {
        return monthlyRathMin;
    }

    public void setMonthlyRathMin(String monthlyRathMin) {
        this.monthlyRathMin = monthlyRathMin;
    }

    public String getMonthlyRathMax() {
        return monthlyRathMax;
    }

    public void setMonthlyRathMax(String monthlyRathMax) {
        this.monthlyRathMax = monthlyRathMax;
    }

    public String get_payType() {
        return _payType;
    }

    public void set_payType(String _payType) {
        this._payType = _payType;
    }

    public String get_assuranceType() {
        return _assuranceType;
    }

    public void set_assuranceType(String _assuranceType) {
        this._assuranceType = _assuranceType;
    }

    public String get_mortgageType() {
        return _mortgageType;
    }

    public void set_mortgageType(String _mortgageType) {
        this._mortgageType = _mortgageType;
    }

    public String get_monthlyPayment() {
        return _monthlyPayment;
    }

    public void set_monthlyPayment(String _monthlyPayment) {
        this._monthlyPayment = _monthlyPayment;
    }

    public String get_totalRath() {
        return _totalRath;
    }

    public void set_totalRath(String _totalRath) {
        this._totalRath = _totalRath;
    }

    public List<ProductListTasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProductListTasks> tasks) {
        this.tasks = tasks;
    }

    public String getTaskManageId() {
        return taskManageId;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.applyCondition);
        dest.writeString(this.productNo);
        dest.writeString(this.applyMaterial);
        dest.writeString(this.assuranceType);
        dest.writeString(this.costDescribe);
        dest.writeString(this.detailDescribe);
        dest.writeString(this.imageLogoPath);
        dest.writeString(this.loanPeriod);
        dest.writeString(this.maxVal);
        dest.writeString(this.minVal);
        dest.writeString(this.monthlyRate);
        dest.writeString(this.mortgageType);
        dest.writeString(this.payType);
        dest.writeString(this.productAmount);
        dest.writeString(this.productName);
        dest.writeString(this.publishSts);
        dest.writeString(this.reputationLevel);
        dest.writeString(this.succeedApplyCount);
        dest.writeString(this.timeMaxVal);
        dest.writeString(this.timeMinVal);
        dest.writeString(this.jobIdentity);
        dest.writeString(this.propertyType);
        dest.writeString(this.carStatus);
        dest.writeString(this.creditStatus);
        dest.writeString(this.productChara);
        dest.writeString(this.orgId);
        dest.writeString(this.accountId);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTime);
        dest.writeInt(this.isValid);
        dest.writeString(this.joinSts);
        dest.writeString(this.joinTime);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.orgName);
        dest.writeString(this.orgType);
        dest.writeString(this.logoPath);
        dest.writeString(this.monthlyRathAvg);
        dest.writeString(this.monthlyRathMin);
        dest.writeString(this.monthlyRathMax);
        dest.writeString(this._payType);
        dest.writeString(this._assuranceType);
        dest.writeString(this._mortgageType);
        dest.writeString(this._monthlyPayment);
        dest.writeString(this._totalRath);
        dest.writeString(this.disposableRateMin);
        dest.writeString(this.disposableRateMax);
        dest.writeString(this.payTypeName);
        dest.writeString(this.taskManageId);
        dest.writeTypedList(tasks);
    }

    protected ProductInfo(Parcel in) {
        this.productId = in.readString();
        this.applyCondition = in.readString();
        this.productNo = in.readString();
        this.applyMaterial = in.readString();
        this.assuranceType = in.readString();
        this.costDescribe = in.readString();
        this.detailDescribe = in.readString();
        this.imageLogoPath = in.readString();
        this.loanPeriod = in.readString();
        this.maxVal = in.readString();
        this.minVal = in.readString();
        this.monthlyRate = in.readString();
        this.mortgageType = in.readString();
        this.payType = in.readString();
        this.productAmount = in.readString();
        this.productName = in.readString();
        this.publishSts = in.readString();
        this.reputationLevel = in.readString();
        this.succeedApplyCount = in.readString();
        this.timeMaxVal = in.readString();
        this.timeMinVal = in.readString();
        this.jobIdentity = in.readString();
        this.propertyType = in.readString();
        this.carStatus = in.readString();
        this.creditStatus = in.readString();
        this.productChara = in.readString();
        this.orgId = in.readString();
        this.accountId = in.readString();
        this.createPersonId = in.readString();
        this.createTime = in.readString();
        this.isValid = in.readInt();
        this.joinSts = in.readString();
        this.joinTime = in.readString();
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.orgName = in.readString();
        this.orgType = in.readString();
        this.logoPath = in.readString();
        this.monthlyRathAvg = in.readString();
        this.monthlyRathMin = in.readString();
        this.monthlyRathMax = in.readString();
        this._payType = in.readString();
        this._assuranceType = in.readString();
        this._mortgageType = in.readString();
        this._monthlyPayment = in.readString();
        this._totalRath = in.readString();
        this.disposableRateMin = in.readString();
        this.disposableRateMax = in.readString();
        this.payTypeName = in.readString();
        this.taskManageId = in.readString();
        this.tasks = in.createTypedArrayList(ProductListTasks.CREATOR);
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        public ProductInfo createFromParcel(Parcel source) {
            return new ProductInfo(source);
        }

        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };
}
