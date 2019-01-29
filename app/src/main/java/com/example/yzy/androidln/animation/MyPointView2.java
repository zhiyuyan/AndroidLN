package com.example.yzy.androidln.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzy on 2019/1/29 0029.
 */

public class MyPointView2 extends View {

    private Point mPoint = new Point(100);

    public MyPointView2(Context context) {
        super(context);
    }

    public MyPointView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPointView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPoint != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(300, 300, mPoint.getRadius(), paint);
        }
        super.onDraw(canvas);
    }

    public int getPointRadius() {
        if (mPoint != null) {
            return mPoint.getRadius();
        }
        return 0;
    }

    public void setPointRadius(int radius) {
        mPoint.setRadius(radius);
        invalidate();
    }
}
