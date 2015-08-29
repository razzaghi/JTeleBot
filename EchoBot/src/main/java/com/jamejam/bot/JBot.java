package com.jamejam.bot;

import co.vandenham.telegram.botapi.CommandHandler;
import co.vandenham.telegram.botapi.DefaultHandler;
import co.vandenham.telegram.botapi.MessageHandler;
import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyKeyboardMarkup;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class JBot extends TelegramBot {

    private static final Logger log = Logger.getLogger(JBot.class.getName());

    public JBot(boolean async) {
        super(Constants.API_TOKEN, async);
    }






    public static void main(String[] args) {
        TelegramBot bot = new JBot(true);
        bot.start();
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        replyTo(message, "سلام خوش اومدی، امیدوارم بتونم بهترین اخبار رو در اختیارت بذارم");
    }

    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        log.info(String.format("%s: %s", message.getChat().getId(), message.getText()));
        replyTo(message, message.getText());
    }

    @DefaultHandler
    public void handleDefault(Message message) {
        replyTo(message, "Say what?");
    }

    String getServiceCode(String t){

        boolean flag = false;
        int i = 0;
        for (String s : Constants.servicesNameTable) {
            if (t.compareTo(s)==0){
                flag = true;
                break;
            }
            i++;
        }
        if(flag)
            return Constants.servicesCodeTable[i];
        else
            return "";
    }

    String getMenuCode(String t){
        boolean flag= false;
        int i=0;
        for (String s : Constants.menuItemLbl) {
            if(t.compareTo(s)==0){
                flag=true;
                break;
            }
            i++;
        }
        if(flag)
            return Constants.menuItem[i];
        else
            return "";

    }

    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }


    void getLastFromService(Message message,String serviceCode){
        String url = Constants.getLast.replaceAll("@code", serviceCode);
        try {
            String resutl = callURL(url);
            JSONArray jsonArray = new JSONArray(resutl);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++){   // iterate through jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                url = jsonObject.getString("Url").replace("www.jamejamonline", "jjo");
                url = url.replace("jamejamonline", "jjo");
                String messageText =  "@jjoBot \r\n http://JJO.Ir \r\n" + jsonObject.getString("Title") + "\r\n" + url;
                replyTo(message, messageText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sendText(Message message,String text){
        replyTo(message,"@jjoBot \r\n"+text);
    }

    void sendCommandForMenu(Message message,String text,String[] reply_markup){
        ReplyKeyboardMarkup
    }
            try:
            bot.send_message(cid, "@jjoBot \r\n"+text,reply_markup=reply_markup)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly


    def lnews(cid):
            try:
    url = getLast.replace("@code","-1")
    content = callAPi(url)
    for r in content:
    data = r
            url = data['Url'].replace("www.jamejamonline","jjo")
    url = url.replace("jamejamonline","jjo")
    sendText(cid, "http://JJO.Ir \r\n" + data['Title'] + "\r\n" + url)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    def inews(cid):
            try:
    content = callAPi(getImportant)
    for r in content:
    data = r
            url = data['Url'].replace("www.jamejamonline","jjo")
    url = url.replace("jamejamonline","jjo")
    sendText(cid, "http://JJO.Ir \r\n" + data['Title'] + "\r\n" + url)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    def showSubmenu(cid):
    sendCommandForMenu(cid, "لطفا سرویس مورد نظر خود را انتخاب نمایید", reply_markup=subMenuSelect)  #show the keyboard

    @bot.message_handler(func=lambda message: (message.text in servicesNameTable))
    def msg_servicePackageSelect(m):
            try:
    cid = m.chat.id
          print("Submenu")
    text = m.text
    bot.send_chat_action(cid,'typing')
    serviceCode =getServiceCode(m.text)
    if serviceCode != False:
            if(serviceCode=="-1"):
    sendCommandForMenu(cid, "لطفا گزینه مورد نظر خود را انتخاب نمایید", reply_markup=menuSelect)
    else:
    getLastFromService(cid,serviceCode)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    @bot.message_handler(func=lambda message: (message.text in menuItemLbl))
    def msg_menuSelect(m):
            try:
    print("Main Menu")
    cid = m.chat.id
            text = m.text
    bot.send_chat_action(cid,'typing')
    menuCode =getMenuCode(m.text)
    print(menuCode)
    if menuCode != False:
            if menuCode=="lnews":
    lnews(cid)
    elif menuCode=="inews":
    inews(cid)
    elif menuCode=="serviceTable":
    showSubmenu(cid)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    @bot.message_handler(commands=['help'])
    def command_help(m):
            try:
    cid = m.chat.id
            helpText = "دستورات زیر موجود است : \n"
    for key in commands:
    helpText += "/" + key + ": "
    helpText += commands[key] + "\n"
    sendText(cid, helpText)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    @bot.message_handler(commands=['lnews'])
    def command_lastNews(m):
            try:
    cid = m.chat.id
    bot.send_chat_action(cid, 'typing')
    lnews(cid)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly


    @bot.message_handler(commands=['inews'])
    def command_important_news(m):
            try:
    cid = m.chat.id
    bot.send_chat_action(cid, 'typing')
    inews(cid)
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly

    @bot.message_handler(func=lambda message: True, content_types=['text'])
    def command_default(m):
            try:
    sendText(m.chat.id,
             "Iدستور وارد شده صحیح نمی باشد برای راهنمایی  از این دستور ا ستفاده کنید \r\n /help")
    except Exception as inst:
    print(type(inst))     # the exception instance
                                          print(inst.args)      # arguments stored in .args
                                                                                   print(inst)           # __str__ allows args to be printed directly


}
