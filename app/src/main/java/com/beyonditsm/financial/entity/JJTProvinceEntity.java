package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/19.
 */

public class JJTProvinceEntity {

    /**
     * name : 甘肃
     * id : 8000803472
     * category : 11
     * parentId : null
     * desc : null
     *
     * (新的返回)
     * {"code":"341700",
     * "pinyin":null,
     * "regionId":null,
     * "parentCode":"340000",
     * "createTime":null,
     * "name":"池州市",
     * "updateUser":null
     * ,"createUser":null,
     * "updateTime":null,"
     * sort":null,
     * "isDisabled":1}
     *
     *
     *
     *
     */

    private String pinyin;
    private String regionId;
    private String parentCode;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String sort;
    private int isDisabled;


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    private String name;
    private String id;
    private int category;
    private Object parentId;
    private Object desc;
    /**
     * code : 110000
     * abbr : 北京
     */

    private String code;
    private String abbr;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

}
