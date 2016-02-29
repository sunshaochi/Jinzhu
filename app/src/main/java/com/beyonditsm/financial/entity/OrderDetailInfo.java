package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 15/12/13.
 */
public class OrderDetailInfo implements Parcelable {


    /**
     * status : 200
     * data : {"jobIdentityName":"上班族","productName":"金蛛贷-招","proFund":0,"maxVal":300000,"payType":"2c908c6e50d077f80150d08048b3000a","minVal":10000,"creditStatusName":"","orderSts":"WAIT_BACKGROUND_APPROVAL","assuranceType":"2c909d245115183b01511525d61d0006","id":"2c908c7e519606440151960db916000a","practicalLoanDate":1449921460000,"customerEvaluate":null,"jobName":"个体户","userSex":1,"orderNo":"dk2639240983987200","productId":"2c908c7e518ed1e201518ed8072c0000","mortgageType":"2c909d245115183b01511523c1f90003","secailSecurity":0,"productCharaName":"","imageLogoPath":"","applyCondition":"2345","totalAmount":50000,"productAmount":null,"identCard":"411323199006120054","mortgageTypeName":"信用卡","detailDescribe":"3456","nativePlace":"河南","expectedFrequency":null,"propertyTypeName":"无房产","haveHours":"办公楼","monthlyRate":1.5,"periodsAmount":8836.13,"timeMaxVal":12,"publishSts":"PUBLISHED","orgId":"2c908c7e51863c6601518655294d000b","totalPeriods":6,"creditManagerId":null,"marrStatus":"1","propertyType":"2c908c6e50d077f80150d082348a000f","applyMaterial":"4567","practicalLoan":null,"productNo":"789","costDescribe":"1234","clientId":"2c908c7e51953cf301519547bed30003","jobIdentity":"2c908c6e50d077f80150d08088dd000b","carStatus":"","creditStatus":"","succeedApplyCount":null,"userName":"ddd","nativePlaceAddr":"北京市北京市东城区","token":"ftyQvlOAwCRC1s+qho5QlHMCHXCu+5d/vcc2bMG8XNWVyv0Q0coctckRhyyrb+VAqd6/eVyGh7DAdwVu7jV1VxK+QWzW/syvvNw3oicfkFziPTWMHnkC+N/s7JSzbKGVehkieIv/IiE=","payTypeName":"分期还款","timeMinVal":3,"currAddress":"","productChara":"","createTime":1449921460000,"taskManageId":"","haveCar":"名下有车","reputationLevel":null,"detailAddress":"北京市北京市东城区","carStatusName":"","loanPeriod":3,"remark":"ss","tasks":[{"taskId":"1ww2222ww","taskName":"教育程度","taskDesc":"上传学历证明","taskStatus":"-1"}]}
     * message : 成功！
     */

    private int status;
    /**
     * jobIdentityName : 上班族
     * productName : 金蛛贷-招
     * proFund : 0
     * maxVal : 300000.0
     * payType : 2c908c6e50d077f80150d08048b3000a
     * minVal : 10000.0
     * creditStatusName :
     * orderSts : WAIT_BACKGROUND_APPROVAL
     * assuranceType : 2c909d245115183b01511525d61d0006
     * id : 2c908c7e519606440151960db916000a
     * practicalLoanDate : 1449921460000
     * customerEvaluate : null
     * jobName : 个体户
     * userSex : 1
     * orderNo : dk2639240983987200
     * productId : 2c908c7e518ed1e201518ed8072c0000
     * mortgageType : 2c909d245115183b01511523c1f90003
     * secailSecurity : 0
     * productCharaName :
     * imageLogoPath :
     * applyCondition : 2345
     * totalAmount : 50000.0
     * productAmount : null
     * identCard : 411323199006120054
     * mortgageTypeName : 信用卡
     * detailDescribe : 3456
     * nativePlace : 河南
     * expectedFrequency : null
     * propertyTypeName : 无房产
     * haveHours : 办公楼
     * monthlyRate : 1.5
     * periodsAmount : 8836.13
     * timeMaxVal : 12
     * publishSts : PUBLISHED
     * orgId : 2c908c7e51863c6601518655294d000b
     * totalPeriods : 6
     * creditManagerId : null
     * marrStatus : 1
     * propertyType : 2c908c6e50d077f80150d082348a000f
     * applyMaterial : 4567
     * practicalLoan : null
     * productNo : 789
     * costDescribe : 1234
     * clientId : 2c908c7e51953cf301519547bed30003
     * jobIdentity : 2c908c6e50d077f80150d08088dd000b
     * carStatus :
     * creditStatus :
     * succeedApplyCount : null
     * userName : ddd
     * nativePlaceAddr : 北京市北京市东城区
     * token : ftyQvlOAwCRC1s+qho5QlHMCHXCu+5d/vcc2bMG8XNWVyv0Q0coctckRhyyrb+VAqd6/eVyGh7DAdwVu7jV1VxK+QWzW/syvvNw3oicfkFziPTWMHnkC+N/s7JSzbKGVehkieIv/IiE=
     * payTypeName : 分期还款
     * timeMinVal : 3
     * currAddress :
     * productChara :
     * createTime : 1449921460000
     * taskManageId :
     * haveCar : 名下有车
     * reputationLevel : null
     * detailAddress : 北京市北京市东城区
     * carStatusName :
     * loanPeriod : 3
     * remark : ss
     * tasks : [{"taskId":"1ww2222ww","taskName":"教育程度","taskDesc":"上传学历证明","taskStatus":"-1"}]
     */

