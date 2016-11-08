package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户
 * Created by wangbin on 15/11/18.
 */
public class UserEntity implements Parcelable {
    private String username;//用户名
    private String password;//密码
    private String captcha;//短信验证码
    private String roleName;//真实姓名

    private String userName;//用户姓名  s
    private String id;
    private String accountId;//账号ID
    private String accountName;//账号名称
    private String headIcon;//用户头像
    private String email;//邮箱
    private String identCard;//身份证号   s
    private Integer userSex;//性别 0女 1男
    private Integer userAge;//年龄
    private String mobile;//手机号
    private String jobId;//工作id
    private String jobName;//工作名称
    private Integer proFund;//是否有公积金 0无 1有
    private Integer secailSecurity;//是否有本地社保 0无1有
    private String haveCar;//名下是否有车
    private String haveHours;//名下是否有房产
    private String towYearCred;//两年内信用
    private String creditGrade;//授权分
    private String creditScore;//信用分
    private String accountBalance;//账户余额
    private String lineOfCredit;//信用额度
    private String createTime;//创建时间   s
    private String createPersonId;//创建人  s
    private String modifyTime;//更新时间  s
    private String modifyPersonId;//更新人  s
    private Integer isValid;//是否有效   s

    private String currAddress;//常住地
    private String detailAddr;//详细地址  s
    private String province;//省  s
    private String city;//市   s
    private String district;// 区县
    private Integer marrySts;//0未婚 1已婚
    private String nativePlace;//籍贯
    private String nativePlaceProvince;//籍贯省
    private String nativePlaceCity;//籍贯市
    private String nativePlaceDistrict;//籍贯区县
    private String nativePlaceAddrProvince;//户籍省
    private String nativePlaceAddrCity;//户籍市
    private String nativePlaceAddrDistrict;//户籍区县
    private String nativePlaceAddr;//户籍地址
    private String referralCode;//推荐码

    private String towYearCredName;

    private String companyName;//公司名称
    private String business;//职务
    private String havaJobName;//职业身份
    private String haveCarName;//车产
    private String haveHoursName;//房产
    private String rcUserId;//容云useriID
    /**
     * bankName :
     * bankAccNo :
     * wkCardPic : img/2650296798462976.jpg
     * servantId : 2c908c7e51b0211e0151b0981c0301b2
     * customerId : 2c908c7e51af9f460151b0037b210037
     * idCardPic : img/2650296914904064.jpg
     * bankNameTitle : 111111
     */

    private String bankName;//收款支行
    private String bankAccNo;//银行账号
    private String wkCardPic;
    private String servantId;
    private String customerId;
    private String idCardPic;
    private String bankNameTitle;

    /**
     * cashTicketAmount 现金券数量<br>
     * unCashTicketAmount 不可用现金券数量<br>
     * deductionTicketAmount 抵扣券数量<br>
     */
    private String cashTicketAmount;//现金券数量
    private String unCashTicketAmount;//不可用现金券数量
    private String deductionTicketAmount;//抵扣券数量
    /**
     * isSuspension : null
     * creditScoreCount : 0
     * nativePlaceAddrApp :
     * otherAccess : null
     * defaultAddrApp : 安徽省合肥市瑶海区
     * nativePlaceApp :
     * userBehavior : null
     */

    private String nativePlaceAddrApp;//户籍地显示
    private String defaultAddrApp;//常住地显示
    private String nativePlaceApp;//籍贯显示


    public String getCashTicketAmount() {
        return cashTicketAmount;
    }

    public void setCashTicketAmount(String cashTicketAmount) {
        this.cashTicketAmount = cashTicketAmount;
    }

    public String getUnCashTicketAmount() {
        return unCashTicketAmount;
    }

    public void setUnCashTicketAmount(String unCashTicketAmount) {
        this.unCashTicketAmount = unCashTicketAmount;
    }

    public String getDeductionTicketAmount() {
        return deductionTicketAmount;
    }

