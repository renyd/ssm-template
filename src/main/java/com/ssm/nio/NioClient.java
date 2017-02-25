package com.ssm.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Domg on 2017/2/19.
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_CONNECT);
        sc.connect(new InetSocketAddress("127.0.0.1", 8000));
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isConnectable()) {
                    SocketChannel client = (SocketChannel) next.channel();
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        System.out.println("--connection finish---");
                    }
                    client.register(selector, SelectionKey.OP_WRITE);
                } else if (next.isWritable()) {
                    writeBuffer.clear();
                    SocketChannel client = (SocketChannel) next.channel();
                    byte[] bytes = new byte[1024];
                    System.in.read(bytes);
                    writeBuffer.put(bytes);
                    writeBuffer.flip();
                    client.write(writeBuffer);
                    client.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    SocketChannel client = (SocketChannel) next.channel();
                    client = (SocketChannel) next.channel();
                    readBuffer.clear();
                    int read = client.read(readBuffer);
                    if (read > 0) {
                        String s = new String(readBuffer.array(), 0, read);
                        System.out.println(s);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
