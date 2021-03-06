package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/20.
 */

public class JJTUseCityEntity {

    /**
     * name : 台湾市
     * id : 8000803792
     * category : 11
     * parentId : 8000803791
     * desc : null
     */

    private String name;
    private String id;
    private int category;
    private long parentId;
    private Object desc;
    /**
     * code : 110100
     * abbr : null
     * provinceCode : 110000
     */

    private String code;
    private Object abbr;
    private String provinceCode;


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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
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

    public Object getAbbr() {
        return abbr;
    }

    public void setAbbr(Object abbr) {
        this.abbr = abbr;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
