package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.tandong.sa.json.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 急借通entity
 * Created by Administrator on 2016/10/17 0017.
 */

public class CreditSpeedEntity implements Parcelable{
    private List<ResideStatusmapBean> resideStatusmap;//居住状况字段信息
    private Map<String,String> repaymentTerm;//还款期限下拉选项
    private String productId;//产品ID
    private String loanPeriodType;//贷款期限类型
    private String imageLogoPath;//产品图片
    private String productName;//产品名称
    private String minLoanPeriod;//最低贷款期限
    private String applyCondition;//申请条件
    private String maxVal;//最大额度
    private List<PayTypessBean>paytypemap;//还款方式
    private String productChara;//产品介绍
    private String minVal;//最小额度
    private String loanTimes;//还款期限"21周,32周,43周",//还款期限
    private List<debtTypemapBean> debtTypemap;//"[{"id":"001","name":"正式"}]",//借款用途
    private String loanRateType;//利率类型(1.月利率2.日利率)
    private String detailDescribe;//详细说明
    private String applyMaterial;//申请材料
    private String minRate;//最小利率
    private String maxRate;//最大利率
    private String maxLoanPeriod;//最高贷款期限
    private String loanPeriod;//放款周期
    private String costDescribe;//产品要求


    private String propertyTypes;//（因为返回来的带斜杠的没用只是用实体来取）
    private String payType;
    private String jobIdentitys;


