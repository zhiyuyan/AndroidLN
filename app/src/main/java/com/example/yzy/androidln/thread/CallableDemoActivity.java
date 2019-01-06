package com.example.yzy.androidln.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by yzy on 2019/1/6 0006.
 */

public class CallableDemoActivity extends AppCompatActivity {

    private static final String TAG = "CallableDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Callable对象
        RtnThread rt = new RtnThread();
        // 使用FutureTask来包装Callable对象
        FutureTask<Integer> task = new FutureTask<Integer>(rt);
        for (int i = 0; i < 100; i++) {
            Log.d(TAG, Thread.currentThread().getName() + "的循环变量i的值： " + i);
            if (i == 20) {
                new Thread(task, "有返回值的线程").start();
            }
        }
        try {
            // 获取线程返回值
            Log.d(TAG, "子线程的返回值：" + task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RtnThread implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 100; i++) {
                Log.d(TAG, Thread.currentThread().getName() + "的循环变量i的值： " + i);
            }
            return i;
        }
    }
}
