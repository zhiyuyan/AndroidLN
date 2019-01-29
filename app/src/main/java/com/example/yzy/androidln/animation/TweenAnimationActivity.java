package com.example.yzy.androidln.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/29 0029.
 */

public class TweenAnimationActivity extends AppCompatActivity {

    private ImageView mImage;

    private Animation mHyperspaceJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);
        mImage = findViewById(R.id.iv_image);
        mHyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.startAnimation(mHyperspaceJump);
            }
        });
    }
}
