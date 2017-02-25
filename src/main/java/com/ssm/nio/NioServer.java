package com.ssm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Domg on 2017/2/19.
 */
public class NioServer implements Runnable {

    int port;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    private int count = 0;

    NioServer(int port) throws IOException {
        this.port = port;
        selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8000));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        while (true) {
            try {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    handlerKey(next);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerKey(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel ssc = null;
        SocketChannel sc = null;
        String receive, send;
        if (selectionKey.isAcceptable()) {
            ssc = (ServerSocketChannel) selectionKey.channel();
            sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            sc = (SocketChannel) selectionKey.channel();
            readBuffer.clear();
            int read = sc.read(readBuffer);
            if (read > 0) {
                receive = new String(readBuffer.array(), 0, read);
                System.out.println("server receive data : " + receive);
                sc.register(selector, SelectionKey.OP_WRITE);
            }
        } else if (selectionKey.isWritable()) {
            writeBuffer.clear();
            sc = (SocketChannel) selectionKey.channel();
            send = "server response : " + count++;
            writeBuffer.put(send.getBytes());
            writeBuffer.flip();
            sc.write(writeBuffer);
            System.out.println(send);
            sc.register(selector, SelectionKey.OP_READ);
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(8000);
        new Thread(server).start();
    }
}

