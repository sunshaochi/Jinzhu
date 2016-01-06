package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 任务列表
 * Created by gxy on 2015/11/24.
 */
public class TaskEntity implements Parcelable {
    private String id;//任务id
    private String taskId;//产品列表里的任务id
    private String taskName;//任务名称
    private String taskDesc;//任务描述
    private String taskType;//任务分类(1:初级任务，2:中级任务，3:高级任务)
    private int taskStatus;//任务状态
    private String fieldName;//
    private String fieldKey;
    private String fieldValue;
    private int scoreType;
    private int valueType;


    protected TaskEntity(Parcel in) {
        id = in.readString();
        taskId = in.readString();
        taskName = in.readString();
        taskDesc = in.readString();
        taskType = in.readString();
        taskStatus = in.readInt();
        fieldName = in.readString();
        fieldKey = in.readString();
        fieldValue = in.readString();
        scoreType = in.readInt();
        valueType = in.readInt();
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskEntity(){

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskId);
        dest.writeString(taskName);
        dest.writeString(taskDesc);
        dest.writeString(taskType);
        dest.writeInt(taskStatus);
        dest.writeString(fieldName);
        dest.writeString(fieldKey);
        dest.writeString(fieldValue);
        dest.writeInt(scoreType);
        dest.writeInt(valueType);
    }
}
