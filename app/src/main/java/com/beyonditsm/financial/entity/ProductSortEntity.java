package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xuleyuan on 2016/7/14.
 */
public class ProductSortEntity implements Parcelable {

    /**
     * orgId : 2c909d98532c569b01532c5c46650001
     * orgName : 2222
     */

    private List<OrgTypeBean> orgType;
    /**
     * termVal : 1~6个月
     * termKey : ONE_TO_SIX
     */

    private List<LoanTermBean> loanTerm;
    /**
     * moneyVal : 0~5万元
     * moneyKey : ZERO_TO_FIVE
     */

    private List<MoneyScopeBean> moneyScope;
    /**
     * orderKey : MONTHLY_RATE_MAX_ASC
     * orderVal : 月利率从低到高
     */

    private List<ProductOrderBean> productOrder;

    protected ProductSortEntity(Parcel in) {
    }

    public static final Creator<ProductSortEntity> CREATOR = new Creator<ProductSortEntity>() {
        @Override
        public ProductSortEntity createFromParcel(Parcel in) {
            return new ProductSortEntity(in);
        }

        @Override
        public ProductSortEntity[] newArray(int size) {
            return new ProductSortEntity[size];
        }
    };

    public List<OrgTypeBean> getOrgType() {
        return orgType;
    }

    public void setOrgType(List<OrgTypeBean> orgType) {
        this.orgType = orgType;
    }

    public List<LoanTermBean> getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(List<LoanTermBean> loanTerm) {
        this.loanTerm = loanTerm;
    }

    public List<MoneyScopeBean> getMoneyScope() {
        return moneyScope;
    }

    public void setMoneyScope(List<MoneyScopeBean> moneyScope) {
        this.moneyScope = moneyScope;
    }

    public List<ProductOrderBean> getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(List<ProductOrderBean> productOrder) {
        this.productOrder = productOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class OrgTypeBean {
        private String orgId;
        private String orgName;
//
//        "dictSubId": "8aae98285215d02c01521b35c92003b1",
//                "dictCode": "productJop",
//                "optionName": "受薪人士",
//                "optionValue": "1",
//                "sort": 1,
//                "isDisabled": 1,
//                "createUser": null,
//                "createTime": null,
//                "updateUser": null,
//                "updateTime": null

        private String dictSubId;
        private String dictCode;
        private String optionName;
        private String optionValue;
        private int sort;
        private int isDisabled;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;

        public String getDictSubId() {
            return dictSubId;
        }

        public void setDictSubId(String dictSubId) {
            this.dictSubId = dictSubId;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }

    public static class LoanTermBean {
        private String termVal;
        private String termKey;

        private String dictSubId;
        private String dictCode;
        private String optionName;
        private String optionValue;
        private int sort;
        private int isDisabled;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;

        public String getDictSubId() {
            return dictSubId;
        }

        public void setDictSubId(String dictSubId) {
            this.dictSubId = dictSubId;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getTermVal() {
            return termVal;
        }

        public void setTermVal(String termVal) {
            this.termVal = termVal;
        }

        public String getTermKey() {
            return termKey;
        }

        public void setTermKey(String termKey) {
            this.termKey = termKey;
        }
    }

    public static class MoneyScopeBean {
        private String moneyVal;
        private String moneyKey;

        private String dictSubId;
        private String dictCode;
        private String optionName;
        private String optionValue;
        private int sort;
        private int isDisabled;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;

        public String getDictSubId() {
            return dictSubId;
        }

        public void setDictSubId(String dictSubId) {
            this.dictSubId = dictSubId;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getMoneyVal() {
            return moneyVal;
        }

        public void setMoneyVal(String moneyVal) {
            this.moneyVal = moneyVal;
        }

        public String getMoneyKey() {
            return moneyKey;
        }

        public void setMoneyKey(String moneyKey) {
            this.moneyKey = moneyKey;
        }
    }

    public static class ProductOrderBean {
        private String orderKey;
        private String orderVal;

        private String dictSubId;
        private String dictCode;
        private String optionName;
        private String optionValue;
        private int sort;
        private int isDisabled;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;

        public String getDictSubId() {
            return dictSubId;
        }

        public void setDictSubId(String dictSubId) {
            this.dictSubId = dictSubId;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(int isDisabled) {
            this.isDisabled = isDisabled;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOrderKey() {
            return orderKey;
        }

        public void setOrderKey(String orderKey) {
            this.orderKey = orderKey;
        }

        public String getOrderVal() {
            return orderVal;
        }

        public void setOrderVal(String orderVal) {
            this.orderVal = orderVal;
        }
    }
}
