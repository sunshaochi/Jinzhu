package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 升级服务者返沪信息条件
 * Created by wangbin on 15/12/8.
 */
public class UpServantEntity implements Parcelable {

    /**
     * status : 200
     * data : {"_primaryTaskCt":67,"_seniorTaskCt":0,"_recommendCount":0,"_creditScoreInt":0,"totalStatus":"false","_middleTaskCt":0,"_customerMustAddInt":0,"message":"升级初级服务者的条件里还有未达标的项目"}
     * message : 成功！
     */

    private int status;
    /**
     * _primaryTaskCt : 67
     * _seniorTaskCt : 0
     * _recommendCount : 0
     * _creditScoreInt : 0
     * totalStatus : false
     * _middleTaskCt : 0
     * _customerMustAddInt : 0
     * message : 升级初级服务者的条件里还有未达标的项目
     */

    private DataEntity data;
    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity implements Parcelable {

        private int _primaryTaskCt;//初级任务完成比例
        private int _seniorTaskCt;//高级任务完成比例
        private int _recommendCount;//推荐好友数目完成比例
        private int _creditScoreInt;//信用分数达标完成比例
        private String totalStatus;//总状态
        private int _middleTaskCt;//中级任务完成比例
        private int _customerMustAddInt;//必填资料完成比例
        private String message;
        /**
         * _sumLoan : 67
         */

        private int _sumLoan;//需要推荐好友的授信总额达标数


        public String getIsApproving() {
            return isApproving;
        }

        public void setIsApproving(String isApproving) {
            this.isApproving = isApproving;
        }

        private String isApproving;//是否在审批中

        public void set_primaryTaskCt(int _primaryTaskCt) {
            this._primaryTaskCt = _primaryTaskCt;
        }

        public void set_seniorTaskCt(int _seniorTaskCt) {
            this._seniorTaskCt = _seniorTaskCt;
        }

        public void set_recommendCount(int _recommendCount) {
            this._recommendCount = _recommendCount;
        }

        public void set_creditScoreInt(int _creditScoreInt) {
            this._creditScoreInt = _creditScoreInt;
        }

        public void setTotalStatus(String totalStatus) {
            this.totalStatus = totalStatus;
        }

        public void set_middleTaskCt(int _middleTaskCt) {
            this._middleTaskCt = _middleTaskCt;
        }

        public void set_customerMustAddInt(int _customerMustAddInt) {
            this._customerMustAddInt = _customerMustAddInt;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int get_primaryTaskCt() {
            return _primaryTaskCt;
        }

        public int get_seniorTaskCt() {
            return _seniorTaskCt;
        }

        public int get_recommendCount() {
            return _recommendCount;
        }

        public int get_creditScoreInt() {
            return _creditScoreInt;
        }

        public String getTotalStatus() {
            return totalStatus;
        }

        public int get_middleTaskCt() {
            return _middleTaskCt;
        }

        public int get_customerMustAddInt() {
            return _customerMustAddInt;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this._primaryTaskCt);
            dest.writeInt(this._seniorTaskCt);
            dest.writeInt(this._recommendCount);
            dest.writeInt(this._creditScoreInt);
            dest.writeString(this.totalStatus);
            dest.writeInt(this._middleTaskCt);
            dest.writeInt(this._customerMustAddInt);
            dest.writeString(this.message);
            dest.writeString(this.isApproving);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this._primaryTaskCt = in.readInt();
            this._seniorTaskCt = in.readInt();
            this._recommendCount = in.readInt();
            this._creditScoreInt = in.readInt();
            this.totalStatus = in.readString();
            this._middleTaskCt = in.readInt();
            this._customerMustAddInt = in.readInt();
            this.message = in.readString();
            this.isApproving = in.readString();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public void set_sumLoan(int _sumLoan) {
            this._sumLoan = _sumLoan;
        }

        public int get_sumLoan() {
            return _sumLoan;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public UpServantEntity() {
    }

    protected UpServantEntity(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<UpServantEntity> CREATOR = new Creator<UpServantEntity>() {
        public UpServantEntity createFromParcel(Parcel source) {
            return new UpServantEntity(source);
        }

        public UpServantEntity[] newArray(int size) {
            return new UpServantEntity[size];
        }
    };
}
