package com.jamejam.bot.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.jamejam.api.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by razzaghi on 05/09/2015.
 */
public class MessageDao {

    private JdbcConnectionSource connectionSource;
    private Dao<MessageModel, String> dao;

    public MessageDao() {

        try{
            connectionSource = new JdbcConnectionSource(Constants.DB_URL, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);
            dao = DaoManager.createDao(connectionSource, MessageModel.class);
        }catch (Exception e){
            System.out.print("TEST"+e.getMessage());
        }

    }

    public void save(MessageModel message) {

        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateSt = sdf.format(date);
            message.setSentDate(dateSt);
            dao.create(message);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

    public List<MessageModel> getList() {
        List<MessageModel> messageModelList = null;
        try {
            messageModelList = dao.queryForAll();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return messageModelList;

    }

    public int getTodayMessageCount() {

        List<MessageModel> messageModelList = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateSt = sdf.format(date);
        try {
            messageModelList = dao.queryForEq("sentDate", dateSt);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        if(messageModelList==null)
            return 0;
        return messageModelList.size();

    }



}