    public String getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(String propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getJobIdentitys() {
        return jobIdentitys;
    }

    public void setJobIdentitys(String jobIdentitys) {
        this.jobIdentitys = jobIdentitys;
    }

    //以上是获取急速贷的bean 下面是原来已有的没重名的
    private String timeMinVal;//最小期限
    private String timeMaxVal;//最大期限
    private String id;
    private String productNo;//
    private String repaymentPeriod;//期限 repaymentPeriod : 15,30,50
    private String mortgageType;//mortgageType : 15,30,50
    private String jobIdentity;//jobIdentity : 524,525,517,516,515,514,513,512,511,510,509,520,519,518,521,523,505,504,503,502,501,506,507,508
    private String propertyType;//propertyType : 905,904,903,902,901,906,907



    public Map<String,String> getRepaymentTerm() {
        return repaymentTerm;
    }

    public void setRepaymentTerm(Map<String,String> repaymentTerm) {
        this.repaymentTerm = repaymentTerm;
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

    public String getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(String minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }


    public String getProductChara() {
        return productChara;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(String loanTimes) {
        this.loanTimes = loanTimes;
    }



    public String getLoanRateType() {
        return loanRateType;
    }

    public void setLoanRateType(String loanRateType) {
        this.loanRateType = loanRateType;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public String getApplyMaterial() {
        return applyMaterial;
    }

    public void setApplyMaterial(String applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    public void setMaxLoanPeriod(String maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
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

    public String getTimeMinVal() {
        return timeMinVal;
    }

    public void setTimeMinVal(String timeMinVal) {
        this.timeMinVal = timeMinVal;
    }

    public String getTimeMaxVal() {
        return timeMaxVal;
    }

    public void setTimeMaxVal(String timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
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

    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
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

    //居住状况
    public static class ResideStatusmapBean implements  Serializable{
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
   //借款用途
    public static class debtTypemapBean implements Serializable{
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

    //还款方式
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

    public CreditSpeedEntity() {
    }

    public List<ResideStatusmapBean> getResideStatusmap() {
        return resideStatusmap;
    }

    public void setResideStatusmap(List<ResideStatusmapBean> resideStatusmap) {
        this.resideStatusmap = resideStatusmap;
    }

    public List<PayTypessBean> getPaytypemap() {
        return paytypemap;
    }

    public void setPaytypemap(List<PayTypessBean> paytypemap) {
        this.paytypemap = paytypemap;
    }

    public List<debtTypemapBean> getDebtTypemap() {
        return debtTypemap;
    }

    public void setDebtTypemap(List<debtTypemapBean> debtTypemap) {
        this.debtTypemap = debtTypemap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.resideStatusmap);
        dest.writeInt(this.repaymentTerm.size());
        for (Map.Entry<String, String> entry : this.repaymentTerm.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.productId);
        dest.writeString(this.loanPeriodType);
        dest.writeString(this.imageLogoPath);
        dest.writeString(this.productName);
        dest.writeString(this.minLoanPeriod);
        dest.writeString(this.applyCondition);
        dest.writeString(this.maxVal);
        dest.writeList(this.paytypemap);
        dest.writeString(this.productChara);
        dest.writeString(this.minVal);
        dest.writeString(this.loanTimes);
        dest.writeList(this.debtTypemap);
        dest.writeString(this.loanRateType);
        dest.writeString(this.detailDescribe);
        dest.writeString(this.applyMaterial);
        dest.writeString(this.minRate);
        dest.writeString(this.maxRate);
        dest.writeString(this.maxLoanPeriod);
        dest.writeString(this.loanPeriod);
        dest.writeString(this.costDescribe);
        dest.writeString(this.propertyTypes);
        dest.writeString(this.payType);
        dest.writeString(this.jobIdentitys);
        dest.writeString(this.timeMinVal);
        dest.writeString(this.timeMaxVal);
        dest.writeString(this.id);
        dest.writeString(this.productNo);
        dest.writeString(this.repaymentPeriod);
        dest.writeString(this.mortgageType);
        dest.writeString(this.jobIdentity);
        dest.writeString(this.propertyType);
    }

    protected CreditSpeedEntity(Parcel in) {
        this.resideStatusmap = new ArrayList<ResideStatusmapBean>();
        in.readList(this.resideStatusmap, ResideStatusmapBean.class.getClassLoader());
        int repaymentTermSize = in.readInt();
        this.repaymentTerm = new HashMap<String, String>(repaymentTermSize);
        for (int i = 0; i < repaymentTermSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.repaymentTerm.put(key, value);
        }
        this.productId = in.readString();
        this.loanPeriodType = in.readString();
        this.imageLogoPath = in.readString();
        this.productName = in.readString();
        this.minLoanPeriod = in.readString();
        this.applyCondition = in.readString();
        this.maxVal = in.readString();
        this.paytypemap = new ArrayList<PayTypessBean>();
        in.readList(this.paytypemap, PayTypessBean.class.getClassLoader());
        this.productChara = in.readString();
        this.minVal = in.readString();
        this.loanTimes = in.readString();
        this.debtTypemap = new ArrayList<debtTypemapBean>();
        in.readList(this.debtTypemap, debtTypemapBean.class.getClassLoader());
        this.loanRateType = in.readString();
        this.detailDescribe = in.readString();
        this.applyMaterial = in.readString();
        this.minRate = in.readString();
        this.maxRate = in.readString();
        this.maxLoanPeriod = in.readString();
        this.loanPeriod = in.readString();
        this.costDescribe = in.readString();
        this.propertyTypes = in.readString();
        this.payType = in.readString();
        this.jobIdentitys = in.readString();
        this.timeMinVal = in.readString();
        this.timeMaxVal = in.readString();
        this.id = in.readString();
        this.productNo = in.readString();
        this.repaymentPeriod = in.readString();
        this.mortgageType = in.readString();
        this.jobIdentity = in.readString();
        this.propertyType = in.readString();
    }

    public static final Creator<CreditSpeedEntity> CREATOR = new Creator<CreditSpeedEntity>() {
        @Override
        public CreditSpeedEntity createFromParcel(Parcel source) {
            return new CreditSpeedEntity(source);
        }

        @Override
        public CreditSpeedEntity[] newArray(int size) {
            return new CreditSpeedEntity[size];
        }
    };
}
