package com.jamejam.bot.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jamejam.api.TelegramBot;
import com.jamejam.api.requests.OptionalArgs;
import com.jamejam.api.types.Message;
import com.jamejam.bot.App;
import com.jamejam.bot.model.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by razzaghi on 02/09/2015.
 */

@Path("/message")
public class SendMessage {

    public TelegramBot telegramBot;

    @GET
    @Produces("text/html"+ ";charset=utf-8")
    @Path("send/{title}/{text}")
    public String send(@PathParam("title") String title,@PathParam("text") String text) {


        UserDao userDao = new UserDao();
        List<UserModel> userModelList = userDao.getList();
        for (UserModel userModel : userModelList) {
            OptionalArgs optionalArgs = new OptionalArgs();
            optionalArgs.disableWebPagePreview();
            String body = "@jjoBot \r\n"+title + "\r\n" +text;
            App.bot.sendMessage(userModel.getTeleId(), body, optionalArgs);

        }

        return title+" -> "+text+"\r\n";
    }

    @GET
    @Produces("text/html"+ ";charset=utf-8")
    @Path("/getTodayCount")
    public String getTodayCount() {
        MessageDao messageDao = new MessageDao();
        return String.valueOf(messageDao.getTodayMessageCount());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    @Produces("text/html"+ ";charset=utf-8")
    @Path("/sendP")
    public String post(String obj) {

        try{

            SentMessage sentMessage = new ObjectMapper().readValue(obj, SentMessage.class);

            UserDao userDao = new UserDao();
            MessageDao messageDao=new MessageDao();
            MessageModel messageModel=new MessageModel();
            messageModel.setMessage(sentMessage.getText());
            messageDao.save(messageModel);
            List<UserModel> userModelList = userDao.getList();
            for (UserModel userModel : userModelList) {
                OptionalArgs optionalArgs = new OptionalArgs();
                optionalArgs.disableWebPagePreview();
                String body = sentMessage.getText();
                App.bot.sendMessage(userModel.getTeleId(), body, optionalArgs);

            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        return obj;
    }

    @GET
    @Produces("text/html")
    @Path("/index")
    public String index() {

        System.out.print("Test");

        return "Test";
    }


}
