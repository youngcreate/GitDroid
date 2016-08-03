package com.youngcreate.gitdroid.github.hotrepo.repolist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 16-8-2.
 */
public class RepoResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<Repo> repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }
}
