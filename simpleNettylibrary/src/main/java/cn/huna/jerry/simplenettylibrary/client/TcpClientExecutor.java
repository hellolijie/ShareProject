package cn.huna.jerry.simplenettylibrary.client;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.huna.jerry.simplenettylibrary.Utils;
import cn.huna.jerry.simplenettylibrary.model.ErrorModel;
import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 17/1/26.
 */

public class TcpClientExecutor {
    private TcpClient tcpClient;
    private Map<String, RequestCallback> callbackMap;
    private TcpClientChannelInboundHandler tcpClientChannelInboundHandler;
    private PushListener pushListener;
    private ConnectionStateListener connectionStateListener;

    public TcpClientExecutor(){
        tcpClientChannelInboundHandler = new TcpClientChannelInboundHandler();

        tcpClient = new TcpClient(tcpClientChannelInboundHandler);
        callbackMap = new HashMap<>();

        tcpClientChannelInboundHandler.setConnectionListener(new TcpClientChannelInboundHandler.ConnectionListener() {
            @Override
            public void onStateChange(ChannelHandlerContext channelContext, int state) {
                if (connectionStateListener != null){
                    if (state == TcpClientChannelInboundHandler.STATE_ACTIVE){
                        connectionStateListener.onConnect(channelContext);
                    }
                    else if (state == TcpClientChannelInboundHandler.STATE_INACTIVE){
                        connectionStateListener.onDisnect(channelContext);
                    }
                    else if (state == TcpClientChannelInboundHandler.STATE_TIME_OVER){
                        connectionStateListener.onTimeOver(channelContext);
                    }
                }
            }

            @Override
            public void onDataReceived(ChannelHandlerContext channelContext, String msg) {
                try {
                    TransmissionModel transmissionModel = new Gson().fromJson(msg, TransmissionModel.class);

                    switch (transmissionModel.transmissionType){
                        case TransmissionModel.TYPE_REQUEST:    //请求
                            handleRequest(transmissionModel);
                            break;
                        case TransmissionModel.TYPE_PUSH:       //推送
                            handlePush(transmissionModel);
                            break;
                    }
                }catch (Exception e){

                }
            }
        });
    }

    private void timeOver(){
        
    }

    /**
     * 处理请求返回
     * @param transmissionModel
     */
    private void handleRequest(TransmissionModel transmissionModel){
        RequestCallback requestCallback = callbackMap.get(transmissionModel.transmissionIdentification);
        if (requestCallback != null){
            requestCallback.onSuc(transmissionModel);
        }
        callbackMap.remove(requestCallback);
    }

    /**
     * 处理推送
     * @param transmissionModel
     */
    private void handlePush(TransmissionModel transmissionModel){
        if (pushListener != null){
            pushListener.onPushMessage(transmissionModel);
        }
    }

    /**
     * 连接服务端
     * @param host
     * @param port
     */
    public void connect(String host, int port){
        try {
            tcpClient.connect(host, port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void shutdown(){
        tcpClient.shutdown();
    }

    /**
     * 发送消息到服务端
     * @param msg
     * @param requestCallback
     */
    public void sendMsg(String msg, RequestCallback requestCallback){
        TransmissionModel transmissionModel = new TransmissionModel();
        transmissionModel.transmissionContent = msg;
        transmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
        transmissionModel.transmissionIdentification = createIdentification();

        requestCallback.requestCreateTime = System.currentTimeMillis();
        callbackMap.put(transmissionModel.transmissionIdentification, requestCallback);
        tcpClient.sendMsg(new Gson().toJson(transmissionModel));
    }

    /**
     * 设置推送监听器
     * @param pushListener
     */
    public void setPushListener(PushListener pushListener){
        this.pushListener = pushListener;
    }

    /**
     * 设置连接状态监听
     * @param connectionStateListener
     */
    public void setConnectionStateListener(ConnectionStateListener connectionStateListener){
        this.connectionStateListener = connectionStateListener;
    }

    /**
     * 生成唯一标识符
     * @return
     */
    public String createIdentification(){
        return Utils.md5(System.currentTimeMillis() + "-" + Math.random());
    }

    /**
     * 请求回调
     */
    public abstract class RequestCallback {
        public long requestCreateTime;

        abstract void onSuc(TransmissionModel transmissionModel);
        abstract void onError(ErrorModel errorModel);
    }

    /**
     * 推送监听器
     */
    public interface PushListener {
        void onPushMessage(TransmissionModel transmissionModel);
    }

    /**
     * 状态回调
     */
    public interface ConnectionStateListener {
        void onDisnect(ChannelHandlerContext channelContext);
        void onConnect(ChannelHandlerContext channelContext);
        void onTimeOver(ChannelHandlerContext channelContext);
    }
}
