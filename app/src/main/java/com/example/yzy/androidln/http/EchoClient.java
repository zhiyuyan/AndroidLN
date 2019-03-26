package com.example.yzy.androidln.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by yzy on 2019/3/18 0018.
 */

public class EchoClient {

    private final Socket mSocket;

    public EchoClient(String host, int port) throws IOException {
        // 创建socket并连接服务器
        mSocket = new Socket(host, port);
    }

    public void run() throws IOException {
        // 和服务端进行通信
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readResponse();
            }
        });
        readerThread.start();

        OutputStream out = mSocket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = System.in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }

    private void readResponse() {
        try {
            InputStream in = mSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
