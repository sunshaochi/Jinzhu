package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/12/19.
 */
public class ProductEntity implements Parcelable {

    /**
     * productId : 2c908c7e51af19260151af4f96010044
     * applyCondition : 年龄要求：22-59周岁；
     流水要求：税后月打卡工资流水2000元以上，需提供近半年银行流水；
     社保要求：需提供社保缴费证明且不低于1年；
     工作年限：现单位工作满6个月以上；
     工作地区：上海；
     征信要求：信用记录良好，无不良逾期，暂不接受信用空白人士申请。
     * productNo : 1450345207291
     * orgId : 2c908c7e51af19260151af1d4e060003
     * applyMaterial : 1、身份证（复印件）；
     2、劳动合同或工作证明、收入证明；
     3、6个月工资流水单；
     4、近期水电煤账单任意一份；
     5、其他有价资产以及证明 (房产证、本科以上学历证书、车辆行驶证、股票基金市值等)。
     * assuranceType : 2c909d245115183b01511525d61d0006
     * costDescribe : 月管理费2.33%
     * createPersonId : 2c908c7e51aee4890151af15b56d0009
     * createTime : 1450345207000
     * detailDescribe : 贷款
     贷款
     贷款
     * imageLogoPath : img/2646183637189632.png
     * isValid : 1
     * loanPeriod : 5
     * maxVal : 300000
     * minVal : 10000
     * modifyPersonId : 2c908c7e51aee4890151af15b56d0009
     * modifyTime : 1450345207000
     * monthlyRate : null
     * mortgageType : 2c909d245115183b01511523c1f90003
     * payType : 2c908c6e50d077f80150d08048b3000a
     * productAmount : 30000
     * productName : 宜信  -  新薪贷--农
     * publishSts : PUBLISHED
     * reputationLevel : null
     * succeedApplyCount : null
     * timeMaxVal : 36
     * timeMinVal : 3
     * jobIdentity : 2c908c6e50d077f80150d081b313000d
     * propertyType : 2c908c6e50d077f80150d08369dc0014
     * carStatus : 2c908c6e50d077f80150d086e184001e
     * creditStatus : 2c908c6e50d077f80150d08751ee001f
     * productChara : 2c908c7651474aa40151478b13820002,2c908c7651474aa40151478beed50003,2c908c7651474aa40151478c2fc70004
     * monthlyRathMin : 1.22
     * monthlyRathMax : 2.33
     * isHotProduct : null
     * disposableRateMin : 5
     * disposableRateMax : 10
     * monthRateMin : 0
     * monthRateMax : 0
     * countCost : 0
     * taskManageId : null
     * propertyTypeName : 经适/限价房
     * creditStatusName : 无形用卡或贷款
     * payTypeName : 分期还款
     * jobIdentityName : 企业主
     * carStatusName : 无车,准备购买
     * mortgageTypeName : 信用卡
     * productCharaNames : ["速度快","手续便捷","利率较低"]
     * publishStsName :
     * assuranceTypeName : 保证
     * tasks : []
     */

    private String productId;
    private String applyCondition;//申请条件
    private String productNo;
    private String orgId;
    private String applyMaterial;//所需材料
    private String assuranceType;//担保类型
    private String costDescribe;//费用说明
    private String createPersonId;
    private long createTime;
    private String detailDescribe;//详细说明
    private String imageLogoPath;//图片logo
    private int isValid;
    private String loanPeriod;//放款周期
    private String maxVal;//额度范围最大值
    private String minVal;//额度范围最小值
    private String modifyPersonId;
    private long modifyTime;
    private String monthlyRate;
    private String mortgageType;
    private String payType;//还款方式
    private String productAmount;//金额 单位是万 5.2代表5.2万
    private String productName;//产品名称
    private String publishSts;//发布状态 未发布、已发布、冻结
    private String reputationLevel;//好评星级 1.5代表 1星半
    private String succeedApplyCount;//'成功申请人数 默认0'
    private String timeMaxVal;//期限范围 最大值 单位为月 1代表1个月
    private String timeMinVal;//期限范围 最小值 单位为月 1代表1个月
    private String jobIdentity;
    private String propertyType;
    private String carStatus;//名下是否有车  无车 、名下有车 、有车，但车已被抵押 、无车，准备购买
    private String creditStatus;//信用状况 无信用卡活贷款 、信用良好
    private String productChara;//'产品特点 下拉框的形式 字典表定义 速度快 手续便捷 利率较低'
    private String monthlyRathMin;//月利率最小值
    private String monthlyRathMax;//月利率最大值
    private Object isHotProduct;
    private String disposableRateMin;//一次性收费最小值
    private String disposableRateMax;//一次性收费最大值
    private Double monthRateMin;
    private Double monthRateMax;
    private Double countCost;
    private String taskManageId;
    private String propertyTypeName;
    private String creditStatusName;
    private String payTypeName;
    private String jobIdentityName;
    private String carStatusName;
    private String mortgageTypeName;
    private String publishStsName;
    private String assuranceTypeName;
    private List<String> productCharaNames;
    private List<?> tasks;

