package com.appsbw.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was server using my Simple Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r"; // 13, 10
            String response =
                    "HTTP/1.1. 200 OK" + CRLF + // Status Line + HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length:" + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());


            LOGGER.info(" * Connection Processing Finished.");

        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }


    }
}
