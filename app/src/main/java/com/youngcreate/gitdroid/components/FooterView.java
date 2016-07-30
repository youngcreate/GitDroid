package com.youngcreate.gitdroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youngcreate.gitdroid.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 上拉功能
 * <p/>
 * 此视图考虑三种不同状态：
 * 展示loading进度条
 * 显示错误
 * 显示没有更多数据
 * <p/>
 * Created by Administrator on 16-7-30.
 */
public class FooterView extends FrameLayout {

    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;
    private int state = STATE_LOADING;

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.tv_complete)TextView tvComplete;
    @BindView(R.id.tv_error)TextView tvError;


    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_load_footer, this, true);
        ButterKnife.bind(this);

    }

    public boolean isLoading(){
        return state==STATE_LOADING;
    }

    public void showLoading(){
        state=STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    public void showComplete(){
        state=STATE_COMPLETE;
        progressBar.setVisibility(View.GONE);
        tvComplete.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    public void showError(String errorMsg){
        state=STATE_ERROR;
        tvComplete.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
    }

    public void setErrorOnClickListener(OnClickListener onClickListener){
        tvError.setOnClickListener(onClickListener);
    }


}
