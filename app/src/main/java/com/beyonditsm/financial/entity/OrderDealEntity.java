package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/15.
 */
public class OrderDealEntity implements Parcelable{

    /**
     * roleName : 信贷经理
     * dealName : 信贷经理抢单
     * dealTime : 1450094497000
     * userId : 2c908c7e519f2e7c01519f7ffb75003b
     */

    private String roleName;
    private String dealName;
    private long dealTime;
    private String userId;
    private String rcHeadPic;//信贷经理头像
    private String nickName;//昵称

    public String getRcHeadPic() {
        return rcHeadPic;
    }

    public void setRcHeadPic(String rcHeadPic) {
        this.rcHeadPic = rcHeadPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * orderFlowStatus : CUSTOMER_SUBMIT
     */

    private String orderFlowStatus;

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDealName() {
        return dealName;
    }

    public long getDealTime() {
        return dealTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setOrderFlowStatus(String orderFlowStatus) {
        this.orderFlowStatus = orderFlowStatus;
    }

    public String getOrderFlowStatus() {
        return orderFlowStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roleName);
        dest.writeString(this.dealName);
        dest.writeLong(this.dealTime);
        dest.writeString(this.userId);
        dest.writeString(this.rcHeadPic);
        dest.writeString(this.nickName);
        dest.writeString(this.orderFlowStatus);
    }

    public OrderDealEntity() {
    }

    protected OrderDealEntity(Parcel in) {
        this.roleName = in.readString();
        this.dealName = in.readString();
        this.dealTime = in.readLong();
        this.userId = in.readString();
        this.rcHeadPic = in.readString();
        this.nickName = in.readString();
        this.orderFlowStatus = in.readString();
    }

    public static final Creator<OrderDealEntity> CREATOR = new Creator<OrderDealEntity>() {
        public OrderDealEntity createFromParcel(Parcel source) {
            return new OrderDealEntity(source);
        }

        public OrderDealEntity[] newArray(int size) {
            return new OrderDealEntity[size];
        }
    };
}
