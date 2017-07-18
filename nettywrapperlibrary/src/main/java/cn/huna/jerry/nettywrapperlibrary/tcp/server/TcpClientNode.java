package cn.huna.jerry.nettywrapperlibrary.tcp.server;

import com.google.gson.Gson;

import java.net.SocketAddress;

import cn.huna.jerry.nettywrapperlibrary.protocol.ProtocolUtil;
import cn.huna.jerry.nettywrapperlibrary.protocol.model.TransmissionModel;
import cn.huna.jerry.nettywrapperlibrary.protocol.request.RequestCallback;
import cn.huna.jerry.nettywrapperlibrary.protocol.request.RequestManager;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 2017/7/11.
 */

public class TcpClientNode {
    private ChannelHandlerContext channelContext;
    private RequestManager requestManager;

    private ConnectionStateListener connectionStateListener;

    private boolean connection;

    public TcpClientNode(ChannelHandlerContext channelContext, RequestManager requestManager){
        this.channelContext = channelContext;
        this.requestManager = requestManager;
    }

    /**
     * 获取客户端IP
     * @return
     */
    public SocketAddress getRemoteAddress(){
        return channelContext.channel().remoteAddress();
    }

    /**
     * 推送数据
     * @param msg
     * @param requestCallback
     * @param timeOverMilliseconds
     */
    public void pushMsg(String msg, RequestCallback requestCallback, int timeOverMilliseconds){
        TransmissionModel transmissionModel = ProtocolUtil.createPushRequestModel(msg);
        requestCallback.timeOverMilliseconds = timeOverMilliseconds;
        if (requestCallback != null)
            requestManager.putRequest(transmissionModel, requestCallback);
        channelContext.writeAndFlush(new Gson().toJson(transmissionModel));
    }

    /**
     * 推送数据
     * @param msg
     * @param requestCallback
     */
    public void pushMsg(String msg, RequestCallback requestCallback){
        pushMsg(msg, requestCallback, 0);
    }

    /**
     * 推送数据
     * @param msg
     */
    public void pushMsg(String msg){
        pushMsg(msg, null, 0);
    }

    /**
     * 获取连接状态
     * @return
     */
    public boolean isConnection(){
        return connection;
    }

    /**
     * 设置连接状态监听
     * @param connectionStateListener
     */
    public void setConnectionStateListener(ConnectionStateListener connectionStateListener){
        this.connectionStateListener = connectionStateListener;
    }

    /**
     * 断开连接
     */
    protected void onDisconnect(){
        connection = false;
        if (connectionStateListener != null){
            connectionStateListener.onDisconnect();
        }
    }

    /**
     * 建立连接
     */
    protected void onConnect(){
        connection = true;
        if (connectionStateListener != null){
            connectionStateListener.onConnect();
        }
    }

    public interface ConnectionStateListener{
        void onConnect();
        void onDisconnect();
    }
}
