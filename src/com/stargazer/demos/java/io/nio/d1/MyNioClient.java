package com.stargazer.demos.java.io.nio.d1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class MyNioClient {
    private final static int port = 3333;
    private final static String ip = "127.0.0.1";
    private final static int BUF_SIZE = 1024;
    private static final ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);

    private void initClient() throws IOException {
        //创建一个选择器
        Selector selector = Selector.open();
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        clientChannel.connect(new InetSocketAddress(ip, port));
        clientChannel.register(selector, SelectionKey.OP_CONNECT);
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()) {
                    doConnect(key);
                } else if (key.isReadable()) {
                    doRead(key);
                }
            }
        }
    }

    public void doConnect(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        if (clientChannel.isConnectionPending()) {
            clientChannel.finishConnect();
        }
        clientChannel.configureBlocking(false);
        String info = "服务端你好!!";
        byteBuffer.clear();
        byteBuffer.put(info.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        clientChannel.write(byteBuffer);
        //clientChannel.register(key.selector(),SelectionKey.OP_READ);
        //clientChannel.close();
    }

    public void doRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        clientChannel.read(byteBuffer);
        byte[] data = byteBuffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端发送消息：" + msg);
        clientChannel.close();
        key.selector().close();
    }

    public static void main(String[] args) throws IOException {
        MyNioClient myNioClient = new MyNioClient();
        myNioClient.initClient();
    }
}
