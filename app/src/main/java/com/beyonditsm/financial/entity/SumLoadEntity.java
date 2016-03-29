package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 提交上传资料
 * Created by wangbin on 16/3/29.
 */
public class SumLoadEntity implements Parcelable {
    private String orderId;
    private String flowId;
    private List<SubUplEntity> uploadItem;

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

    public List<SubUplEntity> getUploadItem() {
        return uploadItem;
    }

    public void setUploadItem(List<SubUplEntity> uploadItem) {
        this.uploadItem = uploadItem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.flowId);
        dest.writeTypedList(uploadItem);
    }

    public SumLoadEntity() {
    }

    protected SumLoadEntity(Parcel in) {
        this.orderId = in.readString();
        this.flowId = in.readString();
        this.uploadItem = in.createTypedArrayList(SubUplEntity.CREATOR);
    }

    public static final Parcelable.Creator<SumLoadEntity> CREATOR = new Parcelable.Creator<SumLoadEntity>() {
        public SumLoadEntity createFromParcel(Parcel source) {
            return new SumLoadEntity(source);
        }

        public SumLoadEntity[] newArray(int size) {
            return new SumLoadEntity[size];
        }
    };
}
