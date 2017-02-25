package cn.huna.jerry.simplenettylibrary.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.huna.jerry.simplenettylibrary.model.ErrorModel;
import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;

/**
 * Created by lijie on 2017/2/25.
 */

public class RequestManager {
    private static final int TIME_OVER_CHECK_DELAY = 3;     //超时轮询时间
    private static final int DEFAULT_TIME_OVER_MILLISECOND = 1000;  //默认超时时间

    private ScheduledExecutorService timeOverService;
    private ConcurrentHashMap<String, RequestCallback> callbackMap;

    public RequestManager(){
        callbackMap = new ConcurrentHashMap<>();
    }

    /**
     * 添加请求
     * @param key
     * @param requestCallback
     */
    public void putRequest(String key, RequestCallback requestCallback){
        requestCallback.requestCreateTime = System.currentTimeMillis();
        callbackMap.put(key, requestCallback);
    }

    /**
     * 处理请求返回
     * @param transmissionModel
     */
    public void handleRequest(TransmissionModel transmissionModel){
        RequestManager.RequestCallback requestCallback = callbackMap.get(transmissionModel.transmissionIdentification);
        if (requestCallback != null){
            requestCallback.onSuc(transmissionModel);
        }
        callbackMap.remove(requestCallback);
    }

    /**
     * 开启超时检查
     */
    public void startTimeOverCheck(){
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
                        }
                    }
                    else {
                        if (System.currentTimeMillis() > requestCallback.requestCreateTime + DEFAULT_TIME_OVER_MILLISECOND){
                            //默认超时时间 超时
                        }
                    }
                }
            }
        }, 10, 1, TimeUnit.SECONDS);
    }

    /**
     * 结束超时检查
     */
    public void endTimeOverCheck(){
        timeOverService.shutdownNow();
    }


    /**
     * 请求回调
     */
    public abstract class RequestCallback {
        public long requestCreateTime;      //请求创建时间
        public int timeOverMilliseconds;    //超时时间

        abstract void onSuc(TransmissionModel transmissionModel);
        abstract void onError(ErrorModel errorModel);
    }
}
