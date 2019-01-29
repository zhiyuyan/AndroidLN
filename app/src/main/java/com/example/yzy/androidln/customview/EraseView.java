package com.example.yzy.androidln.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzy on 2019/1/29 0029.
 */

public class EraseView extends View {
    public EraseView(Context context) {
        super(context);
    }

    public EraseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EraseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas1 = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#001122"));
        Paint mEraser = new Paint();
        mEraser.setColor(0xFFFFFFFF);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas1.drawRoundRect(new RectF(0, 0, 50, 50), 12, 12, mEraser);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
