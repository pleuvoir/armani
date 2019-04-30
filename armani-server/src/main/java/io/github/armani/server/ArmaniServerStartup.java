package io.github.armani.server;

import io.github.armani.common.codec.PacketDecoder;
import io.github.armani.common.codec.PacketEncoder;
import io.github.armani.common.utils.AttributeKeyConst;
import io.github.armani.common.utils.SessionMember;
import io.github.armani.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端
 */
public class ArmaniServerStartup {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArmaniServerStartup.class);

    public static final int DEFAULT_BIND_PORT = 8443;

    public static void main(String[] args) {

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,  1024 )  //设置socket缓冲区队列
                .childOption(ChannelOption.SO_KEEPALIVE,false ) //关闭2小时的长链接keepalive
                .childOption(ChannelOption.TCP_NODELAY,true )  //关闭Nagle算法，不进行批量合并发送
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        LOGGER.info("服务端有读写事件时触发"); //当有读写事件时会被触发
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 6, 4));
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(ChatMessageRequestHandler.INSTANCE);
                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        //给NioServerSocketChannel设置一个map，通常用不到！注意不要把他和下面那个弄混了
        serverBootstrap.attr(AttributeKey.newInstance("name"), " hello");

        //可以给每一条连接指定自定义属性，后续可以通过ctx.channel().attr(AttributeKey.valueOf("version")取出该属性
        serverBootstrap.childAttr(AttributeKey.newInstance("server-version"), "1.0.0");

        //设置通道用户是否已登录
        serverBootstrap.childAttr(AttributeKeyConst.SESSION_MEMBER, SessionMember.EMPTY);

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
