package com.example.yzy.androidln.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/29 0029.
 */

public class ObjectAnimationActivity extends AppCompatActivity {

    private Button mBtnStart;
    private Button mBtnCancel;
    private TextView mTvText;
    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animation);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mTvText = findViewById(R.id.tv);

        mAnimator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.setDuration(10000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char text = (char) animation.getAnimatedValue();
                mTvText.setText(String.valueOf(text));
            }
        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimator.start();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimator.cancel();
            }
        });
    }

    class CharEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt = startValue;
            int endInt = endValue;
            int curInt = (int) (startInt + fraction * (endInt - startInt));
            char result = (char) curInt;
            return result;
        }
    }
}
