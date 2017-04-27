package cn.huna.jerry.simplenettylibrary.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * 连接状态回调
 */
public interface ConnectionStateListener {
    void onDisconnect(ChannelHandlerContext channelContext);
    void onConnect(ChannelHandlerContext channelContext);
    void onHeartBeatTimeOver(ChannelHandlerContext channelContext);
}
