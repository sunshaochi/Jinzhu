package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class OrderBean3 implements Parcelable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户ID
     */
    private String uid;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 订单类型，1-线上订单，2-CC订单，3-地推订单，4-急贷通
     */
    private String orderType;

    /**
     * 还款方式
     */
    private String paymentType;

    /**
     * 申请金额，单位(元)
     */
    private String applyAmount;

    /**
     * 申请期数，单位（月）
     */
    private Integer applyPeriods;

    /**
     * 计算月供的月利率
     */
    private String monthlyRate;

    /**
     * 单期还款金额，单位（元）
     */
    private String periodsAmount;

    /**
     * 利息总额
     */
    private String totalInterest;

    /**
     * 可以抵扣利息最大额
     */
    private String deductibleInterest;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 放款金额，单位元
     */
    private String grantAmount;

    /**
     * 放款期限，单位月
     */
    private Integer grantPeriods;

    /**
     * 是否可取消 0可取消 1 不可取消
     */
    private Integer enableCancel;
    /**
     * 放款时间
     */
    private String grantTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 工作流流程ID，关联工作流
     */
    private String workId;

    /**
     * 批次-电销对应orderId
     */
    private String batchId;

    /**
     * 下一个节点的执行人类型:1人员；2岗位
     */
    private Integer nextNodeExecutorType;

    /**
     * 下一个节点的执行人
     */
    private String nextNodeExecutor;
    /**
     * 电销人员名称
     */
    private String currentOperator;

    /**
     * 电销人员ID
     */
    private String currentOperatorId;

    /**
     * 申贷步骤1,2,3,4
     * add by denny
     */
    private String stepCode;

    /**
     * 每周最大还款额度
     */
    private String maxRepaymentWeekly;

    /**
     *合同编号
     */
    private String  contCode;

    /**
     * 实际最大利率
     */
    private String actMaxRate;

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


    public Integer getEnableCancel() {
        return enableCancel;
    }

    public void setEnableCancel(Integer enableCancel) {
        this.enableCancel = enableCancel;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Integer getApplyPeriods() {
        return applyPeriods;
    }

    public void setApplyPeriods(Integer applyPeriods) {
        this.applyPeriods = applyPeriods;
    }

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

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getDeductibleInterest() {
        return deductibleInterest;
    }

    public void setDeductibleInterest(String deductibleInterest) {
        this.deductibleInterest = deductibleInterest;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(String grantAmount) {
        this.grantAmount = grantAmount;
    }

    public Integer getGrantPeriods() {
        return grantPeriods;
    }

    public void setGrantPeriods(Integer grantPeriods) {
        this.grantPeriods = grantPeriods;
    }

    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Integer getNextNodeExecutorType() {
        return nextNodeExecutorType;
    }

    public void setNextNodeExecutorType(Integer nextNodeExecutorType) {
        this.nextNodeExecutorType = nextNodeExecutorType;
    }

    public String getNextNodeExecutor() {
        return nextNodeExecutor;
    }

    public void setNextNodeExecutor(String nextNodeExecutor) {
        this.nextNodeExecutor = nextNodeExecutor;
    }

    public String getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(String currentOperator) {
        this.currentOperator = currentOperator;
    }

    public String getCurrentOperatorId() {
        return currentOperatorId;
    }

    public void setCurrentOperatorId(String currentOperatorId) {
        this.currentOperatorId = currentOperatorId;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getMaxRepaymentWeekly() {
        return maxRepaymentWeekly;
    }

    public void setMaxRepaymentWeekly(String maxRepaymentWeekly) {
        this.maxRepaymentWeekly = maxRepaymentWeekly;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getActMaxRate() {
        return actMaxRate;
    }

    public void setActMaxRate(String actMaxRate) {
        this.actMaxRate = actMaxRate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.orderNo);
        dest.writeString(this.uid);
        dest.writeString(this.productId);
        dest.writeString(this.orderType);
        dest.writeString(this.paymentType);
        dest.writeString(this.applyAmount);
        dest.writeValue(this.applyPeriods);
        dest.writeString(this.monthlyRate);
        dest.writeString(this.periodsAmount);
        dest.writeString(this.totalInterest);
        dest.writeString(this.deductibleInterest);
        dest.writeString(this.orderStatus);
        dest.writeString(this.grantAmount);
        dest.writeValue(this.grantPeriods);
        dest.writeValue(this.enableCancel);
        dest.writeString(this.grantTime);
        dest.writeString(this.remark);
        dest.writeString(this.workId);
        dest.writeString(this.batchId);
        dest.writeValue(this.nextNodeExecutorType);
        dest.writeString(this.nextNodeExecutor);
        dest.writeString(this.currentOperator);
        dest.writeString(this.currentOperatorId);
        dest.writeString(this.stepCode);
        dest.writeString(this.maxRepaymentWeekly);
        dest.writeString(this.contCode);
        dest.writeString(this.actMaxRate);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public OrderBean3() {
    }

    protected OrderBean3(Parcel in) {
        this.orderId = in.readString();
        this.orderNo = in.readString();
        this.uid = in.readString();
        this.productId = in.readString();
        this.orderType = in.readString();
        this.paymentType = in.readString();
        this.applyAmount = in.readString();
        this.applyPeriods = (Integer) in.readValue(Integer.class.getClassLoader());
        this.monthlyRate = in.readString();
        this.periodsAmount = in.readString();
        this.totalInterest = in.readString();
        this.deductibleInterest = in.readString();
        this.orderStatus = in.readString();
        this.grantAmount = in.readString();
        this.grantPeriods = (Integer) in.readValue(Integer.class.getClassLoader());
        this.enableCancel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.grantTime = in.readString();
        this.remark = in.readString();
        this.workId = in.readString();
        this.batchId = in.readString();
        this.nextNodeExecutorType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nextNodeExecutor = in.readString();
        this.currentOperator = in.readString();
        this.currentOperatorId = in.readString();
        this.stepCode = in.readString();
        this.maxRepaymentWeekly = in.readString();
        this.contCode = in.readString();
        this.actMaxRate = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<OrderBean3> CREATOR = new Creator<OrderBean3>() {
        @Override
        public OrderBean3 createFromParcel(Parcel source) {
            return new OrderBean3(source);
        }

        @Override
        public OrderBean3[] newArray(int size) {
            return new OrderBean3[size];
        }
    };
}
