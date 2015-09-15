package com.jamejam.bot.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by razzaghi on 01/09/2015.
 */
@DatabaseTable(tableName = "message")
public class MessageModel {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true)
    private String message;

    @DatabaseField(canBeNull = true)
    private String sentDate;


    public MessageModel() {

    }

    public MessageModel(String message) {
        this.message = message;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        this.sentDate = sdf.format(date);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(", ").append("message=").append(message);
        sb.append(", ").append("sentDate=").append(sentDate);
        return sb.toString();
    }

}
