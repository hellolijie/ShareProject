package cn.huna.jerry.simplenettylibrary.server;

import com.google.gson.Gson;

import cn.huna.jerry.simplenettylibrary.model.TransmissionModel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lijie on 17/1/27.
 */

public class TcpServer {
    private TcpServerCore tcpServerCore;
    private OnHandelReceivedData onHandelReceivedData;

    public TcpServer(){
        tcpServerCore = new TcpServerCore(new TcpServerInboundHandler.ConnectionListener() {
            @Override
            public void onStateChange(ChannelHandlerContext channelContext, int state) {

            }

            @Override
            public void onDataReceived(ChannelHandlerContext channelContext, String msg) {
                try {
                    Gson gson = new Gson();

                    TransmissionModel transmissionModel = gson.fromJson(msg, TransmissionModel.class);
                    TransmissionModel backTransmissionModel = null;
                    backTransmissionModel = new TransmissionModel();
                    backTransmissionModel.transmissionIdentification = transmissionModel.transmissionIdentification;
                    backTransmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
                    if (onHandelReceivedData != null){
                        String backString = onHandelReceivedData.onReceivedData(transmissionModel.transmissionContent);
                        backTransmissionModel.transmissionContent = backString;
                    }

                    channelContext.writeAndFlush(gson.toJson(backTransmissionModel));

                }catch (Exception e){
                    e.printStackTrace();
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
     * 运行服务端
     * @param port
     */
    public void run(int port){
        tcpServerCore.run(port);
    }

    /**
     * 关闭服务端
     */
    public void shutdown(){
        tcpServerCore.shutdown();
    }

    /**
     * 接收到数据回调
     */
    public interface OnHandelReceivedData{
        /**
         * 处理接收到客户端数据接口
         * @param msgContent  返回给客户端的数据
         * @return
         */
        String onReceivedData(String msgContent);
    }
}
