package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/12/6.
 */

public class Orederinfo implements Parcelable {

    /**
     * productInfo : {"productId":"142e7774d7c14ced91bba5cf10638c10"}
     * orderInfo : {"applyAmount":30000,"applyPeriods":12}
     * customerInfo : {"cusName":"小明","sex":1,"idNo":"511702198304257904","currentProvince":"0001","currentCity":"0003","currentRegion":"0032","isMarried":1,"nativePlace":"安徽","domicileAddr":"安徽","phoneNum":"13678651234","careerName":"32446354dsaa2134da","company":"百度","careerTitle":"93249832420dsfsa43","age":32,"hasHouseFunding":1,"hasSocialInsurance":1,"haveOwnCar":"984329ujisekjsdk","haveOwnHouse":"89324uiijewjk","creditState":"98432jidsoi3278"}
     */

    private ProductInfoBean productInfo;
    private OrderInfoBean orderInfo;
    private CustomerInfoBean customerInfo;

    public ProductInfoBean getProductInfo() {
        if (productInfo == null) {
            productInfo = new ProductInfoBean();
        }
        return productInfo;
    }

    public void setProductInfo(ProductInfoBean productInfo) {
        this.productInfo = productInfo;
    }

    public OrderInfoBean getOrderInfo() {
        if (orderInfo == null) {
            orderInfo = new OrderInfoBean();
        }
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public CustomerInfoBean getCustomerInfo() {
        if (customerInfo == null) {
            customerInfo = new CustomerInfoBean();
        }
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoBean customerInfo) {
        this.customerInfo = customerInfo;
    }

    public static class ProductInfoBean implements Parcelable {
        /**
         * productId : 142e7774d7c14ced91bba5cf10638c10
         */

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

        public ProductInfoBean() {
        }

        protected ProductInfoBean(Parcel in) {
            this.productId = in.readString();
        }

        public static final Creator<ProductInfoBean> CREATOR = new Creator<ProductInfoBean>() {
            @Override
            public ProductInfoBean createFromParcel(Parcel source) {
                return new ProductInfoBean(source);
            }

            @Override
            public ProductInfoBean[] newArray(int size) {
                return new ProductInfoBean[size];
            }
        };
    }

    public static class OrderInfoBean implements Parcelable {
        /**
         * applyAmount : 30000
         * applyPeriods : 12
         */

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.applyAmount);
            dest.writeString(this.applyPeriods);
        }

        public OrderInfoBean() {
        }

        protected OrderInfoBean(Parcel in) {
            this.applyAmount = in.readString();
            this.applyPeriods = in.readString();
        }

        public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
            @Override
            public OrderInfoBean createFromParcel(Parcel source) {
                return new OrderInfoBean(source);
            }

            @Override
            public OrderInfoBean[] newArray(int size) {
                return new OrderInfoBean[size];
            }
        };
    }

    public static class CustomerInfoBean implements Parcelable {
        /**
         * cusName : 小明
         * sex : 1
         * idNo : 511702198304257904
         * currentProvince : 0001
         * currentCity : 0003
         * currentRegion : 0032
         * isMarried : 1
         * nativePlace : 安徽
         * domicileAddr : 安徽
         * phoneNum : 13678651234
         * careerName : 32446354dsaa2134da
         * company : 百度
         * careerTitle : 93249832420dsfsa43
         * age : 32
         * hasHouseFunding : 1
         * hasSocialInsurance : 1
         * haveOwnCar : 984329ujisekjsdk
         * haveOwnHouse : 89324uiijewjk
         * creditState : 98432jidsoi3278
         */

        private String cusName;
        private String sex;
        private String idNo;
        private String currentProvince;
        private String currentCity;
        private String currentRegion;
        private String isMarried;
        private String nativePlace;
        private String domicileAddr;
        private String phoneNum;
        private String careerName;
        private String company;
        private String careerTitle;
        private String age;
        private String hasHouseFunding;
        private String hasSocialInsurance;
        private String haveOwnCar;
        private String haveOwnHouse;
        private String creditState;

        public String getCusName() {
            return cusName;
        }

        public void setCusName(String cusName) {
            this.cusName = cusName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
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

        public String getIsMarried() {
            return isMarried;
        }

        public void setIsMarried(String isMarried) {
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getHasHouseFunding() {
            return hasHouseFunding;
        }

        public void setHasHouseFunding(String hasHouseFunding) {
            this.hasHouseFunding = hasHouseFunding;
        }

        public String getHasSocialInsurance() {
            return hasSocialInsurance;
        }

        public void setHasSocialInsurance(String hasSocialInsurance) {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cusName);
            dest.writeString(this.sex);
            dest.writeString(this.idNo);
            dest.writeString(this.currentProvince);
            dest.writeString(this.currentCity);
            dest.writeString(this.currentRegion);
            dest.writeString(this.isMarried);
            dest.writeString(this.nativePlace);
            dest.writeString(this.domicileAddr);
            dest.writeString(this.phoneNum);
            dest.writeString(this.careerName);
            dest.writeString(this.company);
            dest.writeString(this.careerTitle);
            dest.writeString(this.age);
            dest.writeString(this.hasHouseFunding);
            dest.writeString(this.hasSocialInsurance);
            dest.writeString(this.haveOwnCar);
            dest.writeString(this.haveOwnHouse);
            dest.writeString(this.creditState);
        }

        public CustomerInfoBean() {
        }

        protected CustomerInfoBean(Parcel in) {
            this.cusName = in.readString();
            this.sex = in.readString();
            this.idNo = in.readString();
            this.currentProvince = in.readString();
            this.currentCity = in.readString();
            this.currentRegion = in.readString();
            this.isMarried = in.readString();
            this.nativePlace = in.readString();
            this.domicileAddr = in.readString();
            this.phoneNum = in.readString();
            this.careerName = in.readString();
            this.company = in.readString();
            this.careerTitle = in.readString();
            this.age = in.readString();
            this.hasHouseFunding = in.readString();
            this.hasSocialInsurance = in.readString();
            this.haveOwnCar = in.readString();
            this.haveOwnHouse = in.readString();
            this.creditState = in.readString();
        }

        public static final Creator<CustomerInfoBean> CREATOR = new Creator<CustomerInfoBean>() {
            @Override
            public CustomerInfoBean createFromParcel(Parcel source) {
                return new CustomerInfoBean(source);
            }

            @Override
            public CustomerInfoBean[] newArray(int size) {
                return new CustomerInfoBean[size];
            }
        };
    }

    public Orederinfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.productInfo, flags);
        dest.writeParcelable(this.orderInfo, flags);
        dest.writeParcelable(this.customerInfo, flags);
    }

    protected Orederinfo(Parcel in) {
        this.productInfo = in.readParcelable(ProductInfoBean.class.getClassLoader());
        this.orderInfo = in.readParcelable(OrderInfoBean.class.getClassLoader());
        this.customerInfo = in.readParcelable(CustomerInfoBean.class.getClassLoader());
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
