package com.youngcreate.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.splash.pager.RightPager;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 16-7-27.
 */
public class SplashPagerFragment extends Fragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.content)
    FrameLayout frameLayout;
    @BindView(R.id.layoutPhone)
    FrameLayout layoutPhone;

    @BindView(R.id.ivPhoneFont)
    ImageView ivPhoneFont;
    @BindColor(R.color.colorGreen)
    int colorGreen;
    @BindColor(R.color.colorRed)
    int colorRed;
    @BindColor(R.color.colorYellow)
    int colorYellow;

    private SplashPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        //添加监听
        viewPager.addOnPageChangeListener(pageColorListener);
        viewPager.addOnPageChangeListener(phoneViewListener);

    }

    //主要做背景颜色渐变处理
    private ViewPager.OnPageChangeListener pageColorListener = new ViewPager.OnPageChangeListener() {
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //第一个页面到第二个页面之间
            if (position == 0) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorGreen, colorRed);
                frameLayout.setBackgroundColor(color);
                return;
            }
            //第二个页面到第三个页面之间
            if (position == 1) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorRed, colorYellow);
                frameLayout.setBackgroundColor(color);
                return;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //主要做手机动画效果处理（平移、缩放、透明度变化）
    private ViewPager.OnPageChangeListener phoneViewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0) {

                //手机的缩放处理
                float scale = 0.3f + positionOffset * 0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);

                //手机的平移处理
                int scroll = (int) ((positionOffset - 1) * 280);
                layoutPhone.setTranslationX(scroll);

                //手机字体的渐变
                ivPhoneFont.setAlpha(positionOffset);
            }

            if (position == 1) {
                layoutPhone.setTranslationX(-positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 2) {
                RightPager pager2View = (RightPager) adapter.getView(position);
                pager2View.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