    public void setDeductionTicketAmount(String deductionTicketAmount) {
        this.deductionTicketAmount = deductionTicketAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentCard() {
        return identCard;
    }

    public void setIdentCard(String identCard) {
        this.identCard = identCard;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getProFund() {
        return proFund;
    }

    public void setProFund(Integer proFund) {
        this.proFund = proFund;
    }

    public Integer getSecailSecurity() {
        return secailSecurity;
    }

    public void setSecailSecurity(Integer secailSecurity) {
        this.secailSecurity = secailSecurity;
    }

    public String getHaveCar() {
        return haveCar;
    }

    public void setHaveCar(String haveCar) {
        this.haveCar = haveCar;
    }

    public String getHaveHours() {
        return haveHours;
    }

    public void setHaveHours(String haveHours) {
        this.haveHours = haveHours;
    }

    public String getTowYearCred() {
        return towYearCred;
    }

    public void setTowYearCred(String towYearCred) {
        this.towYearCred = towYearCred;
    }

    public String getCreditGrade() {
        return creditGrade;
    }

    public void setCreditGrade(String creditGrade) {
        this.creditGrade = creditGrade;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(String lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getCurrAddress() {
        return currAddress;
    }

    public void setCurrAddress(String currAddress) {
        this.currAddress = currAddress;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getMarrySts() {
        return marrySts;
    }

    public void setMarrySts(Integer marrySts) {
        this.marrySts = marrySts;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNativePlaceAddr() {
        return nativePlaceAddr;
    }

    public void setNativePlaceAddr(String nativePlaceAddr) {
        this.nativePlaceAddr = nativePlaceAddr;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getTowYearCredName() {
        return towYearCredName;
    }

    public void setTowYearCredName(String towYearCredName) {
        this.towYearCredName = towYearCredName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getHavaJobName() {
        return havaJobName;
    }

    public void setHavaJobName(String havaJobName) {
        this.havaJobName = havaJobName;
    }

    public String getHaveCarName() {
        return haveCarName;
    }

    public void setHaveCarName(String haveCarName) {
        this.haveCarName = haveCarName;
    }

    public String getHaveHoursName() {
        return haveHoursName;
    }

    public void setHaveHoursName(String haveHoursName) {
        this.haveHoursName = haveHoursName;
    }

    public String getRcUserId() {
        return rcUserId;
    }

    public void setRcUserId(String rcUserId) {
        this.rcUserId = rcUserId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getBankNameTitle() {
        return bankNameTitle;
    }

    public void setBankNameTitle(String bankNameTitle) {
        this.bankNameTitle = bankNameTitle;
    }

    public String getNativePlaceProvince() {
        return nativePlaceProvince;
    }

    public void setNativePlaceProvince(String nativePlaceProvince) {
        this.nativePlaceProvince = nativePlaceProvince;
    }

    public String getNativePlaceCity() {
        return nativePlaceCity;
    }

    public void setNativePlaceCity(String nativePlaceCity) {
        this.nativePlaceCity = nativePlaceCity;
    }

    public String getNativePlaceDistrict() {
        return nativePlaceDistrict;
    }

    public void setNativePlaceDistrict(String nativePlaceDistrict) {
        this.nativePlaceDistrict = nativePlaceDistrict;
    }

    public String getNativePlaceAddrProvince() {
        return nativePlaceAddrProvince;
    }

    public void setNativePlaceAddrProvince(String nativePlaceAddrProvince) {
        this.nativePlaceAddrProvince = nativePlaceAddrProvince;
    }

    public String getNativePlaceAddrCity() {
        return nativePlaceAddrCity;
    }

    public void setNativePlaceAddrCity(String nativePlaceAddrCity) {
        this.nativePlaceAddrCity = nativePlaceAddrCity;
    }

    public String getNativePlaceAddrDistrict() {
        return nativePlaceAddrDistrict;
    }

    public void setNativePlaceAddrDistrict(String nativePlaceAddrDistrict) {
        this.nativePlaceAddrDistrict = nativePlaceAddrDistrict;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.captcha);
        dest.writeString(this.roleName);
        dest.writeString(this.userName);
        dest.writeString(this.id);
        dest.writeString(this.accountId);
        dest.writeString(this.accountName);
        dest.writeString(this.headIcon);
        dest.writeString(this.email);
        dest.writeString(this.identCard);
        dest.writeValue(this.userSex);
        dest.writeValue(this.userAge);
        dest.writeString(this.mobile);
        dest.writeString(this.jobId);
        dest.writeString(this.jobName);
        dest.writeValue(this.proFund);
        dest.writeValue(this.secailSecurity);
        dest.writeString(this.haveCar);
        dest.writeString(this.haveHours);
        dest.writeString(this.towYearCred);
        dest.writeString(this.creditGrade);
        dest.writeString(this.creditScore);
        dest.writeString(this.accountBalance);
        dest.writeString(this.lineOfCredit);
        dest.writeString(this.createTime);
        dest.writeString(this.createPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.modifyPersonId);
        dest.writeValue(this.isValid);
        dest.writeString(this.currAddress);
        dest.writeString(this.detailAddr);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeValue(this.marrySts);
        dest.writeString(this.nativePlace);
        dest.writeString(this.nativePlaceAddr);
        dest.writeString(this.referralCode);
        dest.writeString(this.towYearCredName);
        dest.writeString(this.companyName);
        dest.writeString(this.business);
        dest.writeString(this.havaJobName);
        dest.writeString(this.haveCarName);
        dest.writeString(this.haveHoursName);
        dest.writeString(this.rcUserId);
        dest.writeString(this.bankName);
        dest.writeString(this.bankAccNo);
        dest.writeString(this.wkCardPic);
        dest.writeString(this.servantId);
        dest.writeString(this.customerId);
        dest.writeString(this.idCardPic);
        dest.writeString(this.bankNameTitle);
        dest.writeString(this.cashTicketAmount);
        dest.writeString(this.unCashTicketAmount);
        dest.writeString(this.deductionTicketAmount);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.captcha = in.readString();
        this.roleName = in.readString();
        this.userName = in.readString();
        this.id = in.readString();
        this.accountId = in.readString();
        this.accountName = in.readString();
        this.headIcon = in.readString();
        this.email = in.readString();
        this.identCard = in.readString();
        this.userSex = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userAge = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mobile = in.readString();
        this.jobId = in.readString();
        this.jobName = in.readString();
        this.proFund = (Integer) in.readValue(Integer.class.getClassLoader());
        this.secailSecurity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.haveCar = in.readString();
        this.haveHours = in.readString();
        this.towYearCred = in.readString();
        this.creditGrade = in.readString();
        this.creditScore = in.readString();
        this.accountBalance = in.readString();
        this.lineOfCredit = in.readString();
        this.createTime = in.readString();
        this.createPersonId = in.readString();
        this.modifyTime = in.readString();
        this.modifyPersonId = in.readString();
        this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.currAddress = in.readString();
        this.detailAddr = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.marrySts = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nativePlace = in.readString();
        this.nativePlaceAddr = in.readString();
        this.referralCode = in.readString();
        this.towYearCredName = in.readString();
        this.companyName = in.readString();
        this.business = in.readString();
        this.havaJobName = in.readString();
        this.haveCarName = in.readString();
        this.haveHoursName = in.readString();
        this.rcUserId = in.readString();
        this.bankName = in.readString();
        this.bankAccNo = in.readString();
        this.wkCardPic = in.readString();
        this.servantId = in.readString();
        this.customerId = in.readString();
        this.idCardPic = in.readString();
        this.bankNameTitle = in.readString();
        this.cashTicketAmount=in.readString();
        this.unCashTicketAmount=in.readString();
        this.deductionTicketAmount=in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    public String getNativePlaceAddrApp() {
        return nativePlaceAddrApp;
    }

    public void setNativePlaceAddrApp(String nativePlaceAddrApp) {
        this.nativePlaceAddrApp = nativePlaceAddrApp;
    }

    public String getDefaultAddrApp() {
        return defaultAddrApp;
    }

    public void setDefaultAddrApp(String defaultAddrApp) {
        this.defaultAddrApp = defaultAddrApp;
    }

    public String getNativePlaceApp() {
        return nativePlaceApp;
    }

    public void setNativePlaceApp(String nativePlaceApp) {
        this.nativePlaceApp = nativePlaceApp;
    }
}