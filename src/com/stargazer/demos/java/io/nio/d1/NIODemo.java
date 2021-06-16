package com.stargazer.demos.java.io.nio.d1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NIODemo {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new NServer("server")).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(new NClient("client1")).start();
        new Thread(new NClient("client2")).start();
        new Thread(new NClient("client3")).start();
        new Thread(new NClient("client4")).start();
    }
}

class NServer implements Runnable {

    private final String name;

    public NServer(String name) {
        this.name = name;
    }

    private String getMsg(SocketChannel sc) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int size = sc.read(buffer);
        StringBuilder sb = new StringBuilder();
        while (size > 0) {
            buffer.flip();
            sb.append(Charset.defaultCharset().newDecoder().decode(buffer));
            size = sc.read(buffer);
        }
        return sb.toString();
    }

    private void sendMsg(SocketChannel sc, String msg) throws IOException {
        ByteBuffer out = ByteBuffer.wrap(msg.getBytes());
        out.flip();
        while (out.hasRemaining()) {
            sc.write(out);
        }
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            //Selector selector = Selector.open();
            // channel.register(selector, SelectionKey.OP_ACCEPT);
            channel.bind(new InetSocketAddress(3333));

            while (true) {
                SocketChannel sc = channel.accept();
                if (Objects.nonNull(sc)) {
                    String msg = getMsg(sc);
                    if ("".equals(msg)) {
                        continue;
                    }
                    System.out.printf("-------- %s receive msg : %s \n", name, msg);
                    String clientName = msg.substring(msg.indexOf("I am ") + 5, msg.indexOf(","));
                    String retMsg = String.format("Hello %s, I am %s", clientName, name);
                    sendMsg(sc, retMsg);
                    channel.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class NClient implements Runnable {

    private final String name;

    public NClient(String name) {
        this.name = name;
    }

    private String getMsg(SocketChannel sc) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int size = sc.read(buffer);
        StringBuilder sb = new StringBuilder();
        while (size > 0) {
            buffer.flip();
            sb.append(Charset.defaultCharset().newDecoder().decode(buffer).toString());
            size = sc.read(buffer);
        }
        return sb.toString();
    }

    private void sendMsg(SocketChannel sc, String msg) throws IOException {
        ByteBuffer out = ByteBuffer.wrap(msg.getBytes());
        out.flip();
        while (out.hasRemaining()) {
            sc.write(out);
        }
    }

    @Override
    public void run() {
        try {

            while (true) {
                TimeUnit.SECONDS.sleep(1);
                SocketChannel channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(new InetSocketAddress("127.0.0.1", 3333));
                do {
                    TimeUnit.MICROSECONDS.sleep(10);
                } while (!channel.finishConnect());
                sendMsg(channel, String.format("Hello server! I am %s.", name));
                String msg = getMsg(channel);
                System.out.println(msg);
                channel.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}