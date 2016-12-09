package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取推荐产品需要上传的实体
 * Created by bitch-1 on 2016/12/8.
 */

public class TujianBean implements Parcelable {
    private String eduLevel;//学历
    private String domicile ;//户籍(String)
    private String creditStatusKey;// 信用情况(String)
    private String jobIdentityKey;//职位(String)
    private String age;//年龄(Integer)
    private String licenseTimeLength;// 工作或执照时长(Integer)
    private String carStatusKey;// 名下是否有车(1或0)
    private String propertyTypeKey;//名下是否有房(1或0)
    private String salary;// 薪水(Double)
    private String guaranteeSlip;// 是否有保单(1或0)
    private String otherAssets;// 其他资产证明(1或0)
    private String fundTimeLength;//公积金社保时长(Integer)
    private String creditMoney;//金额
    private String creditTime;//期限

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getCreditStatusKey() {
        return creditStatusKey;
    }

    public void setCreditStatusKey(String creditStatusKey) {
        this.creditStatusKey = creditStatusKey;
    }

    public String getJobIdentityKey() {
        return jobIdentityKey;
    }

    public void setJobIdentityKey(String jobIdentityKey) {
        this.jobIdentityKey = jobIdentityKey;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLicenseTimeLength() {
        return licenseTimeLength;
    }

    public void setLicenseTimeLength(String licenseTimeLength) {
        this.licenseTimeLength = licenseTimeLength;
    }

    public String getCarStatusKey() {
        return carStatusKey;
    }

    public void setCarStatusKey(String carStatusKey) {
        this.carStatusKey = carStatusKey;
    }

    public String getPropertyTypeKey() {
        return propertyTypeKey;
    }

    public void setPropertyTypeKey(String propertyTypeKey) {
        this.propertyTypeKey = propertyTypeKey;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getGuaranteeSlip() {
        return guaranteeSlip;
    }

    public void setGuaranteeSlip(String guaranteeSlip) {
        this.guaranteeSlip = guaranteeSlip;
    }

    public String getOtherAssets() {
        return otherAssets;
    }

    public void setOtherAssets(String otherAssets) {
        this.otherAssets = otherAssets;
    }

    public String getFundTimeLength() {
        return fundTimeLength;
    }

    public void setFundTimeLength(String fundTimeLength) {
        this.fundTimeLength = fundTimeLength;
    }

    public String getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(String creditMoney) {
        this.creditMoney = creditMoney;
    }

    public String getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(String creditTime) {
        this.creditTime = creditTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eduLevel);
        dest.writeString(this.domicile);
        dest.writeString(this.creditStatusKey);
        dest.writeString(this.jobIdentityKey);
        dest.writeString(this.age);
        dest.writeString(this.licenseTimeLength);
        dest.writeString(this.carStatusKey);
        dest.writeString(this.propertyTypeKey);
        dest.writeString(this.salary);
        dest.writeString(this.guaranteeSlip);
        dest.writeString(this.otherAssets);
        dest.writeString(this.fundTimeLength);
        dest.writeString(this.creditMoney);
        dest.writeString(this.creditTime);
    }

    public TujianBean() {
    }

    protected TujianBean(Parcel in) {
        this.eduLevel = in.readString();
        this.domicile = in.readString();
        this.creditStatusKey = in.readString();
        this.jobIdentityKey = in.readString();
        this.age = in.readString();
        this.licenseTimeLength = in.readString();
        this.carStatusKey = in.readString();
        this.propertyTypeKey = in.readString();
        this.salary = in.readString();
        this.guaranteeSlip = in.readString();
        this.otherAssets = in.readString();
        this.fundTimeLength = in.readString();
        this.creditMoney = in.readString();
        this.creditTime = in.readString();
    }

    public static final Parcelable.Creator<TujianBean> CREATOR = new Parcelable.Creator<TujianBean>() {
        @Override
        public TujianBean createFromParcel(Parcel source) {
            return new TujianBean(source);
        }

        @Override
        public TujianBean[] newArray(int size) {
            return new TujianBean[size];
        }
    };
}
