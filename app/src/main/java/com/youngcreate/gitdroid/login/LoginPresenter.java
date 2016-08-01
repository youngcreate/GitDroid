package com.youngcreate.gitdroid.login;

import android.widget.Toast;

import com.youngcreate.gitdroid.commons.LogUtils;
import com.youngcreate.gitdroid.network.GitHubApi;
import com.youngcreate.gitdroid.network.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-8-1.
 */
public class LoginPresenter {

    private Call<AccessTokenResult> tokenCall;

    public void login(String code) {
        tokenCall = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        tokenCall.enqueue(callBack);
    }

    private Callback<AccessTokenResult> callBack = new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            //响应结果
            AccessTokenResult tokenResult = response.body();
            String token = tokenResult.getAccessToken();
            LogUtils.d("token=" + token);

        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            LogUtils.d("TokenCallBack onFailure");
        }
    };
}
