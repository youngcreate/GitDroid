package com.youngcreate.gitdroid.favorite.DAO;

import com.j256.ormlite.dao.Dao;
import com.youngcreate.gitdroid.favorite.model.LocalRepo;

import java.sql.SQLException;
import java.util.List;

/**
 * 本地仓库DAO
 * Created by Administrator on 16-8-4.
 */
public class LocalRepoDao {

    private Dao<LocalRepo, Long> dao;

    public LocalRepoDao(DBHelp dbHelp) {
        try {
            dao = dbHelp.getDao(LocalRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加或更新本地仓库数据
     */
    public void createOrUpdate(LocalRepo localRepo) {
        try {
            dao.createOrUpdate(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加或更新本地仓库数据
     */
    public void createOrUpdate(List<LocalRepo> localRepos) {
        for (LocalRepo localRepo : localRepos) {
            createOrUpdate(localRepo);

        }
    }

    /**
     * 删除本地仓库数据
     */
    public void delete(LocalRepo localRepo) {
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库（图像处理类别的，架构类别的等）
     */
    public List<LocalRepo> queryForGroupId(int groupId) {
        //构建查询，去指定一些条件
        try {
            return dao.queryForEq(LocalRepo.COLUMN_GROUP_ID, groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库（未分类的）
     */
    public List<LocalRepo> queryForNoGroup() {
        try {
            return dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库（全部的）
     */
    public List<LocalRepo> queryForAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
