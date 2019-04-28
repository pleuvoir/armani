package io.github.amani;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端
 */
public class NettyServer {

    public static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    public static final int DEFAULT_BIND_PORT = 8443;

    public static void main(String[] args) {

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_BACKLOG,  1024 )  //设置socket缓冲区队列
                .childOption(ChannelOption.SO_KEEPALIVE,false ) //关闭2小时的长链接
                .childOption(ChannelOption.TCP_NODELAY,true )   //关闭Nagle算法，不进行批量发送
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        LOGGER.info("服务端启动中");
                    }
                });

        //给NioServerSocketChannel设置一个map，通常用不到
        serverBootstrap.attr(AttributeKey.newInstance("duck"), " gaga");
        serverBootstrap.attr(AttributeKey.newInstance("project"), " armani");

        //可以给每一条连接指定自定义属性，后续可以通过channel.attr()取出该属性
        serverBootstrap.childAttr(AttributeKey.newInstance("name"), "hello");

        bindWithRetry(serverBootstrap, DEFAULT_BIND_PORT);
    }

    private static void bindWithRetry(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("服务端口{}绑定成功。", port);
                } else {
                    LOGGER.warn("服务端口{}绑定失败，尝试重试。", port);
                    bindWithRetry(serverBootstrap, port + 1);
                }
            }
        });
    }

}
