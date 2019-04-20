package com.example.yzy.androidln;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Created by yzy on 2019/1/30 0030.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // 在主进程初始化调用哈
        super.onCreate();
        BlockCanary.install(this, new BlockCanaryContext()).start();
    }
}
