package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2016/12/12.
 */

public class ZidianBean implements Parcelable {
    private List<RelationEntity> Salary,Working_property,Unit_property,liveType,relation,education,maritalStatus;


    public void setSalary(List<RelationEntity> salary) {
        Salary = salary;
    }

    public void setWorking_property(List<RelationEntity> working_property) {
        Working_property = working_property;
    }

    public void setUnit_property(List<RelationEntity> unit_property) {
        Unit_property = unit_property;
    }

    public void setLiveType(List<RelationEntity> liveType) {
        this.liveType = liveType;
    }

    public void setRelation(List<RelationEntity> relation) {
        this.relation = relation;
    }

    public void setEducation(List<RelationEntity> education) {
        this.education = education;
    }

    public void setMaritalStatus(List<RelationEntity> maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public ZidianBean() {
    }

    public List<RelationEntity> getSalary() {
        return Salary;
    }

    public List<RelationEntity> getWorking_property() {
        return Working_property;
    }

    public List<RelationEntity> getUnit_property() {
        return Unit_property;
    }

    public List<RelationEntity> getLiveType() {
        return liveType;
    }

    public List<RelationEntity> getRelation() {
        return relation;
    }

    public List<RelationEntity> getEducation() {
        return education;
    }

    public List<RelationEntity> getMaritalStatus() {
        return maritalStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Salary);
        dest.writeTypedList(this.Working_property);
        dest.writeTypedList(this.Unit_property);
        dest.writeTypedList(this.liveType);
        dest.writeTypedList(this.relation);
        dest.writeTypedList(this.education);
        dest.writeTypedList(this.maritalStatus);
    }

    protected ZidianBean(Parcel in) {
        this.Salary = in.createTypedArrayList(RelationEntity.CREATOR);
        this.Working_property = in.createTypedArrayList(RelationEntity.CREATOR);
        this.Unit_property = in.createTypedArrayList(RelationEntity.CREATOR);
        this.liveType = in.createTypedArrayList(RelationEntity.CREATOR);
        this.relation = in.createTypedArrayList(RelationEntity.CREATOR);
        this.education = in.createTypedArrayList(RelationEntity.CREATOR);
        this.maritalStatus = in.createTypedArrayList(RelationEntity.CREATOR);
    }

    public static final Creator<ZidianBean> CREATOR = new Creator<ZidianBean>() {
        @Override
        public ZidianBean createFromParcel(Parcel source) {
            return new ZidianBean(source);
        }

        @Override
        public ZidianBean[] newArray(int size) {
            return new ZidianBean[size];
        }
    };
}
