package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CreditManager implements Parcelable {
    public String getRcHeadPic() {
        return rcHeadPic;
    }

    public void setRcHeadPic(String rcHeadPic) {
        this.rcHeadPic = rcHeadPic;
    }

    private String accountId ;//关联账号
    private String accountNo;//银行账号
    private String branchName;//分行名称
    private String createPersonId;//创建人
    private String createTime;//创建时间
    private String creditManagerId;
    private String detailAddr;//详细地址
    private String email;//邮箱
    private String idCardPic;//身份证图片地址
    private String idNo;//身份证号
    private String jobNum;//工号
    private String manaName;//姓名
    private String manaSex;//性别
    private String manaTel;//电话
    private String modifyPersonId;//修改人
    private String modifyTime;//修改时间
    private String orgId;//机构
    private String wkCardPic;//工作证图片
    private String province;//省
    private String city;//市
    private String district;//区
    private int isValid;//是否有效 默认为1,1有效，0无效
    private String rcHeadPic;//信贷经理头像
    private static  long serialVersionUID;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreditManagerId() {
        return creditManagerId;
    }

    public void setCreditManagerId(String creditManagerId) {
        this.creditManagerId = creditManagerId;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getManaName() {
        return manaName;
    }

    public void setManaName(String manaName) {
        this.manaName = manaName;
    }

    public String getManaSex() {
        return manaSex;
    }

    public void setManaSex(String manaSex) {
        this.manaSex = manaSex;
    }

    public String getManaTel() {
        return manaTel;
    }

    public void setManaTel(String manaTel) {
        this.manaTel = manaTel;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getWkCardPic() {
        return wkCardPic;
    }

    public void setWkCardPic(String wkCardPic) {
        this.wkCardPic = wkCardPic;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        CreditManager.serialVersionUID = serialVersionUID;
    }

    public CreditManager() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountId);
        dest.writeString(this.accountNo);
        dest.writeString(this.branchName);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTime);
        dest.writeString(this.creditManagerId);
        dest.writeString(this.detailAddr);
        dest.writeString(this.email);
        dest.writeString(this.idCardPic);
        dest.writeString(this.idNo);
        dest.writeString(this.jobNum);
        dest.writeString(this.manaName);
        dest.writeString(this.manaSex);
        dest.writeString(this.manaTel);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.orgId);
        dest.writeString(this.wkCardPic);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeInt(this.isValid);
        dest.writeString(this.rcHeadPic);
    }

    protected CreditManager(Parcel in) {
        this.accountId = in.readString();
        this.accountNo = in.readString();
        this.branchName = in.readString();
        this.createPersonId = in.readString();
        this.createTime = in.readString();
        this.creditManagerId = in.readString();
        this.detailAddr = in.readString();
        this.email = in.readString();
        this.idCardPic = in.readString();
        this.idNo = in.readString();
        this.jobNum = in.readString();
        this.manaName = in.readString();
        this.manaSex = in.readString();
        this.manaTel = in.readString();
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.orgId = in.readString();
        this.wkCardPic = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.isValid = in.readInt();
        this.rcHeadPic = in.readString();
    }

    public static final Creator<CreditManager> CREATOR = new Creator<CreditManager>() {
        public CreditManager createFromParcel(Parcel source) {
            return new CreditManager(source);
        }

        public CreditManager[] newArray(int size) {
            return new CreditManager[size];
        }
    };
}
