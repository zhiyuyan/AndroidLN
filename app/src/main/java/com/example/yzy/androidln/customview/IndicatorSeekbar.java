package com.example.yzy.androidln.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import com.example.yzy.androidln.R;

import java.text.DecimalFormat;

/**
 * Created by yzy on 2019/1/31 0031.
 */

public class IndicatorSeekbar extends View {

    // 小数点位数
    private int mDecimalPlaces;
    private DecimalFormat mDecimalFormat;

    // 画笔
    private Paint mPaint;

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

    // 最小值和最大值的文本颜色
    private int mMinTextColor;
    private int mMaxTextColor;

    // 最小值和最大值的文本尺寸
    private int mMinTextSize;
    private int mMaxTextSize;

    // thumb的颜色
    private int mThumbColor;
    // thumb的尺寸
    private int mThumbHeight;
    private int mThumbWidth;
    // thumb的文本的颜色
    private int mThumbTextColor;
    // thumb的文本的尺寸
    private int mThumbTextSize;

    // track的尺寸
    private int mTrackSize;
    // 第一段track的颜色
    private int mTrackFirstColor;
    // 第二段track的颜色
    private int mTrackSecondColor;

    // 第一段装饰点的颜色
    private int mDecorFirstColor;
    // 第一段装饰点的颜色
    private int mDecorSecondColor;

    // 气泡的半径
    private int mBubbleRadius;
    // 气泡的颜色
    private int mBubbleColor;
    // 气泡上文本的尺寸
    private int mBubbleTextSize;
    // 气泡上文本的颜色
    private int mBubbleTextColor;

    private int mTextSpace;

    private WindowManager mWindowManager;

    private BubbleView mBubbleView;

    private WindowManager.LayoutParams mLayoutParams;

    private int[] mPoint = new int[2];
    private float mBubbleCenterRawSolidX;
    private float mBubbleCenterRawSolidY;
    private float mBubbleCenterRawX;

    public IndicatorSeekbar(Context context) {
        this(context, null);
    }

