package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.tandong.sa.json.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 急借通entity
 * Created by Administrator on 2016/10/17 0017.
 */

public class CreditSpeedEntity implements Parcelable{

    /**
     * timeMaxVal : 0
     * maxRate : 0.0
     * imageLogoPath : null
     * productName : 急借通
     * timeMinVal : 0
     * maxVal : 0.0
     * minVal : 0.0
     * productChara : 急速借贷,一张身份证就可申请,最快半小时审批放款
     * minRate : 0.0
     * id : 116201919d9f4889978981fb14867653
     * productNo : TN1002
     * loanPeriod : 0
     * costDescribe : 当日户籍用户手机实名制
     */

    private String timeMaxVal;//最大期限
    private String maxRate;//最大利率
    private String imageLogoPath;//产品图片
    private String productName;//产品名称
    private String timeMinVal;//最小期限
    private String maxVal;//最大额度
    private String minVal;//最小额度
    private String productChara;//产品介绍
    private String minRate;//最小利率
    private String id;
    private String productNo;//
    private String loanPeriod;//放款周期
    private String costDescribe;//产品要求
    /**
     * repaymentPeriod : 15,30,50
     */

    private String repaymentPeriod;//期限
    /**
     * applyCondition : 全国
     * applyMaterial : test
     * detailDescribe : test
     */

    private String applyCondition;//申请条件
    private String applyMaterial;//申请材料
    private String detailDescribe;//详细说明
    /**
     * payType : 等本等息
     */

    private String payType;//还款方式
    /**
     * productId : a7eae68cce814ad7bd53ae056b1ff5e8
     * mortgageType : 15,30,50
     * loanTimes : {"15":"15","50":"50","30":"30"}
     */

    private String productId;
    private String mortgageType;
    /**
     * 15 : 15
     * 50 : 50
     * 30 : 30
     */

    private LoanTimesBean loanTimes;
    /**
     * propertyTypes : [{"name":"租房","id":"905"},{"name":"集体宿舍","id":"904"},{"name":"亲属楼宇","id":"903"},{"name":"按揭","id":"902"},{"name":"自置","id":"901"},{"name":"共有住宅","id":"906"},{"name":"其他","id":"907"}]
     * jobIdentity : 524,525,517,516,515,514,513,512,511,510,509,520,519,518,521,523,505,504,503,502,501,506,507,508
     * jobIdentitys : [{"name":"医疗","id":"524"},{"name":"其他","id":"525"},{"name":"其他个人消费","id":"517"},{"name":"企业工人发工资","id":"516"},{"name":"还债","id":"515"},{"name":"企业资金周转","id":"514"},{"name":"个人资金周转","id":"513"},{"name":"购设备","id":"512"},{"name":"备货","id":"511"},{"name":"扩大经营","id":"510"},{"name":"创业","id":"509"},{"name":"新公司开业投资","id":"520"},{"name":"购买机器设备装修厂房等","id":"519"},{"name":"购买原材料","id":"518"},{"name":"购物","id":"521"},{"name":"旅游","id":"523"},{"name":"综合消费","id":"505"},{"name":"教育培训","id":"504"},{"name":"购家电","id":"503"},{"name":"购房","id":"502"},{"name":"购车","id":"501"},{"name":"结婚","id":"506"},{"name":"装修","id":"507"},{"name":"做生意","id":"508"}]
     * propertyType : 905,904,903,902,901,906,907
     */

    private String jobIdentity;
    private String propertyType;
    /**
     * name : 租房
     * id : 905
     */

    private List<PropertyTypesBean> propertyTypes;
    /**
     * name : 医疗
     * id : 524
     */

    private List<JobIdentitysBean> jobIdentitys;
    /**
     * name : 等本等息
     * id : 900091202
     */

    private List<PayTypessBean> payTypess;


    protected CreditSpeedEntity(Parcel in) {
        timeMaxVal = in.readString();
        maxRate = in.readString();
        imageLogoPath = in.readString();
        productName = in.readString();
        timeMinVal = in.readString();
        maxVal = in.readString();
        minVal = in.readString();
        productChara = in.readString();
        minRate = in.readString();
        id = in.readString();
        productNo = in.readString();
        loanPeriod = in.readString();
        costDescribe = in.readString();
        repaymentPeriod = in.readString();
        applyCondition = in.readString();
        applyMaterial = in.readString();
        detailDescribe = in.readString();
        payType = in.readString();
        productId = in.readString();
        mortgageType = in.readString();
        jobIdentity = in.readString();
        propertyType = in.readString();
    }

    public static final Creator<CreditSpeedEntity> CREATOR = new Creator<CreditSpeedEntity>() {
        @Override
        public CreditSpeedEntity createFromParcel(Parcel in) {
            return new CreditSpeedEntity(in);
        }

        @Override
        public CreditSpeedEntity[] newArray(int size) {
            return new CreditSpeedEntity[size];
        }
    };

    public String getTimeMaxVal() {
        return timeMaxVal;
    }

    public void setTimeMaxVal(String timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getImageLogoPath() {
        return imageLogoPath;
    }

    public void setImageLogoPath(String imageLogoPath) {
        this.imageLogoPath = imageLogoPath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTimeMinVal() {
        return timeMinVal;
    }

    public void setTimeMinVal(String timeMinVal) {
        this.timeMinVal = timeMinVal;
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

    public String getProductChara() {
        return productChara;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }


    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getApplyMaterial() {
        return applyMaterial;
    }

    public void setApplyMaterial(String applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public LoanTimesBean getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(LoanTimesBean loanTimes) {
        this.loanTimes = loanTimes;
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

    public List<PropertyTypesBean> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<PropertyTypesBean> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public List<JobIdentitysBean> getJobIdentitys() {
        return jobIdentitys;
    }

    public void setJobIdentitys(List<JobIdentitysBean> jobIdentitys) {
        this.jobIdentitys = jobIdentitys;
    }

    public List<PayTypessBean> getPayTypess() {
        return payTypess;
    }

    public void setPayTypess(List<PayTypessBean> payTypess) {
        this.payTypess = payTypess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(timeMaxVal);
        parcel.writeString(maxRate);
        parcel.writeString(imageLogoPath);
        parcel.writeString(productName);
        parcel.writeString(timeMinVal);
        parcel.writeString(maxVal);
        parcel.writeString(minVal);
        parcel.writeString(productChara);
        parcel.writeString(minRate);
        parcel.writeString(id);
        parcel.writeString(productNo);
        parcel.writeString(loanPeriod);
        parcel.writeString(costDescribe);
        parcel.writeString(repaymentPeriod);
        parcel.writeString(applyCondition);
        parcel.writeString(applyMaterial);
        parcel.writeString(detailDescribe);
        parcel.writeString(payType);
        parcel.writeString(productId);
        parcel.writeString(mortgageType);
        parcel.writeString(jobIdentity);
        parcel.writeString(propertyType);
    }


    public static class LoanTimesBean {
        @SerializedName("15")
        private String value15;
        @SerializedName("50")
        private String value50;
        @SerializedName("30")
        private String value30;

        public String getValue15() {
            return value15;
        }

        public void setValue15(String value15) {
            this.value15 = value15;
        }

        public String getValue50() {
            return value50;
        }

        public void setValue50(String value50) {
            this.value50 = value50;
        }

        public String getValue30() {
            return value30;
        }

        public void setValue30(String value30) {
            this.value30 = value30;
        }
    }

    public static class PropertyTypesBean implements  Serializable{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class JobIdentitysBean implements Serializable{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class PayTypessBean implements Serializable{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
