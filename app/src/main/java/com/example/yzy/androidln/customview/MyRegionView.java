package com.example.yzy.androidln.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyRegionView extends View {

    public MyRegionView(Context context) {
        super(context);
    }

    public MyRegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 初始化Paint
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        // 构造一个椭圆路径
        Path ovalPath = new Path();
        RectF rect = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rect, Path.Direction.CCW);
        // SetPath时，传入一个比椭圆区域小的矩形区域，让其取交集
        Region rgn = new Region();
        rgn.setPath(ovalPath, new Region(50, 50, 200, 200));
        // 画出路径
        drawRegion(canvas, rgn, paint);
    }

    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();
        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }
}
