package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 上传资料
 * Created by wangbin on 16/3/23.
 */
public class UpLoadEntity implements Parcelable {
    private String flowId;
    private String description;
    private String isComplete;//是否上传成功
    private Integer status;//流程的状态：0驳回，1通过，2审核中


    private String displayName;
    private List<CreditUplEntity> items;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<CreditUplEntity> getItems() {
        return items;
    }

    public void setItems(List<CreditUplEntity> items) {
        this.items = items;
    }

    public UpLoadEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flowId);
        dest.writeString(this.description);
        dest.writeString(this.isComplete);
        dest.writeValue(this.status);
        dest.writeString(this.displayName);
        dest.writeTypedList(items);
    }

    protected UpLoadEntity(Parcel in) {
        this.flowId = in.readString();
        this.description = in.readString();
        this.isComplete = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.displayName = in.readString();
        this.items = in.createTypedArrayList(CreditUplEntity.CREATOR);
    }

    public static final Creator<UpLoadEntity> CREATOR = new Creator<UpLoadEntity>() {
        public UpLoadEntity createFromParcel(Parcel source) {
            return new UpLoadEntity(source);
        }

        public UpLoadEntity[] newArray(int size) {
            return new UpLoadEntity[size];
        }
    };
}
