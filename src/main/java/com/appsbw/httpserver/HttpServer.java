package com.appsbw.httpserver;

import com.appsbw.httpserver.config.Configuration;
import com.appsbw.httpserver.config.ConfigurationManager;
import com.appsbw.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Driver Class for the Http Server
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + configuration.getPort());
        LOGGER.info("Using WebRoot: " + configuration.getWebroot());

        ServerListenerThread listenerThread = null;
        try {
            listenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            listenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
