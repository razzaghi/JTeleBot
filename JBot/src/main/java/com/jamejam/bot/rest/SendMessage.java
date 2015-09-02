package com.jamejam.bot.rest;

import com.jamejam.api.TelegramBot;
import com.jamejam.api.requests.OptionalArgs;
import com.jamejam.bot.App;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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


//        UserModel userModel= new UserModel();
//        List<UserModel> userList= new ArrayList<>();
//        userList = userModel.getList();
        OptionalArgs optionalArgs = new OptionalArgs();
        optionalArgs.disableWebPagePreview();
        App.bot.sendMessage(102490145, text, optionalArgs);

/*
        for (UserModel user : userList) {
            telegramBot.sendMessage(102490145, text, optionalArgs);
        }
*/
        System.out.print(title+"\r\n"+text);

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
