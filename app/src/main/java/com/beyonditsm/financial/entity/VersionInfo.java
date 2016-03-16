package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangbin on 15/12/28.
 */
public class VersionInfo implements Parcelable {

    private boolean isNeedUpdrage;//是否可以升级
    private String message;
    /**
     * id : 322
     * version : 2
     * platform : ANDROID
     * packagePath : /downloads/jinzhu_v2.0.apk
     * updrageLog : demo log
     * remark : null
     * createTime : null
     * createPersonId : -1
     */

    private VersionEntity version;
//    private String packagePath;


    public boolean isNeedUpdrage() {
        return isNeedUpdrage;
    }

    public void setIsNeedUpdrage(boolean isNeedUpdrage) {
        this.isNeedUpdrage = isNeedUpdrage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getPackagePath() {
//        return packagePath;
//    }

//    public void setPackagePath(String packagePath) {
//        this.packagePath = packagePath;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(isNeedUpdrage ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
//        dest.writeString(this.packagePath);
    }

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        this.isNeedUpdrage = in.readByte() != 0;
        this.message = in.readString();
//        this.packagePath = in.readString();
    }

    public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
        public VersionInfo createFromParcel(Parcel source) {
            return new VersionInfo(source);
        }

        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };

    public void setVersion(VersionEntity version) {
        this.version = version;
    }

    public VersionEntity getVersion() {
        return version;
    }

    public static class VersionEntity implements Parcelable {
        private String id;
        private int version;
        private String platform;
        private String packagePath;
        private String updrageLog;
        private String remark;
        private String createTime;
        private String createPersonId;
        private String versionName;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public void setUpdrageLog(String updrageLog) {
            this.updrageLog = updrageLog;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setCreatePersonId(String createPersonId) {
            this.createPersonId = createPersonId;
        }

        public String getId() {
            return id;
        }

        public int getVersion() {
            return version;
        }

        public String getPlatform() {
            return platform;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public String getUpdrageLog() {
            return updrageLog;
        }

        public String getRemark() {
            return remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getCreatePersonId() {
            return createPersonId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeInt(this.version);
            dest.writeString(this.platform);
            dest.writeString(this.packagePath);
            dest.writeString(this.updrageLog);
            dest.writeString(this.remark);
            dest.writeString(this.createTime);
            dest.writeString(this.createPersonId);
            dest.writeString(this.versionName);
        }

        public VersionEntity() {
        }

        protected VersionEntity(Parcel in) {
            this.id = in.readString();
            this.version = in.readInt();
            this.platform = in.readString();
            this.packagePath = in.readString();
            this.updrageLog = in.readString();
            this.remark = in.readString();
            this.createTime = in.readString();
            this.createPersonId = in.readString();
            this.versionName = in.readString();
        }

        public static final Creator<VersionEntity> CREATOR = new Creator<VersionEntity>() {
            public VersionEntity createFromParcel(Parcel source) {
                return new VersionEntity(source);
            }

            public VersionEntity[] newArray(int size) {
                return new VersionEntity[size];
            }
        };
    }
}
