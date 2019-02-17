package com.example.yzy.androidln.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yzy.androidln.R;

public class SlideDemoActivity extends AppCompatActivity {

    private CustomView mCustomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_demo);
        mCustomView = findViewById(R.id.custom_view);
        mCustomView.smoothScrollTo(-800, 0);
    }
}
