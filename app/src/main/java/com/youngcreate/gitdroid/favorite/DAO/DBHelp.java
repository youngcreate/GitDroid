package com.youngcreate.gitdroid.favorite.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.youngcreate.gitdroid.favorite.model.LocalRepo;
import com.youngcreate.gitdroid.favorite.model.RepoGroup;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 16-8-4.
 */
public class DBHelp extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "repo_favorite.db";
    private static final int VERSION = 1;
    private Context context;
    private static DBHelp dbHelp;

    public static synchronized DBHelp getInstance(Context context) {
        if (dbHelp == null) {
            dbHelp = new DBHelp(context.getApplicationContext());
        }
        return dbHelp;

    }

    private DBHelp(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);
            //将本地默认数据添加到表里
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getDefaultGroups(context));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getDefaultLocalRepos(context));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RepoGroup.class, true);
            TableUtils.dropTable(connectionSource, LocalRepo.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
