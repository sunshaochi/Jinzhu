package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by Administrator on 2015/12/18.
 */
public class HomeHotProductEntity implements Parcelable{


    /**
     * avgSucRate : 90
     * hotProductId : 2c908c7e51765eb40151768b92e10002
     * hotRank : 1
     * productId : 2c908c7e51af19260151af4dfddd0043
     * productChara : 2c908c7651474aa40151478b13820002,2c908c7651474aa40151478beed50003,2c908c7651474aa40151478c2fc70004
     * productCharas : {"2c908c7651474aa40151478c2fc70004":"利率较低","2c908c7651474aa40151478beed50003":"手续便捷","2c908c7651474aa40151478b13820002":"速度快"}
     * imageLogoPath : img/2646181724931072.png
     * compositiveRate : 80
     * productName : 搜易贷  -  购房首付贷--农
     * loanPeriod : 5
     */

    private int avgSucRate;
    private String hotProductId;
    private int hotRank;
    private String productId;
    private String productChara;
    /**
     * 2c908c7651474aa40151478c2fc70004 : 利率较低
     * 2c908c7651474aa40151478beed50003 : 手续便捷
     * 2c908c7651474aa40151478b13820002 : 速度快
     */

    private Map<String,String> productCharas ;
    /**
     * imageLogoPath : img/2646181724931072.png
     * compositiveRate : 80
     * productName : 搜易贷  -  购房首付贷--农
     * loanPeriod : 5
     */

    private String imageLogoPath;
    private String compositiveRate;
    private String productName;
    private String loanPeriod;
    private double monthlyRateMax;//月利率最大值
    private double monthlyRateMin;//月利率最小值

    public double getMonthlyRateMax() {
        return monthlyRateMax;
    }

    public void setMonthlyRateMax(double monthlyRateMax) {
        this.monthlyRateMax = monthlyRateMax;
    }

    public double getMonthlyRateMin() {
        return monthlyRateMin;
    }

    public void setMonthlyRateMin(double monthlyRateMin) {
        this.monthlyRateMin = monthlyRateMin;
    }

    public int getAvgSucRate() {
        return avgSucRate;
    }

    public void setAvgSucRate(int avgSucRate) {
        this.avgSucRate = avgSucRate;
    }

    public String getHotProductId() {
        return hotProductId;
    }

    public void setHotProductId(String hotProductId) {
        this.hotProductId = hotProductId;
    }

    public int getHotRank() {
        return hotRank;
    }

    public void setHotRank(int hotRank) {
        this.hotRank = hotRank;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductChara() {
        return productChara;
    }

    public void setProductChara(String productChara) {
        this.productChara = productChara;
    }

    public Map<String, String> getProductCharas() {
        return productCharas;
    }

    public void setProductCharas(Map<String, String> productCharas) {
        this.productCharas = productCharas;
    }

    public String getImageLogoPath() {
        return imageLogoPath;
    }

    public void setImageLogoPath(String imageLogoPath) {
        this.imageLogoPath = imageLogoPath;
    }

    public String getCompositiveRate() {
        return compositiveRate;
    }

    public void setCompositiveRate(String compositiveRate) {
        this.compositiveRate = compositiveRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }


    public HomeHotProductEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.avgSucRate);
        dest.writeString(this.hotProductId);
        dest.writeInt(this.hotRank);
        dest.writeString(this.productId);
        dest.writeString(this.productChara);
        dest.writeParcelable((Parcelable) this.productCharas, flags);
        dest.writeString(this.imageLogoPath);
        dest.writeString(this.compositiveRate);
        dest.writeString(this.productName);
        dest.writeString(this.loanPeriod);
        dest.writeDouble(this.monthlyRateMax);
        dest.writeDouble(this.monthlyRateMin);
    }

    protected HomeHotProductEntity(Parcel in) {
        this.avgSucRate = in.readInt();
        this.hotProductId = in.readString();
        this.hotRank = in.readInt();
        this.productId = in.readString();
        this.productChara = in.readString();
        this.imageLogoPath = in.readString();
        this.compositiveRate = in.readString();
        this.productName = in.readString();
        this.loanPeriod = in.readString();
        this.monthlyRateMax = in.readDouble();
        this.monthlyRateMin = in.readDouble();
    }

    public static final Creator<HomeHotProductEntity> CREATOR = new Creator<HomeHotProductEntity>() {
        public HomeHotProductEntity createFromParcel(Parcel source) {
            return new HomeHotProductEntity(source);
        }

        public HomeHotProductEntity[] newArray(int size) {
            return new HomeHotProductEntity[size];
        }
    };
}
