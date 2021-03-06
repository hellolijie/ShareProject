package cn.huna.jerry.simplenettylibrary.server;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by lijie on 17/1/25.
 */

public class TcpServerCore {
    // 设置10秒检测chanel是否接受过心跳数据
    private static final int READ_WAIT_SECONDS = 5;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private TcpServerInboundHandler.ConnectionListener connectionListener;


    public TcpServerCore(TcpServerInboundHandler.ConnectionListener connectionListener){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        this.connectionListener = connectionListener;
    }

    /**
     * 启动服务端
     * 会阻塞
     */
    public void run(final int port){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup);
                b.channel(NioServerSocketChannel.class);
//                b.option(ChannelOption.SO_BACKLOG, 100);
//                b.option(ChannelOption.SO_REUSEADDR, true);
//                b.handler(new LoggingHandler(LogLevel.INFO));
                b.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast("pong", new IdleStateHandler(READ_WAIT_SECONDS, READ_WAIT_SECONDS, READ_WAIT_SECONDS, TimeUnit.SECONDS));
                        pipeline.addLast("handler", new TcpServerInboundHandler().setConnectionListener(connectionListener));
                    }

                });

                try {
                    ChannelFuture bindFuture = b.bind(port);
                    bindFuture.sync().channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    shutdown();
                }
            }
        }).start();

    }

    /**
     * 关闭
     */
    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
