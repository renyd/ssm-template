package com.ssm.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by Domg on 2017/2/25.
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {
    public void completed(AsynchronousSocketChannel asc, AIOServer attachment) {
        // 下一个客户端接入的时候能继续处理
        attachment.serverSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        read(asc, buffer);
    }

    private void read(final AsynchronousSocketChannel asc, ByteBuffer buffer) {
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                String data = new String(attachment.array()).trim();
                System.out.println("server receive data:" + data);
                write(asc, "server response");
            }

            @Override
            public void failed(Throwable throwable, ByteBuffer attachment) {
                throwable.printStackTrace();
            }
        });
    }

    public void failed(Throwable exc, AIOServer attachment) {
        exc.printStackTrace();
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            asc.write(buffer).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
