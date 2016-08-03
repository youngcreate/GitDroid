package com.youngcreate.gitdroid.github.login.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 16-8-2.
 */
public class User implements Serializable{
    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("name")
    private String name;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
