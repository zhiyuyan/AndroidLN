package com.example.yzy.androidln.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by yzy on 2019/1/24 0024.
 */

public class DimenUtils {

    private DimenUtils() {

    }

    /**
     * dp转px
     *
     * @param context
     * @param dp dp值
     * @return px值
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp sp值
     * @return px值
     */
    public static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

}
