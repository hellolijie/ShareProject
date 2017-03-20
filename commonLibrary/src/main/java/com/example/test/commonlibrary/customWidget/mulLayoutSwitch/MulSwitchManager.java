package com.example.test.commonlibrary.customWidget.mulLayoutSwitch;

import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lijie on 2017/2/9.
 */

public class MulSwitchManager {
    private FrameLayout layout;
    private Map<String, SwitchChild> childes;
    private CopyOnWriteArrayList<SwitchChild> showedChildList;

    public MulSwitchManager(FrameLayout layout){
        this.layout = layout;
        childes = new HashMap<>();
        showedChildList = new CopyOnWriteArrayList<>();
    }

    /**
     * 添加子布局
     * @param tag
     * @param switchChild
     */
    public void addChildLayout(String tag, SwitchChild switchChild){
        childes.put(tag, switchChild);
        switchChild.setSwitchManager(this);
    }

    /**
     * 只显示一个布局
     * @param tag
     */
    public void showChildOnly(String tag){
        layout.removeAllViews();
        for (SwitchChild showedChild : showedChildList){
            showedChild.onDismiss();
        }

        showedChildList.clear();
        SwitchChild switchChild = childes.get(tag);
        switchChild.onShow();

        layout.addView(switchChild.getView());
        showedChildList.add(switchChild);
    }

    /**
     * 堆叠一个控件
     * @param tag
     */
    public void pileChild(String tag){

        int showedChildCount = showedChildList.size();
        if (showedChildCount > 0){
            showedChildList.get(showedChildCount - 1).onDismiss();
        }

        SwitchChild switchChild = childes.get(tag);
        layout.addView(switchChild.getView());
        showedChildList.add(switchChild);
        switchChild.onShow();
    }

    /**
     * 后退
     */
    public void back(){
        int childCount = layout.getChildCount();

        showedChildList.get(childCount -1).onDismiss();

        layout.removeViewAt(childCount - 1);
        showedChildList.remove(childCount - 1);

        if (childCount > 1){
            showedChildList.get(childCount - 2).onShow();
        }
    }
}
