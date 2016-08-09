package com.youngcreate.gitdroid.github.hotrepo.repolist.model;

import android.support.annotation.NonNull;

import com.youngcreate.gitdroid.favorite.model.LocalRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 将Repo（热门仓库）转换为LocalRepo(本地仓库）对象，为了实现仓库的收藏功能，默认为未分类
 * Created by Administrator on 16-8-5.
 */
public class RepoConverter {

    private RepoConverter() {

    }

    public static LocalRepo convert(@NonNull Repo repo) {
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatarUrl());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStarCount(repo.getStarCount());
        localRepo.setForkCount(repo.getForkCount());
        localRepo.setRepoGroup(null);
        return localRepo;
    }

    public static List<LocalRepo> convertAll(@NonNull List<Repo> repos){
        ArrayList<LocalRepo> localRepos=new ArrayList<LocalRepo>();
        for(Repo repo:repos){
            localRepos.add(convert(repo));
        }
        return localRepos;

    }

}
