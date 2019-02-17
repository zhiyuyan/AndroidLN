package com.example.yzy.androidln.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class HandlerThreadDemoActivity extends AppCompatActivity {

    private TextView mTvServiceInfo;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;

    private static final int MSG_UPDATE_INFO = 0x110;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread handlerThread = new HandlerThread("HandlerThread");

        handlerThread.start();

        handlerThread.getThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                Log.d("yan", "run");
            }
        });
    }
}
