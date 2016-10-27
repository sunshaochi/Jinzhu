package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 查询帮助所有条目实体类
 * Created by Administrator on 2016/10/10 0010.
 */

public class HelpAllBean implements Parcelable{

    /**
     * themeId : 2c9086075755498c0157554eda1c0001
     * themeName : 借款人帮助
     * createTime : 1474605014000
     * rankPriority : 4
     */

    private String themeId;
    private String themeName;
    private long createTime;
    private int rankPriority;

    protected HelpAllBean(Parcel in) {
        themeId = in.readString();
        themeName = in.readString();
        createTime = in.readLong();
        rankPriority = in.readInt();
    }

    public static final Creator<HelpAllBean> CREATOR = new Creator<HelpAllBean>() {
        @Override
        public HelpAllBean createFromParcel(Parcel in) {
            return new HelpAllBean(in);
        }

        @Override
        public HelpAllBean[] newArray(int size) {
            return new HelpAllBean[size];
        }
    };

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRankPriority() {
        return rankPriority;
    }

    public void setRankPriority(int rankPriority) {
        this.rankPriority = rankPriority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(themeId);
        parcel.writeString(themeName);
        parcel.writeLong(createTime);
        parcel.writeInt(rankPriority);
    }
}
