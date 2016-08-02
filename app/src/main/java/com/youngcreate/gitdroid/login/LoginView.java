package com.youngcreate.gitdroid.login;

/**
 * Created by Administrator on 16-8-2.
 */
public interface LoginView {
    //显示进度
    void showProgress();

    void showMessage(String msg);

    //重置WebView
    void resetWeb();

    //导航切换到Main页面
    void navigateToMain();

}
