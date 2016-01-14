package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/12.
 */
public class FriendEntity implements Parcelable {

    /**
     * aname : 市辖区
     * rcUserId : 8aae98415220ce8301522140c79a0085
     * wkCardPic : img/2677493429322752.png
     * friendAccountId : 8aae982851f7dce40151f7de30060000
     * detailAddr : 海南省
     * braNchName : 长宁支行
     * accountId : 8aae98415220ce83015221372aae005c
     * pName : 海南省
     * cName : 海口市
     * manaSex : 男
     * accountNo : 6214850104416653
     * tel : 18616689900
     * idCardPic : img/2677493456061440.jpg
     * email : 18616689900@qq.com
     * manaName : 张启荣
     */

    private String aname;
    private String rcUserId;
    private String wkCardPic;
    private String friendAccountId;
    private String detailAddr;
    private String braNchName;
    private String accountId;
    private String pName;
    private String cName;
    private String manaSex;
    private String accountNo;
    private String tel;
    private String idCardPic;
    private String email;
    private String manaName;

    protected FriendEntity(Parcel in) {
        aname = in.readString();
        rcUserId = in.readString();
        wkCardPic = in.readString();
        friendAccountId = in.readString();
        detailAddr = in.readString();
        braNchName = in.readString();
        accountId = in.readString();
        pName = in.readString();
        cName = in.readString();
        manaSex = in.readString();
        accountNo = in.readString();
        tel = in.readString();
        idCardPic = in.readString();
        email = in.readString();
        manaName = in.readString();
    }

    public static final Creator<FriendEntity> CREATOR = new Creator<FriendEntity>() {
        @Override
        public FriendEntity createFromParcel(Parcel in) {
            return new FriendEntity(in);
        }

        @Override
        public FriendEntity[] newArray(int size) {
            return new FriendEntity[size];
        }
    };

    public void setAname(String aname) {
        this.aname = aname;
    }

    public void setRcUserId(String rcUserId) {
        this.rcUserId = rcUserId;
    }

    public void setWkCardPic(String wkCardPic) {
        this.wkCardPic = wkCardPic;
    }

    public void setFriendAccountId(String friendAccountId) {
        this.friendAccountId = friendAccountId;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public void setBraNchName(String braNchName) {
        this.braNchName = braNchName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public void setManaSex(String manaSex) {
        this.manaSex = manaSex;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setManaName(String manaName) {
        this.manaName = manaName;
    }

    public String getAname() {
        return aname;
    }

    public String getRcUserId() {
        return rcUserId;
    }

    public String getWkCardPic() {
        return wkCardPic;
    }

    public String getFriendAccountId() {
        return friendAccountId;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public String getBraNchName() {
        return braNchName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPName() {
        return pName;
    }

    public String getCName() {
        return cName;
    }

    public String getManaSex() {
        return manaSex;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getTel() {
        return tel;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public String getEmail() {
        return email;
    }

    public String getManaName() {
        return manaName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aname);
        dest.writeString(rcUserId);
        dest.writeString(wkCardPic);
        dest.writeString(friendAccountId);
        dest.writeString(detailAddr);
        dest.writeString(braNchName);
        dest.writeString(accountId);
        dest.writeString(pName);
        dest.writeString(cName);
        dest.writeString(manaSex);
        dest.writeString(accountNo);
        dest.writeString(tel);
        dest.writeString(idCardPic);
        dest.writeString(email);
        dest.writeString(manaName);
    }
}
