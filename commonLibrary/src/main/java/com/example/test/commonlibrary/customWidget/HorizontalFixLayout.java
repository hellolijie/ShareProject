package com.example.test.commonlibrary.customWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lijie on 2017/4/17.
 */

public class HorizontalFixLayout extends LinearLayout {
    public HorizontalFixLayout(Context context) {
        super(context);
        init();
    }

    public HorizontalFixLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalFixLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void init(){
        setOrientation(HORIZONTAL);
    }

    public void addView(View view){
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        view.setLayoutParams(layoutParams);

        addView(view);
    }
}
