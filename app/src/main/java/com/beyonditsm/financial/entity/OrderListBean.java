package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class OrderListBean implements Parcelable {
    private OrderBean3 order;
    private ProductBean2 product;
    private CustomerBean customer;
    private List<OrderWorkMarkBean> OrderWorkMark;
    private List<FolwmapsBean> folwmaps;
    private List<AttachmentlistBean> Attachmentlist;

    @Override
    public String toString() {
        return "OrderListBean{" +
                "order=" + order +
                ", product=" + product +
                ", customer=" + customer +
                ", OrderWorkMark=" + OrderWorkMark +
                ", folwmaps=" + folwmaps +
                ", Attachmentlist=" + Attachmentlist +
                '}';
    }

    public List<OrderWorkMarkBean> getOrderWorkMark() {
        return OrderWorkMark;
    }

    public void setOrderWorkMark(List<OrderWorkMarkBean> orderWorkMark) {
        OrderWorkMark = orderWorkMark;
    }

    public List<AttachmentlistBean> getAttachmentlist() {
        return Attachmentlist;
    }

    public void setAttachmentlist(List<AttachmentlistBean> attachmentlist) {
        Attachmentlist = attachmentlist;
    }

    public OrderBean3 getOrder() {
        return order;
    }

    public void setOrder(OrderBean3 order) {
        this.order = order;
    }

    public ProductBean2 getProduct() {
        return product;
    }

    public void setProduct(ProductBean2 product) {
        this.product = product;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }



    public List<FolwmapsBean> getFolwmaps() {
        return folwmaps;
    }

    public void setFolwmaps(List<FolwmapsBean> folwmaps) {
        this.folwmaps = folwmaps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order, flags);
        dest.writeParcelable(this.product, flags);
        dest.writeParcelable(this.customer, flags);
        dest.writeTypedList(this.OrderWorkMark);
        dest.writeTypedList(this.folwmaps);
        dest.writeTypedList(this.Attachmentlist);
    }

    public OrderListBean() {
    }

    protected OrderListBean(Parcel in) {
        this.order = in.readParcelable(OrderBean3.class.getClassLoader());
        this.product = in.readParcelable(ProductBean2.class.getClassLoader());
        this.customer = in.readParcelable(CustomerBean.class.getClassLoader());
        this.OrderWorkMark = in.createTypedArrayList(OrderWorkMarkBean.CREATOR);
        this.folwmaps = in.createTypedArrayList(FolwmapsBean.CREATOR);
        this.Attachmentlist = in.createTypedArrayList(AttachmentlistBean.CREATOR);
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel source) {
            return new OrderListBean(source);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };
}
