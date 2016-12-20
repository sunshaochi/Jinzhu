package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Q164454216
 * Created by xiaoke on 2016/12/18.
 */

public class FolwmapsBean implements Parcelable {
    private List<FlowImageListBean> flowImgList;
    private OrderFlowBean orderFlow;

    public List<FlowImageListBean> getFlowImgList() {
        return flowImgList;
    }

    public void setFlowImgList(List<FlowImageListBean> flowImgList) {
        this.flowImgList = flowImgList;
    }

    public OrderFlowBean getOrderFlow() {
        return orderFlow;
    }

    public void setOrderFlow(OrderFlowBean orderFlow) {
        this.orderFlow = orderFlow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.flowImgList);
        dest.writeParcelable(this.orderFlow, flags);
    }

    public FolwmapsBean() {
    }

    protected FolwmapsBean(Parcel in) {
        this.flowImgList = in.createTypedArrayList(FlowImageListBean.CREATOR);
        this.orderFlow = in.readParcelable(OrderFlowBean.class.getClassLoader());
    }

    public static final Creator<FolwmapsBean> CREATOR = new Creator<FolwmapsBean>() {
        @Override
        public FolwmapsBean createFromParcel(Parcel source) {
            return new FolwmapsBean(source);
        }

        @Override
        public FolwmapsBean[] newArray(int size) {
            return new FolwmapsBean[size];
        }
    };
}
