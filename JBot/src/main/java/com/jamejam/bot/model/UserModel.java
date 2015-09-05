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
    private int id;

    @DatabaseField(index = true)
    private int teleId;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private boolean isGroup;


    public UserModel() {

    }

    public UserModel(Integer teleId, String name, Boolean isGroup) {
        try {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(", ").append("teleId=").append(teleId);
        sb.append(", ").append("name=").append(name);
        sb.append(", ").append("isGroup=").append(isGroup);
        return sb.toString();
    }

}
