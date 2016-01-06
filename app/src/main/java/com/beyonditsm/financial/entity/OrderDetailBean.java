package com.beyonditsm.financial.entity;

/**
 * Created by Yang on 2015/12/3 0003.
 */
public class OrderDetailBean {

    /**
     * status : 200
     * data : {"monthlyRate":100,"periodsAmount":500,"timeMaxVal":10,"publishSts":"已发布","orgId":"2c908c7e51418f64015141a3fd890005","productName":"12345","totalPeriods":20,"creditManagerId":"4028b88150b222f90150b22cfac20005","maxVal":100000,"payType":"2c908c6e50d077f80150d08048b3000a","minVal":100000,"orderSts":"CREDIT_MANAGER_APPROVAL","propertyType":"","applyMaterial":"2333","assuranceType":"2c909d245115183b01511526bccf0008","practicalLoan":null,"id":"2c908c6a516260050151627db65e0003","practicalLoanDate":1449065447000,"productNo":null,"customerEvaluate":"不错","costDescribe":"100","clientId":"","orderNo":"dk2625067580294144","productId":"2c908c7e51418f64015141c8978e000d","jobIdentity":"","carStatus":"","mortgageType":"2c909d245115183b01511522e08f0001","creditStatus":"","imageLogoPath":"","succeedApplyCount":0,"applyCondition":"1","totalAmount":10000,"productAmount":100000,"timeMinVal":10,"productChara":"","createTime":1449056385000,"detailDescribe":"2333","reputationLevel":0,"expectedFrequency":2,"loanPeriod":10}
     * message : 成功！
     */

