package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *上传资料
 * Created by wangbin on 16/3/23.
 */
public class UpLoadEntity implements Parcelable {

    private String flowId;
    private String description;
    private String isComplete;//是否上传成功
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
        dest.writeString(this.displayName);
    }

    protected UpLoadEntity(Parcel in) {
        this.flowId = in.readString();
        this.description = in.readString();
        this.isComplete = in.readString();
        this.displayName = in.readString();
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
