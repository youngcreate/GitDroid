package com.youngcreate.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 本地仓库
 * Created by Administrator on 16-8-4.
 */
@DatabaseTable(tableName = "local_repo")
public class LocalRepo {

    public static final String COLUMN_GROUP_ID = "group_id";

    @DatabaseField(id = true)
    @SerializedName("id")
    private long id;

    @DatabaseField
    @SerializedName("name")
    private String name;

    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;

    @DatabaseField
    @SerializedName("description")
    private String description;

    @DatabaseField(columnName = "star_count")
    @SerializedName("stargazers_count")
    private int starCount;

    @DatabaseField(columnName = "fork_count")
    @SerializedName("forks_count")
    private int forkCount;

    @DatabaseField(columnName = "avatar_url")
    @SerializedName("avatar_url")
    private String avatar;
    //外键、可以为空
    @DatabaseField(columnName = COLUMN_GROUP_ID, foreign = true, canBeNull = true)
    @SerializedName("group")
    private RepoGroup repoGroup;


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    //获取本地默认仓库数据
    public static List<LocalRepo> getDefaultLocalRepos(Context context) {

        try {
            InputStream inputStream = context.getAssets().open("defaultrepos.json");
            String content = IOUtils.toString(inputStream);
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<List<LocalRepo>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
