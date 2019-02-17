package com.example.yzy.androidln.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomView extends View {

    private int mLastX;
    private int mLastY;

    private Scroller mScroller;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 4000);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取手指触摸点的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动的距离
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;

                // 方法一：layout()方法
//                // 调用layout方法来重新放置它的位置
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);

                // 方法二：offsetLeftAndRight()和offsetTopAndBottom()方法
//                // 对left和right进行偏移
//                offsetLeftAndRight(offsetX);
//                // 对top和bottom进行偏移
//                offsetTopAndBottom(offsetY);

                // 方法三：改变布局参数LayoutParams
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);

                // 方法四：scrollTo与scrollBy
                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                break;
        }
        return true;
    }
}
