package com.example.test.commonlibrary.customWidget.animation;

import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijie on 2017/3/24.
 */

public class SuccessionAnimationHelper {
    private View view;
    private List<Animation> animationList;
    private int curAnimationIndex;
    private SuccessionAnimationListener successionAnimationListener;

    public SuccessionAnimationHelper(View view){
        this.view = view;
        animationList = new ArrayList<>();
    }

    public void addAnimation(Animation animation){
        animationList.add(animation);
    }

    public void start(){
        curAnimationIndex = 0;
        playAnimation();
    }

    public void cancel(){
        view.clearAnimation();
    }

    private void playAnimation(){
        Animation animation = animationList.get(curAnimationIndex);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (successionAnimationListener != null && curAnimationIndex == 0){
                    successionAnimationListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                curAnimationIndex ++;
                if (curAnimationIndex < animationList.size()){
                    playAnimation();
                }
                else {
                    if (successionAnimationListener != null){
                        successionAnimationListener.onEnd();
                        view.clearAnimation();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.clearAnimation();
        view.startAnimation(animation);

    }

    public void setSuccessionAnimationListener(SuccessionAnimationListener successionAnimationListener){
        this.successionAnimationListener = successionAnimationListener;
    }

    public interface SuccessionAnimationListener{
        void onStart();
        void onEnd();
    }
}
