package cn.huna.jerry.simplenettylibrary.server;

import android.util.Log;

import com.orhanobut.logger.Logger;

import cn.huna.jerry.simplenettylibrary.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by lijie on 17/1/25.
 */

public class TcpServerInboundHandler extends SimpleChannelInboundHandler<String> {
    public static final int STATE_REGISTERED = 0;
    public static final int STATE_UNREGISTERED = 1;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 3;
    public static final int STATE_READ_COMPLETE = 4;

    private static final String TAG = "TcpServerInboundHandler";
    // 定义客户端没有收到客户端的ping消息的最大次数
    private static final int MAX_UN_REC_PING_TIMES = 3;

    private int unRecPingTimes;

    private ConnectionListener connectionListener;

    /**
     * 设置 连接状态监听器
     * @param connectionListener
     */
    public TcpServerInboundHandler setConnectionListener(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
        return this;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Logger.d("channelRegistered: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_REGISTERED);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        Logger.d("channelUnregistered: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_UNREGISTERED);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Logger.d("channelActive: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_ACTIVE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Logger.d("channelInactive: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_INACTIVE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        unRecPingTimes = 0;

        Logger.d("channelReadComplete: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_READ_COMPLETE);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
//        Logger.d("userEventTriggered: ");

        try {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.READER_IDLE) {
	                /*读时*/
//                Logger.d("===服务端===(READER_IDLE 读超时)");
                    // 失败计数器次数大于等于3次的时候，关闭链接，等待client重连
                    if(unRecPingTimes >= MAX_UN_REC_PING_TIMES){
//                    Logger.d("===服务端===(读超时，关闭chanel)");
                        // 连续超过N次未收到client的ping消息，那么关闭该通道，等待client重连
                        ctx.channel().close();
//                        netWorkListener.onStateChange(NetWorkListener.TYPE_INACTIVE, ctx, "channelInactive");
                        unRecPingTimes = 0;
                    }else{
                        // 失败计数器加1
                        unRecPingTimes++;
                    }
                } else if (event.state() == IdleState.WRITER_IDLE) {
	                /*写超时*/
//                Logger.d("===服务端===(WRITER_IDLE 写超时)");
                } else if (event.state() == IdleState.ALL_IDLE) {
	                /*总超时*/
//                Logger.d("===服务端===(ALL_IDLE 总超时)");
                }
            }
        }catch (Exception e){

        }

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        Logger.d("channelWritabilityChanged: ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Logger.d("exceptionCaught: ");

        ctx.close();
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        try {
            unRecPingTimes = 0;
            if (Constants.HEARTBEAT_MESSAGE.equals(msg)){
                //收到心跳 返回数据
                channelHandlerContext.channel().writeAndFlush(Constants.HEARTBEAT_MESSAGE);
            }
            else {
                if (connectionListener != null){
                    connectionListener.onDataReceived(channelHandlerContext, msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 连接监听器
     */
    public interface ConnectionListener{
        void onStateChange(ChannelHandlerContext channelContext, int state);
        void onDataReceived(ChannelHandlerContext channelContext, String msg);
    }
}
