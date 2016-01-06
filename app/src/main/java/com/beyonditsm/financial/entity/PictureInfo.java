package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 15/12/17.
 */
public class PictureInfo implements Parcelable {
    /**
     * status : 200
     * data : {"total":1,"rows":[{"id":"2c908c7e51af9f460151afd445910016","accountId":"2c908c7e51af9f460151afc3d7350002","orderNo":"2c908c7e51af9f460151afd0c784000e","accessoryPath":"img/2646326119678976.jpg","createTime":1450353903000,"modifyTime":1450353903000,"createPerson":null,"modifyPerson":null,"isValid":null}]}
     * message : 查询成功！
     */

    private int status;
    /**
     * total : 1
     * rows : [{"id":"2c908c7e51af9f460151afd445910016","accountId":"2c908c7e51af9f460151afc3d7350002","orderNo":"2c908c7e51af9f460151afd0c784000e","accessoryPath":"img/2646326119678976.jpg","createTime":1450353903000,"modifyTime":1450353903000,"createPerson":null,"modifyPerson":null,"isValid":null}]
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
        private int total;
        /**
         * id : 2c908c7e51af9f460151afd445910016
         * accountId : 2c908c7e51af9f460151afc3d7350002
         * orderNo : 2c908c7e51af9f460151afd0c784000e
         * accessoryPath : img/2646326119678976.jpg
         * createTime : 1450353903000
         * modifyTime : 1450353903000
         * createPerson : null
         * modifyPerson : null
         * isValid : null
         */

        private List<RowsEntity> rows;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setRows(List<RowsEntity> rows) {
            this.rows = rows;
        }

        public int getTotal() {
            return total;
        }

        public List<RowsEntity> getRows() {
            return rows;
        }

        public static class RowsEntity {
            private String id;
            private String accountId;
            private String orderNo;
            private String accessoryPath;
            private long createTime;
            private long modifyTime;
            private Object createPerson;
            private Object modifyPerson;
            private Object isValid;

            public void setId(String id) {
                this.id = id;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public void setAccessoryPath(String accessoryPath) {
                this.accessoryPath = accessoryPath;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public void setCreatePerson(Object createPerson) {
                this.createPerson = createPerson;
            }

            public void setModifyPerson(Object modifyPerson) {
                this.modifyPerson = modifyPerson;
            }

            public void setIsValid(Object isValid) {
                this.isValid = isValid;
            }

            public String getId() {
                return id;
            }

            public String getAccountId() {
                return accountId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public String getAccessoryPath() {
                return accessoryPath;
            }

            public long getCreateTime() {
                return createTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public Object getCreatePerson() {
                return createPerson;
            }

            public Object getModifyPerson() {
                return modifyPerson;
            }

            public Object getIsValid() {
                return isValid;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.total);
            dest.writeList(this.rows);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.total = in.readInt();
            this.rows = new ArrayList<RowsEntity>();
            in.readList(this.rows, List.class.getClassLoader());
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
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

    public PictureInfo() {
    }

    protected PictureInfo(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<PictureInfo> CREATOR = new Parcelable.Creator<PictureInfo>() {
        public PictureInfo createFromParcel(Parcel source) {
            return new PictureInfo(source);
        }

        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };
}