    protected ProductEntity(Parcel in) {
        productId = in.readString();
        applyCondition = in.readString();
        productNo = in.readString();
        orgId = in.readString();
        applyMaterial = in.readString();
        assuranceType = in.readString();
        costDescribe = in.readString();
        createPersonId = in.readString();
        createTime = in.readLong();
        detailDescribe = in.readString();
        imageLogoPath = in.readString();
        isValid = in.readInt();
        loanPeriod = in.readString();
        maxVal = in.readString();
        minVal = in.readString();
        modifyPersonId = in.readString();
        modifyTime = in.readLong();
        monthlyRate = in.readString();
        mortgageType = in.readString();
        payType = in.readString();
        productAmount = in.readString();
        productName = in.readString();
        publishSts = in.readString();
        reputationLevel = in.readString();
        succeedApplyCount = in.readString();
        timeMaxVal = in.readString();
        timeMinVal = in.readString();
        jobIdentity = in.readString();
        propertyType = in.readString();
        carStatus = in.readString();
        creditStatus = in.readString();
        productChara = in.readString();
        monthlyRathMin = in.readString();
        monthlyRathMax = in.readString();
        disposableRateMin = in.readString();
        disposableRateMax = in.readString();
        monthRateMin = in.readDouble();
        monthRateMax = in.readDouble();
        countCost = in.readDouble();
        propertyTypeName = in.readString();
        creditStatusName = in.readString();
        payTypeName = in.readString();
        jobIdentityName = in.readString();
        carStatusName = in.readString();
        mortgageTypeName = in.readString();
        publishStsName = in.readString();
        assuranceTypeName = in.readString();
        productCharaNames = in.createStringArrayList();
    }

