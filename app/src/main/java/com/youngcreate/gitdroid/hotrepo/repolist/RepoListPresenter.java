package com.youngcreate.gitdroid.hotrepo.repolist;

import android.os.AsyncTask;

import com.youngcreate.gitdroid.hotrepo.repolist.view.RepoListView;

import java.util.ArrayList;

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
        new RefreshTask().execute();
    }

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
