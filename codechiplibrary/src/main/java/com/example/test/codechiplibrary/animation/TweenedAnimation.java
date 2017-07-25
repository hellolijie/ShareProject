package com.example.test.codechiplibrary.animation;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.example.test.codechiplibrary.R;

/**
 * Created by lijie on 2017/7/24.
 */

public class TweenedAnimation {

    /**
     * 透明度
     * @param view
     */
    public void testAlphaAnimation(View view){
        //创建一个AnimationSet对象，参数为Boolean型，
        //true表示使用Animation的interpolator，false则是使用自己的
        AnimationSet animationSet = new AnimationSet(true);
        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        //设置动画执行的时间
        alphaAnimation.setDuration(500);
        //将alphaAnimation对象添加到AnimationSet当中
        animationSet.addAnimation(alphaAnimation);
        //使用ImageView的startAnimation方法执行动画
        view.startAnimation(animationSet);
    }

    /**
     * 旋转
     * @param view
     */
    public void testRotateAnimation(View view){
        AnimationSet animationSet = new AnimationSet(true);
        //参数1：从哪个旋转角度开始
        //参数2：转到什么角度
        //后4个参数用于设置围绕着旋转的圆的圆心在哪里
        //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
        //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数5：确定y轴坐标的类型
        //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        animationSet.addAnimation(rotateAnimation);
        view.startAnimation(animationSet);
    }

    /**
     * 缩放
     * @param view
     */
    public void testScaleAnimation(View view){
        AnimationSet animationSet = new AnimationSet(true);
        //参数1：x轴的初始值
        //参数2：x轴收缩后的值
        //参数3：y轴的初始值
        //参数4：y轴收缩后的值
        //参数5：确定x轴坐标的类型
        //参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数7：确定y轴坐标的类型
        //参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 0.1f,0,0.1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        animationSet.addAnimation(scaleAnimation);
        view.startAnimation(animationSet);
    }

    /**
     * 移动
     * @param view
     */
    public void testTranslateAnimation(View view){
        AnimationSet animationSet = new AnimationSet(true);
        //参数1～2：x轴的开始位置
        //参数3～4：y轴的开始位置
        //参数5～6：x轴的结束位置
        //参数7～8：x轴的结束位置
        TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0.5f);
        translateAnimation.setDuration(1000);
        animationSet.addAnimation(translateAnimation);
        view.startAnimation(animationSet);
    }

    /**
     * 加载xml文件动画
     * @param activity
     * @param view
     */
    public void testXmlAnimation(Activity activity, View view){
        // 使用AnimationUtils装载动画配置文件
        Animation animation = AnimationUtils.loadAnimation(
                activity, R.anim.alpha);
        // 启动动画
        view.startAnimation(animation);
    }

    /**
     * LayoutAnimationsController
     * @param activity
     * @param listView
     */
    public void testLayoutAnimation(Activity activity, ListView listView){
        Animation animation = (Animation) AnimationUtils.loadAnimation(
                activity, R.anim.layout_anim);

        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.5f);
        listView.setLayoutAnimation(controller);
        listView.startLayoutAnimation();
    }
}
