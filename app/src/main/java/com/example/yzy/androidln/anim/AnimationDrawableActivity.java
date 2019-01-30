package com.example.yzy.androidln.anim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yzy.androidln.R;

public class AnimationDrawableActivity extends AppCompatActivity {

    AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);
        ImageView imageView = findViewById(R.id.iv_show);
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
    }

    public void start(View view) {
        mAnimationDrawable.start();
    }

    public void end(View view) {
        mAnimationDrawable.stop();
    }

}
