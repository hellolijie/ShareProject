package com.example.test.atmdemo.atmControl;

import com.example.test.atmdemo.atmControl.serialPort.SerialPortController;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijie on 2017/5/15.
 */

public class SendDataController {
    private LinkedBlockingQueue<String> blockingQueue;
    private ScheduledExecutorService sendDataService;

    protected SendDataController(){
        blockingQueue = new LinkedBlockingQueue();
        sendDataService = Executors.newSingleThreadScheduledExecutor();
        sendDataService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String command = blockingQueue.poll();
                if (command != null){
                    SerialPortController.getInstance().writeDataNew(command);
                }
            }
        }, 300, 300, TimeUnit.MILLISECONDS);
    }

    /**
     * 添加命令到队列
     * @param command
     */
    protected void pushCommand(String command){
        blockingQueue.offer(command);
    }

    /**
     * 关闭
     */
    protected void close(){
        sendDataService.shutdownNow();
    }
}
