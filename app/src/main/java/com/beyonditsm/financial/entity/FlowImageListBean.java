package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class FlowImageListBean implements Parcelable {


    /**
     * 流程上传图片表ID
     */

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 流程ID
     */
    private String flowId;

    /**
     * 上传项ID
     */
    private String itemId;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 是否通过：1：通过，0驳回，2待审核，3草稿
     */
    private int status;

    /**
     * 是否有效：1:有效，0无效
     */
    private Boolean isDisabled;

    /**
     * 备注
     */
    private String remark;

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        dest.writeString(this.flowId);
        dest.writeString(this.itemId);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.status);
        dest.writeValue(this.isDisabled);
        dest.writeString(this.remark);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public FlowImageListBean() {
    }

    protected FlowImageListBean(Parcel in) {
        this.orderId = in.readString();
        this.flowId = in.readString();
        this.itemId = in.readString();
        this.imgUrl = in.readString();
        this.status = in.readInt();
        this.isDisabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.remark = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<FlowImageListBean> CREATOR = new Creator<FlowImageListBean>() {
        @Override
        public FlowImageListBean createFromParcel(Parcel source) {
            return new FlowImageListBean(source);
        }

        @Override
        public FlowImageListBean[] newArray(int size) {
            return new FlowImageListBean[size];
        }
    };
}
