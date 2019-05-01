package io.github.armani.client.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.common.protocol.packet.request.PingRequestPacket;
import io.github.armani.common.protocol.packet.response.PongResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 定时心跳<br>
 * 连接建立后每隔5秒向服务端发送心跳报文，这个时间一般设置为控线检测时间的1/3
 */
public class HeartBeatTimerHandler extends SimpleChannelInboundHandler<PongResponsePacket> {

	private static final int HEARTBEAT_INTERVAL = 5;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		LOG.info("客户端已连接，开启定时发送心跳报文任务。");

		sendHeartPacketPeriodicity(ctx);

		ctx.fireChannelActive();
	}

	private void sendHeartPacketPeriodicity(ChannelHandlerContext ctx) {
		ctx.executor().schedule(() -> {
			if (ctx.channel().isActive()) {
				ctx.channel().writeAndFlush(PingRequestPacket.INSTANCE);
				this.sendHeartPacketPeriodicity(ctx); // 如果连接存活那么递归这个任务 数秒后继续发送，不用关闭连接
			}
		}, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PongResponsePacket msg) throws Exception {
		LOG.info("接收到服务端心跳响应：{}", msg.toJSON());
	}

	private static final Logger LOG = LoggerFactory.getLogger(HeartBeatTimerHandler.class);

}
