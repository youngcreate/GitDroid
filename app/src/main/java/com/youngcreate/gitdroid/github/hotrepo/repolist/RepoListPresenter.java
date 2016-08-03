package com.youngcreate.gitdroid.github.hotrepo.repolist;

import com.youngcreate.gitdroid.github.hotrepo.Language;
import com.youngcreate.gitdroid.github.hotrepo.repolist.model.Repo;
import com.youngcreate.gitdroid.github.hotrepo.repolist.model.RepoResult;
import com.youngcreate.gitdroid.github.hotrepo.repolist.view.RepoListView;
import com.youngcreate.gitdroid.github.network.GitHubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-28.
 */
public class RepoListPresenter {
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;

    private Call<RepoResult> repoResultCall;

    public RepoListPresenter(RepoListView repoListView, Language language) {
        this.repoListView = repoListView;
        this.language = language;
    }

    //下拉刷新
    public void refresh() {
        //隐藏加载更多
        repoListView.hideLoadMore();
        repoListView.showContentView();
        //永远刷新最新数据
        nextPage = 1;
        if (repoResultCall != null) {
            repoResultCall.cancel();

        }
        repoResultCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        repoResultCall.enqueue(repoCallBack);
    }

    //上拉加载更多
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        if (repoResultCall != null) {
            repoResultCall.cancel();

        }
        repoResultCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        repoResultCall.enqueue(loadMoreCallBack);

    }

    private Callback<RepoResult> loadMoreCallBack = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMore();
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showLoadMoreError("结果为空！");
                return;
            }
            //取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.addMoreData(repoList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoListView.hideLoadMore();
            repoListView.showMessage("loadMoreCallBack onFailure" + t.getMessage());
        }
    };

    private Callback<RepoResult> repoCallBack = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.stopRefresh();
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showErrorView("结果为空！");
                return;
            }
            //当前搜索的语音，没有仓库
            if (repoResult.getTotalCount() <= 0) {
                repoListView.refreshData(null);
                repoListView.showEmptyView();
                return;
            }
            //取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.refreshData(repoList);
            //下拉刷新成功（1），下一页则更新为2
            nextPage = 2;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoListView.stopRefresh();
            repoListView.showMessage("repoCallBack onFailure" + t.getMessage());
        }
    };


}
