package com.jamejam.bot.model;

/**
 * Created by razzaghi on 06/09/2015.
 */
public class SentMessage {

    public String title;

    public String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                "title:'" + title + '\'' +
                ", text:'" + text + '\'' +
                '}';
    }
}
