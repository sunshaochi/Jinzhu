package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class HelpSecondBean implements Parcelable {

    /**
     * themeName : 平台服务者帮助
     * themeId : 2c908607575112a70157511710870001
     * ActicleList : [{"createTime":1474422036000,"articleId":"650c481d7f9c11e696d760eb69a5af72","remark":2,"sort":16,"title":"如何获取信用报告？","type":"2c908607575112a70157511710870001","content":"大杂烩大杂烩大杂烩"}]
     */

    private String themeName;
    private String themeId;
    /**
     * createTime : 1474422036000
     * articleId : 650c481d7f9c11e696d760eb69a5af72
     * remark : 2
     * sort : 16
     * title : 如何获取信用报告？
     * type : 2c908607575112a70157511710870001
     * content : 大杂烩大杂烩大杂烩
     */

    private List<ActicleListBean> ActicleList;

    protected HelpSecondBean(Parcel in) {
        themeName = in.readString();
        themeId = in.readString();
    }

    public static final Creator<HelpSecondBean> CREATOR = new Creator<HelpSecondBean>() {
        @Override
        public HelpSecondBean createFromParcel(Parcel in) {
            return new HelpSecondBean(in);
        }

        @Override
        public HelpSecondBean[] newArray(int size) {
            return new HelpSecondBean[size];
        }
    };

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public List<ActicleListBean> getActicleList() {
        return ActicleList;
    }

    public void setActicleList(List<ActicleListBean> ActicleList) {
        this.ActicleList = ActicleList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(themeName);
        parcel.writeString(themeId);
    }

}
