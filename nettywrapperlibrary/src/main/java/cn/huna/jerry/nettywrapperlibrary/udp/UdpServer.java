package cn.huna.jerry.nettywrapperlibrary.udp;

import android.text.TextUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;


/**
 * Created by lijie on 2016/4/6.
 */
public class UdpServer {

    private EventLoopGroup workerGroup;
    private DatagramListener datagramListener;

    public UdpServer(){
        workerGroup = new NioEventLoopGroup();
    }

    public void run(final DatagramListener datagramListener, int port){
        this.datagramListener = datagramListener;

        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioDatagramChannel.class);
        b.option(ChannelOption.SO_BROADCAST, true);
        b.option(ChannelOption.SO_REUSEADDR, true);
        b.handler(new SimpleChannelInboundHandler<DatagramPacket>() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket){
                try {
                    if (UdpServer.this.datagramListener != null){
                        ByteBuf buf = datagramPacket.copy().content();
                        byte[] req = new byte[buf.readableBytes()];
                        buf.readBytes(req);
                        String data = new String(req, "UTF-8");
                        if (!TextUtils.isEmpty(data)){
                            String backData = datagramListener.onReceivedData(data);
                            if (backData != null){
//                                channelHandlerContext.writeAndFlush(new DatagramPacket(new B, datagramPacket.sender()))
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        try {
            b.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void shutdown() {
        workerGroup.shutdownGracefully();
    }

    /**
     * 数据接收监听
     */
    public interface DatagramListener{
        String onReceivedData(String data);
    }
}
