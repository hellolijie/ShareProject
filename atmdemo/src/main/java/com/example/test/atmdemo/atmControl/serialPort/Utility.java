package com.example.test.atmdemo.atmControl.serialPort;

/**
 * Created by lijie on 2017/5/4.
 */

public class Utility {
    /**
     * 累加和校验码
     *
     * @param str 数据
     * @return
     */
    public static String getCheckSum(String str) {
        if (null == str || str.isEmpty()) {
            return "";
        }
        int data = 0;
        for(int i=0; i < str.length(); i++){
            data += (int)str.charAt(i);
        }

        return String.valueOf(data);
    }

}
