package com.example.yzy.androidln.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yzy on 2019/3/11 0011.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getExtras().get("msg").toString();
        setResultData("MyBroadcastReceiver接收到广播");
        Toast.makeText(context, "时间:" + new SimpleDateFormat("yyyy-MM-dd hh.mm.ss").format(new Date())
                + "\nMyBroadcastReceiver收到Action名为:" + intent.getAction().toString()
                + "的广播\nComponent:" + intent.getComponent()
                + "\nmsg:" + msg, Toast.LENGTH_LONG).show();
    }
}
