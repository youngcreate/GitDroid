package com.youngcreate.gitdroid.github;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.commons.ActivityUtils;
import com.youngcreate.gitdroid.github.hotrepo.HotRepoFragment;
import com.youngcreate.gitdroid.github.hotuser.HotUserFragment;
import com.youngcreate.gitdroid.github.login.LoginActivity;
import com.youngcreate.gitdroid.github.login.UserRepo;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;

    private Button btnLogin;
    private ImageView ivIcon;

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (UserRepo.isEmpty()) {
            // 登录
            btnLogin.setText(R.string.login_github);
            return;
        }
        // 切换帐号
        btnLogin.setText(R.string.switch_account);
        //设置Title
        getSupportActionBar().setTitle(UserRepo.getUser().getName());
        //设置用户头像
        String photoUrl = UserRepo.getUser().getAvatarUrl();
        ImageLoader.getInstance().displayImage(photoUrl, ivIcon);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.isChecked()){
            item.setChecked(false);
        }

        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;

            case R.id.github_hot_coder:
                if (hotUserFragment == null) {
                    hotUserFragment = new HotUserFragment();
                }
                if (!hotUserFragment.isAdded()) {
                    replaceFragment(hotUserFragment);
                }
                break;
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        return true;
    }
}