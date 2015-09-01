package com.jamejam.webserver;

import org.glassfish.grizzly.http.server.HttpServer;

public class App {

    public static void main(String[] args) {
        HttpServer server = HttpServer.createSimpleServer("0.0.0.0", 8090);
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
