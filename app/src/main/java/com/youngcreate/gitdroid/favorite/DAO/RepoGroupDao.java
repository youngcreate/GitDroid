package com.youngcreate.gitdroid.favorite.DAO;

import com.j256.ormlite.dao.Dao;
import com.youngcreate.gitdroid.favorite.model.RepoGroup;

import java.sql.SQLException;
import java.util.List;

/**
 * 仓库类别表的Dao
 * Created by Administrator on 16-8-4.
 */
public class RepoGroupDao {

    private Dao<RepoGroup, Long> dao;

    public RepoGroupDao(DBHelp dbHelp) {
        try {
            //创建出仓库类别表的Dao
            dao = dbHelp.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // dao.queryForAll()
        // dao.queryForId()
        // dao.createOrUpdate()

    }

    public void createOrUpdate(RepoGroup table) {
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加和更新 仓库类别表
     */
    public void createOrUpdate(List<RepoGroup> tables) {
        for (RepoGroup table : tables) {
            createOrUpdate(table);
        }
    }

    /**
     * 查询指定ID 仓库类别
     */
    public RepoGroup queryForId(long id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有 仓库类别
     */
    public List<RepoGroup> queryForAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
