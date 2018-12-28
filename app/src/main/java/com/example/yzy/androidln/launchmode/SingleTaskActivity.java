package com.example.yzy.androidln.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2018/12/27 0027.
 */

public class SingleTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
    }

    public void onBtn1Click(View view) {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }

    public void onBtn2Click(View view) {
        Intent intent = new Intent(this, SingleTaskActivity.class);
        startActivity(intent);
    }

    public void onBtn3Click(View view) {
        Intent intent = new Intent(this, SingleInstanceActivity.class);
        startActivity(intent);
    }
}
