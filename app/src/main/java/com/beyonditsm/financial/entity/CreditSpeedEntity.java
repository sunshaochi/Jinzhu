package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.tandong.sa.json.annotations.SerializedName;

/**
 * 急借通entity
 * Created by Administrator on 2016/10/17 0017.
 */

public class CreditSpeedEntity  implements Parcelable{

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
     * 907 : 其他
     * 901 : 自置
     * 902 : 按揭
     * 903 : 亲属楼宇
     * 904 : 集体宿舍
     * 905 : 租房
     * 906 : 共有住宅
     */

    private PropertyTypesBean propertyTypes;//居住状况
    /**
     * propertyTypes : {"907":"其他","901":"自置","902":"按揭","903":"亲属楼宇","904":"集体宿舍","905":"租房","906":"共有住宅"}
     * jobIdentity : 524,525,517,516,515,514,513,512,511,510,509,520,519,518,521,523,505,504,503,502,501,506,507,508
     * jobIdentitys : {"520":"新公司开业投资","510":"扩大经营","521":"购物","511":"备货","512":"购设备","523":"旅游","501":"购车","524":"医疗","513":"个人资金周转","502":"购房","525":"其他","514":"企业资金周转","503":"购家电","515":"还债","504":"教育培训","516":"企业工人发工资","505":"综合消费","517":"其他个人消费","506":"结婚","518":"购买原材料","507":"装修","519":"购买机器设备装修厂房等","508":"做生意","509":"创业"}
     * propertyType : 905,904,903,902,901,906,907
     */

    private String jobIdentity;
    /**
     * 520 : 新公司开业投资
     * 510 : 扩大经营
     * 521 : 购物
     * 511 : 备货
     * 512 : 购设备
     * 523 : 旅游
     * 501 : 购车
     * 524 : 医疗
     * 513 : 个人资金周转
     * 502 : 购房
     * 525 : 其他
     * 514 : 企业资金周转
     * 503 : 购家电
     * 515 : 还债
     * 504 : 教育培训
     * 516 : 企业工人发工资
     * 505 : 综合消费
     * 517 : 其他个人消费
     * 506 : 结婚
     * 518 : 购买原材料
     * 507 : 装修
     * 519 : 购买机器设备装修厂房等
     * 508 : 做生意
     * 509 : 创业
     */

    private JobIdentitysBean jobIdentitys;//借款用途
    private String propertyType;
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
        applyCondition  = in.readString();
        applyMaterial = in.readString();
        detailDescribe = in.readString();
        payType = in.readString();
        mortgageType = in.readString();
        productId = in.readString();
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
        parcel.writeString(mortgageType);
        parcel.writeString(productId);
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

