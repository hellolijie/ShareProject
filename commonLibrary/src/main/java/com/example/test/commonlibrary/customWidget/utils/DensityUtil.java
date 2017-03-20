package com.example.test.commonlibrary.customWidget.utils;

import android.content.Context;

/**
 * Created by admin1 on 2015/8/21.
 */
public class DensityUtil {

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px装dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 保留有效位小数
     * @param number
     * @param scale
     * @return
     */
    public static float operateFloatNumber(float number, int scale) {
        if (scale > 0) {
            return (float) (Math.round(number * scale)) / scale;
        }
        return 0.0f;
    }
}
