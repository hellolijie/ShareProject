package cn.huna.jerry.simplenettylibrary.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.huna.jerry.simplenettylibrary.Utils;
import cn.huna.jerry.simplenettylibrary.model.ErrorModel;
import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;

/**
 * Created by lijie on 2017/2/25.
 */

public class RequestManager {
    private static final int TIME_OVER_CHECK_DELAY = 3000;     //超时轮询时间
    private static final int DEFAULT_TIME_OVER_MILLISECOND = 10000;  //默认超时时间

    private ScheduledExecutorService timeOverService;
    private ConcurrentHashMap<String, RequestCallback> callbackMap;

    public RequestManager(){
        callbackMap = new ConcurrentHashMap<>();
    }

    /**
     * 添加请求
     * @param requestCallback
     */
    public void putRequest(TransmissionModel transmissionModel, RequestCallback requestCallback){
        requestCallback.requestCreateTime = System.currentTimeMillis();
        requestCallback.requestKey = transmissionModel.transmissionIdentification;
        callbackMap.put(requestCallback.requestKey, requestCallback);
    }

    /**
     * 处理请求返回
     * @param transmissionModel
     */
    public void handleRequest(TransmissionModel transmissionModel){
        RequestManager.RequestCallback requestCallback = callbackMap.get(transmissionModel.transmissionIdentification);
        if (requestCallback != null){
            requestCallback.onSuc(transmissionModel.transmissionContent);
        }
        callbackMap.remove(requestCallback.requestKey);
    }

    /**
     * 开启超时检查
     */
    public void startTimeOverCheck(){
        if (timeOverService != null)
            timeOverService.shutdownNow();

        timeOverService = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        timeOverService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, RequestCallback> entry : callbackMap.entrySet()){
                    RequestCallback requestCallback = entry.getValue();
                    if (requestCallback.timeOverMilliseconds > 0){
                        if (System.currentTimeMillis() > requestCallback.requestCreateTime + requestCallback.timeOverMilliseconds){
                            //请求设置了超时时间 超时
                            callbackMap.remove(requestCallback.requestKey);
                            requestCallback.onError(ErrorModel.newModel(ErrorModel.ERROR_TIME_OUT, "请求超时"));
                        }
                    }
                    else {
                        if (System.currentTimeMillis() > requestCallback.requestCreateTime + DEFAULT_TIME_OVER_MILLISECOND){
                            //默认超时时间 超时
                            callbackMap.remove(requestCallback.requestKey);
                            requestCallback.onError(ErrorModel.newModel(ErrorModel.ERROR_TIME_OUT, "请求超时"));
                        }
                    }
                }
            }
        }, TIME_OVER_CHECK_DELAY, TIME_OVER_CHECK_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 结束超时检查
     */
    public void endTimeOverCheck(){
        timeOverService.shutdownNow();
        for (Map.Entry<String, RequestCallback> entry : callbackMap.entrySet()){
            RequestCallback requestCallback = entry.getValue();
            callbackMap.remove(requestCallback.requestKey);
            requestCallback.onError(ErrorModel.newModel(ErrorModel.ERROR_CONNECT_DISCONNECT, "连接断开"));
        }
    }

    /**
     * 请求回调
     */
    public static abstract class RequestCallback {
        public long requestCreateTime;      //请求创建时间
        public long timeOverMilliseconds;    //超时时间
        public String requestKey;           //请求唯一标识

        public abstract void onSuc(String msgContent);
        public abstract void onError(ErrorModel errorModel);
    }
}
