package com.example.yzy.androidln.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by yzy on 2019/1/31 0031.
 */

public class IndicatorSeekbar extends View {

    // 画笔
    private Paint mPaint;

    // the height of thumb
    private int mThumbHeight;

    // the width of thumb
    private int mThumbWidth;

    // the size of track
    private int mTrackSize;

    private float mThumbCenterX;

    private float mLeft; // space between left of track and left of the view
    private float mRight; // space between right of track and left of the view
    private float mTrackLength; // pixel length of whole track
    private float mProgress; // real time value
    private float mMin; // min
    private float mMax; // max

    public IndicatorSeekbar(Context context) {
        this(context, null);
    }

    public IndicatorSeekbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mThumbHeight = 200;
        mThumbWidth = 300;
        mTrackSize = 120;
        mMin = 0;
        mMax = 100;
        mProgress = 40;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mThumbHeight;
        setMeasuredDimension(resolveSize(dp2px(180), widthMeasureSpec), height);

        mLeft = getPaddingLeft() + mThumbWidth / 2;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbWidth / 2;
        mTrackLength = mRight - mLeft;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int xLeft = getPaddingLeft() + mThumbWidth / 2;
        int xRight = getMeasuredWidth() - getPaddingRight() - mThumbWidth / 2;
        float yTop = getPaddingTop() + mThumbHeight / 2;

        mThumbCenterX = xLeft + mTrackLength * (mProgress - mMin) / (mMax - mMin);

        // draw track
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mTrackSize);
        canvas.drawLine(xLeft, yTop, mThumbCenterX, yTop, mPaint);

        // draw second track
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(mTrackSize);
        canvas.drawLine(mThumbCenterX, yTop, xRight, yTop, mPaint);


        // draw thumb
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(mThumbCenterX - mThumbWidth / 2, yTop - mThumbHeight / 2, mThumbCenterX + mThumbWidth / 2, yTop + mThumbHeight / 2, 20, 20, mPaint);
    }

    static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}
