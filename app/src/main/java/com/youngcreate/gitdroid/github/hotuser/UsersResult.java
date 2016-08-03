package com.youngcreate.gitdroid.github.hotuser;

import com.google.gson.annotations.SerializedName;
import com.youngcreate.gitdroid.github.hotrepo.repolist.model.Repo;
import com.youngcreate.gitdroid.github.login.model.User;

import java.util.List;

/**
 * 用户列表响应结果
 * Created by Administrator on 16-8-3.
 */
public class UsersResult {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<User> userList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<User> getUserList() {
        return userList;
    }
}
