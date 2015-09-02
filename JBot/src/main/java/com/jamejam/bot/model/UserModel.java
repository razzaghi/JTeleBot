package com.jamejam.bot.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.jamejam.api.Constants;
import com.jamejam.api.types.Message;

import java.util.List;

/**
 * Created by razzaghi on 01/09/2015.
 */
@DatabaseTable(tableName = "users")
public class UserModel {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(id = true)
    private Integer teleId;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private Boolean isGroup;

    Dao<UserModel, String> userDao;
    ConnectionSource connectionSource;

    public UserModel() {

        try {
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            userDao = DaoManager.createDao(connectionSource, UserModel.class);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    public UserModel(Integer teleId, String name, Boolean isGroup) {
        try {
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            userDao = DaoManager.createDao(connectionSource, UserModel.class);
            this.teleId = teleId;
            this.name = name;
            this.isGroup = isGroup;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public Integer getTeleId() {
        return teleId;
    }

    public void setTeleId(Integer teleId) {
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

    public void save(UserModel user) {

        try {
            userDao.create(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

    public List<UserModel> getList() {
        List<UserModel> userList = null;
        try {
            userList = userDao.queryForAll();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return userList;

    }

    public boolean userIsExist(int id) {
        List<UserModel> userList = null;
        try {
            userList = userDao.queryForEq("teleId",id);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        if(userList!=null)
            return true;
        else
            return false;

    }




}
