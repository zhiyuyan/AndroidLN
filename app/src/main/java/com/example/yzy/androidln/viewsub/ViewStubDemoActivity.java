package com.example.yzy.androidln.viewsub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/3/26 0026.
 */

public class ViewStubDemoActivity extends AppCompatActivity {

    private ViewStub mViewStub;
    private TextView mTvHello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub_demo);
        mViewStub = findViewById(R.id.vs);
    }

    public void inflate(View view) {
        mViewStub.inflate();
        mTvHello = findViewById(R.id.hello_tv);
    }

    public void setData(View view) {
        if (mTvHello != null) {
            mTvHello.setText("DATA!!!");
        }
    }
}
