package com.jamejam.bot;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.jamejam.api.CommandHandler;
import com.jamejam.api.Constants;
import com.jamejam.api.MessageHandler;
import com.jamejam.api.TelegramBot;
import com.jamejam.api.requests.OptionalArgs;
import com.jamejam.api.types.Message;
import com.jamejam.api.types.ReplyKeyboardMarkup;
import com.jamejam.bot.model.UserDao;
import com.jamejam.bot.model.UserModel;
import com.jamejam.bot.rest.SendMessage;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class App extends TelegramBot {

    private static final Logger log = Logger.getLogger(App.class.getName());

    public static volatile TelegramBot bot = null;

    public static UserDao userDao;

    public App(boolean async) {
        super(Constants.SBF_API_TOKEN, async);

    }

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        try {

            userDao = new UserDao();

            bot = new App(true);
            bot.start();

            URI baseUri = UriBuilder.fromUri("http://0.0.0.0").port(8090).build();
            ResourceConfig config = new ResourceConfig(SendMessage.class);
            config.register(CORSResponseFilter.class);
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

    public void subscribe(Message message) {
        UserModel userModel = new UserModel();

        try {
            System.out.print(message.getChat().asGroupChat().getId());
            if (!userDao.userIsExist(message.getChat().getId())) {
                if (message.getChat().isGroupChat()) {
                    userModel.setName(message.getChat().asGroupChat().getTitle());
                    userModel.setIsGroup(true);
                    userModel.setTeleId(message.getChat().getId());
                } else {
                    userModel.setName(message.getChat().asUser().getFirstName() + " " + message.getChat().asUser().getLastName());
                    userModel.setTeleId(message.getChat().asUser().getId());
                    userModel.setIsGroup(false);
                }

                userDao.save(userModel);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    @CommandHandler({"start"})
    public void handleStart(Message message) {
        subscribe(message);

    }

    @CommandHandler({"help"})
    public void handleHelp(Message message) {
        sendMessage(message.getChat().getId(), "سلام خوش آمدی ، با این رباط از آخرین اخبار روزنامه جام جم مطلع میشوید");
    }

    public String removeSlash(String text) {
        return text.replaceAll("/", "");
    }

    @CommandHandler({"inews", "lnews", "menu", "/inews", "/lnews", "/menu"})
    public void handleCommands(Message message) {
        subscribe(message);
        String m = removeSlash(message.getText());
        switch (m) {
            case "lnews":
                lNews(message);
                break;
            case "inews":
                iNews(message);
                break;
            case "menu":
                sendCommandForMenu(message, "منو", com.jamejam.api.Constants.menuItemLbl);
                break;
            default:
                handleText(message);
        }
    }

    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        subscribe(message);
        String m = removeSlash(message.getText());
        String menuCode = getMenuCode(message.getText());
        String serviceCode = getServiceCode(message.getText());

        System.out.print(m + "\r\n");
        if (!menuCode.equals("")) {
            handleMenu(message);
        } else if (!serviceCode.equals("")) {
            handleServices(message);
        } else {
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
                sendCommandForMenu(message, "سرویس های برگزیده", com.jamejam.api.Constants.servicesNameTable);
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
                sendCommandForMenu(message, "منوی بات", Constants.menuItemLbl);
                break;
            default:
                getLastFromService(message, serviceCode);
        }
    }


    public void handleText(Message message) {
        System.out.print(message.getText());
    }

    public void fairText(Message message) {
        System.out.print(message.getText());
    }

    String getServiceCode(String t) {

        boolean flag = false;
        int i = 0;
        for (String s : com.jamejam.api.Constants.servicesNameTable) {
            if (t.compareTo(s) == 0) {
                flag = true;
                break;
            }
            i++;
        }
        if (flag)
            return com.jamejam.api.Constants.servicesCodeTable[i];
        else
            return "";
    }

    String getMenuCode(String t) {
        boolean flag = false;
        int i = 0;
        for (String s : com.jamejam.api.Constants.menuItemLbl) {
            if (t.compareTo(s) == 0) {
                flag = true;
                break;
            }
            i++;
        }
        if (flag)
            return com.jamejam.api.Constants.menuItem[i];
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
        String url = com.jamejam.api.Constants.getLast.replaceAll("@code", serviceCode);
        try {
            String resutl = callURL(url);
            JSONArray jsonArray = new JSONArray(resutl);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            if (count > 0) {
                for (int i = 0; i < count; i++) {   // iterate through jsonArray
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    url = jsonObject.getString("SUrl").replace("www.jamejamonline", "jjo");
                    url = url.replace("jamejamonline", "jjo");
                    String messageText = jsonObject.getString("Title") + "\r\n" + url;
                    sendText(message, messageText);
                }
            } else {
                sendText(message, "اخباری یافت نشد ، لطفا چند دقیقه بعد تلاش کنید");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getImportants(Message message) {
        String url = com.jamejam.api.Constants.getLast.replaceAll("@code", "-1");
        try {
            String resutl = callURL(url);
            JSONArray jsonArray = new JSONArray(resutl);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for (int i = 0; i < count; i++) {   // iterate through jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                url = jsonObject.getString("SUrl").replace("www.jamejamonline", "jjo");
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
        System.out.print(message.getChat().getId());
        sendMessage(message.getChat().getId(), text, optionalArgs);
    }

    void sendText(int id, String text) {
        OptionalArgs optionalArgs = new OptionalArgs();
        optionalArgs.disableWebPagePreview();
        sendMessage(id, text, optionalArgs);
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
