package com.youngcreate.gitdroid.hotrepo.repolist;

import android.os.AsyncTask;

import com.youngcreate.gitdroid.commons.LogUtils;
import com.youngcreate.gitdroid.hotrepo.repolist.view.RepoListView;
import com.youngcreate.gitdroid.network.GitHubApi;
import com.youngcreate.gitdroid.network.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-28.
 */
public class RepoListPresenter {
    private RepoListView repoListView;
    private int count;

    public RepoListPresenter(RepoListView repoListView) {
        this.repoListView = repoListView;
    }

    //下拉刷新
    public void refresh() {
        //        new RefreshTask().execute();
        GitHubClient gitHubClient = new GitHubClient();
        GitHubApi gitHubApi = gitHubClient.getGitHubApi();
        Call<ResponseBody> call = gitHubApi.gitHub();
        call.enqueue(refreshCallback);
    }

    private final Callback<ResponseBody> refreshCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoListView.stopRefresh();
            //200-299
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    repoListView.showMessage("unknown error");
                    return;
                }
                repoListView.showContentView();
            } else {
                //401未授权
                repoListView.showMessage("code:" + response.code());
            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoListView.stopRefresh();
            repoListView.showContentView();
        }
    };

    //上拉加载更多
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        new loadMoreTask().execute();
    }


    final class RefreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for (int a = 0; a < 20; a++) {
                datas.add("测试数据" + count++);
            }
            repoListView.stopRefresh();
            repoListView.refreshData(datas);
            repoListView.showContentView();
        }
    }

    final class loadMoreTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for (int a = 0; a < 20; a++) {
                datas.add("测试数据" + count++);
            }
            repoListView.addMoreData(datas);
            repoListView.hideLoadMore();

        }
    }
}
