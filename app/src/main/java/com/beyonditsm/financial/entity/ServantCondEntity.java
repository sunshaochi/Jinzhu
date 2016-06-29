package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 代言人条件bean
 * Created by Administrator on 2016/5/16.
 */
public class ServantCondEntity implements Parcelable{

    /**
     * SERVANT_NAME : ROLE_SERVANT_SENIOR
     * CONDITION_TYPE : 2
     * servantPassCond : 0
     * servantTotalCond : 67
     * SERVANT_LEVEL : 3
     * servantDesc : 贷款m元
     * servantName : 高级代言人
     */

    private String SERVANT_NAME;//代言人名称
    private String CONDITION_TYPE;
    private int servantPassCond;//已完成代言人条件数量
    private int servantTotalCond;//总代言人条件数量
    private int SERVANT_LEVEL;
    private String servantDesc;//代言人名次
    private String servantName;//代言人条件描述
    /**
     * servantLevel : 2
     * servantId : ROLE_SERVANT_MIDDLE
     */

    private int servantLevel;
    private String servantId;

    protected ServantCondEntity(Parcel in) {
        SERVANT_NAME = in.readString();
        CONDITION_TYPE = in.readString();
        servantPassCond = in.readInt();
        servantTotalCond = in.readInt();
        SERVANT_LEVEL = in.readInt();
        servantDesc = in.readString();
        servantName = in.readString();
    }

    public static final Creator<ServantCondEntity> CREATOR = new Creator<ServantCondEntity>() {
        @Override
        public ServantCondEntity createFromParcel(Parcel in) {
            return new ServantCondEntity(in);
        }

        @Override
        public ServantCondEntity[] newArray(int size) {
            return new ServantCondEntity[size];
        }
    };

    public String getSERVANT_NAME() {
        return SERVANT_NAME;
    }

    public void setSERVANT_NAME(String SERVANT_NAME) {
        this.SERVANT_NAME = SERVANT_NAME;
    }

    public String getCONDITION_TYPE() {
        return CONDITION_TYPE;
    }

    public void setCONDITION_TYPE(String CONDITION_TYPE) {
        this.CONDITION_TYPE = CONDITION_TYPE;
    }

    public int getServantPassCond() {
        return servantPassCond;
    }

    public void setServantPassCond(int servantPassCond) {
        this.servantPassCond = servantPassCond;
    }

    public int getServantTotalCond() {
        return servantTotalCond;
    }

    public void setServantTotalCond(int servantTotalCond) {
        this.servantTotalCond = servantTotalCond;
    }

    public int getSERVANT_LEVEL() {
        return SERVANT_LEVEL;
    }

    public void setSERVANT_LEVEL(int SERVANT_LEVEL) {
        this.SERVANT_LEVEL = SERVANT_LEVEL;
    }

    public String getServantDesc() {
        return servantDesc;
    }

    public void setServantDesc(String servantDesc) {
        this.servantDesc = servantDesc;
    }

    public String getServantName() {
        return servantName;
    }

    public void setServantName(String servantName) {
        this.servantName = servantName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(SERVANT_NAME);
        parcel.writeString(CONDITION_TYPE);
        parcel.writeInt(servantPassCond);
        parcel.writeInt(servantTotalCond);
        parcel.writeInt(SERVANT_LEVEL);
        parcel.writeString(servantDesc);
        parcel.writeString(servantName);
    }

    public int getServantLevel() {
        return servantLevel;
    }

    public void setServantLevel(int servantLevel) {
        this.servantLevel = servantLevel;
    }

    public String getServantId() {
        return servantId;
    }

    public void setServantId(String servantId) {
        this.servantId = servantId;
    }
}
