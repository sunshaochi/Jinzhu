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
     */

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
