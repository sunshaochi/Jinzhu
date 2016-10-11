package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xuleyuan on 2016/10/11.
 */

public class NewsCenterEntity {

    /**
     * total : 10
     * rows : [{"createTime":1475218743000,"pictrue":"070a6ad4-cfea-439d-bff4-e6f97da5532e.png","description":"<p>lksddklsklklfsf<\/p>","id":"5ef0b21b86db11e696d760eb69a5af72","title":"datasource2","weights":1,"urlPath":"kkdl"}]
     */

    private int total;
    /**
     * createTime : 1475218743000
     * pictrue : 070a6ad4-cfea-439d-bff4-e6f97da5532e.png
     * description : <p>lksddklsklklfsf</p>
     * id : 5ef0b21b86db11e696d760eb69a5af72
     * title : datasource2
     * weights : 1
     * urlPath : kkdl
     */

    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean  implements Parcelable{
        private long createTime;
        private String pictrue;
        private String description;
        private String id;
        private String title;
        private int weights;
        private String urlPath;

        protected RowsBean(Parcel in) {
            createTime = in.readLong();
            pictrue = in.readString();
            description = in.readString();
            id = in.readString();
            title = in.readString();
            weights = in.readInt();
            urlPath = in.readString();
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel in) {
                return new RowsBean(in);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWeights() {
            return weights;
        }

        public void setWeights(int weights) {
            this.weights = weights;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(createTime);
            dest.writeString(pictrue);
            dest.writeString(description);
            dest.writeString(id);
            dest.writeString(title);
            dest.writeInt(weights);
            dest.writeString(urlPath);
        }
    }
}
