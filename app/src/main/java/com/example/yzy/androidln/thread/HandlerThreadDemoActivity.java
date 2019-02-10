package com.example.yzy.androidln.thread;

import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class HandlerThreadDemoActivity extends AppCompatActivity {

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
