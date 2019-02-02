package com.example.yzy.androidln.dispatchevent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.yzy.androidln.utils.MyUtils;

/**
 * Created by yzy on 2019/2/2 0002.
 */

public class MyViewGroup extends FrameLayout {

    public MyViewGroup(@NonNull Context context) {
        super(context);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(MyUtils.TAG, this.getClass().getSimpleName() + ":onInterceptTouchEvent();" + "ev:" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(MyUtils.TAG, this.getClass().getSimpleName() + ":dispatchTouchEvent();" + "ev:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MyUtils.TAG, this.getClass().getSimpleName() + ":onTouchEvent();" + "ev:" + event.getAction());
        return super.onTouchEvent(event);
    }
}
