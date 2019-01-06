package com.example.yzy.androidln.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yzy on 2019/1/6 0006.
 */

public class IntentServiceDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent service = new Intent(this, LocalIntentService.class);

        service.putExtra("task_action", "com.yzy.action.TASK1");
        startService(service);

        service.putExtra("task_action", "com.yzy.action.TASK2");
        startService(service);

        service.putExtra("task_action", "com.yzy.action.TASK3");
        startService(service);
    }
}
