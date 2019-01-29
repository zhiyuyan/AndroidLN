package com.example.yzy.androidln.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.example.yzy.androidln.R;
import com.example.yzy.androidln.utils.DimenUtils;

/**
 * Created by yzy on 2019/1/23 0023.
 */

public class IndicatorSeekbar extends AppCompatSeekBar {

    // 画笔
    private Paint mPaint;
    // 进度文字位置信息
    private Rect mProgressTextRect = new Rect();
    // 滑块按钮宽度
    private int mThumbWidth = DimenUtils.dp2px(getContext(), 50);
    // 进度指示器宽度
    private int mIndicatorWidth = DimenUtils.dp2px(getContext(), 50);
    // 进度监听
    private OnIndicatorSeekBarChangeListener mIndicatorSeekBarChangeListener;


    public IndicatorSeekbar(Context context) {
        this(context, null);
    }

    public IndicatorSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    public IndicatorSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#00574B"));
        mPaint.setTextSize(DimenUtils.sp2px(getContext(), 16));

        // 如果不设置padding，当滑动到最左边或最右边时，滑块会显示不全
        setPadding(mThumbWidth / 2, 0, mThumbWidth / 2, 0);

        // 设置滑动监听
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // NO OP
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mIndicatorSeekBarChangeListener != null) {
                    mIndicatorSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mIndicatorSeekBarChangeListener != null) {
                    mIndicatorSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String progressText = getProgress() + "%";
        mPaint.getTextBounds(progressText, 0, progressText.length(), mProgressTextRect);

        // 进度百分比
        float progressRatio = (float) getProgress() / getMax();
        // thumb偏移量
        float thumbOffset = (mThumbWidth - mProgressTextRect.width()) / 2 - mThumbWidth * progressRatio;
        float thumbX = getWidth() * progressRatio + thumbOffset;
        float thumbY = getHeight() / 2f + mProgressTextRect.height() / 2f;
        canvas.drawText(progressText, thumbX, thumbY, mPaint);

        if (mIndicatorSeekBarChangeListener != null) {
            float indicatorOffset = getWidth() * progressRatio - (mIndicatorWidth - mThumbWidth) / 2 - mThumbWidth * progressRatio;
            mIndicatorSeekBarChangeListener.onProgressChanged(this, getProgress(), indicatorOffset);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 设置进度监听
     *
     * @param listener OnIndicatorSeekBarChangeListener
     */
    public void setOnSeekBarChangeListener(OnIndicatorSeekBarChangeListener listener) {
        this.mIndicatorSeekBarChangeListener = listener;
    }

    /**
     * 进度监听
     */
    public interface OnIndicatorSeekBarChangeListener {
        /**
         * 进度监听回调
         *
         * @param seekBar         SeekBar
         * @param progress        进度
         * @param indicatorOffset 指示器偏移量
         */
        void onProgressChanged(SeekBar seekBar, int progress, float indicatorOffset);

        /**
         * 开始拖动
         *
         * @param seekBar SeekBar
         */
        void onStartTrackingTouch(SeekBar seekBar);

        /**
         * 停止拖动
         *
         * @param seekBar SeekBar
         */
        void onStopTrackingTouch(SeekBar seekBar);
    }

}
