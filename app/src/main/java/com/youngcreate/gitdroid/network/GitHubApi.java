package com.youngcreate.gitdroid.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Administrator on 16-7-31.
 */
public interface GitHubApi {

    @GET("user")
    Call<ResponseBody> gitHub();
}
