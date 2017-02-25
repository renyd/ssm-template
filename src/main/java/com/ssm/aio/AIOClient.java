package com.ssm.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * Created by Domg on 2017/2/25.
 */
public class AIOClient {

    private AsynchronousSocketChannel asc;

    public AIOClient() throws IOException {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect() {
        asc.connect(new InetSocketAddress("127.0.0.1", 9000));
    }

    public void write(String data) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        asc.write(ByteBuffer.wrap(data.getBytes())).get();
        read();
    }

    public void read() throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer).get();
        buffer.flip();
        byte[] array = new byte[buffer.remaining()];
        buffer.get(array);
        System.out.println(new String(array, "utf-8").trim());
    }

    public static void main(String[] args) throws Exception {
        AIOClient client1 = new AIOClient();
        AIOClient client = new AIOClient();
        client.connect();
        client1.connect();
        client.write("time:" + System.currentTimeMillis());
        client1.write("time:" + System.currentTimeMillis());
    }

}
