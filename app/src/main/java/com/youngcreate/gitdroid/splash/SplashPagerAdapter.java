package com.youngcreate.gitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.youngcreate.gitdroid.splash.pager.LeftPager;
import com.youngcreate.gitdroid.splash.pager.MiddlePager;
import com.youngcreate.gitdroid.splash.pager.RightPager;

/**
 * Created by Administrator on 16-7-27.
 */
public class SplashPagerAdapter extends PagerAdapter {

    private final View[] views;

    public SplashPagerAdapter(Context context) {
        views = new View[]{
                new LeftPager(context),
                new MiddlePager(context),
                new RightPager(context)};
    }

    public View getView(int position) {
        return views[position];
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position], 0);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
