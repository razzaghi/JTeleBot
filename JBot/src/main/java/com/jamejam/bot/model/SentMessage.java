package com.jamejam.bot.model;

/**
 * Created by razzaghi on 06/09/2015.
 */
public class SentMessage {

    public String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                ", text:'" + text + '\'' +
                '}';
    }
}