    private DataEntity data;
    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity implements Parcelable {
        private String jobIdentityName;
        private String productName;
        private Integer proFund;
        private Double maxVal;
        private String payType;
        private Double minVal;
        private String creditStatusName;
        private String orderSts;
        private String assuranceType;
        private String id;
        private String accountId;
        private long practicalLoanDate;
        private String customerEvaluate;
        private String jobName;
        private Integer userSex;
        private String orderNo;
        private String productId;
        private String mortgageType;
        private Integer secailSecurity;
        private String productCharaName;
        private String imageLogoPath;
        private String applyCondition;
        private Double totalAmount;
        private String productAmount;
        private String identCard;
        private String mortgageTypeName;
        private String detailDescribe;
        private String nativePlace;
        private String expectedFrequency;
        private String propertyTypeName;
        private String haveHours;
        private Double monthlyRate;
        private Double periodsAmount;
        private Integer timeMaxVal;
        private String publishSts;
        private String orgId;
        private Integer totalPeriods;
        private String creditManagerId;
        private String marrStatus;
        private String propertyType;
        private String applyMaterial;
        private String practicalLoan;//实际放款金额
        private String bankPracticalPeriods;//实际放款期限
        private String productNo;
        private String costDescribe;
        private String clientId;
        private String jobIdentity;
        private String carStatus;
        private String creditStatus;
        private String succeedApplyCount;
        private String userName;
        private String nativePlaceAddr;
        private String token;
        private String payTypeName;
        private Integer timeMinVal;
        private String currAddress;
        private String productChara;
        private long createTime;
        private String taskManageId;
        private String haveCar;
        private String reputationLevel;
        private String detailAddress;
        private String carStatusName;
        private Integer loanPeriod;
        private String remark;
        private String monthlyRateMin;
        private String monthlyRateMax;
        private String creditAccountId;//信贷经理容云id
        private String disposableRateMin;
        private String disposableRateMax;
        private String realMonthlyInterestRate;//实际月利率
        private String realMonthlyRate;//实际月费率
        private String realOneTimeRate;//实际一次性费率
        private String realManageRate;//实际管理费率
        private String province;
        private String city;
        private String district;
        private String creditAmount;//授信额
        private String evaluateLevel;//信贷经理评价等级

        public String getCreditAmount() {
            return creditAmount;
        }

        public void setCreditAmount(String creditAmount) {
            this.creditAmount = creditAmount;
        }

        public String getEvaluateLevel() {
            return evaluateLevel;
        }

        public void setEvaluateLevel(String evaluateLevel) {
            this.evaluateLevel = evaluateLevel;
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

        public String getDisposableRateMin() {
            return disposableRateMin;
        }

        public String getRealMonthlyInterestRate() {
            return realMonthlyInterestRate;
        }

        public void setRealMonthlyInterestRate(String realMonthlyInterestRate) {
            this.realMonthlyInterestRate = realMonthlyInterestRate;
        }

        public String getRealMonthlyRate() {
            return realMonthlyRate;
        }

        public void setRealMonthlyRate(String realMonthlyRate) {
            this.realMonthlyRate = realMonthlyRate;
        }

        public String getRealOneTimeRate() {
            return realOneTimeRate;
        }

        public void setRealOneTimeRate(String realOneTimeRate) {
            this.realOneTimeRate = realOneTimeRate;
        }

        public String getRealManageRate() {
            return realManageRate;
        }

        public void setRealManageRate(String realManageRate) {
            this.realManageRate = realManageRate;
        }

        public void setDisposableRateMin(String disposableRateMin) {
            this.disposableRateMin = disposableRateMin;
        }

        public String getDisposableRateMax() {
            return disposableRateMax;
        }

        public void setDisposableRateMax(String disposableRateMax) {
            this.disposableRateMax = disposableRateMax;
        }

        public String getBankPracticalPeriods() {
            return bankPracticalPeriods;
        }

        public void setBankPracticalPeriods(String bankPracticalPeriods) {
            this.bankPracticalPeriods = bankPracticalPeriods;
        }

        public String getCreditAccountId() {
            return creditAccountId;
        }

        public void setCreditAccountId(String creditAccountId) {
            this.creditAccountId = creditAccountId;
        }

        public void setPeriodsAmount(Double periodsAmount) {
            this.periodsAmount = periodsAmount;
        }

        public String getMonthlyRateMin() {
            return monthlyRateMin;
        }

        public void setMonthlyRateMin(String monthlyRateMin) {
            this.monthlyRateMin = monthlyRateMin;
        }

        public String getMonthlyRateMax() {
            return monthlyRateMax;
        }

        public void setMonthlyRateMax(String monthlyRateMax) {
            this.monthlyRateMax = monthlyRateMax;
        }

        /**

         * taskId : 1ww2222ww
         * taskName : 教育程度
         * taskDesc : 上传学历证明
         * taskStatus : -1
         */

        private List<TasksEntity> tasks;

        public void setJobIdentityName(String jobIdentityName) {
            this.jobIdentityName = jobIdentityName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProFund(Integer proFund) {
            this.proFund = proFund;
        }

        public void setMaxVal(Double maxVal) {
            this.maxVal = maxVal;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public void setMinVal(Double minVal) {
            this.minVal = minVal;
        }

        public void setCreditStatusName(String creditStatusName) {
            this.creditStatusName = creditStatusName;
        }


        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setOrderSts(String orderSts) {
            this.orderSts = orderSts;
        }

        public void setAssuranceType(String assuranceType) {
            this.assuranceType = assuranceType;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPracticalLoanDate(long practicalLoanDate) {
            this.practicalLoanDate = practicalLoanDate;
        }

        public void setCustomerEvaluate(String customerEvaluate) {
            this.customerEvaluate = customerEvaluate;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public void setUserSex(Integer userSex) {
            this.userSex = userSex;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setMortgageType(String mortgageType) {
            this.mortgageType = mortgageType;
        }

        public void setSecailSecurity(Integer secailSecurity) {
            this.secailSecurity = secailSecurity;
        }

        public void setProductCharaName(String productCharaName) {
            this.productCharaName = productCharaName;
        }

        public void setImageLogoPath(String imageLogoPath) {
            this.imageLogoPath = imageLogoPath;
        }

        public void setApplyCondition(String applyCondition) {
            this.applyCondition = applyCondition;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setProductAmount(String productAmount) {
            this.productAmount = productAmount;
        }

        public void setIdentCard(String identCard) {
            this.identCard = identCard;
        }

        public void setMortgageTypeName(String mortgageTypeName) {
            this.mortgageTypeName = mortgageTypeName;
        }

        public void setDetailDescribe(String detailDescribe) {
            this.detailDescribe = detailDescribe;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace;
        }

        public void setExpectedFrequency(String expectedFrequency) {
            this.expectedFrequency = expectedFrequency;
        }

        public void setPropertyTypeName(String propertyTypeName) {
            this.propertyTypeName = propertyTypeName;
        }

        public void setHaveHours(String haveHours) {
            this.haveHours = haveHours;
        }

        public void setMonthlyRate(Double monthlyRate) {
            this.monthlyRate = monthlyRate;
        }

        public void setPeriodsAmount(double periodsAmount) {
            this.periodsAmount = periodsAmount;
        }

        public void setTimeMaxVal(Integer timeMaxVal) {
            this.timeMaxVal = timeMaxVal;
        }

        public void setPublishSts(String publishSts) {
            this.publishSts = publishSts;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public void setTotalPeriods(Integer totalPeriods) {
            this.totalPeriods = totalPeriods;
        }

        public void setCreditManagerId(String creditManagerId) {
            this.creditManagerId = creditManagerId;
        }

        public void setMarrStatus(String marrStatus) {
            this.marrStatus = marrStatus;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public void setApplyMaterial(String applyMaterial) {
            this.applyMaterial = applyMaterial;
        }

        public void setPracticalLoan(String practicalLoan) {
            this.practicalLoan = practicalLoan;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public void setCostDescribe(String costDescribe) {
            this.costDescribe = costDescribe;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public void setJobIdentity(String jobIdentity) {
            this.jobIdentity = jobIdentity;
        }

        public void setCarStatus(String carStatus) {
            this.carStatus = carStatus;
        }

        public void setCreditStatus(String creditStatus) {
            this.creditStatus = creditStatus;
        }

        public void setSucceedApplyCount(String succeedApplyCount) {
            this.succeedApplyCount = succeedApplyCount;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setNativePlaceAddr(String nativePlaceAddr) {
            this.nativePlaceAddr = nativePlaceAddr;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setPayTypeName(String payTypeName) {
            this.payTypeName = payTypeName;
        }

        public void setTimeMinVal(Integer timeMinVal) {
            this.timeMinVal = timeMinVal;
        }

        public void setCurrAddress(String currAddress) {
            this.currAddress = currAddress;
        }

        public void setProductChara(String productChara) {
            this.productChara = productChara;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setTaskManageId(String taskManageId) {
            this.taskManageId = taskManageId;
        }

        public void setHaveCar(String haveCar) {
            this.haveCar = haveCar;
        }

        public void setReputationLevel(String reputationLevel) {
            this.reputationLevel = reputationLevel;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public void setCarStatusName(String carStatusName) {
            this.carStatusName = carStatusName;
        }

        public void setLoanPeriod(Integer loanPeriod) {
            this.loanPeriod = loanPeriod;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setTasks(List<TasksEntity> tasks) {
            this.tasks = tasks;
        }

        public String getJobIdentityName() {
            return jobIdentityName;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getProFund() {
            return proFund;
        }

        public Double getMaxVal() {
            return maxVal;
        }

        public String getPayType() {
            return payType;
        }

        public Double getMinVal() {
            return minVal;
        }

        public String getCreditStatusName() {
            return creditStatusName;
        }

        public String getOrderSts() {
            return orderSts;
        }

        public String getAssuranceType() {
            return assuranceType;
        }

        public String getId() {
            return id;
        }

        public long getPracticalLoanDate() {
            return practicalLoanDate;
        }

        public String getCustomerEvaluate() {
            return customerEvaluate;
        }

        public String getJobName() {
            return jobName;
        }

        public Integer getUserSex() {
            return userSex;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getProductId() {
            return productId;
        }

        public String getMortgageType() {
            return mortgageType;
        }

        public Integer getSecailSecurity() {
            return secailSecurity;
        }

        public String getProductCharaName() {
            return productCharaName;
        }

        public String getImageLogoPath() {
            return imageLogoPath;
        }

        public String getApplyCondition() {
            return applyCondition;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public String getProductAmount() {
            return productAmount;
        }

        public String getIdentCard() {
            return identCard;
        }

        public String getMortgageTypeName() {
            return mortgageTypeName;
        }

        public String getDetailDescribe() {
            return detailDescribe;
        }

        public String getNativePlace() {
            return nativePlace;
        }

        public String getExpectedFrequency() {
            return expectedFrequency;
        }

        public String getPropertyTypeName() {
            return propertyTypeName;
        }

        public String getHaveHours() {
            return haveHours;
        }

        public Double getMonthlyRate() {
            return monthlyRate;
        }

        public Double getPeriodsAmount() {
            return periodsAmount;
        }

        public Integer getTimeMaxVal() {
            return timeMaxVal;
        }

        public String getPublishSts() {
            return publishSts;
        }

        public String getOrgId() {
            return orgId;
        }

        public int getTotalPeriods() {
            return totalPeriods;
        }

        public String getCreditManagerId() {
            return creditManagerId;
        }

        public String getMarrStatus() {
            return marrStatus;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public String getApplyMaterial() {
            return applyMaterial;
        }

        public String getPracticalLoan() {
            return practicalLoan;
        }

        public String getProductNo() {
            return productNo;
        }

        public String getCostDescribe() {
            return costDescribe;
        }

        public String getClientId() {
            return clientId;
        }

        public String getJobIdentity() {
            return jobIdentity;
        }

        public String getCarStatus() {
            return carStatus;
        }

        public String getCreditStatus() {
            return creditStatus;
        }

        public String getSucceedApplyCount() {
            return succeedApplyCount;
        }

        public String getUserName() {
            return userName;
        }

        public String getNativePlaceAddr() {
            return nativePlaceAddr;
        }

        public String getToken() {
            return token;
        }

        public String getPayTypeName() {
            return payTypeName;
        }

        public Integer getTimeMinVal() {
            return timeMinVal;
        }

        public String getCurrAddress() {
            return currAddress;
        }

        public String getProductChara() {
            return productChara;
        }

        public long getCreateTime() {
            return createTime;
        }

        public String getTaskManageId() {
            return taskManageId;
        }

        public String getHaveCar() {
            return haveCar;
        }

        public String getReputationLevel() {
            return reputationLevel;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public String getCarStatusName() {
            return carStatusName;
        }

        public Integer getLoanPeriod() {
            return loanPeriod;
        }

        public String getRemark() {
            return remark;
        }

        public List<TasksEntity> getTasks() {
            return tasks;
        }

        public static class TasksEntity {
            private String taskId;
            private String taskName;
            private String taskDesc;
            private String taskStatus;

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public void setTaskName(String taskName) {
                this.taskName = taskName;
            }

            public void setTaskDesc(String taskDesc) {
                this.taskDesc = taskDesc;
            }

            public void setTaskStatus(String taskStatus) {
                this.taskStatus = taskStatus;
            }

            public String getTaskId() {
                return taskId;
            }

            public String getTaskName() {
                return taskName;
            }

            public String getTaskDesc() {
                return taskDesc;
            }

            public String getTaskStatus() {
                return taskStatus;
            }
        }

        public DataEntity() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.jobIdentityName);
            dest.writeString(this.productName);
            dest.writeValue(this.proFund);
            dest.writeValue(this.maxVal);
            dest.writeString(this.payType);
            dest.writeValue(this.minVal);
            dest.writeString(this.creditStatusName);
            dest.writeString(this.orderSts);
            dest.writeString(this.assuranceType);
            dest.writeString(this.id);
            dest.writeString(this.accountId);
            dest.writeLong(this.practicalLoanDate);
            dest.writeString(this.customerEvaluate);
            dest.writeString(this.jobName);
            dest.writeValue(this.userSex);
            dest.writeString(this.orderNo);
            dest.writeString(this.productId);
            dest.writeString(this.mortgageType);
            dest.writeValue(this.secailSecurity);
            dest.writeString(this.productCharaName);
            dest.writeString(this.imageLogoPath);
            dest.writeString(this.applyCondition);
            dest.writeValue(this.totalAmount);
            dest.writeString(this.productAmount);
            dest.writeString(this.identCard);
            dest.writeString(this.mortgageTypeName);
            dest.writeString(this.detailDescribe);
            dest.writeString(this.nativePlace);
            dest.writeString(this.expectedFrequency);
            dest.writeString(this.propertyTypeName);
            dest.writeString(this.haveHours);
            dest.writeValue(this.monthlyRate);
            dest.writeValue(this.periodsAmount);
            dest.writeValue(this.timeMaxVal);
            dest.writeString(this.publishSts);
            dest.writeString(this.orgId);
            dest.writeValue(this.totalPeriods);
            dest.writeString(this.creditManagerId);
            dest.writeString(this.marrStatus);
            dest.writeString(this.propertyType);
            dest.writeString(this.applyMaterial);
            dest.writeString(this.practicalLoan);
            dest.writeString(this.productNo);
            dest.writeString(this.costDescribe);
            dest.writeString(this.clientId);
            dest.writeString(this.jobIdentity);
            dest.writeString(this.carStatus);
            dest.writeString(this.creditStatus);
            dest.writeString(this.succeedApplyCount);
            dest.writeString(this.userName);
            dest.writeString(this.nativePlaceAddr);
            dest.writeString(this.token);
            dest.writeString(this.payTypeName);
            dest.writeValue(this.timeMinVal);
            dest.writeString(this.currAddress);
            dest.writeString(this.productChara);
            dest.writeLong(this.createTime);
            dest.writeString(this.taskManageId);
            dest.writeString(this.haveCar);
            dest.writeString(this.reputationLevel);
            dest.writeString(this.detailAddress);
            dest.writeString(this.carStatusName);
            dest.writeValue(this.loanPeriod);
            dest.writeString(this.remark);
            dest.writeString(this.monthlyRateMin);
            dest.writeString(this.monthlyRateMax);
            dest.writeString(this.creditAccountId);
            dest.writeString(this.disposableRateMin);
            dest.writeString(this.disposableRateMax);
            dest.writeString(this.realMonthlyInterestRate);
            dest.writeString(this.realMonthlyRate);
            dest.writeString(this.realOneTimeRate);
            dest.writeString(this.realManageRate);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.district);
            dest.writeList(this.tasks);
            dest.writeString(this.bankPracticalPeriods);
        }

        protected DataEntity(Parcel in) {
            this.jobIdentityName = in.readString();
            this.productName = in.readString();
            this.proFund = (Integer) in.readValue(Integer.class.getClassLoader());
            this.maxVal = (Double) in.readValue(Double.class.getClassLoader());
            this.payType = in.readString();
            this.minVal = (Double) in.readValue(Double.class.getClassLoader());
            this.creditStatusName = in.readString();
            this.orderSts = in.readString();
            this.assuranceType = in.readString();
            this.id = in.readString();
            this.accountId = in.readString();
            this.practicalLoanDate = in.readLong();
            this.customerEvaluate = in.readString();
            this.jobName = in.readString();
            this.userSex = (Integer) in.readValue(Integer.class.getClassLoader());
            this.orderNo = in.readString();
            this.productId = in.readString();
            this.mortgageType = in.readString();
            this.secailSecurity = (Integer) in.readValue(Integer.class.getClassLoader());
            this.productCharaName = in.readString();
            this.imageLogoPath = in.readString();
            this.applyCondition = in.readString();
            this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.productAmount = in.readString();
            this.identCard = in.readString();
            this.mortgageTypeName = in.readString();
            this.detailDescribe = in.readString();
            this.nativePlace = in.readString();
            this.expectedFrequency = in.readString();
            this.propertyTypeName = in.readString();
            this.haveHours = in.readString();
            this.monthlyRate = (Double) in.readValue(Double.class.getClassLoader());
            this.periodsAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.timeMaxVal = (Integer) in.readValue(Integer.class.getClassLoader());
            this.publishSts = in.readString();
            this.orgId = in.readString();
            this.totalPeriods = (Integer) in.readValue(Integer.class.getClassLoader());
            this.creditManagerId = in.readString();
            this.marrStatus = in.readString();
            this.propertyType = in.readString();
            this.applyMaterial = in.readString();
            this.practicalLoan = in.readString();
            this.productNo = in.readString();
            this.costDescribe = in.readString();
            this.clientId = in.readString();
            this.jobIdentity = in.readString();
            this.carStatus = in.readString();
            this.creditStatus = in.readString();
            this.succeedApplyCount = in.readString();
            this.userName = in.readString();
            this.nativePlaceAddr = in.readString();
            this.token = in.readString();
            this.payTypeName = in.readString();
            this.timeMinVal = (Integer) in.readValue(Integer.class.getClassLoader());
            this.currAddress = in.readString();
            this.productChara = in.readString();
            this.createTime = in.readLong();
            this.taskManageId = in.readString();
            this.haveCar = in.readString();
            this.reputationLevel = in.readString();
            this.detailAddress = in.readString();
            this.carStatusName = in.readString();
            this.loanPeriod = (Integer) in.readValue(Integer.class.getClassLoader());
            this.remark = in.readString();
            this.monthlyRateMin = in.readString();
            this.monthlyRateMax = in.readString();
            this.creditAccountId = in.readString();
            this.disposableRateMin = in.readString();
            this.disposableRateMax = in.readString();
            this.realMonthlyInterestRate = in.readString();
            this.realMonthlyRate = in.readString();
            this.realOneTimeRate = in.readString();
            this.realManageRate = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.district = in.readString();
            this.bankPracticalPeriods = in.readString();
            this.tasks = new ArrayList<TasksEntity>();
            in.readList(this.tasks, List.class.getClassLoader());
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public OrderDetailInfo() {
    }

    protected OrderDetailInfo(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<OrderDetailInfo> CREATOR = new Parcelable.Creator<OrderDetailInfo>() {
        public OrderDetailInfo createFromParcel(Parcel source) {
            return new OrderDetailInfo(source);
        }

        public OrderDetailInfo[] newArray(int size) {
            return new OrderDetailInfo[size];
        }
    };
}
