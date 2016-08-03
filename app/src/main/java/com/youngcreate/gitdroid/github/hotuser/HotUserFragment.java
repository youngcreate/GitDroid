package com.youngcreate.gitdroid.github.hotuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.commons.ActivityUtils;
import com.youngcreate.gitdroid.components.FooterView;
import com.youngcreate.gitdroid.github.login.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 16-8-3.
 */
public class HotUserFragment extends Fragment implements HotUserPresenter.HotUserView {

    @BindView(R.id.lvRepos)
    ListView listView;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private HotUserAdapter hotUserAdapter;
    private FooterView footerView;
    private ActivityUtils activityUtils;
    private HotUserPresenter hotUserPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        hotUserPresenter = new HotUserPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //
        hotUserAdapter = new HotUserAdapter();
        listView.setAdapter(hotUserAdapter);
        //
        initPullToRefresh();
        initLoadMoreScroll();

        if (hotUserAdapter.getCount() == 0) {
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            }, 200);

        }
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            //listView,滚动到底部，将触发此方法
            @Override
            public void onLoadMore() {
                hotUserPresenter.loadMore();
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
                hotUserPresenter.refresh();
            }
        });

        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I Like Java");
        header.setPadding(0, 60, 0, 60);
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
        ptrClassicFrameLayout.addPtrUIHandler(header);

    }

    @Override
    public void showRefreshView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);

    }

    @Override
    public void hideRefreshView() {
        ptrClassicFrameLayout.refreshComplete();

    }

    @Override
    public void refreshData(List<User> datas) {
        //将datas数据适配显示到listview
        hotUserAdapter.clear();
        hotUserAdapter.addAll(datas);
    }

    @Override
    public void showLoadMoreView() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);

        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMoreView() {
        listView.removeFooterView(footerView);
    }

    @Override
    public void addMoreData(List<User> datas) {
        hotUserAdapter.addAll(datas);
    }
}