    private int status;
    /**
     * monthlyRate : 100.0
     * periodsAmount : 500.0
     * timeMaxVal : 10
     * publishSts : 已发布
     * orgId : 2c908c7e51418f64015141a3fd890005
     * productName : 12345
     * totalPeriods : 20
     * creditManagerId : 4028b88150b222f90150b22cfac20005
     * maxVal : 100000.0
     * payType : 2c908c6e50d077f80150d08048b3000a
     * minVal : 100000.0
     * orderSts : CREDIT_MANAGER_APPROVAL
     * propertyType :
     * applyMaterial : 2333
     * assuranceType : 2c909d245115183b01511526bccf0008
     * practicalLoan : null
     * id : 2c908c6a516260050151627db65e0003
     * practicalLoanDate : 1449065447000
     * productNo : null
     * customerEvaluate : 不错
     * costDescribe : 100
     * clientId :
     * orderNo : dk2625067580294144
     * productId : 2c908c7e51418f64015141c8978e000d
     * jobIdentity :
     * carStatus :
     * mortgageType : 2c909d245115183b01511522e08f0001
     * creditStatus :
     * imageLogoPath :
     * succeedApplyCount : 0
     * applyCondition : 1
     * totalAmount : 10000.0
     * productAmount : 100000.0
     * timeMinVal : 10
     * productChara :
     * createTime : 1449056385000
     * detailDescribe : 2333
     * reputationLevel : 0.0
     * expectedFrequency : 2
     * loanPeriod : 10
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

    public static class DataEntity {
        private String monthlyRate;
        private String periodsAmount;
        private String timeMaxVal;
        private String publishSts;
        private String orgId;
        private String productName;
        private String totalPeriods;
        private String creditManagerId;
        private String maxVal;
        private String payType;
        private String minVal;
        private String orderSts;
        private String propertyType;
        private String applyMaterial;
        private String assuranceType;
        private String practicalLoan;
        private String id;
        private String practicalLoanDate;
        private String productNo;
        private String customerEvaluate;
        private String costDescribe;
        private String clientId;
        private String orderNo;
        private String productId;
        private String jobIdentity;
        private String carStatus;
        private String mortgageType;
        private String creditStatus;
        private String imageLogoPath;
        private String succeedApplyCount;
        private String applyCondition;
        private String totalAmount;
        private String productAmount;
        private String timeMinVal;
        private String productChara;
        private String createTime;
        private String detailDescribe;
        private String reputationLevel;
        private String expectedFrequency;
        private String loanPeriod;

        public String getPayTypeName() {
            return payTypeName;
        }

        public void setPayTypeName(String payTypeName) {
            this.payTypeName = payTypeName;
        }

        private String payTypeName;

        public String getMonthlyRate() {
            return monthlyRate;
        }

        public void setMonthlyRate(String monthlyRate) {
            this.monthlyRate = monthlyRate;
        }

        public String getPeriodsAmount() {
            return periodsAmount;
        }

        public void setPeriodsAmount(String periodsAmount) {
            this.periodsAmount = periodsAmount;
        }

        public String getTimeMaxVal() {
            return timeMaxVal;
        }

        public void setTimeMaxVal(String timeMaxVal) {
            this.timeMaxVal = timeMaxVal;
        }

        public String getPublishSts() {
            return publishSts;
        }

        public void setPublishSts(String publishSts) {
            this.publishSts = publishSts;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getTotalPeriods() {
            return totalPeriods;
        }

        public void setTotalPeriods(String totalPeriods) {
            this.totalPeriods = totalPeriods;
        }

        public String getCreditManagerId() {
            return creditManagerId;
        }

        public void setCreditManagerId(String creditManagerId) {
            this.creditManagerId = creditManagerId;
        }

        public String getMaxVal() {
            return maxVal;
        }

        public void setMaxVal(String maxVal) {
            this.maxVal = maxVal;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getMinVal() {
            return minVal;
        }

        public void setMinVal(String minVal) {
            this.minVal = minVal;
        }

        public String getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(String orderSts) {
            this.orderSts = orderSts;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getApplyMaterial() {
            return applyMaterial;
        }

        public void setApplyMaterial(String applyMaterial) {
            this.applyMaterial = applyMaterial;
        }

        public String getAssuranceType() {
            return assuranceType;
        }

        public void setAssuranceType(String assuranceType) {
            this.assuranceType = assuranceType;
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

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getCustomerEvaluate() {
            return customerEvaluate;
        }

        public void setCustomerEvaluate(String customerEvaluate) {
            this.customerEvaluate = customerEvaluate;
        }

        public String getCostDescribe() {
            return costDescribe;
        }

        public void setCostDescribe(String costDescribe) {
            this.costDescribe = costDescribe;
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

        public String getJobIdentity() {
            return jobIdentity;
        }

        public void setJobIdentity(String jobIdentity) {
            this.jobIdentity = jobIdentity;
        }

        public String getCarStatus() {
            return carStatus;
        }

        public void setCarStatus(String carStatus) {
            this.carStatus = carStatus;
        }

        public String getMortgageType() {
            return mortgageType;
        }

        public void setMortgageType(String mortgageType) {
            this.mortgageType = mortgageType;
        }

        public String getCreditStatus() {
            return creditStatus;
        }

        public void setCreditStatus(String creditStatus) {
            this.creditStatus = creditStatus;
        }

        public String getImageLogoPath() {
            return imageLogoPath;
        }

        public void setImageLogoPath(String imageLogoPath) {
            this.imageLogoPath = imageLogoPath;
        }

        public String getSucceedApplyCount() {
            return succeedApplyCount;
        }

        public void setSucceedApplyCount(String succeedApplyCount) {
            this.succeedApplyCount = succeedApplyCount;
        }

        public String getApplyCondition() {
            return applyCondition;
        }

        public void setApplyCondition(String applyCondition) {
            this.applyCondition = applyCondition;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getProductAmount() {
            return productAmount;
        }

        public void setProductAmount(String productAmount) {
            this.productAmount = productAmount;
        }

        public String getTimeMinVal() {
            return timeMinVal;
        }

        public void setTimeMinVal(String timeMinVal) {
            this.timeMinVal = timeMinVal;
        }

        public String getProductChara() {
            return productChara;
        }

        public void setProductChara(String productChara) {
            this.productChara = productChara;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDetailDescribe() {
            return detailDescribe;
        }

        public void setDetailDescribe(String detailDescribe) {
            this.detailDescribe = detailDescribe;
        }

        public String getReputationLevel() {
            return reputationLevel;
        }

        public void setReputationLevel(String reputationLevel) {
            this.reputationLevel = reputationLevel;
        }

        public String getExpectedFrequency() {
            return expectedFrequency;
        }

        public void setExpectedFrequency(String expectedFrequency) {
            this.expectedFrequency = expectedFrequency;
        }

        public String getLoanPeriod() {
            return loanPeriod;
        }

        public void setLoanPeriod(String loanPeriod) {
            this.loanPeriod = loanPeriod;
        }
    }
}
