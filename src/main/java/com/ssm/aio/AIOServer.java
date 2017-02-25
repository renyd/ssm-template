package com.ssm.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Domg on 2017/2/25.
 */
public class AIOServer {

    private ExecutorService executorService;

    private AsynchronousChannelGroup channelGroup;

    public AsynchronousServerSocketChannel serverSocketChannel;

    public AIOServer() {
        try {
            executorService = Executors.newCachedThreadPool();
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);
            serverSocketChannel.bind(new InetSocketAddress(9000));
            serverSocketChannel.accept(this, new ServerCompletionHandler());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AIOServer server = new AIOServer();

    }

}