    public static final Creator<ProductEntity> CREATOR = new Creator<ProductEntity>() {
        @Override
        public ProductEntity createFromParcel(Parcel in) {
            return new ProductEntity(in);
        }

        @Override
        public ProductEntity[] newArray(int size) {
            return new ProductEntity[size];
        }
    };

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setApplyMaterial(String applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public void setAssuranceType(String assuranceType) {
        this.assuranceType = assuranceType;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public void setImageLogoPath(String imageLogoPath) {
        this.imageLogoPath = imageLogoPath;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setMonthlyRate(String monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPublishSts(String publishSts) {
        this.publishSts = publishSts;
    }

    public void setReputationLevel(String reputationLevel) {
        this.reputationLevel = reputationLevel;
    }

    public void setSucceedApplyCount(String succeedApplyCount) {
        this.succeedApplyCount = succeedApplyCount;
    }

    public void setTimeMaxVal(String timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
    }

    public void setTimeMinVal(String timeMinVal) {
        this.timeMinVal = timeMinVal;
    }

    public void setJobIdentity(String jobIdentity) {
        this.jobIdentity = jobIdentity;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public void setMonthlyRathMin(String monthlyRathMin) {
        this.monthlyRathMin = monthlyRathMin;
    }

    public void setMonthlyRathMax(String monthlyRathMax) {
        this.monthlyRathMax = monthlyRathMax;
    }

    public void setIsHotProduct(Object isHotProduct) {
        this.isHotProduct = isHotProduct;
    }

    public void setDisposableRateMin(String disposableRateMin) {
        this.disposableRateMin = disposableRateMin;
    }

    public void setDisposableRateMax(String disposableRateMax) {
        this.disposableRateMax = disposableRateMax;
    }

    public void setMonthRateMin(Double monthRateMin) {
        this.monthRateMin = monthRateMin;
    }

    public void setMonthRateMax(Double monthRateMax) {
        this.monthRateMax = monthRateMax;
    }

    public void setCountCost(Double countCost) {
        this.countCost = countCost;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public void setCreditStatusName(String creditStatusName) {
        this.creditStatusName = creditStatusName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public void setJobIdentityName(String jobIdentityName) {
        this.jobIdentityName = jobIdentityName;
    }

    public void setCarStatusName(String carStatusName) {
        this.carStatusName = carStatusName;
    }

    public void setMortgageTypeName(String mortgageTypeName) {
        this.mortgageTypeName = mortgageTypeName;
    }

    public void setPublishStsName(String publishStsName) {
        this.publishStsName = publishStsName;
    }

    public void setAssuranceTypeName(String assuranceTypeName) {
        this.assuranceTypeName = assuranceTypeName;
    }

    public void setProductCharaNames(List<String> productCharaNames) {
        this.productCharaNames = productCharaNames;
    }

    public void setTasks(List<?> tasks) {
        this.tasks = tasks;
    }

    public String getProductId() {
        return productId;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public String getProductNo() {
        return productNo;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getApplyMaterial() {
        return applyMaterial;
    }

    public String getAssuranceType() {
        return assuranceType;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public String getImageLogoPath() {
        return imageLogoPath;
    }

    public int getIsValid() {
        return isValid;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public String getMinVal() {
        return minVal;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public String getMonthlyRate() {
        return monthlyRate;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public String getPayType() {
        return payType;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public String getPublishSts() {
        return publishSts;
    }

    public Object getReputationLevel() {
        return reputationLevel;
    }

    public Object getSucceedApplyCount() {
        return succeedApplyCount;
    }

    public String getTimeMaxVal() {
        return timeMaxVal;
    }

    public String getTimeMinVal() {
        return timeMinVal;
    }

    public String getJobIdentity() {
        return jobIdentity;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public String getProductChara() {
        return productChara;
    }

    public String getMonthlyRathMin() {
        return monthlyRathMin;
    }

    public String getMonthlyRathMax() {
        return monthlyRathMax;
    }

    public Object getIsHotProduct() {
        return isHotProduct;
    }

    public String getDisposableRateMin() {
        return disposableRateMin;
    }

    public String getDisposableRateMax() {
        return disposableRateMax;
    }

    public Double getMonthRateMin() {
        return monthRateMin;
    }

    public Double getMonthRateMax() {
        return monthRateMax;
    }

    public Double getCountCost() {
        return countCost;
    }

    public Object getTaskManageId() {
        return taskManageId;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public String getCreditStatusName() {
        return creditStatusName;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public String getJobIdentityName() {
        return jobIdentityName;
    }

    public String getCarStatusName() {
        return carStatusName;
    }

    public String getMortgageTypeName() {
        return mortgageTypeName;
    }

    public String getPublishStsName() {
        return publishStsName;
    }

    public String getAssuranceTypeName() {
        return assuranceTypeName;
    }

    public List<String> getProductCharaNames() {
        return productCharaNames;
    }

    public List<?> getTasks() {
        return tasks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(applyCondition);
        dest.writeString(productNo);
        dest.writeString(orgId);
        dest.writeString(applyMaterial);
        dest.writeString(assuranceType);
        dest.writeString(costDescribe);
        dest.writeString(createPersonId);
        dest.writeLong(createTime);
        dest.writeString(detailDescribe);
        dest.writeString(imageLogoPath);
        dest.writeInt(isValid);
        dest.writeString(loanPeriod);
        dest.writeString(maxVal);
        dest.writeString(minVal);
        dest.writeString(modifyPersonId);
        dest.writeLong(modifyTime);
        dest.writeString(monthlyRate);
        dest.writeString(mortgageType);
        dest.writeString(payType);
        dest.writeString(productAmount);
        dest.writeString(productName);
        dest.writeString(publishSts);
        dest.writeString(reputationLevel);
        dest.writeString(succeedApplyCount);
        dest.writeString(timeMaxVal);
        dest.writeString(timeMinVal);
        dest.writeString(jobIdentity);
        dest.writeString(propertyType);
        dest.writeString(carStatus);
        dest.writeString(creditStatus);
        dest.writeString(productChara);
        dest.writeString(monthlyRathMin);
        dest.writeString(monthlyRathMax);
        dest.writeString(disposableRateMin);
        dest.writeString(disposableRateMax);
        dest.writeDouble(monthRateMin);
        dest.writeDouble(monthRateMax);
        dest.writeDouble(countCost);
        dest.writeString(propertyTypeName);
        dest.writeString(creditStatusName);
        dest.writeString(payTypeName);
        dest.writeString(jobIdentityName);
        dest.writeString(carStatusName);
        dest.writeString(mortgageTypeName);
        dest.writeString(publishStsName);
        dest.writeString(assuranceTypeName);
        dest.writeStringList(productCharaNames);
    }
}
