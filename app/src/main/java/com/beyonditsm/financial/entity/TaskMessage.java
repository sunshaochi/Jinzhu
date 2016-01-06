package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 任务信息（提交）
 * Created by gxy on 2015/11/25.
 */
public class TaskMessage implements Parcelable{
    private String taskId;//任务id
    private String strategyId;//任务策略id
    private String fieldName;//
    private String fieldKey;
    private String fieldValue;
    private int scoreType;

    public TaskMessage(){

    }


    protected TaskMessage(Parcel in) {
        taskId = in.readString();
        strategyId = in.readString();
        fieldName = in.readString();
        fieldKey = in.readString();
        fieldValue = in.readString();
        scoreType = in.readInt();
    }

    public static final Creator<TaskMessage> CREATOR = new Creator<TaskMessage>() {
        @Override
        public TaskMessage createFromParcel(Parcel in) {
            return new TaskMessage(in);
        }

        @Override
        public TaskMessage[] newArray(int size) {
            return new TaskMessage[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeString(strategyId);
        dest.writeString(fieldName);
        dest.writeString(fieldKey);
        dest.writeString(fieldValue);
        dest.writeInt(scoreType);
    }
}
