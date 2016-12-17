package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取支持银行列表
 * Q164454216
 * Created by xiaoke on 2016/12/12.
 */

public class BanksBean implements Parcelable {

    private String modifyTime;
    private String bankName;//": "中国银行",
    private String id;//": "2c908c6e525362f3015253634ec40001",
    private String modifyPerson;//": null,
    private String status;//": null


    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.modifyTime);
        dest.writeString(this.bankName);
        dest.writeString(this.id);
        dest.writeString(this.modifyPerson);
        dest.writeString(this.status);
    }

    public BanksBean() {
    }

    protected BanksBean(Parcel in) {
        this.modifyTime = in.readString();
        this.bankName = in.readString();
        this.id = in.readString();
        this.modifyPerson = in.readString();
        this.status = in.readString();
    }

    public static final Creator<BanksBean> CREATOR = new Creator<BanksBean>() {
        @Override
        public BanksBean createFromParcel(Parcel source) {
            return new BanksBean(source);
        }

        @Override
        public BanksBean[] newArray(int size) {
            return new BanksBean[size];
        }
    };
}
