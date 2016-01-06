package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 任务策略
 * Created by gxy on 2015/11/25.
 */
public class TaskStrategyEntity implements Parcelable {
    private String stragegyId;//策略id
    private String taskId;//任务id
    private String fieldKey;//组件name属性值
    private String fieldName;//字段名称
    private int modelType;//组件类型(1:输入框，2:下拉列表，3：单选按钮，4：多选框，5：上传)
    private String dataType;//组件接收的数据类型
    private int scoreType;//加分类型(0:信用分，1:授信分) 此值提交数据时一起提交
    private List<String> optionValues;//下拉、多选、单选

    public TaskStrategyEntity(){

    }


    protected TaskStrategyEntity(Parcel in) {
        stragegyId = in.readString();
        taskId = in.readString();
        fieldKey = in.readString();
        fieldName = in.readString();
        modelType = in.readInt();
        dataType = in.readString();
        scoreType = in.readInt();
        optionValues = in.createStringArrayList();
    }

    public static final Creator<TaskStrategyEntity> CREATOR = new Creator<TaskStrategyEntity>() {
        @Override
        public TaskStrategyEntity createFromParcel(Parcel in) {
            return new TaskStrategyEntity(in);
        }

        @Override
        public TaskStrategyEntity[] newArray(int size) {
            return new TaskStrategyEntity[size];
        }
    };

    public String getStragegyId() {
        return stragegyId;
    }

    public void setStragegyId(String stragegyId) {
        this.stragegyId = stragegyId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<String> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<String> optionValues) {
        this.optionValues = optionValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stragegyId);
        dest.writeString(taskId);
        dest.writeString(fieldKey);
        dest.writeString(fieldName);
        dest.writeInt(modelType);
        dest.writeString(dataType);
        dest.writeInt(scoreType);
        dest.writeStringList(optionValues);
    }
}
