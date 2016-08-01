package com.youngcreate.gitdroid.network;

import com.youngcreate.gitdroid.login.AccessTokenResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 16-7-31.
 */
public interface GitHubApi {


    String CALL_BACK = "feicui";
    String CLIENT_ID = "88c44e3c5826ce8fa0a9";
    String CLIENT_SECRET="119ac484d707167621406f5cc93a0b7418553b05";
    String OAUTH_SCOPE = "user,public_repo,repo";
    //授权登陆页面（用WebView来加载）
    String OAUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + OAUTH_SCOPE;



    //获取Token Api
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(@Field("client_id") String clientId,
                       @Field("client_secret") String clientSecret,
                       @Field("code") String code);

}
