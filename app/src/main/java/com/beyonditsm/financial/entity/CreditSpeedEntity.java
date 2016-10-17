package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

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

    private int timeMaxVal;//最大期限
    private double maxRate;//最大利率
    private String imageLogoPath;//产品图片
    private String productName;//产品名称
    private int timeMinVal;//最小期限
    private double maxVal;//最大额度
    private double minVal;//最小额度
    private String productChara;//产品介绍
    private double minRate;//最小利率
    private String id;
    private String productNo;//
    private int loanPeriod;//放款周期
    private String costDescribe;//产品要求

    protected CreditSpeedEntity(Parcel in) {
        timeMaxVal = in.readInt();
        maxRate = in.readDouble();
        imageLogoPath = in.readString();
        productName = in.readString();
        timeMinVal = in.readInt();
        maxVal = in.readDouble();
        minVal = in.readDouble();
        productChara = in.readString();
        minRate = in.readDouble();
        id = in.readString();
        productNo = in.readString();
        loanPeriod = in.readInt();
        costDescribe = in.readString();
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

    public int getTimeMaxVal() {
        return timeMaxVal;
    }

    public void setTimeMaxVal(int timeMaxVal) {
        this.timeMaxVal = timeMaxVal;
    }

    public double getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(double maxRate) {
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

    public int getTimeMinVal() {
        return timeMinVal;
    }

    public void setTimeMinVal(int timeMinVal) {
        this.timeMinVal = timeMinVal;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(double maxVal) {
        this.maxVal = maxVal;
    }

    public double getMinVal() {
        return minVal;
    }

    public void setMinVal(double minVal) {
        this.minVal = minVal;
    }

    public String getProductChara() {
        return productChara;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public double getMinRate() {
        return minRate;
    }

    public void setMinRate(double minRate) {
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

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
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
        parcel.writeInt(timeMaxVal);
        parcel.writeDouble(maxRate);
        parcel.writeString(imageLogoPath);
        parcel.writeString(productName);
        parcel.writeInt(timeMinVal);
        parcel.writeDouble(maxVal);
        parcel.writeDouble(minVal);
        parcel.writeString(productChara);
        parcel.writeDouble(minRate);
        parcel.writeString(id);
        parcel.writeString(productNo);
        parcel.writeInt(loanPeriod);
        parcel.writeString(costDescribe);
    }
}
