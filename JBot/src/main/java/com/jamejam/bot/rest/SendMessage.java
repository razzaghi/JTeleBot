package com.jamejam.bot.rest;

import com.jamejam.api.TelegramBot;
import com.jamejam.api.requests.OptionalArgs;
import com.jamejam.bot.App;
import com.jamejam.bot.model.UserDao;
import com.jamejam.bot.model.UserModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by razzaghi on 02/09/2015.
 */

@Path("/message")
public class SendMessage {

    public TelegramBot telegramBot;

    @GET
    @Produces("text/html")
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
    @Produces("text/html")
    @Path("/index")
    public String index() {

        System.out.print("Test");

        return "Test";
    }


}
