package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * vip实体类
 * Created by Administrator on 2016/5/31.
 */
public class VipBean implements Parcelable {

    /**
     * userName : 13655441234
     * userVipLevel : 1
     * userVipId : 2d2ebb67a1d711e599b3eca86ba4ba05
     * vipImage : img/87162178.jpg
     * expireDate : 2016-09-31 00:08:29
     * vipList : [{"vipId":"2d2ebb67a1d711e599b3eca86ba4ba05","vipLevel":1,"imageUrl":"img/87162178.jpg","feeWaiver":5,"originalPrice":100,"presentPrice":90},{"vipId":"2d2ebb67a1d711e599b3eca86ba4ba06","vipLevel":2,"imageUrl":"img/12832987.jpg","feeWaiver":7,"originalPrice":200,"presentPrice":150},{"vipId":"2d2ebb67a1d711e599b3eca86ba4ba07","vipLevel":3,"imageUrl":"img/12832987.jpg","feeWaiver":10,"originalPrice":300,"presentPrice":220}]
     */

    private String userName;
    private int userVipLevel;
    private String userVipId;
    private String vipImage;
    private long expireDate;
    /**
     * vipId : 2d2ebb67a1d711e599b3eca86ba4ba05
     * vipLevel : 1
     * imageUrl : img/87162178.jpg
     * feeWaiver : 5.0
     * originalPrice : 100.0
     * presentPrice : 90.0
     */

    private List<VipListEntity> vipList;

    protected VipBean(Parcel in) {
        userName = in.readString();
        userVipLevel = in.readInt();
        userVipId = in.readString();
        vipImage = in.readString();
        expireDate = in.readLong();
    }

    public static final Creator<VipBean> CREATOR = new Creator<VipBean>() {
        @Override
        public VipBean createFromParcel(Parcel in) {
            return new VipBean(in);
        }

        @Override
        public VipBean[] newArray(int size) {
            return new VipBean[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserVipLevel() {
        return userVipLevel;
    }

    public void setUserVipLevel(int userVipLevel) {
        this.userVipLevel = userVipLevel;
    }

    public String getUserVipId() {
        return userVipId;
    }

    public void setUserVipId(String userVipId) {
        this.userVipId = userVipId;
    }

    public String getVipImage() {
        return vipImage;
    }

    public void setVipImage(String vipImage) {
        this.vipImage = vipImage;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public List<VipListEntity> getVipList() {
        return vipList;
    }

    public void setVipList(List<VipListEntity> vipList) {
        this.vipList = vipList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeInt(userVipLevel);
        parcel.writeString(userVipId);
        parcel.writeString(vipImage);
        parcel.writeLong(expireDate);
    }

    public static class VipListEntity {
        private String vipId;
        private int vipLevel;
        private String imageUrl;
        private String feeWaiver;
        private String originalPrice;
        private String presentPrice;

        public String getVipId() {
            return vipId;
        }

        public void setVipId(String vipId) {
            this.vipId = vipId;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFeeWaiver() {
            return feeWaiver;
        }

        public void setFeeWaiver(String feeWaiver) {
            this.feeWaiver = feeWaiver;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getPresentPrice() {
            return presentPrice;
        }

        public void setPresentPrice(String presentPrice) {
            this.presentPrice = presentPrice;
        }
    }
}
