package com.beyonditsm.financial.entity;

import java.util.List;

/**
 * Created by xuleyuan on 2016/8/16.
 */
public class CreditOfflineDetil {

    /**
     * orderId : 001c1f0445ec11e6b85500163e0e011c
     * orderSts : REJECT
     * orderRemark : 缺少必要照片
     * images : [{"id":"4028888a55c8c12c0155c90359a90021","imgUrl":"img/8322821.jpg","name":"身份证正面照","sts":"3"},{"id":"4028888a55c8c12c0155c90359a90022","imgUrl":"img/8322822.jpg","name":"身份证背面照","sts":"3"},{"id":"4028888a55c8c12c0155c90359a90023","imgUrl":"img/8322823.jpg","name":"身份证正面照1","sts":"2"},{"id":"4028888a55c8c12c0155c90359a90024","imgUrl":"img/8322824.jpg","name":"身份证正面照2","sts":"2"},{"id":"4028888a55c8c12c0155c90359a90025","imgUrl":"img/8322825.jpg","name":"身份证正面照3","sts":"2"},{"id":"4028888a55c8c12c0155c90359a90026","imgUrl":"img/8322826.jpg","name":"身份证正面照4","sts":"2"},{"id":"4028888a55c8c12c0155c90359a90027","imgUrl":"img/8322827.jpg","name":"身份证正面照5","sts":"2"},{"id":"4028888a55c8c12c0155c90359a90028","imgUrl":"img/8322828.jpg","name":"身份证正面照6","sts":"2"}]
     */

    private String orderId;
    private String orderSts;
    private String orderRemark;
    /**
     * id : 4028888a55c8c12c0155c90359a90021
     * imgUrl : img/8322821.jpg
     * name : 身份证正面照
     * sts : 3
     */

    private List<ImagesBean> images;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(String orderSts) {
        this.orderSts = orderSts;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        private String id;
        private String imgUrl;
        private String name;
        private String sts;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSts() {
            return sts;
        }

        public void setSts(String sts) {
            this.sts = sts;
        }
    }
}
