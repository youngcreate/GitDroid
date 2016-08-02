package com.youngcreate.gitdroid.hotrepo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.youngcreate.gitdroid.hotrepo.repolist.RepoListFragment;

import java.util.List;

/**
 * Created by Administrator on 16-7-28.
 */
public class HotRepoAdapter extends FragmentPagerAdapter {

    private List<Language> languages;

    public HotRepoAdapter(FragmentManager fm, Context context) {
        super(fm);
        languages = Language.getDefaultLanguages(context);

    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
