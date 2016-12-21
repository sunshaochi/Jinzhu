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
    private String loanPeriodType;//   贷款期限类型(1.年2.月3.周4.日5.期数)
    private String minLoanPeriod;//  最低贷款期限
    private String maxLoanPeriod;//   最高贷款期限
    private String applyMaterialDesc;//   申请材料说明
    private String address;//   详细地址


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

    public String getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(String loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public String getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(String minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public String getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    public void setMaxLoanPeriod(String maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
    }

    public String getApplyMaterialDesc() {
        return applyMaterialDesc;
    }

    public void setApplyMaterialDesc(String applyMaterialDesc) {
        this.applyMaterialDesc = applyMaterialDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public OrderListBean() {
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
        dest.writeString(this.loanPeriodType);
        dest.writeString(this.minLoanPeriod);
        dest.writeString(this.maxLoanPeriod);
        dest.writeString(this.applyMaterialDesc);
        dest.writeString(this.address);
    }

    protected OrderListBean(Parcel in) {
        this.order = in.readParcelable(OrderBean3.class.getClassLoader());
        this.product = in.readParcelable(ProductBean2.class.getClassLoader());
        this.customer = in.readParcelable(CustomerBean.class.getClassLoader());
        this.OrderWorkMark = in.createTypedArrayList(OrderWorkMarkBean.CREATOR);
        this.folwmaps = in.createTypedArrayList(FolwmapsBean.CREATOR);
        this.Attachmentlist = in.createTypedArrayList(AttachmentlistBean.CREATOR);
        this.loanPeriodType = in.readString();
        this.minLoanPeriod = in.readString();
        this.maxLoanPeriod = in.readString();
        this.applyMaterialDesc = in.readString();
        this.address = in.readString();
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
