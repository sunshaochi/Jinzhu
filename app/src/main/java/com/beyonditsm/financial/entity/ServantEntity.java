package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gxy on 2015/11/25.
 */
public class ServantEntity implements Parcelable {


    /**
     * headIcon :
     * accountName : 18311111111
     * haveHours : null
     * city : null
     * creditGrade : null
     * bankName : 643462866668
     * createPersonId : 4028b88150b222f90150b22cfac20005
     * bankAccNo : 5887415666995496695
     * wkCardPic :
     * servantId : 2c908c6e5141bef1015141c574950002
     * userAge : null
     * detailAddr : 福建省福州市鼓楼区
     * proFund : 0
     * modifyTime : 1449045187000
     * lineOfCredit : null
     * province : null
     * customerId : 2c908c7e514c18e001514cddb148000b
     * id : 2c908c7e514c18e001514cddb148000b
     * email : 4111111@163.com
     * jobName : null
     * userSex : 1
     * creditScore : null
     * isValid : 1
     * mobile :
     * secailSecurity : 0
     * userName : 服务者
     * nativePlaceAddr : null
     * marrySts : null
     * accountId : 2c908c7e514c18e001514cddb1450009
     * jobId : null
     * identCard : 411323199006120054
     * towYearCred : null
     * createTime : 1448507435000
     * haveCar : null
     * district : null
     * nativePlace : null
     * modifyPersonId : 2c908c7e514c18e001514cddb1450009
     * idCardPic : img/2624723827606528.png
     * accountBalance : null
     * bankNameTitle : 1111113236698
     */

    private String headIcon;//头像
    private String accountName;//账户姓名
    private String haveHours;//名下是否有房产
    private String city;//市
    private Integer creditGrade;//授权分
    private String bankName;//银行名称
    private String createPersonId;//创建人ID
    private String bankAccNo;//银行账号
    private String wkCardPic;//工作证图片
    private String servantId;//服务者ID
    private Integer userAge;//年龄
    private String detailAddr;//详细地址
    private Integer proFund;//是否有公积金 1有 0 无
    private long modifyTime;//修改时间
    private Integer lineOfCredit;//信用额度
    private String province;//省
    private String customerId;//客户ID
    private String id;
    private String email;//电子邮箱
    private String jobName;//职位名字
    private Integer userSex;//性别
    private Integer creditScore;//信用分
    private Integer isValid;//是否有效 1有 0 无
    private String mobile;//手机号
    private Integer secailSecurity;//是否有本地社保 1有 0 无
    private String userName;//真实姓名
    private String nativePlaceAddr;//户籍地址
    private String marrySts;//是否结婚 1 已婚 0 未婚
    private String accountId;//账号ID
    private String jobId;//工作id
    private String identCard;//身份证号
    private String towYearCred;//
    private String createTime;//创建时间
    private String haveCar;//名下是否有车
    private String district;//区
    private String nativePlace;//籍贯
    private String modifyPersonId;//修改人id
    private String idCardPic;//身份证图片
    private String accountBalance;//账户余额
    private String bankNameTitle;//支行名字

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getHaveHours() {
        return haveHours;
    }

