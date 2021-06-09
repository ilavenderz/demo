package com.stargazer.demos.java.io.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {


        // 1. serverSelector负责轮询是否有新的连接，服务端监测到新的连接之后，不再创建一个新的线程，

        Selector serverSelector = init(3333);
        if (Objects.nonNull(serverSelector)) {
            new Thread(new Server(serverSelector)).start();
        }
    }

    private static Selector init (int port) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().setReuseAddress(true);
            channel.socket().bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("------server init----------");
            return selector;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Server implements Runnable {
    private final Selector serverSelector;

    public Server(Selector serverSelector) {
        this.serverSelector = serverSelector;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                if (serverSelector.select(10) > 0) {
                    // 得到已经被捕获了的SelectionKey的集合
                    Set<SelectionKey> set = serverSelector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = set.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
                            System.out.println("客户端机子的地址是 "
                                            + sc.socket().getRemoteSocketAddress()
                                            + "  客户端机机子的端口号是 "
                                            + sc.socket().getLocalPort());
                            sc.configureBlocking(false);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            sc.register(serverSelector, SelectionKey.OP_READ, buffer);
                        }
                        if (key.isReadable()) {
                            try {
                                service(key);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                keyIterator.remove();
                            }
                        }
                        key.cancel();
                        key.channel().close();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void service(SelectionKey key) throws Exception {
        if (Objects.isNull(key))
            return;
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer srcBuffer = ByteBuffer.allocate(1024);
        int size = channel.read(srcBuffer);
        StringBuilder sb = new StringBuilder();
        while (size > 0) {
            srcBuffer.flip();
            sb.append(Charset.defaultCharset().newDecoder().decode(srcBuffer).toString());
            size = channel.read(srcBuffer);
        }
        String data = sb.toString();
        System.out.println(data);
        Thread.sleep(100);

        String retMsg = "Hello client, I am server.";
        ByteBuffer dstBuffer = ByteBuffer.wrap(retMsg.getBytes());
        while (dstBuffer.hasRemaining()) {
            channel.write(dstBuffer);
        }
    }
}

