package cn.huna.jerry.simplenettylibrary.client;

import com.google.gson.Gson;

import cn.huna.jerry.simplenettylibrary.Utils;
import cn.huna.jerry.simplenettylibrary.model.ErrorModel;
import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 17/1/26.
 */

public class TcpClientExecutor {
    public static final int CONNECT_STATE_CONNECT = 1;       //连接成功
    public static final int CONNECT_STATE_DISCONNECT = -1;       //连接断开
    public static final int CONNECT_STATE_TIMEOUT = -2;     //连接超时

//    private TcpClient tcpClient;
    private Channel channel;

    private RequestManager requestManager;
    private TcpClientInboundHandler tcpClientInboundHandler;
    private PushListener pushListener;
    private ConnectionStateListener connectionStateListener;

    private int connectState = -1;

    public TcpClientExecutor(Channel channel, TcpClientInboundHandler tcpClientInboundHandler){
        requestManager = new RequestManager();
        this.tcpClientInboundHandler = tcpClientInboundHandler;
        this.channel = channel;

//        tcpClient = new TcpClient(tcpClientInboundHandler);

        tcpClientInboundHandler.setConnectionListener(new TcpClientInboundHandler.ConnectionListener() {
            @Override
            public void onStateChange(ChannelHandlerContext channelContext, int state) {
                if (connectionStateListener != null){
                    if (state == TcpClientInboundHandler.STATE_ACTIVE){
                        connectionStateListener.onConnect(channelContext);
                        connectState = CONNECT_STATE_CONNECT;
                    }
                    else if (state == TcpClientInboundHandler.STATE_INACTIVE){
                        connectionStateListener.onDisconnect(channelContext);
                        connectState = CONNECT_STATE_DISCONNECT;
                    }
                    else if (state == TcpClientInboundHandler.STATE_TIME_OVER){
                        connectionStateListener.onHeartBeatTimeOver(channelContext);
                        connectState = CONNECT_STATE_TIMEOUT;
                    }
                    else if (state == TcpClientInboundHandler.STATE_READ_COMPLETE){
                        connectState = CONNECT_STATE_CONNECT;
                    }
                }
            }

            @Override
            public void onDataReceived(ChannelHandlerContext channelContext, String msg) {
                try {
                    TransmissionModel transmissionModel = new Gson().fromJson(msg, TransmissionModel.class);

                    switch (transmissionModel.transmissionType){
                        case TransmissionModel.TYPE_REQUEST:    //请求
                            requestManager.handleRequest(transmissionModel);
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

    /**
     * 处理推送
     * @param transmissionModel
     */
    private void handlePush(TransmissionModel transmissionModel){
        if (pushListener != null){
            pushListener.onPushMessage(transmissionModel.transmissionContent);
        }
    }

    /**
     * 关闭连接
     */
    public void shutdown(){
        channel.close().awaitUninterruptibly();
        requestManager.endTimeOverCheck();
    }

    /**
     * 发送消息到服务端
     * @param msg
     * @param requestCallback
     */
    public void sendMsg(String msg, RequestManager.RequestCallback requestCallback){
        sendMsg(msg, requestCallback, 0);
    }

    /**
     * 发送消息到服务端
     * @param msg
     * @param requestCallback
     */
    public void sendMsg(String msg, RequestManager.RequestCallback requestCallback, int timeOverMilliseconds){

        if (connectState != CONNECT_STATE_CONNECT){
            requestCallback.onError(ErrorModel.newModel(ErrorModel.ERROR_CONNECT_DISCONNECT, "未连接"));
            return;
        }

        TransmissionModel transmissionModel = new TransmissionModel();
        transmissionModel.transmissionContent = msg;
        transmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
        transmissionModel.transmissionIdentification = createIdentification();

        requestCallback.requestCreateTime = System.currentTimeMillis();
        requestCallback.timeOverMilliseconds = timeOverMilliseconds;

        requestManager.putRequest(transmissionModel, requestCallback);
//        tcpClient.sendMsg(new Gson().toJson(transmissionModel));

        try {
            channel.writeAndFlush(new Gson().toJson(transmissionModel)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * 推送监听器
     */
    public interface PushListener {
        void onPushMessage(String pushContent);
    }

    /**
     * 状态回调
     */
    public interface ConnectionStateListener {
        void onDisconnect(ChannelHandlerContext channelContext);
        void onConnect(ChannelHandlerContext channelContext);
        void onHeartBeatTimeOver(ChannelHandlerContext channelContext);
    }
}
