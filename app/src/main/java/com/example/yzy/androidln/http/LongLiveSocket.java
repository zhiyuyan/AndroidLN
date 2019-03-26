package com.example.yzy.androidln.http;

/**
 * Created by yzy on 2019/3/18 0018.
 */

public class LongLiveSocket {

    /**
     * 错误回调
     */
    public interface ErrorCallback {
        /**
         * 如果需要重连，返回true
         */
        boolean onError();
    }

    /**
     * 读数据回调
     */
    public interface DataCallback {
        void onData(byte[] data, int offset, int len);
    }

    /**
     * 写数据回调
     */
    public interface WritingCallback {
        void onSuccess();
        void onFail(byte[] data, int offset, int len);
    }

    public LongLiveSocket(String host, int port, DataCallback dataCallback, ErrorCallback errorCallback) {

    }

    public void write(byte[] data, WritingCallback writingCallback) {

    }

    public void write(byte[] data, int offset, int len, WritingCallback writingCallback) {

    }

    public void close() {

    }
}
