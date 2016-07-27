package com.youngcreate.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.youngcreate.gitdroid.R;

/**
 * Created by Administrator on 16-7-27.
 */
public class LeftPager extends FrameLayout {
    public LeftPager(Context context) {
        this(context, null);
    }

    public LeftPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LeftPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0, this, true);
    }
}
