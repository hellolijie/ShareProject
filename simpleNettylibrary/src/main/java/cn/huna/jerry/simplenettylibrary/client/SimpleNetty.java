package cn.huna.jerry.simplenettylibrary.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
 * Created by lijie on 2017/4/21.
 */

public class SimpleNetty {
    // 多长时间未请求后，发送心跳
    private static final int WRITE_WAIT_SECONDS = 5;

    private static SimpleNetty instance;

    private EventLoopGroup workerGroup;
    private Bootstrap bootstrap;

    private SimpleNetty(){
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
//            b.option(ChannelOption.AUTO_READ, false);
    }

    public static synchronized SimpleNetty getInstance(){
        if (instance == null)
            instance = new SimpleNetty();
        return instance;
    }

    /**
     * 建立连接
     * @param host
     * @param port
     * @return
     */
    public TcpClient connect(String host, int port, ConnectionStateListener connectionStateListener){

        final TcpClientInboundHandler tcpClientInboundHandler = new TcpClientInboundHandler();
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                pipeline.addLast("ping", new IdleStateHandler(WRITE_WAIT_SECONDS, WRITE_WAIT_SECONDS, WRITE_WAIT_SECONDS, TimeUnit.SECONDS));

                pipeline.addLast("handler", tcpClientInboundHandler);
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // Start the client.

        ChannelFuture channelFuture = bootstrap.connect(host, port);

        TcpClient tcpClient = new TcpClient(channelFuture, tcpClientInboundHandler);
        tcpClient.setConnectionStateListener(connectionStateListener);

        return tcpClient;

    }

    /**
     * 关闭所有连接
     */
    public void shutDownAll(){
        try {
            workerGroup.shutdownGracefully();
            instance = null;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
