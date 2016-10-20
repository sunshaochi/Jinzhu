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
    private long id;
    private int category;
    private Object parentId;
    private Object desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
