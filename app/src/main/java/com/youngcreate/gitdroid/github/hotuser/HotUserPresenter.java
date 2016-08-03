package com.youngcreate.gitdroid.github.hotuser;

import com.youngcreate.gitdroid.github.login.model.User;
import com.youngcreate.gitdroid.github.network.GitHubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-8-3.
 */
public class HotUserPresenter {

    //热门用户视图接口
    public interface HotUserView {

        void showRefreshView();

        void hideRefreshView();

        void refreshData(List<User> datas);

        void showLoadMoreView();

        void hideLoadMoreView();

        void addMoreData(List<User> datas);
    }

    public HotUserPresenter(HotUserView hotUserView) {
        this.hotUserView = hotUserView;
    }

    private HotUserView hotUserView;
    private Call<UsersResult> usersResultCall;
    private int nextPage = 0;

    /**
     * 下拉刷新
     */
    public void refresh() {
        hotUserView.hideLoadMoreView();
        hotUserView.showRefreshView();
        nextPage = 1;
        if (usersResultCall != null) {
            usersResultCall.cancel();
        }
        usersResultCall = GitHubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersResultCall.enqueue(ptrCallBack);
    }

    /**
     * 上拉加载更多
     */
    public void loadMore() {
        hotUserView.showLoadMoreView();
        if (usersResultCall != null) {
            usersResultCall.cancel();
        }
        usersResultCall = GitHubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersResultCall.enqueue(loadMoreCallBack);
    }

    private Callback<UsersResult> ptrCallBack = new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            hotUserView.hideRefreshView();
            //得到响应结果
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                return;
            }
            //将列表数据交付给“视图”去处理
            hotUserView.refreshData(usersResult.getUserList());
            nextPage = 2;

        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {

        }
    };

    private Callback<UsersResult> loadMoreCallBack = new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            hotUserView.hideLoadMoreView();
            //得到响应结果
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                return;
            }
            //将列表数据交付给“视图”去处理
            hotUserView.addMoreData(usersResult.getUserList());
            nextPage++;
        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {

        }
    };
}
