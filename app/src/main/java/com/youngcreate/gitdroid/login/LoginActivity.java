package com.youngcreate.gitdroid.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.network.GitHubApi;


import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

//授权登陆
//1. GitHub会有一个授权url   （用webview）
//2. 同意授权后，将重定向到另一个URL，带出临时授权码code
//3. 用code去授权，得到token值
//4. 使用token就能访问用户接口，得到用户数据

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        loginPresenter=new LoginPresenter();
        initWebView();


    }

    private void initWebView() {
        //删除所有的Cookie，主要为了清除以前的登陆记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //授权登陆URL
        webView.loadUrl(GitHubApi.OAUTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

    }

    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri uri=Uri.parse(url);
            if(GitHubApi.CALL_BACK.equals(uri.getScheme())){
                //获取code
                String code = uri.getQueryParameter("code");
                //用code做登陆业务
                loginPresenter.login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);

        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
          //  super.onProgressChanged(view, newProgress);
            if (newProgress >= 100) {
                gifImageView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }
    };
}
