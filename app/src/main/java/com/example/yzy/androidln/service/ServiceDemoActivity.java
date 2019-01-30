package com.example.yzy.androidln.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/2 0002.
 */
public class ServiceDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSartService;

    private Button mBtnStopService;

    private Button mBtnBindService;

    private Button mBtnUnBindService;

    private MyService.MyBinder mBinder;

    private MyAIDLService mAIDLService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = (MyService.MyBinder) service;
//            mBinder.startDownLoad();
            mAIDLService = MyAIDLService.Stub.asInterface(service);
            try {
                int result = mAIDLService.plus(3,5);
                String upperStr = mAIDLService.toUpperCase("hello world");
                Log.d("TAG", "result is " + result);
                Log.d("TAG", "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        mBtnSartService = findViewById(R.id.start_service);
        mBtnStopService = findViewById(R.id.stop_service);
        mBtnBindService = findViewById(R.id.bind_service);
        mBtnUnBindService = findViewById(R.id.unbind_service);
        mBtnSartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);
        mBtnBindService.setOnClickListener(this);
        mBtnUnBindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent intent = new Intent(this, MyService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
        }
    }
}