    public PropertyTypesBean getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(PropertyTypesBean propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public String getJobIdentity() {
        return jobIdentity;
    }

    public void setJobIdentity(String jobIdentity) {
        this.jobIdentity = jobIdentity;
    }

    public JobIdentitysBean getJobIdentitys() {
        return jobIdentitys;
    }

    public void setJobIdentitys(JobIdentitysBean jobIdentitys) {
        this.jobIdentitys = jobIdentitys;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
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

    public static class PropertyTypesBean {
        @SerializedName("907")
        private String value907;
        @SerializedName("901")
        private String value901;
        @SerializedName("902")
        private String value902;
        @SerializedName("903")
        private String value903;
        @SerializedName("904")
        private String value904;
        @SerializedName("905")
        private String value905;
        @SerializedName("906")
        private String value906;

        public String getValue907() {
            return value907;
        }

        public void setValue907(String value907) {
            this.value907 = value907;
        }

        public String getValue901() {
            return value901;
        }

        public void setValue901(String value901) {
            this.value901 = value901;
        }

        public String getValue902() {
            return value902;
        }

        public void setValue902(String value902) {
            this.value902 = value902;
        }

        public String getValue903() {
            return value903;
        }

        public void setValue903(String value903) {
            this.value903 = value903;
        }

        public String getValue904() {
            return value904;
        }

        public void setValue904(String value904) {
            this.value904 = value904;
        }

        public String getValue905() {
            return value905;
        }

        public void setValue905(String value905) {
            this.value905 = value905;
        }

        public String getValue906() {
            return value906;
        }

        public void setValue906(String value906) {
            this.value906 = value906;
        }
    }

    public static class JobIdentitysBean {
        @SerializedName("520")
        private String value520;
        @SerializedName("510")
        private String value510;
        @SerializedName("521")
        private String value521;
        @SerializedName("511")
        private String value511;
        @SerializedName("512")
        private String value512;
        @SerializedName("523")
        private String value523;
        @SerializedName("501")
        private String value501;
        @SerializedName("524")
        private String value524;
        @SerializedName("513")
        private String value513;
        @SerializedName("502")
        private String value502;
        @SerializedName("525")
        private String value525;
        @SerializedName("514")
        private String value514;
        @SerializedName("503")
        private String value503;
        @SerializedName("515")
        private String value515;
        @SerializedName("504")
        private String value504;
        @SerializedName("516")
        private String value516;
        @SerializedName("505")
        private String value505;
        @SerializedName("517")
        private String value517;
        @SerializedName("506")
        private String value506;
        @SerializedName("518")
        private String value518;
        @SerializedName("507")
        private String value507;
        @SerializedName("519")
        private String value519;
        @SerializedName("508")
        private String value508;
        @SerializedName("509")
        private String value509;

        public String getValue520() {
            return value520;
        }

        public void setValue520(String value520) {
            this.value520 = value520;
        }

        public String getValue510() {
            return value510;
        }

        public void setValue510(String value510) {
            this.value510 = value510;
        }

        public String getValue521() {
            return value521;
        }

        public void setValue521(String value521) {
            this.value521 = value521;
        }

        public String getValue511() {
            return value511;
        }

        public void setValue511(String value511) {
            this.value511 = value511;
        }

        public String getValue512() {
            return value512;
        }

        public void setValue512(String value512) {
            this.value512 = value512;
        }

        public String getValue523() {
            return value523;
        }

        public void setValue523(String value523) {
            this.value523 = value523;
        }

        public String getValue501() {
            return value501;
        }

        public void setValue501(String value501) {
            this.value501 = value501;
        }

        public String getValue524() {
            return value524;
        }

        public void setValue524(String value524) {
            this.value524 = value524;
        }

        public String getValue513() {
            return value513;
        }

        public void setValue513(String value513) {
            this.value513 = value513;
        }

        public String getValue502() {
            return value502;
        }

        public void setValue502(String value502) {
            this.value502 = value502;
        }

        public String getValue525() {
            return value525;
        }

        public void setValue525(String value525) {
            this.value525 = value525;
        }

        public String getValue514() {
            return value514;
        }

        public void setValue514(String value514) {
            this.value514 = value514;
        }

        public String getValue503() {
            return value503;
        }

        public void setValue503(String value503) {
            this.value503 = value503;
        }

        public String getValue515() {
            return value515;
        }

        public void setValue515(String value515) {
            this.value515 = value515;
        }

        public String getValue504() {
            return value504;
        }

        public void setValue504(String value504) {
            this.value504 = value504;
        }

        public String getValue516() {
            return value516;
        }

        public void setValue516(String value516) {
            this.value516 = value516;
        }

        public String getValue505() {
            return value505;
        }

        public void setValue505(String value505) {
            this.value505 = value505;
        }

        public String getValue517() {
            return value517;
        }

        public void setValue517(String value517) {
            this.value517 = value517;
        }

        public String getValue506() {
            return value506;
        }

        public void setValue506(String value506) {
            this.value506 = value506;
        }

        public String getValue518() {
            return value518;
        }

        public void setValue518(String value518) {
            this.value518 = value518;
        }

        public String getValue507() {
            return value507;
        }

        public void setValue507(String value507) {
            this.value507 = value507;
        }

        public String getValue519() {
            return value519;
        }

        public void setValue519(String value519) {
            this.value519 = value519;
        }

        public String getValue508() {
            return value508;
        }

        public void setValue508(String value508) {
            this.value508 = value508;
        }

        public String getValue509() {
            return value509;
        }

        public void setValue509(String value509) {
            this.value509 = value509;
        }
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
}
