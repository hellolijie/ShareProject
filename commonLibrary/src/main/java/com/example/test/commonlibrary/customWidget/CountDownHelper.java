package com.example.test.commonlibrary.customWidget;

import android.widget.TextView;

/**
 * Created by lijie on 2017/3/6.
 */

public abstract class CountDownHelper {
    private int count;
    private TextView textView;
    private Runnable runnable;

    public CountDownHelper(TextView textView){
        this.textView = textView;
    }

    public void startCount(int startCount){
        count = startCount;
        count();
    }

    public void cancel(){
        textView.removeCallbacks(runnable);
    }

    private void count(){
        if (count <= 0){
            countFinish();
            return;
        }

        textView.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                count --;
                textView.setText(getCountText(count));

                count();
            }
        }, 1000);
    }

    public abstract String getCountText(int count);
    public abstract void countFinish();
}
