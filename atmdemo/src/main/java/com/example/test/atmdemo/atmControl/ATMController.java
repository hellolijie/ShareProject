package com.example.test.atmdemo.atmControl;

import android.os.Handler;
import android.text.TextUtils;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.test.atmdemo.atmControl.serialPort.SerialPortController;

/**
 * Created by lijie on 2017/5/4.
 */

public class ATMController {
    private static final int TIME_OVER_CHECK_DELAY = 500;     //超时轮询时间
    private static final int DEFAULT_TIME_OVER_MILLISECOND = 2000;  //默认超时时间

    private static ATMController instance;
    private Handler serialPortHandler;
    private ATMListener atmListener;
    private CopyOnWriteArrayList<CommandResponse> commandResponseList;

    private ScheduledExecutorService timeOverService;
    private ReceivedDataResolver receivedDataResolver;
    private SendDataController sendDataController;

    public synchronized static ATMController getInstance(){
        if (instance == null)
            instance = new ATMController();
        return instance;
    }

    /**
     * 初始化
     */
    public void initSerialPort(){
//        serialPortHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case SerialPortController.SERIAL_PORT_RECEIVE_DATA_NEW:
//                        analysisReceiveData((String) msg.obj);
//                        break;
//                }
//
//            }
//        };
//        SerialPortController.getInstance().setHandlerNew(serialPortHandler);
        receivedDataResolver = new ReceivedDataResolver();
        sendDataController = new SendDataController();

        SerialPortController.getInstance().openSerialPortNew("/dev/ttySAC2", 115200);

        SerialPortController.getInstance().setOnReceivedDataListener(new SerialPortController.OnReceivedDataListener() {
            @Override
            public void onReceived(String data) {
                receivedDataResolver.append(data);
                String command = receivedDataResolver.resolveCommand();
                if (command != null){
                    analysisReceiveData(command);
                }
            }
        });

        startTimeOverCheck();
        commandResponseList = new CopyOnWriteArrayList<>();
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort(){
        timeOverService.shutdownNow();
        sendDataController.close();
        for (CommandResponse commandResponse : commandResponseList) {
            commandResponse.onTimeOver();
            commandResponseList.remove(commandResponse);
        }
        SerialPortController.getInstance().closeSerialPortNew();
    }

    /**
     * 开始超时检查
     */
    private void startTimeOverCheck(){
        if (timeOverService != null){
            timeOverService.shutdownNow();
        }

        timeOverService = Executors.newSingleThreadScheduledExecutor();
        timeOverService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                traversalCommandResponse(true, null);
            }
        }, TIME_OVER_CHECK_DELAY, TIME_OVER_CHECK_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 解析串口回调数据
     * @param receivedData
     */
    private void analysisReceiveData(String receivedData){
        if (receivedData == null)
            return;

        receivedData = receivedData.trim();

        if (atmListener != null){
            atmListener.onReceived(receivedData);
        }

        if (!TextUtils.isEmpty(receivedData) && receivedData.startsWith("$$$") && receivedData.endsWith("%%%")){
            String data = TextUtils.substring(receivedData, "$$$|".length(), receivedData.length() - "%%%".length());
            String[] dataArr = data.split("|");
            if (dataArr != null && dataArr.length > 0){
                traversalCommandResponse(false, dataArr);
            }

        }
    }

    /**
     * 遍历发送数据回调，需要同步
     * @param timeOverCheck
     * @param dataArr
     */
    private synchronized void traversalCommandResponse(boolean timeOverCheck, String[] dataArr){
        if (timeOverCheck) {
            //检查超时
            long curTime = System.currentTimeMillis();
            for (CommandResponse commandResponse : commandResponseList) {
                if (commandResponse.SEND_TIMESTAMP > 0
                        && commandResponse.SEND_TIMESTAMP < curTime - DEFAULT_TIME_OVER_MILLISECOND){
                    commandResponse.onTimeOver();
                    commandResponseList.remove(commandResponse);
                }
            }
        }
        else {
            //数据返回回调
            for (CommandResponse commandResponse : commandResponseList){
                if (commandResponse.matchCommand(dataArr)){
                    commandResponse.onResponse(dataArr);
                    commandResponseList.remove(commandResponse);
                }
            }
        }
    }

    /**
     * 发送命令
     * @param command
     * @param commandResponse
     */
    public void sendCommand(String command, CommandResponse commandResponse){
        sendCommand(command);

        if (commandResponse != null) {
            commandResponseList.add(commandResponse);
            commandResponse.SEND_TIMESTAMP = System.currentTimeMillis();
        }
    }

    /**
     * 发送命令
     * @param command
     */
    public void sendCommand(String command){
//        SerialPortController.getInstance().writeDataNew(command);
        sendDataController.pushCommand(command);
    }

    /**
     * 监听一次指令
     * @param commandResponse
     */
    public void listenOnce(CommandResponse commandResponse){
        commandResponseList.add(commandResponse);
        commandResponse.SEND_TIMESTAMP = -1;
    }

    public void setAtmListener(ATMListener atmListener){
        this.atmListener = atmListener;
    }

    /**
     * 发送命令,串口监听回调
     */
    public abstract class CommandResponse{
        public long SEND_TIMESTAMP;

        abstract void onResponse(String[] dataArr);
        abstract void onTimeOver();
        abstract boolean matchCommand(String[] dataArr);
    }

    /**
     * ATM串口监听
     */
    public interface ATMListener{
        void onReceived(String data);
    }

}
