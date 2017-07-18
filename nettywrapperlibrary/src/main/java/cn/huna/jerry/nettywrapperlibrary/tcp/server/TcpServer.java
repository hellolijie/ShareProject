package cn.huna.jerry.nettywrapperlibrary.tcp.server;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.huna.jerry.nettywrapperlibrary.protocol.ProtocolUtil;
import cn.huna.jerry.nettywrapperlibrary.protocol.model.TransmissionModel;
import cn.huna.jerry.nettywrapperlibrary.protocol.request.RequestManager;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 17/1/27.
 */

public class TcpServer {
    private TcpServerCore tcpServerCore;
    private ReceivedDataListener receivedDataListener;

    private Map<ChannelHandlerContext, TcpClientNode> clientNodeCollections;
    private RequestManager requestManager;

    public TcpServer(){
        tcpServerCore = new TcpServerCore(new TcpServerInboundHandler.ConnectionListener() {
            @Override
            public void onStateChange(ChannelHandlerContext channelContext, int state) {
                switch (state){
                    case TcpServerInboundHandler.STATE_ACTIVE:      //连接
                        pushClientNode(channelContext);
                        break;
                    case TcpServerInboundHandler.STATE_INACTIVE:    //断开
                        removeClientNode(channelContext);
                        break;
                }
            }

            @Override
            public void onDataReceived(ChannelHandlerContext channelContext, String msg) {
                try {
                    Gson gson = new Gson();

                    TransmissionModel transmissionModel = gson.fromJson(msg, TransmissionModel.class);
                    switch (transmissionModel.transmissionType) {   //请求
                        case TransmissionModel.TYPE_REQUEST:
                            channelContext.writeAndFlush(gson.toJson(handleRequest(channelContext, transmissionModel)));
                            break;
                        case TransmissionModel.TYPE_PUSH:       //推送
                            requestManager.handleRequest(transmissionModel);
                            break;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        clientNodeCollections = new HashMap<>();
        requestManager = new RequestManager();
    }

    /**
     * 添加客户端节点
     * @param channelContext
     */
    private void pushClientNode(ChannelHandlerContext channelContext){
        TcpClientNode clientNode = clientNodeCollections.get(channelContext);
        if (clientNode == null){
            clientNode = new TcpClientNode(channelContext, requestManager);
        }
        clientNode.onConnect();
        clientNodeCollections.put(channelContext, clientNode);
    }

    /**
     * 移除客户端节点
     * @param channelContext
     */
    private void removeClientNode(ChannelHandlerContext channelContext){
        TcpClientNode clientNode = clientNodeCollections.get(channelContext);
        if (clientNode != null){
            clientNode.onDisconnect();
        }
        clientNodeCollections.remove(channelContext);
    }

    /**
     * 处理客户端请求
     * @param transmissionModel
     * @return
     */
    private TransmissionModel handleRequest(ChannelHandlerContext channelHandlerContext, TransmissionModel transmissionModel){

        TransmissionModel backTransmissionModel = ProtocolUtil.createBackRequestModel(transmissionModel);
        if (receivedDataListener != null) {
            TcpClientNode clientNode = clientNodeCollections.get(channelHandlerContext);
            if (clientNode != null) {
                String backString = receivedDataListener.onReceivedData(clientNode, transmissionModel.transmissionContent);
                backTransmissionModel.transmissionContent = backString;
            }
        }

        return backTransmissionModel;
    }

    /**
     * 设置数据接收监听器
     * @param receivedDataListener
     */
    public void setReceivedDataListener(ReceivedDataListener receivedDataListener){
        this.receivedDataListener = receivedDataListener;
    }

    /**
     * 运行服务端
     * @param port
     */
    public void run(int port){
        tcpServerCore.run(port);
        requestManager.startTimeOverCheck();
    }

    /**
     * 关闭服务端
     */
    public void shutdown(){
        tcpServerCore.shutdown();
        clientNodeCollections.clear();
        requestManager.endTimeOverCheck();
    }

    /**
     * 接收到数据回调
     */
    public interface ReceivedDataListener {
        /**
         * 处理接收到客户端数据接口
         * @param msgContent  返回给客户端的数据
         * @return
         */
        String onReceivedData(TcpClientNode clientNode, String msgContent);
    }
}
