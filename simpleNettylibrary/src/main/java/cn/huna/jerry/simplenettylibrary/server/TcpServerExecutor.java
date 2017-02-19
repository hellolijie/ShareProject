package cn.huna.jerry.simplenettylibrary.server;

import com.google.gson.Gson;

import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 17/1/27.
 */

public class TcpServerExecutor {
    private TcpServer tcpServer;
    private TcpServerInboundHandler tcpServerInboundHandler;

    private OnHandelReceivedData onHandelReceivedData;

    public TcpServerExecutor(){
        tcpServerInboundHandler = new TcpServerInboundHandler();

        tcpServer = new TcpServer(tcpServerInboundHandler);

        tcpServerInboundHandler.setConnectionListener(new TcpServerInboundHandler.ConnectionListener() {
            @Override
            public void onStateChange(ChannelHandlerContext channelContext, int state) {

            }

            @Override
            public void onDataReceived(ChannelHandlerContext channelContext, String msg) {
                try {
                    Gson gson = new Gson();

                    TransmissionModel transmissionModel = gson.fromJson(msg, TransmissionModel.class);
                    TransmissionModel backTransmissionModel = null;
                    if (onHandelReceivedData != null){
                        backTransmissionModel = onHandelReceivedData.onReceivedData(transmissionModel);
                    }

                    if (backTransmissionModel == null){
                        backTransmissionModel = new TransmissionModel();
                        backTransmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
                    }

                    channelContext.writeAndFlush(gson.toJson(backTransmissionModel));

                }catch (Exception e){

                }
            }
        });
    }

    /**
     * 推送数据
     * @param handlerContext
     * @param msg
     */
    public void pushMsg(ChannelHandlerContext handlerContext, String msg){
        TransmissionModel transmissionModel = new TransmissionModel();
        transmissionModel.transmissionType = TransmissionModel.TYPE_PUSH;
        transmissionModel.transmissionContent = msg;
        handlerContext.writeAndFlush(new Gson().toJson(transmissionModel));
    }

    /**
     * 设置数据接收监听器
     * @param onHandelReceivedData
     */
    public void setOnHandelReceivedData(OnHandelReceivedData onHandelReceivedData){
        this.onHandelReceivedData = onHandelReceivedData;
    }

    /**
     * 运行服务端  ps:是阻塞的
     * @param port
     */
    public void run(int port){
        tcpServer.run(port);
    }

    /**
     * 关闭服务端
     */
    public void shutdown(){
        tcpServer.shutdown();
    }

    /**
     * 接收到数据回调
     */
    public interface OnHandelReceivedData{
        /**
         * 处理接收到客户端数据接口
         * @param transmissionModel  返回给客户端的数据
         * @return
         */
        TransmissionModel onReceivedData(TransmissionModel transmissionModel);
    }
}
