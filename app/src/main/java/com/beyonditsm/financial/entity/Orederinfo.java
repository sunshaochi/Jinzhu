package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bitch-1 on 2016/12/6.
 */

public class Orederinfo implements Parcelable {
    public productInfoBean productInfo;//产品相关的
    public orderInfoBean orderInfo;//订单相关
    public customerInfoBean customerInfo;//用户相关

    public Orederinfo() {
    }

    public productInfoBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(productInfoBean productInfo) {
        this.productInfo = productInfo;
    }

    public orderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(orderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public customerInfoBean getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(customerInfoBean customerInfo) {
        this.customerInfo = customerInfo;
    }


    public class productInfoBean implements Parcelable{
        private String productId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.productId);
        }

        public productInfoBean() {
        }

        protected productInfoBean(Parcel in) {
            this.productId = in.readString();
        }

        public final Creator<productInfoBean> CREATOR = new Creator<productInfoBean>() {
            @Override
            public productInfoBean createFromParcel(Parcel source) {
                return new productInfoBean(source);
            }

            @Override
            public productInfoBean[] newArray(int size) {
                return new productInfoBean[size];
            }
        };
    }

    public class orderInfoBean implements Serializable{
        private String applyAmount;
        private String applyPeriods;

        public String getApplyAmount() {
            return applyAmount;
        }

        public void setApplyAmount(String applyAmount) {
            this.applyAmount = applyAmount;
        }

        public String getApplyPeriods() {
            return applyPeriods;
        }

        public void setApplyPeriods(String applyPeriods) {
            this.applyPeriods = applyPeriods;
        }
    }
    public class customerInfoBean implements Serializable{
       private String cusName;//用户姓名,
        private int sex;//性别,
        private String idNo;//身份证号,
        private String currentProvince;// 常住地省,
        private String currentCity;//常住地市,
        private String currentRegion;// 常住地区,
        private int isMarried;// 婚姻状况,
        private String nativePlace;// 籍贯,
        private String domicileAddr;// 户籍地,
        private String phoneNum;// 手机号码,
        private String careerName;// 职业身份,
        private String company;// 公司,
        private String careerTitle;//职务,
        private int age;//年龄,
        private int hasHouseFunding;//是否有公积金,
        private int hasSocialInsurance;// 是否有社保,
        private String haveOwnCar;//是否有,
        private String haveOwnHouse;//是否有房,
        private String creditState;//信用状况

        public String getCusName() {
            return cusName;
        }

        public void setCusName(String cusName) {
            this.cusName = cusName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getCurrentProvince() {
            return currentProvince;
        }

        public void setCurrentProvince(String currentProvince) {
            this.currentProvince = currentProvince;
        }

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getCurrentRegion() {
            return currentRegion;
        }

        public void setCurrentRegion(String currentRegion) {
            this.currentRegion = currentRegion;
        }

        public int getIsMarried() {
            return isMarried;
        }

        public void setIsMarried(int isMarried) {
            this.isMarried = isMarried;
        }

        public String getNativePlace() {
            return nativePlace;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace;
        }

        public String getDomicileAddr() {
            return domicileAddr;
        }

        public void setDomicileAddr(String domicileAddr) {
            this.domicileAddr = domicileAddr;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getCareerName() {
            return careerName;
        }

        public void setCareerName(String careerName) {
            this.careerName = careerName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCareerTitle() {
            return careerTitle;
        }

        public void setCareerTitle(String careerTitle) {
            this.careerTitle = careerTitle;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHasHouseFunding() {
            return hasHouseFunding;
        }

        public void setHasHouseFunding(int hasHouseFunding) {
            this.hasHouseFunding = hasHouseFunding;
        }

        public int getHasSocialInsurance() {
            return hasSocialInsurance;
        }

        public void setHasSocialInsurance(int hasSocialInsurance) {
            this.hasSocialInsurance = hasSocialInsurance;
        }

        public String getHaveOwnCar() {
            return haveOwnCar;
        }

        public void setHaveOwnCar(String haveOwnCar) {
            this.haveOwnCar = haveOwnCar;
        }

        public String getHaveOwnHouse() {
            return haveOwnHouse;
        }

        public void setHaveOwnHouse(String haveOwnHouse) {
            this.haveOwnHouse = haveOwnHouse;
        }

        public String getCreditState() {
            return creditState;
        }

        public void setCreditState(String creditState) {
            this.creditState = creditState;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.productInfo, flags);
        dest.writeSerializable(this.orderInfo);
        dest.writeSerializable(this.customerInfo);
    }

    protected Orederinfo(Parcel in) {
        this.productInfo = in.readParcelable(productInfoBean.class.getClassLoader());
        this.orderInfo = (orderInfoBean) in.readSerializable();
        this.customerInfo = (customerInfoBean) in.readSerializable();
    }

    public static final Creator<Orederinfo> CREATOR = new Creator<Orederinfo>() {
        @Override
        public Orederinfo createFromParcel(Parcel source) {
            return new Orederinfo(source);
        }

        @Override
        public Orederinfo[] newArray(int size) {
            return new Orederinfo[size];
        }
    };
}
