package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/9.
 */

public class HotNewsEntity{

    /**
     * createTime : 1475218743000
     * pictrue : 070a6ad4-cfea-439d-bff4-e6f97da5532e.png
     * description : <p>lksddklsklklfsf</p>
     * id : 5ef0b21b86db11e696d760eb69a5af72
     * title : datasource2
     * weights : 1
     * urlPath : kkdl
     */

    private long createTime;
    private String pictrue;
    private String description;
    private String id;
    private String title;
    private int weights;
    private String urlPath;

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

}
