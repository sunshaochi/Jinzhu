package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yang on 2015/11/26 0026.
 */
public class GrabOrderBean {
    private int total;
    private List<RowsEntity> rows;
    public void setTotal(int total) {
        this.total = total;
    }
    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }
    public int getTotal() {
        return total;
    }
    public List<RowsEntity> getRows() {
        return rows;
    }
    public static class RowsEntity implements Parcelable{
        private String accountName;//账号名称
        private String headIcon;//用户头像
        private String haveHours;//名下是否有房产
        private String city;//市
        private String periodsAmount;//单期还款金额
        private String creditGrade;//授信分
        private String createPersonId;//创建人
        private String totalPeriods;//总期数 单位 月
        private String userAge;//用户年龄
        private String detailAddr;//详细地址
        private String creditManagerId;//信贷经理id
        private String proFund;//是否有公积金（0：无，1：有）
        private String modifyTime;//修改时间
        private String lineOfCredit;//信用额度
        private String province;//省
        private String orderSts;//订单状态
        private String practicalLoan;//实际放款额
        private String id;//主键id
        private String practicalLoanDate;//实际放款日期
        private String customerEvaluate;//管理平台给客户的评价
        private String email;//用户邮箱
        private String jobName;//工作名称
        private String userSex;//用户性别
        private String creditScore;//信用分
        private String clientId;//客户表主键id'
        private String orderNo;// '订单编号'
        private String productId;//'产品表主键
        private String isValid;//是否有效
        private String mobile;//手机号
        private String secailSecurity;//是否有本地社保（0：无，1：有
        private String userName;//用户姓名
        private String nativePlaceAddr;//户籍地址
        private String marrySts;//婚姻状态 0-未婚 1-已婚
        private String totalAmount;//总金额 单位 元
        private String accountId;//账号id
        private String jobId;//工作id
        private String identCard;//身份证号
        private String towYearCred;//两年内信用
        private String createTime;//创建时间
        private String haveCar;//名下是否有车产
        private String district;//区县
        private String nativePlace;//籍贯
        private String badPercent;//预期违约率 单位百分比 1 代表 1%'
        private String modifyPersonId;//修改人
        private String accountBalance;//账户余额
        private String productName;//产品名称
        private String maxVal;//额度最大值
        private String minVal;//额度最小值
        private String payType;//还款方式

        private String assuranceType;//担保类型
        private String mortgageType;//'抵押类型
        private String imageLogoPath;//产品logo
        private String applyCondition;//申请条件
        private String loanPeriod;//放款周期 单位 工作日
        private String monthlyRate;//月利率
        private String productAmount;//金额 单位是万 5.2代表5.2万
        private String timeMaxVal;//期数范围 最大值 单位为月 1代表1个月
        private String timeMinVal;//期数范围 最小值 单位为月 1代表1个月


        public String getTimeMinVal() {
            return timeMinVal;
        }

        public void setTimeMinVal(String timeMinVal) {
            this.timeMinVal = timeMinVal;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getAssuranceType() {
            return assuranceType;
        }

        public void setAssuranceType(String assuranceType) {
            this.assuranceType = assuranceType;
        }

        public String getMortgageType() {
            return mortgageType;
        }

        public void setMortgageType(String mortgageType) {
            this.mortgageType = mortgageType;
        }

        public String getImageLogoPath() {
            return imageLogoPath;
        }

        public void setImageLogoPath(String imageLogoPath) {
            this.imageLogoPath = imageLogoPath;
        }

        public String getApplyCondition() {
            return applyCondition;
        }

        public void setApplyCondition(String applyCondition) {
            this.applyCondition = applyCondition;
        }

        public String getLoanPeriod() {
            return loanPeriod;
        }

        public void setLoanPeriod(String loanPeriod) {
            this.loanPeriod = loanPeriod;
        }

        public String getMonthlyRate() {
            return monthlyRate;
        }

        public void setMonthlyRate(String monthlyRate) {
            this.monthlyRate = monthlyRate;
        }

        public String getProductAmount() {
            return productAmount;
        }

        public void setProductAmount(String productAmount) {
            this.productAmount = productAmount;
        }

        public String getTimeMaxVal() {
            return timeMaxVal;
        }

        public void setTimeMaxVal(String timeMaxVal) {
            this.timeMaxVal = timeMaxVal;
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

        public String getPeriodsAmount() {
            return periodsAmount;
        }

        public void setPeriodsAmount(String periodsAmount) {
            this.periodsAmount = periodsAmount;
        }

        public String getCreditGrade() {
            return creditGrade;
        }

        public void setCreditGrade(String creditGrade) {
            this.creditGrade = creditGrade;
        }

        public String getCreatePersonId() {
            return createPersonId;
        }

        public void setCreatePersonId(String createPersonId) {
            this.createPersonId = createPersonId;
        }

        public String getTotalPeriods() {
            return totalPeriods;
        }

        public void setTotalPeriods(String totalPeriods) {
            this.totalPeriods = totalPeriods;
        }

        public String getUserAge() {
            return userAge;
        }

        public void setUserAge(String userAge) {
            this.userAge = userAge;
        }

        public String getDetailAddr() {
            return detailAddr;
        }

        public void setDetailAddr(String detailAddr) {
            this.detailAddr = detailAddr;
        }

        public String getCreditManagerId() {
            return creditManagerId;
        }

        public void setCreditManagerId(String creditManagerId) {
            this.creditManagerId = creditManagerId;
        }

        public String getProFund() {
            return proFund;
        }

        public void setProFund(String proFund) {
            this.proFund = proFund;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getLineOfCredit() {
            return lineOfCredit;
        }

        public void setLineOfCredit(String lineOfCredit) {
            this.lineOfCredit = lineOfCredit;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(String orderSts) {
            this.orderSts = orderSts;
        }

        public String getPracticalLoan() {
            return practicalLoan;
        }

        public void setPracticalLoan(String practicalLoan) {
            this.practicalLoan = practicalLoan;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPracticalLoanDate() {
            return practicalLoanDate;
        }

        public void setPracticalLoanDate(String practicalLoanDate) {
            this.practicalLoanDate = practicalLoanDate;
        }

        public String getCustomerEvaluate() {
            return customerEvaluate;
        }

        public void setCustomerEvaluate(String customerEvaluate) {
            this.customerEvaluate = customerEvaluate;
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

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(String creditScore) {
            this.creditScore = creditScore;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getIsValid() {
            return isValid;
        }

        public void setIsValid(String isValid) {
            this.isValid = isValid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSecailSecurity() {
            return secailSecurity;
        }

        public void setSecailSecurity(String secailSecurity) {
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

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
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

        public String getBadPercent() {
            return badPercent;
        }

        public void setBadPercent(String expectedFrequency) {
            this.badPercent = expectedFrequency;
        }

        public String getModifyPersonId() {
            return modifyPersonId;
        }

        public void setModifyPersonId(String modifyPersonId) {
            this.modifyPersonId = modifyPersonId;
        }

        public String getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(String accountBalance) {
            this.accountBalance = accountBalance;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.accountName);
            dest.writeString(this.headIcon);
            dest.writeString(this.haveHours);
            dest.writeString(this.city);
            dest.writeString(this.periodsAmount);
            dest.writeString(this.creditGrade);
            dest.writeString(this.createPersonId);
            dest.writeString(this.totalPeriods);
            dest.writeString(this.userAge);
            dest.writeString(this.detailAddr);
            dest.writeString(this.creditManagerId);
            dest.writeString(this.proFund);
            dest.writeString(this.modifyTime);
            dest.writeString(this.lineOfCredit);
            dest.writeString(this.province);
            dest.writeString(this.orderSts);
            dest.writeString(this.practicalLoan);
            dest.writeString(this.id);
            dest.writeString(this.practicalLoanDate);
            dest.writeString(this.customerEvaluate);
            dest.writeString(this.email);
            dest.writeString(this.jobName);
            dest.writeString(this.userSex);
            dest.writeString(this.creditScore);
            dest.writeString(this.clientId);
            dest.writeString(this.orderNo);
            dest.writeString(this.productId);
            dest.writeString(this.isValid);
            dest.writeString(this.mobile);
            dest.writeString(this.secailSecurity);
            dest.writeString(this.userName);
            dest.writeString(this.nativePlaceAddr);
            dest.writeString(this.marrySts);
            dest.writeString(this.totalAmount);
            dest.writeString(this.accountId);
            dest.writeString(this.jobId);
            dest.writeString(this.identCard);
            dest.writeString(this.towYearCred);
            dest.writeString(this.createTime);
            dest.writeString(this.haveCar);
            dest.writeString(this.district);
            dest.writeString(this.nativePlace);
            dest.writeString(this.badPercent);
            dest.writeString(this.modifyPersonId);
            dest.writeString(this.accountBalance);
            dest.writeString(this.productName);
            dest.writeString(this.maxVal);
            dest.writeString(this.minVal);
            dest.writeString(this.payType);
            dest.writeString(this.assuranceType);
            dest.writeString(this.mortgageType);
            dest.writeString(this.imageLogoPath);
            dest.writeString(this.applyCondition);
            dest.writeString(this.loanPeriod);
            dest.writeString(this.monthlyRate);
            dest.writeString(this.productAmount);
            dest.writeString(this.timeMaxVal);
            dest.writeString(this.timeMinVal);
        }

        public RowsEntity() {
        }

        protected RowsEntity(Parcel in) {
            this.accountName = in.readString();
            this.headIcon = in.readString();
            this.haveHours = in.readString();
            this.city = in.readString();
            this.periodsAmount = in.readString();
            this.creditGrade = in.readString();
            this.createPersonId = in.readString();
            this.totalPeriods = in.readString();
            this.userAge = in.readString();
            this.detailAddr = in.readString();
            this.creditManagerId = in.readString();
            this.proFund = in.readString();
            this.modifyTime = in.readString();
            this.lineOfCredit = in.readString();
            this.province = in.readString();
            this.orderSts = in.readString();
            this.practicalLoan = in.readString();
            this.id = in.readString();
            this.practicalLoanDate = in.readString();
            this.customerEvaluate = in.readString();
            this.email = in.readString();
            this.jobName = in.readString();
            this.userSex = in.readString();
            this.creditScore = in.readString();
            this.clientId = in.readString();
            this.orderNo = in.readString();
            this.productId = in.readString();
            this.isValid = in.readString();
            this.mobile = in.readString();
            this.secailSecurity = in.readString();
            this.userName = in.readString();
            this.nativePlaceAddr = in.readString();
            this.marrySts = in.readString();
            this.totalAmount = in.readString();
            this.accountId = in.readString();
            this.jobId = in.readString();
            this.identCard = in.readString();
            this.towYearCred = in.readString();
            this.createTime = in.readString();
            this.haveCar = in.readString();
            this.district = in.readString();
            this.nativePlace = in.readString();
            this.badPercent = in.readString();
            this.modifyPersonId = in.readString();
            this.accountBalance = in.readString();
            this.productName = in.readString();
            this.maxVal = in.readString();
            this.minVal = in.readString();
            this.payType = in.readString();
            this.assuranceType = in.readString();
            this.mortgageType = in.readString();
            this.imageLogoPath = in.readString();
            this.applyCondition = in.readString();
            this.loanPeriod = in.readString();
            this.monthlyRate = in.readString();
            this.productAmount = in.readString();
            this.timeMaxVal = in.readString();
            this.timeMinVal = in.readString();
        }

        public static final Creator<RowsEntity> CREATOR = new Creator<RowsEntity>() {
            public RowsEntity createFromParcel(Parcel source) {
                return new RowsEntity(source);
            }

            public RowsEntity[] newArray(int size) {
                return new RowsEntity[size];
            }
        };
    }
}
