package io.github.armani.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.common.ArmaniConst;
import io.github.armani.common.utils.AttributeKeyConst;
import io.github.armani.common.utils.SessionMember;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 */
public class ArmaniServerStartup {

	private static final Logger LOG = LoggerFactory.getLogger(ArmaniServerStartup.class);

	public static final int DEFAULT_BIND_PORT = ArmaniConst.SERVER_PORT;

	public static void main(String[] args) throws Exception {

		final ServerBootstrap bootstrap = new ServerBootstrap();

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			bootstrap.group(bossGroup, workGroup)
					.localAddress(new InetSocketAddress(DEFAULT_BIND_PORT))
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024) 
					.childOption(ChannelOption.SO_KEEPALIVE, false)
					.childOption(ChannelOption.TCP_NODELAY, true) 
					.childHandler(new ArmaniServerInitializer());
			// 设置通道用户是否已登录
			bootstrap.childAttr(AttributeKeyConst.SESSION_MEMBER, SessionMember.EMPTY);
			ChannelFuture f = bootstrap.bind().sync();
			LOG.info("Armani Server started at port {}", DEFAULT_BIND_PORT);
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

}
