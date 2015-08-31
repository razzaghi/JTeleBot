package com.jamejam.bot;

import co.vandenham.telegram.botapi.CommandHandler;
import co.vandenham.telegram.botapi.MessageHandler;
import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.requests.OptionalArgs;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyKeyboardMarkup;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class App extends TelegramBot {

    private static final Logger log = Logger.getLogger(App.class.getName());

    public App(boolean async) {
        super(Constants.API_TOKEN, async);
    }

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        TelegramBot bot = new App(true);
        bot.start();
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        sendMessage(message.getChat().getId(), "سلام خوش آمدی ، با این رباط از آخرین اخبار روزنامه جام جم مطلع میشوید");
    }

    public String removeSlash(String text) {
        return text.replaceAll("/", "");
    }

    @CommandHandler({"inews", "lnews", "menu","/inews", "/lnews", "/menu"})
    public void handleCommands(Message message) {
        String m = removeSlash(message.getText());
        switch (m) {
            case "lnews":
                lNews(message);
                break;
            case "inews":
                iNews(message);
                break;
            case "menu":
                sendCommandForMenu(message, "منو", Constants.menuItemLbl);
                break;
            default:
                handleText(message);
        }
    }

    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        String m = removeSlash(message.getText());
        String menuCode = getMenuCode(message.getText());
        String serviceCode = getServiceCode(message.getText());

        System.out.print(m + "\r\n");
        if(!menuCode.equals("")){
            handleMenu(message);
        }else if(!serviceCode.equals("")){
            handleServices(message);
        }else{
            fairText(message);
        }
    }


    public void handleMenu(Message message) {
        String m = removeSlash(message.getText());
        String menuCode = getMenuCode(message.getText());

        switch (menuCode) {
            case "lnews":
                lNews(message);
                break;
            case "inews":
                iNews(message);
                break;
            case "serviceTable":
                sendCommandForMenu(message, "سرویس های برگزیده", Constants.servicesNameTable);
                break;
            default:
                handleText(message);
        }
    }

    public void handleServices(Message message) {
        String m = removeSlash(message.getText());
        String serviceCode = getServiceCode(message.getText());
        switch (serviceCode) {
            case "-1":
//                sendCommandForMenu(message, "لطفا گزینه مورد نظر را انتخاب کنید", Constants.menuItemLbl);
                sendCommandForMenu(message, "", Constants.menuItemLbl);
                break;
            default:
                getLastFromService(message, serviceCode);
        }
    }


    public void handleText(Message message) {
        System.out.print(message.getText());
    }
//        sendText(message, "لطفا از منو انتخاب نمایید");
//    }

    public void fairText(Message message) {
        System.out.print(message.getText());
//     sendText(message, "لطفا از منو انتخاب نمایید");
    }

    String getServiceCode(String t) {

        boolean flag = false;
        int i = 0;
        for (String s : Constants.servicesNameTable) {
            if (t.compareTo(s) == 0) {
                flag = true;
                break;
            }
            i++;
        }
        if (flag)
            return Constants.servicesCodeTable[i];
        else
            return "";
    }

    String getMenuCode(String t) {
        boolean flag = false;
        int i = 0;
        for (String s : Constants.menuItemLbl) {
            if (t.compareTo(s) == 0) {
                flag = true;
                break;
            }
            i++;
        }
        if (flag)
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
                        Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
                bufferedReader.close();
            }
            assert in != null;
            in.close();
        } catch (Exception e) {
            System.out.print("خطا در بازیابی اطلاعات");
        }

        return sb.toString();
    }


    void getLastFromService(Message message, String serviceCode) {
        String url = Constants.getLast.replaceAll("@code", serviceCode);
        try {
            String resutl = callURL(url);
            JSONArray jsonArray = new JSONArray(resutl);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            if(count>0){
                for (int i = 0; i < count; i++) {   // iterate through jsonArray
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    url = jsonObject.getString("Url").replace("www.jamejamonline", "jjo");
                    url = url.replace("jamejamonline", "jjo");
                    String messageText = jsonObject.getString("Title") + "\r\n" + url;
                    sendText(message, messageText);
                }
            }else{
                sendText(message,"اخباری یافت نشد ، لطفا چند دقیقه بعد تلاش کنید");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getImportants(Message message) {
        String url = Constants.getLast.replaceAll("@code", "-1");
        try {
            String resutl = callURL(url);
            JSONArray jsonArray = new JSONArray(resutl);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for (int i = 0; i < count; i++) {   // iterate through jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                url = jsonObject.getString("Url").replace("www.jamejamonline", "jjo");
                url = url.replace("jamejamonline", "jjo");
                String messageText = jsonObject.getString("Title") + "\r\n" + url;
                sendText(message, messageText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sendText(Message message, String text) {
        OptionalArgs optionalArgs = new OptionalArgs();
        optionalArgs.disableWebPagePreview();
        sendMessage(message.getChat().getId(), text, optionalArgs);
    }

    void sendCommandForMenu(Message message, String text, String[] reply_markup) {
        ReplyKeyboardMarkup.Builder builder = new ReplyKeyboardMarkup.Builder();

        for (String s : reply_markup) {
            builder.add(100, s);

        }
        builder.setOneTimeKeyboard();
        builder.setResizeKeyboard();

        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup(builder);

        OptionalArgs optionalArgs = new OptionalArgs();
        optionalArgs.disableWebPagePreview();
        optionalArgs.replyMarkup(replyMarkup);
        sendMessage(message.getChat().getId(), text, optionalArgs);

    }


    void lNews(Message message) {

        this.getLastFromService(message, "-1");
    }

    void iNews(Message message) {

        this.getImportants(message);
    }


}
