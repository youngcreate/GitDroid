package com.youngcreate.gitdroid.github.hotrepo.repolist.view;

import com.youngcreate.gitdroid.github.hotrepo.repolist.model.Repo;

import java.util.List;

/**
 * Created by Administrator on 16-7-30.
 */
public interface RepoListPtrView {

    void showContentView();

    void showErrorView(String errorMsg);

    void showEmptyView();

    void showMessage(String msg);

    void stopRefresh();

    void refreshData(List<Repo> data);
}