    public void setHaveHours(String haveHours) {
        this.haveHours = haveHours;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCreditGrade() {
        return creditGrade;
    }

    public void setCreditGrade(Integer creditGrade) {
        this.creditGrade = creditGrade;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getWkCardPic() {
        return wkCardPic;
    }

    public void setWkCardPic(String wkCardPic) {
        this.wkCardPic = wkCardPic;
    }

    public String getServantId() {
        return servantId;
    }

    public void setServantId(String servantId) {
        this.servantId = servantId;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public Integer getProFund() {
        return proFund;
    }

    public void setProFund(Integer proFund) {
        this.proFund = proFund;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(Integer lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSecailSecurity() {
        return secailSecurity;
    }

    public void setSecailSecurity(Integer secailSecurity) {
        this.secailSecurity = secailSecurity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNativePlaceAddr() {
        return nativePlaceAddr;
    }

    public void setNativePlaceAddr(String nativePlaceAddr) {
        this.nativePlaceAddr = nativePlaceAddr;
    }

    public String getMarrySts() {
        return marrySts;
    }

    public void setMarrySts(String marrySts) {
        this.marrySts = marrySts;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getIdentCard() {
        return identCard;
    }

    public void setIdentCard(String identCard) {
        this.identCard = identCard;
    }

    public String getTowYearCred() {
        return towYearCred;
    }

    public void setTowYearCred(String towYearCred) {
        this.towYearCred = towYearCred;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHaveCar() {
        return haveCar;
    }

    public void setHaveCar(String haveCar) {
        this.haveCar = haveCar;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getBankNameTitle() {
        return bankNameTitle;
    }

    public void setBankNameTitle(String bankNameTitle) {
        this.bankNameTitle = bankNameTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.headIcon);
        dest.writeString(this.accountName);
        dest.writeString(this.haveHours);
        dest.writeString(this.city);
        dest.writeValue(this.creditGrade);
        dest.writeString(this.bankName);
        dest.writeString(this.createPersonId);
        dest.writeString(this.bankAccNo);
        dest.writeString(this.wkCardPic);
        dest.writeString(this.servantId);
        dest.writeValue(this.userAge);
        dest.writeString(this.detailAddr);
        dest.writeValue(this.proFund);
        dest.writeLong(this.modifyTime);
        dest.writeValue(this.lineOfCredit);
        dest.writeString(this.province);
        dest.writeString(this.customerId);
        dest.writeString(this.id);
        dest.writeString(this.email);
        dest.writeString(this.jobName);
        dest.writeValue(this.userSex);
        dest.writeValue(this.creditScore);
        dest.writeValue(this.isValid);
        dest.writeString(this.mobile);
        dest.writeValue(this.secailSecurity);
        dest.writeString(this.userName);
        dest.writeString(this.nativePlaceAddr);
        dest.writeString(this.marrySts);
        dest.writeString(this.accountId);
        dest.writeString(this.jobId);
        dest.writeString(this.identCard);
        dest.writeString(this.towYearCred);
        dest.writeString(this.createTime);
        dest.writeString(this.haveCar);
        dest.writeString(this.district);
        dest.writeString(this.nativePlace);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.idCardPic);
//        dest.writeValue(this.accountBalance);
        dest.writeString(this.accountBalance);
        dest.writeString(this.bankNameTitle);
    }

    public ServantEntity() {
    }

    protected ServantEntity(Parcel in) {
        this.headIcon = in.readString();
        this.accountName = in.readString();
        this.haveHours = in.readString();
        this.city = in.readString();
        this.creditGrade = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bankName = in.readString();
        this.createPersonId = in.readString();
        this.bankAccNo = in.readString();
        this.wkCardPic = in.readString();
        this.servantId = in.readString();
        this.userAge = (Integer) in.readValue(Integer.class.getClassLoader());
        this.detailAddr = in.readString();
        this.proFund = (Integer) in.readValue(Integer.class.getClassLoader());
        this.modifyTime = in.readLong();
        this.lineOfCredit = (Integer) in.readValue(Integer.class.getClassLoader());
        this.province = in.readString();
        this.customerId = in.readString();
        this.id = in.readString();
        this.email = in.readString();
        this.jobName = in.readString();
        this.userSex = (Integer) in.readValue(Integer.class.getClassLoader());
        this.creditScore = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mobile = in.readString();
        this.secailSecurity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userName = in.readString();
        this.nativePlaceAddr = in.readString();
        this.marrySts = in.readString();
        this.accountId = in.readString();
        this.jobId = in.readString();
        this.identCard = in.readString();
        this.towYearCred = in.readString();
        this.createTime = in.readString();
        this.haveCar = in.readString();
        this.district = in.readString();
        this.nativePlace = in.readString();
        this.modifyPersonId = in.readString();
        this.idCardPic = in.readString();
//        this.accountBalance = (Double) in.readValue(Double.class.getClassLoader());
        this.accountBalance = in.readString();
        this.bankNameTitle = in.readString();
    }

    public static final Creator<ServantEntity> CREATOR = new Creator<ServantEntity>() {
        public ServantEntity createFromParcel(Parcel source) {
            return new ServantEntity(source);
        }

        public ServantEntity[] newArray(int size) {
            return new ServantEntity[size];
        }
    };
}
