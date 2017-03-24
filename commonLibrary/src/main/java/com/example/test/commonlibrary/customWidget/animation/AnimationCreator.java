package com.example.test.commonlibrary.customWidget.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by lijie on 2017/3/24.
 */

public class AnimationCreator {
    public static Animation createTraslateAnimation(float fromXValue,
                                                    float toXValue,
                                                    float fromYValue,
                                                    float toYValue,
                                                    long duration){
        TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, fromXValue,
                        Animation.RELATIVE_TO_SELF, toXValue,
                        Animation.RELATIVE_TO_SELF, fromYValue,
                        Animation.RELATIVE_TO_SELF, toYValue);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillBefore(true);
        translateAnimation.setFillAfter(true);

        return translateAnimation;
    }

    public static Animation createAlphaAnimation(float fromAlpha, float toAlpha, long duration){
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillBefore(true);
        alphaAnimation.setFillAfter(true);

        return alphaAnimation;
    }

    public static Animation createScaleAnimation(float fromX,
                                                 float toX,
                                                 float fromY,
                                                 float toY,
                                                 float pivotX,
                                                 float pivotY,
                                                 long duration){
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, pivotX,
                Animation.RELATIVE_TO_SELF, pivotY);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillBefore(true);
        scaleAnimation.setFillAfter(true);

        return scaleAnimation;
    }

    public static Animation createRotateAnimation(float fromDegrees,
                                                  float toDegrees,
                                                  float pivotX,
                                                  float pivotY,
                                                  long duration){
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF,pivotX,
                Animation.RELATIVE_TO_SELF,pivotY);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setFillBefore(true);

        return rotateAnimation;
    }
}
