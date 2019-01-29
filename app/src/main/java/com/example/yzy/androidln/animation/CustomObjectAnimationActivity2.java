package com.example.yzy.androidln.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/29 0029.
 */

public class CustomObjectAnimationActivity2 extends AppCompatActivity {

    private Button mBtnStart;
    private MyPointView2 mPointView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_object_animation);

        mBtnStart = findViewById(R.id.btn_start);
        mPointView = findViewById(R.id.pointview);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPointViewAnimation();
            }
        });

    }

    private void doPointViewAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 300, 100);
        animator.setDuration(2000);
        animator.start();
    }
}
