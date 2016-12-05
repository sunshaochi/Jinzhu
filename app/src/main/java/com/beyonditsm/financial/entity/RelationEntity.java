package com.beyonditsm.financial.entity;

/** 月薪发放形式，工作性质，单位性质，亲属关系，学历状况，婚姻状况
 * Created by xuleyuan on 2016/10/18.
 */

public class RelationEntity {
//    {"dictSubId":"1111222",
//            "dictCode":"education",
//            "optionName":"研究生",
//            "optionValue":"6401",
//            "sort":12,
//            "isDisabled":1,
//            "createUser":"admmin",
//            "createTime":1480666896000,
//            "updateUser":"admin",
//            "updateTime":1480666901000}

    private String dictCode;
    private String optionName;

    private String optionValue;
    private String sort;

    private String isDisabled;
    private String createUser;

    private String createTime;
    private String updateUser;

    private String updateTime;
    private String dictSubId;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
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

    public String getDictSubId() {
        return dictSubId;
    }

    public void setDictSubId(String dictSubId) {
        this.dictSubId = dictSubId;
    }

    //之前是原来的字段

    /**
     * name : 父子（女）
     * type : relation
     * id : 2201
     */

    private String name;
    private String type;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
