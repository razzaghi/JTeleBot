package com.jamejam.bot.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.jamejam.api.Constants;

import java.util.List;

/**
 * Created by razzaghi on 05/09/2015.
 */
public class UserDao {

    private JdbcConnectionSource connectionSource;
    private Dao<UserModel, String> dao;

    public UserDao() {

        try{
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            dao = DaoManager.createDao(connectionSource, UserModel.class);
        }catch (Exception e){
            System.out.print("TEST"+e.getMessage());
        }

    }

    public void save(UserModel user) {

        try {
            dao.create(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

    public List<UserModel> getList() {
        List<UserModel> userList = null;
        try {
            userList = dao.queryForAll();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return userList;

    }

    public boolean userIsExist(int id) {
        List<UserModel> userList = null;
        try {
            userList = dao.queryForEq("teleId", id);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        if(userList==null)
            return false;
        assert userList != null;
        return userList.size() > 0;

    }




}
