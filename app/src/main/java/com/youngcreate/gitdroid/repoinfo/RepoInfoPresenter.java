package com.youngcreate.gitdroid.repoinfo;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.youngcreate.gitdroid.hotrepo.repolist.model.Repo;
import com.youngcreate.gitdroid.network.GitHubClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-8-2.
 */
public class RepoInfoPresenter {

    //视图接口
    public interface RepoInfoView {
        void showProgress();

        void hideProgress();

        void showMessage(String msg);

        void setData(String htmlContent);
    }

    public RepoInfoPresenter(@NonNull RepoInfoView repoInfoView) {
        this.repoInfoView = repoInfoView;
    }

    private RepoInfoView repoInfoView;
    private Call<RepoContentResult> repoContentResultCall;
    private Call<ResponseBody> mdHtmlCall;

    public void getReadme(Repo repo) {
        repoInfoView.showProgress();
        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        repoContentResultCall = GitHubClient.getInstance().getReadme(login, name);
        repoContentResultCall.enqueue(repoContentCallBack);

    }

    private Callback<RepoContentResult> repoContentCallBack = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {

            String content = response.body().getContent();
            //做base64解码
            byte[] data = Base64.decode(content, Base64.DEFAULT);
            //根据data获取到markdown(README文件)的HTML文件
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody requestBody = RequestBody.create(mediaType, data);
            if (mdHtmlCall != null) {
                mdHtmlCall.cancel();
            }
            mdHtmlCall = GitHubClient.getInstance().markDown(requestBody);
            mdHtmlCall.enqueue(mdHtmlCallBack);

        }

        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };

    private Callback<ResponseBody> mdHtmlCallBack = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoInfoView.hideProgress();
            try {
                String htmlContent = response.body().string();
                repoInfoView.setData(htmlContent);
            } catch (IOException e) {
                onFailure(call, e);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };
}
