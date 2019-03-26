package com.example.yzy.androidln.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yzy on 2019/3/18 0018.
 */

public class EchoServer {

    private final ServerSocket mServerSocket;

    public EchoServer(int port) throws IOException {
        // 1.创建一个ServerSocket，并监听端口port
        mServerSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        // 2.开始接收客户连接
        Socket client = mServerSocket.accept();
        handleClient(client);
    }

    private void handleClient(Socket socket) throws IOException {
        // 3.使用socket进行通信
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }

}
