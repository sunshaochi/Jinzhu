package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class OrderFlowBean implements Parcelable {


    /**
     * 主键
     */
    private String id;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 流程ID
     */
    private String flowId;

    /**
     * 流程模板版本
     */
    private int flowVersion;

    /**
     * 流程模板名称
     */
    private String flowName;

    /**
     * 流程类型 1：必填流程2：增信流程
     */
    private int flowType;

    /**
     * 上传项验证规则
     */
    private String flowRule;

    /**
     * 流程配置
     */
    private String cfgRule;

    /**
     * 流程描述
     */
    private String flowDesc;

    /**
     * 是否完成 1：完成 0：未完成
     */
    private Boolean isComplete;

    /**
     * 流程的状态：0驳回，1通过，2审核中
     */
    private Integer status;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public int getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(int flowVersion) {
        this.flowVersion = flowVersion;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public String getFlowRule() {
        return flowRule;
    }

    public void setFlowRule(String flowRule) {
        this.flowRule = flowRule;
    }

    public String getCfgRule() {
        return cfgRule;
    }

    public void setCfgRule(String cfgRule) {
        this.cfgRule = cfgRule;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        dest.writeString(this.id);
        dest.writeString(this.orderId);
        dest.writeString(this.flowId);
        dest.writeInt(this.flowVersion);
        dest.writeString(this.flowName);
        dest.writeInt(this.flowType);
        dest.writeString(this.flowRule);
        dest.writeString(this.cfgRule);
        dest.writeString(this.flowDesc);
        dest.writeValue(this.isComplete);
        dest.writeValue(this.status);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public OrderFlowBean() {
    }

    protected OrderFlowBean(Parcel in) {
        this.id = in.readString();
        this.orderId = in.readString();
        this.flowId = in.readString();
        this.flowVersion = in.readInt();
        this.flowName = in.readString();
        this.flowType = in.readInt();
        this.flowRule = in.readString();
        this.cfgRule = in.readString();
        this.flowDesc = in.readString();
        this.isComplete = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<OrderFlowBean> CREATOR = new Creator<OrderFlowBean>() {
        @Override
        public OrderFlowBean createFromParcel(Parcel source) {
            return new OrderFlowBean(source);
        }

        @Override
        public OrderFlowBean[] newArray(int size) {
            return new OrderFlowBean[size];
        }
    };
}
