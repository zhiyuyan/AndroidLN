package com.example.yzy.androidln.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/9 0009.
 */

public class LruCacheDemoActivity extends AppCompatActivity {

    private LruCache<String, Bitmap> mMemoryCache;

    private ImageView mIvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrucache_demo);
        mIvContent = findViewById(R.id.iv_content);
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void putCache(View view) {
        mMemoryCache.put(String.valueOf(R.mipmap.ic_launcher), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
    }

    public void getCache(View view) {
        Bitmap b = mMemoryCache.get(String.valueOf(R.mipmap.ic_launcher));
        mIvContent.setImageBitmap(b);
    }
}
