package com.example.yzy.androidln.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yzy on 2019/1/6 0006.
 */

public class LocalIntentService extends IntentService {

    private static final String TAG = "LocalIntentService";

    public LocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.d(TAG, "receive task : " + action);
        SystemClock.sleep(3000);
        if ("com.yzy.action.TASK1".equals(action)) {
            Log.d(TAG, "handle task: " + action);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "service destroyed.");
        super.onDestroy();
    }
}
