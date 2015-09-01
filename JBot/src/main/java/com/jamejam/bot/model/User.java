package com.jamejam.bot.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.jamejam.api.Constants;

import java.util.List;

/**
 * Created by razzaghi on 01/09/2015.
 */
@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField(id = true)
    private Long teleId;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private Boolean isGroup;

    Dao<User, String> userDao;
    ConnectionSource connectionSource;

    User() {

        try {
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            userDao = DaoManager.createDao(connectionSource, User.class);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    public User(Long teleId, String name, Boolean isGroup) {
        try {
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            userDao = DaoManager.createDao(connectionSource, User.class);
            this.teleId = teleId;
            this.name = name;
            this.isGroup = isGroup;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public Long getTeleId() {
        return teleId;
    }

    public void setTeleId(Long teleId) {
        this.teleId = teleId;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save(User user) {

        try {
            userDao.create(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

    public List<User> getList() {
        List<User> userList = null;
        try {
            userList = userDao.queryForAll();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return userList;

    }


}
