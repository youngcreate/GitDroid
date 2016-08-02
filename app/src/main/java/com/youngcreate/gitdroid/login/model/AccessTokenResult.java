package com.youngcreate.gitdroid.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * 授权登陆响应结果
 * Created by Administrator on 16-8-1.
 */
public class AccessTokenResult {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
