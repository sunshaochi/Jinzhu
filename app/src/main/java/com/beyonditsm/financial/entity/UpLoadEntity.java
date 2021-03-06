package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 上传资料
 * Created by wangbin on 16/3/23.
 */
public class UpLoadEntity implements Parcelable {
    private String flowId;//流程id
    private String description;//例如上传身份证正面
    private String flowDisplayNam;//流程展示名
    private String isComplete;//资料上传状态，1为完成资料上传，0为未完成资料上传
    private Integer status;//流程的状态：0驳回，1通过，2审核中
    private int imgNum;//用户上传图片数量
    private int total;//流程所需图片数量,为0时不限制

    public String getFlowDisplayNam() {
        return flowDisplayNam;
    }

    public void setFlowDisplayNam(String flowDisplayNam) {
        this.flowDisplayNam = flowDisplayNam;
    }

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private String displayName;//上传企业经营场所环境照
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
        dest.writeString(this.flowDisplayNam);
        dest.writeString(this.isComplete);
        dest.writeValue(this.status);
        dest.writeInt(this.imgNum);
        dest.writeInt(this.total);
        dest.writeString(this.displayName);
        dest.writeTypedList(this.items);
    }

    protected UpLoadEntity(Parcel in) {
        this.flowId = in.readString();
        this.description = in.readString();
        this.flowDisplayNam = in.readString();
        this.isComplete = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imgNum = in.readInt();
        this.total = in.readInt();
        this.displayName = in.readString();
        this.items = in.createTypedArrayList(CreditUplEntity.CREATOR);
    }

    public static final Creator<UpLoadEntity> CREATOR = new Creator<UpLoadEntity>() {
        @Override
        public UpLoadEntity createFromParcel(Parcel source) {
            return new UpLoadEntity(source);
        }

        @Override
        public UpLoadEntity[] newArray(int size) {
            return new UpLoadEntity[size];
        }
    };
}
