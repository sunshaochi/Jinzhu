package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class OrderWorkMarkBean implements Parcelable {
    /**
     * 主键
     */
    private String ordeDatWorkMarkId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 工作流流程ID
     */
    private String workId;

    /**
     * 下一个节点的执行人类型:1人员；2岗位
     */
    private int nextNodeExecutorType;

    /**
     * 下一个节点的执行人
     */
    private String nextNodeExecutor;

    /**
     * 流程业务状态（类似电销待处理，风控待处理）
     */
    private String status;

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

    private int nodeType;

    /**
     * 备注
     */
    private String remark;


    public String getOrdeDatWorkMarkId() {
        return ordeDatWorkMarkId;
    }

    public void setOrdeDatWorkMarkId(String ordeDatWorkMarkId) {
        this.ordeDatWorkMarkId = ordeDatWorkMarkId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getNextNodeExecutorType() {
        return nextNodeExecutorType;
    }

    public void setNextNodeExecutorType(int nextNodeExecutorType) {
        this.nextNodeExecutorType = nextNodeExecutorType;
    }

    public String getNextNodeExecutor() {
        return nextNodeExecutor;
    }

    public void setNextNodeExecutor(String nextNodeExecutor) {
        this.nextNodeExecutor = nextNodeExecutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ordeDatWorkMarkId);
        dest.writeString(this.orderId);
        dest.writeString(this.workId);
        dest.writeInt(this.nextNodeExecutorType);
        dest.writeString(this.nextNodeExecutor);
        dest.writeString(this.status);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
        dest.writeInt(this.nodeType);
        dest.writeString(this.remark);
    }

    public OrderWorkMarkBean() {
    }

    protected OrderWorkMarkBean(Parcel in) {
        this.ordeDatWorkMarkId = in.readString();
        this.orderId = in.readString();
        this.workId = in.readString();
        this.nextNodeExecutorType = in.readInt();
        this.nextNodeExecutor = in.readString();
        this.status = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
        this.nodeType = in.readInt();
        this.remark = in.readString();
    }

    public static final Creator<OrderWorkMarkBean> CREATOR = new Creator<OrderWorkMarkBean>() {
        @Override
        public OrderWorkMarkBean createFromParcel(Parcel source) {
            return new OrderWorkMarkBean(source);
        }

        @Override
        public OrderWorkMarkBean[] newArray(int size) {
            return new OrderWorkMarkBean[size];
        }
    };
}
