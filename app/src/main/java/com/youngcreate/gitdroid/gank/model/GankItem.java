package com.youngcreate.gitdroid.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Administrator on 16-8-8.
 */
@SuppressWarnings("unused")
public class GankItem {

    @SerializedName("_id")
    private String objectId;

    private Date createAt;

    private String desc;

    private Date publishedAt;

    private String type;

    private String url;

    private boolean used;

    private String who;

    public String getObjectId() {
        return objectId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public String getDesc() {
        return desc;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}
