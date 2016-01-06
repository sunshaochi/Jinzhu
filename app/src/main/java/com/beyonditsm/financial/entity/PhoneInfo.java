package com.beyonditsm.financial.entity;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

/**
 * Created by gxy on 2015/11/25.
 */
public class PhoneInfo {
    private String name;//姓名
    private String phoneNumber;//电话号码
    private Bitmap Image;//
    private String sortLetters;  //显示数据拼音的首字母

    public PhoneInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public PhoneInfo() {

    }

    public PhoneInfo(String name, String phoneNumber, Bitmap image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        Image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }


}
