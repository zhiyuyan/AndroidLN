package com.example.yzy.androidln.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/24 0024.
 */

public class RateSeekbar extends FrameLayout {

    private IndicatorSeekbar mSeekBar;
    private EditText mEditText;

    private float mIndicatorOffset;

    LayoutParams mEditTextLayoutParams;

    public RateSeekbar(Context context) {
        this(context, null);
    }

    public RateSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RateSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSeekBar = new IndicatorSeekbar(mContext);
        mSeekBar.setThumb(mContext.getResources().getDrawable(R.drawable.seekbar_thumb));
        mSeekBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.seekbar_progress_drawable));
        mSeekBar.setMax(100);
        LayoutParams seekLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seekLayoutParams.gravity = Gravity.CENTER;
        addView(mSeekBar, seekLayoutParams);

        mEditTextLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mEditText = new EditText(mContext);
        mEditText.setVisibility(GONE);
        addView(mEditText, mEditTextLayoutParams);

        mSeekBar.setOnSeekBarChangeListener(new IndicatorSeekbar.OnIndicatorSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, float indicatorOffset) {
                mEditTextLayoutParams.leftMargin = (int) indicatorOffset;
                mEditText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
