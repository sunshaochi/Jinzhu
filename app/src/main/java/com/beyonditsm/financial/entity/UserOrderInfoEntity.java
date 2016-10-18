package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/18.
 */

public class UserOrderInfoEntity {

    /**
     * orderId : 12ddwd
     * idcardFront : asdasd
     * idcardBack : asdasd
     * storeCity : asdasd
     * storeId : asdasd
     * storeAddr : asdasd
     */

    private String orderId;
    private String idcardFront;
    private String idcardBack;
    private String storeCity;
    private String storeId;
    private String storeAddr;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }
}
