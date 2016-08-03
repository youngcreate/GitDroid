package com.youngcreate.gitdroid.github.hotrepo.repolist.view;

import com.youngcreate.gitdroid.github.hotrepo.repolist.model.Repo;

import java.util.List;

/**
 * Created by Administrator on 16-7-30.
 */
public interface RepoListLmView {

    void showLoadMoreLoading();

    void hideLoadMore();

    void showLoadMoreError(String errorMsg);

    void addMoreData(List<Repo> datas);

}
