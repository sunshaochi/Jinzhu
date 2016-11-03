package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class ActicleListBean implements Parcelable {
    private long createTime;
    private String articleId;
    private String url;
//    private int remark;
    private int sort;
    private String title;
    private String type;
    private String content;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //    public int getRemark() {
//        return remark;
//    }
//
//    public void setRemark(int remark) {
//        this.remark = remark;
//    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected ActicleListBean(Parcel in) {
        createTime = in.readLong();
        articleId = in.readString();
        url = in.readString();
//        remark = in.readInt();
        sort = in.readInt();
        title = in.readString();
        type = in.readString();
        content = in.readString();
    }

    public static final Creator<ActicleListBean> CREATOR = new Creator<ActicleListBean>() {
        @Override
        public ActicleListBean createFromParcel(Parcel in) {
            return new ActicleListBean(in);
        }

        @Override
        public ActicleListBean[] newArray(int size) {
            return new ActicleListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(createTime);
        parcel.writeString(articleId);
        parcel.writeString(url);
//        parcel.writeInt(remark);
        parcel.writeInt(sort);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(content);
    }
}
