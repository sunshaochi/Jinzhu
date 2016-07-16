package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gxy on 2015/12/10.
 */
public class ProductListTasks implements Parcelable{
    private String taskId;
    private String taskName;
    private String taskDesc;

    protected ProductListTasks(Parcel in) {
        taskId = in.readString();
        taskName = in.readString();
        taskDesc = in.readString();
    }

    public static final Creator<ProductListTasks> CREATOR = new Creator<ProductListTasks>() {
        @Override
        public ProductListTasks createFromParcel(Parcel in) {
            return new ProductListTasks(in);
        }

        @Override
        public ProductListTasks[] newArray(int size) {
            return new ProductListTasks[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeString(taskName);
        dest.writeString(taskDesc);
    }
}
