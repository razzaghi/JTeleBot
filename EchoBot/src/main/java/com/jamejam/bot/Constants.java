package com.jamejam.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by razzaghi on 29/08/2015.
 */
public class Constants {

    final static String API_TOKEN = "98066991:AAGdW3DZMZukIBy4UxapfYQ4rexYkVmoeHM";

    final static String serviceIp = "10.0.9.151";

    HashMap<String, String> commands = new HashMap<String, String>();

    final static String getLast = "http://" + serviceIp + "/DoCommand.aspx?fn=getLast&Code=@code&top=5&nf=28";
    final static String getImportant = "http://" + serviceIp + "/DoCommand.aspx?fn=getPopular";
    final static String search = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28&query=";
    final static String inews = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28";
    final static String lnews = "http://" + serviceIp + "/DoCommand.aspx?fn=search&Code=@code&top=5&nf=28";
    final static String[] menuItem = {"lnews", "inews", "serviceTable"};

    final static String[] menuItemLbl = {
            "¬Œ—?‰ «Œ»«—",
            "„Â„ —?‰ «Œ»«—",
            "”—Ê?” Â«? »—ê“?œÂ"
    };

    final static String[] servicesCodeTable = {
    "01-04-102",
            "01-02-01-169",
            "01-06-91",
            "01-03-01-119",
            "112",
            "-1"
    };

    final static String[] servicesNameTable = {
            "Ã«„⁄Â",
            "”?«”?",
            "ÕÊ«œÀ",
            "«ﬁ ’«œ?",
            "Ê—“‘?",
            "»«“ê‘ "
    };


}
