package io.github.armani.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.client.command.ConsoleCommandManager;
import io.github.armani.common.ArmaniConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 客户端
 */
public class ArmaniClientStartup {

	private static final Logger LOG = LoggerFactory.getLogger(ArmaniClientStartup.class);
	
	private static CountDownLatch awaitConnectLatch = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {

		final Bootstrap bootstrap = new Bootstrap();
		final NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try {
			bootstrap.group(eventLoopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ArmaniConst.CONNECT_TIMEOUT_MILLIS)
					.option(ChannelOption.SO_KEEPALIVE, false)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ArmaniClientInitializer());

			ChannelFuture f = connectWithRetry(bootstrap, new InetSocketAddress("127.0.0.1", 8443), ArmaniConst.MAX_RETRY_CONNECT_NUM);
			awaitConnectLatch.await();
			f.channel().closeFuture().sync();
		}finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

	private static ChannelFuture connectWithRetry(Bootstrap bootstrap, SocketAddress remoteAddress, int retryNum) {
		return bootstrap.connect(remoteAddress).addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()) {
					awaitConnectLatch.countDown();
					ChannelFuture f = (ChannelFuture) future;
					LOG.info("Armani Client connect success at {}", f.channel().remoteAddress());
					ConsoleCommandManager.INSTANCE.startConsoleInput(f.channel());
				} else if (retryNum == 0) {
					LOG.error("尝试重连到达上限，不再进行连接。");
					awaitConnectLatch.countDown();
				} else {
					// 第几次重连
					int sequence = ArmaniConst.MAX_RETRY_CONNECT_NUM - retryNum + 1;
					int delay = 1 << sequence;
					LOG.warn("客户端第{}次连接失败，{}，{}秒后尝试重新连接... ", sequence, future.cause().getMessage(), delay);
					bootstrap.config().group().schedule(() -> {
						connectWithRetry(bootstrap, remoteAddress, retryNum - 1);
					}, delay, TimeUnit.SECONDS);
				}
			}
		});
	}

}
