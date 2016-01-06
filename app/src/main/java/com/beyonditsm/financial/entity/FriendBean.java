package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 好友实体类
 * Created by Yang on 2015/12/20 0020.
 */
@Table(name="friend_table")
public class FriendBean implements Parcelable{

    @Column
    private String user_id;

    @Column
    private String user_head;

    @Column
    private String user_name;

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String userName) {
        this.user_name = userName;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getUserHead() {
        return user_head;
    }

    public void setUserHead(String userHead) {
        this.user_head = userHead;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.user_head);
        dest.writeString(this.user_name);
    }

    public FriendBean() {
    }

    protected FriendBean(Parcel in) {
        this.user_id = in.readString();
        this.user_head = in.readString();
        this.user_name = in.readString();
    }

    public static final Creator<FriendBean> CREATOR = new Creator<FriendBean>() {
        public FriendBean createFromParcel(Parcel source) {
            return new FriendBean(source);
        }

        public FriendBean[] newArray(int size) {
            return new FriendBean[size];
        }
    };
}
