package com.example.yzy.androidln.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
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
    private float mDelta; // max - min
    private float mDeltaX;

    // 进度文字位置信息
    private Rect mProgressTextRect = new Rect();

    // 是否是拖动thumb
    private boolean mIsThumbOnDragging;

    // 是否点击track，直接跳转到某数值
    private boolean mIsTouchToSeek;

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
        mDelta = mMax - mMin;
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                getParent().requestDisallowInterceptTouchEvent(true);

                mIsThumbOnDragging = isThumbTouched(event);
                if (mIsThumbOnDragging) {
                    invalidate();
                } else if (isTrackTouched(event)) {
                    mIsThumbOnDragging = true;

                    mThumbCenterX = event.getX();
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    mProgress = calculateProgress();
                    invalidate();
                }
                mDeltaX = mThumbCenterX - event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsThumbOnDragging) {
                    mThumbCenterX = event.getX() + mDeltaX;
                    if (mThumbCenterX < mLeft) {
                        mThumbCenterX = mLeft;
                    }
                    if (mThumbCenterX > mRight) {
                        mThumbCenterX = mRight;
                    }
                    mProgress = calculateProgress();
                    invalidate();
                }
                break;
        }
        return mIsThumbOnDragging || mIsTouchToSeek || super.onTouchEvent(event);
    }

    /**
     * 判断是否是触摸Thumb
     *
     * @param event 触摸事件
     * @return
     */
    private boolean isThumbTouched(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int xLeft = getPaddingLeft() + mThumbWidth / 2;
        float distance = xLeft + mTrackLength / mDelta * (mProgress - mMin);
        if (event.getX() >= distance - mThumbWidth / 2
                && event.getX() <= distance + mThumbWidth / 2) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是触摸track
     *
     * @param event 触摸事件
     * @return
     */
    private boolean isTrackTouched(MotionEvent event) {
        return isEnabled() && event.getX() >= getPaddingLeft() && event.getX() <= getMeasuredWidth() - getPaddingRight()
                && event.getY() >= getPaddingTop() && event.getY() <= getMeasuredHeight() - getPaddingBottom();
    }

    private float calculateProgress() {
        return (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // track的左侧起始点
        int xLeft = getPaddingLeft() + mThumbWidth / 2;
        int xRight = getMeasuredWidth() - getPaddingRight() - mThumbWidth / 2;
        float yTop = getPaddingTop() + mThumbHeight / 2;

        mThumbCenterX = xLeft + mTrackLength * (mProgress - mMin) / (mMax - mMin);

        // draw track
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mTrackSize);
        canvas.drawLine(xLeft, yTop, mThumbCenterX, yTop, mPaint);

        // draw second track
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(mTrackSize);
        canvas.drawLine(mThumbCenterX, yTop, xRight, yTop, mPaint);

        mPaint.setTextSize(sp2px(14));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);

        // thumb偏移量
        float thumbX = mThumbCenterX;
        float thumbY = getHeight() / 2f + mProgressTextRect.height() / 2f;

        // draw min text
        canvas.drawText(String.valueOf(mMin), mLeft + 48, thumbY, mPaint);

        // draw max text
        canvas.drawText(String.valueOf(mMax), mRight - 48, thumbY, mPaint);

        // draw thumb
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawRoundRect(mThumbCenterX - mThumbWidth / 2, yTop - mThumbHeight / 2 + 2, mThumbCenterX + mThumbWidth / 2, yTop + mThumbHeight / 2 - 2, 20, 20, mPaint);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mThumbCenterX - mThumbWidth / 2, yTop - mThumbHeight / 2, mThumbCenterX + mThumbWidth / 2, yTop + mThumbHeight / 2, 20, 20, mPaint);

        String progressText = String.valueOf(Math.round(mProgress));
        mPaint.setTextSize(sp2px(14));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.getTextBounds(progressText, 0, progressText.length(), mProgressTextRect);
        canvas.drawText(progressText, thumbX, thumbY, mPaint);
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
