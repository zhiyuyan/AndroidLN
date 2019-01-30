package com.example.yzy.androidln.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzy on 2019/1/30 0030.
 */

public class MyTextView extends View {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLineX = 0;
        int baseLineY = 200;

        String text = "harvic's blog";

        Paint paint = new Paint();
        paint.setTextSize(120);

        Paint.FontMetrics fm = paint.getFontMetrics();
        paint.setStrokeWidth(4);

        int topLineY = (int) (baseLineY + fm.top);
        int ascentLineY = (int) (baseLineY + fm.ascent);
        int descentLineY = (int) (baseLineY + fm.descent);
        int bottomLineY = (int) (baseLineY + fm.bottom);

        // 画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        // 画top线
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, topLineY, 3000, topLineY, paint);

        // 画ascent线
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascentLineY, 3000, ascentLineY, paint);

        // 画descent线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descentLineY, 3000, descentLineY, paint);

        // 画bottom线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottomLineY, 3000, bottomLineY, paint);

        // 画Text所占的区域
        int width = (int) paint.measureText(text);
        Rect rect = new Rect(baseLineX, topLineY, baseLineX + width, bottomLineY);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);

        // 画最小矩形
        Rect minRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), minRect);
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        paint.setColor(Color.RED);
        canvas.drawRect(minRect, paint);

        // 写文字
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }
}
