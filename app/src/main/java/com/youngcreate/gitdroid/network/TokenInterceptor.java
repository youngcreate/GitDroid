package com.youngcreate.gitdroid.network;

import com.youngcreate.gitdroid.login.UserRepo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token拦截器
 * Created by Administrator on 16-8-2.
 */
public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        //每次请求，都加一个token值
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (UserRepo.hasAccessToken()) {
            //注意token后带空格
            builder.header("Authorization", "token " + UserRepo.getAccessToken());

        }
        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权的！限制是每分钟10次！");
        }else{
            throw new IOException("响应码："+response.code());
        }

    }
}
