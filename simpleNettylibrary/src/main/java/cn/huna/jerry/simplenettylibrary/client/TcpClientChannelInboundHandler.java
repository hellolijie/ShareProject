package cn.huna.jerry.simplenettylibrary.client;

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

public class TcpClientChannelInboundHandler extends SimpleChannelInboundHandler<String> {
    private static final String TAG = "TcpClientInboundHandler";
    public static final int STATE_REGISTERED = 0;
    public static final int STATE_UNREGISTERED = 1;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 3;
    public static final int STATE_READ_COMPLETE = 4;
    public static final int STATE_TIME_OVER = 5;

    // 定义客户端没有收到服务端的pong消息的最大次数
    private static final int MAX_UN_REC_PONG_TIMES = 5;

    private int unRecPongTimes;

    private ConnectionListener connectionListener;

    /**
     * 设置 连接状态监听器
     * @param connectionListener
     */
    public void setConnectionListener(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Log.d(TAG, "channelRegistered: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_REGISTERED);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        Log.d(TAG, "channelUnregistered: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_UNREGISTERED);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d(TAG, "channelActive: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_ACTIVE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.d(TAG, "channelInactive: ");
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_INACTIVE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        Log.d(TAG, "channelReadComplete: ");
        unRecPongTimes = 0;
        if (connectionListener != null){
            connectionListener.onStateChange(ctx, STATE_READ_COMPLETE);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
//        Logger.d(TAG, "userEventTriggered: ");

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
	                /*读超时*/
//                Logger.d("===客户端===(READER_IDLE 读超时)");
            } else if (event.state() == IdleState.WRITER_IDLE) {
	                /*写超时*/
                if(unRecPongTimes < MAX_UN_REC_PONG_TIMES){
                    ctx.channel().writeAndFlush(Constants.HEARTBEAT_MESSAGE) ;
                    unRecPongTimes++;
                }else{
                    Logger.d("===客户端===(WRITER_IDLE 写超时)");
                    unRecPongTimes = 0;
                    if (connectionListener != null){
                        connectionListener.onStateChange(ctx, STATE_TIME_OVER);
                    }
                }
            } else if (event.state() == IdleState.ALL_IDLE) {
	                /*总超时*/
//                Logger.d("===客户端===(ALL_IDLE 总超时)");
            }
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        Log.d(TAG, "channelWritabilityChanged: ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.d(TAG, "exceptionCaught: ");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        try {
            unRecPongTimes = 0;
            if (Constants.HEARTBEAT_MESSAGE.equals(msg)){
                //收到心跳返回数据
                return;
            }

            if (connectionListener != null){
                connectionListener.onDataReceived(channelHandlerContext, msg);
            }
        }catch (Exception e){

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
