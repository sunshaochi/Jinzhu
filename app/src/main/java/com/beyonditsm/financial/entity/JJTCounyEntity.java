package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/20.
 */

public class JJTCounyEntity {

    /**
     * name : 西宁市
     * id : 8000803587
     * category : 11
     * parentId : 8000803586
     * desc : null
     */

    private String name;
    private long id;
    private int category;
    private long parentId;
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
}
