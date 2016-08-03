package com.youngcreate.gitdroid.github.hotrepo.repolist.model;

import com.google.gson.annotations.SerializedName;
import com.youngcreate.gitdroid.github.login.model.User;

import java.io.Serializable;


/**
 * Created by Administrator on 16-8-2.
 */
public class Repo implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("owner")
    private User owner;

    @SerializedName("description")
    private String description;

    @SerializedName("stargazers_count")
    private int starCount;

    @SerializedName("forks_count")
    private int forkCount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public User getOwner() {
        return owner;
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
}
