package com.example.test.commonlibrary.customWidget.animation;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by lijie on 2017/3/24.
 */

public class RepeatAnimationHelper {
    private View view;
    private Animation animation;

    public RepeatAnimationHelper(View view){
        this.view = view;
    }

    public void setAnimation(Animation animation){
        this.animation = animation;
    }

    public void start(){
        playAnimation();
    }

    public void cancel(){
        view.clearAnimation();
    }

    private void playAnimation(){
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animation);
    }
}
