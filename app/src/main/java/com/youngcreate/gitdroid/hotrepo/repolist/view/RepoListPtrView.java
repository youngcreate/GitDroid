package com.youngcreate.gitdroid.hotrepo.repolist.view;

import android.view.View;

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

    void refreshData(List<String> data);
}
