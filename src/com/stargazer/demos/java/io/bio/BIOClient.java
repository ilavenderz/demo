package com.stargazer.demos.java.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BIOClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            for (int i = 0; i < 5; i++) {
                socket = new Socket("127.0.0.1", 3333);
                new Thread(new Client(socket, "client." + i)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Client implements Runnable{
    private final Socket socket;
    private final String name;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        if (Objects.nonNull(socket)) {
            while (true) {
                try {
                    String msg = String.format("Hello Server! I am %s, now is %s.", name, sdf.format(new Date()));
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                    InputStream inputStream = socket.getInputStream();
                    int len;
                    byte[] data = new byte[1024];
                    StringBuilder sb = new StringBuilder();

                    do {
                        len = inputStream.read(data);
                        sb.append(new String(data, 0, len));
                    } while(len > 2014);
                    String retMsg = sb.toString();
                    System.out.println(retMsg);
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

