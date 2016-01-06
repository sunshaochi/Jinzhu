package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/30.
 */
public class UpdateServantEntity implements Parcelable {
    private UserEntity userEntity;
    private ServantEntity servantEntity;

    protected UpdateServantEntity(Parcel in) {
        userEntity = in.readParcelable(UserEntity.class.getClassLoader());
        servantEntity = in.readParcelable(ServantEntity.class.getClassLoader());
    }

    public static final Creator<UpdateServantEntity> CREATOR = new Creator<UpdateServantEntity>() {
        @Override
        public UpdateServantEntity createFromParcel(Parcel in) {
            return new UpdateServantEntity(in);
        }

        @Override
        public UpdateServantEntity[] newArray(int size) {
            return new UpdateServantEntity[size];
        }
    };

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ServantEntity getServantEntity() {
        return servantEntity;
    }

    public void setServantEntity(ServantEntity servantEntity) {
        this.servantEntity = servantEntity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userEntity, flags);
        dest.writeParcelable(servantEntity, flags);
    }

    public UpdateServantEntity() {
    }

    public UpdateServantEntity(UserEntity userEntity, ServantEntity servantEntity) {
        this.userEntity = userEntity;
        this.servantEntity = servantEntity;
    }
}
