package cn.huna.jerry.simplenettylibrary.client;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by lijie on 17/1/25.
 */

public class TcpClient {
    // 多长时间未请求后，发送心跳
    private static final int WRITE_WAIT_SECONDS = 5;

    private Channel channel;
    private EventLoopGroup workerGroup;
    private ChannelHandler channelHandler;

    public TcpClient(ChannelHandler channelHandler){
        this.channelHandler = channelHandler;
    }

    /**
     * 连接
     * @param host
     * @param port
     * @throws InterruptedException
     */
    public void connect(final String host, final int port) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                workerGroup = new NioEventLoopGroup();
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
//            b.option(ChannelOption.AUTO_READ, false);

                b.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                        pipeline.addLast("ping", new IdleStateHandler(WRITE_WAIT_SECONDS, WRITE_WAIT_SECONDS, WRITE_WAIT_SECONDS, TimeUnit.SECONDS));

                        pipeline.addLast("handler", channelHandler);
                    }
                });

                // Start the client.

                try {
                    channel = b.connect(host, port).sync().channel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 发送消息 异步
     * @param msg
     */
    public ChannelFuture sendMsg(String msg){

        if(channel != null) {
            return channel.writeAndFlush(msg);
        }
        return null;
    }


    /**
     * 发送消息 同步
     * @param msg
     * @return
     */
    public ChannelFuture sendMsgSync(String msg){

        ChannelFuture channelFuture = null;

        if(channel != null){
            try {
                channelFuture = channel.writeAndFlush(msg).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return channelFuture;
    }

    /**
     * 关闭
     */
    public void shutdown(){
        try {
            if(channel != null){
                channel.close().awaitUninterruptibly();
                workerGroup.shutdownGracefully();
                Logger.d("channel close");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
