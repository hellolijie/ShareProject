package com.example.test.commonlibrary.customWidget.animation;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by lijie on 2017/3/24.
 */

public class CutToAnimationHelper {
    private View viewIn;
    private View viewOut;
    private Animation animationIn;
    private Animation animationOut;

    private CutToStepListener cutToStepListener;

    public CutToAnimationHelper(View viewIn, View viewOut, Animation animationIn, Animation animationOut){
        this.viewIn = viewIn;
        this.viewOut = viewOut;
        this.animationIn = animationIn;
        this.animationOut = animationOut;

        initAnimationListener();
    }

    private void initAnimationListener(){
        animationIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (cutToStepListener != null){
                    cutToStepListener.onInAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (cutToStepListener != null){
                    cutToStepListener.onInAnimationFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (cutToStepListener != null){
                    cutToStepListener.onOutAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (cutToStepListener != null){
                    cutToStepListener.onOutAnimationFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void cutTo(long outStartDelay, long inStartDelay, long outDuration, long inDuration){
        animationOut.setDuration(outDuration);
        animationIn.setDuration(inDuration);

        if (outStartDelay > 0){
            viewOut.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewOut.startAnimation(animationOut);
                }
            }, outStartDelay);
        }
        else {
            viewOut.startAnimation(animationOut);
        }

        if (inStartDelay > 0){
            viewIn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewIn.startAnimation(animationIn);
                }
            }, inStartDelay);
        }
        else {
            viewIn.startAnimation(animationIn);
        }
    }

    public void setCutToStepListener(CutToStepListener cutToStepListener){
        this.cutToStepListener = cutToStepListener;
    }

    public interface CutToStepListener{
        void onOutAnimationStart();
        void onOutAnimationFinish();
        void onInAnimationStart();
        void onInAnimationFinish();
    }
}
