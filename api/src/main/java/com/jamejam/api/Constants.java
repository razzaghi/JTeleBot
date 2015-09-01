package com.jamejam.api;

import java.util.HashMap;

/**
 * Created by razzaghi on 29/08/2015.
 */
public class Constants {

    public final  static String JJO_API_TOKEN = "93764156:AAFrZoe6Qe7gkX88AV_PqNqETO2NCXkk07Q";
    public final  static String SBF_API_TOKEN = "98066991:AAGdW3DZMZukIBy4UxapfYQ4rexYkVmoeHM";

    public final  static String serviceIp = "10.0.9.151";

    HashMap<String, String> commands = new HashMap<String, String>();

    public static final  String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final  String DB_URL = "jdbc:mysql://localhost/JTeleBot";
    public static final  String DB_HOST = "localhost";
    public static final  String DB = "JTeleBot";
    public static final  Integer DB_PORT = 3306;

    public static final  String JDBC_USERNAME = "root";
    public static final  String JDBC_PASSWORD = "";



    public final  static String getLast = "http://" + serviceIp + "/DoCommand.aspx?fn=getLast&Code=@code&top=5&nf=28";
    public final  static String getImportant = "http://" + serviceIp + "/DoCommand.aspx?fn=getPopular";
    public final  static String search = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28&query=";
    public final  static String inews = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28";
    public final  static String lnews = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28";
    public final  static String[] menuItem = {"lnews", "inews", "serviceTable"};

    public final  static String[] menuItemLbl = {
            "آخرین اخبار",
            "مهمترین اخبار",
            "سرویس های برگزیده"
    };

    public final  static String[] servicesCodeTable = {
    "01-04-102",
            "01-02-01-169",
            "01-06-91",
            "01-03-01-119",
            "03-151",
            "-1"
    };

    public final  static String[] servicesNameTable = {
            "جامعه",
            "سیاسی",
            "حوادث",
            "اقتصادی",
            "ورزشی",
            "بازگشت"
    };



}
