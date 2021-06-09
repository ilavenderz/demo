package com.stargazer.demos.java.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Objects;

public class NIOClient {
    public static void main(String[] args) {
       Runnable r = init("127.0.0.1", 3333);
       if (Objects.nonNull(r))
           new Thread(r).start();
    }

    private static Client init(String ip, int port) {
        try {
            // 获得socket通道
            SocketChannel channel = SocketChannel.open();
            // 设置通道为非阻塞
            channel.configureBlocking(false);
            // 获得一个通道选择器
            Selector selector = Selector.open();


            // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
            //用channel.finishConnect();才能完成连接
            channel.connect(new InetSocketAddress(ip, port));
            //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
            channel.register(selector, SelectionKey.OP_CONNECT);

            return new Client(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Client implements Runnable{
    private final Selector selector;

    public Client(Selector selector) {
        this.selector = selector;
    }

    private void connect(SocketChannel channel, Selector selector) throws IOException {
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        // 设置为非阻塞式
        channel.configureBlocking(false);
        // 发送消息
        channel.write(ByteBuffer.wrap(new String("Hello Server! I am client.").getBytes()));
        // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void service(SelectionKey key) throws IOException, InterruptedException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer in = ByteBuffer.allocate(1024);
        channel.read(in);
        in.flip();
        String msg = Charset.defaultCharset().newDecoder().decode(in).toString();
        System.out.println(msg);
        Thread.sleep(1000);
        ByteBuffer out = ByteBuffer.wrap("hello Server!, I am client.".getBytes());
        out.flip();
        while (out.hasRemaining()) {
            channel.write(out);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 如果队列有新的Channel加入，那么Selector.select()会被唤醒
                selector.select();
                // 获得selector中选中的项的迭代器
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 链接事件发生
                    if (key.isConnectable()) {
                        connect((SocketChannel) key.channel(), selector);
                    } else if (key.isReadable()){
                        service(key);
                    }
                    //删除
                    iterator.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}