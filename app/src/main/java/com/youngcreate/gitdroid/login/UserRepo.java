package com.youngcreate.gitdroid.login;

import com.youngcreate.gitdroid.login.model.User;

/**
 * Created by Administrator on 16-8-2.
 */
public class UserRepo {
    private UserRepo() {
    }

    private static String accessToken;
    private static User user;

    //当前是否有token
    public static boolean hasAccessToken() {
        return accessToken != null;
    }

    //当前是否是“空的”（还没有登陆）
    public static boolean isEmpty() {
        return accessToken == null || user == null;
    }

    //清除信息
    public static void clear() {
        accessToken = null;
        user = null;
    }


    public static void setAccessToken(String accessToken) {
        UserRepo.accessToken = accessToken;
    }

    public static void setUser(User user) {
        UserRepo.user = user;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static User getUser() {
        return user;
    }
}