    public IndicatorSeekbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorSeekbar, defStyleAttr, 0);

        mMinTextColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_min_text_color, Color.parseColor("#ffffff"));
        mMaxTextColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_max_text_color, Color.parseColor("#a8a8a8"));
        mMinTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorSeekbar_is_min_text_size, sp2px(10));
        mMaxTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorSeekbar_is_max_text_size, sp2px(10));

        mThumbColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_thumb_color, Color.parseColor("#ffffff"));
        mThumbWidth = typedArray.getDimensionPixelOffset(R.styleable.IndicatorSeekbar_is_thumb_width, dp2px(42));
        mThumbHeight = typedArray.getDimensionPixelOffset(R.styleable.IndicatorSeekbar_is_thumb_height, dp2px(30));
        mThumbTextColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_thumb_text_color, Color.parseColor("#333333"));
        mThumbTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorSeekbar_is_thumb_text_color, sp2px(16));

        mTrackSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorSeekbar_is_track_size, dp2px(20));
        mTrackFirstColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_track_first_color, Color.parseColor("#37aeff"));
        mTrackSecondColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_track_second_color, Color.parseColor("#e5e5e5"));

        mDecorFirstColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_decor_first_color, Color.parseColor("#74c6ff"));
        mDecorSecondColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_decor_second_color, Color.parseColor("#fafafa"));

        mMin = typedArray.getFloat(R.styleable.IndicatorSeekbar_is_min, 0f);
        mMax = typedArray.getFloat(R.styleable.IndicatorSeekbar_is_max, 100f);
        mProgress = typedArray.getFloat(R.styleable.IndicatorSeekbar_is_progress, 0f);

        mBubbleColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_bubble_color, Color.parseColor("#37aeff"));
        mBubbleTextColor = typedArray.getColor(R.styleable.IndicatorSeekbar_is_bubble_text_color, Color.parseColor("#ffffff"));
        mBubbleTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorSeekbar_is_bubble_text_size, sp2px(14));

        mDecimalPlaces = typedArray.getInteger(R.styleable.IndicatorSeekbar_is_decimal_places, 0);

        typedArray.recycle();

        buildDecimalFormat();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mDelta = mMax - mMin;
        mTextSpace = dp2px(2);

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mBubbleView = new BubbleView(context);
        mBubbleView.setProgressText(getFormatNumber(mProgress));
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;

        calculateRadiusOfBubble();
    }

    private void calculateRadiusOfBubble() {
        mPaint.setTextSize(mBubbleTextSize);

        // 计算滑到两端气泡里文字需要显示的宽度，比较取最大值为气泡的半径
        int w1 = getMinTextWidth();
        int w2 = getMaxTextWidth();
        mBubbleRadius = dp2px(14);
        int max = Math.max(mBubbleRadius, Math.max(w1, w2));
        mBubbleRadius = max + mTextSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mThumbHeight;
        setMeasuredDimension(resolveSize(dp2px(180), widthMeasureSpec), height);

        mLeft = getPaddingLeft() + mThumbWidth / 2;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbWidth / 2;
        mTrackLength = mRight - mLeft;

        mBubbleView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility != VISIBLE) {
            hideBubble();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        locationPositionOnScreen();
    }

    @Override
    protected void onDetachedFromWindow() {
        hideBubble();
        super.onDetachedFromWindow();
    }

    /**
     * 气泡BubbleView实际是通过WindowManager动态添加的一个视图，因此与SeekBar唯一的位置联系就是它们在屏幕上的
     * 绝对坐标。
     * 先计算进度mProgress为mMin时BubbleView的中心坐标（mBubbleCenterRawSolidX，mBubbleCenterRawSolidY），
     * 然后根据进度来增量计算横坐标mBubbleCenterRawX，再动态设置LayoutParameter.x，就实现了气泡跟随滑动移动。
     */
    private void locationPositionOnScreen() {
        getLocationOnScreen(mPoint);

        ViewParent parent = getParent();
        if (parent != null && parent instanceof View && ((View) parent).getMeasuredWidth() > 0) {
            mPoint[0] %= ((View) parent).getMeasuredWidth();
        }

        mBubbleCenterRawSolidX = mPoint[0] + mLeft - mBubbleView.getMeasuredWidth() / 2f;
        mBubbleCenterRawX = calculateCenterRawXofBubbleView();
        mBubbleCenterRawSolidY = mPoint[1] - mBubbleView.getMeasuredHeight();
        mBubbleCenterRawSolidY -= dp2px(24);

        Context context = getContext();
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            if (window != null) {
                int flags = window.getAttributes().flags;
                if ((flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0) {
                    Resources res = Resources.getSystem();
                    int id = res.getIdentifier("status_bar_height", "dimen", "android");
                    mBubbleCenterRawSolidY += res.getDimensionPixelSize(id);
                }
            }
        }
    }

    private float calculateCenterRawXofBubbleView() {
        return mBubbleCenterRawSolidX + mTrackLength * (mProgress - mMin) / mDelta;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                getParent().requestDisallowInterceptTouchEvent(true);

                mIsThumbOnDragging = isThumbTouched(event);
                if (mIsThumbOnDragging) {
                    showBubble();
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
                    mBubbleCenterRawX = calculateCenterRawXofBubbleView();
                    showBubble();
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
                    if (mBubbleView.getParent() != null) {
                        mBubbleCenterRawX = calculateCenterRawXofBubbleView();
                        mLayoutParams.x = (int) (mBubbleCenterRawX + 0.5f);
                        mWindowManager.updateViewLayout(mBubbleView, mLayoutParams);
                        mBubbleView.setProgressText(getFormatNumber(mProgress));
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBubbleView.animate()
                                .alpha(0f)
                                .setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        hideBubble();
                                        mIsThumbOnDragging = false;
                                        invalidate();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                        hideBubble();

                                        mIsThumbOnDragging = false;
                                        invalidate();
                                    }
                                }).start();
                    }
                }, 200);
                break;
        }
        return mIsThumbOnDragging || mIsTouchToSeek || super.onTouchEvent(event);
    }

    private void showBubble() {
        if (mBubbleView == null || mBubbleView.getParent() != null) {
            return;
        }

        mLayoutParams.x = (int) (mBubbleCenterRawX + 0.5f);
        mLayoutParams.y = (int) (mBubbleCenterRawSolidY + 0.5f);

        mBubbleView.setAlpha(0);
        mBubbleView.setVisibility(VISIBLE);
        mBubbleView.animate().alpha(1f).setDuration(mIsTouchToSeek ? 0 : 200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mWindowManager.addView(mBubbleView, mLayoutParams);
                    }
                }).start();
        mBubbleView.setProgressText(getFormatNumber(mProgress));
    }

    private void hideBubble() {
        if (mBubbleView == null)
            return;

        mBubbleView.setVisibility(GONE); // 防闪烁
        if (mBubbleView.getParent() != null) {
            mWindowManager.removeViewImmediate(mBubbleView);
        }
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
        mThumbCenterX = xLeft + mTrackLength * (mProgress - mMin) / (mMax - mMin);

        // draw track
        drawTrack(canvas);

        // draw min text
        drawMinText(canvas);

        // draw max text
        drawMaxText(canvas);

        // draw
        drawDecoration(canvas);

        // draw thumb
        drawThumb(canvas);
    }

    private void drawTrack(Canvas canvas) {
        int xLeft = getPaddingLeft() + mThumbWidth / 2;
        int xRight = getMeasuredWidth() - getPaddingRight() - mThumbWidth / 2;
        float yTop = getPaddingTop() + mThumbHeight / 2;

        mPaint.setStrokeWidth(mTrackSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // draw first track
        mPaint.setColor(mTrackFirstColor);
        canvas.drawLine(xLeft, yTop, mThumbCenterX, yTop, mPaint);

        // draw second track
        mPaint.setColor(mTrackSecondColor);
        canvas.drawLine(mThumbCenterX, yTop, xRight, yTop, mPaint);
    }

    private void drawMinText(Canvas canvas) {
        float x;
        float y;
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(mMinTextColor);
        mPaint.setTextSize(mMinTextSize);
        String minStr = getFormatNumber(mMin);
        mPaint.getTextBounds(minStr, 0, minStr.length(), mProgressTextRect);
        x = mLeft;
        y = getHeight() / 2f + mProgressTextRect.height() / 2f;
        canvas.drawText(minStr, x, y, mPaint);
    }

    private void drawMaxText(Canvas canvas) {
        float x;
        float y;
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(mMaxTextColor);
        mPaint.setTextSize(mMaxTextSize);
        String maxStr = getFormatNumber(mMax);
        mPaint.getTextBounds(maxStr, 0, maxStr.length(), mProgressTextRect);
        x = mRight;
        y = getHeight() / 2f + mProgressTextRect.height() / 2f;
        canvas.drawText(maxStr, x, y, mPaint);
    }

    private void drawDecoration(Canvas canvas) {
        int minStrWidth = getMinTextWidth();
        int maxStrWidth = getMaxTextWidth();

        int strWidth = Math.max(minStrWidth, maxStrWidth);
        float length = mTrackLength - 2 * strWidth;
        int step = dp2px(27);
        int delta = ((int) (length) / step);
        int reminder = ((int) length) % step;
        float x = getPaddingLeft() + mThumbWidth / 2 + reminder / 2 + strWidth;
        float y = getHeight() / 2f;
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.FILL);

        if (delta > 0) {
            for (int i = 0; i <= delta; i++) {
                if (x + step * i < mThumbCenterX) {
                    mPaint.setColor(mDecorFirstColor);
                } else {
                    mPaint.setColor(mDecorSecondColor);
                }
                canvas.drawPoint(x + step * i, y, mPaint);
            }
        }

    }

    private int getMinTextWidth() {
        Rect rect = new Rect();
        mPaint.setTextSize(mMinTextSize);
        String minStr = getFormatNumber(mMin);
        mPaint.getTextBounds(minStr, 0, minStr.length(), rect);
        return rect.width();
    }

    private int getMaxTextWidth() {
        Rect rect = new Rect();
        mPaint.setTextSize(mMaxTextSize);
        String minStr = getFormatNumber(mMax);
        mPaint.getTextBounds(minStr, 0, minStr.length(), rect);
        return rect.width();
    }

    private void drawThumb(Canvas canvas) {
        float yTop = getPaddingTop() + mThumbHeight / 2;

        // 绘制thumb
        mPaint.setStrokeWidth(2);
        mPaint.setColor(mThumbColor);
        canvas.drawRoundRect(mThumbCenterX - mThumbWidth / 2, yTop - mThumbHeight / 2 + 2, mThumbCenterX + mThumbWidth / 2, yTop + mThumbHeight / 2 - 2, mThumbHeight / 8, mThumbHeight / 8, mPaint);
        mPaint.setColor(Color.parseColor("#dddddd"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mThumbCenterX - mThumbWidth / 2, yTop - mThumbHeight / 2, mThumbCenterX + mThumbWidth / 2, yTop + mThumbHeight / 2, mThumbHeight / 8, mThumbHeight / 8, mPaint);

        // 绘制thumb文本
        mPaint.setTextSize(mThumbTextSize);
        mPaint.setColor(mThumbTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        String progressText = getFormatNumber(mProgress);
        mPaint.getTextBounds(progressText, 0, progressText.length(), mProgressTextRect);
        float thumbX = mThumbCenterX;
        float thumbY = getHeight() / 2f + mProgressTextRect.height() / 2f;

        canvas.drawText(progressText, thumbX, thumbY, mPaint);
    }

    /**
     * 小数点位数格式化
     */
    private void buildDecimalFormat() {
        StringBuilder stringBuilder = new StringBuilder("0");
        if (mDecimalPlaces > 0) {
            stringBuilder = new StringBuilder(".");
            for (int i = 0; i < mDecimalPlaces; i++) {
                stringBuilder.append("0");
            }
        }
        mDecimalFormat = new DecimalFormat(stringBuilder.toString());
    }

    /**
     * 返回格式化（保留特定小数位的字符串）
     *
     * @param num 要进行格式化的数值
     */
    private String getFormatNumber(float num) {
        return mDecimalFormat.format(num);
    }

    static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    private class BubbleView extends View {

        private Paint mBubblePaint;
        private Path mBubblePath;
        private RectF mBubbleRectF;
        private Rect mRect;
        private String mProgressText = "";

        BubbleView(Context context) {
            this(context, null);
        }

        BubbleView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            mBubblePaint = new Paint();
            mBubblePaint.setAntiAlias(true);
            mBubblePaint.setTextAlign(Paint.Align.CENTER);

            mBubblePath = new Path();
            mBubbleRectF = new RectF();
            mRect = new Rect();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            setMeasuredDimension(3 * mBubbleRadius, 3 * mBubbleRadius);

            mBubbleRectF.set(getMeasuredWidth() / 2f - mBubbleRadius, 0,
                    getMeasuredWidth() / 2f + mBubbleRadius, 2 * mBubbleRadius);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            mBubblePath.reset();
            float x0 = getMeasuredWidth() / 2f;
            float y0 = getMeasuredHeight() - mBubbleRadius / 3f;
            mBubblePath.moveTo(x0, y0);
            float x1 = (float) (getMeasuredWidth() / 2f - Math.sqrt(3) / 2f * mBubbleRadius);
            float y1 = 3 / 2f * mBubbleRadius;
            mBubblePath.quadTo(
                    x1 - dp2px(2), y1 - dp2px(2),
                    x1, y1
            );
            mBubblePath.arcTo(mBubbleRectF, 150, 240);

            float x2 = (float) (getMeasuredWidth() / 2f + Math.sqrt(3) / 2f * mBubbleRadius);
            mBubblePath.quadTo(
                    x2 + dp2px(2), y1 - dp2px(2),
                    x0, y0
            );
            mBubblePath.close();

            mBubblePaint.setColor(mBubbleColor);
            canvas.drawPath(mBubblePath, mBubblePaint);

            mBubblePaint.setTextSize(mBubbleTextSize);
            mBubblePaint.setColor(mBubbleTextColor);
            mBubblePaint.getTextBounds(mProgressText, 0, mProgressText.length(), mRect);
            Paint.FontMetrics fm = mBubblePaint.getFontMetrics();
            float baseline = mBubbleRadius + (fm.descent - fm.ascent) / 2f - fm.descent;
            canvas.drawText(mProgressText, getMeasuredWidth() / 2f, baseline, mBubblePaint);
        }

        void setProgressText(String progressText) {
            if (progressText != null && !mProgressText.equals(progressText)) {
                mProgressText = progressText;
                invalidate();
            }
        }
    }
}
