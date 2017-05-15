package com.example.test.atmdemo.atmControl;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lijie on 2017/5/15.
 */

public class ReceivedDataResolver {
    private static final String COMMAND_START = "$$$";
    private static final String COMMAND_END = "%%%";

    private StringBuffer dataBuffer;

    protected ReceivedDataResolver(){
        dataBuffer = new StringBuffer();
    }

    /**
     * 添加数据到缓存区
     * @param data
     */
    protected void append(String data){
        dataBuffer.append(data);
    }

    /**
     * 解析命令
     * @return
     */
    protected String resolveCommand(){
        int startIndex = dataBuffer.indexOf(COMMAND_START);
        if (startIndex == -1)
            return null;

        int endIndex = dataBuffer.indexOf(COMMAND_END);
        if (endIndex == -1)
            return null;

        endIndex += COMMAND_END.length();
        String command = dataBuffer.substring(startIndex, endIndex);
        dataBuffer.delete(0, endIndex);

        Log.d("SerialPort", dataBuffer.toString() + "----");
        return command;
    }

}
