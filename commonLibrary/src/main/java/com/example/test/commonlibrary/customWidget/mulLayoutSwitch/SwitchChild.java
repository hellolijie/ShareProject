package com.example.test.commonlibrary.customWidget.mulLayoutSwitch;

import android.view.View;

/**
 * Created by lijie on 2017/2/9.
 */

public class SwitchChild {
    protected View view;
    protected MulSwitchManager switchManager;
    public SwitchChild(View view){
        this.view = view;
    }

    /**
     * 设置所属管理对象
     * @param switchManager
     */
    protected void setSwitchManager(MulSwitchManager switchManager){
        this.switchManager = switchManager;
    }

    protected View getView(){
        return view;
    }

    protected void onShow(){

    }

    protected void onDismiss(){

    }
}
