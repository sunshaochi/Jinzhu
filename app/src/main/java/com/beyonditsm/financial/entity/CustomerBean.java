package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class CustomerBean implements Parcelable {
    /**
     * 订单的客户信息ID
     */
    private String orderCustomerId;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 客户姓名
     */
    private String cusName;

    /**
     * 地区
     */
    private String cusRegion;

    /**
     * 客户来源
     */
    private String cusOrigin;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否为黑名单
     */
    private Boolean isInBlacklist;

    /**
     * 是否已婚, 0:未婚,1:已婚
     */
    private Boolean isMarried;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 常住地省
     */
    private String currentProvince;

    /**
     * 常住地市
     */
    private String currentCtiy;

    /**
     * 常住地区
     */
    private String currentRegion;

    /**
     * 户籍地
     */
    private String domicileAddr;

    /**
     * 职业身份
     */
    private String careerName;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位
     */
    private String careerTitle;

    /**
     * 是否有公积金. 0:无，1:有
     */
    private Boolean hasHouseFunding;

    /**
     * 是否有本地社保. 0:无，1:有
     */
    private Boolean hasSocialInsurance;

    /**
     * 是否有车
     */
    private String haveOwnCar;

    /**
     * 是否有房产
     */
    private String haveOwnHouse;

    /**
     * 信用状况
     */
    private String creditState;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 订单的完整客户信息，json格式存储
     */
    private String customerInfo;


    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusRegion() {
        return cusRegion;
    }

    public void setCusRegion(String cusRegion) {
        this.cusRegion = cusRegion;
    }

    public String getCusOrigin() {
        return cusOrigin;
    }

    public void setCusOrigin(String cusOrigin) {
        this.cusOrigin = cusOrigin;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getInBlacklist() {
        return isInBlacklist;
    }

    public void setInBlacklist(Boolean inBlacklist) {
        isInBlacklist = inBlacklist;
    }

    public Boolean getMarried() {
        return isMarried;
    }

    public void setMarried(Boolean married) {
        isMarried = married;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getCurrentProvince() {
        return currentProvince;
    }

    public void setCurrentProvince(String currentProvince) {
        this.currentProvince = currentProvince;
    }

    public String getCurrentCtiy() {
        return currentCtiy;
    }

    public void setCurrentCtiy(String currentCtiy) {
        this.currentCtiy = currentCtiy;
    }

    public String getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(String currentRegion) {
        this.currentRegion = currentRegion;
    }

    public String getDomicileAddr() {
        return domicileAddr;
    }

    public void setDomicileAddr(String domicileAddr) {
        this.domicileAddr = domicileAddr;
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

    public Boolean getHasHouseFunding() {
        return hasHouseFunding;
    }

    public void setHasHouseFunding(Boolean hasHouseFunding) {
        this.hasHouseFunding = hasHouseFunding;
    }

    public Boolean getHasSocialInsurance() {
        return hasSocialInsurance;
    }

    public void setHasSocialInsurance(Boolean hasSocialInsurance) {
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderCustomerId);
        dest.writeString(this.idNo);
        dest.writeString(this.orderId);
        dest.writeString(this.uid);
        dest.writeString(this.phoneNum);
        dest.writeString(this.cusName);
        dest.writeString(this.cusRegion);
        dest.writeString(this.cusOrigin);
        dest.writeValue(this.sex);
        dest.writeValue(this.age);
        dest.writeValue(this.isInBlacklist);
        dest.writeValue(this.isMarried);
        dest.writeString(this.nativePlace);
        dest.writeString(this.currentProvince);
        dest.writeString(this.currentCtiy);
        dest.writeString(this.currentRegion);
        dest.writeString(this.domicileAddr);
        dest.writeString(this.careerName);
        dest.writeString(this.company);
        dest.writeString(this.careerTitle);
        dest.writeValue(this.hasHouseFunding);
        dest.writeValue(this.hasSocialInsurance);
        dest.writeString(this.haveOwnCar);
        dest.writeString(this.haveOwnHouse);
        dest.writeString(this.creditState);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeString(this.customerInfo);
    }

    public CustomerBean() {
    }

    protected CustomerBean(Parcel in) {
        this.orderCustomerId = in.readString();
        this.idNo = in.readString();
        this.orderId = in.readString();
        this.uid = in.readString();
        this.phoneNum = in.readString();
        this.cusName = in.readString();
        this.cusRegion = in.readString();
        this.cusOrigin = in.readString();
        this.sex = (Byte) in.readValue(Byte.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isInBlacklist = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isMarried = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.nativePlace = in.readString();
        this.currentProvince = in.readString();
        this.currentCtiy = in.readString();
        this.currentRegion = in.readString();
        this.domicileAddr = in.readString();
        this.careerName = in.readString();
        this.company = in.readString();
        this.careerTitle = in.readString();
        this.hasHouseFunding = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hasSocialInsurance = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.haveOwnCar = in.readString();
        this.haveOwnHouse = in.readString();
        this.creditState = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.customerInfo = in.readString();
    }

    public static final Creator<CustomerBean> CREATOR = new Creator<CustomerBean>() {
        @Override
        public CustomerBean createFromParcel(Parcel source) {
            return new CustomerBean(source);
        }

        @Override
        public CustomerBean[] newArray(int size) {
            return new CustomerBean[size];
        }
    };
}
