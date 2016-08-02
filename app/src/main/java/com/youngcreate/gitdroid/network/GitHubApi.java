package com.youngcreate.gitdroid.network;

import com.youngcreate.gitdroid.hotrepo.repolist.model.RepoResult;
import com.youngcreate.gitdroid.login.model.AccessTokenResult;
import com.youngcreate.gitdroid.login.model.User;
import com.youngcreate.gitdroid.repoinfo.RepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 16-7-31.
 */
public interface GitHubApi {


    String CALL_BACK = "feicui";
    String CLIENT_ID = "88c44e3c5826ce8fa0a9";
    String CLIENT_SECRET = "119ac484d707167621406f5cc93a0b7418553b05";
    String OAUTH_SCOPE = "user,public_repo,repo";
    //授权登陆页面（用WebView来加载）
    String OAUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + OAUTH_SCOPE;

    /**
     * 获取Token Api
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(@Field("client_id") String clientId,
                                          @Field("client_secret") String clientSecret,
                                          @Field("code") String code);

    /**
     * 获取用户信息
     */
    @GET("user")
    Call<User> getUserInfo();


    /**
     * 获取仓库信息
     *
     * @param query  查询参数（language:java）
     * @param pageId 查询页数据（从1开始）
     */
    @GET("search/repositories")
    Call<RepoResult> searchRepos(@Query("q") String query, @Query("page") int pageId);


    /**
     * 获取README
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容，将是markdown格式，且做了base64处理
     */
    @GET("repos/{owner}/{repo}/readme")
   Call<RepoContentResult> getReadme(@Path("owner") String owner,@Path("repo") String repo);


    /**
     * 获取一个markdown内容对应的HTML页面
     * @param body 请求体
     */
    @Headers({"Content-Type:text/plain"})
    @POST("markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

}
