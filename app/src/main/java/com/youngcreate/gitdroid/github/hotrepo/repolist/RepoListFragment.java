package com.youngcreate.gitdroid.github.hotrepo.repolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.commons.ActivityUtils;
import com.youngcreate.gitdroid.components.FooterView;
import com.youngcreate.gitdroid.favorite.DAO.DBHelp;
import com.youngcreate.gitdroid.favorite.DAO.LocalRepoDao;
import com.youngcreate.gitdroid.favorite.model.LocalRepo;
import com.youngcreate.gitdroid.github.hotrepo.Language;
import com.youngcreate.gitdroid.github.hotrepo.repolist.model.Repo;
import com.youngcreate.gitdroid.github.hotrepo.repolist.model.RepoConverter;
import com.youngcreate.gitdroid.github.hotrepo.repolist.view.RepoListView;
import com.youngcreate.gitdroid.github.repoinfo.RepoInfoActivity;

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

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LANGUAGE, language);
        fragment.setArguments(args);
        return fragment;
    }

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @BindView(R.id.lvRepos)
    ListView listView;

    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;


    private RepoListAdapter adapter;
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
        activityUtils = new ActivityUtils(this);
        repoListPresenter = new RepoListPresenter(this, getLanguage());

        adapter = new RepoListAdapter();
        listView.setAdapter(adapter);
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);
            }
        });
        //长按某个仓库后，加入收藏
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //热门仓库列表上的Repo
                Repo repo = adapter.getItem(position);
                //将Repo转为LocalRepo
                LocalRepo localRepo = RepoConverter.convert(repo);
                //添加到本地仓库表中去（只认本地仓库实体LocalRepo）
                new LocalRepoDao(DBHelp.getInstance(getContext())).createOrUpdate(localRepo);
                activityUtils.showToast("收藏成功");
                return true;
            }
        });


        //
        initPullToRefresh();
        initLoadMoreScroll();
        if (adapter.getCount() == 0) {
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
    public void refreshData(List<Repo> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    //上拉加载更多的方法---------------------------------------------------------

    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
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
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(errorMsg);
    }

    @Override
    public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }

}
