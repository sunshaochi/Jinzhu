package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class AttachmentlistBean implements Parcelable {
    /**
     * 订单的附件信息表
     */
    private String orderAttachmentId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 附件名称
     */
    private String attachName;

    /**
     * 附件的URL
     */
    private String attachUrl;

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


    public String getOrderAttachmentId() {
        return orderAttachmentId;
    }

    public void setOrderAttachmentId(String orderAttachmentId) {
        this.orderAttachmentId = orderAttachmentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
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
        dest.writeString(this.orderAttachmentId);
        dest.writeString(this.orderId);
        dest.writeString(this.attachName);
        dest.writeString(this.attachUrl);
        dest.writeString(this.createUser);
        dest.writeString(this.createTime);
        dest.writeString(this.updateUser);
        dest.writeString(this.updateTime);
    }

    public AttachmentlistBean() {
    }

    protected AttachmentlistBean(Parcel in) {
        this.orderAttachmentId = in.readString();
        this.orderId = in.readString();
        this.attachName = in.readString();
        this.attachUrl = in.readString();
        this.createUser = in.readString();
        this.createTime = in.readString();
        this.updateUser = in.readString();
        this.updateTime = in.readString();
    }

    public static final Creator<AttachmentlistBean> CREATOR = new Creator<AttachmentlistBean>() {
        @Override
        public AttachmentlistBean createFromParcel(Parcel source) {
            return new AttachmentlistBean(source);
        }

        @Override
        public AttachmentlistBean[] newArray(int size) {
            return new AttachmentlistBean[size];
        }
    };
}
