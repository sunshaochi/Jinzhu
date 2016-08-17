package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/8/17.
 */
public class CreditOfflineUploadEntity {

    /**
     * orderId : 001c1f0445ec11e6b85500163e0e011c
     * id : REJECT
     * name : 缺少必要照片
     * imageUrl : 照片url
     */

    private String orderId;
    private String id;
    private String name;
    private String imageUrl;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
