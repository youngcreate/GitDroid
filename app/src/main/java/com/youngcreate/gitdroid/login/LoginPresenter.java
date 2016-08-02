package com.youngcreate.gitdroid.login;

import com.youngcreate.gitdroid.commons.LogUtils;
import com.youngcreate.gitdroid.login.model.AccessTokenResult;
import com.youngcreate.gitdroid.login.model.User;
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
    private Call<User> userCall;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String code) {
        loginView.showProgress();
        if (tokenCall != null) {
            tokenCall.cancel();
        }
        tokenCall = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        tokenCall.enqueue(tokenCallBack);
    }

    private Callback<AccessTokenResult> tokenCallBack = new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            //响应结果
            AccessTokenResult tokenResult = response.body();
            String token = tokenResult.getAccessToken();
            //缓存token
            UserRepo.setAccessToken(token);

            if (userCall != null) {
                userCall.cancel();
            }
            userCall = GitHubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallBack);

        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };

    private Callback<User> userCallBack = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            //缓存user
            UserRepo.setUser(user);
            loginView.showMessage("登陆成功");
            loginView.navigateToMain();

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };
}
