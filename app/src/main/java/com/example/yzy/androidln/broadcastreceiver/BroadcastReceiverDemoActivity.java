package com.example.yzy.androidln.broadcastreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/3/11 0011.
 */

public class BroadcastReceiverDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnOne;
    private Button mBtnTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver_demo);
        mBtnOne = findViewById(R.id.btn_one);
        mBtnTwo = findViewById(R.id.btn_two);
        mBtnOne.setOnClickListener(this);
        mBtnTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MyBroadcastReceiver");
                intent.putExtra("msg", "我在发送广播！这只是一个普通的广播，" +
                        "你们无法通过abortBroadcast()的方式停止广播的传播，" +
                        "也无法往Broadcast中存入数据因为它是异步的");
                sendBroadcast(intent);
                break;
            case R.id.btn_two:
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.MyBroadcastReceiver");
                intent2.putExtra("msg", "我在发送个有序的广播，" +
                        "你们可以通过abortBroadcast()的方式停止广播的传播，" +
                        "也可以往Broadcast中存入数据");
                sendOrderedBroadcast(intent2, null);
                break;
        }
    }
}
