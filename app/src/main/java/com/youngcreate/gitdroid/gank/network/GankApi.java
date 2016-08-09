package com.youngcreate.gitdroid.gank.network;

import com.youngcreate.gitdroid.gank.model.GankResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 接口来自代码家的干货集中营：
 * http://gank.io/api
 * Created by Administrator on 16-8-8.
 */
public interface GankApi {

    public static final String ENDPOINT = "http://gank.io/api/";

    @GET("day/{year}/{month}/{day}")
    Call<GankResult> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
