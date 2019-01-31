package com.example.yzy.androidln.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzy on 2019/1/30 0030.
 */

public class MyBezierView extends View {

    public MyBezierView(Context context) {
        super(context);
    }

    public MyBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        Path path = new Path();
        path.moveTo(100, 300);
        path.quadTo(200, 200, 300, 300);
        path.quadTo(400, 400, 500, 300);

        canvas.drawPath(path, paint);
    }
}
