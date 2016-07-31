package com.youngcreate.gitdroid.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.commons.ActivityUtils;
import com.youngcreate.gitdroid.components.FooterView;
import com.youngcreate.gitdroid.hotrepo.repolist.RepoListPresenter;
import com.youngcreate.gitdroid.hotrepo.repolist.view.RepoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 16-7-28.
 */
public class RepoListFragment extends Fragment implements RepoListView {

    @BindView(R.id.lvRepos)
    ListView listView;

    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;


    private ArrayAdapter<String> adapter;

    private RepoListPresenter repoListPresenter;
    private ActivityUtils activityUtils;
    private FooterView footerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        activityUtils=new ActivityUtils(this);
        repoListPresenter = new RepoListPresenter(this);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());

        listView.setAdapter(adapter);

        initPullToRefresh();
        initLoadMoreScroll();
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            //listView,滚动到底部，将触发此方法
            @Override
            public void onLoadMore() {
                repoListPresenter.loadMore();
            }

            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();
    }

    private void initPullToRefresh() {
        //记录上一次的刷新时间，如果两次下拉时间太近，将不会触发
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        //关闭header所用时长
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        //下拉刷新监听处理
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //去做具体业务
                repoListPresenter.refresh();
            }
        });

        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + "JAVA");
        header.setPadding(0, 60, 0, 60);
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
        ptrClassicFrameLayout.addPtrUIHandler(header);

    }

    //下拉刷新的方法---------------------------------------------------------

    //显示内容or错误or空白，三选一

    //显示提示信息

    @Override
    public void showContentView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);


    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void stopRefresh() {
        ptrClassicFrameLayout.refreshComplete();
    }

    //刷新数据
    @Override
    public void refreshData(List<String> data) {
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    //上拉加载更多的方法---------------------------------------------------------

    @Override
    public void showLoadMoreLoading() {
        if(listView.getFooterViewsCount()==0){
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMore() {
       listView.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreError(String errorMsg) {
        if(listView.getFooterViewsCount()==0){
            listView.addFooterView(footerView);
        }
       footerView.showError(errorMsg);
    }

    @Override
    public void addMoreData(List<String> datas) {
       adapter.addAll(datas);
        adapter.notifyDataSetChanged();
    }

}
