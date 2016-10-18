package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/18.
 */

public class VendorEntity {

    /**
     * name : L合肥二部
     * telephone : null
     * id : 835
     * addr : null
     */

    private String name;
    private Object telephone;
    private int id;
    private Object addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getTelephone() {
        return telephone;
    }

    public void setTelephone(Object telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getAddr() {
        return addr;
    }

    public void setAddr(Object addr) {
        this.addr = addr;
    }
}
