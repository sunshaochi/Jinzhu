package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HotProductEntity implements Parcelable{
    /**
     * total : 2
     * rows : [{"d7name":"无车","d6name":"无房产","monthlyRathMin":1,"monthlyRathAvg":1.5,"productName":"微时贷","joinSts":"1","orgType":"银行","modifyTime":1449477893000,"maxVal":3000,"payType":"2c908c6e50d077f80150d08048b3000a","minVal":1000,"assuranceType":"2c909d245115183b01511525d61d0006","d9name":null,"monthlyRathMax":2,"d5name":"上班族","orgName":"平安银行","productId":"2c908c7e517b9cc001517b9d6a4e0000","mortgageType":"2c909d245115183b01511523c1f90003","_mortgageType":"信用卡","logoPath":"","imageLogoPath":"","applyCondition":"有六个月以上贷款或信用卡还款记录，且信用记录良好","accountId":"2c908c715141b924015141ba7e500000","d8name":null,"productAmount":1,"detailDescribe":"啊","monthlyRate":1.15,"timeMaxVal":12,"publishSts":"PUBLISHED","modifyPerson":"4028b88150b222f90150b22cfac20005","orgId":"2c908c7e51418f64015141ba99650007","propertyType":"2c908c6e50d077f80150d082348a000f","applyMaterial":"啊","productNo":"001","costDescribe":"30000","_payType":"分期还款","jobIdentity":"2c908c6e50d077f80150d08088dd000b","carStatus":"2c908c6e50d077f80150d0861aa6001b","_assuranceType":"保证","joinTime":1447383505000,"isValid":1,"createPerson":"4028b88150b222f90150b22cfac20005","creditStatus":"","succeedApplyCount":null,"timeMinVal":1,"productChara":"","createTime":1449477893000,"taskManageId":"2c908c7e5156757b015157a232890073","reputationLevel":null,"loanPeriod":1}]
     */

    private int total;
    /**
     * d7name : 无车
     * d6name : 无房产
     * monthlyRathMin : 1.0
     * monthlyRathAvg : 1.5
     * productName : 微时贷
     * joinSts : 1
     * orgType : 银行
     * modifyTime : 1449477893000
     * maxVal : 3000.0
     * payType : 2c908c6e50d077f80150d08048b3000a
     * minVal : 1000.0
     * assuranceType : 2c909d245115183b01511525d61d0006
     * d9name : null
     * monthlyRathMax : 2.0
     * d5name : 上班族
     * orgName : 平安银行
     * productId : 2c908c7e517b9cc001517b9d6a4e0000
     * mortgageType : 2c909d245115183b01511523c1f90003
     * _mortgageType : 信用卡
     * logoPath :
     * imageLogoPath :
     * applyCondition : 有六个月以上贷款或信用卡还款记录，且信用记录良好
     * accountId : 2c908c715141b924015141ba7e500000
     * d8name : null
     * productAmount : 1.0
     * detailDescribe : 啊
     * monthlyRate : 1.15
     * timeMaxVal : 12
     * publishSts : PUBLISHED
     * modifyPerson : 4028b88150b222f90150b22cfac20005
     * orgId : 2c908c7e51418f64015141ba99650007
     * propertyType : 2c908c6e50d077f80150d082348a000f
     * applyMaterial : 啊
     * productNo : 001
     * costDescribe : 30000
     * _payType : 分期还款
     * jobIdentity : 2c908c6e50d077f80150d08088dd000b
     * carStatus : 2c908c6e50d077f80150d0861aa6001b
     * _assuranceType : 保证
     * joinTime : 1447383505000
     * isValid : 1
     * createPerson : 4028b88150b222f90150b22cfac20005
     * creditStatus :
     * succeedApplyCount : null
     * timeMinVal : 1
     * productChara :
     * createTime : 1449477893000
     * taskManageId : 2c908c7e5156757b015157a232890073
     * reputationLevel : null
     * loanPeriod : 1
     */

    private List<HotProductInfo> rows;

    protected HotProductEntity(Parcel in) {
        total = in.readInt();
    }

    public static final Creator<HotProductEntity> CREATOR = new Creator<HotProductEntity>() {
        @Override
        public HotProductEntity createFromParcel(Parcel in) {
            return new HotProductEntity(in);
        }

        @Override
        public HotProductEntity[] newArray(int size) {
            return new HotProductEntity[size];
        }
    };

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(List<HotProductInfo> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public List<HotProductInfo> getRows() {
        return rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
    }

}
