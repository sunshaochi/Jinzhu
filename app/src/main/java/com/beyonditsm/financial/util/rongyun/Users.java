package com.beyonditsm.financial.util.rongyun;

import android.os.Parcel;
import android.os.Parcelable;

//import com.sea_monster.common.ParcelUtils;

import java.io.Serializable;

/**
 * Created by Bob_ge on 15/9/7.
 */
public class Users implements Parcelable, Serializable {

    private String userId;

    private String passwd;


    /**
     * 返回码
     */
    private int code;
    /**
     * 错误码 message
     */
    private String message;
    /**
     * 返回信息
     */
    private int result;

    public Users() {

    }

    public Users(Parcel in) {
//        userId = ParcelUtils.readFromParcel(in);
//
//        passwd = ParcelUtils.readFromParcel(in);

    }


    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

//        ParcelUtils.writeToParcel(dest, userId);
//
//        ParcelUtils.writeToParcel(dest, passwd);

    }

    public String getUserId() {

        return userId;

    }

    public void setUserId(String userId) {

        this.userId = userId;

    }


    public String getPasswd() {

        return passwd;

    }

    public void setPasswd(String passwd) {

        this.passwd = passwd;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}