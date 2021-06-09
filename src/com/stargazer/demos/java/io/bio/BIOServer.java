package com.stargazer.demos.java.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3333);
        while (true) {
            try {
                Socket socket = server.accept();
                new Thread(new Server(socket, "Server" + System.currentTimeMillis())).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Server implements Runnable {
    private final Socket socket;
    private final String name;

    public Server(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (Objects.nonNull(socket)) {
                inputStream = socket.getInputStream();
                int len;
                byte[] data = new byte[1024];
                do {
                    StringBuilder sb = new StringBuilder();
                    len = inputStream.read(data);
                    sb.append(new String(data, 0, len));
                    String str = sb.toString();
                    System.out.println(str);
                    Thread.sleep(10000);
                    String clientName = str.substring(str.indexOf("I am ") + 5, str.indexOf(","));
                    String retMsg = String.format("Hello %s, I am %s", clientName, name);
                    outputStream = socket.getOutputStream();
                    outputStream.write(retMsg.getBytes());
                    outputStream.flush();
                } while (len != -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
